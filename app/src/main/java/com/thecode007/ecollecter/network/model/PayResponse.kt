package com.thecode007.ecollecter.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PayResponse {

    @SerializedName("message")
    @Expose
    var message: String? = ""


}