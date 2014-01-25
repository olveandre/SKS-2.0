<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Endre student</h1>
<form:form action="endreValgtStudent" method="post">
    <input type="submit" class="btn btn-warning" value="Tilbake" name="tilbake"/>
</form:form>
<table class="table table-condensed table-hover" id="minTable">
    <thead>
    <tr>
        <th class="header">Fornavn</th>
        <th class="header">Etternavn</th>
        <th class="header">Epost</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td><c:out value="${sessionScope.valgtPerson.fornavn}"/></td>
            <td><c:out value="${sessionScope.valgtPerson.etternavn}"/></td>
            <td><c:out value="${sessionScope.valgtPerson.mail}"/></td>
        </tr>
    </tbody>
</table>
<hr>
<div class="text-info">
    <h3><c:out value="${forrigeOp}"/></h3>
</div>

<form:form action="utførOperasjon" method="post">
    <table class="table table-condensed">
        <tbody>
        <tr>
            <td>
                <div class="form-group">
                        <div class="form-group">
                            <label for="emner">Gjør til studentassistent i valgte fag:</label>
                            <select id="emner" name="emner" class="form-control">
                                <c:forEach var="emne" items="${sessionScope.innloggetBruker.emne}">
                                    <c:forEach var="delemne" items="${emne.delemner}">
                                        <option value="${delemne.delEmneNavn}"><c:out value="${delemne.delEmneNavn}"/></option>
                                    </c:forEach>
                                </c:forEach>
                            </select>
                        </div>
                    <input type="submit" class="btn btn-success" value="Lagre" name="lagre"/>
                </div>
            </td>

            <td>
                <div class="form-group">
                    <div class="form-group">
                        <label for="emner4">Fjern som studentassistent i valgte delemne:</label>
                        <select id="emner4" name="emner4" class="form-control">
                            <c:forEach var="emne" items="${sessionScope.innloggetBruker.emne}">
                                <c:forEach var="delemne" items="${emne.delemner}">
                                    <option value="${delemne.delEmneNavn}"><c:out value="${delemne.delEmneNavn}"/></option>
                                </c:forEach>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="submit" class="btn btn-danger" value="Fjern" name="fjernStudass"/>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <table class="table table-condensed">
        <tbody>
        <tr>
            <td>
                <div class="form-group">
                    <div class="form-group">
                        <label for="emner3">Gi tilgang til valgte emne:</label>
                        <select id="emner3" name="emner3" class="form-control">
                            <c:forEach var="emne" items="${sessionScope.innloggetBruker.emne}" varStatus="status">
                                <option value="${emne.emneKode}"><c:out value="${emne.emneKode}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="submit" class="btn btn-success" value="Legg til" name="leggTil"/>
                </div>
            </td>

            <td>
                <div class="form-group">
                    <div class="form-group">
                        <label for="emner2">Fjern tilgang til valgte emne:</label>
                        <select id="emner2" name="emner2" class="form-control">
                            <c:forEach var="emne" items="${sessionScope.innloggetBruker.emne}" varStatus="status">
                                <option value="${emne.emneKode}"><c:out value="${emne.emneKode}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="submit" class="btn btn-danger" value="Fjern" name="fjern"/>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>

