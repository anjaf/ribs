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
        <script id='results-template' type='text/x-handlebars-template'>
            <div id="left-column" class="row small-up-3">
                    {{#each this.hits}}
                    <div class="column project-container">{{&result this}}</div>
                    {{/each}}
            </div>
        </script>
        <script id='project-result-template' type='text/x-handlebars-template'>
            <div class="search-result">
                <div class="title" data-type="{{type}}" data-accession="{{accession}}">
                    <a href="${contextPath}/{{project}}/studies/{{accession}}">{{title}}</a>
                </div>
                {{#if content}}
                    <div class="content">{{content}}</div>
                {{/if}}

                <div class="meta-data">
                    <span class="accession">{{accession}}</span>
                    {{#if isPublic}}
                        <span class="release-date">{{formatDate release_date}}</span>
                    {{else}}
                        <span>🔒 Private</span>
                    {{/if}}
                </div>
            </div>
        </script>
        <script src="${contextPath}/js/projects.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="loader">
            <i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span>
        </div>
    </jsp:body>
</t:generic>