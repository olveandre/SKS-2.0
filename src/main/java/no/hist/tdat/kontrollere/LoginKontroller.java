package no.hist.tdat.kontrollere;

import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.beanservice.BrukerService;
import no.hist.tdat.javabeans.beanservice.EmneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Enumeration;


@Controller
public class LoginKontroller {

    @Autowired
    EmneService emneService;
    @Autowired
    BrukerService service;
    @Autowired
    Bruker bruker;

    @RequestMapping(value = "loggerinn.SSL", method= RequestMethod.POST)
    private String loggerInn(@Valid @ModelAttribute("bruker") Bruker bruker, BindingResult result,HttpSession session) {
        if(result.hasErrors()){
            return "loggInn";
        }
        bruker = service.loggInn(bruker); // Oppretter brukerobjekt.
        if(bruker==null){
            return "loggInn";
        }
        try{
        emneService.hentEmner(bruker);  //Henter emner,
        }catch (NullPointerException ne){
            System.out.println("Nullpointer ved login: \n"+ne);
        }
        /*for (int i = 0; i < bruker.getEmne().size(); i++) {
            //System.out.println(bruker.getEmne().get(i).getEmneNavn());
/*            for (int j = 0; j < bruker.getEmne().get(i).getStudentovinger().size() ; j++) {
                System.out.println("\tovingsnr: "+bruker.getEmner().get(i).getStudentovinger().get(j).getOvingnr());
                System.out.println("\tgodkjent av: "+bruker.getEmner().get(i).getStudentovinger().get(j).getGodkjentAv());
                System.out.println("\tgodkjent dato: "+bruker.getEmner().get(i).getStudentovinger().get(j).getGodkjentTid());
            } //TODO TED
            //TODO fiks nullpointer hvis bruker ikke er lagt til emne

        }*/

        session.setAttribute("innloggetBruker", bruker);
        return "minside";
    }

    @RequestMapping("/login.htm")
    public String loggInn(@ModelAttribute("bruker") Bruker bruker, Model model, HttpSession session){
        if(session.getAttribute("innloggetBruker")!=null ){
            return "minside";
        }
        return "loggInn";
    }
}
