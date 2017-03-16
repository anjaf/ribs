!function(d) {

    var params = document.location.search.replace(/(^\?)/,'').split("&").map(
            function(s) {
                return s = s.split("="), this[s[0]] = s[1], this
            }.bind({}))[0];
    registerHelpers(params);

    // Prepare template
    var templateSource = $('script#results-template').html();
    var template = Handlebars.compile(templateSource);

    // Data in json
    $.getJSON(contextPath+"/api/search", params,function (data) {
        // Generate html using template and data
        var html = template(data);

        // Add the result to the DOM
        d.getElementById('renderedContent').innerHTML = html;
        //postRender();

    });
}(document);

function registerHelpers(params) {

    Handlebars.registerHelper('result', function(o) {
        var template = Handlebars.compile($('script#result-template').html());
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
            ul += '<li class="pagination-previous"><a href="search?'+$.param(params)+'" aria-label="Previous page">Previous <span class="show-for-sr">page</span></a></li>';
        }

        if (maxPage<=10) {
            for (var i = 1; i <= maxPage; i++) {
                params.page = i;
                ul += '<li ' + (i == page ? 'class="current"' : '') + '><a href="search?' + $.param(params) + '" aria-label="Page ' + i + '">' + i + '</a></li>';
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
                    ul += '<li><a href="search?' + $.param(params) + '" aria-label="Page ' + arr[i] + '">' + arr[i] + '</a></li>';
                }
            };
        }

        if (o.data.root.page && o.data.root.pageSize &&  o.data.root.page*o.data.root.pageSize < o.data.root.totalHits) {
            params.page = page+1;
            ul += '<li class="pagination-next"><a href="search?'+$.param(params)+'" aria-label="Next page">Next <span class="show-for-sr">page</span></a></li>';
        }

        /*<li class="current"><span class="show-for-sr">You're on page</span> 1</li>
        <li><a href="#" aria-label="Page 2">2</a></li>
            <li><a href="#" aria-label="Page 3">3</a></li>
            <li><a href="#" aria-label="Page 4">4</a></li>
            <li class="ellipsis" aria-hidden="true"></li>
            <li><a href="#" aria-label="Page 12">12</a></li>
            <li><a href="#" aria-label="Page 13">13</a></li>
            <li class="pagination-next"><a href="#" aria-label="Next page">Next <span class="show-for-sr">page</span></a></li>
            */
        ul += '</ul>'
        return new Handlebars.SafeString(ul);
    });

    Handlebars.registerHelper('asString', function(v) {
        console.log(v)
        console.log(this)
        return this.name
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

function postRender() {

}

