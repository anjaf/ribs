var FacetRenderer = (function (_self) {

    _self.render = function (params) {
        $('#left-column').slideDown("fast", function () {
            // fill facets
            $.getJSON(contextPath + "/api/v1/" + (project||"public") + "/facets", params, function (data) {
                var templateSource = $('script#facet-list-template').html();
                var template = Handlebars.compile(templateSource);
                data.project = (project||"public");
                data.existing = getExistingParams(params, 'facet.');
                var html = template(data);
                $('#facets').html(html);
            }).fail(function (error) {
                showError(error);
            }).done( function (data) {
                postRenderFacets(data, params);
            });
        })
    };

    function postRenderFacets (data, params) {

        // check the currently selected facet, if any
         for (var fkey in params) {
            if (fkey.toLowerCase().indexOf("facet.")!=0) continue;
            $.each( $.isArray(params[fkey]) ? params[fkey] : [params[fkey]] , function (i,v) {
                $('input[name="'+fkey+'"][value="' + v + '"]').attr('checked', 'checked');
            });
        }

        // put selected facets on top and add facet labels on top of results
        var facetMap={};
        $('li .facet-value:checked').each(function(){
            var fkey = $(this).attr('name');
            if (!facetMap[fkey]) facetMap[fkey] = [];
            facetMap[fkey].push($(this).parent().parent().detach());
        })

        var facetFilterMap = [];
        for (var key in facetMap  ) {
            var selectedFilters = [];
            $('ul#facet_'+jqueryEncode(key)).prepend(facetMap[key]);
            $.each(facetMap[key], function(i,v) {
                selectedFilters.push( { id: $('input',v).attr('id'), value:$('label',$(v)).text().trim() } );
            });
            facetFilterMap.push({
                    id:jqueryEncode(key),
                    name:$('span.facet-title',$('ul#facet_'+jqueryEncode(key)).prev()).text().trim(),
                    values: selectedFilters
            })
        }

        var face_filter_template = Handlebars.compile($('script#facet-filters-template').html());
        $('#facet-filters').append(face_filter_template(facetFilterMap));


        $('.drop-facet').bind('click', function(){
           $('#'+jqueryEncode($(this).data('facet-id'))).click();
        });

        // resubmit form when facets are changed
        $('input.facet-value').change(function(){ $(this).parents('form:first').submit() });

        // handle show more
        $('.facet-more').click(function(e) {
            e.stopPropagation();
            //if (!project) return;
            $('body').append('<div id="blocker" class="blocker"></div>');
            $('body').append('<div id="facet-loader"><i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><div class="sr-only">Loading...</div></div>');
            var thisFacet = $(this).data('facet');
            $.getJSON(contextPath+"/api/v1/"+(project? project : 'public')+'/facets/'+thisFacet+'/',params, function(data) {
                if ( !$('#facet-loader').length) return;
                $('#facet-loader').hide();
                var templateSource = $('script#all-facets-template').html();
                var template = Handlebars.compile(templateSource);
                var existing = getExistingParams(params, thisFacet);
                $('body').append(template({facets:data, existing:  existing}));
                $('#facet-search').focus()

                //build lookup cache
                var facetNames = [];
                var facetListItems = $(".allfacets ul li");
                for (var i = 0, len = data.children.length; i < len; i++) {
                    facetNames.push(data.children[i].name.toLowerCase());
                }

                // add filter
                searchWaitInterval = null;
                $('#facet-search').change( function(){
                    var filter = $('#facet-search').val().toLowerCase();
                    for (var i = 0, len = facetNames.length; i < len; i++) {
                       if (facetNames[i].indexOf(filter)>=0)
                           $(facetListItems[i]).show();
                       else
                           $(facetListItems[i]).hide();
                    }
                }).keyup(function () {
                    var that = this;
                    clearInterval(searchWaitInterval);
                    searchWaitInterval = setTimeout(function(){
                        $(that).change();
                    },200);
                });

                //hook events
                $(".allfacets ul li input").change(function() {
                    toggleFacetSearch();
                });

                $('#close-facet-search').click( function () {
                    closeFullScreen();
                });

                // check the currently selected face, if any
                for (var v in params){
                    if (v.indexOf(thisFacet)!=0) continue;
                    $.each($.isArray(params[v]) ? params[v] : [params[v]], function (i,value) {
                        $('input[value="'+ value + '"]', $(".allfacets ul li")).attr('checked','checked');
                    });

                };

                //handle select all
                $('#all-check').click(function () {
                    if ($("#all-check:checked").length) {
                        $(".allfacets ul li input").attr('checked','checked')
                    } else {
                        $(".allfacets ul li input").removeAttr('checked')
                    }
                });

                toggleFacetSearch();

            });
        });

        //handle escape key on fullscreen
        $(document).on('keydown',function ( e ) {
            if ( e.keyCode === 27 ) {
                closeFullScreen();
            }
        });

        //handle facet toggles
        $('.facet-name').bind('click', function(){
            var facetUL = $(this).next();
            if (facetUL.is(':visible'))  {
                facetUL.slideUp(100);
            } else {
                facetUL.slideDown(100);
            }
            $('.toggle-facet', this).find('[data-fa-i2svg]').toggleClass('fa-angle-right fa-angle-down');
        });
    }


    function toggleFacetSearch() {
        if ($(".allfacets ul li input:checked").length>100) {
            $("#facet-search-button").attr("disabled", "disabled");
        } else {
            $("#facet-search-button").removeAttr("disabled");
        }
    }

    function closeFullScreen() {
        $('#blocker').remove();
        $('#facet-loader').remove();
        $('.allfacets').remove();

    }

    return _self;
})(FacetRenderer || {});