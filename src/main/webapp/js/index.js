!function(d) {

    registerHelpers();

    // Prepare template
    var template = Handlebars.compile($('script#stats-template').html());

    $.getJSON( contextPath + "/api/v1/search",{query:'type:Study',pageSize:5,sortBy:'release_date',sortOrder:'descending'}, function( data ) {
         if (data) {
             var totalCount = data.totalHits + (data.totalHits == 1 ? ' study' : ' studies');
             $('#template').html(template(data));
             $('#latest').slideDown();
             $('#studyCountStats').fadeIn();

             $.getJSON( contextPath + "/api/v1/search",{query:'type:Project'}, function( data ) {
                 if (data && data.totalHits && data.totalHits>0) {
                     $('#projectCount').text(data.totalHits + (data.totalHits == 1 ? ' project' : ' projects'));
                     $('#projectCountStats').fadeIn();
                 }
             });
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
