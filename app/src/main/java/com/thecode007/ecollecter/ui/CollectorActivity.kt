package com.thecode007.ecollecter.ui

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.UploadProgressListener
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.thecode007.ecollecter.R
import com.thecode007.ecollecter.network.APIEndPoint
import com.thecode007.ecollecter.network.model.*
import com.thecode007.ecollecter.rx.AppSchedulerProvider
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_collector.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*


open class CollectorActivity : AppCompatActivity(), View.OnFocusChangeListener, UploadProgressListener {
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collector)
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.deniedPermissionResponses.size > 0) {
                        onBackPressed()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }

            }).check()
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                val m = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        if (intent.extras != null && intent.extras!!.containsKey("USER")) {
            user = Gson().fromJson(
                intent.extras!!
                    .getString("USER"),
                User::class.java
            )
            toolbar.title = "Bill Details of " + user!!.name
            te_name.setText(user!!.name)
            te_email.setText(user!!.email)
            te_phone.setText(user!!.phone)
            te_city.setText(user!!.city)
            te_last_consumption.setText(user!!.initialValue.toString())
        }
        te_current_consumption.onFocusChangeListener = this
        te_e_meter.onFocusChangeListener = this

        te_current_consumption.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        te_e_meter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        te_current_consumption.requestFocus()

        btn_scan_consumption.setOnClickListener {
            startCameraActivity()
        }
    }


    private var testInputEditText:TextInputEditText ? = null
    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (R.id.te_current_consumption == p0!!.id) {
            testInputEditText = te_current_consumption
        }
        else {
            testInputEditText = te_e_meter
        }
    }
    private var outputFileUri: Uri? = null
    private var _path:String? = null

    protected fun startCameraActivity() {
        _path =  Environment.getExternalStorageDirectory().toString() +"/ocr.jpg";
        val file = File(_path)
        outputFileUri = Uri.fromFile(file)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        startActivityForResult(intent, 0)
    }

    private fun calculate() {

        progressBar.visibility = View.VISIBLE
        val request = ECalculateRequest()
        request.code = te_e_meter.text.toString()
        request.user_id = user!!.userId.toString()
        request.current = te_current_consumption.text.toString()

        val sharedpreferences = getSharedPreferences("ecollector", MODE_PRIVATE)
        val logInResponse = Gson().fromJson(
            sharedpreferences.getString("profile", ""),
            LogInResponse::class.java
        )

        Rx2AndroidNetworking.post(APIEndPoint.COLLECTOR)
                .addHeaders("Authorization", "Bearer " + logInResponse.accessToken)
                .addApplicationJsonBody(request)
                .build()
                .getParseSingle(object : TypeToken<CalculateResponse>() {})
                .subscribeOn(AppSchedulerProvider().io())
                .observeOn(AppSchedulerProvider().ui())
                .subscribe(object : DisposableSingleObserver<CalculateResponse>() {
                    override fun onSuccess(response: CalculateResponse) {
                        progressBar.visibility = View.GONE
                        tv_cost.text = response.toPay.toString().plus(" KW")
                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE
                        val anError = e as ANError
                        Toast.makeText(
                            this@CollectorActivity,
                            anError.errorBody,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

    }

    fun upload() {
        progressBar.visibility = View.VISIBLE

        val request = ECalculateRequest()
        request.code = te_e_meter.text.toString()
        request.user_id = user!!.userId.toString()
        request.current = te_current_consumption.text.toString()
        request.image = convertBase64()


        val sharedpreferences = getSharedPreferences("ecollector", MODE_PRIVATE)
        val logInResponse = Gson().fromJson(
            sharedpreferences.getString("profile", ""),
            LogInResponse::class.java
        )

        Rx2AndroidNetworking.post(APIEndPoint.COLLECTOR)
                .addHeaders("Authorization", "Bearer " + logInResponse.accessToken)
                .addApplicationJsonBody(request)
                .build()
                .setUploadProgressListener(this)
                .getParseSingle(object : TypeToken<CalculateResponse>() {})
                .subscribe(object : DisposableSingleObserver<CalculateResponse>() {
                    override fun onSuccess(response: CalculateResponse) {
                        progressBar.visibility = View.GONE
                        tv_cost.text = response.toPay.toString().plus(" KW")
                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE
                        val anError = e as ANError
                        Toast.makeText(
                            this@CollectorActivity,
                            anError.errorBody,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    fun calculateE(view: View) {
        if (_path == null || _path.isNullOrEmpty()) {
            calculate()
        }
        else {
            val file = File(_path)
            if (file.exists()) {
                upload()
            }
            else {
                calculate()
            }
        }

    }

    fun pay(view: View) {

        val sharedpreferences = getSharedPreferences("ecollector", MODE_PRIVATE)
        val logInResponse = Gson().fromJson(
            sharedpreferences.getString("profile", ""),
            LogInResponse::class.java
        )

        val request = PayRequest()
        request.userId = user!!.userId

        Rx2AndroidNetworking.post(APIEndPoint.PAY)
                .addHeaders("Authorization", "Bearer " + logInResponse.accessToken)
                .addApplicationJsonBody(request)
                .build()
                .getParseSingle(object : TypeToken<PayResponse>() {})
                .subscribeOn(AppSchedulerProvider().io())
                .observeOn(AppSchedulerProvider().ui())
                .subscribe(object : DisposableSingleObserver<PayResponse>() {
                    override fun onSuccess(response: PayResponse) {
                        Toast.makeText(this@CollectorActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE
                        val anError = e as ANError
                        Toast.makeText(
                            this@CollectorActivity,
                            anError.errorBody,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    fun convertBase64() :String {
        if (bitmapD != null) {
            val baos = ByteArrayOutputStream()
            bitmapD?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageBytes: ByteArray = baos.toByteArray()
            return "data:" + Base64.encodeToString(imageBytes, Base64.DEFAULT) + ";base64,"
        }
        return ""
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
                val uri = result.uri
                val imageView = ImageView(this)
                imageView.setImageURI(uri)
                val bitmap = imageView.drawable as BitmapDrawable
                val recognizer = TextRecognizer.Builder(applicationContext).build()
                if (!recognizer.isOperational) {
                    Toast.makeText(this, "Not Operational", Toast.LENGTH_SHORT).show()
                } else {
                    val frame = Frame.Builder().setBitmap(bitmap.bitmap).build()
                    val items = recognizer.detect(frame)
                    val sd = StringBuilder()
                    for (i in 0 until items.size()) {
                        val textBlock = items.valueAt(i)
                        sd.append(textBlock.value)
                    }
                    if (testInputEditText != null) {
                        testInputEditText!!.setText(sd.toString())
                    }
                }
                return
            }
            onPhotoTaken()
        }
    }

    var  bitmapD:Bitmap? = null
    protected fun onPhotoTaken() {
        val options = BitmapFactory.Options()
        options.inSampleSize = 4
        var bitmap = BitmapFactory.decodeFile(_path, options)
        try {
            val exif = ExifInterface(_path!!)
            val exifOrientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            var rotate = 0
            when (exifOrientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
            }
            if (rotate != 0) {

                // Getting width & height of the given image.
                val w = bitmap.width
                val h = bitmap.height

                // Setting pre rotate
                val mtx = Matrix()
                mtx.preRotate(rotate.toFloat())

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false)
            }
            // Convert to ARGB_8888, required by tess
            CropImage.activity(outputFileUri).setGuidelines(CropImageView.Guidelines.ON).start(this)
            val imageView = ImageView(this)
            imageView.setImageBitmap(bitmap.copy(Bitmap.Config.ARGB_8888, true))
            bitmapD = bitmap
        } catch (e: IOException) {

        }
    }

    override fun onProgress(bytesUploaded: Long, totalBytes: Long) {
        val progress = (bytesUploaded * 100 / totalBytes).toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, true)
        } else progressBar.progress = progress
        if (totalBytes == bytesUploaded) {
            val file = File(_path)
            file.delete()
        }
    }


}