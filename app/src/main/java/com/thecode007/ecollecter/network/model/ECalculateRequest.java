package com.thecode007.ecollecter.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ECalculateRequest {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("current")
    @Expose
    private String current;
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("image")
    @Expose
    private String image;
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCurrent() {
        return current;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
