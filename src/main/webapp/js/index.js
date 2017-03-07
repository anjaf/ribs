!function(d) {

    registerHelpers();

    // Prepare template
    var templateSource = $('script#results-template').html();
    var template = Handlebars.compile(templateSource);


    // Data in json
    $.getJSON("api/search", function (data) {
        // Generate html using template and data
        var html = template(data);

        // Add the result to the DOM
        d.getElementById('renderedContent').innerHTML = html;

        //postRender();

    });
}(document);

function registerHelpers() {

    Handlebars.registerHelper('result', function(o) {
        var template = Handlebars.compile($('script#result-template').html());
        return template(o);
    });

    Handlebars.registerHelper('asString', function(v) {
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

