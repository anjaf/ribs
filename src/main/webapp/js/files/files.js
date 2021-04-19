$(function() {
    registerHelpers();
    var url = window.location.pathname.replace('/files','');
    var accession = url.substr(url.lastIndexOf('/')+1);
    $('#accession').html('<a href="'+contextPath + (collection? '/'+collection:'')+'/studies/'+accession+'">'+accession+'</a>')
        .parent().parent().append('<li>Files</li>');
    updateTitleFromBreadCrumbs();
    $('#main-content-area').removeClass("row");
    FileTable.render(accession, getParams(), false);

    function registerHelpers() {
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
                    return $.inArray(v2, v1)>=0 ? options.fn(this) : options.inverse(this);
                case 'notin':
                    return $.inArray(v1, eval(v2))<0 ? options.fn(this) : options.inverse(this);
                case 'haslength':
                    return v1.length == v2 ? options.fn(this) : options.inverse(this);
                default:
                    return options.inverse(this);
            }
        });
    }
});

