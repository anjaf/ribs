<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
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
                    {{&pager}}
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
                            <a class="fa fa-angle-down" id="sort-desc"/><a class="fa fa-angle-up" id="sort-asc"/>
                        </span>
                    </div>
                    {{#ifCond query '&&' hits}}
                        <h4 class="clearboth">Search results for <span class="query">{{query}}</span></h4>
                    {{/ifCond}}
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
                        {{#if suggestion}}
                            <h3>Did you mean...</h3>
                            <ul id="spell-suggestions">
                            {{#each suggestion}}
                                <li> {{dynamicLink this}} </li>
                            {{/each}}
                            </ul>
                        {{/if}}
                    {{/if}}
                    {{&pager}}
                </div>
            </div>
            <div class="clearboth"></div>
        </script>
        <%@ include file="result-templates.jsp" %>
        <script src="${contextPath}/js/search.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="loader">
            <i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span>
        </div>
    </jsp:body>
</t:generic>