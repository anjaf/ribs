Home.StatsLoader = (function () {

    var _self ={};
    var statsAnimation;

    _self.render = function () {
        statsAnimation = $({countNum: $('#linkCount').text()}).animate({countNum: 1000000}, {
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
        getStats();

    };

    function getStats() {
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
    }

    return _self;
})();
