<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<script>
    function hentRettEmne(emnekode) {
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                window.location = "godkjenningsoversikt.htm";
            }

        }
        xmlhttp.open("POST", "hentRiktigEmne", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("emne=" + emnekode);


    }
</script>

<label for="emne">Velg emne:</label>
<select id="emne" name="emne" class="form-control" onchange="hentRettEmne(this.value)">
    <option value="ingen"><i>Velg delemne</i></option>
    <c:forEach var="emne" items="${sessionScope.innloggetBruker.emne}">
        <c:forEach var="delemne" items="${emne.delemner}">
            <option value="${delemne.delEmneNavn}">
                <c:out value="${delemne.delEmneNavn}"/>
            </option>
        </c:forEach>
    </c:forEach>
</select>

<h2>
    <div class="text-info">
        Delemne: <c:out value="${sessionScope.ovingsoversikt[0].emne[0].delemner[0].delEmneNavn}"/>
    </div>
</h2>

<c:if test="${sessionScope.innloggetBruker.rettighet==2}">
    <form:form action="visAlleMedBestatt" method="post">
        <input class="btn btn-info" type="submit" value="Vis liste med alle eksamensklare">
    </form:form>
</c:if>

<table class="col-lg-10 table table-striped">
    <thead>
    <tr>
        <th>Student</th>
        <th></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="pers" items="${sessionScope.ovingsoversikt}" varStatus="personTeller">
        <tr>
            <td><c:out value="${pers.fornavn}"/>&nbsp;<c:out value="${pers.etternavn}"/>&nbsp;
                <c:if test="${pers.emne[0].delemner[0].ovingerBestatt}">
                    <button class="btn btn-success btn-sm active" title="Kandidat er klar til eksamen">
                        <span class="glyphicon glyphicon-ok"></span>
                    </button>
                </c:if>
            </td>

            <td>
                <c:forEach var="ovng" items="${pers.emne[0].delemner[0].studentovinger}" varStatus="nr">
                    <c:choose>
                        <c:when test="${ovng.godkjent}">
                            <button class='btn btn-success btn-sm active'
                                    title="Godkjent av ${ovng.godkjentAv} ${ovng.godkjentTid}">
                                    ${ovng.ovingnr}
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button class='btn btn-danger btn-sm active' title="Ikke godkjent">
                                    ${ovng.ovingnr}
                            </button>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
