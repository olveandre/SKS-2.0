<%--
  Created by IntelliJ IDEA.
  User: olve og gm, ass
  Date: 13.01.14
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="col-md-6">
    <h2>Godkjenn øvinger</h2>

    <form:form action="godkjennGruppeOving.htm">
        <p>Kommentar: ${gruppeFraKoe.getKommentar()}</p>
        <table class="table table-condensed" id="godkjennGruppe">
            <thead>
            <tr>
                <th class="header">Fornavn</th>
                <th class="header">Etternavn</th>
                <th class="header">&Oslash;vinger</th>
                <th class="header col-sm-1"></th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="bruker" items="${gruppeFraKoe.getMedlemmer()}" varStatus="status">
                <tr>
                    <td><c:out value="${bruker.fornavn}"></c:out> </td>
                    <td><c:out value="${bruker.etternavn}"></c:out> </td>
                    <td><c:out value="${gruppeFraKoe.getOvingerIString()}"></c:out> </td>
                    <td>
                        <div class="input-group-btn">
                            <button type="edit" class="btn btn-warning btn-sm" data-toggle="modal"
                                    id="${bruker.mail}" onclick="redigerBrukerFraKnapp(this.id)"title="Endre">
                                <i class="glyphicon glyphicon-edit"></i></button>
                            <button type="button" value="Slett" class="btn btn-danger btn-sm" data-task="remove"
                                    id="${bruker.mail}" onclick="slettBrukerFraKnapp(this.id)"
                                    title="Slett"><i class="glyphicon glyphicon-remove"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


    <div class="btn-group">
        <button type="submit" class="btn btn-success" name="godkjennKnapp"><i class="glyphicon glyphicon-ok"></i> Godkjenn</button>

        <button type="submit" class="btn btn-primary" name="leggTilStudentModal" data-toggle="modal" data-target="#leggTilStudentModal"><i class="glyphicon glyphicon-user"></i> Legg til studenter
        </button>

        <button type="submit" class="btn btn-info" name="endreOvingerKnapp"><i
                class="glyphicon glyphicon-plus"></i> Endre øvinger
        </button>

        <button type="submit" class="btn btn-danger" name="tilbakeKnapp">Lukk</button>
    </div>
        <div class="modal fade" id="leggTilStudentModal" tabindex="-1" role="dialog" aria-labelledby="leggTilStudent"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title" id="leggTilStudentHeader">Legg til student</h2>
                    </div>
                    <div class="modal-body">
                        <div class="btn-group">
                            <select class="form-control" name="dropdown" id="leggTilStudentIGruppe">

                                <option value="ingenValgte">Ingen valgte</option>
                                <c:forEach var="brukerValgt" items="${flereStudenter}" varStatus="status">
                                <option value="${brukerValgt.mail}">${brukerValgt.etternavn}, ${brukerValgt.fornavn}</option>
                                </c:forEach>

                            </select>
                            <br/>
                            <table class="table table-condensed">
                                <thead>
                                <tr>
                                    <th class="header">Fornavn</th>
                                    <th class="header">Etternavn</th>
                                    <th class="header col-sm-1"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="leggTilBrukere" items="" varStatus="status">
                                <tr>
                                    <td><c:out value="${leggTilBrukere.fornavn}"></c:out> </td>
                                    <td><c:out value="${leggTilBrukere.etternavn}"></c:out> </td>
                                    <td>
                                        <button type="button" value="Slett" class="btn btn-danger btn-sm" data-task="remove"
                                                id="${leggTilBrukere.mail}"
                                                title="Slett"><i class="glyphicon glyphicon-remove"></i>
                                        </button>
                                    </td>
                                </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Lukk</button>
                        <button type="button" class="btn btn-primary">Lagre</button>
                    </div>
                </div>
            </div>
        </div>

    </form:form>


</div>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
