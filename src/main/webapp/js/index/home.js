var Home = (function (_self) {

    _self.render = function(){
        this.registerHelpers();
        this.ProjectLoader.render();
        this.StatsLoader.render();
        this.LatestFeedLoader.render();
    };

    return _self;

})(Home || {});



$(function() {
    Home.render();
});


