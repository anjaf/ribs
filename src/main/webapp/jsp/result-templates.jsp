<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
            <span class="release-date">{{formatDate release_date}}</span>
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
        </div>
    </div>
</script>
