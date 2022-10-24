package com.example.sdesign;

public class Scafa {

    private String tipProdus,culoare,tipPlaca,lungime;


    public Scafa (){

    }

    public Scafa(String tipProdus, String culoare, String tipPlaca, String lungime) {
        this.tipProdus = tipProdus;
        this.culoare = culoare;
        this.tipPlaca = tipPlaca;
        this.lungime = lungime;
    }

    public String getTipProdus() {
        return tipProdus;
    }

    public void setTipProdus(String tipProdus) {
        this.tipProdus = tipProdus;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public String getTipPlaca() {
        return tipPlaca;
    }

    public void setTipPlaca(String tipPlaca) {
        this.tipPlaca = tipPlaca;
    }

    public String getLungime() {
        return lungime;
    }

    public void setLungime(String lungime) {
        this.lungime = lungime;
    }
}
