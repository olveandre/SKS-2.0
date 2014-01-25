package no.hist.tdat.kontrollere;

import no.hist.tdat.javabeans.*;
import no.hist.tdat.javabeans.beanservice.EmneService;
import no.hist.tdat.javabeans.beanservice.KoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by Henriette on 09/01/14.
 */
@Controller
public class KoeKontroller {
    @Autowired
    KoeService koe_service;

    @Autowired
    EmneService emne_service;

    @Autowired
    Bruker innloggetBruker;

    @RequestMapping(value = "/StartStoppKoe.htm", method = RequestMethod.POST)
    public String startKoen(@ModelAttribute DelEmne delEmne, HttpServletRequest request) {
        int koe_id = Integer.parseInt(request.getParameter("KoeIndex"));

        delEmne = koe_service.hentDelEmneStatus(koe_id);
        if (delEmne.isKoe_status()) {
            delEmne.setKoe_status(false);
            emne_service.endreKoeStatus(koe_id, 0);
            return "koOversikt";
        }
        emne_service.endreKoeStatus(koe_id, 1);
        return "koOversikt";
    }

    @RequestMapping(value = "velgPlass.htm")
    public String Koe(@Validated @ModelAttribute("plassering") Plassering plassering, BindingResult error, HttpServletRequest request) {
        if (error.hasErrors()) {
            return "sittIKo.htm";
        }

        try {
            String plass = request.getParameter("sitteplass");
            koe_service.getAntBord(plass);
            int bordnret = request.getIntHeader("bordnr");

        } catch (NullPointerException e) {
            System.out.println("Du må være logget inn før du kan sette  deg i kø!");
            return "koOversikt";
        }
        return "sittIKo.htm";

    }

    @RequestMapping(value = "StillIKo.htm", method = RequestMethod.POST)
    public String StillIKo(HttpServletRequest request, @Valid @ModelAttribute("koegrupper") KoeGrupper koegrupper, Model model, BindingResult error, HttpSession session) {

        int delemneNr = Integer.parseInt(request.getParameter("delemneNr"));    //Index i bruker-objektet, IKKE i DB
        int emnenr = Integer.parseInt(request.getParameter("emneNr"));          //Index i bruker-objektet, IKKE i DB
        innloggetBruker = (Bruker) session.getAttribute("innloggetBruker");
        Emne emne = innloggetBruker.getEmne().get(emnenr);
        DelEmne delEmne = emne.getDelemner().get(delemneNr);
        koegrupper.setKoe_id(delEmne.getKoe_id());

        //Lager de nødvendige attributtene;
        int koeId = delEmne.getKoe_id();
        Koe koe = new Koe();
        koe.setGrupper(koe_service.getKoe(koeId));
        koe.setKoeId(koeId);
        session.setAttribute("koe", koe);
        DelEmne denne = koe_service.hentDelEmneStatus(koeId);
        delEmne.setKoe_status(denne.isKoe_status());
        ArrayList<KoeGrupper> grupper = koe.getGrupper();
        ArrayList<Oving> oving = delEmne.getStudentovinger();
        ArrayList<Oving> ikkeGodkjent = new ArrayList<Oving>();
        for(int i = 0; i<oving.size(); i++){
            if(!oving.get(i).isGodkjent()){
                ikkeGodkjent.add(oving.get(i));
            }
        }
        //Attributter for settIKo
        model.addAttribute("oving", ikkeGodkjent);
        model.addAttribute("plassering", koe_service.getPlasseringer());
        //Attributter for koOversikt
        model.addAttribute("emneIndex",emnenr);
        model.addAttribute("delEmneIndex", delemneNr);
        model.addAttribute("koe", koe);
        model.addAttribute("grupper", grupper);
        model.addAttribute("delEmne", delEmne);
        if (error.hasErrors()) {
            return "settIKo";
        }else if(koegrupper.getSitteplass().equals("tom")){
            model.addAttribute("melding", "Du må velge sitteplass!");
            return "settIKo";
        }

        ArrayList<Bruker> medlemmer = new ArrayList<>();
        medlemmer.add(innloggetBruker);
        if(koegrupper.getMedlemmer()!=null){
            for(int i = 0; i<koegrupper.getMedlemmer().size(); i++){
                medlemmer.add(koegrupper.getMedlemmer().get(i));
            }
        }
        koegrupper.setMedlemmer(medlemmer);
        String ovingString = "";
        for(int i = 0; i<koegrupper.getOvingnr().size(); i++){
            ovingString = "Øving " + koegrupper.getOvingnr().get(i) + ", ";
        }
        koe_service.leggTilIKo(koegrupper, delEmne, ovingString);
        model.addAttribute("Vellykket", "Vellykket!");
        return "settIKo";
    }
}
