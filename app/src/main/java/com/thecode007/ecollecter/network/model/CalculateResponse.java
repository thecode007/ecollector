package com.thecode007.ecollecter.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculateResponse {
    @SerializedName("toPay")
    @Expose
    private int toPay;


    public int getToPay() {
        return toPay;
    }

    public void setToPay(int toPay) {
        this.toPay = toPay;
    }
}
