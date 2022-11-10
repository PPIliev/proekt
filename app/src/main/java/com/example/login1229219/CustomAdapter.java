package com.example.login1229219;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList product_id, product_title, product_author, product_price;
    private recyclerViewClickListener listener;

    public CustomAdapter(Context context, ArrayList product_id, ArrayList product_title, ArrayList product_author, ArrayList product_price, recyclerViewClickListener listener) {
        this.context = context;
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_author = product_author;
        this.product_price = product_price;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_productID.setText(String.valueOf(product_id.get(position)));
        holder.tv_productTitle.setText(String.valueOf(product_title.get(position)));
        holder.tv_productAuthor.setText(String.valueOf(product_author.get(position)));
        holder.tv_productPrice.setText(String.valueOf(product_price.get(position)));

    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_productID, tv_productTitle, tv_productAuthor, tv_productPrice;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productID = itemView.findViewById(R.id.tv_productID);
            tv_productTitle = itemView.findViewById(R.id.tv_productTitle);
            tv_productAuthor = itemView.findViewById(R.id.tv_productAuhor);
            tv_productPrice = itemView.findViewById(R.id.tv_productPrice);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface recyclerViewClickListener {
        void onClick(View v, int position);
    }




}
