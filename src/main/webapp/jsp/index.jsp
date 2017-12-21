<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<t:generic>
    <jsp:attribute name="head">
        <link rel="stylesheet" href="${contextPath}/css/index.css" type="text/css">
    </jsp:attribute>
    <jsp:attribute name="postBody">
        <script id='latest-studies-template' type='text/x-handlebars-template'>
            {{#each this.hits}}
                {{#if @first}}<ul id="latestList">{{/if}}
                <li>
                    <a href="studies/{{accession}}">{{title}}</a>
                    <span class="browse-study-accession">{{accession}}</span>
                </li>
                {{#if @last}}</ul>{{/if}}
            {{/each}}
        </script>
        <script src="${contextPath}/js/index.js"></script>
    </jsp:attribute>

    <jsp:body>
        <div id="bs-title-header">
            <div class="ribbon">s</div>
            <div class="twist">d</div>
            <div class="molecule">b</div>
            <div class="flask">A</div>
            <h2 id="tagline">BioStudies – one package for all the data supporting a study</h2>
        </div>
        <p> The BioStudies database holds descriptions of biological studies, links to data
            from these studies in other databases at EMBL-EBI or outside, as well as data that do not fit in the
            structured archives at EMBL-EBI. The database can accept a wide range of types of studies described
            via a simple format. It also enables manuscript authors to submit supplementary information and link
            to it from the publication.
        </p>
        <div class="medium-3 medium-centered columns">
            <h2>
                <a href="studies/">
                    <span class="icon icon-functional home-icon" data-icon="1"> Browse</span>
                </a>
            </h2>
        </div>
        <div>
            <div class="columns medium-12" id="tertiary">
                <div class="row" id="template">
                    <div class="columns medium-3">
                        <h5 id="fileCountStats">
                            <a href="studies/" title="Browse BioStudies">
                                <span class="icon fa fa-files-o home-icon-small" id="filesIcon" ></span>
                                <span id="fileCount">100000</span> files
                            </a>
                        </h5>
                        <h5 id="linkCountStats">
                            <a href="studies/" title="Browse BioStudies">
                                <span class="icon fa fa-link home-icon-small" id="linksIcon" ></span>
                                <span id="linkCount">100000</span> links
                            </a>
                        </h5>
                        <h5 id="projectCountStats">
                            <a href="studies/?query=type:Project" title="Browse BioStudies">
                                <span class="icon icon-functional home-icon-small" id="projectsIcon" data-icon="A"></span>
                                <span id="projectCount">4</span> projects
                            </a>
                        </h5>
                        <h5 id="studyCountStats">
                            <a href="?query=type:Study" title="Browse BioStudies">
                                <span class="icon icon-functional home-icon-small" id="studiesIcon" data-icon="b"></span>
                                <span id="studyCount">100000</span> studies
                            </a>
                        </h5>
                        <h5 id="lastUpdate">
                            <a href="" title="LastUpdate">
                                <span class="icon fa fa-clock-o home-icon-small" id="lastUpdateIcon"></span>
                                <span id="lastUpdateTime">N/A</span>
                            </a>
                        </h5>
                    </div>
                    <div class="columns medium-9">
                        <h5 id="latestHeading">
                            <a href="#" title="Latest studies">
                                <span class="icon icon-functional home-icon-small" id="latestIcon" data-icon="n"></span>
                                Latest
                            </a>
                        </h5>
                        <div id="latestLoader" style="text-align: center"><i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span></div>
                        <div id="latest"></div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>



</t:generic>

