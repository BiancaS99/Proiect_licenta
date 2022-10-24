package com.example.sdesign;

class Producator {
    private String email;
    private String parola;
    private String username;
    private String CUI;
    public Producator(String email, String parola, String username, String CUI) {
        this.email = email;
        this.parola = parola;
        this.username = username;
        this.CUI = CUI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCUI() {
        return CUI;
    }

    public void setCUI(String CUI) {
        this.CUI = CUI;
    }
}
