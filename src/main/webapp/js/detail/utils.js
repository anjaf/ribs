function getDateFromEpochTime(t) {
    var date = (new Date(t)).toLocaleDateString("en-gb", { year: 'numeric', month: 'long', day: 'numeric' });
    return date == 'Invalid Date' ? (new Date()).getFullYear() : date;
}


String.format = function() {
    var s = arguments[0];
    for (var i = 0; i < arguments.length - 1; i++) {
        var reg = new RegExp("\\{" + i + "\\}", "gm");
        s = s.replace(reg, arguments[i + 1]);
    }

    return s;
}

$.fn.groupBy = function(fn) {
    var arr = $(this),grouped = {};
    $.each(arr, function (i, o) {
        key = fn(o);
        if (typeof(grouped[key]) === "undefined") {
            grouped[key] = [];
        }
        grouped[key].push(o);
    });

    return grouped;
}

function accToLink(acc) {
    return acc.replace('/','').replace(' ','');
}


function getURL(accession, type) {
    if (!type) {
        type = /^[a-zA-z]+/.exec(accession);
        if (type && type.length) {
            type = type[0];
        } else {
            return null;
        }
    }
    var url =  DetailPage.linkMap[type.toLowerCase()] ? String.format(DetailPage.linkMap[type.toLowerCase()], encodeURIComponent(accession)) : null;
    if (type.toLowerCase()=='ega' && accession.toUpperCase().indexOf('EGAD')==0) {
        url = url.replace('/studies/','/datasets/');
    }
    if (accession.indexOf('http:')==0 || accession.indexOf('https:')==0  || accession.indexOf('ftp:')==0 ) {
        var value = accession.replace("http://",'').replace("https://",'').replace("ftp://",'')
        for(var r in DetailPage.reverseLinkMap) {
            var acc = new RegExp(r).exec(value);
            if (acc && acc.length>0) {
                return {url:accession, type: reverseLinkMap[r], text:acc[1] }
            }
        }
        url = accession;
    }
    return url ?  {url:url, type:type, text:accession} : null;
}
