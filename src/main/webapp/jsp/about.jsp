<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<t:generic>
    <jsp:attribute name="breadcrumbs">
        <ul class="breadcrumbs">
            <li><a href="${contextPath}/">BioStudies</a></li>
            <li>
                <span class="show-for-sr">Current: </span> About
            </li>
        </ul>
    </jsp:attribute>
    <jsp:body>
        <div id="static-text">
        <h3 class="icon icon-generic" data-icon="i">What is BioStudies?</h3>

        <p class="justify">The BioStudies database holds descriptions of biological studies, links to data from
            these studies in other databases at EMBL-EBI or outside, as well as data that do not fit in the
            structured archives at EMBL-EBI. The database accepts submissions via an online tool, or in a simple tab-delimited format.
            It also enables authors to submit supplementary information and link to it from the publication. See McEntyre J. et al., 2015. <a href="http://europepmc.org/abstract/MED/26700850">The BioStudies database</a>.
        </p>

         <h3 class="icon icon-generic" data-icon="P">Publications / How to cite</h3>
        <ul>
            <li><strong>Citing BioStudies database</strong>: please use the following publication:
                McEntyre J. et al., 2015. <a href="http://europepmc.org/abstract/MED/26700850">The BioStudies database</a>.
            </li>
            <li>
                <strong>Citing BioStudies submission in your manuscript</strong>: please include your experiment accession number and the URL to BioStudies home page,
                http://www.ebi.ac.uk/biostudies. e.g. "Data are available in the BioStudies database (http://www.ebi.ac.uk/biostudies) under accession number S-EPMC5090800."
            </li>
        </ul>



         <h3 class="icon icon-generic" data-icon="}">The team behind BioStudies</h3>

        <p class="justify">The BioStudies team is part of the <a href="/about/people/ugis-sarkans">Ugis Sarkans's
            team at EMBL-EBI</a>.</p>

        <p>The BioStudies team would like to thank the following companies for their support through the offering
            of free software licenses:</p>
        <ul>
            <li><a href="http://www.jetbrains.com">JetBrains</a> for the most intelligent Java IDE - <a href="http://www.jetbrains.com/idea/index.html" target="_blank">IntelliJ IDEA</a>.</li>
            <li><a href="http://www.atlassian.com">Atlassian</a> for the <a href="http://www.atlassian.com/software/bamboo/overview" target="_blank">Bamboo</a> - Continuous Integration and Release Management tool.</li>
            <li><a href="https://sprymedia.co.uk/">SpryMedia</a> for the <a href="https://datatables.net/" target="_blank">DataTables</a> jQuery plugin.</li>
        </ul>
        </div>
    </jsp:body>
</t:generic>

