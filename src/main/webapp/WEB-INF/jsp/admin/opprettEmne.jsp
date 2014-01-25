<%--
  Created by IntelliJ IDEA.
  User: Eirik
  Date: 20.01.14
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form method="POST" modelAttribute="emne" action="lagEmne">
    <h2>Opprett emne</h2>

    <p style="color: red"><strong>${emnefeil}</strong></p>

    <p style="color: red"><strong>${emneSQLfeil}</strong></p>

    <div class="form-group">
        <label for="emneNavn">Emnenavn:</label>
        <form:input path="emneNavn" id="emneNavn" class="form-control" required="true"/>
    </div>
    <div class="form-group">
        <label for="emneKode">Emnekode:</label>
        <form:input path="emneKode" id="emneKode" class="form-control" required="true"/>
    </div>

    <input type="submit" class="btn btn-primary btn-block" value="Legg til"/>
    <br>
</form:form>

<script src="<c:url value="/resources/js/admin.js"/>"></script>
