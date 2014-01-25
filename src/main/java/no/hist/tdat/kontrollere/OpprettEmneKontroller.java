package no.hist.tdat.kontrollere;

import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.DelEmne;
import no.hist.tdat.javabeans.Emne;
import no.hist.tdat.javabeans.PersonerBeans;
import no.hist.tdat.javabeans.beanservice.BrukerService;
import no.hist.tdat.javabeans.beanservice.EmneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Eirik on 20.01.14.
 */

@Controller
public class OpprettEmneKontroller {
    @Autowired
    EmneService eservice;
    @Autowired
    BrukerService bservice;
    @RequestMapping("lagEmne")
    public String emne(@ModelAttribute(value = "emne") Emne emne, @ModelAttribute("delemne")DelEmne delEmne, Model model, HttpSession session) {
        try{
            eservice.opprettEmne(emne);
            model.addAttribute("emnerett", "Emne \"" + emne.getEmneNavn() + "\" med emnekode \"" + emne.getEmneKode() + "\" er opprettet");
            Emne redigerEmne = emne;
            session.setAttribute("redigerEmne", redigerEmne);
            return "adminEmneEndre";
        }catch(org.springframework.dao.DuplicateKeyException e){
            model.addAttribute("emneSQLfeil", "Emnenavn eller emnekode er opprettet fra f&oslash;r");
            return "opprettEmne";
        }
    }
    @RequestMapping(value="sokBoks", method= RequestMethod.POST)
    public String finnL(@ModelAttribute(value = "emne") Emne emne, @ModelAttribute("personerBeans") PersonerBeans personerBeans, @ModelAttribute("bruker") Bruker bruker, Model modell, HttpServletRequest request,HttpSession session) {
        String tab = request.getParameter("tab");
        String brukere = request.getParameter("srch-term");
        if(brukere == null){
            if(session.getAttribute("sok")!=null){
                personerBeans.setValgt(bservice.finnL((String)session.getAttribute("sok")));
                session.setAttribute("sok", (String)session.getAttribute("sok"));
            }
        }else{
            personerBeans.setValgt(bservice.finnL(brukere));
            session.setAttribute("sok", brukere);
        }
        modell.addAttribute("tabForm", tab);
        modell.addAttribute("personerBeans", personerBeans);
        return "leggTilEmneAnsView";
    }

    @RequestMapping(value = "/velgBruker.htm", method = RequestMethod.POST)
    public String velgBruker(@ModelAttribute(value = "emne") Emne emne, @ModelAttribute("bruker") Bruker bruker, Model modell, HttpServletRequest request, HttpSession session) {
        String tab = request.getParameter("tab");
        String emneKode = request.getParameter("emneIndex");
        String mail = request.getParameter("brukerIndex");
        System.out.println(emneKode + ", " + mail);
        Bruker redigerBrukere = bservice.hentBruker(mail);
        session.setAttribute("redigerBrukere", redigerBrukere);
        if (redigerBrukere == null) {
            modell.addAttribute("melding", "Finner ikke bruker i databasen");
            return "leggTilEmneAnsView";
        } else {
            bservice.leggTilEmne(emneKode, mail, 1);
            return "leggTilEmneAnsView";
        }

    }
}
