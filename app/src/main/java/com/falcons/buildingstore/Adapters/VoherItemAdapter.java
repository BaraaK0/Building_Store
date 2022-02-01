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

import java.util.ArrayList;

public class VoherItemAdapter extends RecyclerView.Adapter<VoherItemAdapter.VoherItemAdapterViewHolder> {
ArrayList<Item>items;
Context context;

   public VoherItemAdapter(ArrayList<Item> items, Context context) {
      this.items = items;
      this.context = context;
   }

   @NonNull
   @Override
   public VoherItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_row, parent, false);
      return new VoherItemAdapter.VoherItemAdapterViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull VoherItemAdapterViewHolder holder, int position) {
      holder.itemNCode.setText(items.get(position).getItemNCode());
              holder.discount.setText(items.get(position).getDiscount()+"");
                      holder.qty.setText(items.get(position).getQty()+"");
   }

   @Override
   public int getItemCount() {
      return items.size();
   }

   class VoherItemAdapterViewHolder extends RecyclerView.ViewHolder{
      TextView itemNCode,discount,qty;
      public VoherItemAdapterViewHolder(@NonNull View itemView) {


         super(itemView);
         itemNCode=itemView.findViewById(R.id.itemNCode);
         discount=itemView.findViewById(R.id.discount);
         qty=itemView.findViewById(R.id.qty);
      }
   }
}
