!function(d) {

    registerHelpers();


    var projectAnimation = $({countNum: $('#projectCount').text()}).animate({countNum: 1}, {
        duration: 10000,
        easing:'swing',
        step: function(now) {
            $('#projectCount').text(formatNumber(Math.floor(this.countNum)));
        },
        complete: function() {
            $('#projectCount').text(formatNumber(this.countNum)+'+');
        }
    });
    $.getJSON( contextPath + "/api/v1/search",{query:'type:Project'}, function( data ) {
        if (data && data.totalHits && data.totalHits>0) {
            projectAnimation.stop();
            $('#projectCount').text(formatNumber(data.totalHits));
        }
    });

    var statsAnimation = $({countNum: $('#linkCount').text()}).animate({countNum: 1000000}, {
        duration: 5000,
        easing:'swing',
        step: function(now) {
            $('#fileCount').text(formatNumber(Math.floor(this.countNum)));
            $('#linkCount').text(formatNumber(Math.floor(this.countNum)));
        },
        complete: function() {
            $('#fileCount').text(formatNumber(this.countNum)+'+');
            $('#linkCount').text(formatNumber(this.countNum)+'+');
        }
    });
    $.getJSON( contextPath + "/api/v1/stats", function( data ) {
        if (data && data.files && data.links) {
            statsAnimation.stop();
            $('#fileCount').text(formatNumber(data.files));
            $('#linkCount').text(formatNumber(data.links));
        }
        $('#lastUpdateTime').text(data.time);
    });

    var studyAnimation = $({countNum: $('#studyCount').text()}).animate({countNum: 1000000}, {
        duration: 5000,
        easing:'swing',
        step: function(now) {
            $('#studyCount').text(formatNumber(Math.floor(this.countNum)));
        },
        complete: function() {
            $('#studyCount').text(formatNumber(this.countNum)+'+');
        }
    });
    $.getJSON( contextPath + "/api/v1/latest", function( data ) {
        if (data) {
            // Prepare template
            var template = Handlebars.compile($('script#latest-studies-template').html());
            $('#latest').html(template(data));
            $('#latestLoader').hide();
            $('#latest').slideDown();
            studyAnimation.stop();
            $('#studyCount').text(formatNumber(data.totalHits));
        }

    });


}(document);

function registerHelpers() {

    Handlebars.registerHelper('asString', function() {
        console.log(this)
        return this.name
    });

    Handlebars.registerHelper('formatNumber', function(s) {
        return formatNumber(s);
    });


    Handlebars.registerHelper('eachStudy', function(key, val, arr, options) {
        var mod = arr.reduce(function(r, i) {
            r[i[key]] = r[i[key]] || [];
            r[i[key]].push(i[val]);
            return r;
        }, {})
        var ret = '';
        for(var k in mod) {
            ret = ret + options.fn({name:k,value:mod[k].join(',')});
        }
        return ret;
    });
}
