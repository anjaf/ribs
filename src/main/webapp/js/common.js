!function(d) {
    $('#login-button').click( function () {
        showLoginForm();
    });
    $('.popup-close').click( function () {
        $(this).parent().parent().hide();
    });
    $('#logout-button').click( function () {
        $('#logout-form').submit();
    });

    var message = $.cookie("AeAuthMessage");
    if(message) {
        showLoginError(message);
        showLoginForm();
    }

}(document);

function showLoginForm() {
    $('#login-form').show();
    $('#user-field').focus();
}

function showLoginError(s) {
    $('#login-status').text(s).show();
}


function showError(error) {
    var errorTemplateSource = $('script#error-template').html();
    var errorTemplate = Handlebars.compile(errorTemplateSource);
    var data;
    switch (error.status) {
        case 400:
            data = {
                title: 'We’re sorry that we cannot process your request',
                message: 'There was a query syntax error in <span class="alert"><xsl:value-of select="$error-message"/></span>. Please try a different query or check our <a href="{$context-path}/help/index.html">query syntax help</a>.'
            }
            break;

        case 403:
            data = {
                title: 'We’re sorry that you don’t have access to this page or file',
                message: 'Please <a href="#" class="login">log in</a> to access <span class="alert"><xsl:value-of select="$error-request-uri"/></span>.'
            }
            break;

        case 404:
            data = {
                title: 'We’re sorry that the page or file you’ve requested is not publicly available',
                message: 'The resource may have been removed, had its name changed, or has restricted access.If you have been granted access, please <a href="#" class="login">log in</a> to proceed.'
            }
            break;

        default:
            data = {
                title: 'Oops! Something has gone wrong with BioStudies',
                message: 'The service you are trying to access is currently unavailable. We’re very sorry. Please try again later or use the feedback link to report if the problem persists.'
            }
            break;
    }

    var html = errorTemplate(data);
    $('#renderedContent').html(html);
    $('#accession').text("Error");
}
