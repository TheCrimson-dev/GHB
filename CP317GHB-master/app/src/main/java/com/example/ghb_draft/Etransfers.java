package com.example.ghb_draft;

import java.text.DecimalFormat;

public class Etransfers {
    private String outgoingEmail, receivingEmail;
    private Float amount;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public Etransfers(String outgoingEmail, String receivingEmail, Float amount){
        this.outgoingEmail = outgoingEmail;
        this.amount = amount;
        this.receivingEmail = receivingEmail;
    }
    public Etransfers() {

    }

    @Override
    public String toString() {
            return "$" + df.format(amount)  + ", Sent From: " + outgoingEmail + ", Received By: " + receivingEmail;
    }

    public String getOutgoingEmail() {
        return outgoingEmail;
    }

    public void setOutgoingEmail(String outgoingEmail) {
        this.outgoingEmail = outgoingEmail;
    }

    public String getReceivingEmail() {
        return receivingEmail;
    }

    public void setReceivingEmail(String receivingEmail) {
        this.receivingEmail = receivingEmail;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
