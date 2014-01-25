package no.hist.tdat.javabeans.beanservice;

import no.hist.tdat.database.DatabaseConnector;
import no.hist.tdat.javabeans.Bruker;
import no.hist.tdat.javabeans.DelEmne;
import no.hist.tdat.javabeans.Emne;
import no.hist.tdat.javabeans.Oving;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmneService {

    @Qualifier("databaseConnector")
    @Autowired
    DatabaseConnector databaseConnector;

    public void hentEmner(Bruker bruker) {
       bruker.setEmne(databaseConnector.hentMineEmner(bruker));
       ArrayList<Emne> emneList  = bruker.getEmne();
       ArrayList<DelEmne> delEmneList;
        ArrayList<Oving> godkjentList;
        ArrayList<Oving> ovingList;
        for(int a=0;a<emneList.size();a++){ // For hvert emne i lista
            Emne tempEmne = emneList.get(a);
            delEmneList = databaseConnector.hentDelemner(bruker,tempEmne);
            tempEmne.setDelemner(delEmneList);
            for (int b = 0; b <delEmneList.size(); b++) {   //for hvert delemne pr emne
                DelEmne tempDelEmne = delEmneList.get(b);
                ovingList=databaseConnector.hentDelEmneOving(tempDelEmne.getNr(), tempEmne.getEmneKode());
                godkjentList = databaseConnector.hentStudOvinger(bruker,tempEmne,tempDelEmne);
                Oving tempOving;
                for (int c = 0; c < godkjentList.size(); c++) {
                    tempOving = godkjentList.get(c);
                    tempOving.setGodkjent(true);
                    ovingList.set(tempOving.getOvingnr()-1,tempOving);
                }
                tempDelEmne.setStudentovinger(ovingList);
            }
        }
    }

    /**
     * Endrer på koe_statusen, fra åpen til lukket.
     * @param koeId
     * @param status
     * @return boolean, vellykket eller ikke.
     *
     */
    public boolean endreKoeStatus(int koeId, int status){
        return databaseConnector.endreKoeStatus(koeId, status);
    }

    /**
     * Henter alle emner knyttet til en bruker (mail)
     *
     * @param mail, unik identifikator
     * @return liste over alle emner
     */
   public ArrayList<Emne> hentEmnerForStud(String mail) {
        return databaseConnector.hentEmnerForStud(mail);
    }

    public DelEmne hentDelEmne (int koe_id){
        return databaseConnector.hentDelEmne(koe_id);
    }

    /** NB! IKKE SAMME SOM DEN OVER!
     * Henter delemne, gitt navn
     *
     * @param navn
     * @return delemne
     */
    public DelEmne hentDelemne(String navn){
        return databaseConnector.hentDelemne(navn);
    }

    /**
     * Oppretter et emne
     *
     * @param emne
     * @return boolean
     */
    public boolean opprettEmne(Emne emne) throws org.springframework.dao.DuplicateKeyException{
        return databaseConnector.opprettEmne(emne);
    }

    /**
     * Henter emne, gitt navn på delemne
     *
     * @param navn
     * @return emne
     */
    public Emne hentEmne(String navn){
        return databaseConnector.hentEmne(navn);
    }
    public Emne hentEmneNavn(String emnekode){
        return databaseConnector.hentEmneNavn(emnekode);
    }
    public ArrayList<Oving> hentDelEmneOving(Bruker bruker, Emne emne, DelEmne delEmne){
        return databaseConnector.hentStudOvinger(bruker,emne,delEmne);
    }

    public boolean opprettDelemne(DelEmne delEmne, Emne emne) throws org.springframework.dao.DuplicateKeyException{
        return databaseConnector.opprettDelemne(delEmne, emne);
    }

    /**
     * Henter en liste med mulige bruker
     *
     * @param input søkeordet
     * @return ArrayList med bruker objecter, eller null om ingen treffer med søkeordet
     */
    public ArrayList<Emne> finnEmne(String input) {
        return databaseConnector.finnEmne(input);
    }

    public boolean slettEmne(String emnekode){
        return databaseConnector.slettEmne(emnekode);
    }

    public boolean oppdaterEmne(String emnekode, Emne emne) {
        return databaseConnector.oppdaterEmne(emne, emnekode);
    }

    public boolean opprettOving(int i, DelEmne delemne) {
        return databaseConnector.opprettOving(i, delemne);
    }

    public boolean lagRegler(String regler, int ant, DelEmne delemne) {
        return databaseConnector.legRegler(regler, ant, delemne);
    }

    public boolean slettDelEmne (String emnekode, String delEmneNr){
        return databaseConnector.slettDelEmne(emnekode, delEmneNr);
    }
    public boolean slettEmneansvarlig(String emneKode,String mail){
        return databaseConnector.slettEmneansvarlig(emneKode, mail);
    }
}