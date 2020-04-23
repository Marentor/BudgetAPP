package com.example.budgetapp.Activity.ui.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.Activity.AddActivity;
import com.example.budgetapp.Activity.LoginActivity;
import com.example.budgetapp.Activity.MainActivity;
import com.example.budgetapp.R;
import com.example.budgetapp.RestApi.RetrofitClient;
import com.example.budgetapp.adapters.TransactionsAdapter;
import com.example.budgetapp.models.AuthResponse;
import com.example.budgetapp.models.Transaction;
import com.example.budgetapp.models.TransactionsResponse;
import com.example.budgetapp.models.User;
import com.example.budgetapp.storage.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.budgetapp.storage.SharedPrefManager.getInstance;

public class TransactionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Transaction> transactionList;
    private TransactionsAdapter adapter;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transactions, container, false);
        recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new TransactionsAdapter(getActivity(),transactionList);
        recyclerView.setAdapter(adapter);
        fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTransactionActivity();

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Call<List<Transaction>> call= RetrofitClient.getInstance().getApi().getTransactions(SharedPrefManager.getInstance(getActivity()).getjwt());

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                 transactionList=response.body();
                 adapter=new TransactionsAdapter(getActivity(),transactionList);
                 recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
    });

    }

    private void openAddTransactionActivity(){
        Intent intent=new Intent(getActivity(), AddActivity.class);
        startActivity(intent);
    }



}
