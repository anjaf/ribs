var Searcher = (function (_self) {

    var projectScripts = ['arrayexpress'];
    var responseData;

    _self.render = function () {
        var params = getParams();
        this.registerHelpers(params);
        // Prepare template
        var templateSource = $('script#results-template').html();
        var template = Handlebars.compile(templateSource);

        // do search
        $.getJSON(contextPath+(project ? "/api/v1/"+project+"/search" : "/api/v1/search"), params,function (data) {
            if (params.first && data.hits) {
                location.href= contextPath+'/studies/' +data.hits[0].accession;
                return;
            }
            if(project) {
                data.project = project;
            }
            responseData = data;
            var html = template(data);
            $('#renderedContent').html(html);

            postRender(data, params);
        }).done( function () {
            FacetRenderer.render(params);
        });
    };

    _self.getResponseData = function() { return responseData; };

    _self.setSortParameters = function (data, params) {
        var projectPath = contextPath + (project ? '/' + project : '');
        $('#sort-by').val(data.sortBy);
        $('#sort-by').change(function (e) {
            params.sortBy = $(this).val();
            params.sortOrder = 'descending';
            window.location = projectPath + '/studies/?' + $.param(params, true);
        });
        if (data.sortOrder == 'ascending') {
            $('#sort-desc').removeClass('selected');
            $('#sort-asc').addClass('selected');
        } else {
            $('#sort-desc').addClass('selected');
            $('#sort-asc').removeClass('selected');
        }
        $('#sort-desc').click(function (e) {
            if ($(this).hasClass('selected')) return;
            params.sortOrder = 'descending';
            window.location = projectPath + '/studies/?' + $.param(params, true);
        });
        $('#sort-asc').click(function (e) {
            if ($(this).hasClass('selected')) return;
            params.sortOrder = 'ascending';
            window.location = projectPath + '/studies/?' + $.param(params, true);
        });
    };


    function postRender(data, params) {
        addHighlights('#search-results',data);
        getProjectLogo();
        Searcher.setSortParameters(data, params);
        limitAuthors();
        handleProjectBasedScriptInjection(data);
    }

    function handleProjectBasedScriptInjection(data) {
        if ($.inArray(data.project && data.project.toLowerCase(), projectScripts)==-1 ) return;
        var scriptURL = window.contextPath + '/js/project/search/' + project.toLowerCase() + '.js';
        $.getScript(scriptURL);
    }

    function limitAuthors() {
        $('.authors').each(function () {
            var authors = $(this).text().split(',');
            if (authors.length > 10) {
                $(this).text(authors.slice(0, 9).join(', '));
                var rest = $('<span/>', {class: 'hidden'}).text(', ' + authors.slice(10).join(','));
                var more = $('<span/>', {class: 'more'}).text('+ ' + (authors.length - 10) + ' more')
                    .click(function () {
                        $(this).next().show();
                        $(this).hide();
                    })
                $(this).append(more).append(rest)
            }
        })
    }

    function getProjectLogo() {
        $("div[data-type='project']").each(function () {
            var $prj = $(this), accession = $(this).data('accession');
            $('a', $prj).attr('href', contextPath + '/' + accession + '/studies');
            $.getJSON(contextPath + '/api/v1/studies/' + accession, function (data) {
                var path = data.section.files.path;
                if (!path && data.section.files[0]) path = data.section.files[0].path;
                if (!path && data.section.files[0][0]) path = data.section.files[0][0].path;
                if (path) {
                    $prj.prepend('<a class="project-logo" href="' + contextPath + '/' + accession + '/studies">' +
                        '<img src="' + contextPath + '/files/' + accession + '/' + path + '"/>'
                        + '</a>');
                }
            })
        });
    }

    return _self;
})(Searcher || {});