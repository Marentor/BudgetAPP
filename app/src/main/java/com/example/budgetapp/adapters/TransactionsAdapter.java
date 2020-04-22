package com.example.budgetapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.R;
import com.example.budgetapp.models.Transaction;
import com.example.budgetapp.models.User;

import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {
    private Context mContext;
    private List<Transaction> transactionList;

    public TransactionsAdapter(Context mContext,List<Transaction> transactionList) {
        this.mContext = mContext;
        this.transactionList=transactionList;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.usercard,parent,false);
        return new TransactionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        Transaction transaction =transactionList.get(position);
        holder.textAmount.setText(String.valueOf(transaction.getAmount()    ));
        holder.textDescription.setText(transaction.getDescription());
        holder.textCreated_at.setText(transaction.getCreated_at());
    }

    @Override
    public int getItemCount() {

        int a ;

        if(transactionList != null && !transactionList.isEmpty()) {

            a =transactionList.size();
        }
        else {

            a = 0;

        }

        return a;
    }


    class TransactionsViewHolder extends RecyclerView.ViewHolder{
        TextView textDescription,textAmount,textCategory,textCreated_at;

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);

            textAmount=itemView.findViewById(R.id.text_amount);
            textDescription=itemView.findViewById(R.id.text_description);
            textCreated_at=itemView.findViewById(R.id.text_created_at);
            textCategory=itemView.findViewById(R.id.text_category);
        }
    }

}
