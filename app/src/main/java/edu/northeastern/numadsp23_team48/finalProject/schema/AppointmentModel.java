package edu.northeastern.numadsp23_team48.finalProject.schema;

public class AppointmentModel {

    private String name, hospital, contact, fees, date, time;

    public AppointmentModel() {
    }

    public AppointmentModel(String name, String hospital, String contact, String fees, String date, String time) {
        this.name = name;
        this.hospital = hospital;
        this.contact = contact;
        this.fees = fees;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
