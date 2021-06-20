package com.thecode007.ecollecter.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculateRequest {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("current")
    @Expose
    private String current;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
