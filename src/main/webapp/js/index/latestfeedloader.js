Home.LatestFeedLoader = (function () {

    var _self ={};

    _self.render = function () {
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
                $('#latest').prepend(template(data));
                $('#latestLoader').hide();
                $('#latestContainer').show();
                $('#latest').slideDown();
                studyAnimation.stop();
                $('#studyCount').text(formatNumber(data.totalHits));
                $('#lastUpdateTime').slideDown();
            }

        });
    };

    return _self;
})();
