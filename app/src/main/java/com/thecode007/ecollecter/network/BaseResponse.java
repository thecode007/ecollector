package com.thecode007.ecollecter.network;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName(value = "status")
    private Integer status;
    @SerializedName(value = "message")
    private String message;
    @SerializedName(value = "Code", alternate = "code")
    private Integer code;
    @SerializedName(value = "Data", alternate = "data")
    private T data;

    @SerializedName(value = "checksum")
    private String checksum;

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}