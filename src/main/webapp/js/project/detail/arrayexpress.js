// create links for sample number
$(Metadata.getSectionTables()).each(function () {
    var sampleColumn =  this.columns(function(i,d,n){ return $(n).text()=='No. of Samples' });
    if (!sampleColumn || !sampleColumn.length ||!sampleColumn.nodes()[0] ||  !sampleColumn.nodes()[0].length) return;
    $(sampleColumn.nodes()[0]).each( function(i,v) {
        if($('a',this).length==1) return;
        var id = Metadata.getNextGeneratedId();
        $(v).wrapInner('<a id="genid' +  id  + '" name="genid'+ id +'" href="#"></a>');
        $('a',v).attr('data-file-data-search',$(v).siblings().map(function(){ return $(this).text(); }).toArray().join(' '));
    })

});
$('a[data-file-data-search]').click( function () {
    expansionSource = ''+$(this).attr('id');
    FileTable.clearFileFilter();
    $('#all-files-expander').click();
    var filesTable = FileTable.getFilesTable();
    filesTable.search($(this).data('file-data-search'));
    FileTable.hideEmptyColumns();
    filesTable.draw();
    return false;
})

// format MIAME/MinSeq scores
var $miameTitleDiv = $('.bs-name:contains("MIAME Score")');
if ($miameTitleDiv.text().trim().toLowerCase()=='miame score') {
    $miameTitleDiv.next().removeClass('has-child-section').css({'column-count':'5', 'width':'33%'});
    $('.bs-name',$miameTitleDiv.next()).toggleClass('bs-name miame-score-title');
    $('.miame-score-title').css('text-align','center').next().css('text-align','center').each(function() {
        $(this).html($(this).text().trim()=='*' ? '<i class="fas fa-asterisk" data-fa-transform="shrink-8"></i>' : '<i class="fas fa-minus" data-fa-transform="shrink-8"></i>' )
    })
}
var $minseqTitleDiv = $('.bs-name:contains("MINSEQE Score")');
if ($minseqTitleDiv.text().trim().toLowerCase()=='minseqe score') {
    $minseqTitleDiv.next().removeClass('has-child-section').css({'column-count':'5', 'width':'42%'});
    $('.bs-name',$minseqTitleDiv.next()).toggleClass('bs-name minseq-score-title');
    $('.minseq-score-title').css('text-align','center').next().css('text-align','center').each(function() {
        $(this).html($(this).text().trim()=='*' ? '<i class="fas fa-asterisk" data-fa-transform="shrink-8"></i>' : '<i class="fas fa-minus" data-fa-transform="shrink-8"></i>' )
    })
}


// add icon for files
var newIcon = $('<span class="fa-icon" title="Click to expand"><i class="fa fa-external-link-alt"></i></span>')
    .css({'float':'right', 'padding-left': '3px', 'cursor': 'pointer'})
    .click(function() {
        window.open( contextPath + (project? '/'+project:'')+'/studies/'+ $('#accession').text() + '/files' );
    });
$('#all-files-expander').before(newIcon);

// add icon for sdrf
var sdrfIcon = $('<div class="bs-name">Detailed sample information and links to data ' +
    '<a class="show-more" href="#" title="Click to open SDRF Viewer">' +
    ' view table <i class="fas fa-external-link-square-alt"></i></a>' +
    '</div>').click(function() {
        var acc = $('#accession').text();
        window.open( contextPath + (project? '/'+project:'')+'/studies/'+ acc + '/sdrf');
    });
sdrfIcon.insertAfter($('.bs-name:contains("Description")').first().next());