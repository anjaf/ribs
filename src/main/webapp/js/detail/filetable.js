var FileTable = (function (_self) {

    var totalRows=-1;
    var selectedFiles=[];
    var selectedFilesCount=0;
    var filesTable;
    var firstRender = true;

    _self.render = function (acc, params, isDetailPage){
        $.ajax({url: window.contextPath+"/api/v1/info/"+acc,
            data:params,
            success: function(response){
                if (isDetailPage) {
                    handleSecretKey(response.seckey);
                    handleModificationDate(response.modified);
                }
                if (!response.files || response.files==0) {
                    $('#file-list-container').parent().remove();
                    return;
                }
                handleFileTableColumns(response.columns, acc, params, isDetailPage);
                handleFileDownloadSelection(params.key);
                handleFileFilters(response.sections);

            }});
    };


    _self.clearFileFilter =  function() {
        if (!filesTable) return; // not yet initialised
        filesTable.columns().visible(true);
        filesTable.search('').columns().search('').draw();
    };

    function handleModificationDate(t) {
        $('.release-date').append('&nbsp; ' + String.fromCharCode(0x25AA)+' &nbsp; Modified: '+ getDateFromEpochTime(t));
    }

    function handleSecretKey(key) {
        if (!key) return;
        var $secret = $('<a id="secret" href="#" class="source-icon source-icon-secret"><i class="fas fa-share-alt" aria-hidden="true"></i> Share</a>');

        $secret.bind('click', function() {
            var templateSource = $('script#secret-template').html();
            var template = Handlebars.compile(templateSource);

            $('#biostudies-secret').html(template({
                url:window.location.protocol + "//"+ window.location.host+ window.contextPath+"/studies/"+$('#accession').text()+"?key="+key
            }));
            $('#biostudies-secret').foundation('open');
            $('#copy-secret').bind('click', function(){
                var $inp = $("<input>");
                $("body").append($inp);
                $inp.val($('#secret-link').text()).select();
                document.execCommand("copy");
                $inp.remove();
                $('#secret-copied').show().delay(1000).fadeOut();

            });
        });
        $('#download-source').prepend($secret);

    }

    function handleFileTableColumns(columns, acc, params, isDetailPage) {

        if (!isDetailPage) {
            $('#file-list').addClass('bigtable');
        }
        columns.splice(0, 0, {
            name: "x",
            title: "",
            searchable: false,
            type:"string",
            visible: true,
            orderable: false,
            render: function (data, type, row) {
                return '<div class="file-check-box"><input type="checkbox" data-name="' + row.path + '"></input></div>';;
            }
        });
        // add section rendering
        var sectionColumn = columns.filter(function(c) {return c.name=='Section';});
        if (sectionColumn.length) {
            sectionColumn[0].render = function (data, type, row) {
                return '<a href="#'+data+'">'+$('#'+data+' .section-name').first().text().trim()+'</a>';
            }
        }

        filesTable = $('#file-list').DataTable({
            lengthMenu: [[5, 10, 25, 50, 100], [5, 10, 25, 50, 100]],
            processing: true,
            serverSide: true,
            columns: columns,
            scrollX: !isDetailPage,
            order: [[ 1, "asc" ]],
            columnDefs: [
                {
                    orderable: false,
                    className: 'select-checkbox',
                    targets: 0
                },
                {
                    targets: 2,
                    render: function (data, type, row) {
                        return getByteString(data);
                    }
                },
                {
                    targets: 1,
                    render: function (data, type, row) {
                        return '<a class="overflow-name-column" target="_blank" style="max-width: 500px;" title="'
                            + data
                            + '" href="'
                            + window.contextPath+'/files/'+acc+'/' +row.path.replaceAll('#','%23')
                            + (params.key ? '?key='+params.key : '')
                            + '">'
                            + data + '</a>';
                    }
                },
                {
                    targets: 3,
                    render: function (data, type, row) {
                        if(columns[3].TITLE='Thumbnail')    {return '<img  height="100" width="100" src="'
                            +window.contextPath+'/thumbnail/'+ $('#accession').text()+'/'+row.path +'" </img> ';
                        }
                    }
                }
            ],
            ajax: {
                url: '/biostudies/api/v1/filelist',
                type: 'post',
                data: function (dtData) {
                    // add file search filter
                    if (firstRender && params['fs']) {
                        $('#all-files-expander').click();
                        dtData.search.value = params.fs;
                        firstRender = false;
                    }

                    return $.extend($.extend(dtData, {acc: acc}), params)
                },
                complete: function (data) {
                    //handleFileDownloadSelection();
                }
            },
            rowCallback: function( row, data ) {
                if ( $.inArray(data.path, selectedFiles) !== -1 ) {
                    $(row).addClass('selected');
                }
            },
            "infoCallback": function( settings, start, end, max, total, out ) {
                btn = $('<span/>').html ('<a class="section-button" id="clear-file-filter"><span class="fa-layers fa-fw">'
                    +'<i class="fas fa-filter"></i>'
                    +'<span class="fa-layers-text" data-fa-transform="shrink-2 down-4 right-6">×</span>'
                    +'</span> show all files');
                return (total== max) ? out : out + btn.html();
            }
        }).on('xhr.dt', function (e, settings, json, xhr) {
            if (totalRows == -1) { //override totalFiles
                totalRows = json.recordsTotal
            } else {
                json.recordsTotal = totalRows
            }
        }).on('draw.dt', function (e) {
            $('.file-check-box input').on('click', function(){
                if ($(this).is(':checked')) {
                    selectedFiles.push( $(this).data('name'));
                } else {
                    selectedFiles.splice($.inArray($(this).data('name'), selectedFiles), 1);
                }
                $(this).parent().parent().parent().toggleClass('selected');
                updateSelectedFiles();
            });

            $('.file-check-box input').each(function(){
                if ($.inArray($(this).data('name'), selectedFiles)>=0 ) {
                    $(this).attr('checked','checked');
                }
            });

            $('#clear-file-filter').on('click', function () {
                FileTable.clearFileFilter();
            });

            // TODO: enable select on tr click
            updateSelectedFiles();
            handleThumbnails();
            //if (params.fs) filesTable.search(params.fs).draw();

        });

    }


    function handleFileFilters(sections) {
        // add file filter button for section
        $(sections).each(function (i,divId) {
            var bar = $('#' + divId + '> .bs-name > .section-title-bar');
            bar.append('<a class="section-button" data-files-id="'+ divId + '"><i class="fa fa-filter"></i> show files in this section</a>');
        });
        // handle clicks on file filters in section
        $("a.section-button[data-files-id]").click(function () {
            var expansionSource = '' + $(this).data('files-id');
            Metadata.setExpansionSource(expansionSource);
            //clearFileFilter();
            $('#all-files-expander').click();
            filesTable.column(':contains(Section)').search(expansionSource);
            // hide empty columns
            //filesTable.columns().every(function(){ if (filesTable.cells({search:'applied'},this).data().join('').trim()=='') this.visible(false) });
            filesTable.draw();
        });
    }


    function handleFileDownloadSelection(key) {

        // add select all checkboz
        $(filesTable.columns(0).header()).html('<input id="select-all-files"  type="checkbox"/>');
        $('#select-all-files').on('click', function () {
            $('body').css('cursor', 'progress');
            $('#select-all-files').css('cursor', 'progress');
            $('#file-list_wrapper').css('pointer-events','none');
            if ($(this).is(':checked')) {
                $('.select-checkbox').parent().addClass('selected');
                $('.select-checkbox input').prop('checked',true);
                $.post('/biostudies/api/v1/filelist', $.extend(true, {}, filesTable.ajax.params(), {
                        length: -1,
                        metadata: false,
                        start: 0
                    }),
                    function (response) {
                        var filtered = [];
                        for (var i=0; i< response.data.length; i++) {
                            filtered.push(response.data[i].path);
                        }
                        selectedFiles = $.uniqueSort($.merge(selectedFiles, filtered ));
                        updateSelectedFiles();
                    }
                );
            } else {
                selectedFiles=[];
                $('.select-checkbox').parent().removeClass('selected');
                $('.select-checkbox input').prop('checked',false);
                $('#file-list_wrapper').css('pointer-events','auto');
                updateSelectedFiles();
            }
        });

        $("#download-selected-files").on('click', function () {
            // select all checked input boxes and get the href in the links contained in their siblings
            var html = '<form method="POST" target="_blank" action="'
                + window.contextPath + "/files/"
                + $('#accession').text() + '/zip">';
            $(selectedFiles).each( function(i,v) {
                html += '<input type="hidden" name="files" value="'+v+'"/>'
            });
            if (key) {
                html += '<input type="hidden" name="key" value="'+key+'"/>' ;
            }
            html += '</form>';
            var submissionForm = $(html);
            $('body').append(submissionForm);
            $(submissionForm).submit();
        });

    }

    function updateSelectedFiles() {
        if (selectedFiles.length>0) {
            $('.filetoolbar #selected-file-count').html(selectedFiles.length);
            $('.filetoolbar').css('visibility', 'visible');
        } else {
            $('.filetoolbar').css('visibility', 'hidden');
        }

        $('#select-all-files').prop('checked', $('.select-checkbox input:checked').length == $('.select-checkbox input').length );
        $('body').css('cursor', 'default');
        $('#select-all-files').css('cursor', 'default');
        $('#file-list_wrapper').css('pointer-events','auto');
    }

    function handleThumbnails() {
        var imgFormats = ['bmp','jpg','wbmp','jpeg','png','gif','tif','tiff','pdf','docx','txt','csv','html','htm'];
        var isZip = false;
        if(filesTable.column('Thumbnail')) {
            isZip = true;
        }
        if(isZip)
            imgFormats.splice(1,0,'zip');
        $(filesTable.column(1).nodes()).each(function () {
            var path = $('input',$(this).prev()).data('name').replaceAll('#','%23');
            $('a',this).addClass('overflow-name-column');
            $('a',this).attr('title',$(this).text());
            if ( $.inArray(path.toLowerCase().substring(path.lastIndexOf('.')+1),
                imgFormats) >=0 ) {
                $(this).append('<a href="'+$(this).find('a').attr('href')+'" class="thumbnail-icon" data-thumbnail="'
                    +window.contextPath+'/thumbnail/'+ $('#accession').text()+'/'+path+'"><i class="far fa-file-image"></i></a>')
            }
        });

        $(filesTable.column(1).nodes()).hover( function() {
            var $tn = $(this).find('.thumbnail-icon');
            if (!$tn.length) return;
            $('#thumbnail').html('<i class="fa fa-spinner fa-pulse fa-fw"></i><span class="sr-only">Loading...</span>')
            $('#thumbnail').css('top',$tn.offset().top - 10);
            $('#thumbnail').css('left',$tn.parent().offset().left - $('#thumbnail').width() - 10);
            $('#thumbnail').show();
            var img = $("<img />").attr('src', $tn.data('thumbnail'))
                .on('load', function() {
                    if (!this.complete || typeof this.naturalWidth == "undefined" || this.naturalWidth == 0) {
                        $('#thumbnail').hide();
                    } else {
                        $('#thumbnail').html('').append(img)
                        $('#thumbnail').css('top',$tn.offset().top - 10);
                        $('#thumbnail').css('left',$tn.parent().offset().left - $('#thumbnail').width() - 10);
                        $('#thumbnail').show();
                    }
                });
        }, function () {
            $('#thumbnail').hide();
        });

    }

    function getByteString(b) {
        if (b==undefined) return '';
        if (b==0) return '0 bytes';
        if (b==1) return '1 byte';
        prec = {'bytes':0, 'KB':0, 'MB':1, 'GB':2, 'TB':2, 'PB':2, 'EB':2, 'ZB':2, 'YB':2};
        keys = $.map(prec, function(v,i){return i});
        var i = Math.floor(Math.log(b) / Math.log(1000))
        return parseFloat(b / Math.pow(1000, i)).toFixed(prec[keys[i]]) + ' ' + keys[i];
    }


    return _self;

})(FileTable || {});