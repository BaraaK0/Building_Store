package com.falcons.buildingstore.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;
import com.falcons.buildingstore.R;

import org.w3c.dom.Text;

import java.util.List;

public class OrderReportAdapter extends RecyclerView.Adapter<OrderReportAdapter.ViewHolder> {
    Context context;
    List<OrdersDetails> list;

    public OrderReportAdapter(Context context, List<OrdersDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_report_row, parent, false);

        return new OrderReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("Tax==",list.get(position).getTax()+"");
   holder.tax.setText(list.get(position).getTax()+"");
        holder.discount.setText(list.get(position).getDiscount()+"");
        holder.price.setText(list.get(position).getPrice() +"");
        holder.qty.setText(list.get(position).getQty()+"");
        holder.item_name.setText(list.get(position).getItemName());
        holder.itemNCode.setText(list.get(position).getItemNo());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{

   TextView tax,discount,price,qty,item_name,itemNCode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
              tax=itemView.findViewById(R.id.taxx);
              discount=itemView.findViewById(R.id.discount);
              price=itemView.findViewById(R.id.price);
              qty=itemView.findViewById(R.id.qty);
              item_name=itemView.findViewById(R.id.item_name);
              itemNCode=itemView.findViewById(R.id.itemNCode);
        }
    }
}
