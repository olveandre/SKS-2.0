package no.hist.tdat.kontrollere;

import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.DelEmne;
import no.hist.tdat.javabeans.Emne;
import no.hist.tdat.javabeans.Oving;
import no.hist.tdat.javabeans.beanservice.BrukerService;
import no.hist.tdat.javabeans.beanservice.EmneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by vimCnett
 */

@Controller
public class GodkjenningsoversiktKontroller {
    @Autowired
    BrukerService service;
    @Autowired
    EmneService service2;

    @RequestMapping(value = "hentRiktigEmne", method = RequestMethod.POST)  //kalles av ajax
    public String hentRiktigEmne(HttpServletRequest request, HttpSession session) {
        String emne = request.getParameter("emne");
        ArrayList<Bruker> alle = service.finnStudenterIDelemne(emne);   //alle med faget
        for (int i = 0; i < alle.size(); i++) {
            ArrayList<Emne> em = new ArrayList<Emne>();
            ArrayList<DelEmne> a = new ArrayList<DelEmne>();
            Bruker br = alle.get(i);
            ArrayList<Oving> ovinger = service.hentOvinger(emne); //henter �vinger til delemnet

            ArrayList<Oving> godkj = service.hentGodkjOvinger(br.getMail(), emne); //henter godkj �vinger til delemnet
            if (godkj.size() != 0) {
                for (int j = 0; j < godkj.size(); j++) {
                    Oving o = godkj.get(j);
                    ovinger.set(o.getOvingnr() - 1, o);
                }
            }
            DelEmne delEmne = service2.hentDelemne(emne);   // henter delemnet
            delEmne.sjekkAntOvinger(godkj);
            delEmne.setStudentovinger(ovinger);
            a.add(delEmne);

            Emne emnet = service2.hentEmne(emne);    //lager emnet
            emnet.setDelemner(a);
            em.add(emnet);

            br.setEmne(em);
        }
        session.setAttribute("ovingsoversikt", alle);
        return "error";
    }

    @RequestMapping(value = "visAlleMedBestatt")
    public String visAlleMedBestatt() {
        return "visAlleMedBestatt";
    }
}