package com.thecode007.ecollecter.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.error.ANError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.thecode007.ecollecter.R
import com.thecode007.ecollecter.network.APIEndPoint
import com.thecode007.ecollecter.network.model.Bill
import com.thecode007.ecollecter.network.model.BillResponse
import com.thecode007.ecollecter.network.model.LogInResponse
import com.thecode007.ecollecter.rx.AppSchedulerProvider
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_bill_history.*

class BillHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_history)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        getBills()
    }

    private fun getBills() {
        progress.visibility = View.VISIBLE
        val sharedpreferences = getSharedPreferences("ecollector", MODE_PRIVATE)
        val logInResponse = Gson().fromJson(
            sharedpreferences.getString("profile", ""),
            LogInResponse::class.java
        )
        Rx2AndroidNetworking.get(APIEndPoint.BILLS)
            .addQueryParameter("user_id", logInResponse.userId.toString())
            .addHeaders("Authorization", "Bearer " + logInResponse.accessToken)
            .build()
            .getParseSingle(object : TypeToken<BillResponse?>() {})
            .subscribeOn(AppSchedulerProvider().io())
            .observeOn(AppSchedulerProvider().ui())
            .subscribe(object : DisposableSingleObserver<BillResponse>() {
                override fun onSuccess(response: BillResponse) {
                    progress.visibility = View.GONE

                    if (response.bills != null) {
                        val bills = ArrayList<Bill>()
                        for(bill in response.bills.values) {
                            bills.add(bill)
                        }
                        rv_my_bills.layoutManager = LinearLayoutManager(this@BillHistoryActivity)
                        rv_my_bills.adapter = BillAdapter(bills)
                    }
                }

                override fun onError(e: Throwable) {
                    progress.visibility = View.GONE
                    val err = e as ANError
                    Toast.makeText(this@BillHistoryActivity, err.errorBody, Toast.LENGTH_SHORT).show()

                }
            })
    }
}