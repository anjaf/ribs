!function(d) {

    var split_params = document.location.search.replace(/(^\?)/,'')
            .split("&")
            .filter(function (a) {return a!='' })
            .map(function(s) {
                    s = s.split("=")
                    v = decodeURIComponent(s[1]).split('+').join(' ');
                    this[s[0]] =  this[s[0]] ? this[s[0]]+','+v:v;
                    return this;
                }.bind({}));
    var params = split_params.length ? split_params[0] : {};
    registerHelpers(params);

    showResults(params);
}(document);


function showResults(params) {
    // Prepare template
    var templateSource = $('script#results-template').html();
    var template = Handlebars.compile(templateSource);

    // do search
    $.getJSON(contextPath+(project ? "/api/"+project+"/search" : "/api/search"), params,function (data) {
        if(project) {
            data.project = project;
        }
        var html = template(data);
        $('#renderedContent').html(html);

        postRender(data, params);
    }).done( function () {
        $('#left-column').slideDown("fast", function () {
            // fill facets
            if ($('#hasFacets').length) {
                $.getJSON(contextPath + "/api/" + project + "/facets", function (data) {
                    var templateSource = $('script#facet-list-template').html();
                    var template = Handlebars.compile(templateSource);
                    data.selectedFacets = params.facets ? params.facets.split(",") : [];
                    var html = template(data);
                    $('#facets').html(html);
                    postRenderFacets(data,params);
                }).fail(function (error) {
                    showError(error);
                }).done( function (data) {
                    postRenderFacets(data, params);
                });
            }
        })
    });
}
function registerHelpers(params) {

    Handlebars.registerHelper('result', function(o) {
        var template = Handlebars.compile($('script#result-template').html());
        o.project = project;
        return template(o);
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
            ul += '<li class="pagination-previous"><a href="'+contextPath+'/studies?'+$.param(prms)+'" aria-label="Previous page">Previous <span class="show-for-sr">page</span></a></li>';
        }

        if (maxPage<=10) {
            for (var i = 1; i <= maxPage; i++) {
                prms.page = i;
                ul += '<li ' + (i == page ? 'class="current"' : '') + '><a href="'+contextPath+'/studies?' + $.param(prms) + '" aria-label="Page ' + i + '">' + i + '</a></li>';
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
                    ul += '<li class="current">' + arr[i] + '</li>';
                } else {
                    ul += '<li><a href="'+contextPath+'/studies?' + $.param(prms) + '" aria-label="Page ' + arr[i] + '">' + arr[i] + '</a></li>';
                }
            };
        }

        if (o.data.root.page && o.data.root.pageSize &&  o.data.root.page*o.data.root.pageSize < o.data.root.totalHits) {
            prms.page = page+1;
            ul += '<li class="pagination-next"><a href="'+contextPath+'/studies?'+$.param(prms)+'" aria-label="Next page">Next <span class="show-for-sr">page</span></a></li>';
        }

        ul += '</ul>'
        return new Handlebars.SafeString(ul);
    });

    Handlebars.registerHelper('asString', function(v) {
        console.log(v)
        console.log(this)
        return this.name
    });

    Handlebars.registerHelper('epochToDate', function(v) {
        return (new Date(v*1000)).toLocaleDateString("en-gb", { year: 'numeric', month: 'long', day: 'numeric' });
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
            $("#search-results").highlight(v.word,{className:v.class});
        });
    }

    // get project logo
    $("div[data-type='project']").each( function() {
        var $prj = $(this), accession = $(this).data('accession');
        $('a',$prj).attr('href',contextPath+'/'+accession+'/studies');
        $.getJSON(contextPath+ '/api/studies/'+accession, function (data) {
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
        window.location = projectPath+'/studies/?' + $.param(params);
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
        window.location = projectPath+'/studies/?' + $.param(params);
    });
    $('#sort-asc').click(function (e) {
        if ($(this).hasClass('selected')) return;
        params.sortOrder = 'ascending';
        window.location = projectPath+'/studies/?' + $.param(params);
    });
}

function postRenderFacets(data, params) {
    // check the currently selected face
    if (params.facets) {
        $(params.facets.split(",")).each(function () {
            $('input[id="'+this+'"]').attr('checked','checked');
        })
    }

}

