package com.example.budgetapp.Activity.ui.categories;

import android.app.Activity;
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

import com.example.budgetapp.Activity.CategoryActivity;
import com.example.budgetapp.R;
import com.example.budgetapp.RestApi.RetrofitClient;
import com.example.budgetapp.adapters.CategoriesAdapter;
import com.example.budgetapp.models.Category;
import com.example.budgetapp.storage.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {

    public static int ADD_ACTIVITY_REQ_CODE = 137;

    private RecyclerView recyclerView;
    private List<Category> categoryList = new ArrayList<>();
    private CategoriesAdapter adapter;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        recyclerView = root.findViewById(R.id.category_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CategoriesAdapter(getContext(), categoryList, SharedPrefManager.getInstance(getActivity()).getjwt());
        recyclerView.setAdapter(adapter);
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> openAddCategoryActivity());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadCategoriesApi();

    }

    private void loadCategoriesApi() {
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
    }

    private void openAddCategoryActivity() {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        startActivityForResult(intent, ADD_ACTIVITY_REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            loadCategoriesApi();
        }
    }
}
