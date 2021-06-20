package com.thecode007.ecollecter.network;

import com.google.gson.reflect.TypeToken;
import com.rx2androidnetworking.Rx2ANRequest;

import io.reactivex.Single;

public class CustomRx2ANRequest extends Rx2ANRequest {

    public CustomRx2ANRequest(GetRequestBuilder builder) {
        super(builder);
    }

    public CustomRx2ANRequest(PostRequestBuilder builder) {
        super(builder);
    }

    public CustomRx2ANRequest(DownloadBuilder builder) {
        super(builder);
    }

    public CustomRx2ANRequest(MultiPartBuilder builder) {
        super(builder);
    }

    @Override
    public <T> Single<T> getParseSingle(TypeToken<T> typeToken) {

        return super.getParseSingle(typeToken);
    }
}
