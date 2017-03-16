!function(d) {

    registerHelpers();

    // Prepare template
    var template = Handlebars.compile($('script#template').html());

    $.getJSON( contextPath + "/api/search",{query:'type:Study',pagesize:5,sortby:'release_date'}, function( data ) {
         if (data) {
             var totalCount = data.totalHits + (data.totalHits == 1 ? ' study' : ' studies');
             $('#template').html(template(data));
             $('#latest').slideDown();
             $('#studyCountStats').fadeIn();

             $.getJSON( contextPath + "api/search",{query:'type:Project'}, function( data ) {
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
