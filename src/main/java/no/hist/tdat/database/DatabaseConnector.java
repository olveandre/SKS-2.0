package no.hist.tdat.database;

import no.hist.tdat.database.verktoy.*;
import no.hist.tdat.javabeans.*;
import no.hist.tdat.koe.KoeBruker;
import no.hist.tdat.kontrollere.GodkjennKontroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DatabaseConnector kobler til databasen og gj�r sp�rring, for deretter � stenge tilkoblingen.
 *
 * @author VimCnett
 */

@Service
public class DatabaseConnector {
    private static final String QUERY_ERROR = "FEIL I SP�RRING";
    private static final String CONNECTION_ERROR = "FEIL VED TILKOBLING TIL DATABASE";
    private static final Integer ACTIVE = 1;

    // **** Legger alle Queryes her. Ikke fordi vi m�, men fordi Grethe liker det s�nn...*/ //TODO remove this
    private final String getKoeSQL = "";
    private final String hentBrukerSQL = "SELECT * FROM brukere WHERE mail = ?";
    private final String hentGruppeOvingerSQL = "SELECT oving_nr, oving_id FROM gruppe_oving NATURAL JOIN oving WHERE gruppe_oving.gruppe_id=?";
    private final String hentGruppeMedlemmerSQL = "SELECT * FROM gruppe NATURAL JOIN brukere WHERE gruppe_id=? ORDER BY gruppe.leder DESC";
    private final String hentkoeGrupperSQL = "Select * FROM koe_gruppe WHERE koe_id= ? ORDER BY koe_gruppe.koe_plass ASC";
    private final String brukerOvingerSQL = "SELECT * FROM oving_brukere NATURAL JOIN oving WHERE oving_brukere.mail = ? AND emnekode = ? AND delemne_nr = ?";  //returnerer godkjente øvinger til et delemne
    private final String brukerDelemnerSQL = "SELECT * FROM emner JOIN delemne ON emner.emnekode = delemne.emnekode JOIN emner_brukere ON delemne.emnekode = emner_brukere.emnekode AND emner_brukere.mail=? AND delemne.emnekode=?";
    private final String brukerEmnerSQL = "SELECT * FROM emner, emner_brukere WHERE emner.emnekode = emner_brukere.emnekode AND emner_brukere.mail = ?";
    private final String loggInnBrukerSQL = "SELECT * FROM brukere WHERE mail = ? AND passord = ?";
    private final String leggTilBrukerSQL = "INSERT INTO brukere (mail, rettighet_id, fornavn, etternavn, passord, aktiv) VALUES (?,?,?,?,?,?)";
    private final String oppdaterBrukerSQL = "UPDATE brukere SET mail = ?, rettighet_id = ?, fornavn = ?, etternavn = ?, aktiv = ? WHERE mail = ?";
    private final String finnBrukerSQL = "SELECT * FROM brukere WHERE mail LIKE ? OR fornavn LIKE ? OR etternavn LIKE ?";
    private final String slettBrukerSQL = "DELETE FROM brukere WHERE mail = ?";
    private final String finnAlleDeltakereSQL = "SELECT * FROM brukere, emner_brukere WHERE brukere.mail = emner_brukere.mail AND emner_brukere.emnekode = ? AND brukere.rettighet_id = 1 AND brukere.mail != ?";
    private final String endrePassordSQL = "UPDATE brukere SET passord = ? WHERE mail = ? ";
    private final String endreKoeStatusSQL = "UPDATE koe SET aapen = ? WHERE koe_id = ?";
    private final String finnStudentSQL = "SELECT * FROM brukere WHERE rettighet_id=1 AND mail = ? OR fornavn = ? OR etternavn = ?";
    private final String hentEmnerForBrukerSQL = "SELECT * FROM emner_brukere JOIN emner ON emner_brukere.emnekode = emner.emnekode WHERE mail LIKE ?";
    private final String finnAllePlasserSQL = "SELECT * FROM plassering";
    private final String oppdaterOvingSQL = "UPDATE oving_brukere SET godkjent_av = ?, godkjent_tid = ? WHERE mail = ? AND oving_id = ?";
    private final String finnOvingerSQL = "SELECT * FROM koe_gruppe, gruppe, gruppe_oving WHERE koe_gruppe.gruppe_id = gruppe.gruppe_id AND gruppe.gruppe_id = gruppe_oving.gruppe_id AND gruppe.mail = ? AND koe_gruppe.koe_id = ? AND koe_gruppe.koe_plass = ?";
    private final String finnAntBordSQL = "SELECT ant_bord FROM plassering WHERE plassering_navn = ?";
        //Legg til I Kø
    private final String leggTilKoGruppeSQL = "INSERT INTO koe_gruppe (koe_id, gruppe_id, plassering_navn, bordnummer, info, koe_plass, tidspunkt) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
    private final String maxKoe_PlassSQL = "SELECT * FROM koe_gruppe WHERE koe_id = ? ORDER BY koe_plass DESC";
    private final String maxGruppeIdSQL = "SELECT * FROM koe_gruppe WHERE koe_id = ? ORDER BY gruppe_id DESC";
    private final String leggtilGruppeOvingSQL = "INSERT INTO gruppe_oving (gruppe_id, koe_id ,oving_id) VALUES (?, ?, ?) ";
    private final String leggTilGruppeMedlemSQL = "INSERT INTO gruppe (koe_id, gruppe_id, mail, leder) VALUES (?, ?, ?, ?)";
    private final String finnOvingIDSQL = "SELECT * FROM oving WHERE oving_nr = ? AND emnekode = ? AND delemne_nr = ?";
        //Admin Emne
    private final String finnEmneSQL = "SELECT * FROM emner WHERE emnekode LIKE ? OR emnenavn LIKE ?";
    private final String slettEmneSQL = "DELETE FROM emner WHERE emnekode = ?";
    private final String oppdaterEmneSQL = "UPDATE emner SET emnekode = ?, emnenavn = ? WHERE emnekode = ?";

    private final String finnDelEmneSQL = "SELECT * FROM delemne WHERE koe_id LIKE ?";
    private final String hentKoeObjektSQL = "SELECT * FROM koe WHERE koe_id LIKE ? ";
    private final String leggTilEmneSQL = "INSERT INTO emner_brukere (emnekode, mail, foreleser) VALUES (?,?,?)";
    private final String opprettEmneSQL = "INSERT INTO emner (emnekode, emnenavn) VALUES (?,?)";
    private final String opprettDelemneSQL = "INSERT INTO delemne (delemne_nr, emnekode, koe_id, delemnenavn, semester) VALUES (?,?,?,?,?)";
    private final String fjernEmneSQL = "DELETE FROM emner_brukere WHERE mail = ? AND emnekode = ?";
    private final String settStudassSQL = "INSERT INTO delemne_brukere(mail,emnekode,delemne_nr) VALUES (?, (SELECT emnekode FROM delemne WHERE delemnenavn LIKE ?), (SELECT delemne_nr FROM delemne WHERE delemnenavn LIKE ?))";
    private final String fjernStudassSQL = "DELETE FROM delemne_brukere WHERE mail LIKE ? AND emnekode LIKE (SELECT emnekode FROM delemne WHERE delemnenavn LIKE ?) AND delemne_nr = (SELECT delemne_nr FROM delemne WHERE delemnenavn LIKE ?)";

    private final String finnStudenterIDelemneSQL = "SELECT DISTINCT brukere.mail, brukere.fornavn, brukere.etternavn, brukere.rettighet_id, brukere.passord, brukere.aktiv FROM brukere JOIN emner_brukere ON brukere.mail LIKE emner_brukere.mail WHERE emner_brukere.emnekode LIKE (SELECT emnekode FROM delemne WHERE delemnenavn LIKE ?) AND emner_brukere.foreleser = 0";
    private final String delemneIKoeSQL = "INSERT INTO koe (aapen) VALUES (?)";
    private final String hentSisteKoeSQL = "SELECT koe_id FROM koe ORDER BY koe_id DESC";
    private final String hentDelEmneOvingSQL = "SELECT * FROM oving WHERE emnekode = ? AND delemne_nr = ?";
    private final String hentDelemneSQL = "SELECT * FROM delemne WHERE delemnenavn LIKE ?";
    private final String opprettOvingSQL = "INSERT INTO oving (oving_nr, emnekode, delemne_nr) VALUES (?,?,?)";
    private final String lagReglerSQL = "UPDATE delemne SET ovingsregler = ?, ant_ovinger = ? WHERE delemne_nr = ?";

    //Brukes til å godkjenn gruppe hent bruker shiiet
    private final String opprettGodkjentOvingSQL = "INSERT INTO oving_brukere (oving_id, mail, godkjent_av, godkjent_tid) VALUE (?,?,?,?)";
    private final String fjernKoeGruppeFraKoeSQL = "DELETE FROM koe_gruppe WHERE gruppe_id = ? AND koe_id = ?";
    //SQL SETNING som henter ALLE studenter med et gitt emne. sortert etter etternavn!
    private final String hentStudentMedEmneSQL = "SELECT * FROM brukere, emner_brukere WHERE brukere.mail = emner_brukere.mail  AND emner_brukere.emnekode = ? AND brukere.aktiv = 1 AND brukere.rettighet_id = 3 ORDER BY brukere.etternavn";


    private final String hentOvingerSQL = "SELECT * FROM oving WHERE emnekode = (SELECT emnekode FROM delemne WHERE delemnenavn = ?) AND delemne_nr = (SELECT delemne_nr FROM delemne WHERE delemnenavn = ?)";
    private final String hentEmneSQL = "SELECT * FROM emner WHERE emnekode = (SELECT emnekode FROM delemne WHERE delemnenavn = ?)";
    private final String sjekkGodkjentSQL = "SELECT * FROM oving NATURAL JOIN oving_brukere WHERE oving_brukere.mail = ? AND oving.emnekode = (SELECT emnekode FROM delemne WHERE delemnenavn = ?) AND oving.delemne_nr = (SELECT delemne_nr FROM delemne WHERE delemnenavn = ?)";
    @Autowired
    private DataSource dataKilde; //Felles datakilde for alle sp�rringer.

    /**
     *
     * @param emneKodeLol   Alle studenter (rettighet = 3) for denne emnekoden trololo
     * @return              Liste med brukere
     */
    public ArrayList<Bruker> hentStudenterMedGittEmne(String emneKodeLol) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> studentList = con.query(hentStudentMedEmneSQL, new BrukerKoordinerer(), emneKodeLol);
        ArrayList<Bruker> output = new ArrayList<>();
        for (Bruker denne : studentList) {
            output.add(denne);
        }
        return output;
    }

    public boolean opprettGodkjentOving(int oving_id, String mail, String godkjentAvMail, String datoGodkjent) {

        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(opprettGodkjentOvingSQL,
                oving_id, mail,
                godkjentAvMail, datoGodkjent);
        return true;
    }
    /**
     *
     * @param mail
     * @param koe_id
     * @param koe_plass
     * @return
     */
    public ArrayList<KoeGrupper> hentBrukerFraKo(String mail, int koe_id, int koe_plass) {
        if (mail == null) {
            return null;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<KoeGrupper> koeGrupperList = con.query(finnOvingerSQL, new KoeGruppeKoordinerer(), mail, koe_id, koe_plass);
        ArrayList<KoeGrupper> output = new ArrayList<>();
        for (KoeGrupper denne : koeGrupperList) {
            output.add(denne);
        }
        return output;
    }

    public ArrayList<DelEmne> hentDelemner(Bruker bruker, Emne emne) {
        if (bruker == null) {
            return null;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<DelEmne> delemneList = con.query(brukerDelemnerSQL, new DelEmneKoordinerer(), bruker.getMail(), emne.getEmneKode());
        ArrayList<DelEmne> output = new ArrayList<>();
        for (DelEmne denne : delemneList) {
            output.add(denne);
        }
        return output;
    }

    public ArrayList<Oving> hentStudOvinger(Bruker bruker, Emne emne, DelEmne delemne) {
        if (bruker == null) {
            return null;
        }

        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Oving> ovingList = con.query(brukerOvingerSQL, new OvingKoordinerer(), bruker.getMail(), emne.getEmneKode(), delemne.getNr());
        ArrayList<Oving> output = new ArrayList<>();//TODO Hent alle ovinger for alle fag!
        for (Oving denne : ovingList) {
            output.add(denne);
        }
        return output;
    }

    /**
     * Denne klassen skal kun brukes av TESTPROGRAMMET ikke noe annet.
     *
     * @param dataKilde testdatakilde
     */
    public void setDataKilde(DataSource dataKilde) { //TODO (OR NOT TODO) IKKE LOV TIL � BRUKE!!!!!!!!!!
        this.dataKilde = dataKilde;
    }

    /**
     * Legger til en bruker i databasen
     *
     * @param bruker
     * @return true om den blir lagt til, ellers false
     */
    public boolean leggTilBruker(Bruker bruker) throws org.springframework.dao.DuplicateKeyException {
        if (bruker == null) {
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(leggTilBrukerSQL,
                bruker.getMail(),
                bruker.getRettighet(),
                bruker.getFornavn(),
                bruker.getEtternavn(),
                bruker.getPassord(),
                bruker.getAktiv());
        return true;
    }

    /**
     * Oppdatterer en spesifikk bruker
     *
     * @param bruker Den brukeren du il endre
     * @param mail   mailen til den du skal endre, denne er i tilfelle man endrer mail
     * @return true om bruker blir oppdatert ellers false
     */
    public boolean oppdaterBruker(Bruker bruker, String mail) {
        if (bruker == null) {
            return false;
        } else {
            JdbcTemplate con = new JdbcTemplate(dataKilde);
            con.update(oppdaterBrukerSQL,
                    bruker.getMail(),
                    bruker.getRettighet(),
                    bruker.getFornavn(),
                    bruker.getEtternavn(),
                    bruker.getAktiv(),
                    mail);
            return true;
        }
    }

    /**
     * Tar inn en string som s�keord, s�ker i databasen etter mail, fornavn, etternavn som ligner p� s�keordet.
     *
     * @param soeketekst S�keord etter bruker
     * @return ArrayList med bruker objekter eller null om ingen finnes.
     */
    public ArrayList<Bruker> finnBruker(String soeketekst) {
        if (soeketekst == null) {
            return null;
        }
        String input = "%";
        input += soeketekst + "%";
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> brukerList = con.query(finnBrukerSQL, new BrukerKoordinerer(), input, input, input);
        ArrayList<Bruker> res = new ArrayList<>();

        for (Bruker bruker : brukerList) {
            res.add(bruker);
        }
        return res;
    }

    public ArrayList<Bruker> hentBruker(String mail) {
        if (mail == null) {
            return null;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> brukerList = con.query(hentBrukerSQL, new BrukerKoordinerer(), mail);
        ArrayList<Bruker> res = new ArrayList<>();

        for (Bruker bruker : brukerList) {
            res.add(bruker);
        }
        return res;
    }

    /**
     *
     * @param mail
     * @param oving_id
     * @param godkjentAvMail
     * @param datoGodkjent
     * @return
     */
    public boolean oppdaterOving(String mail, int oving_id, String godkjentAvMail, Date datoGodkjent) {

        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(oppdaterOvingSQL,
                mail, oving_id,
                godkjentAvMail, datoGodkjent);
        return true;

    }

    /**
     * Tar inn en string som s�keord, s�ker i databasen etter mail, fornavn, etternavn som ligner p� s�keordet.
     *
     * @param soeketekst S�keord etter studentr
     * @return ArrayList med bruker objekter eller null om ingen finnes.
     */
    public ArrayList<Bruker> finnStudenter(String soeketekst) {
        if (soeketekst == null) {
            return null;
        }
        String input = "%";
        input += soeketekst + "%";
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> brukerList = con.query(finnStudentSQL, new BrukerKoordinerer(), input, input, input);
        ArrayList<Bruker> res = new ArrayList<>();

        for (Bruker bruker : brukerList) {
            res.add(bruker);
        }
        return res;
    }

    /**
     * Sjekker om mail og passord korresponderer
     *
     * @param bruker brukerobjekt med kun mail og passord
     * @return nytt brukerobjekt med all brukerinformasjon
     */
    public Bruker loggInn(Bruker bruker) {
        if (bruker == null) {
            return null;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> brukerList = con.query(loggInnBrukerSQL, new BrukerKoordinerer(), bruker.getMail(), bruker.getPassord());
        ArrayList<Bruker> res = new ArrayList<>();

        for (Bruker brukerObj : brukerList) {

            res.add(brukerObj);
        }

        if (res.size() > 0) {

            return res.get(0);
        }

        return null;
    }

    /**
     * Henter emnene en bruker har
     *
     * @param bruker
     * @return en ArrayList med emner.
     */
    public ArrayList<Emne> hentMineEmner(Bruker bruker) {
        if (bruker == null) {
            return null;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Emne> emneList = con.query(brukerEmnerSQL, new EmneBrukerKoordinerer(), bruker.getMail());
        ArrayList<Emne> res = new ArrayList<>();
        for (Emne emne : emneList) {
            res.add((Emne) emne);
        }


        if (res.size() > 0) {
            return res;
        }
        return null;
    }

    /**
     * Sletter bruker med gitt epost.
     *
     * @param epost eposten til den brukeren som skal slettes fra databasen
     * @return true hvis en eller flere rader fra tabellen har blitt slettet. false hvis ingen rader blir slettet.
     */
    public boolean slettBruker(String epost) {
        if (epost == null)
            return false;
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        int num = con.update(slettBrukerSQL, epost);
        return num > 0;

    }

    public boolean slettKoeGruppe(int koeId, int gruppeId)  {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        int num = con.update(fjernKoeGruppeFraKoeSQL, koeId, gruppeId);
        return num > 0;
    }

    /**
     * Tar inn en string som s�keord, s�ker i databasen etter mail, fornavn, etternavn som er lik s�keordet.
     *
     * @param soeketekst S�keord etter studenter
     * @return objekt av Bruker, eller null om den ikke finnes
     */
    public Bruker finnStudent(String soeketekst) {

        if (soeketekst == null) {
            return null;
        }

        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> brukerList = con.query(finnStudentSQL, new BrukerKoordinerer(), soeketekst, soeketekst, soeketekst);

        return brukerList.get(0);
    }

    /**
     * Tar inn mailen til brukeren som skal endrest samt det nye passordet.
     *
     * @param passord, det nye passordet
     * @param mail,    mailen til brukeren
     * @return true dersom vellykket
     */
    public boolean endrePassord(String mail, String passord) {
        if (mail == null) {
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(endrePassordSQL,
                passord,
                mail);
        return true;
    }

    public boolean endreKoeStatus(int koe_id, int status) {
        if (koe_id == 0 || status > 1) {
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(endreKoeStatusSQL,
                status,
                koe_id);
        return true;
    }

    /**
     * Tar inn en string som s�keord, s�ker i databasen etter mail, fornavn, etternavn som ligner p� s�keordet.
     *
     * @param emnekode Hvilket emne man vil sette seg i k� i
     * @return ArrayList med alle studenter i samme emne
     */
    public ArrayList<Bruker> finnAlleDeltakere(String emnekode, String mail) {

        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> brukerList = con.query(finnAlleDeltakereSQL, new BrukerKoordinerer(), emnekode, mail);
        ArrayList<Bruker> res = new ArrayList<Bruker>();

        for (Bruker bruker : brukerList) {
            res.add(bruker);
        }
        return res;

    }

    /**
     * Tar inn en string som s�keord, s�ker i databasen etter mail, fornavn, etternavn som ligner p� s�keordet.
     *
     * @param mail id-mail
     * @return ArrayList med alle emner
     */
    public ArrayList<Emne> hentEmnerForStud(String mail) {
        if (mail == null) {
            return null;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Emne> emneList = con.query(hentEmnerForBrukerSQL, new EmneKoordinerer(), mail);
        ArrayList<Emne> res = new ArrayList<>();
        for (Emne emne : emneList) {
            res.add(emne);
        }
        return res;
    }

    /**
     * @return Liste over alle mulige plasseringer
     * @author henriette
     * Henter ut alle mulige plasser man kan sitte for � f� �ving retta
     */
    public ArrayList<Plassering> finnAllePlasseringer() {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Plassering> plassListe = con.query(finnAllePlasserSQL, new PlasseringKoordinerer());
        ArrayList<Plassering> res = new ArrayList<Plassering>();

        for (Plassering plass : plassListe) {
            res.add(plass);
        }
        return res;
    }

    /**
     * @param plasseringNavn plassen som har bordet man sitter p�
     * @return alle mulige bord p� den valgte plassen
     * @author henriette
     * Henter ut alle mulige bord p� en gitt plassering
     */
    public int getAntallBord(String plasseringNavn) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Plassering> plassListe = con.query(finnAntBordSQL, new PlasseringKoordinerer(), plasseringNavn);
        ArrayList<Plassering> res = new ArrayList<Plassering>();
        for (Plassering plass : plassListe) {
            res.add(plass);
        }
        return plassListe.get(0).getAnt_bord();
    }

    /**
     * legger til emne for bruker, gitt mail
     *
     * @param mail id-mail, emnekode, foreleser
     * @return boolean
     */
    public boolean leggTilEmne(String emnekode, String mail, int foreleser) {
        if (mail == null || emnekode == null) {
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        try {
            con.update(leggTilEmneSQL, emnekode, mail, foreleser);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Fjerner tilgang til emne
     *
     * @param mail id-mail og emnekode
     * @return boolean
     */
    public boolean fjernEmne(String emnekode, String mail) {
        if (mail == null || emnekode == null) {
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        try {
            con.update(fjernEmneSQL, mail, emnekode);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public ArrayList<Bruker> getBrukereIKoeGruppe(int gruppeId) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> brukereFraDb = con.query(hentGruppeMedlemmerSQL, new BrukerKoordinerer(), gruppeId);
        ArrayList<Bruker> brukere = new ArrayList<>();
        Bruker bruker;
        for (int i = 0; i < brukereFraDb.size(); i++) {
            bruker = brukereFraDb.get(i);
            brukere.add(bruker);
        }
        return brukere;
    }

    public ArrayList<Oving> getOvingerIKoeGruppe(int gruppeId) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Oving> ovingerFraDb = con.query(hentGruppeOvingerSQL, new KoeOvingKoordinerer(), gruppeId);
        ArrayList<Oving> ovinger = new ArrayList<>();
        Oving oving;
        for (int i = 0; i < ovingerFraDb.size(); i++) {
            oving = ovingerFraDb.get(i);
            ovinger.add(oving);
        }
        return ovinger;
    }

    public ArrayList<KoeGrupper> getKoe(int koeId) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<KoeGrupper> grupperFromDb = con.query(hentkoeGrupperSQL, new KoeGruppeKoordinerer(), koeId);
        ArrayList<KoeGrupper> grupper = new ArrayList<>();
        KoeGrupper koeGrupper;
        for (int i = 0; i < grupperFromDb.size(); i++) {
            koeGrupper = grupperFromDb.get(i);

            koeGrupper.setMedlemmer(getBrukereIKoeGruppe(koeGrupper.getGruppeID()));
            koeGrupper.setOvinger(getOvingerIKoeGruppe(koeGrupper.getGruppeID()));
            grupper.add(koeGrupper);
        }
        if (grupper.size() > 0) {
            return grupper;
        }
        return null;
    }

    public DelEmne hentDelEmne(int koe_id) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<DelEmne> delEmne = con.query(finnDelEmneSQL, new DelEmneKoordinerer(), koe_id);
        return delEmne.get(0);
    }

    /**
     * @param koeGruppe
     * @return true or false, om updaten gjekk gjennom eller ikke
     * Author Thomas
     */
    public boolean leggTilKoGruppe(KoeGrupper koeGruppe, String oving, int gruppeID) {
        if (koeGruppe == null) {
            return false;
        }
        System.out.println(
                "Ko_id: " +  koeGruppe.getKoe_id() + ", " +
                "Max_gruppe: " + finnMaxGruppeId(koeGruppe.getKoe_id()) + ", " +
                "Sitteplass: " + koeGruppe.getSitteplass() + ", " +
                "Bordnr: " + koeGruppe.getBordnr() +", " +
                "Kommentar: " + koeGruppe.getKommentar() + ", " +
                "Max_plass: " + finnMaxKoe_plass(koeGruppe.getKoe_id()));
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(
                leggTilKoGruppeSQL,                                   //koe_id, gruppe_id, plassering_navn, bordnummer, info, koe_plass, tidspunkt
                koeGruppe.getKoe_id(),
                gruppeID,
                koeGruppe.getSitteplass(),
                koeGruppe.getBordnr(),
                koeGruppe.getKommentar(),
                finnMaxKoe_plass(koeGruppe.getKoe_id())
        );
        return true;
    }
    public int finnMaxKoe_plass(int koe_id){
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<KoeGrupper> list = con.query(
                maxKoe_PlassSQL,
                new KoeGruppeKoordinerer(),
                koe_id
        );
        return (list.get(0).getKoePlassering()+1);
    }
    public int finnMaxGruppeId(int koe_id){
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<KoeGrupper> list = con.query(
                maxGruppeIdSQL,
                new KoeGruppeKoordinerer(),
                koe_id
        );
        return (list.get(0).getKoePlassering()+1);
    }
    public int finnOving_id(int oving_nr, String emnekode, int delemne_nr){    //oving_nr, emnekode, delemne_nr
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Oving> list = con.query(
                finnOvingIDSQL,
                new KoeOvingKoordinerer(),
                oving_nr,
                emnekode,
                delemne_nr
        );
        return (list.get(0).getOvingid());
    }
    /**
     *
     * @param gruppe_id
     * @param mail
     * @param leder
     * @return
     */
    public boolean leggTilGruppeMedlem(int koe_id,int gruppe_id, String mail, int leder){           //koe_id,  gruppe_id, mail, leder(0 elr 1)
        System.out.println(
                "Ko_id: " +  koe_id + ", " +
                        "GruppeID: " + gruppe_id + ", "  +
                        "Mail: " + mail + ", " +
                        "Leder: " + leder + "");
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(leggTilGruppeMedlemSQL,
                    koe_id,
                    gruppe_id,
                    mail,
                    leder);
        return true;
    }

    /**
     *
     * @param gruppe_id
     * @param koe_id
     * @param oving_id
     * @return
     */
    public boolean leggTilGruppeOving(int gruppe_id, int koe_id, int oving_id){      //gruppe_id, koe_id ,oving_id
        System.out.println(
                "Ko_id: " +  koe_id + ", " +
                        "GruppeID: " + gruppe_id + ", "  +
                        "ØvingID: " + oving_id);
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(leggtilGruppeOvingSQL,
                gruppe_id,
                koe_id,
                oving_id);
        return true;
    }

    /**
     *
     * @param koe_id
     * @return
     */
    public DelEmne getKoeObjekt(int koe_id){
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<DelEmne> delEmne = con.query(hentKoeObjektSQL, new KoestatusDelEmneKoordinerer(), koe_id);
        return delEmne.get(0);
    }

    /**
     * Setter studass
     *
     * @param mail id-mail og emnekode og delemne
     * @return boolean
     */
    public boolean settStudass(String emnenavn,String mail) {
        if (mail == null || emnenavn == null) {
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        try {
            con.update(settStudassSQL, mail, emnenavn, emnenavn);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Fjerner studass
     *
     * @param mail id-mail, delemne
     * @return boolean
     */
    public boolean fjernStudass(String navn, String mail) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        try {
            con.update(fjernStudassSQL, mail, navn, navn);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Henter delemne, gitt navn
     *
     * @param navn
     * @return boolean
     */
    public DelEmne hentDelemne(String navn) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<DelEmne> emne = con.query(hentDelemneSQL, new DelEmneKoordinerer(), navn);
        return emne.get(0);
    }

     /**
     * Oppretter et emne
     *
<<<<<<< HEAD
=======
     *Oppretter et emne
>>>>>>> d5dfd37cb7e5e6713bbca8edb8130281dca855ff
     * @param emne
     * @return boolean
     * @throws org.springframework.dao.DuplicateKeyException
     */
    public boolean opprettEmne(Emne emne) throws org.springframework.dao.DuplicateKeyException {
        if (emne == null) {
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(opprettEmneSQL,
                emne.getEmneKode(),
                emne.getEmneNavn());
        return true;
    }


    public boolean opprettDelemne(DelEmne delEmne, Emne emne) throws org.springframework.dao.DuplicateKeyException {
        if (delEmne == null) {
            return false;
        }
        delEmne.setKoe_id(delemneIKoe().getKoeId());
        delEmne.setEmneKode(emne.getEmneKode());
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(opprettDelemneSQL,
                delEmne.getNr(),
                emne.getEmneKode(),
                delEmne.getKoe_id(),
                delEmne.getDelEmneNavn(),
                delEmne.getSemester());
        return true;
    }

    public Koe delemneIKoe(){
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(delemneIKoeSQL,0);
        List <Koe> alle = con.query(hentSisteKoeSQL, new KoeKoordinator());
        ArrayList<Koe> res = new ArrayList<Koe>();
        for (Koe koe : alle) {
            res.add(koe);
        }
        return res.get(0);
    }

    /**
     * Author Thomas
     * @param delemne_nr
     * @param emnekode
     * @return
     */
    public ArrayList<Oving> hentDelEmneOving(int delemne_nr, String emnekode){
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Oving> alle = con.query(hentDelEmneOvingSQL, new OvingKoordinerer(),emnekode ,delemne_nr);
        ArrayList<Oving> res = new ArrayList<Oving>();
        for (Oving oving : alle) {
            res.add(oving);
        }
        return res;
    }

    /**
     * @param navn
     * @return tab over alle treff
     */
    public ArrayList<Bruker> finnStudenterIDelemne(String navn) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Bruker> plassListe = con.query(finnStudenterIDelemneSQL, new BrukerKoordinerer(), navn);
        ArrayList<Bruker> res = new ArrayList<Bruker>();

        for (Bruker plass : plassListe) {
            res.add(plass);
        }
        return res;
    }

    /**
<<<<<<< HEAD
     * Henter alle �vinger til en student i et delemne
     * @param navn, epost
=======
     * Henter alle �vinger til en student i et delemne
     * @param navn
>>>>>>> 717f5655302668a2e1996e1e59354d8931d6383a
     * @return tab over alle treff
     */
    public ArrayList<Oving> hentOvinger(String navn) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Oving> plassListe = con.query(hentOvingerSQL, new OvingKoordinerer(), navn, navn);
        ArrayList<Oving> res = new ArrayList<Oving>();
        for (Oving plass : plassListe) {
            res.add(plass);
        }
        return res;
    }

    /**
     * Henter emne, gitt navn p� delemne
     * @param navn
     * @return emnet
     */
    public Emne hentEmne(String navn) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Emne> emne = con.query(hentEmneSQL, new EmneKoordinerer(), navn);
        return emne.get(0);
    }

    /**
     * Tar inn en string som s�keord, s�ker i databasen etter emnekode, emnenavn som ligner p� s�keordet.
     */
    public ArrayList<Emne> finnEmne(String soeketekst) {
        if (soeketekst == null) {
            return null;
        }
        String input = "%";
        input += soeketekst + "%";
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Emne> emnerList = con.query(finnEmneSQL, new EmneKoordinerer(), input, input);
        ArrayList<Emne> res = new ArrayList<>();

        for (Emne emner : emnerList) {
            res.add(emner);
        }
        return res;
    }
    public boolean slettEmne(String emnekode){
        if(emnekode==null){
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(slettEmneSQL, emnekode);
        return true;
    }
    public boolean oppdaterEmne(Emne emne, String emnekode){
        if(emne==null || emnekode==null){
            return false;
        }
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(oppdaterEmneSQL,
                emne.getEmneKode(),
                emne.getEmneNavn(),
                emnekode);
        return true;
    }
    public boolean opprettOving(int i, DelEmne delemne) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(opprettOvingSQL,
                i+1,
                delemne.getEmneKode(),
                delemne.getNr());
        return true;
    }

    public boolean legRegler(String regler, int ant, DelEmne delemne) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        con.update(lagReglerSQL,
                regler,
                ant,
                delemne.getNr());
        return true;
    }

    /**
     * Henter emne, gitt navn p� delemne
     * @param epost, emne
     * @return �vinger
     */
    public ArrayList<Oving> hentGodkjOvinger(String epost, String emne) {
        JdbcTemplate con = new JdbcTemplate(dataKilde);
        List<Oving> o = con.query(sjekkGodkjentSQL, new OvingKoordinerer(), epost, emne, emne);

        ArrayList<Oving> res = new ArrayList<Oving>();
        for (Oving plass : o) {
            res.add(plass);
        }
        return res;
    }
}
