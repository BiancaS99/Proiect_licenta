package com.example.sdesign;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="utilizatori")
public class Utilizatori implements Parcelable {
    @NonNull
    @PrimaryKey
    private String id;
    private String email;
    private String pass;
    @Ignore
    private String nume;
    @Ignore
    private String zi_nastere;
    @Ignore
    private String tip;

    @Ignore
    public Utilizatori(){

    }
    @Ignore
    public  Utilizatori( String email,String pass){
        this.email=email;
        this.pass=pass;
    }

    public  Utilizatori(String id, String email,String pass){
        this.id=id;
        this.email=email;
        this.pass=pass;
    }
    public  Utilizatori(String id, String email,String pass,String tip){
        this.id=id;
        this.email=email;
        this.pass=pass;
        this.tip=tip;
    }

    @Ignore
    public Utilizatori(@NonNull String id, String email, String pass, String nume, String zi_nastere, String tip) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.nume = nume;
        this.zi_nastere = zi_nastere;
        this.tip = tip;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getZi_nastere() {
        return zi_nastere;
    }

    public void setZi_nastere(String zi_nastere) {
        this.zi_nastere = zi_nastere;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    protected Utilizatori(Parcel in) {
        this.id=in.readString();
        this.email=in.readString();
        this.pass=in.readString();
        this.nume=in.readString();
        this.zi_nastere=in.readString();
        this.tip=in.readString();
    }

    public static final Creator<Utilizatori> CREATOR = new Creator<Utilizatori>() {
        @Override
        public Utilizatori createFromParcel(Parcel in) {
            return new Utilizatori(in);
        }

        @Override
        public Utilizatori[] newArray(int size) {
            return new Utilizatori[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Utilizatori{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", nume='" + nume + '\'' +
                ", zi_nastere='" + zi_nastere + '\'' +
                ", tip='" + tip + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(pass);
        dest.writeString(nume);
        dest.writeString(zi_nastere);
        dest.writeString(tip);




    }
}
