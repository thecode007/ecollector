package com.thecode007.ecollecter.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.thecode007.ecollecter.R
import com.thecode007.ecollecter.network.APIEndPoint
import com.thecode007.ecollecter.network.model.LogInRequest
import com.thecode007.ecollecter.network.model.LogInResponse
import com.thecode007.ecollecter.rx.AppSchedulerProvider
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_sigin.*
import java.util.*

class SignActivity : AppCompatActivity() {
    var sharedpreferences :SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin)

        sharedpreferences = getSharedPreferences("ecollector", Context.MODE_PRIVATE)


        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {


                if (p0.toString().isEmpty()) {
                    tv_name_error.visibility = View.VISIBLE
                    et_name.backgroundTintList = ContextCompat.getColorStateList(
                        this@SignActivity,
                        android.R.color.holo_red_dark
                    )
                    tv_name_error.text = "Field Required"

                }   else if (!Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()) {
                    tv_name_error.visibility = View.VISIBLE
                    tv_name_error.text = "Invalid Email Format"
                    et_name.backgroundTintList = ContextCompat.getColorStateList(
                        this@SignActivity,
                        android.R.color.holo_red_dark
                    )
                }
                else {
                    tv_name_error.visibility = View.INVISIBLE
                    et_name.backgroundTintList = ContextCompat.getColorStateList(
                        this@SignActivity,
                        R.color.primary
                    )
                }
                checkButtonStatus()
            }
        })

        et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isEmpty()) {
                    tv_password_error.visibility = View.VISIBLE
                    et_password.backgroundTintList = ContextCompat.getColorStateList(
                        this@SignActivity,
                        android.R.color.holo_red_dark
                    )
                } else {
                    tv_password_error.visibility = View.INVISIBLE
                    et_password.backgroundTintList = ContextCompat.getColorStateList(
                        this@SignActivity,
                        R.color.primary
                    )
                }
                checkButtonStatus()
            }
        })

        btn_sign_in.setOnClickListener {
//            startActivity(Intent(this@SignActivity, MainActivity::class.java))
            sigIn()
        }
    }


    private fun sigIn() {
        val requestObject = LogInRequest()
        requestObject.email = et_name.text.toString()
        requestObject.password = et_password.text.toString()
        findViewById<ProgressBar>(R.id.progress_circular).visibility = View.VISIBLE
        Rx2AndroidNetworking.post(APIEndPoint.LOGIN)
                .addApplicationJsonBody(requestObject)
                .build()
                .getParseSingle(object : TypeToken<LogInResponse>() {})
                .subscribeOn(AppSchedulerProvider().io())
                .observeOn(AppSchedulerProvider().ui())
                .subscribe(object : DisposableSingleObserver<LogInResponse>() {
                    override fun onSuccess(response: LogInResponse) {
                        findViewById<ProgressBar>(R.id.progress_circular).visibility = View.GONE
                        val editor: SharedPreferences.Editor = sharedpreferences!!.edit()
                        editor.putString("profile", Gson().toJson(response))
                        editor.apply()
                        if (response.role == "user") {
                            startActivity(Intent(this@SignActivity, UserBillActivity::class.java))
                        }
                        else {
                            startActivity(Intent(this@SignActivity, MainActivity::class.java))
                        }
                    }

                    override fun onError(e: Throwable) {
                        findViewById<ProgressBar>(R.id.progress_circular).visibility = View.GONE
                        Toast.makeText(
                            this@SignActivity,
                            "Wrong user name or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    fun checkButtonStatus() {
        btn_sign_in.isEnabled = et_name.text.isNotEmpty() && et_name.text.isNotBlank()
                && et_password.text.isNotEmpty() && et_password.text.isNotBlank()
    }


}