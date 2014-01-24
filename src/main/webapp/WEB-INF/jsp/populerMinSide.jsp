<%@ page import="no.hist.tdat.javabeans.Bruker" %>
<%
    Bruker denne = (Bruker) request.getSession().getAttribute("innloggetBruker");
    if(denne.getEmne() != null){
        for (int i = 0; i < denne.getEmne().size(); i++) {
            out.println("<tr ><td>" + i + "</td><td>" + denne.getEmne().get(i).getEmneKode() + "</td><td><button class='btn btn-lg btn-primary btn-block'>" + denne.getEmne().get(i).getEmneNavn() + "</button></td></tr>");

            for (int j = 0; j < denne.getEmne().get(i).getDelemner().size(); j++) {

                out.print("<tr><td></td><td></td><td><input type='submit' onclick='delemnenr="+j+";emnenr="+i+"' value ='"+denne.getEmne().get(i).getDelemner().get(j).getDelEmneNavn()+"' class='pull-right btn btn-md btn-info' style='width:50%;' /></td><td>");
                for (int a = 0; a < denne.getEmne().get(i).getDelemner().get(j).getStudentovinger().size(); a++) {
                    if (denne.getEmne().get(i).getDelemner().get(j).getStudentovinger().get(a).isGodkjent()) {
                        String godkjentInfo = "Godkjent av:\t " + denne.getEmne().get(i).getDelemner().get(j).getStudentovinger().get(a).getGodkjentAv() + "\ndato:\t\t" + denne.getEmne().get(i).getDelemner().get(j).getStudentovinger().get(a).getGodkjentTid();
                        out.println("<li class ='btn btn-success btn-sm active' title='" + godkjentInfo + "'>" + denne.getEmne().get(i).getDelemner().get(j).getStudentovinger().get(a).getOvingnr() + "</li>");
                    } else {
                        out.println("<li class ='btn btn-default btn-sm active'  title=\"Ikke godkjent\"  >" + denne.getEmne().get(i).getDelemner().get(j).getStudentovinger().get(a).getOvingnr() + "</li>");
                    }
                }
                out.println("</td></tr>");
            }
        }
    }else{
        out.print("<tr >Du er ikke registrert i noen fag. Vi anbefaler at du registrerer deg i et fag hvis du vil studere.</tr>");
    }
%>