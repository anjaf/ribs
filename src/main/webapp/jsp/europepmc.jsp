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
                    {{#if this.hits}}
                        {{&pager}}
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
                    {{/if}}
                </div>
                <div class="small-12 columns">
                    <div id="facets" class="small-3 columns">
                    </div>
                    <div class="small-9 columns">
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
                            <ul id="search-results">
                                {{#each this.hits}}
                                <li>{{&result this}}</li>
                                {{/each}}
                            </ul>
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
                        {{/if}}
                    </div>
                </div>
            </div>
            </div>
            <div class="clearboth"></div>
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
                <div id="facet">
                    {{#each this}}
                        <div class="facet-name">{{title}}</div>
                        <ul>
                        {{#each children}}
                            <li>
                                <label class="facet-label" for="{{../name}}:{{name}}">
                                    <input class="facet-value" type="checkbox" name="facets" value="{{../name}}:{{name}}" id="{{../name}}:{{name}}"/>
                                    <span>{{name}}</span>
                                    <span class="facet-hits">({{hits}})</span>
                                </label>
                            </li>
                        {{/each}}
                        </ul>
                    {{/each}}
                    <div class="update-button">
                        <button type="submit" class="icon icon-functional update-icon" data-icon="1">Update</button>
                    </div>
                </div>
            </form>
        </script>
        <%@ include file="result-templates.jsp" %>
        <script src="${contextPath}/js/search.js"></script>
        <span id="hasFacets"></span>
    </jsp:attribute>
    <jsp:body>
        <div id="loader">
            <i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span>
        </div>
    </jsp:body>
</t:generic>