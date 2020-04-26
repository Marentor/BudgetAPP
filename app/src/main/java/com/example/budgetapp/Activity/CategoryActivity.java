package com.example.budgetapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetapp.R;
import com.example.budgetapp.RestApi.RetrofitClient;
import com.example.budgetapp.models.Category;
import com.example.budgetapp.models.CategoryRequest;
import com.example.budgetapp.storage.SharedPrefManager;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private Button btn_add;
    private EditText text_category;
    private CategoryRequest category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        text_category = findViewById(R.id.text_category);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(v -> sendData());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void sendData() {
        final ProgressDialog loading = new ProgressDialog(CategoryActivity.this);
        loading.setMessage("Please Wait...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        category = new CategoryRequest();
        category.setName(text_category.getText().toString());
        Call<Category> call = RetrofitClient.getInstance().getApi().createCategory(SharedPrefManager.getInstance(CategoryActivity.this).getjwt(), category);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                loading.dismiss();
                Toast.makeText(CategoryActivity.this, "Category created", Toast.LENGTH_LONG).show();

                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
