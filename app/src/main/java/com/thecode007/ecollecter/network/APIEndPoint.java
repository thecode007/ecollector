package com.thecode007.ecollecter.network;

public class APIEndPoint {

    private static final String BASE_URL = "http://35.174.155.97/api/";
    public static final String LOGIN = BASE_URL + "auth/login";
    public static final String LOGOUT = BASE_URL + "auth/logout";
    public static final String GET_USERS = BASE_URL + "users";
    public static final String CALCULATE = BASE_URL + "calculate";
    public static final String COLLECTOR = BASE_URL + "collector";
    public static final String PAY = BASE_URL + "pay";
    public static final String BILLS = BASE_URL + "bills";

}
