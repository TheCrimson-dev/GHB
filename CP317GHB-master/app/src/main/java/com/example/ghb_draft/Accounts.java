package com.example.ghb_draft;

import java.text.DecimalFormat;
// Bank Account Class
public class Accounts {
    private String email, type;
    private Float balance;
    private Integer studentNumber, id;
    DataBaseHelper dataBaseHelper;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public Accounts(String email, Float balance, String type, Integer studentNumber, Integer id){
        this.email = email;
        this.balance = balance;
        this.type = type;
        this.studentNumber = studentNumber;
        this.id = id;
    }
    public Accounts() {

    }

    @Override
    public String toString() {
        if (studentNumber.equals(0)) {
            return "Account: " + type +
                    ", Balance = $" + df.format(balance);
        } else{
            return "Account: " + type +
                    ", Balance = $" + df.format(balance) +
                    "\nStudent Number: " + studentNumber;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
