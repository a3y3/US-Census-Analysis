import java.io.Serializable;

public class CensusRecord implements Serializable {
    public String STNAME, CTYNAME;
    public double YEAR, AGEGRP, WA_TOTAL, BA_TOTAL, IA_TOTAL, AA_TOTAL, NA_TOTAL,
            TOM_TOTAL;

    public CensusRecord(){}
    public CensusRecord(String STNAME, String CTYNAME, double YEAR, double AGEGRP, double WA_TOTAL,
                        double BA_TOTAL, double IA_TOTAL, double AA_TOTAL, double NA_TOTAL,
                        double TOM_TOTAL) {
        this.STNAME = STNAME;
        this.CTYNAME = CTYNAME;
        this.YEAR = YEAR;
        this.AGEGRP = AGEGRP;
        this.WA_TOTAL = WA_TOTAL;
        this.BA_TOTAL = BA_TOTAL;
        this.IA_TOTAL = IA_TOTAL;
        this.AA_TOTAL = AA_TOTAL;
        this.NA_TOTAL = NA_TOTAL;
        this.TOM_TOTAL = TOM_TOTAL;
    }

    public String getSTNAME() {
        return STNAME;
    }

    public void setSTNAME(String STNAME) {
        this.STNAME = STNAME;
    }

    public String getCTYNAME() {
        return CTYNAME;
    }

    public void setCTYNAME(String CTYNAME) {
        this.CTYNAME = CTYNAME;
    }

    public double getYEAR() {
        return YEAR;
    }

    public void setYEAR(double YEAR) {
        this.YEAR = YEAR;
    }

    public double getAGEGRP() {
        return AGEGRP;
    }

    public void setAGEGRP(double AGEGRP) {
        this.AGEGRP = AGEGRP;
    }

    public double getWA_TOTAL() {
        return WA_TOTAL;
    }

    public void setWA_TOTAL(double WA_TOTAL) {
        this.WA_TOTAL = WA_TOTAL;
    }

    public double getBA_TOTAL() {
        return BA_TOTAL;
    }

    public void setBA_TOTAL(double BA_TOTAL) {
        this.BA_TOTAL = BA_TOTAL;
    }

    public double getIA_TOTAL() {
        return IA_TOTAL;
    }

    public void setIA_TOTAL(double IA_TOTAL) {
        this.IA_TOTAL = IA_TOTAL;
    }

    public double getAA_TOTAL() {
        return AA_TOTAL;
    }

    public void setAA_TOTAL(double AA_TOTAL) {
        this.AA_TOTAL = AA_TOTAL;
    }

    public double getNA_TOTAL() {
        return NA_TOTAL;
    }

    public void setNA_TOTAL(double NA_TOTAL) {
        this.NA_TOTAL = NA_TOTAL;
    }

    public double getTOM_TOTAL() {
        return TOM_TOTAL;
    }

    public void setTOM_TOTAL(double TOM_TOTAL) {
        this.TOM_TOTAL = TOM_TOTAL;
    }
}

