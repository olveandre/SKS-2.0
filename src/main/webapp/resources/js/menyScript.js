function emnekodeFraMenu(kode) {

    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "emneOversikt.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("emnekodeFraNav=" + kode);

    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }
    window.location ="emneOversikt.htm";
}

function redigerDelEmneFraKnapp (emnekode, gruppeID){
    alert("rediger fungerer");
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "/redigerDelEmne.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("emnekode=" + emnekode+"&gruppeID="+gruppeID);

    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }
    window.location ="redigerDelEmne.htm";

}

function slettDelEmneFraKnapp (emnekode, delemnenr){
    alert("Denne funksjonen er under utvikling.\n\n Du må oppdatere siden for at effektet skal vises, og alle andre på logge ut og inn")
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "/slettDelEmne.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("emnekode=" + emnekode+"&delEmneNr="+delemnenr);

    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }

    window.location ="emneOversikt.htm";
}