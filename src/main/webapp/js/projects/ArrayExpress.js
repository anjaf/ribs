$(sectionTables).each(function () {
    var sampleColumn =  this.columns(function(i,d,n){ return $(n).text()=='No. of Samples' });
    if (!sampleColumn || !sampleColumn.length ||!sampleColumn.nodes()[0] ||  !sampleColumn.nodes()[0].length) return;
    /*$(sampleColumn.nodes()[0]).each( function() {
        $(this).wrapInner('<a href="#"></a>'
    )})*/

});