package com.example.budgetapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.budgetapp.R;
import com.example.budgetapp.RestApi.RetrofitClient;
import com.example.budgetapp.models.Transaction;
import com.example.budgetapp.models.TransactionRequest;
import com.example.budgetapp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowActivity extends AppCompatActivity {
    private Button edit_btn;
    private Button delete_btn;
    private ImageView imageView;
    private EditText text_description;
    private EditText text_Amount;
    private Spinner spinner;
    private TransactionRequest transaction;
    private int id;
    private String attachment;
    private Double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_transaction);
        delete_btn=(Button) findViewById(R.id.btn_delete);
        edit_btn = (Button) findViewById(R.id.btn_add);
        imageView = (ImageView) findViewById(R.id.imageView8);
        text_Amount = (EditText) findViewById(R.id.text_amount);
        text_description = (EditText) findViewById(R.id.text_description);

        Intent intent=getIntent();
        amount=intent.getDoubleExtra("amount",0);
        text_Amount.setText(String.valueOf(amount));
        text_description.setText(intent.getStringExtra("description"));
        attachment=intent.getStringExtra("attachment");
        byte[] decodedString= Base64.decode(attachment,Base64.DEFAULT);
        Bitmap decodedImage= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        imageView.setImageBitmap(decodedImage);
        id=intent.getIntExtra("id",-1);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTransaction();
            }
        });
        
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTransaction();
            }
        });
    }

    private void deleteTransaction() {
        Call<Void> call = RetrofitClient.getInstance().getApi().deleteTransaction(id, SharedPrefManager.getInstance(ShowActivity.this).getjwt());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==204){
                    Toast.makeText(ShowActivity.this, "Transaction Deleted", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ShowActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void editTransaction() {
        String description = text_description.getText().toString();
        String amount = text_Amount.getText().toString();
        transaction=new TransactionRequest(description);
        transaction.setAmount(amount);
        transaction.setAttachment(attachment);
        Call<Void> call = RetrofitClient.getInstance().getApi().editTransaction(id,SharedPrefManager.getInstance(ShowActivity.this).getjwt(),transaction);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==204){
                    Toast.makeText(ShowActivity.this, "Transaction Updated", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
