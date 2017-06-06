<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<t:generic>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="${contextPath}/css/jquery.dataTables.css">
        <link rel="stylesheet" href="${contextPath}/css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="${contextPath}/css/detail.css" type="text/css">
    </jsp:attribute>
    <jsp:attribute name="breadcrumbs">
        <ul class="breadcrumbs">
            <li><a href="${contextPath}">BioStudies</a></li>
            <li><a href="${contextPath}/studies">Studies</a></li>
            <li>
                <span class="show-for-sr">Current: </span> <span id="accession">Loading</span>
            </li>
        </ul>
    </jsp:attribute>

    <jsp:attribute name="postBody">
        <script src="${contextPath}/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/js/jquery.highlight.js"></script>
        <!-- Handlebars templates-->
        <script id='study-template' type='text/x-handlebars-template'>
            <div id="left-column">
                <div id="right-column">
                    {{&main-file-table}}
                    {{&main-link-table}}
                </div>
                <div id="release-date-download">
                    <span class="release-date">
                        {{#if releaseDate}}
                            Release Date: {{releaseDate}}
                        {{else}}
                            <i class="fa fa-lock" aria-hidden="true"></i> Private
                        {{/if}}
                    </span>
                    <div id="download-source">
                        <a href="/biostudies/files/{{accno}}/{{accno}}.json" target="_blank"
                                                 title="Download Study as JSON" class="source-icon source-icon-json"
                                                 data-icon="=">{JSON}</a>
                        <a href="/biostudies/files/{{accno}}/{{accno}}.xml" target="_blank"
                            title="Download Study as XML" class="source-icon source-icon-xml" data-icon="=">
                        &lt;XML&gt;</a><a href="/biostudies/files/{{accno}}/{{accno}}.pagetab.tsv" target="_blank"
                                          title="Download Study as PageTab" class="source-icon source-icon-pagetab"
                                          data-icon="=">→PageTab↲</a>
                        <a href="ftp://ftp.biostudies.ebi.ac.uk/pub/{{root}}" target="_blank"
                            title="Open FTP Folder" class="source-icon source-icon-ftp" data-icon="="><i
                            class="fa fa-cloud-download"></i>FTP</a>
                    </div>
                </div>
                <div id="bs-content">
                    <div id="thumbnail"></div>
                    <h4>{{valueWithName 'Title' attributes}}</h4>
                    <!-- Authors -->
                    <ul id="bs-authors">
                        {{#eachAuthor this}}
                        <li><span class="author">{{Name}}
                                {{#if affiliation}}
                                    <sup><a class="org-link" data-affiliation="{{affiliation}}">{{affiliationNumber}}</a></sup>
                                {{/if}}
                            </span>
                        </li>
                        {{/eachAuthor}}
                    </ul>
                    <!-- Affiliations -->
                    <ul id="bs-orgs">
                        {{#eachOrganization this}}
                        <li id="{{affiliation}}"><sup>{{affiliationNumber}}</sup> {{name}}</li>
                        {{/eachOrganization}}
                    </ul>

                    <!-- Accession -->
                    <div class="bs-name">Accession</div>
                    <div>{{accno}}</div>

                    <!-- Study level attributes -->
                    {{#eachGroup attributes}}
                        {{#each this}}
                            {{#if @first}}<div class="bs-name">{{name}}</div><div>{{/if}}
                                {{value}}
                            {{#if @last}}</div>{{/if}}
                        {{/each}}
                    {{/eachGroup}}


                    <!-- Publication -->
                    {{publication this}}

                    <!-- Funding -->
                    {{#eachFunder this}}
                    {{#if @first}}
                    <div class="bs-name">Funding</div>
                    <ul id="bs-funding">
                        {{/if}}
                        <li>{{name}}{{#if grants}}:
                            <span class="bs-grants">{{grants}}</span>
                            {{/if}}
                        </li>
                        {{#if @last}}
                    </ul>
                    {{/if}}
                    {{/eachFunder}}

                    <!-- Subsections -->
                    {{#if subsections}}
                        {{#each subsections}}
                            {{#ifRenderable this}}
                                {{&section this}}
                            {{/ifRenderable}}
                        {{/each}}
                    {{/if}}

                </div>
            </div>
            <div class="clearboth"></div>
        </script>

        <script id='publication-template' type='text/x-handlebars-template'>
            {{#if this}}
            <div class="bs-name">Publication</div>
            <div>{{#if authors}}{{authors}}.{{/if}}
                {{#if journal}}<i>{{journal}}</i>.{{/if}}
                {{#if publication_date}}{{publication_date}};{{/if}}
                {{volume}}{{#if pages}}: {{pages}}{{/if}}
                {{#if URLs}}
                    {{#each URLs}}
                        <span class="publication-link">
                            <span class="publication-link-type"><i class="fa fa-external-link" aria-hidden="true"></i> {{type}}</span>
                            <a href="{{url}}" target="_blank">{{text}}</a>
                        </span>
                    {{/each}}
                {{else}}
                    {{accno}}
                {{/if}}

            </div>
            {{/if}}
        </script>

        <script id='section-template' type='text/x-handlebars-template'>
            <section name="{{replaceCharacter this.accno '/' '-'}}">
                <div class="bs-name {{this.indentClass}}">
                    {{#ifHasAttribute 'Title' this.attributes}}
                        {{valueWithName 'Title' this.attributes}}
                    {{else}}
                        {{type}}
                    {{/ifHasAttribute}}
                    <!--span class="section-title-bar"><span class="file-filter"><i class="fa fa-filter"></i>
                                Files in: </span><a class="section-button" data-files-id="df1">This section</a></span-->
                </div>
                <div class="has-child-section">
                    {{&section-link-tables}}
                    {{#if subsections}}
                        {{#each subsections}}
                            {{#ifArray this}}
                                {{&table this}}
                            {{/ifArray}}
                        {{/each}}
                    {{/if}}

                    <!-- section level attributes -->
                    {{#eachGroup attributes}}
                        {{#each this}}
                            {{#ifCond name '!=' 'Title'}}
                                {{#if @first}}<div class="bs-name">{{name}}</div><div>{{/if}}
                                    {{value}}{{&valquals valqual}}{{#if @last}}</div>{{else}}, {{/if}}
                            {{/ifCond}}
                        {{/each}}
                    {{/eachGroup}}

                    {{#if subsections}}
                        {{#each subsections}}
                            {{#ifArray this}}
                            {{else}}
                                {{#ifRenderable this}}
                                    {{&section this 'true'}}
                                {{/ifRenderable}}
                            {{/ifArray}}
                        {{/each}}
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
                                <td>{{valueWithName this ../this.attributes }}</td>
                                {{/each}}
                            </tr>
                            {{/each}}
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
        </script>

        <script id='main-file-table' type='text/x-handlebars-template'>
            {{setFileTableHeaders}}
            {{#if this.headers}}
            <section>
                <div id="file-list-container">
                    <div class="table-caption">
                        <span class="widge-title"><i class="fa fa-download"></i> Download data files</span>
                        <span class="fa fa-expand fa-icon table-expander" title="Click to expand"/>
                    </div>
                    <div class="table-wrapper">
                        <table id="file-list" class="stripe compact hover">
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
                                    <input type="checkbox" data-name="{{path}}"/>
                                </td>
                                {{#each ../headers}}
                                    <td>{{valueWithName this ../this.attributes}}</td>
                                {{/each}}
                            </tr>
                            {{/each}}
                            </tbody>
                        </table>
                        <div>
                            <span id="selected-file-text"></span><a id="download-selected-files">Download all</a><br/><br/>
                        </div>
                    </div>
                </div>
            </section>
            {{/if}}
        </script>

        <script id='main-link-table' type='text/x-handlebars-template'>
            {{#eachLinkTable}}
            <section>
                <div class="table-caption">
                    <span class="widge-title"><i class="fa fa-download"></i> Linked Information</span>
                    <span class="fa fa-expand fa-icon table-expander" title="Click to expand"/>
                </div>
                {{&link-table this}}
            </section>
            {{/eachLinkTable}}
        </script>

        <script id='section-link-tables' type='text/x-handlebars-template'>
            {{#if this.links}}
            <section>
                <a class="show-more toggle-links" data-total="1"><i class="fa fa-caret-right"></i> show link table</a>
                <div class="bs-section-links">
                    {{#eachLinkTable}}
                    <div class="bs-section-link-table">
                        <div class="table-caption">
                            Link Table {{@indexPlusOne}}
                            <span class="fa fa-expand fa-icon table-expander" title="Click to expand"/>
                        </div>
                        {{&link-table this}}
                    </div>
                    {{/eachLinkTable}}
                </div>
            </section>
            {{/if}}
        </script>

        <script id='link-table' type='text/x-handlebars-template'>
            <div class="link-filters"></div>
            <div class="table-wrapper">
                <table class="link-list" class="stripe compact hover">
                    <thead>
                    <tr>
                        {{#each linkHeaders}}
                        <th>{{this}}</th>
                        {{/each}}
                    </tr>
                    </thead>
                    <tbody>
                    {{#each this.links}}
                    <tr>
                        {{#each ../linkHeaders}}
                        <td>{{valueWithName this ../attributes}}</td>
                        {{/each}}
                    </tr>
                    {{/each}}
                    </tbody>
                </table>
            </div>
        </script>

        <script id='valqual-template' type='text/x-handlebars-template'>{{#ifArray this}}
                {{&renderOntologySubAttribute this}}
                {{#eachSubAttribute this}}
                    {{#if @first}}<i class="fa fa-info-circle sub-attribute-info"></i><span class="sub-attribute">{{/if}}
                    <span class="sub-attribute-title">{{name}}:</span>
                    <span>{{value}}</span>
                    <br/>
                    {{#if @last}}</span>{{/if}}
                {{/eachSubAttribute}}
        {{/ifArray}}</script>

        <script id='error-template' type='text/x-handlebars-template'>
             <section>
                 <h3 class="alert"><i class="icon icon-generic padding-right-medium" data-icon="l"></i>{{title}}</h3>
                 <p>{{&message}}</p>
                 <p>If you require further assistance locating missing page or file, please <a href="mailto://biostudies@ebi.ac.uk" class="feedback">contact us</a> and we will look into it for you.</p>
             </section>
         </script>

        <script src="${contextPath}/js/detail.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="loader">
            <i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span>
        </div>
    </jsp:body>
</t:generic>