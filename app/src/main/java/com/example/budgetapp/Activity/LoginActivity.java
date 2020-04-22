package com.example.budgetapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetapp.R;
import com.example.budgetapp.RestApi.RetrofitClient;
import com.example.budgetapp.models.AuthRequest;
import com.example.budgetapp.models.AuthResponse;
import com.example.budgetapp.models.User;
import com.example.budgetapp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText userText;
    private EditText passwordText;
    private  Button loginButton;
    private TextView signupLink;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userText=(EditText) findViewById(R.id.input_username) ;
        passwordText=(EditText) findViewById(R.id.input_password);
        loginButton=(Button) findViewById(R.id.btn_login) ;
        signupLink=(TextView) findViewById(R.id.link_signup);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

//        _signupLink.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Start the Signup activity
//                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivityForResult(intent, REQUEST_SIGNUP);
//            }
//        });
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        String username = userText.getText().toString();
        String password = passwordText.getText().toString();
        AuthRequest request=new AuthRequest(username,password );
        Call<AuthResponse> call= RetrofitClient
                .getInstance().getApi().getjwt(request);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.body() != null) {
                    AuthResponse authResponse =response.body();
                    SharedPrefManager.getInstance(LoginActivity.this).saveJwt("Bearer " + authResponse.getJwt());
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();


                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



        new android.os.Handler().postDelayed(
                () -> {
                    // On complete call either onLoginSuccess or onLoginFailed
                    onLoginSuccess();
                    // onLoginFailed();
                    progressBar.setVisibility(View.GONE);;
                }, 3000);
    }






    public void onLoginSuccess() {
        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = userText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty() ) {
            userText.setError("enter a username");
            valid = false;
        } else {
            userText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 12) {
            passwordText.setError("between 4 and 12 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}