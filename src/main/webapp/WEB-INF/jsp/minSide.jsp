<%@ page import="no.hist.tdat.javabeans.Bruker" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-8">
    <h3><u>Mine Fag</u></h3>
<div class="row">
    <div class=" table-responsive">

        <table class="col-lg-10 table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>Emnekode</th>
                <th>Emnenavn</th>
                <c:if test="${sessionScope.innloggetBruker.rettighet == 3}">
                    <th>&Oslash;vingsoversikt</th>
                </c:if>
            </tr>
            </thead>
            <tbody>

            <form onsubmit="mysubmit()" method="POST" action="koOversikt.htm">
                <input type="hidden" name="delemneNr" id="delemneNr" />
                <input type="hidden" name="emneNr" id="emneNr" />
                <jsp:include page="populerMinSide.jsp" />
            </form>
            </tbody>
        </table>
    </div>
</div>
</div>
<script src="<c:url value="/resources/js/koen.js"/>"></script>
<script src="<c:url value="/resources/js/menyScript.js"/>"></script>

