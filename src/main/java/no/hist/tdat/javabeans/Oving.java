package no.hist.tdat.javabeans;

import java.util.Date;

/**
 * Created by ted on 1/15/14.
 */
public class Oving {
    private int ovingid;
    private int ovingnr;
    private boolean godkjent;
    private String godkjentAv;
    private Date godkjentTid;

    public int getOvingnr() {
        return ovingnr;
    }

    public int getOvingid() {
        return ovingid;
    }

    public void setOvingid(int id) {
        this.ovingid = id;
    }

    public void setOvingnr(int ovingnr) {
        this.ovingnr = ovingnr;
    }

    public boolean isGodkjent() {
        return godkjent;
    }

    public int denneErGodkjent() {
        if (godkjent == true) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setGodkjent(boolean godkjent) {
        this.godkjent = godkjent;
    }

    public String getGodkjentAv() {
        return godkjentAv;
    }

    public void setGodkjentAv(String godkjentAv) {
        this.godkjentAv = godkjentAv;
    }

    public Date getGodkjentTid() {
        return godkjentTid;
    }

    public void setGodkjentTid(Date godkjentTid) {
        this.godkjentTid = godkjentTid;
    }
}
