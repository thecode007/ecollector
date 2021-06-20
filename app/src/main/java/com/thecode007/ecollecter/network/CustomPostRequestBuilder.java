package com.thecode007.ecollecter.network;

import com.rx2androidnetworking.Rx2ANRequest;

public class CustomPostRequestBuilder extends  Rx2ANRequest.PostRequestBuilder {

    public CustomPostRequestBuilder(String url) {
        super(url);
    }

    @Override
    public Rx2ANRequest build() {
        return super.build();
    }
}

//{
//        "Status": 1,
//        "Data": {
//        "Settings": {
//        "BRAND": "32e2b0bc-7c77-49bf-af8f-668cc0b8a8b2",
//        "ADS": "6d6be075-97a3-4156-a9ea-8555dd956b5d",
//        "TYPES": "b5e0c070-98ce-42d7-ac47-c9bdec8d52bb",
//        "VIDEOS": "d709b477-b410-4ed5-ba6c-64f7d269069e",
//        "GREETING": "672bfd57-56c8-4284-a6d0-6b4f99c395d4",
//        "CONTENT": "9efc07f8-f51c-4387-bdbe-414fe1290280",
//        "SEARCH_CAT_FOUNDATION_SUB46": "9efc07f8-f51c-4387-bdbe-414fe1290280"
//        },
//        "TTL": 3600.0
//        },
//        "Message": "Success"
//        }