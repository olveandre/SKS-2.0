package no.hist.tdat.kontrollere;

/**
 * Created by Thomas on 14.01.14.
 */

import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.beanservice.BrukerService;
import no.hist.tdat.javabeans.utils.MailService;
import no.hist.tdat.javabeans.utils.PassordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class GlemtPassordKontroller {
    @Autowired
    BrukerService service;

    @Autowired
    MailService mailMail;

    @RequestMapping(value = "sendNyttPassord")
    private String endrePassordet(@Valid @ModelAttribute("bruker") Bruker bruker, BindingResult result, HttpServletRequest request, Model modell) {
        if (result.hasErrors()) {
            return "glemtPassord";
        }

        String mail = request.getParameter("mail");
        if(service.hentBruker(mail)==null){
            modell.addAttribute("errorMail", "Feil email!");
            return "glemtPassord";
        }
        try {
            if (service.hentBruker(mail) != null) {
                bruker = service.hentBruker(mail);
                String nyttPassord =  PassordService.genererPassord();
                bruker.setPassord(nyttPassord);
                service.endrePassord(mail, bruker.getPassord());
                modell.addAttribute("nyPassord", "Vellykket! Det nye passordet er sendt p&aring; mail.");
                System.out.println("Nytt Passord: " + nyttPassord + "\n");

                //Mail funksjon
                String sender="noreplay.sks@gmail.com";
                String receiver=mail;
                mailMail.sendMail(sender, receiver, "SKS Passord Service - Hist", "Ditt nye passord er: " + nyttPassord);





                return "loggInn";
            }
        } catch (IndexOutOfBoundsException e) {
            modell.addAttribute("errorMail", "Feil email!");
            System.out.println("Mailen ble ikke funnet i databasen.");
        }
        return "glemtPassord";
    }
}
