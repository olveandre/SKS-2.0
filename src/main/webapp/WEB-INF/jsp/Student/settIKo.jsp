<%@ page import="no.hist.tdat.javabeans.Bruker" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="no.hist.tdat.javabeans.beanservice.BrukerService" %>
<%@ page import="no.hist.tdat.javabeans.Oving" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="col-md-6">
    <h1>Sett deg i k&oslash; (${delEmne.delEmneNavn})</h1>

    <form:form action="StillIKo.htm" onsubmit='mysubmit()' modelAttribute="koegrupper" method="post" id="formStillIKo">
        <p style="color: red"><strong>${melding}</strong></p>
        <p style="color: green"><strong> ${Vellykket}</strong></p>

        <div class="form-group">
            <label for="sitteplass">Sitteplass:</label>
            <form:select class="form-control" name="Sitteplass" id="sitteplass" path="sitteplass"
                         onchange="hentBord(this)">
                <option id="tom" value="tom">Velg Sitteplass</option>
                <c:forEach items="${plassering}" var="plass">
                    <form:option onclick='visBilde(this.value)' id="${plass.ant_bord}"
                                 value="${plass.plassering_navn}">${plass.plassering_navn}</form:option>
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
            <form:select id="oving" multiple="true" class="form-control" path="ovingnr">
                <c:forEach items="${oving}" var="ovinger">
                    <form:option id="${ovinger.ovingnr}"
                                 value="${ovinger.ovingnr}">&Oslash;ving ${ovinger.ovingnr}</form:option>
                </c:forEach>
            </form:select>
        </div>

        <div class="form-group">
            <label for="kommentar">Kommentar:</label>
            <form:textarea class="form-control" type="text" id="kommentar" path="kommentar" maxlength="20"/>
        </div>

        <div class="form-group">
            <label for="gruppe">Gruppe?</label>
            <form:select id="gruppe" path="medlemmer" class="form-control">
                <c:forEach items="${personerBeans.valgt}" var="bruker">
                    <form:option value="${bruker.mail}">${bruker.etternavn}, ${bruker.fornavn}</form:option>
                </c:forEach>
            </form:select>
        </div>
        <input type="hidden" name="delemneNr" id="delemneNr"/>
        <input type="hidden" name="emneNr" id="emneNr"/>

        <div class="modal-footer">

            <input onclick='delemnenr="${delEmneIndex}"; emnenr="${emneIndex}"; tilKoe();/*mysubmit();*/'
                   value="Tilbake"
                   class="btn btn-md btn-danger col-md-5">

            <input type="submit" id="leggTil" onclick='delemnenr="${delEmneIndex}"; emnenr="${emneIndex}"'
                   class="btn btn-md btn-primary col-md-5" value="Legg til i k&oslash;">
        </div>
    </form:form>
</div>
<div id="bilde">

</div>
<script src="<c:url value="/resources/js/koen.js"/>"></script>
<%-- Svart ramme rundt bildet --%>
<%-- style="float:right"   align="right" --%>
<%--  <c:forEach var="bruker" items="${personerBeans.valgt}" varStatus="status">  --%>
