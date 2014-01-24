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

    public void setOvingsRegler(String ovingsRegler) {this.ovingsRegler = ovingsRegler;}

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
}
