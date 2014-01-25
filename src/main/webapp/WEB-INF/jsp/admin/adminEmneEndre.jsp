<%--
  Created by IntelliJ IDEA.
  User: Thomas
  Date: 23.01.14
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6">
    <h3>Administrer <strong>${redigerEmne.emneNavn}</strong></h3>
    <form:form method="POST" modelAttribute="emne" action="redigerEmneLagre.htm">
        <p style="color: red"><strong>${melding}</strong></p>
        <p style="color: green"><strong>${delemnerett}</strong></p>
        <p style="color: green"><strong>${Vellykket}</strong></p>


        <div class="form-group">
            <label for="endrekode">Emnekode</label>
            <form:input value="${redigerEmne.emneKode}" path="emneKode" id="endrekode" class="form-control"/>
            <errors path="emneKode"/>
        </div>
        <div class="form-group">
            <label for="endreNavn">Emnenavn</label>
            <form:input value="${redigerEmne.emneNavn}" path="emneNavn" id="endreNavn" class="form-control"/>
            <errors path="emneNavn"/>
        </div>
        <input type="hidden" name="redigerEmneKode" value="${redigerEmne.emneKode}">
        <input type="submit" id="endreEmne" value="Endre emneinfo" class="btn btn-primary"/>
        <input type="hidden" name="tab" value="endre">
    </form:form>
    <hr>
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
    <hr>
    <button onclick="visLeggTilEmneansv(this.id)" id="${emne.emneKode}" value="leggtilEmneansvarlig" class="btn btn-primary btn-sm">Legg til emneansvarlig</button>

    <%--    <% if () { %>

        <div class="form-group">
            <table>
                <thead>
                <tr>
                    <th class="">Delemner</th>
                    <th class="header col-sm-1"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${redigerEmne.delemner}" var="delemner">
                <tr>
                    <td><p>${delemner.delEmneNavn}, ${delemner.delEmneNavn}</p>
                    </td>
                    <td>
                        <button type="button" value="Slett" class="btn btn-danger btn-sm" data-task="remove"
                                id="${delemner.emneKode}" onclick="slettDelEmne(this.id)"
                                title="Slett"><i class="glyphicon glyphicon-remove"></i>
                        </button>

                    </td>
                </tr>
                </tbody>
                </c:forEach>
            </table>
            <button onclick="leggTilDelEmne(${redigerEmne.emneKode})" class="btn btn-primary">Legg til Delemne</button>
        </div>
        <% } %>--%>
    <button class="btn btn-primary btn-sm" id="${redigerEmne.emneKode}" onclick="leggtilDelEmne(this.id)">Legg til delemne</button>
    <hr>
    <div class="modal-footer">
        <button type="button" id="${redigerEmne.emneKode}" onclick="adminEmneTilbake(this.id)"
                class="btn btn-danger btn-block">Tilbake
        </button>
    </div>

</div>
<script src="<c:url value="/resources/js/admin.js"/>"></script>
