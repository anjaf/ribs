Home.ProjectLoader = (function () {

    var _self ={};


    _self.render = function () {
        $.getJSON( contextPath + "/api/v1/search",{type:'project'}, function( data ) {
            if (data && data.totalHits && data.totalHits>0) {
                data.hits = data.hits.sort(function(a, b) {
                    return a.title.toLowerCase() > b.title.toLowerCase() ? 1 : -1
                });
                var maxProject = 5;
                if (data.hits.length>maxProject) data.hits = data.hits.slice(0,maxProject);

                var template = Handlebars.compile($('script#projects-template').html());
                $('#projects').html(template(data));
                $('#ProjectLoader').slideDown();
                $("a[data-type='project']").each( function() {
                    var $prj = $(this), accession = $(this).data('accession');
                    $(this).attr('href',contextPath+'/'+accession+'/studies');
                    $.getJSON(contextPath+ '/api/v1/studies/'+accession, function (data) {
                        var path = data.section.files.path;
                        if (!path && data.section.files[0]) path =data.section.files[0].path;
                        if (!path && data.section.files[0][0]) path = data.section.files[0][0].path;
                        if (path) {
                            $prj.prepend('<img src="' + contextPath + '/files/' + accession + '/' + path + '"/>');
                        }
                    })});
            }
        });

    };

    return _self;
})();
