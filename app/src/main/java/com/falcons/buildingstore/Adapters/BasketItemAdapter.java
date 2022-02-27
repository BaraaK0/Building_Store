package com.falcons.buildingstore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falcons.buildingstore.Activities.HomeActivity;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;

import java.util.ArrayList;

import static com.falcons.buildingstore.Activities.HomeActivity.badge;
import static com.falcons.buildingstore.Activities.HomeActivity.vocher_Items;

public class BasketItemAdapter extends RecyclerView.Adapter<BasketItemAdapter.VoherItemAdapterViewHolder> {
    ArrayList<Item> items;
    Context context;

    public BasketItemAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public VoherItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_row, parent, false);
        return new BasketItemAdapter.VoherItemAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoherItemAdapterViewHolder holder, int position) {
        holder.tax.setText(items.get(position).getTax() + "");
        holder.discount.setText(items.get(position).getDiscount() + "");
        holder.price.setText(items.get(position).getPrice() + "");
        holder.qty.setText(items.get(position).getQty() + "");
        holder.item_name.setText(items.get(position).getItemName());
        holder.itemNCode.setText(items.get(position).getItemNCode());
        holder.area.setText(items.get(position).getArea());

        holder. reomveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.vocher_Items.remove(position);
                notifyItemRemoved(position);
                badge.setNumber(vocher_Items.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VoherItemAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tax, discount, price, qty, item_name, itemNCode,area,reomveItem;

        public VoherItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tax = itemView.findViewById(R.id.taxx);
            discount = itemView.findViewById(R.id.discount);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            item_name = itemView.findViewById(R.id.item_name);
            itemNCode = itemView.findViewById(R.id.itemNCode);
            area= itemView.findViewById(R.id.area);
            reomveItem=itemView.findViewById(R.id.reomveItem);
        }
    }
}
