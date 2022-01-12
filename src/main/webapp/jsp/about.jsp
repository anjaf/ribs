<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<t:generic>
    <jsp:attribute name="head">
        <jwr:script src="/js/common.min.js"/>
    </jsp:attribute>
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
                structured archives at EMBL-EBI. The database accepts submissions via an online tool, or in a simple
                tab-delimited format.
                It also enables authors to submit supplementary information and link to it from the publication.
            </p>

            <h3 class="icon icon-generic" data-icon="P">Publications / How to cite</h3>
            <ul>
                <li>Ugis Sarkans, Mikhail Gostev, Awais Athar, Ehsan Behrangi, Olga Melnichuk, Ahmed Ali, Jasmine
                    Minguet, Juan Camillo Rada, Catherine Snow, Andrew Tikhonov, Alvis Brazma, Johanna McEntyre; <a
                            href="https://doi.org/10.1093/nar/gkx965" target="_blank">The BioStudies database—one stop
                        shop for all data supporting a life sciences study</a>, Nucleic Acids Research, 2017.
                </li>
            </ul>


            <h3 class="icon icon-generic" data-icon="}">The team behind BioStudies</h3>

            <p class="justify">The BioStudies team is part of <a href="https://www.ebi.ac.uk/about/people/ugis-sarkans">Ugis
                Sarkans'
                team</a>.</p>
            <p class="justify">The ArrayExpress collection and Annotare are maintained by <a
                    href="https://www.ebi.ac.uk/about/people/irene-papatheodorou">Irene Papatheodorou's team</a>.</p>


            <h3 class="icon icon-generic" data-icon="I">Acknowledgments</h3>

            <p>The BioStudies team would like to thank the following companies for their support through the offering
                of free software licenses:</p>
            <ul>
                <li><a href="http://www.atlassian.com">Atlassian</a> for the <a
                        href="http://www.atlassian.com/software/bamboo/overview" target="_blank">Bamboo</a> - Continuous
                    Integration and Release Management tool.
                </li>
                <li><a href="https://sprymedia.co.uk/">SpryMedia</a> for the <a href="https://datatables.net/"
                                                                                target="_blank">DataTables</a> jQuery
                    plugin.
                </li>
            </ul>
        </div>
    </jsp:body>
</t:generic>

