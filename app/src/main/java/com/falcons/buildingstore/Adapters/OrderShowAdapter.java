package com.falcons.buildingstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.falcons.buildingstore.Activities.OrderReport;
import com.falcons.buildingstore.Activities.ShowPreviousOrder;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.R;

import java.util.List;

public  class OrderShowAdapter extends RecyclerView.Adapter<OrderShowAdapter.ViewHolder> {
Context context;
List<OrderMaster>orderMasters;

    public OrderShowAdapter(Context context, List<OrderMaster> orderMasters) {
        this.context = context;
        this.orderMasters = orderMasters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pre_order_row, parent, false);

        return new OrderShowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.VHFNO.setText("Order# "+orderMasters.get(position).getVhfNo()+"");
        holder.CONFIRMSTATE.setText(orderMasters.get(position).getConfirmState()+"");
   //     holder.cusName.setText(orderMasters.get(position).getCustomerId()+"");
        if(orderMasters.get(position).getConfirmState()==1)
        {
            holder.CONFIRMSTATE.setTextColor(android.graphics.Color.GREEN);
            holder.CONFIRMSTATE.setText("Orderd");

        }
        else
        {
            holder.CONFIRMSTATE.setTextColor(Color.RED);
            holder.CONFIRMSTATE.setText("Saved");

        }


        holder. show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create the bundle
                Bundle bundle = new Bundle();

                bundle.putInt("VOHNO", orderMasters.get(position).getVhfNo());

                Intent intent=new Intent(context, OrderReport.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderMasters.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder
    {   TextView VHFNO,CONFIRMSTATE,cusName,show;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            VHFNO=itemView.findViewById(R.id.vocherno);
                CONFIRMSTATE=itemView.findViewById(R.id.orderstate);
            cusName=itemView.findViewById(R.id.cusNO);
            show=itemView.findViewById(R.id.show);
        }
    }

        }
