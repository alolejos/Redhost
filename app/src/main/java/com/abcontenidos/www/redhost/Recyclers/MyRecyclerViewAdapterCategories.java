package com.abcontenidos.www.redhost.Recyclers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.abcontenidos.www.redhost.Objets.Category;
import com.abcontenidos.www.redhost.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyRecyclerViewAdapterCategories extends RecyclerView.Adapter<MyRecyclerViewAdapterCategories.ViewHolder> {

    private ArrayList<Category> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public MyRecyclerViewAdapterCategories(Context context, ArrayList<Category> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_category, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textName.setText(mData.get(position).getName());
        holder.textDetails.setText(mData.get(position).getDetails());
        holder.switchSelected.setChecked(Boolean.parseBoolean(mData.get(position).getSelected()));
        Picasso.get().load(mData.get(position).getImage()).resize(120, 120).into(holder.imageCategory);
    }


    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        TextView textName, textDetails;
        ImageView imageCategory;
        Switch switchSelected;

        ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName1);
            textDetails = itemView.findViewById(R.id.textCategoryDetails);
            imageCategory = itemView.findViewById(R.id.imageCategory);
            switchSelected = itemView.findViewById(R.id.switchCategory);
            switchSelected.setOnCheckedChangeListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mData.get(getAdapterPosition()).setSelected(Boolean.toString(isChecked));

        }
    }

    // convenience method for getting data at click position
    Category getItem(int id) {
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
