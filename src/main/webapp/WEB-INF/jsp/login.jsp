<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>

<html>
<head>
    <meta charset="ISO-8859-1">
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/signin.css"/>" rel="stylesheet">
</head>
<body>
<div class="container">
    <form:form action="loggerinn.SSL" method="POST"  modelAttribute="bruker" class="form-signin">
        <h2 class="form-signin-heading" id="header">Logg inn</h2>
        <p style="color: green;"><strong>${nyPassord}</strong></p>

        <form:input type="text" class="form-control"  id="mailInput" path="mail" placeholder="Email" autofocus="true"/>
        <form:input type="password" class="form-control" path="passord" placeholder="Passord" id="passordInput"/>
        <!--<label class="checkbox">
            <input type="checkbox" value="husk"> Husk email
        </label> -->
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Logg inn"/>
        <a href="glemtPassord.htm">Glemt Passord</a>
    </form:form>


</div>

<script src="<c:url value="/resources/js/jquery-1.10.2.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>