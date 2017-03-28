String.format = function() {
    var s = arguments[0];
    for (var i = 0; i < arguments.length - 1; i++) {
        var reg = new RegExp("\\{" + i + "\\}", "gm");
        s = s.replace(reg, arguments[i + 1]);
    }

    return s;
}

$.fn.groupBy = function(fn) {
    var arr = $(this),grouped = {};
    $.each(arr, function (i, o) {
        key = fn(o);
        if (typeof(grouped[key]) === "undefined") {
            grouped[key] = [];
        }
        grouped[key].push(o);
    });

    return grouped;
}
!function(d) {

    linkMap = {'pmc':'http://europepmc.org/articles/{0}',
        'pmid':'http://europepmc.org/abstract/MED/{0}',
        'doi':'http://dx.doi.org/{0}',
        'chembl':'https://www.ebi.ac.uk/chembldb/compound/inspect/{0}',
        'ega':'http://www.ebi.ac.uk/ega/studies/{0}',
        'sprot':'http://www.uniprot.org/uniprot/{0}',
        'gen':'http://www.ebi.ac.uk/ena/data/view/{0}',
        'arrayexpress files':'http://www.ebi.ac.uk/arrayexpress/experiments/{0}/files/',
        'arrayexpress':'http://www.ebi.ac.uk/arrayexpress/experiments/{0}',
        'refsnp':'http://www.ncbi.nlm.nih.gov/SNP/snp_ref.cgi?rs={0}',
        'pdb':'http://www.ebi.ac.uk/pdbe-srv/view/entry/{0}/summary',
        'pfam':'http://pfam.xfam.org/family/{0}',
        'omim':'http://omim.org/entry/{0}',
        'interpro':'http://www.ebi.ac.uk/interpro/entry/{0}',
        'refseq':'http://www.ncbi.nlm.nih.gov/nuccore/{0}',
        'geo':'http://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc={0}',
        'doi':'http://dx.doi.org/{0}',
        'intact':'http://www.ebi.ac.uk/intact/pages/details/details.xhtml?experimentAc={0}',
        'biostudies':'https://www.ebi.ac.uk/biostudies/studies/{0}',
        'biostudies search':'https://www.ebi.ac.uk/biostudies/studies/search.html?query={0}',
        'go':'http://amigo.geneontology.org/amigo/term/{0}',
        'chebi':'http://www.ebi.ac.uk/chebi/searchId.do?chebiId={0}'
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


    $.getJSON(url,params, function (data) {
        // set accession
        $('#accession').text(data.accno);
        data.section.accno = data.accno;
        var rootPath = data.attributes.filter( function (v,i) { return    v.name=='RootPath';   });
        data.section.root = rootPath.length ? rootPath[0].value : '';
        var html = template(data.section);
        d.getElementById('renderedContent').innerHTML = html;
        postRender();

    }).fail(function(error) {
        showError(error);
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
        if (e==undefined) return '' ;
        return new Handlebars.SafeString( e.url ? '<a href="'+e.url+ (e.url[0]!='#' ? '" target="_blank':'')+'">'+e.value+'</a>' : e.value);
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

    Handlebars.registerHelper('replaceCharacter', function(val, a, b ) {
        return val.replace(a,b);
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
        if (!arr) return;
        var mod = arr.reduce(function(r, i) {
            r[i[key]] = r[i[key]] || [];
            r[i[key]].push(i[val]);
            return r;
        }, {})
        var ret = '';
        for(var k in mod) {
            ret = ret + options.fn({name:k,value:mod[k].join(', ')});
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

    Handlebars.registerHelper('setFileTableHeaders', function(o) {
        if (this && this.length) {
            var names = ['Name', 'Size'];
            var hsh = {'Name': 1, 'Size': 1};
            $.each(this, function (i, file) {
                file.attributes = file.attributes || [];
                file.attributes.push({"name": "Name", "value": file.path});
                $.each(file.attributes, function (i, attribute) {
                    if (!(attribute.name in hsh)) {
                        names.push(attribute.name);
                        hsh[attribute.name] = 1;
                    }
                    // make file link
                    if(attribute.name=='Name') {
                        attribute.url= contextPath+'/files/'+$('#accession').text()+'/'+ file.path
                        console.log(o)
                    }
                })
            });
            this.headers = names;
            this.type = this[0].type
        }
    });

    Handlebars.registerHelper('main-file-table', function() {
        var o = findall(this,'files');
        var template = Handlebars.compile($('script#main-file-table').html());
        return template(o);
    });

    Handlebars.registerHelper('section-link-tables', function(o) {
        var template = Handlebars.compile($('script#section-link-tables').html());
        return template(o.data.root);
    });

    Handlebars.registerHelper('link-table', function(o) {
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
        var groupsByColumns = $(links).groupBy(function (obj) {
            var att_fp = $.unique($.map(obj.attributes, function (attr) { return attr.name}).sort()).join('|')
            return att_fp
        });
        var keys = Object.keys(groupsByColumns), data = Handlebars.createFrame(options.data);
        $.each(keys, function(i,key) {
            data.first = i==0, data.last = i==(keys.length-1), data.index = i, data.indexPlusOne = i+1;
            ret = ret + options.fn({links: groupsByColumns[key]},{data:data});
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
            orgs[o.accno] = o.attributes ? o.attributes.filter(function (p) { return p.name.toLowerCase()=='name'})[0].value : '';
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
        var orgs = {};

        if (!obj.subsections) return '';

        // make an org map
        $.each(obj.subsections.filter( function(o) { return o.type.toLowerCase()=='organization';}), function (i,o) {
            orgs[o.accno] = o.attributes ? o.attributes.filter(function (p) { return p.name.toLowerCase()=='name'})[0].value : '';
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
        var keys = Object.keys(orgs), data = Handlebars.createFrame(options.data);
        $.each(keys, function (i,v) {
            data.first = i==0, data.last = i==(keys.length-1), data.index = i;
            ret += options.fn({name:v,grants:orgs[v]},{data:data});
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
        publication.URL = getURL(type, publication.accno);
        var template = Handlebars.compile($('script#publication-template').html());
        return new Handlebars.SafeString(template(publication));
    });


    Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) {

        switch (operator) {
            case '==':
                return (v1 == v2) ? options.fn(this) : options.inverse(this);
            case '===':
                return (v1 === v2) ? options.fn(this) : options.inverse(this);
            case '!=':
                return (v1 != v2) ? options.fn(this) : options.inverse(this);
            case '!==':
                return (v1 !== v2) ? options.fn(this) : options.inverse(this);
            case '<':
                return (v1 < v2) ? options.fn(this) : options.inverse(this);
            case '<=':
                return (v1 <= v2) ? options.fn(this) : options.inverse(this);
            case '>':
                return (v1 > v2) ? options.fn(this) : options.inverse(this);
            case '>=':
                return (v1 >= v2) ? options.fn(this) : options.inverse(this);
            case '&&':
                return (v1 && v2) ? options.fn(this) : options.inverse(this);
            case '||':
                return (v1 || v2) ? options.fn(this) : options.inverse(this);
            default:
                return options.inverse(this);
        }
    });


}

function findall(obj,k,unroll){ // works only for files and links
    if (unroll==undefined) unroll =true
    var ret = [];
    for(var key in obj)
    {
        if (key===k) {
            if (!obj.root) {
                var accno = obj.accno, type = obj['type'];

                $.each(obj[k], function () {
                    $.each($.isArray(this) ? this : [this], function () {
                        if (accno && this.attributes) {
                            this.attributes.splice(0, 0, {'name': 'Section', 'value': type, 'url':'#'+accno.replace('/','-')});
                        }
                    });
                });
            }
            $.merge(ret,obj[k]);
        } else if(typeof(obj[key]) == "object"){
            $.merge(ret,findall(obj[key],k,unroll));
        }
    }
    //unroll file tables
    if (unroll) {
        return $.map( ret, function(n){
            return n;
        });
    }
    return  ret;
}

function postRender() {
    $('body').append('<div id="blocker"/><div id="tooltip"/>');
    drawSubsections();
    createDataTables();
    createMainFileTable();
    createLinkTables();
    handleSectionArtifacts();
    handleTableExpansion();
    handleOrganisations();
    formatPageHtml();
    $('#left-column').slideDown();

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
   var url =  linkMap[type] ? String.format(linkMap[type], accession) : null;
   if (type=='ega' && accession.toUpperCase().indexOf('EGAD')==0) {
       url = url.replace('/studies/','/datasets/');
   }
   if (accession.indexOf('http:')==0 || accession.indexOf('https:')==0  || accession.indexOf('ftp:')==0 ) {
       url = accession;
   }

   return url;
}
function createLinkTables() {

    //handle links
    $(".link-list").each(function () {
        //create external links for known link types
        $("tr",this).each( function (i,row) {
            if (i==0) return;
            var type =  $($('td',row)[1]).text().toLowerCase();
            var url = getURL( type, $($('td',row)[0]).text());
            if (url) {
                $($('td',row)[0]).wrapInner('<a href="'+ url +'" target="_blank">');
            }
        });

    });

    //format the right column tables
    $("#right-column .link-list").DataTable({
        "lengthMenu": [[5, 10, 25, 50, 100], [5, 10, 25, 50, 100]],
        "dom": "rlftpi"
    });

    //format section tables
    $("#bs-content .link-list").DataTable({
        "dom": "t",
        paging: false
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
        var type = $(this).hasClass("toggle-files") ? "file(s)" : $(this).hasClass("toggle-links") ? "link(s)" : "table";
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

function formatPageHtml() {
    //replace all newlines with html tags
    $('#ae-detail > .value').each(function () {
        var html = $(this).html();
        if (html.indexOf('<') < 0) { // replace only if no tags are inside
            $(this).html($(this).html().replace(/\n/g, '<br/>'))
        }
    });


    //handle escape key on fullscreen
    $(document).on('keydown',function ( e ) {
        if ( e.keyCode === 27 ) {
            $('.table-expander','.fullscreen').click();
            $('#right-column-expander','.fullscreen').click();
        }
    });

    // add highlights
    // $("#renderedContent").highlight(['mice','crkl']);
    // $("#renderedContent").highlight(['ductal','CrkII '],{className:'synonym'});
    // $("#renderedContent").highlight(['mouse','gland '],{className:'efo'});
    //
    //
    // $("#renderedContent .highlight").attr('title','This is exact string matched for input query terms');
    // $("#renderedContent .efo").attr('title','This is matched child term from Experimental Factor Ontology e.g. brain and subparts of brain');
    // $("#renderedContent .synonym").attr('title','This is synonym matched from Experimental Factor Ontology e.g. neoplasia for cancer');



}


function showError(error) {
    var errorTemplateSource = $('script#error-template').html();
    var errorTemplate = Handlebars.compile(errorTemplateSource);
    var data;
    switch (error.status) {
        case 400:
            data = {
                title: 'We’re sorry that we cannot process your request',
                message: 'There was a query syntax error in <span class="alert"><xsl:value-of select="$error-message"/></span>. Please try a different query or check our <a href="{$context-path}/help/index.html">query syntax help</a>.'
            }
            break;

        case 403:
            data = {
                title: 'We’re sorry that you don’t have access to this page or file',
                message: 'Please <a href="#" class="login">log in</a> to access <span class="alert"><xsl:value-of select="$error-request-uri"/></span>.'
            }
            break;

        case 404:
            data = {
                title: 'We’re sorry that the page or file you’ve requested is not publicly available',
                message: 'The resource may have been removed, had its name changed, or has restricted access.If you have been granted access, please <a href="#" class="login">log in</a> to proceed.'
            }
            break;

        default:
            data = {
                title: 'Oops! Something has gone wrong with BioStudies',
                message: 'The service you are trying to access is currently unavailable. We’re very sorry. Please try again later or use the feedback link to report if the problem persists.'
            }
            break;
    }

    var html = errorTemplate(data);
    $('#renderedContent').html(html);
    $('#accession').text("Error");
}

function openHREF(href) {
    var section = $(href);
    var o = section;
    while (o.prop("tagName")!=='BODY') {
        var p =  o.parent().parent();
        if(o.parent().css('display')!='block') {
            p.prev().click();
        }
        o = p;
    }
    if(section.next().children().first().css('display')=='none') {
        section.click();
    }

    $('html, body').animate({
        scrollTop: $(section).offset().top -10
    }, 200);
}


function handleAnchors() {
    // handle clicks on section links in main file table
    $("a[href^='#']", "#file-list" ).filter(function(){ return $(this).attr('href').length>1 }).click( function(){
        var subsec = $(this).attr('href');
        closeFullScreen();
        openHREF(subsec);
    });

    // handle clicks on file filters in section
    $("a[data-files-id]").click( function() {
        $('#right-column-expander').click();
        filesTable.column(2).search('^'+$(this).data('files-id')+'$',true,false).draw();
    });

    // scroll to main anchor
    if (location.hash) {
        openHREF(location.hash);
    }

    // add file search filter
    if (params['fs']) {
        $('#right-column-expander').click();
        filesTable.search(params['fs']).draw();
    }



}
