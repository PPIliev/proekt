package com.example.login1229219.Adapters;

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

import com.example.login1229219.Helpers.ProductsHelper;
import com.example.login1229219.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList product_id, product_title, product_author, product_price, product_image;
    private recyclerViewClickListener listener;
    private recyclerViewMenuClick mListener;
    ProductsHelper pHelper = new ProductsHelper();

    public CustomAdapter(Context context, ArrayList product_id, ArrayList product_title, ArrayList product_author, ArrayList product_price, ArrayList product_image, recyclerViewClickListener listener, recyclerViewMenuClick mListener) {
        this.context = context;
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_author = product_author;
        this.product_price = product_price;
        this.product_image = product_image;
        this.listener = listener;
        this.mListener = mListener;
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
        holder.iv_productImage.setImageBitmap(pHelper.stringToBitmap(String.valueOf(product_image.get(position))));

    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
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
            showPopUpMenu(view);
            listener.onClick(view, getAdapterPosition());
        }
        public void showPopUpMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.cmenu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int position = getAdapterPosition();
            mListener.onMenuItem(position, menuItem);
            return false;
        }
    }

    public interface recyclerViewClickListener {
        void onClick(View v, int position);
    }

    public interface recyclerViewMenuClick {
        void onMenuItem(int position, MenuItem menuItem);
    }



}
