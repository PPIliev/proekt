package com.example.login1229219;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder> {
    private Context context;
    private ArrayList product_id, product_title, product_author, product_price, product_image;
    private recyclerViewClickListener listener;

    public CustomAdapter2(Context context, ArrayList product_id, ArrayList product_title, ArrayList product_author, ArrayList product_price, ArrayList product_image, recyclerViewClickListener listener) {
        this.context = context;
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_author = product_author;
        this.product_price = product_price;
        this.product_image = product_image;
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
        holder.iv_productImage.setImageBitmap(stringToBitmap(String.valueOf(product_image.get(position))));

    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_productID, tv_productTitle, tv_productAuthor, tv_productPrice;
        ImageView iv_productImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productID = itemView.findViewById(R.id.tv_productID);
            tv_productTitle = itemView.findViewById(R.id.tv_productTitle);
            tv_productAuthor = itemView.findViewById(R.id.tv_productAuhor);
            tv_productPrice = itemView.findViewById(R.id.tv_productPrice);
            iv_productImage = itemView.findViewById(R.id.iv_productImage);

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


    private Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] encodeByte = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
