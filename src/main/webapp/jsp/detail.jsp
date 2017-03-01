<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:generic>
<jsp:body>
<link rel="stylesheet" href="../css/jquery.dataTables.css">
<link rel="stylesheet" href="../css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="../css/biostudies-colours.css">
<link rel="stylesheet" href="../css/detail.css" type="text/css"><!-- Handlebars templates-->
<script src="../js/jquery.dataTables.min.js"></script>
<script id='study-template' type='text/x-handlebars-template'>
    <div id="left-column">
        <div id="right-column">
            {{&file-table}}
        </div>
        <h4>{{valueWithName 'Title' attributes}}</h4>
        <ul id="bs-authors">
            {{#eachAuthor this}}
                <li>{{Name}} <sup><a class="org-link" data-affiliation="{{affiliation}}">{{affiliationNumber}}</a></sup></li>
            {{/eachAuthor}}
        </ul>
        <ul id="bs-orgs">
            {{#eachOrganization this}}
            <li id="{{affiliation}}"><sup>{{affiliationNumber}}</sup> {{name}}</li>
            {{/eachOrganization}}
        </ul>
        <div class="bs-name">Accession</div>
        <div>{{accno}}</div>
        {{#eachGroup 'name' 'value' attributes}}
            <div class="bs-name">{{name}}</div>
            <div>{{value}}</div>
        {{/eachGroup}}
        {{#if subsections}}
            {{#each subsections}}
                {{#ifRenderable this}}
                    {{&section this}}
                {{/ifRenderable}}
            {{/each}}
        {{/if}}
    </div>
    <div class="clearboth"></div>
</script>

<script id='section-template' type='text/x-handlebars-template'>
    <section>
        <div class="bs-name {{this.indentClass}}">
            {{type}}
            <!--span class="section-title-bar"><span class="file-filter"><i class="fa fa-filter"></i>
                        Files in: </span><a class="section-button" data-files-id="df1">This section</a></span-->
        </div>
        <div>
            {{#each attributes}}
                <div class="bs-name">{{this.name}}</div>
                <div>{{this.value}}</div>
            {{/each}}
            {{#if subsections}}
            <div class="has-child-section">
                {{#each subsections}}
                {{#ifArray this}}
                    {{&table this}}
                {{else}}
                    {{#ifRenderable this}}
                        {{&section this 'true'}}
                    {{/ifRenderable}}
                {{/ifArray}}
                {{/each}}
            </div>
            {{/if}}
        </div>
    </section>
</script>

<script id='section-table' type='text/x-handlebars-template'>
    {{setHeaders}}
    <section>
        <a class="show-more toggle-tables" data-total="1"><i class="fa fa-caret-right"></i> show table</a>
        <div class="bs-section-tables">
            <div class="table-caption">
                Table: {{this.type}}
                <span class="fa fa-expand fa-icon table-expander" title="Click to expand"/>
            </div>
            <div class="table-wrapper">
                <table class="stripe compact hover section-table">
                    <thead>
                    <tr>
                        {{#each this.headers}}
                        <th>{{this}}</th>
                        {{/each}}
                    </tr>
                    </thead>
                    <tbody>
                    {{#each this}}
                    <tr>
                        {{#each ../headers}}
                        <td>{{valueWithName this ../this.attributes}}</td>
                        {{/each}}
                    </tr>
                    {{/each}}
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</script>

<script id='file-table' type='text/x-handlebars-template'>
    {{setFileTableHeaders}}
    {{#if this.headers}}
    <section>
        <div id="file-list-container">
            <div class="table-caption">
                <span class="widge-title"><i class="fa fa-download"></i> Download data files</span>
                <span class="fa fa-expand fa-icon table-expander" title="Click to expand"/>
            </div>
            <div class="table-wrapper">
                <table id ="file-list" class="stripe compact hover">
                    <thead>
                    <tr>
                        <th id="select-all-files-header">
                            <input type="checkbox" id="select-all-files"/>
                        </th>
                        {{#each this.headers}}
                        <th>{{this}}</th>
                        {{/each}}
                    </tr>
                    </thead>
                    <tbody>
                    {{#each this}}
                    <tr>
                        <td class="disable-select file-check-box">
                            <input class="text-bottom" type="checkbox" data-name="{$vPath}"/>
                        </td>
                        {{#each ../headers}}
                            <td>{{valueWithName this ../this.attributes}}</td>
                        {{/each}}
                    </tr>
                    {{/each}}
                    </tbody>
                </table>
            </div>
        </div>
    </section>
    {{/if}}
</script>

<script>
    var accession = '${param.accession}';
</script>
<script src="../js/detail.js"></script>
</jsp:body>
</t:generic>