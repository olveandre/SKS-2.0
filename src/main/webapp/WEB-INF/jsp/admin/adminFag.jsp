<%--
  Created by IntelliJ IDEA.
  User: Thomas
  Date: 09.01.14
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="col-lg-10">
    <ul class="nav nav-tabs nav-justified">
        <li id="lenkeEndre" class="active"><a href="#endre" data-toggle="tab">Adm. Emne</a></li>
        <li id="lenkeleggTilEnkelEmne"><a href="#leggTilEmne" data-toggle="tab">Legg til Emne</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane fade in active" id="endre">
            <h2>Administrer Emne</h2>
            <form:form class="søkbar" role="search" modelAttribute="emnerBeans" action="searchFag.htm" method="POST">
            <div class="input-group">

                <input type="text" class="form-control" placeholder="S&oslash;k" name="srch-term" id="srch-term">

                <div class="input-group-btn">
                    <button class="btn btn-success" type="submit"><i class="glyphicon glyphicon-search"></i>
                    </button>
                </div>
            </div>
            <p style="color: red">${message}</p>
            <div class="span5">
                <table class="table table-condensed table-hover" id="minTable">
                    <thead>
                    <tr>
                        <th class="header col-sm-2">Emnekode</th>
                        <th class="header col-sm-4">Emnenavn</th>
                        <th class="header">Emneansvarlig</th>
                        <th class="header col-sm-1"></th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="emne" items="${emnerBeans.valgt}" varStatus="status">
                        <tr>
                            <td><c:out value="${emne.emneKode}"/></td>
                            <td><c:out value="${emne.emneNavn}"/></td>
                            <td><c:out value="${emne.forelesereTilString()}"/></td>
                            <td>
                                <div class="input-group-btn">
                                    <button type="edit" class="btn btn-warning btn-sm" data-toggle="modal"
                                            id="${emne.emneKode}" onclick="redigerEmneFraKnapp(this.id)"
                                            title="Endre">
                                        <i class="glyphicon glyphicon-edit"></i></button>
                                    <button type="button" value="Slett" class="btn btn-danger btn-sm" data-task="remove"
                                            id="${emne.emneKode}" onclick="slettEmneFraKnapp(this.id)"
                                            title="Slett"><i class="glyphicon glyphicon-remove"></i>
                                    </button>

                                </div>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
                <input type="hidden" name="tab" value="endre">

                </form:form>
            </div>
        </div>
        <div class="tab-pane fade in" id="leggTilEmne">
            <jsp:include page="opprettEmne.jsp"/>
        </div>

    </div>
</div>
<script src="<c:url value="/resources/js/admin.js"/>"></script>
