!function(d) {

    registerHelpers();

    $.getJSON( contextPath + "/api/v1/search",{query:'type:Project', pageSize:4}, function( data ) {
        if (data && data.totalHits && data.totalHits>0) {
                data.hits.sort(function(a, b) { return a.title.toLowerCase() > b.title.toLowerCase()})
                var template = Handlebars.compile($('script#projects-template').html());
                $('#projects').html(template(data));
                $('#projectsLoader').hide();
                $('#projects').slideDown();
                $("a[data-type='project']").each( function() {
                    var $prj = $(this), accession = $(this).data('accession');
                    $(this).attr('href',contextPath+'/'+accession+'/studies');
                    $.getJSON(contextPath+ '/api/v1/studies/'+accession, function (data) {
                        var path = data.section.files.path;
                        if (!path && data.section.files[0]) path =data.section.files[0].path;
                        if (!path && data.section.files[0][0]) path = data.section.files[0][0].path;
                        if (path) {
                            $prj.prepend('<img src="' + contextPath + '/files/' + accession + '/' + path + '"/>');
                        }
                    })
                });
        } else {
            //TODO: Hide project panel
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
            if (data.time) {
                $('#lastUpdateTime').hide().text('(Updated: ' + new Date(data.time).toLocaleTimeString("en-gb", {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric'
                }) + ")");
            }
        }

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
            $('#lastUpdateTime').slideDown();
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

    Handlebars.registerHelper('eachProject', function(key, val, arr, options) {
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
