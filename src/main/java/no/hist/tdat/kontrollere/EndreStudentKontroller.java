package no.hist.tdat.kontrollere;


import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.PersonerBeans;
import no.hist.tdat.javabeans.beanservice.BrukerService;
import no.hist.tdat.javabeans.beanservice.EmneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by vimCnett
 */
@Controller

public class EndreStudentKontroller {
    @Autowired
    BrukerService service;
    @Autowired
    EmneService service2;


    @RequestMapping(value = "leggTilStudentListe")
    public String leggTilListe(@ModelAttribute("personerBeans") PersonerBeans personerBeans, HttpServletRequest request, HttpSession session) {
        String txt = request.getParameter("soketekst");
        if (txt == null || txt.equals("")) {
            return "endreStudent";
        }
        personerBeans.setValgt(service.finnStudenter(txt));
        session.setAttribute("personerBeans", personerBeans);
        return "endreStudent";
    }


    @RequestMapping(value = "endreValgtBruker", method = RequestMethod.POST)
    @ResponseBody
    public String endreValgtBruker(HttpServletRequest request, HttpSession session) {

        String mail = request.getParameter("brukerIndex");
        Bruker b = service.hentBruker(mail);
        b.setEmne(service2.hentEmnerForStud(b.getMail()));
        session.setAttribute("valgtPerson", b);
        return "hei";
    }

    @RequestMapping(value = "endreValgtStudent")
    public String videresend(HttpServletRequest request, HttpSession session) {
        String tilb = request.getParameter("tilbake");
        if (tilb != null) {
            session.removeAttribute("valgtPerson");
            return "endreStudent";
        }
        return "endreValgtStudent";
    }

    @RequestMapping(value = "utførOperasjon")
    public String utførOperasjon(Model modell, HttpServletRequest request, HttpSession session) {
        String lagre = request.getParameter("lagre");
        String fjern = request.getParameter("fjern");
        String leggTil = request.getParameter("leggTil");

        Bruker b = (Bruker) session.getAttribute("valgtPerson");

        if (lagre != null) {    //sett som studass
            String emnenavn = request.getParameter("emner");
            if (service.settStudass(emnenavn, b.getMail())) {
                modell.addAttribute("forrigeOp", b.getFornavn() + " " + b.getEtternavn() + " satt som studass i " + emnenavn);
            } else {
                modell.addAttribute("forrigeOp", "Dette delemnenummer finnes ikke, eller student er allerede studentassistent i faget");
            }
        } else if (fjern != null) {   //fjern fag
            String emnekode2 = request.getParameter("emner2");
            if (service.fjernEmne(emnekode2, b.getMail())) {
                modell.addAttribute("forrigeOp", "Fjernet rettighet til emnet " + emnekode2 + " for " + b.getFornavn() + " " + b.getEtternavn());
            } else {
                modell.addAttribute("forrigeOp", b.getFornavn() + " " + b.getEtternavn() + " har ikke tilgang til " + emnekode2);
            }
        } else if (leggTil != null) {  //legg til fag
            String emnekode3 = request.getParameter("emner3");
            if (service.leggTilEmne(emnekode3, b.getMail(), 0)) {
                modell.addAttribute("forrigeOp", "Tilgang til emnet " + emnekode3 + " lagt til for " + b.getFornavn() + " " + b.getEtternavn());
            } else {
                modell.addAttribute("forrigeOp", b.getFornavn() + " " + b.getEtternavn() + " har allerede tilgang til " + emnekode3);
            }
        } else {  //fjern som studass
            String emne4 = request.getParameter("emner4");
            if (service.fjernStudass(emne4, b.getMail())) {
                modell.addAttribute("forrigeOp", b.getFornavn() + " " + b.getEtternavn() + " er fjernet som studentassistent for " + emne4);
            } else {
                modell.addAttribute("forrigeOp", b.getFornavn() + " " + b.getEtternavn() + " er ikke studentassistent i " + emne4);
            }
        }
        return "endreValgtStudent";
    }
}


