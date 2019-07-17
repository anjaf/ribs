function addHighlights(selector, data) {
    if (data.query) {
        var highlights = [];
        if (data.expandedSynonyms) highlights = highlights.concat(data.expandedSynonyms.map(function (v) {
            return {word: v, class: 'synonym'}
        }));
        if (data.expandedEfoTerms) highlights = highlights.concat(data.expandedEfoTerms.map(function (v) {
            return {word: v, class: 'efo'}
        }));
        var split = data.query.match(/(?:[^\s"]+|"[^"]*"|[\(\)]+)+/g).map(function (v) {
            return v.replace(/[\"|(|)]/g, '')
        });
        highlights = highlights.concat(split.map(function (v) {
            return {word: v, class: 'highlight'}
        }));
        highlights.sort(function (a, b) {
            return b.word.length - a.word.length
        })
        $.each(highlights, function (i, v) {
            if (v.word != 'AND' && v.word != 'OR' && v.word != 'NOT') {
                $(selector).mark(v.word, {className: v.class, wildcards:'enabled',separateWordSearch: false, accuracy:'exactly'});
            }
        });

        $("#renderedContent .highlight").attr('title', 'This is exact string matched for input query terms');
        $("#renderedContent .efo").attr('title', 'This is matched child term from Experimental Factor Ontology e.g. brain and subparts of brain');
        $("#renderedContent .synonym").attr('title', 'This is synonym matched from Experimental Factor Ontology e.g. neoplasia for cancer');


    }
}
