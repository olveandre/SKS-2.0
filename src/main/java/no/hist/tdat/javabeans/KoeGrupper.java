package no.hist.tdat.javabeans;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thomas on 15.01.14.
 */
public class KoeGrupper {
    private Date klokkeslett;
    private int gruppeID;
    private int koePlassering;
    private Bruker gruppeLeder; // TODO fjernes
    @NotBlank
    private String sitteplass;
    @NotNull
    private int bordnr;
    private int koe_id;
    private String kommentar;
    private ArrayList<Bruker>medlemmer;
    private ArrayList<Integer>ovingnr;
    private ArrayList<Oving>ovinger;  // hvilke øvinger som ønskes godkjent
    private String faarHjelp;


    public KoeGrupper(){
        kommentar="";
    }


    public KoeGrupper(int koe_id, Date klokkeslett, int koePlassering, Bruker gruppeLeder, String sitteplass, int bordnr, String kommentar, ArrayList<Bruker> medlemmer, ArrayList<Oving> ovinger) {

        this.koe_id=koe_id;
        this.klokkeslett = klokkeslett;
        this.koePlassering = koePlassering;
        this.gruppeLeder = gruppeLeder;
        this.sitteplass = sitteplass;
        this.bordnr = bordnr;
        this.kommentar = kommentar;
        this.medlemmer = medlemmer;
        this.ovinger = ovinger;
    }


    public int getKoePlassering() {
        return koePlassering;
    }

    public void setKoePlassering(int koePlassering) {
        this.koePlassering = koePlassering;
    }

    public String getFaarHjelp() {
        return faarHjelp;
    }

    public void setFaarHjelp(String faarHjelp) {
        this.faarHjelp = faarHjelp;
    }

    public Date getKlokkeslett() {
        return klokkeslett;
    }

    public void setKlokkeslett(Date klokkeslett) {
        this.klokkeslett = klokkeslett;
    }

    public Bruker getGruppeLeder() {
        return gruppeLeder;
    }

    public void setGruppeLeder(Bruker gruppeLeder) {
        this.gruppeLeder = gruppeLeder;
    }

    public int getGruppeID() {
        return gruppeID;
    }

    public void setGruppeID(int gruppeID) {
        this.gruppeID = gruppeID;
    }

    public String getSitteplass() {
        return sitteplass;
    }

    public void setSitteplass(String sitteplass) {
        this.sitteplass = sitteplass;
    }

    public int getBordnr() {
        return bordnr;
    }

    public void setBordnr(int bordnr) {
        this.bordnr = bordnr;
    }

    public ArrayList<Bruker> getMedlemmer() {
        return medlemmer;
    }

    public void setMedlemmer(ArrayList<Bruker> medlemmer) {
        this.medlemmer = medlemmer;
    }

    public ArrayList<Oving> getOvinger() {
        return ovinger;
    }

    public void setOvinger(ArrayList<Oving> ovinger) {
        this.ovinger = ovinger;
    }

    public String getKommentar() {
        return kommentar;
    }

    public String getOvingerIString(){
        String output="";
        for (int i = 0; i <ovinger.size() ; i++) {
            if(i==0){
                output += ovinger.get(i).getOvingnr();

            }else{
                output += ", " + ovinger.get(i).getOvingnr();
            }
        }
        return output;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public int getKoe_id() {
        return koe_id;
    }

    public void setKoe_id(int koe_id) {
        this.koe_id = koe_id;
    }

    public ArrayList<Integer> getOvingnr() {
        return ovingnr;
    }

    public void setOvingnr(ArrayList<Integer> ovingnr) {
        this.ovingnr = ovingnr;
    }


    /**
     * Hvis to grupper har samme gruppeleder ( index 0 av medlemmer ) er de like
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Bruker temp = ((KoeGrupper)obj).getMedlemmer().get(0);
        if(this.medlemmer.get(0).equals(temp)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "KoeGrupper{" +
                "klokkeslett=" + klokkeslett +
                ", gruppeID=" + gruppeID +
                ", koePlassering=" + koePlassering +
                ", gruppeLeder=" + gruppeLeder +
                ", sitteplass='" + sitteplass + '\'' +
                ", bordnr=" + bordnr +
                ", kommentar='" + kommentar + '\'' +
                ", medlemmer=" + medlemmer +
                ", ovinger=" + ovinger +
                '}';
    }
}
