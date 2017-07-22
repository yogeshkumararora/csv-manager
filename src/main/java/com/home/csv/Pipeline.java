package com.home.csv;

/**
 * Created by yogeshkumararora on 22/07/17.
 */
public class Pipeline {

    private long staffId;
    private String folderName;
    private int jobCount;
    private String gbgf;
    private String serviceLine;

    public Pipeline(long staffId, String folderName, int jobCount) {
        this.staffId = staffId;
        this.folderName = folderName;
        this.jobCount = jobCount;
    }

    public int getJobCount() {
        return jobCount;
    }

    public String getServiceLine() {
        return serviceLine;
    }

    public void setJobCount(int jobCount) {
        this.jobCount = jobCount;
    }

    public void setServiceLine(String serviceLine) {
        this.serviceLine = serviceLine;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getGbgf() {
        return gbgf;
    }

    public void setGbgf(String gbgf) {
        this.gbgf = gbgf;
    }

    @Override
    public String toString() {
        return "Pipeline{" +
                "staffId=" + staffId +
                ", folderName='" + folderName + '\'' +
                ", jobCount='" + jobCount + '\'' +
                ", gbgf='" + gbgf + '\'' +
                ", serviceLine=" + serviceLine +
                '}';
    }
}
