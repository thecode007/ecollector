package com.thecode007.ecollecter.network;

import android.annotation.SuppressLint;
import android.provider.Settings.Secure;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by assaad on 07/07/17.
 */

public class ApiHeader {

    private ProtectedApiHeader mProtectedApiHeader;
    private PublicApiHeader mPublicApiHeader;

    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }

    public static final class ProtectedApiHeader {

        @Expose
        @SerializedName("Authorization")
        private String Authorization;



        public ProtectedApiHeader(String mApiKey, Long mUserId,
                                  String mAccessToken, String language) {
            this.Authorization = "Bearer " + mAccessToken;
        }

        public String getAccessToken() {
            return Authorization;
        }

        public void setAccessToken(String accessToken) {
            Authorization = "Bearer " + accessToken;
        }

        public String getAuthorization() {
            return Authorization;
        }

        public void setAuthorization(String authorization) {
            Authorization = authorization;
        }
    }

    public static final class PublicApiHeader {
        @Expose
        @SerializedName("api_key")
        private String mApiKey;

        public PublicApiHeader(String apiKey) {
            mApiKey = apiKey;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
        }
    }
}
