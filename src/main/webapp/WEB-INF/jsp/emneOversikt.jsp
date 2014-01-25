<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="no.hist.tdat.javabeans.Bruker" %>
<%@ page import="no.hist.tdat.javabeans.Emne" %>
<script src="<c:url value="/resources/js/koen.js"/>"></script>

<%
    Bruker innlogget = (Bruker) session.getAttribute("innloggetBruker");
    Emne emne = null;
    String emnekode = (String) session.getAttribute("emnekodeFraMeny");
    int i = 0;
    for (int t = 0; t < innlogget.getEmne().size(); t++) {
        if (innlogget.getEmne().get(t).getEmneKode().equals(emnekode)) {
            emne = innlogget.getEmne().get(t);
            i = t;
        }
    }
%>

<div class="col-lg-10">
    <ul class="nav nav-tabs nav-justified">
        <li id="lenkeEmne" class="active"><a href="#emne"
                                             data-toggle="tab"><strong><% out.print(emne.getEmneNavn());%></strong></a>
        </li>
        <%
            for (int j = 0; j < emne.getDelemner().size(); j++) {
                out.print("<li onclick=\"kjor()\" id=\"lenkeOvinger\"><a href=\"#" + j + "\" data-toggle=\"tab\">" + emne.getDelemner().get(j).getDelEmneNavn() + "</a></li>");
            }

        %>
    </ul>

    <div class="tab-content">

        <div class="tab-pane fade in active" id="emne">


            <div class="col-md-8">
                <div class="row">
                    <div class=" table-responsive">

                        <table class="col-lg-10 table table-striped">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Delemne</th>
                                <th>&Oslash;vingsoversikt</th>
                            </tr>
                            </thead>
                            <tbody>

                            <form onsubmit="mysubmit()" method="POST" action="koOversikt.htm">
                                <input type="hidden" name="delemneNr" id="delemneNr"/>
                                <input type="hidden" name="emneNr" id="emneNr"/>
                                <%

                                    for (int j = 0; j < emne.getDelemner().size(); j++) {
                                        int delemnenr = emne.getDelemner().get(j).getNr();

                                        out.print("<tr><td></td><td><input type='submit' onclick='delemnenr=" + j + ";emnenr=" + i + "' value ='" + emne.getDelemner().get(j).getDelEmneNavn() + "' class='pull-right btn btn-md btn-info btn-block'  /></td><td>");
                                        if(((Bruker) session.getAttribute("innloggetBruker")).getRettighet()<3){

                                            out.println("<button type=\"button\" class=\"btn btn-warning btn-sm\" data-toggle=\"modal\"\n" +
                                                    "                                     onclick=\"redigerDelEmneFraKnapp(\"${emne.getEmnekode}\",\"${emne.getDelemner().get("+j+").getNr()}\")\" title=\"Endre\">\n" +
                                                    "                                    <i class=\"glyphicon glyphicon-edit\"></i></button>\n" +


                                                    "                                <button type=\"button\" value=\"Slett\" class=\"btn btn-danger btn-sm\" data-task=\"remove\"\n" +
                                                    "                                        onclick=\"slettDelEmneFraKnapp('"+emne.getEmneKode()+"', "+delemnenr+")\" " +
                                                    "                                        title=\"Slett\"><i class=\"glyphicon glyphicon-remove\"></i>\n" +
                                                    "                                </button>");
                                        }
                                        for (int a = 0; a < emne.getDelemner().get(j).getStudentovinger().size(); a++) {
                                                if (emne.getDelemner().get(j).getStudentovinger().get(a).isGodkjent()) {
                                                    String godkjentInfo = "Godkjent av:\t " + emne.getDelemner().get(j).getStudentovinger().get(a).getGodkjentAv() + "\ndato:\t\t" + emne.getDelemner().get(j).getStudentovinger().get(a).getGodkjentTid();
                                                    out.println("<li class ='btn btn-success btn-sm active' title='" + godkjentInfo + "'>" + emne.getDelemner().get(j).getStudentovinger().get(a).getOvingnr() + "</li>");
                                                } else {
                                                    out.println("<li class ='btn btn-default btn-sm active' title=\"Ikke godkjent\" >" + emne.getDelemner().get(j).getStudentovinger().get(a).getOvingnr() + "</li>");
                                                }
                                            }
                                        out.println("</td></tr>");

                                    }
                                %>
                            </form>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>



        <%
            for (int j = 0; j < emne.getDelemner().size(); j++) {

                out.print("<div class=\"tab-pane fade\" id=\"" + j + "\">");

                out.print("<div class=\"row\"><div class=\" table-responsive\"><table class=\"col-lg-10 table table-striped\">\n" +
                        "        <thead><tr>\n" +
                        "                   <th>#</th>\n" +
                        "                   <th>Øving</th>\n" +
                        "                   <th>Godkjenner</th>\n" +
                        "                   <th>tid</th>\n" +
                        "               </tr>\n" +
                        "        </thead>\n" +
                        "        <tbody>"
                );


                for (int a = 0; a < emne.getDelemner().get(j).getStudentovinger().size(); a++) {
                    out.print("<tr><td></td>");
                    if (emne.getDelemner().get(j).getStudentovinger().get(a).isGodkjent()) {
                        out.println("<td><li class ='btn btn-success btn-sm active' >" + emne.getDelemner().get(j).getStudentovinger().get(a).getOvingnr() + "</li></td>" +
                                "<td>"+emne.getDelemner().get(j).getStudentovinger().get(a).getGodkjentAv()+"</td>" +
                                "<td>"+emne.getDelemner().get(j).getStudentovinger().get(a).getGodkjentTid()+"</td>");
                    } else {
                        out.println("<td><li class ='btn btn-default btn-sm active' >" + emne.getDelemner().get(j).getStudentovinger().get(a).getOvingnr() + "</li></td><td></td><td></td>");
                    }
                }
                out.println("</tr></tbody></table></div></div></div>");

            }
        %>




    </div>

</div>

<script src="<c:url value="/resources/js/menyScript.js"/>"></script>

