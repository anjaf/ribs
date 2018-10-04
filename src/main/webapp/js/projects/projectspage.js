var ProjectsPage = (function (_self) {

    _self.render = function() {
        var params = getParams();
        this.registerHelpers(params);

        // Prepare template
        var templateSource = $('script#results-template').html();
        var template = Handlebars.compile(templateSource);

        // do search
        $.getJSON(contextPath+"/api/v1/search?type=project", params,function (data) {
            var html = template(data);
            $('#renderedContent').html(html);
            postRender(data, params);
        }).done( function () {
            $('#left-column').slideDown("fast")
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

    return _self;
})(ProjectsPage || {});