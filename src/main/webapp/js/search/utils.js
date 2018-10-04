
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

function getParams() {
    var split_params = document.location.search.replace(/(^\?)/, '')
        .split("&")
        .filter(function (a) {
            return a != ''
        })
        .map(function (s) {
            s = s.split("=")
            v = decodeURIComponent(s[1]).split('+').join(' ');
            if (this[s[0]]) {
                if ($.isArray(this[s[0]])) {
                    this[s[0]].push(v)
                } else {
                    this[s[0]] = [this[s[0]], v];
                }
            } else {
                this[s[0]] = v;
            }
            return this;
        }.bind({}));
    var params = split_params.length ? split_params[0] : {};
    return params;
}