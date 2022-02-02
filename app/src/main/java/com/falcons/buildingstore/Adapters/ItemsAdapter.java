package com.falcons.buildingstore.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    List<Item> itemsList;
    Context context;

    public ItemsAdapter(List<Item> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.items_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.mainLayout.setOnClickListener(v -> {
            if (holder.expandedLayout.getVisibility() == View.GONE)
                holder.expandedLayout.setVisibility(View.VISIBLE);
            else
                holder.expandedLayout.setVisibility(View.GONE);
        });

        holder.itemName.setText(itemsList.get(position).getItemName());
        holder.itemCode.setText(itemsList.get(position).getItemOCode());
        holder.itemKind.setText(itemsList.get(position).getItemKind());
        holder.itemPrice.setText(itemsList.get(position).getPrice()+"");
        holder.tax.setText(itemsList.get(position).getTax());


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout mainLayout;
        LinearLayout expandedLayout;

        ImageView itemImg;
        TextView itemName, itemCode, itemKind, itemPrice, tax;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);

            itemImg = itemView.findViewById(R.id.itemImg);
            itemName = itemView.findViewById(R.id.itemName);
            itemCode = itemView.findViewById(R.id.itemCode);
            itemKind = itemView.findViewById(R.id.itemKind);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            tax = itemView.findViewById(R.id.tax);

        }
    }

}
