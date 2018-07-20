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
    $.getJSON(contextPath+"/api/v1/search?query=type:project", params,function (data) {
        var html = template(data);
        $('#renderedContent').html(html);
        postRender(data, params);
    }).done( function () {
        $('#left-column').slideDown("fast")
    });
}
function registerHelpers(params) {

    Handlebars.registerHelper('result', function(o) {
        var template = Handlebars.compile($('script#project-result-template').html());
        o.project = project;
        return template(o);
    });

    Handlebars.registerHelper('formatNumber', function(s) {
        return s.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");;
    });

    Handlebars.registerHelper('formatDate', function(v) {
        var date = (new Date(v.substr(0,4)+'-'+v.substr(4,2)+'-'+v.substr(6,2))).toLocaleDateString("en-gb", { year: 'numeric', month: 'long', day: 'numeric' });
        return date == 'Invalid Date' ? (new Date()).getFullYear() : date;
    });

}

function postRender(data, params) {
    // get project logo
    $("div[data-type='project']").each( function() {
        var $prj = $(this), accession = $(this).data('accession');
        $('a',$prj).attr('href',contextPath+'/'+accession+'/studies');
        $.getJSON(contextPath+ '/api/v1/studies/'+accession, function (data) {
            var path = data.section.files.path;
            if (!path && data.section.files[0]) path =data.section.files[0].path;
            if (!path && data.section.files[0][0]) path = data.section.files[0][0].path;
            if (path) {
                $prj.prepend('<div><a class="project-logo" href="' + contextPath + '/' + accession + '/studies">' +
                    '<img src="' + contextPath + '/files/' + accession + '/' + path + '"/>'
                    + '</a></div>');
            }
        })
    });

}
