<%--
  Created by IntelliJ IDEA.
  User: Thomas
  Date: 24.01.14
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-8">
    <h3>Legg til Emneansvarlig i emnet ${redigerEmne.emneNavn}</h3>
    <div class="form-group">
        <table class="table table-condensed table-hover">
            <thead>
            <tr>
                <th class="">Emneansvarlige</th>
                <th class="header col-sm-1"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${redigerEmne.foreleserListe}" var="foreleser">
            <tr>
                <td><p>${foreleser.etternavn}, ${foreleser.fornavn}</p>
                </td>
                <td>
                    <button type="button" class="btn btn-danger btn-sm" data-task="remove"
                            id="${foreleser.mail}" onclick="slettEmneansvarlig(this.id, this.value)" value="${redigerEmne.emneKode}"
                            title="Slett"><i class="glyphicon glyphicon-remove"></i>
                    </button>

                </td>
            </tr>
            </tbody>
            </c:forEach>
        </table>
    </div>
    <form:form class="søkbar" role="search" modelAttribute="personerBeans" action="sokBoks" method="POST">
    <div class="input-group">

        <input type="text" class="form-control" placeholder="S&oslash;k" name="srch-term" id="srch-term"/>

        <div class="input-group-btn">
            <button class="btn btn-success" type="submit"><i class="glyphicon glyphicon-search"></i>
            </button>
        </div>

    </div>
    <div class="span5">
        <table class="table table-condensed table-hover" id="minTable">
            <thead>
            <tr>
                <th class="header">Fornavn</th>
                <th class="header">Etternavn</th>
                <th class="header">Epost</th>
                <th class="header col-sm-1">Velg</th>
                <th class="header col-sm-1"></th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="bruker" items="${personerBeans.valgt}" varStatus="status">
                <tr>
                    <td><c:out value="${bruker.fornavn}"/></td>
                    <td><c:out value="${bruker.etternavn}"/></td>
                    <td><c:out value="${bruker.mail}"/></td>
                    <td>
                        <button type="edit" class="btn btn-success btn-sm" data-toggle="modal"
                                id="${bruker.mail}" value="${redigerEmne.emneKode}" onclick="leggTilEmneansvarlig(this.id, this.value)"
                                title="Velg">
                            Velg
                        </button>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        </form:form>
    </div>
    <div class="modal-footer">
        <button type="button" id="${redigerEmne.emneKode}" onclick="adminEmneEmneAnsTilbake(this.id)"
                class="btn btn-danger btn-block">Tilbake
        </button>
    </div>
</div>
<script src="<c:url value="/resources/js/admin.js"/>"></script>

