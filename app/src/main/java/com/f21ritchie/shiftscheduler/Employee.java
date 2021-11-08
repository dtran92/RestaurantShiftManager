package com.f21ritchie.shiftscheduler;

import java.util.Objects;

public class Employee {
    int id;
    String firstName;
    String lastName;
    String email;
    String trained_am;
    String trained_pm;
    String mon_AM;
    String mon_PM;
    String tue_AM;
    String tue_PM;
    String wed_AM;
    String wed_PM;
    String thu_AM;
    String thu_PM;
    String fri_AM;
    String fri_PM;
    String sat_AM;
    String sat_PM;
    String sun_AM;
    String sun_PM;

    public Employee(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Employee(int id, String firstName, String lastName, String email,
                    String trained_am, String trained_pm,
                    String mon_AM, String mon_PM, String tue_AM, String tue_PM,
                    String wed_AM, String wed_PM, String thu_AM, String thu_PM,
                    String fri_AM, String fri_PM, String sat_AM, String sat_PM,
                    String sun_AM, String sun_PM) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.trained_am = trained_am;
        this.trained_pm = trained_pm;
        this.email = email;
        this.mon_AM = mon_AM;
        this.mon_PM = mon_PM;
        this.tue_AM = tue_AM;
        this.tue_PM = tue_PM;
        this.wed_AM = wed_AM;
        this.wed_PM = wed_PM;
        this.thu_AM = thu_AM;
        this.thu_PM = thu_PM;
        this.fri_AM = fri_AM;
        this.fri_PM = fri_PM;
        this.sat_AM = sat_AM;
        this.sat_PM = sat_PM;
        this.sun_AM = sun_AM;
        this.sun_PM = sun_PM;
    }

    // getters and setters
    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    //trained AM
    public String getTrained_am() {
        return trained_am;
    }

    public void setTrained_am(String new_trained_am) {
        this.trained_am = new_trained_am;
    }

    //trained PM
    public String getTrained_pm() {
        return trained_pm;
    }

    public void setTrained_pm(String new_trained_pm) {
        this.trained_pm = new_trained_pm;
    }

    //mon
    public String getMon_AM() {
        return mon_AM;
    }

    public void setMon_AM(String new_mon_AM) {
        this.mon_AM = new_mon_AM;
    }

    public String getMon_PM() {
        return mon_PM;
    }

    public void setMon_PM(String new_mon_PM) {
        this.mon_PM = new_mon_PM;
    }

    //tue
    public String getTue_AM() {
        return tue_AM;
    }

    public void setTue_AM(String new_tue_AM) {
        this.tue_AM = new_tue_AM;
    }

    public String getTue_PM() {
        return tue_PM;
    }

    public void setTue_PM(String new_tue_PM) {
        this.tue_PM = new_tue_PM;
    }

    //wed
    public String getWed_AM() {
        return wed_AM;
    }

    public void setWed_AM(String new_wed_AM) {
        this.wed_AM = new_wed_AM;
    }

    public String getWed_PM() {
        return wed_PM;
    }

    public void setWed_PM(String new_wed_PM) {
        this.wed_PM = new_wed_PM;
    }

    //thu
    public String getThu_AM() {
        return thu_AM;
    }

    public void setThu_AM(String new_thu_AM) {
        this.thu_AM = new_thu_AM;
    }

    public String getThu_PM() {
        return thu_PM;
    }

    public void setThu_PM(String new_thu_PM) {
        this.thu_PM = new_thu_PM;
    }

    //fri
    public String getFri_AM() {
        return fri_AM;
    }

    public void setFri_AM(String new_fri_AM) {
        this.fri_AM = new_fri_AM;
    }

    public String getFri_PM() {
        return fri_PM;
    }

    public void setFri_PM(String new_fri_PM) {
        this.fri_PM = new_fri_PM;
    }

    //sat
    public String getSat_AM() {
        return sat_AM;
    }

    public void setSat_AM(String new_sat_AM) {
        this.sat_AM = new_sat_AM;
    }

    public String getSat_PM() {
        return sat_PM;
    }

    public void setSat_PM(String new_sat_PM) {
        this.sat_PM = new_sat_PM;
    }

    //sun
    public String getSun_AM() {
        return sun_AM;
    }

    public void setSun_AM(String new_sun_AM) {
        this.sun_AM = new_sun_AM;
    }

    public String getSun_PM() {
        return sun_PM;
    }

    public void setSun_PM(String new_sun_PM) {
        this.sun_PM = new_sun_PM;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName
                + "\n" + "Trained for Opening: " + trained_am
                + "\n" + "Trained for Closing: " + trained_pm
                + "\n" + "Email: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && email.equals(employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
