package com.example.budgetapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.budgetapp.R;
import com.example.budgetapp.RestApi.RetrofitClient;
import com.example.budgetapp.models.AuthRequest;
import com.example.budgetapp.models.AuthResponse;
import com.example.budgetapp.models.Transaction;
import com.example.budgetapp.models.TransactionRequest;
import com.example.budgetapp.storage.SharedPrefManager;

import java.io.ByteArrayOutputStream;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private Button upload_btn;
    private ImageView imageView;
    private EditText text_description;
    private EditText text_Amount;
    private Spinner spinner;
    private TransactionRequest transaction;

    private String attachment;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        upload_btn = (Button) findViewById(R.id.btn_add);
        imageView = (ImageView) findViewById(R.id.imageView8);
        text_Amount = (EditText) findViewById(R.id.text_amount);
        text_description = (EditText) findViewById(R.id.text_description);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void sendData() {
        final ProgressDialog loading = new ProgressDialog(AddActivity.this);
        loading.setMessage("Please Wait...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);
        String description = text_description.getText().toString();
        String amount = text_Amount.getText().toString().toString();

        transaction = new TransactionRequest(description);
        transaction.setAmount(amount);
        transaction.setAttachment(attachment);
        Call<Transaction> call = RetrofitClient.getInstance().getApi().createTransaction(SharedPrefManager.getInstance(AddActivity.this).getjwt(), transaction);
        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                loading.dismiss();
                if (response.code() == 200) {
                    Toast.makeText(AddActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Toast.makeText(AddActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void selectImage() {
        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        }
        Uri selectedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String picturePath = c.getString(columnIndex);
        c.close();
        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
        thumbnail = getResizedBitmap(thumbnail, 400);
        imageView.setImageBitmap(thumbnail);
        BitMapToString(thumbnail);

    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        attachment = Base64.encodeToString(b, Base64.DEFAULT);
        return attachment;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
