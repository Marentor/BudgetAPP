package com.example.budgetapp.RestApi;

import com.example.budgetapp.models.AuthRequest;
import com.example.budgetapp.models.AuthResponse;
import com.example.budgetapp.models.Category;
import com.example.budgetapp.models.CategoryRequest;
import com.example.budgetapp.models.Transaction;
import com.example.budgetapp.models.TransactionRequest;
import com.example.budgetapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @POST("/auth")
    Call<AuthResponse> getjwt(@Body AuthRequest authRequest);

    @GET("/auth")
    Call<User> userLogin(@Header("Authorization") String jwt);

    @GET("/transactions")
    Call<List<Transaction>> getTransactions(@Header("Authorization") String jwt);

    @POST("/transactions")
    Call<Transaction> createTransaction(@Header("Authorization") String jwt, @Body TransactionRequest transactionRequest);

    @GET("/categories")
    Call<List<Category>> getCategories(@Header("Authorization") String jwt);

    @POST("/categories")
    Call<Category> createCategory(@Header("Authorization") String jwt, @Body CategoryRequest categoryRequest);

    @DELETE("/transactions/{id}")
    Call<Void> deleteTransaction (@Path("id") int id, @Header("Authorization") String jwt);

    @DELETE("/categories/{id}")
    Call<Void> deleteCategory (@Path("id") int id, @Header("Authorization") String jwt);

    @PUT("/transactions/{id}")
    Call<Void> editTransaction(@Path("id") int id, @Header("Authorization") String jwt, @Body TransactionRequest transactionRequest);



}
