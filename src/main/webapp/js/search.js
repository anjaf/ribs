var project;
!function(d) {

    var params = document.location.search.replace(/(^\?)/,'').split("&").map(
            function(s) {
                return s = s.split("="), this[s[0]] = s[1], this
            }.bind({}))[0];
    registerHelpers(params);


    var parts = $.grep($(location).attr('pathname').replace(contextPath+'/','').split('/'),function(a) {return a!=''});
    project = parts.length>1 ? parts[0] : undefined;
    if(project) {
        params.query = (params.query ? params.query : '')+' %2Bproject:'+project;
        $.getJSON(contextPath+"/api/studies/"+project,function (data) {
            showProjectBanner(data);
            showResults(params);
        }).fail(function(error) {
            showError(error);
        });
    } else {
        showResults(params);
    }


}(document);

function showProjectBanner(data) {
    var templateSource = $('script#project-banner-template').html();
    var template = Handlebars.compile(templateSource);
    var project={logo:contextPath+'/files/'+data.accno+'/'+data.section.files[0][0].path};
    $(data.section.attributes).each(function () {
        project[this.name.toLowerCase()] = this.value
    })
    var html = template(project);
    $('#project-banner').html(html);
}

function showResults(params) {
    // Prepare template
    var templateSource = $('script#results-template').html();
    var template = Handlebars.compile(templateSource);

    // Data in json
    $.getJSON(contextPath+"/api/search", params,function (data) {
        // Generate html using template and data
        if(project) {
            data.project = project;
        }
        var html = template(data);

        // Add the result to the DOM
        $('#renderedContent').html(html);
        postRender(data)

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
        var ul = '<ul class="pagination" role="navigation" aria-label="Pagination">';
        var page = o.data.root.page, pageSize = o.data.root.pageSize, totalHits =o.data.root.totalHits;
        var maxPage = Math.ceil(totalHits/pageSize*1.0);

        if (page>1) {
            params.page = o.data.root.page-1;
            ul += '<li class="pagination-previous"><a href="'+contextPath+'/studies?'+$.param(params)+'" aria-label="Previous page">Previous <span class="show-for-sr">page</span></a></li>';
        }

        if (maxPage<=10) {
            for (var i = 1; i <= maxPage; i++) {
                params.page = i;
                ul += '<li ' + (i == page ? 'class="current"' : '') + '><a href="'+contextPath+'/studies?' + $.param(params) + '" aria-label="Page ' + i + '">' + i + '</a></li>';
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
                params.page = arr[i];
                if (arr[i]==page) {
                    ul += '<li class="current">' + arr[i] + '</li>';
                } else {
                    ul += '<li><a href="'+contextPath+'/studies?' + $.param(params) + '" aria-label="Page ' + arr[i] + '">' + arr[i] + '</a></li>';
                }
            };
        }

        if (o.data.root.page && o.data.root.pageSize &&  o.data.root.page*o.data.root.pageSize < o.data.root.totalHits) {
            params.page = page+1;
            ul += '<li class="pagination-next"><a href="'+contextPath+'/studies?'+$.param(params)+'" aria-label="Next page">Next <span class="show-for-sr">page</span></a></li>';
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
        return (new Date(v)).toLocaleDateString("en-gb", { year: 'numeric', month: 'long', day: 'numeric' });
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

function postRender(data) {
    $('#left-column').slideDown();
    // add highlights
    if (data.query) $("#search-results").highlight(data.query.split(' '));
    // $("#renderedContent").highlight(['ductal','CrkII '],{className:'synonym'});
    // $("#renderedContent").highlight(['mouse','gland '],{className:'efo'});

    // get project logo
    $("div[data-type='project']").each( function() {
        var $prj = $(this), accession = $(this).data('accession');
        $('a',$prj).attr('href',contextPath+'/'+accession+'/studies');
        $.getJSON(contextPath+ '/api/studies/'+accession, function (data) {
            $prj.prepend('<a class="project-logo" href="'+contextPath+'/'+accession+'/studies">'+
                '<img src="'+contextPath+'/files/'+accession+'/'+data.section.files[0][0].path+'"/>'
            +'</a>');
        })
    });

}

