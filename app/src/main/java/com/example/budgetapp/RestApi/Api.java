package com.example.budgetapp.RestApi;

import com.example.budgetapp.models.AuthRequest;
import com.example.budgetapp.models.AuthResponse;
import com.example.budgetapp.models.Transaction;
import com.example.budgetapp.models.TransactionRequest;
import com.example.budgetapp.models.TransactionsResponse;
import com.example.budgetapp.models.User;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @POST("/auth")
    Call<AuthResponse> getjwt( @Body AuthRequest authRequest);

    @GET("/auth")
    Call<User> userLogin(@Header("Authorization") String jwt);

    @GET("/transactions")
    Call<List<Transaction>> getTransactions(@Header("Authorization") String jwt);

    @POST("transactions")
    Call<Transaction> createTransaction(@Header("Authorization") String jwt, @Body TransactionRequest transactionRequest );
}
