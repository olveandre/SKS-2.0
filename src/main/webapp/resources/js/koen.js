function hentKoeStudent(koe_id) {

    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById("page-wrapper").innerHTML = xmlhttp.responseText;
        }
    }
    xmlhttp.open("POST", "/koOversikt.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("koeid=" + koe_id);
}

function setKoeId(num) {
    document.getElementById('hiddenKoe').value = num;
}

var delemnenr;      //Index i bruker-objektet, IKKE i DB
var emnenr;         //Index i bruker-objektet, IKKE i DB
function mysubmit() {
    var delemneNrField = document.getElementById("delemneNr");  //Index i bruker-objektet, IKKE i DB
    var emneNrField = document.getElementById("emneNr");        //Index i bruker-objektet, IKKE i DB
    delemneNrField.value = delemnenr;
    emneNrField.value = emnenr;
}

function sjekkAktivKoe(status) {
    alert(status);

    if (status) {
        document.getElementById("stillIKo").disabled = false;
    }
}
function startStoppKoe(koe_id) {
    var counter = 0;
    var knapp = document.getElementById("startStoppKnapp");
    var statusDiv = document.getElementById("koeStatus");
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

            if (knapp.value == "Stopp sks" && counter == 0) {
                statusDiv.innerHTML = "(Deaktivert)";
                statusDiv.style.color = "red";
                knapp.value = "Start sks";
                knapp.className = "btn btn-sm btn-success";
            } else {
                statusDiv.innerHTML = "(Aktivert)";
                statusDiv.style.color = "green";
                knapp.value = "Stopp sks";
                knapp.className = "btn btn-sm btn-danger";
            }
            counter++;
        }
    }
    xmlhttp.open("POST", "/StartStoppKoe.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("KoeIndex=" + koe_id);
}
function settIKo(koe_id) {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "/settIKo.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("KoeIndex=" + koe_id);

    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }
}
function StillIKo(emne_id) {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "StillIKo", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("KoeIndex=" + emne_id);

    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }
    // oppdaterKoe();
}


/* function oppdaterKoe() {
 if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
 xmlhttp = new XMLHttpRequest();
 }
 else {// code for IE6, IE5
 xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
 }
 xmlhttp.onreadystatechange = function () {
 alert("READYSTATE: "+xmlhttp.readyState+"\nSTATUS: "+xmlhttp.status);
 if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
 document.getElementById("koeTabell").innerHTML=xmlhttp.responseText;
 }
 }
 xmlhttp.open("POST", "/oppdaterKoe.htm", true);
 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
 xmlhttp.send("koelol=1");
 }*/
function visBilde(sitteplass) {

    switch (sitteplass.value) {
        case ("Labben 2.etg"):
            document.getElementById("bilde").innerHTML = "<img src='<c:url value='/resources/img/lab.png'/>' style='padding:1px;border:thin solid black;'/>";
            break;
        case "Polareal 1.etg":
            document.getElementById("bilde").innerHTML = "<img src='<c:url value='/resources/img/pol.png'/>' style='padding:1px;border:thin solid black;'/>";
            break;
        case  "Sukkerhuset 4.etg":
            document.getElementById("bilde").innerHTML = "<img src='<c:url value='/resources/img/nettlab.png'/>' style='padding:1px;border:thin solid black;'/>";
            break;
    }
    if (document.getElementById("sitteplass").value === "tom") {
        document.getElementById("bordnr").disabled = true;
    } else {
        document.getElementById("bordnr").disabled = false;
    }

}
function hentBord(formSelectElement) {
    var value = formSelectElement.options[formSelectElement.selectedIndex].id;
    var selectElement = document.getElementById("bordnr");
    selectElement.options.length = 0; // clearer forrige verdier.
    var option = "";
    if (value === "tom") {
        selectElement.disabled = true;
    }else{
        selectElement.disabled = false;
        for (var i = 1; i <= value; i++) {
            option = document.createElement('option');
            option.appendChild(document.createTextNode(i));
            option.value = i;
            selectElement.appendChild(option);
        }
    }
}
function tilKoe(){
    var form =document.getElementById("formStillIKo");
    form.action = "/koOversikt.htm";
    document.getElementById("emneNr").value=emnenr;
    document.getElementById("delemneNr").value=delemnenr;
    form.submit();
}
//Fjernet visadmin, statuskoe og var statusknapp.
function oppdaterkoen() {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

            document.getElementById("koetabell").innerHTML = xmlhttp.responseText;
        }
    }
    xmlhttp.open("POST", "/oppdaterKoe.htm", true);
    //xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("koe_id=" + "1");
}

function startStoppKoeKnapp() {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById("startStoppKoe").innerHTML = xmlhttp.responseText;
        }
    }
    xmlhttp.open("POST", "/startStoppKoe.htm", true);
    //xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("koe_id=" + "1");
}


//Velg gruppe fra kø
function velgGruppeFraKoe(koe_id, gruppe_id) {
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5

        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {

    }
    xmlhttp.open("POST", "/hentUtKoGruppe.htm", true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("koeid=" + koe_id+"&gruppeid="+gruppe_id);
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    }
    window.location = "godkjennOving.htm"
}


