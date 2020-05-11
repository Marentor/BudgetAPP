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
        String nameIntent = getIntent().getStringExtra("name");
        if (nameIntent != null) {
            text_category.setText(nameIntent);
        }

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
        String categoryName = text_category.getText().toString();
        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Missing category name", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog loading = new ProgressDialog(CategoryActivity.this);
        loading.setMessage("Please Wait...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        category = new CategoryRequest();
        category.setName(text_category.getText().toString());

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("id", 0);
        if (categoryId > 0) {
            APIeditCategory(loading, categoryId);
        } else {
            APIcreateCategory(loading);
        }

    }

    private void APIeditCategory(ProgressDialog loading, int categoryId) {
        Call<Void> call = RetrofitClient.getInstance().getApi().editCategory(categoryId, SharedPrefManager.getInstance(CategoryActivity.this).getjwt(), category);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loading.dismiss();
                Toast.makeText(CategoryActivity.this, "Category updated", Toast.LENGTH_LONG).show();
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void APIcreateCategory(ProgressDialog loading) {
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
