package com.thecode007.ecollecter.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;
    private Gson gson;

    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
        gson = new Gson();
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public void setApiHeader(ApiHeader apiHeader) {

    }


}
