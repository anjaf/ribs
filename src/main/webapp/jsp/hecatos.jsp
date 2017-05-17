<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<t:generic>
    <jsp:attribute name="head">
        <link rel="stylesheet" href="${contextPath}/css/search.css" type="text/css">
    </jsp:attribute>
    <jsp:attribute name="preContent">
        <div id="project-banner">
        </div>
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
                </div>
                <div class="small-12 columns">
                    <div id="facets" class="small-3 columns">
                    </div>
                    <div class="small-9 columns">
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

        <script id='result-template' type='text/x-handlebars-template'>
            <div class="search-result">
                <div class="meta-data">
                    <span class="release-date">{{epochToDate release_date}}</span>
                    {{#ifCond type '!=' 'project'}}
                    {{#ifCond links '!=' 0}}
                    <span class="release-links">{{links}} link(s)</span>
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
                </div>
                <div class="title" data-type="{{type}}" data-accession="{{accession}}">
                    {{#if project}}
                    <a href="${contextPath}/{{project}}/studies/{{accession}}">{{title}}</a> <span class="accession">{{accession}}</span>
                    {{else}}
                    <a href="${contextPath}/studies/{{accession}}">{{title}}</a> <span
                        class="accession">{{accession}}</span>
                    {{/if}}
                </div>
            </div>
        </script>

        <script id='error-template' type='text/x-handlebars-template'>
            <section>
                <h3 class="alert"><i class="icon icon-generic padding-right-medium" data-icon="l"></i>{{title}}</h3>
                <p>{{&message}}</p>
                <p>If you require further assistance locating missing page or file, please <a
                        href="mailto://biostudies@ebi.ac.uk" class="feedback">contact us</a> and we will look into it
                    for you.</p>
            </section>
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
            <div id="facet">
                {{#each this}}
                    <div class="facet-name">{{title}}</div>
                    <ul>
                    {{#each children}}
                        <li>
                            <label class="facet-label" for="{{../name}}:{{name}}">
                                <input class="facet-value" type="checkbox" id="{{../name}}:{{name}}"/>
                                <span>{{name}}</span>
                                <span class="facet-hits">{{hits}}</span>
                            </label>
                        </li>
                    {{/each}}
                    </ul>
                {{/each}}
            </div>
        </script>

        <script src="${contextPath}/js/search.js"></script>
        <span id="hasFacets"></span>
    </jsp:attribute>
    <jsp:body>
        <div id="loader">
            <i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span>
        </div>
    </jsp:body>
</t:generic>