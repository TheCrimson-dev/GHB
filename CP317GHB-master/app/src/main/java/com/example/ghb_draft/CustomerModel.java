package com.example.ghb_draft;
// Contact list customer model class
public class CustomerModel {
    private String name, email, user;

    // constructors

    public CustomerModel(String user, String name, String email) {
        this.user = user;
        this.name = name;
        this.email = email;
    }

    public CustomerModel() {
    }
    // toString is necessary for printing the contents of a class object

    @Override
    public String toString() {
        return
                "Name: " + name +
                        ", Email: " + email;
    }

    // getters and setters


    public String getUser() { return user; }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}