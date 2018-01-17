<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<t:generic>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="${contextPath}/css/jquery.dataTables.css">
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
        <script src="${contextPath}/js/jquery.dataTables.js"></script>
        <script src="${contextPath}/js/jquery.highlight.js"></script>
        <script src="<spring:eval expression="@orcidConfig.getdataClaimingUrl()"/>"></script>
        <!-- Handlebars templates-->
        <script id='study-template' type='text/x-handlebars-template'>
            <div id="left-column"  itemscope="itemscope" itemtype="http://schema.org/Dataset">
                {{#if project}}
                    <meta itemprop="url" content="${contextPath}/{{project}}/studies/{{accession}}" />
                    <meta itemprop="sameAs" content="${contextPath}/studies/{{accession}}" />
                {{else}}
                    <meta itemprop="url" content="${contextPath}/studies/{{accession}}" />
                {{/if}}
                <div id="right-column">
                    {{&main-file-table}}
                    {{&main-link-table}}
                    {{{main-orcid-claimer}}}
                </div>
                <div id="release-date-download">
                    <span class="release-date">
                        {{#if releaseDate}}
                        Release Date: <span  id="orcid-publication-year">{{formatDate releaseDate}}</span>
                        {{else}}
                            <i class="fa fa-lock" aria-hidden="true"></i> Private
                        {{/if}}
                    </span>
                    <div id="download-source">
                        <a href="${contextPath}/files/{{accno}}/{{accno}}.json" target="_blank"
                                                 title="Download Study as JSON" class="source-icon source-icon-json"
                                                 data-icon="=">{JSON}</a>
                        <a href="${contextPath}/files/{{accno}}/{{accno}}.xml" target="_blank"
                            title="Download Study as XML" class="source-icon source-icon-xml" data-icon="=">
                        &lt;XML&gt;</a><a href="${contextPath}/files/{{accno}}/{{accno}}.pagetab.tsv" target="_blank"
                                          title="Download Study as PageTab" class="source-icon source-icon-pagetab"
                                          data-icon="=">→PageTab↲</a>
                        {{#if releaseDate}}
                        <a href="ftp://ftp.biostudies.ebi.ac.uk/pub/{{root}}" target="_blank"
                            title="Open FTP Folder" class="source-icon source-icon-ftp" data-icon="="><i
                            class="fa fa-cloud-download"></i>FTP</a>
                        {{/if}}
                    </div>
                </div>
                <div id="bs-content">
                    <div id="thumbnail"></div>
                    <h4 id="orcid-title"  itemprop="name">{{valueWithName 'Title' attributes}}</h4>
                    <!-- Authors -->
                    <ul id="bs-authors">
                        {{#eachAuthor this}}
                            <li {{#ifCond @index '>=' 10}}class="hidden"{{/ifCond}} ><span class="author">
                        <span itemscope itemtype="http://schema.org/Person"><span itemprop="name">{{Name}}</span></span>
                                    {{#if affiliation}}
                                        {{#ifArray affiliation}}
                                            {{#each affiliation}}
                                                <sup><a class="org-link" data-affiliation="{{org}}">{{affiliationNumber}}</a></sup>
                                            {{/each}}
                                        {{else}}
                                            <sup><a class="org-link" data-affiliation="{{affiliation}}">{{affiliationNumber}}</a></sup>
                                        {{/ifArray}}
                                    {{/if}}
                                </span>
                            </li>
                            {{#if @last}}
                                {{#ifCond @index '>=' 10}}
                                    <li><span class="more" id="expand-authors">+ {{@left}} more</span></li>
                                {{/ifCond}}
                            {{/if}}
                        {{/eachAuthor}}
                    </ul>
                    <!-- Affiliations -->
                    <ul id="bs-orgs">
                        {{#eachOrganization this}}
                            <li {{#ifCond @index '>=' 10}}class="hidden"{{/ifCond}} id="{{affiliation}}"><sup>{{affiliationNumber}}</sup>
                                    <span itemscope itemtype="http://schema.org/Organization"><span itemprop="name">{{name}}</span></span>
                            </li>
                            {{#if @last}}
                                {{#ifCond @index '>=' 10}}
                                    <li><span class="more" id="expand-orgs">+ {{@left}} more</span></li>
                                {{/ifCond}}
                            {{/if}}
                        {{/eachOrganization}}
                    </ul>

                    <!-- Accession -->
                    <div class="bs-name">Accession</div>
                    <div id="orcid-accession" itemprop="identifier">{{accno}}</div>

                    <!-- Study level attributes -->
                    {{#eachGroup attributes}}
                        {{#each this}}
                            {{#ifCond name '!=' 'Title'}}
                                {{#if @first}}<div class="bs-name">{{name}}</div><div>{{/if}}
                                {{value}}{{&valquals valqual}}{{#if @last}}</div>{{else}}, {{/if}}
                            {{/ifCond}}
                        {{/each}}
                    {{/eachGroup}}


                    <!-- Publication -->
                    {{renderPublication this}}

                    <!-- Funding -->
                    {{#eachFunder this}}
                    {{#if @first}}
                    <div class="bs-name">Funding</div>
                    <ul id="bs-funding">
                        {{/if}}
                        <li><span itemscope itemtype="http://schema.org/Organization"><span itemprop="name">{{name}}</span></span>{{#if grants}}:
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
            <section id="{{accToLink this.accno}}" name="{{accToLink this.accno}}">
                <div class="bs-name {{this.indentClass}}">
                    {{#ifHasAttribute 'Title' this.attributes}}
                        {{valueWithName 'Title' this.attributes}}
                    {{else}}
                        {{type}} <span class="section-acc"><i class="fa fa-map-pin"></i> {{accno}}</span>
                    {{/ifHasAttribute}}
                    <span class="section-title-bar"></span>
                </div>
                <div class="has-child-section">
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
                        <span class="fa fa-expand fa-icon table-expander" id="all-files-expander" title="Click to expand"/>
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
                                    {{renderFileTableRow this ../this.attributes}}
                                {{/each}}
                            </tr>
                            {{/each}}
                            </tbody>
                        </table>
                        <div>
                            <span id="selected-file-text"></span><a id="download-selected-files">Download all</a>
                        </div>
                    </div>
                </div>
            </section>
            {{/if}}
        </script>

        <script id='main-link-table' type='text/x-handlebars-template'>
            {{setLinkTableHeaders}}
            {{#if this.linkHeaders}}
            <section>
                <div id="link-list-container">
                    <div class="table-caption">
                        <span class="widge-title"><i class="fa fa-external-link"></i> Linked Information</span>
                        <span class="fa fa-expand fa-icon table-expander" id="all-links-expander" title="Click to expand"/>
                    </div>
                    <div class="table-wrapper">
                        <table id="link-list" class="stripe compact hover">
                            <thead>
                            <tr>
                                {{#each linkHeaders}}
                                <th>{{this}}</th>
                                {{/each}}
                            </tr>
                            </thead>
                            <tbody>
                            {{#each this}}
                            <tr>
                                {{#each ../linkHeaders}}
                                    {{renderLinkTableRow this ../attributes}}
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

        <script id='main-similar-studies' type='text/x-handlebars-template'>
            {{#ifArray this}}
            <section>
                <div id="similar-study-container">
                    <div class="table-caption">
                        <span class="widge-title"><i class="icon icon-functional" data-icon="O"></i> Similar Studies</span>
                        <span class="fa fa-expand fa-icon table-expander" id="similar-studies-expander" title="Click to expand"/>
                    </div>
                    <div class="table-wrapper">
                        <ul class="recommendations">
                            {{#each this}}
                                <li class="browse-study-title">
                                    <a href="${contextPath}/studies/{{accession}}">{{title}}</a>
                                    <span class="browse-study-accession">
                                        {{accession}}
                                    </span>
                                </li>
                            {{/each}}
                        </ul>
                    </div>
                </div>
            </section>
            {{/ifArray}}
        </script>


        <script id='valqual-template' type='text/x-handlebars-template'>{{#ifArray this}}{{&renderOntologySubAttribute this}}{{#eachSubAttribute this}}
                    {{#if @first}}<i class="fa fa-info-circle sub-attribute-info"></i><span class="sub-attribute">{{/if}}
                    <span class="sub-attribute-title">{{name}}:</span>
                    <span>{{value}}</span>
                    <br/>
                    {{#if @last}}</span>{{/if}}
                {{/eachSubAttribute}}{{/ifArray}}</script>



        <script id='main-orcid-claimer' type='text/x-handlebars-template'>
            <section id="orc-id-claimer-section">
                <div class="table-caption">
                    <span class="widge-title"><img class="orcid-logo" src="${contextPath}/images/orcid.svg"></img>ORCID: Data claiming</span>
                    <span class="fa fa-expand fa-icon table-expander" title="Click to expand"/>
                </div>
                <div class="thor_div_showIf_notSigned" style="display:none">
                    <div>
                        You can <a href="#" class="thor_a_generate_signinLink">sign-in to ORCID</a> to claim your data
                    </div>
                    <div>
                        <input type="checkbox" class="thor_checkbox_rememberMe_cookie"> Remember
                            me on this computer
                    </div>
                </div>
                <div class="thor_div_showIf_signedIn" style="display:none">
                    <div>You have signed in as <label class="thor_label_show_userName" ></label>
                        <a href="#" title="Sign Out" class="thor_a_generate_logoutLink">(sign out)</a>
                    </div>
                    <div style="display:none" class="thor_div_showIf_datasetNotClaimed">
                        <a href="#" class="thor_a_generate_claimLink">Claim {{accession}} into your ORCID</a>
                    </div>
                    <div style="display:none" class="thor_div_showIf_datasetAlreadyClaimed">
                        You have claimed {{accession}} into your ORCID.
                    </div>
                </div>
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