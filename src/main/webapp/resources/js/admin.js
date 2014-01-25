/*
 AdminFag
 */

function adminFagOperasjon(valgt) {
    if (document.getElementById("emnevalg").value !== "ingen") {
        document.getElementById("operasjonstekst").innerHTML = "Endre " + valgt + "";
    }
    else {
        document.getElementById("operasjonstekst").innerHTML = "Lag nytt fag";
    }
}
function endreEmne(valgt) {

    if (document.getElementsByTagName("edit").value !== "ingen") {
        document.getElementById("operasjonstekst").innerHTML = "Endre bruker";
        document.getElementById("leggtilknapp").innerHTML = "Lagre endring";

    }
    else {
        document.getElementById("operasjonstekst").innerHTML = "Legg til bruker";
        document.getElementById("leggtilknapp").innerHTML = "Legg til";
    }
}


function slettEmne(valgt) {
    if (typeof(valgt) == "object") {
        $(valgt).closest("tr").remove();
        alert("Slett emne?")
    } else {
        return false;
    }
}


function slettEmneFraKnapp(emnekode) {
    var svar = confirm("Slette emnet " + emnekode + "?");
    if (svar) {
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5

            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {

            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                window.location = "searchFag.htm"

            }
        }
        xmlhttp.open("POST", "/slettEmne.htm", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("emneIndex=" + emnekode);


    }
}

function redigerEmneFraKnapp(emnekode) {

    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            window.location = "adminEmneEndre.htm";

        }
    }
    xmlhttp.open("POST", "/redigerEmne.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("emneIndex=" + emnekode);


}

function adminEmneTilbake(emnekode) {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            window.location = "searchFag.htm"
        }
    }
    xmlhttp.open("POST", "/searchFag.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("emneIndex=" + emnekode);

}
function velgBrukerL(mail) {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            window.location = "velgBruker.htm";
        }
    }
    xmlhttp.open("POST", "/velgBruker.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("brukerIndex=" + mail);


}

function visLeggTilEmneansv(emnekode) {

    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        }
    }
    xmlhttp.open("POST", "/redigerEmne.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("emneIndex=" + emnekode);
    window.location = "/leggTilEmneAnsView.htm";

}
function leggTilEmneansvarlig(mail, emneKode) {
    if (confirm("Legge til " + mail + " som emneansvarlig?")) {
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5

            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {

        }
        xmlhttp.open("POST", "/velgBruker.htm", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("brukerIndex=" + mail + "&emneIndex=" + emneKode);
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        }
        window.location = "/leggTilEmneAnsView.htm";
    }
}
function slettEmneansvarlig(mail, emneKode) {
    if (confirm("Fjern " + mail + " som emneansvarlig?")) {
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5

            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                window.location="adminEmneEndre.htm";
            }
        }
        xmlhttp.open("POST", "/slettEmneAnsv.htm", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("brukerIndex=" + mail + "&emneIndex=" + emneKode);
    }
}
function adminEmneEmneAnsTilbake(emnekode) {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            window.location = "adminEmneEndre.htm";
        }
    }
    xmlhttp.open("POST", "/redigerEmne.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("emneIndex=" + emnekode);
}
function leggtilDelEmne(emnekode) {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "/redigerEmne.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("emneIndex=" + emnekode);
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }

    window.location = "delEmne.htm";
}
/*
 AdminBrukere
 */
function handleFileSelect(hendelse) {
    var minFil = hendelse.target.files[0];
    infoStreng = '<ul><li>Navn: ' + minFil.name + '</li>' +
        '<li>St&oslash;rrelse: ' + minFil.size + 'bytes</li>' +
        '<li>Type: ' + minFil.type + '</li></ul>';

    document.getElementById('filInfo').innerHTML = infoStreng;
}
/*
 <fieldset disabled><fieldset disabled>
 <fieldset disabled>
 <fieldset disabled>
 <fieldset disabled>

 */
function endreBruker(valgt) {

    if (document.getElementsByTagName("edit").value !== "ingen") {
        document.getElementById("operasjonstekst").innerHTML = "Endre bruker";
        document.getElementById("leggtilknapp").innerHTML = "Lagre endring";

    }
    else {
        document.getElementById("operasjonstekst").innerHTML = "Legg til bruker";
        document.getElementById("leggtilknapp").innerHTML = "Legg til";
    }
}


function slettBruker(valgt) {
    if (typeof(valgt) == "object") {
        $(valgt).closest("tr").remove();
        alert("Slett bruker?")
    } else {
        return false;
    }
}


function slettBrukerFraKnapp(mail) {
    if (confirm("Sikker på at du vil slette " + mail + "?")) {
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5

            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {

        }
        xmlhttp.open("POST", "/slettBruker.htm", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("brukerIndex=" + mail);

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        }

        window.location = "search.htm"

    }

}

function redigerBrukerFraKnapp(mail) {

    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "/redigerBruker.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("brukerIndex=" + mail);
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }

    window.location = "adminBrukereEndre.htm";

}

function adminBrukereTilbake(mail) {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "/search.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("brukerIndex=" + mail);
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }
    window.location = "search.htm"
}

