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
    $.getJSON("api/search", params,function (data) {
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
        var ul = '<ul class="pagination" role="navigation" aria-label="Pagination">'
        console.log($.param(params))
        if (!o.data.root.page || o.data.root.page==1  ) {
            ul += '<li class="pagination-previous disabled">Previous <span class="show-for-sr">page</span></li>';
        } else {
            params.page = o.data.root.page-1;
            ul += '<li class="pagination-previous"><a href="search?'+$.param(params)+'" aria-label="Previous page">Previous <span class="show-for-sr">page</span></a></li>';
        }

        if (!o.data.root.page || !o.data.root.pageSize ||  o.data.root.page*o.data.root.pageSize > o.data.root.totalHits) {
            ul += '<li class="pagination-next disabled">Next <span class="show-for-sr">page</span></li>';
        } else {
            params.page = o.data.root.page+1;
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

