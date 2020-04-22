package com.example.budgetapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.budgetapp.models.AuthResponse;
import com.example.budgetapp.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME ="my_shared_preff";
    private  static SharedPrefManager mInstance;
    private Context mContext;

    private SharedPrefManager(Context mContext){
        this.mContext=mContext;
    }

    public static synchronized SharedPrefManager getInstance(Context mContext){
        if(mInstance==null){
            mInstance= new SharedPrefManager(mContext);

        }
        return mInstance;
    }

    public void saveJwt(String jwt){
        SharedPreferences sharedPreferences=  mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("jwt",jwt);
        editor.apply();


    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString("jwt","null")!="null";

    }
    public String getjwt(){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("jwt","null");
    }

    public  void clear(){
        SharedPreferences sharedPreferences=  mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
