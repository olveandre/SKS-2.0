package no.hist.tdat.kontrollere;

import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.DelEmne;
import no.hist.tdat.javabeans.Emne;
import no.hist.tdat.javabeans.beanservice.EmneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Eirik on 20.01.14.
 */

@Controller
public class OpprettDelemneKontroller {
    @Autowired
    EmneService service;
    @RequestMapping("lagDelemne")
    public String delemne(@ModelAttribute(value = "delemne")DelEmne delEmne, Model model, HttpSession session) {
        try{
            Emne emne = (Emne)session.getAttribute("redigerEmne");
            service.opprettDelemne(delEmne, emne);
            model.addAttribute("delemnerett", "Delemne \""+delEmne.getDelEmneNavn()+"\" med delemnekode \""+delEmne.getNr()+"\" er opprettet");
            session.setAttribute("delemne", delEmne);
            return "ovingsopplegget";
        }catch(org.springframework.dao.DuplicateKeyException e){
            model.addAttribute("delemneSQLfeil", "Delemnenavn eller delemnekode er opprettet fra f&oslash;r");
            return "opprettDelemne";
        }
    }


    @RequestMapping(value = "/slettDelEmne.htm", method = RequestMethod.POST)
    public String slettDelEmne(HttpServletRequest request, HttpSession session) {
        Bruker innloggetBruker = (Bruker)session.getAttribute("innloggetBruker");
        String emnekode = request.getParameter("emnekode");
        String delEmneNr = request.getParameter("delEmneNr");
        service.slettDelEmne(emnekode, delEmneNr);
        service.hentEmner(innloggetBruker);
        return "emneOversikt";
    }

}
