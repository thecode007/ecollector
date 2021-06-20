package com.thecode007.ecollecter.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Bill {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null

    @SerializedName("meter_balance")
    @Expose
    var meterBalance: String? = null

    @SerializedName("bill_balance")
    @Expose
    var billBalance: String? = null

    @SerializedName("meter_image")
    @Expose
    var meterImage: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("employee_id")
    @Expose
    var employeeId: String? = null


    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
}