<%@ page import="no.hist.tdat.javabeans.Emne" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eirik
  Date: 20.01.14
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-6">
    <p style="color: green"><strong>${emnerett}</strong></p>

    <h3>Legg til delemne for emnet  <strong>${redigerEmne.emneNavn}</strong></h3>

    <form:form method="POST" modelAttribute="delemne" action="lagDelemne">

        <p style="color: red"><strong>${delemnefeil}</strong></p>

        <p style="color: red"><strong>${delemneSQLfeil}</strong></p>

        <div class="form-group">
            <label for="delEmneNavn">Delemnenavn:</label>
            <form:input path="delEmneNavn" id="delEmneNavn" class="form-control" required="true"/>
        </div>
        <div class="form-group">
            <label for="nr">Delemnenr:</label>
            <form:input path="nr" id="nr" class="form-control" required="true"/>
        </div>
        <div class="form-group">
            <label for="semester">Semester:</label>

            <form:radiobutton path="semester" id="semester" class="btn-primary btn" value="h"/>Høst&nbsp;&nbsp;
            <form:radiobutton path="semester" id="semester" class="btn-primary btn" value="v"/>Vår

        </div>
        <div class="btn-input-group">
            <input type="submit" class="btn btn-primary btn-block" value="Legg til"/>
        </div>
    </form:form>
</div>