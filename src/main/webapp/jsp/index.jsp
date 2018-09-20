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
                {{#if @last}}<span class="more"><a href="studies">more...</a></span></ul>{{/if}}
            {{/each}}
        </script>
        <script id='projects-template' type='text/x-handlebars-template'>
            {{#each this.hits}}
            {{#if @first}}<ul id="projectList">{{/if}}
                <li>
                    <a data-type="{{type}}" data-accession="{{accession}}" href="studies/{{accession}}"></a>
                </li>
                {{#if @last}}<span class="more"><a href="projects">more...</a></span></ul>{{/if}}
            {{/each}}

        </script>
        <script src="${contextPath}/js/index.js"></script>
    </jsp:attribute>

    <jsp:body>
        <div class="medium-8 columns">
            <div>
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
            </div>
            <div class="callout">
                <h5 id="latestHeading">
                    <a href="studies" title="Latest studies">Latest</a>
                    <span id="lastUpdateTime"></span>
                </h5>
                <div id="latestLoader" style="text-align: center"><i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span></div>
                <div id="latest"></div>
            </div>

        </div>
        <div class="medium-3 columns">
            <div class="callout">
                <h5>Data Content</h5>
                <h6 id="fileCountStats">
                    <a href="studies/" title="Browse BioStudies">
                        <span class="icon icon-generic home-icon-small" id="filesIcon" data-icon=";"></span>
                        <span id="fileCount">100000</span> files
                    </a>
                </h6>
                <h6 id="linkCountStats">
                    <a href="studies/" title="Browse BioStudies">
                        <span class="icon icon-generic home-icon-small" id="linksIcon" data-icon="L"></span>
                        <span id="linkCount">100000</span> links
                    </a>
                </h6>
                <h6 id="studyCountStats">
                    <a href="studies/" title="Browse BioStudies">
                        <span class="icon icon-functional home-icon-small" id="studiesIcon" data-icon="b"></span>
                        <span id="studyCount">100000</span> studies
                    </a>
                </h6>
            </div>
            <div  class="callout" id="allProjects" style="display: none">
                <h5>
                    <a href="projects" title="Projects">
                        Projects
                    </a>
                </h5>
                <div id="projectsLoader" style="text-align: center"><i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i><span class="sr-only">Loading...</span></div>
                <div id="projects"></div>
            </div>
        </div>


    </jsp:body>



</t:generic>

