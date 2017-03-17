!function(d) {
    $('#login-button').click( function () {
        showLoginForm();
    })
    $('.popup-close').click( function () {
        $(this).parent().parent().hide();
    })

    var message = $.cookie("AeAuthMessage");
    if(message) {
        showError(message);
        showLoginForm();
    }

}(document);

function showLoginForm() {
    $('#login-form').show();
    $('#user-field').focus();
}

function showError(s) {
    $('#login-status').text(s).show();
}
