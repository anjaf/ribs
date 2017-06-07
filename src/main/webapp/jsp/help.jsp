<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<t:generic>
    <jsp:attribute name="breadcrumbs">
        <ul class="breadcrumbs">
            <li><a href="${contextPath}/">BioStudies</a></li>
            <li>
                <span class="show-for-sr">Current: </span> Help
            </li>
        </ul>
    </jsp:attribute>
    <jsp:body>

        <h3>How to search BioStudies database</h3>

        <p class="justify">Use the Search box available in the top-right corner of every page. Enter any words that
            describe studies
            you are interested in. As you start typing, a drop-down list will appear suggesting terms that match.
            For terms that are in <a href="http://www.ebi.ac.uk/efo" target="_blank">EFO</a>
            (<a href="http://www.ebi.ac.uk/efo" target="_blank">Experimental Factor Ontology</a> - an EMBL-EBI
            resource providing systematic
            descriptions of biological samples and experimental variables), a button will be provided enabling
            expansion of more specific terms. Search terms are retained in the search box, where they can be refined
            (see the <a href="#advancedsearch">Advanced search</a> section below).</p>

        <p class="justify">The search results page is a list of matching biostudies sorted according to relevance.
            You can also change the sorting by using the <i>Sort by</i> selector. If there are many results,
            they will be split over multiple pages. Links at the top of the results allow you to change the number
            of studies displayed per page as well as the current result page. Clicking on the title of a study
            takes you to a more detailed page about that study.</p>

        <p class="justify">Within the results any matching terms are highlighted.
            <span class="highlight">Yellow</span> highlighting indicates
            exact matches, <span class="synonym">green</span> highlighting indicates synonyms,
            and <span class="efo">peach</span> highlighting indicates more specific
            matches (e.g. "carcinoma" as a more specific term for "cancer"). These more specific terms are from EFO.
        </p>

        <h3 id="advancedsearch">Advanced search</h3>

        <p class="justify">Each word in the query is treated as a separate term (unless surrounded by double
            quotes), and by default
            every result has to contain all the terms. This behaviour can be modified by using boolean operators and
            brackets; e.g., Leukemia AND ( mouse OR human ), or cancer AND NOT ( human ).</p>

        <p class="justify">Queries containing star or question mark characters are treated separately. A star
            character will match
            any combination of zero or more characters, e.g., leuk*mia will match to leukemia and leukaemia, as well
            as leukqwertymia. A question mark character will match any single characters, e.g., m?n will match both
            man and men. Queries that include wildcards are not expanded.</p>

    </jsp:body>
</t:generic>

