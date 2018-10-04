
String.prototype.replaceAll = function(src, dst) {
    return this.replace(new RegExp(src, 'g'), dst);
};

function getExistingParams(params, filter) {
    var existing = [];
    $.each(params, function (k,v) {
        if (k.indexOf(filter)==0 || k=='page') return;
        $.each($.isArray(v) ? v : [v], function (i,s) {
            existing.push({key: k, value: s});
        });
    });
    return existing;
}

function jqueryEncode(v) {
    return v.replace( /(\/|:|\.|\[|\]|,|=)/g, "\\$1" );
}

