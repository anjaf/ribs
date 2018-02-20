!function(d) {

    var split_params = document.location.search.replace(/(^\?)/,'')
            .split("&")
            .filter(function (a) {return a!='' })
            .map(function(s) {
                    s = s.split("=")
                    v = decodeURIComponent(s[1]).split('+').join(' ');
                    if (this[s[0]]) {
                        if ($.isArray(this[s[0]])) {
                            this[s[0]].push(v)
                        } else {
                          this[s[0]] = [this[s[0]], v];
                        }
                    } else {
                        this[s[0]] = v;
                    }
                    return this;
                }.bind({}));
    var params = split_params.length ? split_params[0] : {};
    registerHelpers(params);

    showResults(params);
}(document);

function serialiseParams(params) {
    var p = {};
    $.each(params, function (k, v) {
        $.each($.isArray(v) ? v : [v], function (i, s) {
            p[key] = s;
        });
    });
}

function showResults(params) {
    // Prepare template
    var templateSource = $('script#results-template').html();
    var template = Handlebars.compile(templateSource);

    // do search
    $.getJSON(contextPath+(project ? "/api/v1/"+project+"/search" : "/api/v1/search"), params,function (data) {
        if(project) {
            data.project = project;
        }
        var html = template(data);
        $('#renderedContent').html(html);

        postRender(data, params);
    }).done( function () {
        $('#left-column').slideDown("fast", function () {
            // fill facets
            $.getJSON(contextPath + "/api/v1/" + (project||"public") + "/facets", params, function (data) {
                var templateSource = $('script#facet-list-template').html();
                var template = Handlebars.compile(templateSource);
                data.project = (project||"public");
                data.existing = getExistingParams(params, 'facet.');
                var html = template(data);
                $('#facets').html(html);
            }).fail(function (error) {
                showError(error);
            }).done( function (data) {
                postRenderFacets(data, params);
            });
        })
    });
}
function registerHelpers(params) {

    Handlebars.registerHelper('result', function(o) {
        var template = Handlebars.compile($('script#result-template').html());
        o.project = project;
        return template(o);
    });

    Handlebars.registerHelper('formatNumber', function(s) {
        return s.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");;
    });

    Handlebars.registerHelper('pager', function(o) {
        if (!o.data.root.page || !o.data.root.pageSize || !o.data.root.totalHits ) {
            return '';
        }
        var ul = '<ul id="pager" class="pagination" role="navigation" aria-label="Pagination">';
        var page = o.data.root.page, pageSize = o.data.root.pageSize, totalHits =o.data.root.totalHits;
        var maxPage = Math.ceil(totalHits/pageSize*1.0);
        var prms = $.extend({}, params);
        if (page>1) {
            prms.page = o.data.root.page-1;
            ul += '<li class="pagination-previous"><a href="'+contextPath+(project ? '/'+project : '')+'/studies?'+$.param(prms, true)+'" aria-label="Previous page">Previous <span class="show-for-sr">page</span></a></li>';
        }

        if (maxPage<=10) {
            for (var i = 1; i <= maxPage; i++) {
                prms.page = i;
                ul += '<li ' + (i == page ? 'class="current"' : '') + '><a href="'+contextPath+(project ? '/'+project : '')+'/studies?' + $.param(prms, true) + '" aria-label="Page ' + i + '">' + i + '</a></li>';
            }
        } else {
            var arr;
            switch (page) {
                case 1:
                case 2:
                case 3:
                    arr = [1, 2, 3, -1, maxPage - 2, maxPage - 1, maxPage];
                    break;
                case 4:
                    arr = [1, 2, 3, 4, -1, maxPage - 2, maxPage - 1, maxPage];
                    break;
                case 5:
                    arr = [1, 2, 3, 4, 5, 6, -1, maxPage - 2, maxPage - 1, maxPage];
                    break;
                case maxPage - 4:
                    arr = [1, 2, 3, -1 ,  maxPage - 4 , maxPage - 3, maxPage - 2, maxPage - 1, maxPage];
                    break;
                case maxPage - 3:
                    arr = [1, 2, 3, -1 , maxPage - 3, maxPage - 2, maxPage - 1, maxPage];
                    break;
                case maxPage - 2: case maxPage - 1: case maxPage:
                    arr = [1, 2, 3, -1 , maxPage - 2, maxPage - 1, maxPage];
                    break;
                default:
                    arr = [1, 2, 3, -1, page - 1, page, page + 1, -1, maxPage - 2, maxPage - 1, maxPage];
                    break;
            }
            for (var i=0; i<arr.length; i++) {
                if (arr[i]==-1) {
                    ul += '<li class="ellipsis" aria-hidden="true"></li>';
                    continue;
                }
                prms.page = arr[i];
                if (arr[i]==page) {
                    ul += '<li class="current">' + formatNumber(arr[i]) + '</li>';
                } else {
                    ul += '<li><a href="'+contextPath+(project ? '/'+project : '')+'/studies?' + $.param(prms, true) + '" aria-label="Page ' + arr[i] + '">' + formatNumber(arr[i]) + '</a></li>';
                }
            };
        }

        if (o.data.root.page && o.data.root.pageSize &&  o.data.root.page*o.data.root.pageSize < o.data.root.totalHits) {
            prms.page = page+1;
            ul += '<li class="pagination-next"><a href="'+contextPath+(project ? '/'+project : '')+'/studies?'+$.param(prms, true)+'" aria-label="Next page">Next <span class="show-for-sr">page</span></a></li>';
        }
        // ul += '<li class="result-count"> (Showing ' + formatNumber((o.data.root.page-1)*20+1) + ' ‒ '
        //     + formatNumber(o.data.root.page*20 < o.data.root.totalHits ? o.data.root.page*20 : o.data.root.totalHits)
        //     +' of ' + formatNumber(o.data.root.totalHits) + ' results)</li>';
        ul += '</ul>'
        return new Handlebars.SafeString(ul);
    });

    Handlebars.registerHelper('resultcount', function(o) {
        if (!o.data.root.page || !o.data.root.pageSize || !o.data.root.totalHits ) {
            return '';
        }
        var spn = '<span class="result-count">'
             + formatNumber((o.data.root.page-1)*20+1) + ' ‒ '
             + formatNumber(o.data.root.page*20 < o.data.root.totalHits ? o.data.root.page*20 : o.data.root.totalHits)
             +' of ' + formatNumber(o.data.root.totalHits) + ' results</li>';
        return new Handlebars.SafeString(spn);
    });

    Handlebars.registerHelper('asString', function(v) {
        console.log(v)
        console.log(this)
        return this.name
    });

    Handlebars.registerHelper('formatDate', function(v) {
        return (new Date(v.substr(0,4)+'-'+v.substr(4,2)+'-'+v.substr(6,2))).toLocaleDateString("en-gb", { year: 'numeric', month: 'long', day: 'numeric' });
    });

    Handlebars.registerHelper('dynamicLink', function(query) {
        var link = '<a href="studies?query='+query+'" >'+query+'</a>';
        return new Handlebars.SafeString(link);
    });

    Handlebars.registerHelper('ifArray', function (v, options) {
        return $.isArray(v) ? options.fn(this) : options.inverse(this);
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
            case 'contains':
                return (v1.indexOf(v2) >=0 ) ? options.fn(this) : options.inverse(this);
            default:
                return options.inverse(this);
        }
    });

}

function postRender(data, params) {
    // add highlights

    if (data.query) {
        var highlights = [];
        if (data.expandedSynonyms) highlights = highlights.concat(data.expandedSynonyms.map(function (v) { return {word:v,class:'synonym'} } ));
        if (data.expandedEfoTerms) highlights = highlights.concat(data.expandedEfoTerms.map(function (v) { return {word:v,class:'efo'} } ));
        var split = data.query.match(/(?:[^\s"]+|"[^"]*")+/g).map( function(v) { return v.replace(/\"/g,'')});
        highlights = highlights.concat(split.map(function (v) { return {word:v,class:'highlight'} } ));
        highlights.sort(function (a,b) {return b.word.length-a.word.length })
        $.each(highlights, function (i,v) {
            if (v.word!='AND' && v.word!='OR' && v.word!='NOT') {
                $("#search-results").highlight(v.word, {className: v.class, wordsOnly: v.word.indexOf('*') <0});
            }
        });

        $("#renderedContent .highlight").attr('title','This is exact string matched for input query terms');
        $("#renderedContent .efo").attr('title','This is matched child term from Experimental Factor Ontology e.g. brain and subparts of brain');
        $("#renderedContent .synonym").attr('title','This is synonym matched from Experimental Factor Ontology e.g. neoplasia for cancer');


    }

    // get project logo
    $("div[data-type='project']").each( function() {
        var $prj = $(this), accession = $(this).data('accession');
        $('a',$prj).attr('href',contextPath+'/'+accession+'/studies');
        $.getJSON(contextPath+ '/api/v1/studies/'+accession, function (data) {
            var path = data.section.files.path;
            if (!path && data.section.files[0]) path =data.section.files[0].path;
            if (!path && data.section.files[0][0]) path = data.section.files[0][0].path;
            if (path) {
                $prj.prepend('<a class="project-logo" href="' + contextPath + '/' + accession + '/studies">' +
                    '<img src="' + contextPath + '/files/' + accession + '/' + path + '"/>'
                    + '</a>');
            }
        })
    });

    //set sort params
    var projectPath = contextPath + (project ? '/'+project : '');
    $('#sort-by').val(data.sortBy);
    $('#sort-by').change(function (e) {
        params.sortBy = $(this).val();
        params.sortOrder = 'descending';
        window.location = projectPath+'/studies/?' + $.param(params, true);
    });
    if (data.sortOrder=='ascending') {
        $('#sort-desc').removeClass('selected');
        $('#sort-asc').addClass('selected');
    } else {
        $('#sort-desc').addClass('selected');
        $('#sort-asc').removeClass('selected');
    }
    $('#sort-desc').click(function (e) {
        if ($(this).hasClass('selected')) return;
        params.sortOrder = 'descending';
        window.location = projectPath+'/studies/?' + $.param(params, true);
    });
    $('#sort-asc').click(function (e) {
        if ($(this).hasClass('selected')) return;
        params.sortOrder = 'ascending';
        window.location = projectPath+'/studies/?' + $.param(params, true);
    });

    // limit authors
    $('.authors').each( function() {
        var authors = $(this).text().split(',');
        if (authors.length>10) {
            $(this).text(authors.slice(0,9).join(', '));
            var rest = $('<span/>', {class:'hidden'}).text(', '+authors.slice(10).join(','));
            var more = $('<span/>', {class:'more'}).text('+ ' + (authors.length-10) + ' more')
                .click(function () {
                    $(this).next().show();
                    $(this).hide();
                })
            $(this).append(more).append(rest)
        }
    })
}

function postRenderFacets(data, params) {

    // check the currently selected facet, if any
     for (var fkey in params) {
        if (fkey.toLowerCase().indexOf("facet.")!=0) continue;
        $.each( $.isArray(params[fkey]) ? params[fkey] : [params[fkey]] , function () {
            var fval= this, fid = (fkey + ':' + fval);
            if ($('input[id="' + fid + '"]').length) {
                $('input[id="' + fid + '"]').attr('checked', 'checked');
            }
        });
    }

    // put selected facets on top and add facet labels on top of results
    var facetMap={};
    $('li .facet-value:checked').each(function(){
        var fkey = $(this).attr('name');
        if (!facetMap[fkey]) facetMap[fkey] = [];
        facetMap[fkey].push($(this).parent().parent().detach());
    })

    for (var key in facetMap  ) {
        $('ul#facet_'+jqueryEncode(key)).prepend(facetMap[key]);
        $('#facet-filters').append($('<span/>',{class:'facet-filter-label'}).text($('span.facet-title',$('ul#facet_'+jqueryEncode(key)).prev()).text().trim()));
        $.each(facetMap[key], function(i,v) {
            $('#facet-filters').append(
                $('<span/>',{class:'facet-filter-value'})
                    .text( $('label',$(v)).text().trim())
                    .append($('<a/>',{class:'drop-facet', "data-facet-id":$('input',v).attr('id')}).text('✕') )
            );
        })
    }
    $('.drop-facet').bind('click', function(){
       $('#'+jqueryEncode($(this).data('facet-id'))).click();
    });

    // resubmit form when facets are changed
    $('input.facet-value').change(function(){ $(this).parents('form:first').submit() });

    // handle show more
    $('.facet-more').click(function() {
        //if (!project) return;
        $('body').append('<div id="blocker" class="blocker"></div>');
        $('body').append('<div id="facet-loader"><i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><div class="sr-only">Loading...</div></div>');
        var thisFacet = $(this).data('facet');
        $.getJSON(contextPath+"/api/v1/"+(project? project : 'public')+'/facets/'+thisFacet+'/',{}, function(data) {
            if ( !$('#facet-loader').length) return;
            $('#facet-loader').hide();
            var templateSource = $('script#all-facets-template').html();
            var template = Handlebars.compile(templateSource);
            var existing = getExistingParams(params, thisFacet);
            $('body').append(template({facets:data, existing:  existing}));
            $('#facet-search').focus()

            //build lookup cache
            var facetNames = [];
            var facetListItems = $(".allfacets ul li");
            for (var i = 0, len = data.children.length; i < len; i++) {
                facetNames.push(data.children[i].name.toLowerCase());
            }

            // add filter
            searchWaitInterval = null;
            $('#facet-search').change( function(){
                var filter = $('#facet-search').val().toLowerCase();
                for (var i = 0, len = facetNames.length; i < len; i++) {
                   if (facetNames[i].indexOf(filter)>=0)
                       $(facetListItems[i]).show();
                   else
                       $(facetListItems[i]).hide();
                }
            }).keyup(function () {
                var that = this;
                clearInterval(searchWaitInterval);
                searchWaitInterval = setTimeout(function(){
                    $(that).change();
                },200);
            });

            //hook events
            $(".allfacets ul li input").change(function() {
                toggleFacetSearch();
            });

            $('#close-facet-search').click( function () {
                closeFullScreen();
            });

            // check the currently selected face, if any
            for (var v in params){
                if (v.indexOf(thisFacet)!=0) return;
                $.each($.isArray(params[v]) ? params[v] : [params[v]], function (i,s) {
                    $('input[id="all-'+v+':'+s+'"]', $(".allfacets ul li")).attr('checked','checked');
                });

            };

            //handle select all
            $('#all-check').click(function () {
                if ($("#all-check:checked").length) {
                    $(".allfacets ul li input").attr('checked','checked')
                } else {
                    $(".allfacets ul li input").removeAttr('checked')
                }
            });

            toggleFacetSearch();

        });
    });

    //handle escape key on fullscreen
    $(document).on('keydown',function ( e ) {
        if ( e.keyCode === 27 ) {
            closeFullScreen();
        }
    });

    //handle facet toggles
    $('.facet-name').bind('click', function(){
        var facetUL = $(this).next();
        if (facetUL.is(':visible'))  {
            facetUL.slideUp(100);
        } else {
            facetUL.slideDown(100);
        }
        $('.toggle-facet', this).find('[data-fa-i2svg]').toggleClass('fa-angle-right fa-angle-down');
    });
}

function getExistingParams(params, filter) {
    var existing = [];
    $.each(params, function (k,v) {
        if (k.indexOf(filter)==0) return;
        $.each($.isArray(v) ? v : [v], function (i,s) {
            existing.push({key: k, value: s});
        });
    });
    return existing;
}

function toggleFacetSearch() {
    if ($(".allfacets ul li input:checked").length>100) {
        $("#facet-search-button").attr("disabled", "disabled");
    } else {
        $("#facet-search-button").removeAttr("disabled");
    }
}

function closeFullScreen() {
    $('#blocker').remove();
    $('#facet-loader').remove();
    $('.allfacets').remove();

}

function jqueryEncode(v) {
    return v.replace( /(:|\.|\[|\]|,|=)/g, "\\$1" );
}