package no.hist.tdat.javabeans;

import java.util.ArrayList;

/**
 * Created by vimCnett
 */
public class Emne {
    private String emneKode;
    private String emneNavn;
    private ArrayList<DelEmne> delemner;
    private int foreleser;
    private ArrayList<Bruker> foreleserListe;

    public Emne(String kode, String navn) {
        this.emneKode = kode;
        this.emneNavn = navn;
    }

    public Emne() {
    }

    public String getEmneKode() {
        return emneKode;
    }

    public void setEmneKode(String emneKode) {
        this.emneKode = emneKode;
    }

    public int getForeleser() {
        return foreleser;
    }

    public ArrayList<Bruker> getForeleserListe() {
        return foreleserListe;
    }

    public void setForeleserListe(ArrayList<Bruker> foreleserListe) {
        this.foreleserListe = foreleserListe;
    }

    public void setForeleser(int foreleser) {
        this.foreleser = foreleser;
    }

    public String getEmneNavn() {
        return emneNavn;
    }

    public void setEmneNavn(String emneNavn) {
        this.emneNavn = emneNavn;
    }

    public ArrayList<DelEmne> getDelemner() {
        return delemner;
    }

    public void setDelemner(ArrayList<DelEmne> delemner) {
        this.delemner = delemner;
    }

    public void leggTilDelEmne(DelEmne delEmne){
        delemner.add(delEmne);
        System.out.println();
    }

    public String delEmnetilString(){
        String delEmner = "";
        for(int i = 0; i<delemner.size(); i++){
            if(i==0){
                delEmner += delemner.get(i).getDelEmneNavn();
            }else{
                delEmner += ", " + delemner.get(i).getDelEmneNavn();
            }
        }
        return delEmner;
    }
    public String forelesereTilString(){
        String forelesere = "";
        for(int i = 0; i<foreleserListe.size(); i++){
            if(i==0){
                forelesere += foreleserListe.get(i).getFornavn() + " " + foreleserListe.get(i).getEtternavn();
            }else{
                forelesere += ", " + foreleserListe.get(i).getFornavn() + " " + foreleserListe.get(i).getEtternavn();
            }
        }
        return forelesere;
    }
}
