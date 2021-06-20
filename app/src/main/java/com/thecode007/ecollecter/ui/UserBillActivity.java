package com.thecode007.ecollecter.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thecode007.ecollecter.R;
import com.thecode007.ecollecter.network.APIEndPoint;
import com.thecode007.ecollecter.network.model.CalculateRequest;
import com.thecode007.ecollecter.network.model.CalculateResponse;
import com.thecode007.ecollecter.network.model.LogInResponse;
import com.thecode007.ecollecter.rx.AppSchedulerProvider;

public class UserBillActivity extends Activity {

    public static final String DATA_PATH = Environment
            .getExternalStorageDirectory().toString() + "/";
    private static final String TAG = "UserBillActivity.java";
    protected EditText _field;
    protected String _path;
    protected boolean _taken;
    TextView tv_current_cost;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bill);

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.getDeniedPermissionResponses().size() > 0) {
                    onBackPressed();
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

        _path = DATA_PATH + "/ocr.jpg";
        _field = (EditText) findViewById(R.id.et_current_consumption);
        tv_current_cost = findViewById(R.id.tv_current_cost);
        _field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                findViewById(R.id.btn_calculate).setEnabled(!TextUtils.isEmpty(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        findViewById(R.id.iv_bill_history).setOnClickListener(view -> {
            startActivity(new Intent(this, BillHistoryActivity.class));
        });
    }

    public void scan(View view) {
        new ButtonClickHandler().onClick(view);
    }

    @SuppressLint({"CheckResult", "DefaultLocale"})
    public void calculate(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("ecollector", Context.MODE_PRIVATE);
        LogInResponse logInResponse = new Gson().fromJson(sharedpreferences.getString("profile", ""), LogInResponse.class);

        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setUser_id(logInResponse.getUserId() + "");
        calculateRequest.setCurrent(Integer.parseInt(_field.getText().toString()) + "");

        Rx2AndroidNetworking.post(APIEndPoint.CALCULATE)
                .addApplicationJsonBody(calculateRequest)
                .addHeaders("Authorization", "Bearer " + logInResponse.getAccessToken())
                .build()
                .getParseSingle(new TypeToken<CalculateResponse>() {
                })
                .subscribeOn(new AppSchedulerProvider().io())
                .observeOn(new AppSchedulerProvider().ui())
                .subscribe(response -> {
                    tv_current_cost.setText(String.format("%d L.L", response.getToPay()));
                    }, error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                });
    }

    public class ButtonClickHandler implements View.OnClickListener {
        public void onClick(View view) {
            Log.v(TAG, "Starting Camera app");
            startCameraActivity();
        }
    }


    private Uri outputFileUri = null;
    protected void startCameraActivity() {
        File file = new File(_path);
        outputFileUri = Uri.fromFile(file);
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "resultCode: " + resultCode);

        if (resultCode == -1) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri uri = result.getUri();

                ImageView imageView = new ImageView(this);

                imageView.setImageURI(uri);

                BitmapDrawable bitmap = (BitmapDrawable) imageView.getDrawable();
                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Not Operational", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap.getBitmap()).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);

                    StringBuilder sd = new StringBuilder();
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock textBlock = items.valueAt(i);
                        sd.append(textBlock.getValue());
                    }
                    _field.setText(sd.toString());
                }
                return;
            }
            onPhotoTaken();
        } else {
            Log.v(TAG, "User cancelled");
        }
    }

    protected void onPhotoTaken() {
        _taken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile(_path, options);

        try {
            ExifInterface exif = new ExifInterface(_path);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            Log.v(TAG, "Orient: " + exifOrientation);

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            Log.v(TAG, "Rotation: " + rotate);

            if (rotate != 0) {

                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
            }
            // Convert to ARGB_8888, required by tess
            CropImage.activity(outputFileUri).setGuidelines(CropImageView.Guidelines.ON).start(this);
            bitmap.copy(Bitmap.Config.ARGB_8888, true);
        } catch (IOException e) {
            Log.e(TAG, "Couldn't correct orientation: " + e.toString());
        }
    }
}

