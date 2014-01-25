<%@ page import="no.hist.tdat.javabeans.DelEmne" %>
<%--
  Created by IntelliJ IDEA.
  User: Thomas
  Date: 10.01.14
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="pull-left col-lg-10" onload="sjekkAktivKoe(${delEmne.koe_status});">

    <h3>
        <div id="operasjonstekst">
            K&oslash; for <c:out value="${delEmne.delEmneNavn}"/>
        </div>
        <div id="koeStatus" style="float: right">
            <c:if test="${delEmne.koe_status}">
                <p style="color: green">(Aktivert)</p>
            </c:if>
            <c:if test="${!delEmne.koe_status}">
                <p style="color: red">(Deaktivert)</p>
            </c:if>
        </div>
    </h3>

    <div class="input-group">
        <c:if test="${sessionScope.innloggetBruker.emne[emneIndex].foreleser==0 && delEmne.koe_status && !sessionScope.koe.inneholderBruker(sessionScope.innloggetBruker)}">
            <form action="settIKo.htm" onsubmit="mysubmit()" method="POST">
                <input type="hidden" name="emneNr" id="emneNr"/>
                <input type="hidden" name="delemneNr" id="delemneNr"/>

                <%--<div class="btn-group">--%>
                <input type="submit" class="btn btn-sm btn-primary" id="stillIKo"
                       onclick="delemnenr=${delEmneIndex};emnenr=${emneIndex}" value="Still i k&oslash;"/>
                <%--</div>--%>
            </form>
        </c:if>
        <c:if test="${sessionScope.innloggetBruker.emne[emneIndex].foreleser==1}">
            <div id="startStoppKoe">
                <c:if test="${sessionScope.innloggetBruker.emne[emneIndex].delemner[delEmneIndex].koe_status}">
                    <input type="button" id="startStoppKnapp" onclick="startStoppKoe(${delEmne.koe_id})" class="btn btn-sm btn-danger" id="stoppKoe" value="Stopp sks">
                </c:if>
                <c:if test="${!sessionScope.innloggetBruker.emne[emneIndex].delemner[delEmneIndex].koe_status}">
                    <input type="button" id="startStoppKnapp" onclick="startStoppKoe(${delEmne.koe_id})" class="btn btn-sm btn-success" id="startKoe" value="Start sks">
                </c:if>

            </div>
        </c:if>
        </div>
    <div id="koetabell">
        <tbody>
        <jsp:include page="oppdaterKoe.jsp"/>
    </div>
</div>
<script src="<c:url value="/resources/js/koen.js"/>"></script>
<script src="<c:url value="/resources/js/oppdaterKoe.js"/>"></script>
