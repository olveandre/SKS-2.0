package no.hist.tdat.javabeans;

import java.util.ArrayList;

/**
 * Created by vimCnett
 */
public class DelEmne {
    private int nr;
    private String semester = "h";
    private int koe_id;
    private String ovingsRegler;
    private boolean koe_status;
    private ArrayList<Oving> studentovinger;
    private String delEmneNavn;
    private String emneKode;
    private int antOvinger;
    private boolean ovingerBestatt;

    public String getDelEmneNavn() {
        return delEmneNavn;
    }

    public void setDelEmneNavn(String delEmneNavn) {
        this.delEmneNavn = delEmneNavn;
    }

    public DelEmne() {
    }

    public DelEmne(int nr, char semester, int koe_id, Ovingsopplegg ovinger) {
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public boolean isOvingerBestatt() {
        return ovingerBestatt;
    }

    public void setOvingerBestatt(boolean b) {
        this.ovingerBestatt = b;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getKoe_id() {
        return koe_id;
    }

    public void setKoe_id(int koe_id) {
        this.koe_id = koe_id;
    }

    public int getAntOvinger() {
        return antOvinger;
    }

    public void setAntOvinger(int antOvinger) {
        this.antOvinger = antOvinger;
    }

    public String getOvingsRegler() { return ovingsRegler; }

    public void setOvingsRegler(String ovingsRegler) {
        this.ovingsRegler = ovingsRegler;
    }

    public boolean isKoe_status() {
        return koe_status;
    }

    public void setKoe_status(boolean koe_status) {
        this.koe_status = koe_status;
    }

    public ArrayList<Oving> getStudentovinger() {
        return studentovinger;
    }

    public void setStudentovinger(ArrayList<Oving> studentovinger) {
        this.studentovinger = studentovinger;
    }

    public String getEmneKode() {
        return emneKode;
    }

    public void setEmneKode(String emneKode) {
        this.emneKode = emneKode;
    }


    @Override
    public String toString() {
        return "DelEmne{" +
                "nr=" + nr +
                ", semester='" + semester + '\'' +
                ", koe_id=" + koe_id +
                ", ovingsRegler='" + ovingsRegler + '\'' +
                ", koe_status=" + koe_status +
                ", studentovinger=" + studentovinger +
                ", delEmneNavn='" + delEmneNavn + '\'' +
                ", emneKode='" + emneKode + '\'' +
                '}';
    }
    public void sjekkAntOvinger(ArrayList<Oving> ovinger) {
        String[] regel = ovingsRegler.split("[|]");
        //10 7 | 3 5 8 ; 2 | 4 7 ; 1 |

        int antOvinger = Integer.parseInt(regel[0].split(" ")[0]);
        int kravGodkj = Integer.parseInt(regel[0].split(" ")[1]);
        int antallGodkj = ovinger.size();

        if (antallGodkj < kravGodkj) {
            ovingerBestatt = false;
            return;
        }

        for (int j = 1; j < regel.length; j++) {
            String[] regelS = regel[j].split(";");
            String[] ovingerS = regelS[0].split(" ");
            int gjort = 0;
            int krav = Integer.parseInt(regelS[1].trim());

            for (int k = 0; k < ovingerS.length; k++) {
                if (ovingerS[k] != null && !ovingerS[k].equals("")) {
                    int krevdOving = Integer.parseInt(ovingerS[k].trim());
                    for (int i = 0; i < ovinger.size(); i++) {
                        if (krevdOving == ovinger.get(i).getOvingnr()) {
                            gjort++;
                        }
                    }
                }
            }
            if (gjort < krav) {
                ovingerBestatt = false;
                return;
            }
        }
        ovingerBestatt = true;

    }
}
