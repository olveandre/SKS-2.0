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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class NavigasjonsKontroller {

    @Autowired
    Bruker innloggetBruker;
    @Autowired
    BrukerService service;
    @Autowired
    KoeService koeservice;
    @Autowired
    EmneService emneService;

    @RequestMapping("/")
    public String omdirigerHjem(@ModelAttribute("bruker") Bruker bruker) {
        return "loggInn";
    }

    @RequestMapping("/endrePassord.htm")
    public String omdirigerEndrePassord(@ModelAttribute(value = "passord") PassordBeans passord) {
        return "endrePassord";
    }

    @RequestMapping("/glemtPassord.htm")
    public String glemtPassord(@ModelAttribute Bruker bruker) {
        return "glemtPassord";
    }

    @RequestMapping("/adminBrukere.htm")
    public String omdirigerAdminBrukere(@ModelAttribute Bruker bruker, @ModelAttribute PersonerBeans personerBeans) {
        return "adminBrukere";
    }

    @RequestMapping("/adminBrukereEndre.htm")
    public String omdirigerAdminBrukereEndre(@ModelAttribute Bruker bruker, @ModelAttribute PersonerBeans personerBeans) {
        return "adminBrukereEndre";
    }

    @RequestMapping("/godkjennOving.htm")
    public String omdirigerGodkjenn() {
        return "godkjennOving";
    }

    @RequestMapping("/adminFag.htm")
    public String omdirigerAdminFag(@ModelAttribute("emne") Emne emne, @ModelAttribute("emnerBeans") EmnerBeans emnerBeans, HttpServletRequest request, HttpSession session, Model model) {
        return "adminFag";
    }
    @RequestMapping("/adminEmneEndre.htm")
    public String omdirigerAdminEmneEndre(@ModelAttribute ("emne") Emne emne, @ModelAttribute("emnerBeans") EmnerBeans emnerBeans) {
        return "adminEmneEndre";
    }

    @RequestMapping(value = "/koOversikt.htm", method = RequestMethod.POST)
    public String koOversikt(@ModelAttribute("delEmne") DelEmne delEmne, HttpServletRequest request, HttpSession session, Model model) {
        int delemneNr = Integer.parseInt(request.getParameter("delemneNr"));    //Index i bruker-objektet, IKKE i DB
        int emnenr = Integer.parseInt(request.getParameter("emneNr"));          //Index i bruker-objektet, IKKE i DB
        innloggetBruker = (Bruker) session.getAttribute("innloggetBruker");
        delEmne = innloggetBruker.getEmne().get(emnenr).getDelemner().get(delemneNr);
        int koeId = delEmne.getKoe_id();
        Koe koe = new Koe();
        koe.setGrupper(koeservice.getKoe(koeId));
        koe.setKoeId(koeId);
        session.setAttribute("koe", koe);
        DelEmne denne = koeservice.hentDelEmneStatus(koeId);
        delEmne.setKoe_status(denne.isKoe_status());
        ArrayList<KoeGrupper> grupper = koe.getGrupper();
        model.addAttribute("emneIndex", emnenr);
        model.addAttribute("delEmneIndex", delemneNr);
        model.addAttribute("koe", koe);
        model.addAttribute("grupper", grupper);
        model.addAttribute("delEmne", delEmne);
        return "koOversikt";
    }

    @RequestMapping(value = "/startStoppKoe.htm", method = RequestMethod.POST)
    @ResponseBody
    public String koOversikt(HttpServletRequest request, HttpSession session) {

        return koeservice.genererStartStopKnapp((DelEmne)session.getAttribute("delEmne"));
    }

    @RequestMapping(value="/oppdaterKoe.htm", method = RequestMethod.POST)
    @ResponseBody
    public String oppdaterKoe(@ModelAttribute("delEmne") DelEmne delEmne,HttpServletRequest request,HttpSession session, Model model) {
        innloggetBruker = (Bruker) session.getAttribute("innloggetBruker");
        Koe koe = (Koe)session.getAttribute("koe");
       // System.out.println("wtf");
        //int koeId = (int)request.getAttribute("koe_id");
        koe.setGrupper(koeservice.getKoe(koe.getKoeId()));
        //System.out.println("koe elements grupper size:"+koe.getGrupper().size());
        return koeservice.genererKoeOversikt(koe,innloggetBruker);
    }

    @RequestMapping("/error.htm")
    public String omdirigerError() {
        return "error";
    }

    @RequestMapping(value = "/settIKo.htm", method = RequestMethod.POST)
    public String omdirigerTilKo(@ModelAttribute("personerBeans") PersonerBeans personerBeans, @ModelAttribute("bruker") Bruker bruker,
                                 @ModelAttribute("koegrupper") KoeGrupper koegrupper, @ModelAttribute("delEmne") DelEmne delEmne,
                                 Model model, HttpSession session, HttpServletRequest request) {
        int delemneNr = Integer.parseInt(request.getParameter("delemneNr"));    //Index i bruker-objektet, IKKE i DB
        int emnenr = Integer.parseInt(request.getParameter("emneNr"));          //Index i bruker-objektet, IKKE i DB
        innloggetBruker = (Bruker) session.getAttribute("innloggetBruker");


        Emne emne = innloggetBruker.getEmne().get(emnenr);
        delEmne = emne.getDelemner().get(delemneNr);
        ArrayList<Oving> oving = delEmne.getStudentovinger();
        ArrayList<Oving> ikkeGodkjent = new ArrayList<Oving>();
        for(int i = 0; i<oving.size(); i++){
            if(!oving.get(i).isGodkjent()){
                ikkeGodkjent.add(oving.get(i));
            }
        }
        personerBeans.setValgt(service.getMedstudenter(delEmne.getEmneKode(), innloggetBruker.getMail()));
        model.addAttribute("personerBeans", personerBeans);
        DelEmne denne = koeservice.hentDelEmneStatus(delEmne.getKoe_id());
        delEmne.setKoe_status(denne.isKoe_status());
        System.out.println("Bruker: " + innloggetBruker.getFornavn() + ", Emne: " + emne.getEmneKode() + ", DelEmne: " + delEmne.getNr() + ", AntOving: " + delEmne.getStudentovinger().size());
        model.addAttribute("oving", ikkeGodkjent);
        model.addAttribute("plassering", koeservice.getPlasseringer());
        model.addAttribute("delEmne", delEmne);
        model.addAttribute("emneIndex",emnenr);
        model.addAttribute("delEmneIndex", delemneNr);

        return "settIKo";
    }

    @RequestMapping("/endreStudent.htm")
    public String omdirEndreStudent(@ModelAttribute("bruker") Bruker bruker) {
        return "endreStudent";
    }

    @RequestMapping("/minside.htm")
    public String omdirigerMinside(@ModelAttribute("bruker") Bruker bruker, HttpSession session) {
        bruker = (Bruker) session.getAttribute("innloggetBruker");
        return "minside";
    }

    @RequestMapping("/emne.htm")
    public String hentMittEmne(@ModelAttribute("bruker") Bruker bruker, HttpSession session) {
        bruker = (Bruker) session.getAttribute("innloggetBruker");
        bruker.getEmne().get(0);
        return "minside";
    }

    @RequestMapping("/ovingsopplegget.htm")
    public String ovingsOpplegg() {
        return "ovingsopplegget";
    }

    @RequestMapping("/*")
    public String direct404() {
        return "error";
    }



    @RequestMapping("/nyStudent.htm")
    public String nyStudent(@ModelAttribute("nyStudent") Bruker stud) {
        return "nyStudent";
    }

    @RequestMapping("/loggUt.htm")
    public String loggUt(@ModelAttribute("bruker") Bruker bruker, HttpSession session) {
        session.invalidate();
        System.out.println("utlogget");
        return "loggInn";
    }

    @RequestMapping("/opprettDelemne.htm")
    public String opprettDelemne(@ModelAttribute("delemne") DelEmne delEmne) {
        return "opprettDelemne";
    }


        //HENTER FOR ETIKK
    @RequestMapping("/godkjenningsoversikt.htm")
    public String godkjOversikt(HttpServletRequest request, Model modell, HttpSession session) {

        String emne = "ingen";//request.getParameter("emne");
        if (emne.equals("ingen")) {
            return "godkjenningsoversikt";
        }

        DelEmne valgtEmne = emneService.hentDelemne(emne);
        session.setAttribute("valgteEmne", valgtEmne);

        return "godkjenningsoversikt";
    }

    @RequestMapping("/opprettEmne.htm")
    public String opprettEmne(@ModelAttribute("emne") Emne emne) {
        return "opprettEmne";
    }

    @RequestMapping(value = "/emneOversikt.htm", method = RequestMethod.POST)
    public void omdirEmneOversikt(@ModelAttribute("koegrupper") KoeGrupper koegrupper,@ModelAttribute("bruker") Bruker bruker,@ModelAttribute("personerBeans") PersonerBeans personerBeans,@ModelAttribute("delEmne") DelEmne delEmne, @ModelAttribute("emne")Emne emne,HttpServletRequest request, HttpSession session) {
        String emnekode = request.getParameter("emnekodeFraNav");
        session.setAttribute("emnekodeFraMeny",emnekode);
    }

    @RequestMapping("/emneOversikt.htm")
    public String omdirEmneOversiktKlone(@ModelAttribute("koegrupper") KoeGrupper koegrupper,@ModelAttribute("bruker") Bruker bruker,@ModelAttribute("personerBeans") PersonerBeans personerBeans,@ModelAttribute("delEmne") DelEmne delEmne,@ModelAttribute("emne")Emne emne, HttpServletRequest request, HttpSession session) {
        return "emneOversikt";

    }
}