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
    String sat;
    String sun;

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
                    String fri_AM, String fri_PM, String sat, String sun) {
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
        this.sat = sat;
        this.sun = sun;
    }

    public Employee() {}

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


    public String getMon_PM() {
        return mon_PM;
    }


    //tue
    public String getTue_AM() {
        return tue_AM;
    }


    public String getTue_PM() {
        return tue_PM;
    }


    //wed
    public String getWed_AM() {
        return wed_AM;
    }



    public String getWed_PM() {
        return wed_PM;
    }



    //thu
    public String getThu_AM() {
        return thu_AM;
    }



    public String getThu_PM() {
        return thu_PM;
    }



    //fri
    public String getFri_AM() {
        return fri_AM;
    }



    public String getFri_PM() {
        return fri_PM;
    }



    public String getSat() {
        return sat;
    }

    public String getSun() {
        return sun;
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
