<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<t:generic>
    <jsp:attribute name="breadcrumbs">
        <ul class="breadcrumbs">
            <li><a href="${contextPath}/">BioStudies</a></li>
            <li>
                <span class="show-for-sr">Current: </span> Error
            </li>
        </ul>
    </jsp:attribute>
    <jsp:body>
        <section id="error-message">
            <div class="bigicon"><i class="icon icon-functional" data-icon="j"></i></div>
            <br/>
            <h3>Page not found!</h3>
            <p>The resource you were looking for was not found on the server.</p>
            <p>If you require further assistance locating missing page or file, please <a
                    href="mailto://biostudies@ebi.ac.uk" class="feedback">contact us</a> and we will look into it
                for you.</p>
        </section>
    </jsp:body>
</t:generic>

