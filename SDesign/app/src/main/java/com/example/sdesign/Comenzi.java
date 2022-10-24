package com.example.sdesign;

public class Comenzi {
   private int imagine;
   private String titlu_comanda;

   public Comenzi(){

   }

   public Comenzi(int imagine, String titlu_comanda) {
      this.imagine = imagine;
      this.titlu_comanda = titlu_comanda;
   }

   public int getImagine() {
      return imagine;
   }

   public void setImagine(int imagine) {
      this.imagine = imagine;
   }

   public String getTitlu_comanda() {
      return titlu_comanda;
   }

   public void setTitlu_comanda(String titlu_comanda) {
      this.titlu_comanda = titlu_comanda;
   }
}
