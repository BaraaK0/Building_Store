package com.falcons.buildingstore.Adapters;

//import static com.falcons.buildingstore.Activities.HomeActivity.custmsDialog;
//import static com.falcons.buildingstore.Activities.HomeActivity.customerNameTV;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.R;

import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {

    private Context context;
    private List<CustomerInfo> customersList;

    public CustomersAdapter(Context context, List<CustomerInfo> customersList) {
        this.context = context;
        this.customersList = customersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.customer_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.customerName.setText(customersList.get(position).getCustomerName());

        holder.phoneNo.setText(customersList.get(position).getPhoneNo());

        holder.parentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("Customer_Selected", customersList.get(holder.getAdapterPosition()).getCustomerName());
//                customerNameTV.setText(customersList.get(holder.getAdapterPosition()).getCustomerName());
//
//                custmsDialog.dismiss();

            }
        });

    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView parentCard;
        TextView customerName, phoneNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentCard = itemView.findViewById(R.id.parentCard);
            customerName = itemView.findViewById(R.id.customerName);
            phoneNo = itemView.findViewById(R.id.phoneNo);

        }

    }
}
