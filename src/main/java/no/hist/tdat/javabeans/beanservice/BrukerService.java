package no.hist.tdat.javabeans.beanservice;

import no.hist.tdat.database.DatabaseConnector;
import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.Emne;
import no.hist.tdat.javabeans.Oving;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class BrukerService {

    @Autowired
    DatabaseConnector databaseConnector;

    /**
     * Legger til en bruker i databasen
     *
     * @param bruker den brukeren du skal legge til i databasen
     * @see
     */
    public boolean leggTilBruker(Bruker bruker) throws org.springframework.dao.DuplicateKeyException {
        return databaseConnector.leggTilBruker(bruker);
    }

    /**
     * Sjekker om mail og passord korresponderer
     * Brukerobjekt opprettes av mail og passord før denne kalles.
     *
     * @param bruker brukeren som skal logges inn
     * @return boolean, true om mail og passord korresponderer
     */
    public Bruker loggInn(Bruker bruker) {
        return databaseConnector.loggInn(bruker);
    }

    /**
     * Henter en bruker ut fra databasen
     *
     * @param email brukerens epost
     * @return et brukerobjekt basert på databasedata funnet i databasen. Returnerer null hvis ingenting blir funnet.
     */
    public Bruker hentBruker(String email) {
        ArrayList<Bruker> brukere = databaseConnector.hentBruker(email);
        if(brukere.size()==0){
            return null;
        }
        return brukere.get(0);
    }

    /**
     * Henter en liste med mulige bruker
     *
     * @param input søkeordet
     * @return ArrayList med bruker objecter, eller null om ingen treffer med søkeordet
     */
    public ArrayList<Bruker> finnBruker(String input) {
        return databaseConnector.finnBruker(input);
    }

    /**
     * Henter en liste med mulige studenter
     *
     * @param input søkeordet
     * @return ArrayList med bruker objecter, eller null om ingen treffer med søkeordet
     */
    public ArrayList<Bruker> finnStudenter(String input) {
        return databaseConnector.finnStudenter(input);
    }

    /**
     * Sletter en spesifikk bruker
     *
     * @param mail brukerens mail
     * @return true om bruker ble slettet, ellers false
     */
    public boolean slettBruker(String mail) {
        return databaseConnector.slettBruker(mail);
    }

    /**
     * Endrer passordet til en bruker
     *
     * @param mail og nytt passord
     * @return true om vellykket
     */
    public boolean endrePassord(String mail, String passord) {
        return databaseConnector.endrePassord(mail, passord);
    }

    /**
     * Henter alle medstudenter i emnet du vil sette deg i kø i
     *
     * @param emnekode og mail
     * @return ArrayList med brukerobjekter
     */
    public ArrayList<Bruker> getMedstudenter(String emnekode, String mail) {
        return databaseConnector.finnAlleDeltakere(emnekode, mail);

    }

    public boolean oppdaterBruker(String mail, Bruker bruker) {
        return databaseConnector.oppdaterBruker(bruker, mail);
    }

    /**
     * Gir bruker tilgang til nytt fag
     *
     * @param emnekode og mail
     * @return boolean
     */
    public boolean leggTilEmne(String emnekode, String mail, int foreleser) {
        return databaseConnector.leggTilEmne(emnekode, mail, foreleser);
    }

    /**
     * Fjerner tilgang til emne for bruker
     *
     * @param emnekode og mail
     * @return boolean
     */
    public boolean fjernEmne(String emnekode, String mail) {
        return databaseConnector.fjernEmne(emnekode, mail);
    }

    /**
     * Setter student som studass
     *
     * @param emnekode, delemne og mail
     * @return boolean
     */
    public boolean settStudass(String emnekode, String mail) {
        return databaseConnector.settStudass(emnekode, mail);
    }

    /**
     * Slett studass
     *
     * @param delEmne og mail
     * @return boolean
     */
    public boolean fjernStudass(String delEmne, String mail) {
        return databaseConnector.fjernStudass(delEmne, mail);
    }

    public ArrayList<Bruker> finnStudenterIDelemne(String emne) {
        return databaseConnector.finnStudenterIDelemne(emne);
    }

    /**
     * henter øvinger til bruker
     *
     * @param delEmne og mail
     * @return Arraylist med øvinger
     */
    public ArrayList<Oving> hentOvinger(String delEmne) {
        return databaseConnector.hentOvinger(delEmne);
    }

    /**
     * finner om øving er godkjent
     *
     * @param id og epost
     * @return oving, evt null
     */
    public ArrayList<Oving> hentGodkjOvinger(String epost, String id) {
        return databaseConnector.hentGodkjOvinger(epost, id);
    }

    public boolean leggTilGodkjentOving (int ovings_id, String mail, String godkjennersMail, String datoGodkjent) {
        return databaseConnector.opprettGodkjentOving(ovings_id, mail, godkjennersMail, datoGodkjent);
    }

    public boolean slettKoeGruppe(int koeId, int gruppeId) {
        return databaseConnector.slettKoeGruppe(koeId, gruppeId);
    }

    public ArrayList<Bruker> hentStudenterMedEmne (String emneKode) {
        ArrayList<Bruker> studenter = databaseConnector.hentStudenterMedGittEmne(emneKode);
        return studenter;
    }

    public ArrayList<Emne> hentEmnekodeMedBareKoeID(int KoeIDLOL) {
        return databaseConnector.hentEmneKodeMedKoeId(KoeIDLOL);
    }
}