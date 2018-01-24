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
                            <h4 class="clearboth">Search results for <span class="query">{{query}}</span></h4>
                        {{else}}
                            {{#if facets}}
                                <h4 class="clearboth">
                                    Search results for
                                    {{#each facets}}
                                    <span class="query">{{@key}}:{{this}}</span>
                                    {{/each}}
                                </h4>
                            {{/if}}
                        {{/ifCond}}
                        {{#if this.hits}}
                            {{&resultcount}}
                            <div id="sort-by-div">
                                <span>Sort by:</span>
                                <select id="sort-by">
                                    <option value="relevance">Relevance</option>
                                    <option value="release_date">Released</option>
                                    <option value="files">Files</option>
                                    <option value="links">Links</option>
                                </select>
                                <span id="sorting-links">
                                    <a class="fa fa-angle-down" id="sort-desc"/><a class="fa fa-angle-up" id="sort-asc"/>
                                </span>
                            </div>
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

        <script id='project-banner-template' type='text/x-handlebars-template'>
            <div class="project-banner-content columns medium-12 clearfix row">
                <span class="project-logo">
                    <a class="no-border" href="{{url}}" target="_blank">
                        <img src="{{logo}}"></a>
                </span>
                <span class="project-text">
                    <span class="project-description">{{description}}</span>
                </span>
            </div>
        </script>
        <script id='facet-list-template' type='text/x-handlebars-template'>
            <form>
                <div id="facet" class="{{project}}-facets">
                    {{#each this}}
                    <div class="facet-name">{{title}}
                        {{#ifCond children.length '==' 20 }}
                            <div class="facet-top"><span> TOP 20</span> <a class="facet-more" data-facet="{{name}}">show all</a></div>
                        {{/ifCond}}
                    </div>
                    <ul id="facet_{{name}}">
                        {{#each children}}
                        <li>
                            <label class="facet-label" for="{{../name}}:{{value}}">
                                <input class="facet-value" type="checkbox" name="facets" value="{{../name}}:{{value}}" id="{{../name}}:{{value}}"/>
                                <span>{{name}}</span>
                                <!--span class="facet-hits">{{formatNumber hits}}</span-->
                            </label>
                        </li>
                        {{/each}}
                    </ul>
                    {{/each}}
                </div>
                <input type="hidden" id="facet-query" name="query" value=""/>
            </form>
        </script>
        <script id='result-template' type='text/x-handlebars-template'>
            <div class="search-result">
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
                    {{/ifCond}}
                </div>
            </div>
        </script>
       <script id='all-facets-template' type='text/x-handlebars-template'>
           <div class="allfacets fullscreen">
               <form id="all-facet-form">
                   <input type="hidden" value="{{existing}}" name="facets"/>
                   <a href="#" id="close-facet-search">CLOSE</a>
                   <input id="facet-search" type="search" title="Search" placeholder="Start typing to search" />
                   <button id="facet-search-button" type="submit">
                       <span class="fa-stack fa-2x">
                          <i class="fa fa-search fa-stack-2x"></i>
                          <i class="fa fa-check fa-stack-1x" style="margin-left:-3pt;margin-top:-1pt"></i>
                       </span>
                       <br/>
                       Update
                   </button>
                   <!--label class="facet-label" for="all-check" style="display:inherit">
                       <input type="checkbox" class="facet-value" id="all-check" style="margin:0"/>
                       <span style="vertical-align: middle">Select all</span>
                    </label-->
                   <ul>
                       {{#each facets.children}}
                       <li>
                           <label class="facet-label" for="all-{{../facets.name}}:{{value}}">
                               <input class="facet-value" type="checkbox" name="facets" value="{{../facets.name}}:{{value}}" id="all-{{../facets.name}}:{{value}}"/>
                               <span>{{name}}</span>
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