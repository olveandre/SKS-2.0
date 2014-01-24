package no.hist.tdat.kontrollere;

import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.DelEmne;
import no.hist.tdat.javabeans.Koe;
import no.hist.tdat.javabeans.KoeGrupper;
import no.hist.tdat.javabeans.beanservice.BrukerService;
import no.hist.tdat.javabeans.beanservice.EmneService;
import no.hist.tdat.javabeans.beanservice.KoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Olve André on 20.01.14.
 */
@Controller
public class GodkjennKontroller {
    @Autowired
    EmneService emneService;

    @Autowired
    Bruker innloggetBruker;

    @Autowired
    BrukerService brukerService;

    @Autowired
    KoeService koeservice;

    @RequestMapping(value = "/hentUtKoGruppe.htm", method = RequestMethod.POST)
    public String hentUtKoGruppe(@ModelAttribute("koeGrupper") KoeGrupper koeGrupper, Model modell, HttpServletRequest request, HttpSession session){

        Koe koe = (Koe)session.getAttribute("koe");
        String gruppeNokkler = request.getParameter("gruppeIndexFraKoe");
        String[] nokler = gruppeNokkler.split(":");
        KoeGrupper gruppe = null;
        for (int i = 0; i <koe.getGrupper().size() ; i++) {
            if(koe.getGrupper().get(i).getGruppeID()==Integer.parseInt(nokler[1]) && koe.getGrupper().get(i).getKoe_id()==Integer.parseInt(nokler[0])){
                gruppe = koe.getGrupper().get(i);
                System.out.println(gruppe.getKoe_id()+ "I kontroller");
                System.out.println(koe.getGrupper().get(i).getKoe_id());
            }
        }
        session.removeAttribute("gruppeFraKoe");
        session.setAttribute("gruppeFraKoe", gruppe);
        return "godkjennOving";

    }

    @RequestMapping(value = "/godkjennGruppeOving.htm", method = RequestMethod.POST)
    public String godkjennGruppeOving(@ModelAttribute Bruker bruker, HttpServletRequest request, HttpSession session) {
        String godkjenn = request.getParameter("godkjennKnapp");
        String leggTilStudenter = request.getParameter("leggTilStundeterKnapp");
        String leggTilOving = request.getParameter("endreOvingerKnapp");

        KoeGrupper koeGrupper = (KoeGrupper)session.getAttribute("gruppeFraKoe");
        Bruker heibruker = (Bruker)session.getAttribute("innloggetBruker");

        String personenSomGodkjenner = heibruker.getMail();
        int koeID = koeGrupper.getKoe_id();

        String emneKode = "ALM805F-A";

        ArrayList<Bruker> studenter = new ArrayList<>();
        for (int e = 0; e < brukerService.hentStudenterMedEmne(emneKode).size(); e++) {
            if (brukerService.hentStudenterMedEmne(emneKode).get(e) != null) {
                Bruker student = brukerService.hentStudenterMedEmne(emneKode).get(e);
                studenter.add(student);
            }
        }
        session.removeAttribute("studenter");
        session.setAttribute("studenter", studenter);


        if (godkjenn != null) {
            Date date = new Date(); //2000-01-01 13:37:00
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String naaTid = ft.format(date);
            int gruppeID = koeGrupper.getGruppeID();
            System.out.println(gruppeID);
            for(int i = 0; i < koeGrupper.getMedlemmer().size(); i++) {
                String brukerMail = koeGrupper.getMedlemmer().get(i).getMail();

                if (koeGrupper.getOvinger().size() == 0) {
                    System.out.println("dette må være en dust..");
                    brukerService.slettKoeGruppe(koeID, gruppeID);
                }

                if (koeGrupper.getOvinger().size() == 1) {
                    int enkelOving = koeGrupper.getOvinger().get(0).getOvingid();
                    brukerService.leggTilGodkjentOving(enkelOving, brukerMail, personenSomGodkjenner, naaTid);
                    brukerService.slettKoeGruppe(koeID, gruppeID);
                }

                if (koeGrupper.getOvinger().size() >= 2) {
                    String[] ovingerSomSkalGodkjennes = koeGrupper.getOvingerIString().split(",");
                    for(int j = 0; ovingerSomSkalGodkjennes.length > j ; j++) { //Går igjennom hver oving
                        String randomNummerLol = ovingerSomSkalGodkjennes[j].trim();
                        if (!randomNummerLol.equals("") && randomNummerLol != null) {
                            int ovingsID = koeGrupper.getOvinger().get(j).getOvingid();
                            brukerService.leggTilGodkjentOving(ovingsID, brukerMail, personenSomGodkjenner, naaTid);
                            brukerService.slettKoeGruppe(koeID, gruppeID);

                        }
                    }
                }
            }

            return "koOversikt";
        }

        if (leggTilStudenter != null) {

            //koeGrupper.getMedlemmer().add(BRUKEREN
        }

        if (leggTilOving != null){
           /* for (int i = 0; i < studenter.size(); i++) {
                System.out.println("." + studenter.get(i) + ".");
            }*/
        }
        return "godkjentOving";
    }
}

