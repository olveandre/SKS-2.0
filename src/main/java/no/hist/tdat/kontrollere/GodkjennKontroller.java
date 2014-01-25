package no.hist.tdat.kontrollere;

import no.hist.tdat.javabeans.*;
import no.hist.tdat.javabeans.beanservice.BrukerService;
import no.hist.tdat.javabeans.beanservice.EmneService;
import no.hist.tdat.javabeans.beanservice.KoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.management.resources.agent;

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
        String gruppeId = request.getParameter("gruppeid");
        String koeId= request.getParameter("koeid");
        //String[] nokler = gruppeNokkler.split(":");

        KoeGrupper gruppe = null;
        int koeID = koe.getKoeId();

        for (int i = 0; i <koe.getGrupper().size() ; i++) {
            if(koe.getGrupper().get(i).getGruppeID()==Integer.parseInt(gruppeId) && koe.getGrupper().get(i).getKoe_id()==Integer.parseInt(koeId)){
                gruppe = koe.getGrupper().get(i);
            }
        }

        ArrayList<Emne> emner = brukerService.hentEmnekodeMedBareKoeID(koeID);
        Emne detteEmne = null;
        for (int a = 0; a < emner.size(); a++) {
            if (emner.size() == 1) {
                detteEmne = emner.get(a);
            }
        }
        String emneKode = detteEmne.getEmneKode();
        ArrayList<Bruker> mangeStudenter = new ArrayList<>();
        for (int e = 0; e < brukerService.hentStudenterMedEmne(emneKode).size(); e++) {
            Bruker student = brukerService.hentStudenterMedEmne(emneKode).get(e);
            mangeStudenter.add(student);
        }

        session.removeAttribute("flereStudenter");
        session.setAttribute("flereStudenter", mangeStudenter);
        session.removeAttribute("gruppeFraKoe");
        session.setAttribute("gruppeFraKoe", gruppe);

        return "godkjennOving";

    }

    @RequestMapping(value = "/godkjennGruppeOving.htm", method = RequestMethod.POST)
    public String godkjennGruppeOving(@ModelAttribute Bruker bruker, HttpServletRequest request, HttpSession session) {
        String godkjenn = request.getParameter("godkjennKnapp");
        String leggTilStudenter = request.getParameter("leggTilStundeterKnapp");
        String leggTilOving = request.getParameter("endreOvingerKnapp");
        String tilbake = request.getParameter("tilbakeKnapp");

        KoeGrupper koeGrupper = (KoeGrupper)session.getAttribute("gruppeFraKoe");
        Bruker heibruker = (Bruker)session.getAttribute("innloggetBruker");

        String personenSomGodkjenner = heibruker.getMail();
        int koeID = koeGrupper.getKoe_id();


        if (godkjenn != null) {
            Date date = new Date(); //2000-01-01 13:37:00
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String naaTid = ft.format(date);

            int gruppeID = koeGrupper.getGruppeID();

            for(int i = 0; i < koeGrupper.getMedlemmer().size(); i++) {
                String brukerMail = koeGrupper.getMedlemmer().get(i).getMail();

                if (koeGrupper.getOvinger().size() == 0) {
                    System.out.println("dette må være en dust..");
                }

                if (koeGrupper.getOvinger().size() == 1) {
                    int enkelOving = koeGrupper.getOvinger().get(0).getOvingid();
                    brukerService.leggTilGodkjentOving(enkelOving, brukerMail, personenSomGodkjenner, naaTid);
                }

                if (koeGrupper.getOvinger().size() > 1) {
                    String[] ovingerSomSkalGodkjennes = koeGrupper.getOvingerIString().split(",");
                    for(int j = 0; ovingerSomSkalGodkjennes.length > j ; j++) { //Går igjennom hver oving
                        String randomNummerLol = ovingerSomSkalGodkjennes[j].trim();
                        if (!randomNummerLol.equals("") && randomNummerLol != null) {
                            int ovingsID = koeGrupper.getOvinger().get(j).getOvingid();
                            brukerService.leggTilGodkjentOving(ovingsID, brukerMail, personenSomGodkjenner, naaTid);
                        }
                    }
                }
            }
            brukerService.slettKoeGruppe(koeID, gruppeID);
            return "koOversikt";
        }

        if (leggTilStudenter != null) {
            //koeGrupper.getMedlemmer().add(BRUKEREN
        }

        if (leggTilOving != null){
            System.out.println("hei");
        }

        if (tilbake != null){
            return "koOversikt";
        }
        return "godkjennOving";
    }
}

