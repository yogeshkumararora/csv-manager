package com.home.csv;

/**
 * Created by yogeshkumararora on 22/07/17.
 */
public class Mapping {

    private long staffId;
    private String gbGf;
    private String svcLine;

    public Mapping(long staffId, String gbGf, String svcLine) {
        this.staffId = staffId;
        this.gbGf = gbGf;
        this.svcLine = svcLine;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public String getGbGf() {
        return gbGf;
    }

    public void setGbGf(String gbGf) {
        this.gbGf = gbGf;
    }

    public String getSvcLine() {
        return svcLine;
    }

    public void setSvcLine(String svcLine) {
        this.svcLine = svcLine;
    }

    @Override
    public String toString() {
        return "Mapping{" +
                "staffId=" + staffId +
                ", gbGf='" + gbGf + '\'' +
                ", svcLine='" + svcLine + '\'' +
                '}';
    }
}
