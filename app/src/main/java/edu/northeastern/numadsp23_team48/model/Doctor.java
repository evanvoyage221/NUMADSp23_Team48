package edu.northeastern.numadsp23_team48.model;

public class Doctor {
    private String doctorName;
    private String hospital;
    private String exp;
    private String mobile;
    private int fee;

    public Doctor() {
    }

    public Doctor(String doctorName, String hospital, String exp, String mobile, int fee) {
        this.doctorName = doctorName;
        this.hospital = hospital;
        this.exp = exp;
        this.mobile = mobile;
        this.fee = fee;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getHospital() {
        return hospital;
    }

    public String getExp() {
        return exp;
    }

    public String getMobile() {
        return mobile;
    }

    public int getFee() {
        return fee;
    }
}
