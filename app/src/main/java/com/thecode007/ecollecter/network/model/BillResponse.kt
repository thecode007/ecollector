package com.thecode007.ecollecter.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class BillResponse {
    @SerializedName("bills")
    @Expose
     val bills: Map<String, Bill>? = null
}