var Home = (function (_self) {

    _self.registerHelpers = function(){

        Handlebars.registerHelper('asString', function () {
            console.log(this)
            return this.name
        });

        Handlebars.registerHelper('formatNumber', function (s) {
            return formatNumber(s);
        });

        Handlebars.registerHelper('eachStudy', function (key, val, arr, options) {
            var mod = arr.reduce(function (r, i) {
                r[i[key]] = r[i[key]] || [];
                r[i[key]].push(i[val]);
                return r;
            }, {})
            var ret = '';
            for (var k in mod) {
                ret = ret + options.fn({name: k, value: mod[k].join(',')});
            }
            return ret;
        });

        Handlebars.registerHelper('eachProject', function (key, val, arr, options) {
            var mod = arr.reduce(function (r, i) {
                r[i[key]] = r[i[key]] || [];
                r[i[key]].push(i[val]);
                return r;
            }, {})
            var ret = '';
            for (var k in mod) {
                ret = ret + options.fn({name: k, value: mod[k].join(',')});
            }
            return ret;
        });
    }

    return _self;
})(Home || {});
