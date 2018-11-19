var Help = (function (_self) {

    _self.render = function(){
        if (project && project=='BioImages') {
            $('#renderedContent').load(contextPath + '/help/' + (project ? project+'-' : '')+'help.html',
                function ( responseText, textStatus, jqXHR) {
                    if (textStatus=='error') {
                        loadCommonHelp()
                    }
                }
            );

        } else {
            loadCommonHelp();
        }
    };

    function loadCommonHelp() {
        $('#renderedContent').load(contextPath + '/help/help.html');
    }

    return _self;

})(Help || {});



$(function() {
    Help.render();
});
