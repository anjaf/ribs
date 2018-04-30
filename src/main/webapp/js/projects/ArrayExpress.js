$(sectionTables).each(function () {
    var sampleColumn =  this.columns(function(i,d,n){ return $(n).text()=='No. of Samples' });
    if (!sampleColumn || !sampleColumn.length ||!sampleColumn.nodes()[0] ||  !sampleColumn.nodes()[0].length) return;
    $(sampleColumn.nodes()[0]).each( function(i,v) {
        var id = generatedID++;
        $(v).wrapInner('<a id="genid' +  id  + '" name="genid'+ id +'" href="#"></a>');
        $('a',v).attr('data-file-data-search',$(v).siblings().map(function(){ return $(this).text(); }).toArray().join(' '));
    })

});

$('a[data-file-data-search]').click( function () {
    expansionSource = ''+$(this).attr('id');
    clearFileFilter();
    $('#all-files-expander').click();
    filesTable.search($(this).data('file-data-search'));
    // hide empty columns
    filesTable.columns().every(function(){ if (filesTable.cells({search:'applied'},this).data().join('').trim()=='') this.visible(false) });
    filesTable.draw();
    return false;
})
