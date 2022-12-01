package com.example.login1229219.Adapters;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.login1229219.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SlidersAdapter extends SliderViewAdapter<SlidersAdapter.Holder>{

    Bitmap[] images;

    public SlidersAdapter(Bitmap[] images){

        this.images = images;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slideritems,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.imageView.setImageBitmap(images[position]);


    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class Holder extends  SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public Holder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

        }
    }

}
