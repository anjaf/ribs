String.format = function() {
    var s = arguments[0];
    for (var i = 0; i < arguments.length - 1; i++) {
        var reg = new RegExp("\\{" + i + "\\}", "gm");
        s = s.replace(reg, arguments[i + 1]);
    }

    return s;
}
!function(d) {

    linkMap = {'pmc':'http://europepmc.org/articles/{0}',
        'pmid':'http://europepmc.org/abstract/MED/{0}',
        'doi':'http://dx.doi.org/{0}',
        'chembl':'https://www.ebi.ac.uk/chembldb/compound/inspect/{0}',
        'ega':'http://www.ebi.ac.uk/ega/studies/{0}'
    };
    orgOrder= [];

    var params = document.location.search.replace(/(^\?)/,'').split("&").map(
        function(s) {
            return s = s.split("="), this[s[0]] = s[1], this
        }.bind({}))[0];
    registerHelpers();

    // Prepare template
    var templateSource = $('script#study-template').html();
    var template = Handlebars.compile(templateSource);
    var url = window.location.href;
    url = url.replace('studies','api/studies');
    // Data in json
    $.getJSON(url,params, function (data) {
        // Generate html using template and data
        data.submissions[0].section.accno = data.submissions[0].accno;
        var html = template(data.submissions[0].section);
        // Add the result to the DOM
        d.getElementById('renderedContent').innerHTML = html;

        postRender();

    });
}(document);

function registerHelpers() {

    Handlebars.registerHelper('find', function(key, keyval, val, obj) {
        var e = obj.filter( function(o) { return o[key]==keyval})[0];
        if (e!=undefined) return new Handlebars.SafeString(e[val]);
    });

    Handlebars.registerHelper('valueWithName', function(val, obj) {
        if (obj==null) return;
        var e = obj.filter( function(o) { return o['name']==val})[0];
        return (e!=undefined) ? new Handlebars.SafeString(e['value']) : '-';
    });

    Handlebars.registerHelper('section', function(o, shouldIndent) {
        var template = Handlebars.compile($('script#section-template').html());
        this.indentClass = (shouldIndent=='true') ? 'indented-section' : '';
        return template(o);
    });

    Handlebars.registerHelper('table', function(o) {
        var template = Handlebars.compile($('script#section-table').html());
        return template(o);
    });

    Handlebars.registerHelper('ifArray', function(arr,options) {
        if(Array.isArray(arr)) {
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    });

    Handlebars.registerHelper('ifRenderable', function(arr,options) {
        var specialSections = ['author', 'organization', 'funding', 'publication'];

        if($.inArray(arr.type.toLowerCase(),specialSections) < 0) {
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    });

    Handlebars.registerHelper('eachGroup', function(key, val, arr, options) {
        var mod = arr.reduce(function(r, i) {
            r[i[key]] = r[i[key]] || [];
            r[i[key]].push(i[val]);
            return r;
        }, {})
        var ret = '';
        for(var k in mod) {
            ret = ret + options.fn({name:k,value:mod[k].join(',')});
        }
        return ret;
    });


    Handlebars.registerHelper('setHeaders', function() {
        var names = $.map(this, function (v) {
            return $.map(v.attributes, function (v) {
                return v.name
            })
        })
        this.headers = $.unique(names);
        this.type = this[0].type
    });

    Handlebars.registerHelper('setFileTableHeaders', function() {
        if (this && this.length) {
            var names = ['Name', 'Size'];
            var hsh = {'Name': 1, 'Size': 1};
            $.each(this, function (i, v) {
                v.attributes = v.attributes || [];
                v.attributes.push({"name": "Name", "value": v.path});
                $.each(v.attributes, function (i, v) {
                    if (!(v.name in hsh)) {
                        names.push(v.name);
                        hsh[v.name] = 1;
                    }
                })
            });
            this.headers = names;
            this.type = this[0].type
        }
    });

    Handlebars.registerHelper('file-table', function() {
        var o = findall(this,'files');
        var template = Handlebars.compile($('script#file-table').html());
        return template(o);
    });

    Handlebars.registerHelper('link-table', function(o) {
        if (!o.links && !o.links.length) return null;
        var names = ['Name'];
        var hsh = {'Name':1};
        $.each(o.links, function (i, v) {
            v.attributes = v.attributes || [];
            v.attributes.push({"name": "Name", "value": v.url});
            $.each(v.attributes, function (i, v) {
                if (!(v.name in hsh)) {
                    names.push(v.name);
                    hsh[v.name] = 1;
                }
            })
        });
        o.linkHeaders = names;
        var template = Handlebars.compile($('script#link-table').html());
        return template(o);
    });

    Handlebars.registerHelper('eachLinkTable', function(options) {
        var ret = '';
        var links = findall(this,'links');
        $.each(links, function(i,v) {
            ret = ret + options.fn({links: ($.isArray(v) ? v : [v]) });
        });
        return ret;
    });

    Handlebars.registerHelper('main-link-table', function(o) {
        var template = Handlebars.compile($('script#main-link-table').html());
        return template(o.data.root);
    });

    Handlebars.registerHelper('asString', function() {
        console.log(this)
        return this.name
    });

    Handlebars.registerHelper('eachAuthor', function(obj, options) {
        var ret = '';
        var orgs = {}

        // make an org map
        if (!obj.subsections) return '';

        $.each(obj.subsections.filter( function(o) { return o.type.toLowerCase()=='organization';}), function (i,o) {
            orgs[o.accno] = o.attributes.filter(function (p) { return p.name.toLowerCase()=='name'})[0].value;
        });


        var orgNumber = 1;
        var orgToNumberMap = {}
         $.each(obj.subsections.filter( function(o) { return o.type.toLowerCase()=='author';}), function (i,o) {
            var author = {}
            $.each(o.attributes, function (i,v) {
                author[v.name] = v.value;
            });
            if (!orgToNumberMap[author.affiliation]) {
                orgToNumberMap[author.affiliation] = orgNumber++;
                orgOrder.push(author.affiliation);
            }
            author.affiliationNumber = orgToNumberMap[author.affiliation]
            ret += options.fn(author);
        });
        return ret;
    });
    Handlebars.registerHelper('eachOrganization', function(obj, options) {
        var ret = '';
        var orgs = {}

        if (!obj.subsections) return '';

        // make an org map
        $.each(obj.subsections.filter( function(o) { return o.type.toLowerCase()=='organization';}), function (i,o) {
            orgs[o.accno] = o.attributes.filter(function (p) { return p.name.toLowerCase()=='name'})[0].value;
        });

        $.each(orgOrder, function(i,v) {
            ret += options.fn({name:orgs[v],affiliationNumber:i+1, affiliation:v});
        });
        return ret;
    });

    Handlebars.registerHelper('eachOrganization', function(obj, options) {
        var ret = '';
        if (!obj.subsections) return '';

        // make an org map
        $.each(obj.subsections.filter( function(o) { return o.type.toLowerCase()=='organization';}), function (i,o) {
            orgs[o.accno] = o.attributes.filter(function (p) { return p.name.toLowerCase()=='name'})[0].value;
        });

        $.each(orgOrder, function(i,v) {
            ret += options.fn({name:orgs[v],affiliationNumber:i+1, affiliation:v});
        });
        return ret;
    });

    Handlebars.registerHelper('eachFunder', function(obj, options) {
        var ret = '';
        var orgs = {};
        if (!obj.subsections) return '';
        // make an org map
        $.each(obj.subsections.filter( function(subsection) { return subsection.type.toLowerCase()=='funding';}), function (i,o) {
            var org = null, grant = '';
            $(o.attributes).each(function () {
                if (this.name.toLowerCase()=='agency') org = this.value;
                if (this.name.toLowerCase()=='grant_id') grant = this.value;
            });
            if (org) {
                orgs[org] = (orgs[org]) ? orgs[org] + (", "+grant) : orgs[org] = grant;
            }
        });

        $.each(Object.keys(orgs), function (i,v) {
            ret += options.fn({name:v,grants:orgs[v]});
        });
        return ret;
    });

    Handlebars.registerHelper('publication', function(obj, options) {
        var publication = {}
        if (!obj.subsections) return '';
        var pubs = obj.subsections.filter( function(o) { return o.type.toLowerCase()=='publication';});
        if (!pubs || pubs.length <1) return null;
        $.each(pubs[0].attributes, function(i,v) {
            publication[v.name.toLowerCase().replace(' ','_')] = v.value
        });
        publication.accno = pubs[0].accno;
        var type = publication.accno.toLowerCase().substr(0,3);
        publication.URL = linkMap[type];
        var template = Handlebars.compile($('script#publication-template').html());
        return new Handlebars.SafeString(template(publication));
    });


}

function findall(obj,k){
    var ret = [];
    for(var key in obj)
    {
        if (key===k) {
            $.merge(ret,obj[k]);
        } else if(typeof(obj[key]) == "object"){
            $.merge(ret,findall(obj[key],k));
        }
    }
    //unroll file tables
    return $.map( ret, function(n){
        return n;
    });
}

function postRender() {
    $('body').append('<div id="blocker"/><div id="tooltip"/>');
    drawSubsections();
    createDataTables();
    createMainFileTable();
    createMainLinkTables();
    handleSectionArtifacts();
    handleTableExpansion();
    handleOrganisations();

}


function createDataTables() {
    $(".section-table").each(function () {
        $(this).DataTable({
            "dom": "t",
            paging: false
        });
    });
}

function createMainFileTable() {
    $("#file-list").DataTable({
        "lengthMenu": [[5, 10, 25, 50, 100], [5, 10, 25, 50, 100]],
        "columnDefs": [ {"targets": [0], "searchable": false, "orderable": false, "visible": true},
         //   {"targets": [2], "searchable": true, "orderable": false, "visible": false}
            ],
        "dom": "rlftpi",
        "infoCallback": function( settings, start, end, max, total, out ) {
            return (total== max) ? out : out +'<br/><a class="section-button" id="clear-filter" onclick="clearFilter();return false;">' +
                '<span class="fa-stack bs-icon-fa-stack">' +
                '<i class="fa fa-filter fa-stack-1x"></i>' +
                '<i class="fa-stack-1x filter-cross">×</i>' +
                '</span> clear filter</a>';
        }
    });
}

function getURL(type, accession) {
   var url =  String.format(linkMap[type], accession) || '';
   if (type=='ega' && accession.toUpperCase().indexOf('EGAD')==0) {
       url = url.replace('/studies/','/datasets/');
   }
   return url;
}
function createMainLinkTables() {

    //handle type checkboxes
    $(".link-list").each(function () {

        //create external links for known link types
        $("tr",this).each( function (i,row) {
            if (i==0) return;
            var type =  $($('td',row)[1]).text().toLowerCase();
            if (linkMap[type]) {
                $($('td',row)[0]).wrapInner('<a href="'+ getURL( type, $($('td',row)[0]).text()) +'" target="_blank">');
            }
        });

    });

    //format the table!
    $(".link-list").DataTable({
        "lengthMenu": [[5, 10, 25, 50, 100], [5, 10, 25, 50, 100]],
        "dom": "rlftpi"
    });
}


function drawSubsections() {
    // draw subsection and hide them
    $(".indented-section").prepend('<span class="toggle-section fa-fw fa fa-caret-right fa-icon" title="Click to expand"/>')
    $(".indented-section").next().hide();

    $('.toggle-section').parent().css('cursor', 'pointer');
    $('.toggle-section').parent().on('click', function () {
        var indented_section = $(this).parent().children().first().next();
        if (indented_section.css('display') == 'none') {
            $(this).children().first().toggleClass('fa-caret-down').toggleClass('fa-caret-right').attr('title', 'Click to collapse');
            indented_section.show();
            //redrawTables(true);
        } else {
            $(this).children().first().toggleClass('fa-caret-down').toggleClass('fa-caret-right').attr('title', 'Click to expand');
            indented_section.hide();
            //redrawTables(true);
        }
    })
    // limit section title clicks
    $(".section-title-bar").click(function(e) {
        e.stopPropagation();
    })
}

function handleSectionArtifacts() {
    $(".toggle-files, .toggle-links, .toggle-tables").on('click', function () {
        var type = $(this).hasClass("toggle-files") ? "file" : $(this).hasClass("toggle-links") ? "link" : "table";
        var section = $(this).siblings('.bs-section-' + type + 's');
        if (section.css('display') == 'none') {
            section.show();
            //redrawTables(true);
            $(this).html('<i class="fa fa-caret-down"></i> hide ' + type + ($(this).data('total') == '1' ? '' : 's'))
        } else {
            section.hide();
            $(this).html('<i class="fa fa-caret-right"></i> show ' + type + ($(this).data('total') == '1' ? '' : 's'))
        }
    });
    $(".toggle-files, .toggle-links, .toggle-tables").each(function () {
        var type = $(this).hasClass("toggle-files") ? "file" : $(this).hasClass("toggle-links") ? "link" : "table";
        $(this).html('<i class="fa fa-caret-right"></i> show ' + type + ($(this).data('total') == '1' ? '' : 's'));
    });

    //handle file attribute table icons
    $(".attributes-icon").on ('click', function () {
        closeFullScreen();
        var section = '#'+$(this).data('section-id');
        openHREF(section);
        var toggleLink = $(section).next().find('.toggle-tables').first();
        if (toggleLink.first().text().indexOf('show')>=0) toggleLink.click();

    });

    // add link type filters
    $(".link-filter").on('change', function() {
        var filters = $(".link-filter:checked").map(function() { return '^'+this.id+'$'}).get();
        if (filters.length==0) {
            filters = ['^$']
        }
        linksTable[$(this).data('position')-1].column(1).search(filters.join('|'),true, false).draw()
    });

}

function handleTableExpansion() {
    //table expansion
    $('.table-expander').click(function () {
        $('.fullscreen .table-wrapper').css('max-height','');
        $(this).toggleClass('fa-close').toggleClass('fa-expand');
        $(this).attr('title', $(this).hasClass('fa-expand') ? 'Click to expand' : 'Click to close');
        $('html').toggleClass('stop-scrolling');
        $('#blocker').toggleClass('blocker');
        $(this).parent().parent().toggleClass('fullscreen');
        $("table.dataTable tbody td a").css("max-width", $(this).hasClass('fa-expand') ? '200px' : '500px');
        $('.table-wrapper').css('height', 'auto');
        $('.table-wrapper').css('height', 'auto');
        $('.fullscreen .table-wrapper').css('max-height', (parseInt($(window).height()) * 0.80) + 'px').css('top', '45%');
    });
}

function handleOrganisations() {
    $('.org-link').each( function () {
        $(this).attr('href','#'+$(this).data('affiliation'));
    });
    $('.org-link').click(function () {
        var href = $(this).attr('href');
        /*if (!$(href).is(':visible')) {
            $('#hidden-orgs').find('a.show-more').click()
        }*/
        $('html, body').animate({
            scrollTop: $(href).offset().top
        }, 200);

        $(href).animate({opacity: 0.8}, 200, function () {
            $(href).css('background-color', 'yellow');
            $(href).css('color', 'black');
            $(href).animate({opacity: 0.4}, 3000, function () {
                $(href).css('background-color', 'lightgray');
                $(href).animate({opacity: 1}, 600);
                $(href).css('background-color', 'transparent');
                $(href).css('color', '');
            })
        });

    });
}