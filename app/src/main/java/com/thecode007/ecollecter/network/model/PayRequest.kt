package com.thecode007.ecollecter.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PayRequest {

    @SerializedName("user_id")
    @Expose
    var userId: Int? = null


}