
$(function() {
    $('#main-content-area').removeClass("row");
    var url = window.location.pathname.replace('/sdrf','');
    var accession = url.substr(url.lastIndexOf('/')+1);
    var params = getParams();
    $('#accession').html('<a href="'+contextPath + (project? '/'+project:'')+'/studies/'+accession+'">'+accession+'</a>')
        .parent().parent().append('<li>SDRF</li>');
    updateTitleFromBreadCrumbs();
    $.get(contextPath + '/files/'+accession+  '/' + accession + '.sdrf.txt' ).done(function(data) {
        var html = '<table id="sdrf" width="100%"><thead>';
        var rows = data.split('\n');
        var colsToMove = [];
        var columnDefs = rows[0]
            .split('\t')
            .map(function (header, i) {
                var col = {name:header, targets:[i]};
                if (header.indexOf('Characteristics') === 0) {
                    col.className = 'sdrf-sample-attribute-column';
                } else if (header.indexOf('Factor Value') === 0) {
                    col.className = 'sdrf-variable-column';
                } else if (header.indexOf('Extract Name') === 0) {
                    col.className = 'sdrf-assay-column';
                    col.name='Assay Name';
                    colsToMove.push(i);
                } else if (header.indexOf('Source Name') === 0) {
                    col.className = 'source-column';
                } else if (header.indexOf('[ENA_RUN]') > 0) {
                    col.className = 'ena-column'
                    col.name = 'ENA';
                    colsToMove.push(i);
                    col.render = function (data, type, row) {
                        return '<a href="'+getURL(data, 'ena').url+'" target="_blank"><i class="icon icon-generic" data-icon="L"></i></a>';
                    }
                } else if (header.indexOf('[FASTQ_URI]') > 0) {
                    col.className = 'fastq-column';
                    col.name = 'FASTQ';
                    colsToMove.push(i);
                    col.render = function (data, type, row) {
                        return '<a href="'+data+'" target="_blank"><i class="icon icon-functional" data-icon="="></i></a>';
                    }
                }

                if (col.hasOwnProperty('className')) {
                    var matches = /.*\[(.*)\]/g.exec(header);
                    col.name = (col.name!==header ||  matches==null ? col.name : matches[1]);
                }
                col.visible = col.hasOwnProperty('className');
                return col;
            });

        html += columnDefs.map( function (col) {
            return '<th>'+ col.name + '</th>';
        }).join('');

        html += "</thead><tbody>";
        for (var i =1; i < rows.length; i++){
            if (rows[i]=='') continue;
            html += '<tr>';
            html+=rows[i].split('\t')
                .map(function (v) {
                    return  '<td>'+v.replace(/^["\s\uFEFF\xA0]+|["\s\uFEFF\xA0]+$/g, '')+'</td>'
                }).join('');
            html += '</tr>';
        }

        html += "</tbody></table>";
        $('#main-content-area').append($(html));

        var sdrfTable = $('#sdrf').DataTable( {
            "columnDefs": columnDefs,
            lengthMenu: [[10,25, 50, 100, 250, 500, -1], [10,25, 50, 100, 250, 500,'All']],
            scrollX:true,
            colReorder: true
        });

        var order  = sdrfTable.colReorder.order();
        var newOrder = order.filter(function (v) {
           return $.inArray(v, colsToMove)<0;
        });
        sdrfTable.colReorder.order($.merge(newOrder, colsToMove)).draw();


    }).fail(function (error) {
        showError(error);
    });
});
