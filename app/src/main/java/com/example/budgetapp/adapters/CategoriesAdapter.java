package com.example.budgetapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.R;
import com.example.budgetapp.RestApi.RetrofitClient;
import com.example.budgetapp.models.Category;
import com.example.budgetapp.storage.SharedPrefManager;

import java.text.ParseException;
import java.util.List;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private Context mContext;
    private List<Category> categoryList;
    private String jwt;
    private Function<Integer, Integer> openCategoryEdit;

    public CategoriesAdapter(Context mContext, List<Category> categoryList, String jwt, Function<Integer, Integer> openCategoryEdit) {
        this.mContext = mContext;
        this.categoryList = categoryList;
        this.jwt = jwt;
        this.openCategoryEdit = openCategoryEdit;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        if (categoryList == null) {
            return 0;
        }
        return categoryList.get(position).getId();
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.categories_recycler_item, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.textCategory.setText(category.getName());
        holder.deleteButton.setOnClickListener(v -> {
            categoryList.remove(category);
            notifyDataSetChanged();

            Call<Void> call = RetrofitClient.getInstance().getApi().deleteCategory(category.getId(), jwt);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        });
        holder.editButton.setOnClickListener(v -> {
            openCategoryEdit.apply(position);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }


    class CategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView textCategory;
        ImageView editButton;
        ImageView deleteButton;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.text_category);
            editButton = itemView.findViewById(R.id.edit);
            deleteButton = itemView.findViewById(R.id.delete);
        }
    }

}
