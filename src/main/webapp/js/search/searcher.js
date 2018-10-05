var Searcher = (function (_self) {

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
            var html = template(data);
            $('#renderedContent').html(html);

            postRender(data, params);
        }).done( function () {
            FacetRenderer.render(params);
        });
    };

    function postRender(data, params) {
        addHighlights(data);
        getProjectLogo();
        setSortParameters(data, params);
        limitAuthors();
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

    function setSortParameters(data, params) {
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
    }

    function addHighlights(data) {
        if (data.query) {
            var highlights = [];
            if (data.expandedSynonyms) highlights = highlights.concat(data.expandedSynonyms.map(function (v) {
                return {word: v, class: 'synonym'}
            }));
            if (data.expandedEfoTerms) highlights = highlights.concat(data.expandedEfoTerms.map(function (v) {
                return {word: v, class: 'efo'}
            }));
            var split = data.query.match(/(?:[^\s"]+|"[^"]*")+/g).map(function (v) {
                return v.replace(/\"/g, '')
            });
            highlights = highlights.concat(split.map(function (v) {
                return {word: v, class: 'highlight'}
            }));
            highlights.sort(function (a, b) {
                return b.word.length - a.word.length
            })
            $.each(highlights, function (i, v) {
                if (v.word != 'AND' && v.word != 'OR' && v.word != 'NOT') {
                    $("#search-results").highlight(v.word, {className: v.class, wordsOnly: v.word.indexOf('*') < 0});
                }
            });

            $("#renderedContent .highlight").attr('title', 'This is exact string matched for input query terms');
            $("#renderedContent .efo").attr('title', 'This is matched child term from Experimental Factor Ontology e.g. brain and subparts of brain');
            $("#renderedContent .synonym").attr('title', 'This is synonym matched from Experimental Factor Ontology e.g. neoplasia for cancer');


        }
    }


    return _self;
})(Searcher || {});