package com.example.budgetapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.R;
import com.example.budgetapp.models.Category;
import com.example.budgetapp.models.Transaction;
import com.example.budgetapp.models.User;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {
    private Context mContext;
    private List<Transaction> transactionList;
    private OntransactionListener ontransactionListener;
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public TransactionsAdapter(Context mContext, List<Transaction> transactionList, OntransactionListener ontransactionListener) {
        this.mContext = mContext;
        this.transactionList = transactionList;
        this.ontransactionListener=ontransactionListener;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.usercard, parent, false);
        return new TransactionsViewHolder(view,ontransactionListener );
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.textAmount.setText(String.valueOf(transaction.getAmount()));
        holder.textDescription.setText(transaction.getDescription());
        try {
            holder.textCreated_at.setText(transaction.getCreated_at());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Category mcategory= categoryList.stream()
                .filter(category -> transaction.getCategory_id()==(category.getId()))
                .findAny()
                .orElse(null);
        if(mcategory==null){
            holder.textCategory.setText("");
        }
        else {
            String category = mcategory.getName();
            holder.textCategory.setText(category);
        }
    }

    @Override
    public int getItemCount() {
        return transactionList == null ? 0 : transactionList.size();
    }


    class TransactionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textDescription, textAmount, textCategory, textCreated_at;
        OntransactionListener ontransactionListener;

        public TransactionsViewHolder(@NonNull View itemView,OntransactionListener ontransactionListener) {
            super(itemView);

            textAmount = itemView.findViewById(R.id.text_amount);
            textDescription = itemView.findViewById(R.id.text_description);
            textCreated_at = itemView.findViewById(R.id.text_created_at);
            textCategory = itemView.findViewById(R.id.text_category);
            this.ontransactionListener = ontransactionListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ontransactionListener.onTransactionClick(getAdapterPosition());

        }
    }

    public interface  OntransactionListener{
        void onTransactionClick(int position);

    }

}
