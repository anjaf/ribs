Home.LatestFeedLoader = (function () {

    var _self ={};

    _self.render = function () {
        $.getJSON( contextPath + "/api/v1/latest", function( data ) {
            if (data) {
                var template = Handlebars.compile($('script#latest-studies-template').html());
                $('#latestList').html(template(data));
                /*$('#latestLoader').hide();
                $('#latestContainer').show();
                $('#latest').slideDown();*/
                $('#studyCount').text(formatNumber(data.totalHits)+' studies').removeClass('fader');
                $('#lastUpdateTime').css('display','inline-block');
            }

        });
    };

    return _self;
})();
