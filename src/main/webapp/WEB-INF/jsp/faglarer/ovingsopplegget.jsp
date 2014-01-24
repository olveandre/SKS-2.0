
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="col-md-4">
    <h1>Sett regler for &oslash;vingsopplegget</h1>

    <form:form method="POST" modelAttribute="delemne" action="ovingsopplegget">
        <h3>Grunnleggende</h3>

        <div class="form-group">
            <label for="tot">Totalt antall &oslash;vinger:</label>
            <input class="form-control" name="antall" type="number" id="tot" value="1" min="1" oninput="settMaxKrav()">
        </div>

        <div class="form-group">
            <label for="ant">Minimum godkjente:</label>
            <input type="number" name="min" id="ant" value="1" min="0" max="1" class="form-control">
        </div>


        <h3>Avansert (obligatoriske arbeidskrav)</h3>



            <input type="radio" name="visMer" value="aktiver" id="visMerAkt" onclick="utvid();">
            <lable for="visMerAkt">Aktiver</lable>
            <input type="radio" name="visMer" value="deaktiver" id="visMerDAkt" onclick="utvid();" checked>
            <lable for="visMerDAkt">Deaktiver</lable>


        <div id="visMer" style="display:none">
            <hr>

            <h4>Sett opp reglement for &oslash;vingssystemet.</h4>

                <h3>&Oslash;vinger</h3>



                <div class="form-group">
                    <select multiple id="ovingValg" onchange="settMaxKrav2()" class="form-control">
                        <option value="oving1" selected="selected">oving1</option>
                    </select>

                    <h3>Minst godkjente</h3>

                    <input class="form-control" type="number" id="antGodkjente" value="1" min="1">
                    <input class="btn" type="button" value="Legg til regel" onclick="leggTilRegelen()">
                </div>


            <p><i>Sl&aring; sammen flere &oslash;vinger ved &aring; hold inne "CTRL"-tasten mens du
                trykker p&aring; &oslash;nsket &oslash;ving,<br>eller hold inn "SHIFT"-tasten for &aring; automatisk
                velge alle &oslash;vingene til og
                med
                den
                du trykker p&aring;.<br>Trykk deretter "legg til". Flere regler kan legges til etterp&aring;.</i>
            </p>

            <h3>Innlagte spesialregler</h3>

            <div id="regler"><i>ingen</i></div>
            <input type="hidden" name="newText" id="newText"/>
        </div>
        <div id="test"></div>

        <div class="btn-input-group">
            <input type="submit"  id="ovingsopplegg" value="Opprett regler" class="btn btn-primary btn-block"/>
        </div>
    </form:form>
</div>
<script src="<c:url value="/resources/js/ovingsopplegg.js"/>"></script>

