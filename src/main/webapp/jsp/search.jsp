<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<t:generic>
    <jsp:attribute name="head">
        <link rel="stylesheet" href="${contextPath}/css/search.css" type="text/css">
    </jsp:attribute>
    <jsp:attribute name="breadcrumbs">
        <ul class="breadcrumbs">
            <li><a href="${contextPath}/">BioStudies</a></li>
            <li>
                <span class="show-for-sr">Current: </span> Studies
            </li>
        </ul>
    </jsp:attribute>
    <jsp:attribute name="postBody">
        <script src="${contextPath}/js/jquery.highlight.js"></script>
        <script id='results-template' type='text/x-handlebars-template'>
            <div id="left-column">
                <div class="small-12 columns">
                    <div id="facets" class="small-2 columns"></div>
                    <div class="small-10 columns">
                        {{#ifCond query '&&' hits}}
                            <div><h4 id="search-results-query">Search results for <span class="query">{{query}}</span></h4></div>
                        {{/ifCond}}

                        {{#if this.hits}}
                            <div id="sort-by-div">
                                <span>Sort by:</span>
                                <select id="sort-by">
                                    <option value="relevance">Relevance</option>
                                    <option value="release_date">Released</option>
                                    <option value="files">Files</option>
                                    <option value="links">Links</option>
                                </select>
                                <span id="sorting-links">
                                    <a id="sort-desc"><i class="fa fa-angle-down"/></a><a id="sort-asc"><i class="fa fa-angle-up"></i></a>
                                </span>
                            </div>


                            <div class="clearboth" id="facet-filters"/>
                            {{&resultcount}}

                            <ul id="search-results">
                                {{#each this.hits}}
                                 <li>{{&result this}}</li>
                                {{/each}}
                            </ul>
                            {{&pager}}
                        {{else}}
                            {{#if query}}
                                <section>
                                    <h3 class="alert"><i class="icon icon-generic padding-right-medium" data-icon="l"></i>
                                        Your search for {{query}} returned no results.</h3>
                                    <p></p>
                                </section>
                            {{else}}
                                <section>
                                    <h3 class="alert"><i class="icon icon-generic padding-right-medium" data-icon="l"></i>
                                        Your search returned no results.</h3>
                                    <p></p>
                                </section>
                            {{/if}}
                            {{#if suggestion}}
                                <h3>Did you mean...</h3>
                                <ul id="spell-suggestions">
                                    {{#each suggestion}}
                                        <li> {{dynamicLink this}} </li>
                                    {{/each}}
                                </ul>
                            {{/if}}
                        {{/if}}
                    </div>
                </div>
            </div>
            <div class="clearboth"></div>
            <div id="allfacets"></div>
        </script>

     <script id='facet-filters-template' type='text/x-handlebars-template'>
         {{#each this}}
            <span class="selected-facet-values">
                <span class="facet-filter-label">{{name}}</span>
            {{#each values}}
                <span class="selected-facet-value"><span class="facet-filter-value">{{value}}</span><span class="drop-facet" data-facet-id="{{id}}">✕</span></span>
            {{/each}}
            </span>
         {{/each}}
     </script>
     <script id='facet-list-template' type='text/x-handlebars-template'>
            <form id="facet-form">
                <div id="facet" class="{{project}}-facets">
                    {{#each this}}
                        {{#if this.children}}
                            <div class="facet-name"><span class="toggle-facet"><i class="fa fa-angle-down"/></span> <span class="facet-title">{{title}}</span>
                                <a class="facet-more" data-facet="{{name}}">see all</a>
                                {{#ifCond children.length '>=' 10 }}
                                    <span class="top20">TOP 10</span>
                                {{/ifCond}}
                            </div>
                            <ul id="facet_{{name}}" class="menu vertical clearboth">
                                {{#each children}}
                                    <li>
                                        <label class="facet-label" for="facet-{{../name}}-{{@index}}">
                                            <input class="facet-value" type="checkbox" name="{{../name}}" value="{{value}}" id="facet-{{../name}}-{{@index}}"/>
                                            <span>{{name}}</span>
                                        </label>
                                        <span class="facet-hits"> {{formatNumber hits}}</span>
                                    </li>
                                {{/each}}
                            </ul>
                        {{/if}}
                    {{/each}}
                </div>
                {{#each existing}}
                    <input type="hidden" name="{{key}}" value="{{value}}" />
                {{/each}}
            </form>
        </script>
        <script id='result-template' type='text/x-handlebars-template'>
            <div class="search-result">
                <div class="meta-data">
                    <span class="accession">{{accession}}</span>
                    {{#ifCond isPublic '==' true}}
                    <span class="release-date">{{formatDate release_date}}</span>
                    {{/ifCond}}
                    {{#ifCond type '!=' 'project'}}
                    {{#ifCond links '!=' 0}}
                    <span class="release-links">
                        {{#ifCond links '>' 1}}
                            {{links}} links
                        {{else}}
                            {{links}} link
                        {{/ifCond}}
                    </span>
                    {{/ifCond}}
                    {{#ifCond files '!=' 0}}
                    <span class="release-files">
                        {{#ifCond files '>' 1}}
                            {{files}} files
                        {{else}}
                            {{files}} file
                        {{/ifCond}}
                    </span>
                    {{/ifCond}}
                    {{/ifCond}}
                    {{#ifCond isPublic '==' false}}
                    <span>🔒 Private</span>
                    <span>Modified: {{printDate mtime}}</span>
                    {{/ifCond}}
                </div>

                <div class="title" data-type="{{type}}" data-accession="{{accession}}">
                    {{#if project}}
                    <a href="${contextPath}/{{project}}/studies/{{accession}}">{{title}}</a>
                    {{else}}
                    <a href="${contextPath}/studies/{{accession}}">{{title}}</a>
                    {{/if}}
                </div>

                {{#if authors}}
                <div class="authors">{{authors}}</div>
                {{/if}}

                {{#if content}}
                <div class="content">{{content}}</div>
                {{/if}}


            </div>
        </script>
       <script id='all-facets-template' type='text/x-handlebars-template'>
           <div class="allfacets fullscreen">
               <form id="all-facet-form">
                   {{#each existing}}
                        <input type="hidden" name="{{key}}" value="{{value}}" />
                   {{/each}}
                   <a href="#" id="close-facet-search">CLOSE</a>
                   <input id="facet-search" type="search" title="Search" placeholder="Start typing to search" />
                   <button id="facet-search-button" type="submit">
                       <span class="fa-stack fa-2x">
                          <i class="fa fa-search fa-stack-2x"></i>
                          <i class="fa fa-check fa-stack-1x" data-fa-transform="shrink-2 left-3 up-2"></i>
                       </span>
                       <br/>
                       Update
                   </button>
                   <ul>
                       {{#each facets.children}}
                       <li>
                           <label class="facet-label" for="all-{{@index}}">
                               <input class="facet-value" type="checkbox" name="{{../facets.name}}" value="{{value}}" id="all-{{@index}}"/>
                               <span>{{name}}</span>
                               <span class="all-facet-hits"> {{formatNumber hits}}</span>
                           </label>
                       </li>
                       {{/each}}
                   </ul>
           </form>
           </div>
       </script>
       <script src="${contextPath}/js/search.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="loader">
            <i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span>
        </div>
    </jsp:body>
</t:generic>