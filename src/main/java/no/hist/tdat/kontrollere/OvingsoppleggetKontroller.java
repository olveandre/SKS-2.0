package no.hist.tdat.kontrollere;

import no.hist.tdat.javabeans.DelEmne;
import no.hist.tdat.javabeans.beanservice.EmneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Eirik on 22.01.14.
 */

@Controller
public class OvingsoppleggetKontroller {
    @Autowired
    EmneService service;
    @RequestMapping(value="ovingsopplegget")
    public String oving(@ModelAttribute(value="delemne")DelEmne delEmne, Model model, HttpSession session, HttpServletRequest req) {
        int antall = Integer.parseInt(req.getParameter("antall"));
        DelEmne delemne = (DelEmne)session.getAttribute("delemne");
        int ant = 0;
        String tot = req.getParameter("antall");
        String min = req.getParameter("min");
        String text = req.getParameter("newText");
        String regler = tot+" "+min+" | "+text;
        try{
            for (int i = 0; i < antall; i++) {
                service.opprettOving(i, delemne);
                ant++;
            }
            service.lagRegler(regler, ant, delemne);
            model.addAttribute("Vellykket", "Øvingsregel opprettet i delemnet " + delemne.getDelEmneNavn());
            return "adminEmneEndre";
        }catch(org.springframework.dao.DuplicateKeyException e){
            model.addAttribute("delemneSQLfeil", "Delemnenavn eller delemnekode er opprettet fra f&oslash;r");
            return "ovingsopplegget";
        }
    }
}
