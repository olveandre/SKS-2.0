<%@ page import="no.hist.tdat.javabeans.Bruker" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="no.hist.tdat.javabeans.beanservice.BrukerService" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="col-md-4">
    <h1>Sett deg i k&oslash; </h1>

    <form:form action="StillIKo" modelAttribute="koegrupper" method="post">
        <div class="form-group">
            <label for="sitteplass">Sitteplass:</label>
            <form:select class="form-control" name="Sitteplass" id="sitteplass" path="sitteplass" onchange="hentBord()">
                <option id="tom" value="tom">Velg Sitteplass</option>
                <c:forEach items="${plassering}" var="plass">
                    <form:option onclick="visBilde(this.value)" id="plassering"
                                 value="${plass}">${plass.plassering_navn}</form:option>
                </c:forEach>
            </form:select>
        </div>


        <div class="form-group">
            <label for="bordnr">Bordnr:</label>
            <form:select class="form-control" name="Bord" id="bordnr" path="bordnr" disabled="true">
                <%--Her må det være noe som går gjennom de forskjellige bordalternativene etter hva som er blitt
                valgt på "sitteplass"--%>
               <%-- <c:forEach begin="1"  var="bordNr" end="${plassering.ant_bord}">
                    <form:option value="bordNr">bordNr</form:option>
                </c:forEach>--%>

            </form:select>
        </div>
        <div class="form-group">
            <label for="oving">&Oslash;ving:</label>
            <form:select id="oving" multiple="true" class="form-control" path="ovinger">
                <c:forEach items="${oving}" var="ovinger">
                    <form:option id="${ovinger.ovingnr}" value="${ovinger.ovingnr}">${ovinger.ovingnr}</form:option>
                </c:forEach>
            </form:select>
        </div>

        <div class="form-group">
            <label for="kommentar">Kommentar:</label>
            <form:textarea class="form-control" type="text" id="kommentar" path="kommentar"/>
        </div>

        <div class="form-group">
            <label for="gruppe">Gruppe?</label>
            <form:select id="gruppe" path="medlemmer" class="form-control">
                <c:forEach items="${personerBeans.valgt}" var="bruker">
                    <form:option value="${bruker.mail}">${bruker.etternavn}, ${bruker.fornavn}</form:option>
                </c:forEach>
            </form:select>
        </div>
        <input type="submit" id="leggTil" class="btn btn-md btn-primary" onclick="StillIKo(${delEmne.nr})" value="Legg til i k&oslash;">
    </form:form>
</div>
<div id="bilde">

</div>
<script src="<c:url value="/resources/js/koen.js"/>"></script>
<%-- Svart ramme rundt bildet --%>
<%-- style="float:right"   align="right" --%>
<%--  <c:forEach var="bruker" items="${personerBeans.valgt}" varStatus="status">  --%>
