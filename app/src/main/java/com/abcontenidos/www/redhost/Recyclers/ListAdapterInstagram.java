package com.abcontenidos.www.redhost.Recyclers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcontenidos.www.redhost.Objets.Post;
import com.abcontenidos.www.redhost.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapterInstagram extends RecyclerView.Adapter<ListAdapterInstagram.ViewHolder> {

    private ArrayList<Post> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public ListAdapterInstagram(Context context, ArrayList<Post> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_grid_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = "http://redoff.bithive.cloud/files/posts/"+mData.get(position).getImage();
        holder.textName.setText(mData.get(position).getName());
        holder.textDetails.setText(mData.get(position).getDetails());
        holder.textCommerce.setText(mData.get(position).getCommerce());
        Picasso.get().load(image).resize(400, 400).into(holder.imagePromo);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textName, textDetails, textCommerce;
        ImageView imagePromo;

        ViewHolder(View itemView) {
            super(itemView);
            textName= itemView.findViewById(R.id.textNameGrid);
            textDetails = itemView.findViewById(R.id.textDetailsGrid);
            textCommerce = itemView.findViewById(R.id.textCommerceGrid);
            imagePromo = itemView.findViewById(R.id.imagePromoGrid);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Post getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
