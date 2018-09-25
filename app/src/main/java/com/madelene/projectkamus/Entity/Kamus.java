package com.madelene.projectkamus.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Kamus implements Parcelable {
    private int id;
    private String kata;
    private String deskripsi;


    protected Kamus(Parcel in) {
        id = in.readInt();
        kata = in.readString();
        deskripsi = in.readString();
    }

    public Kamus() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public static final Creator<Kamus> CREATOR = new Creator<Kamus>() {
        @Override
        public Kamus createFromParcel(Parcel in) {
            return new Kamus(in);
        }

        @Override
        public Kamus[] newArray(int size) {
            return new Kamus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(kata);
        parcel.writeString(deskripsi);
    }
}
