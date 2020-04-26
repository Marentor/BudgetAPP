package com.example.budgetapp.Activity.ui.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.Activity.AddActivity;
import com.example.budgetapp.Activity.ShowActivity;
import com.example.budgetapp.R;
import com.example.budgetapp.RestApi.RetrofitClient;
import com.example.budgetapp.adapters.TransactionsAdapter;
import com.example.budgetapp.adapters.TransactionsAdapter.OntransactionListener;
import com.example.budgetapp.models.Category;
import com.example.budgetapp.models.Transaction;
import com.example.budgetapp.storage.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsFragment extends Fragment implements OntransactionListener {

    public static int ADD_ACTIVITY_REQ_CODE = 137;
    private RecyclerView recyclerView;
    private List<Transaction> transactionList;
    private TransactionsAdapter adapter;
    private FloatingActionButton fab;
    private List<Category> categoryList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transactions, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TransactionsAdapter(getActivity(), transactionList,this);
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

        Call<List<Category>> call = RetrofitClient.getInstance().getApi().getCategories(SharedPrefManager.getInstance(getActivity()).getjwt());

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                adapter.setCategoryList(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

       loadTransactionsApi();

    }

    private void openAddTransactionActivity() {
        Intent intent = new Intent(getActivity(), AddActivity.class);
        startActivityForResult(intent,ADD_ACTIVITY_REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadTransactionsApi();
    }

    @Override
    public void onTransactionClick(int position) {
        Intent intent= new Intent(getActivity(), ShowActivity.class);
        intent.putExtra("description",transactionList.get(position).getDescription());
        intent.putExtra("amount",transactionList.get(position).getAmount());
        intent.putExtra("attachment",transactionList.get(position).getAttachment());
        intent.putExtra("id",transactionList.get(position).getId());
        startActivityForResult(intent,ADD_ACTIVITY_REQ_CODE);
    }

    private void loadTransactionsApi(){
        Call<List<Transaction>> newcall = RetrofitClient.getInstance().getApi().getTransactions(SharedPrefManager.getInstance(getActivity()).getjwt());
        newcall.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                transactionList = response.body();
                //  adapter = new TransactionsAdapter(getActivity(), transactionList,);
                adapter.setTransactionList(transactionList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
