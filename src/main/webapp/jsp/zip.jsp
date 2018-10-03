<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<t:generic>
     <jsp:attribute name="head">
        <jwr:script src="/js/zip.min.js"/>
    </jsp:attribute>
    <jsp:attribute name="breadcrumbs">
        <ul class="breadcrumbs">
            <li><a href="${contextPath}/">BioStudies</a></li>
            <li>
                <span class="show-for-sr">Current: </span> Download
            </li>
        </ul>
    </jsp:attribute>
    <jsp:body>
        <div style="text-align: center">
            <h3>Your download is being prepared.</h3>
            <p class="center">Once the download is ready, a link will appear below which you can click to start the download. <br/>
                This link will be available for 24 hours.</p>
            <p/>
            <form id="variables" action="" method="post">
                <input type="hidden" id="filename" />
                <input type="hidden" id="dc" />
                <input type="hidden" id="accession" />
            </form>
            <p id="ftp-link"><img src="${contextPath}/images/busy.gif"/></p>
        </div>
        <script src="${contextPath}/js/zip/zip.js"></script>
    </jsp:body>
</t:generic>

