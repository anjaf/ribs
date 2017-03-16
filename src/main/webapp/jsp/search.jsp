<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:generic>
    <jsp:attribute name="head">
        <link rel="stylesheet" href="../css/search.css" type="text/css">
    </jsp:attribute>
    <jsp:attribute name="breadcrumbs">
    <ul class="breadcrumbs">
        <li><a href="../../">BioStudies</a></li>
        <li>
            <span class="show-for-sr">Current: </span> Studies
        </li>
    </ul>
    </jsp:attribute>
    <jsp:attribute name="postBody">
        <!-- Handlebars templates-->
        <script id='results-template' type='text/x-handlebars-template'>
            <div id="left-column">
                <div class="small-12 columns">
                    {{#if query}}
                    <h2>Search results for: {{query}}</h2>
                    {{/if}}
                    {{&pager}}
                    <ul id="search-results">
                        {{#each this.hits}}
                        <li>{{&result this}}</li>
                        {{/each}}
                    </ul>
                </div>
            </div>
            <div class="clearboth"></div>
        </script>

        <script id='result-template' type='text/x-handlebars-template'>
            <div class="search-result">
                <div class="meta-data">
                    <span class="release-date">4 February 2017</span>
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

                </div>
                <div class="title"><a href="../studies/{{accession}}">{{title}}</a> <span class="accession">{{accession}}</span>
                </div>
            </div>
        </script>
        <script src="../js/search.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="loader">
            <i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span>
        </div>
    </jsp:body>
</t:generic>