import java.io.Serializable;

public class CensusRecord implements Serializable {
    public String STNAME, CTYNAME;
    public int YEAR, AGEGRP, WA_TOTAL, BA_TOTAL, IA_TOTAL, AA_TOTAL, NA_TOTAL,
            TOM_TOTAL;

    public CensusRecord(){}
    public CensusRecord(String STNAME, String CTYNAME, int YEAR, int AGEGRP, int WA_TOTAL,
                        int BA_TOTAL, int IA_TOTAL, int AA_TOTAL, int NA_TOTAL,
                        int TOM_TOTAL) {
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

    public int getYEAR() {
        return YEAR;
    }

    public void setYEAR(int YEAR) {
        this.YEAR = YEAR;
    }

    public int getAGEGRP() {
        return AGEGRP;
    }

    public void setAGEGRP(int AGEGRP) {
        this.AGEGRP = AGEGRP;
    }

    public int getWA_TOTAL() {
        return WA_TOTAL;
    }

    public void setWA_TOTAL(int WA_TOTAL) {
        this.WA_TOTAL = WA_TOTAL;
    }

    public int getBA_TOTAL() {
        return BA_TOTAL;
    }

    public void setBA_TOTAL(int BA_TOTAL) {
        this.BA_TOTAL = BA_TOTAL;
    }

    public int getIA_TOTAL() {
        return IA_TOTAL;
    }

    public void setIA_TOTAL(int IA_TOTAL) {
        this.IA_TOTAL = IA_TOTAL;
    }

    public int getAA_TOTAL() {
        return AA_TOTAL;
    }

    public void setAA_TOTAL(int AA_TOTAL) {
        this.AA_TOTAL = AA_TOTAL;
    }

    public int getNA_TOTAL() {
        return NA_TOTAL;
    }

    public void setNA_TOTAL(int NA_TOTAL) {
        this.NA_TOTAL = NA_TOTAL;
    }

    public int getTOM_TOTAL() {
        return TOM_TOTAL;
    }

    public void setTOM_TOTAL(int TOM_TOTAL) {
        this.TOM_TOTAL = TOM_TOTAL;
    }
}