package com.falcons.buildingstore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;

import java.util.List;


public class BasketItemsAdapter extends RecyclerView.Adapter<BasketItemsAdapter.ViewHolder>{

    private Context context;
    private List<Item> itemList;

    public BasketItemsAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.basket_item_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.item_name.setText(itemList.get(position).getItemName());
        holder.item_code.setText(itemList.get(position).getItemNCode());
        holder.item_qty.setText(String.valueOf(itemList.get(position).getQty()));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, item_code, item_qty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            item_code = itemView.findViewById(R.id.item_code);
            item_qty = itemView.findViewById(R.id.item_qty);

        }
    }

}
