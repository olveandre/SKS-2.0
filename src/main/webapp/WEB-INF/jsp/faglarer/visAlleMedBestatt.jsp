<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h1>
    Liste over alle studenter som har nok øvinger i ${sessionScope.ovingsoversikt[0].emne[0].delemner[0].delEmneNavn}
</h1>

<c:forEach var="pers" items="${sessionScope.ovingsoversikt}" varStatus="personTeller">
    <c:if test="${pers.emne[0].delemner[0].ovingerBestatt}">
        <c:out value="${pers.mail}"/>, <c:out value="${pers.fornavn}"/>&nbsp;<c:out value="${pers.etternavn}"/><br>
    </c:if>
</c:forEach>

<form:form action="godkjenningsoversikt.htm" method="POST">
    <input class="btn btn-warning" type="submit" value="Tilbake">
</form:form>