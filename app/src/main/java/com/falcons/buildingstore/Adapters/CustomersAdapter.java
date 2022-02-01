package com.falcons.buildingstore.Adapters;

import static com.falcons.buildingstore.Activities.HomeActivity.custmsDialog;
import static com.falcons.buildingstore.Activities.HomeActivity.customerNames_sp;
import static com.falcons.buildingstore.Activities.HomeActivity.customersSpinner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

                if (!customerNames_sp.contains(customersList.get(holder.getAdapterPosition()).getCustomerName())) {

                    Log.e("Customer", "Added");
                    customerNames_sp.add(customersList.get(holder.getAdapterPosition()).getCustomerName());
                    customersSpinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, customerNames_sp));
                    customersSpinner.setSelection(customerNames_sp.size() - 1);

                } else {

                    Log.e("Customer", "Already Exist");
                    customersSpinner.setSelection(customerNames_sp.indexOf(customersList.get(holder.getAdapterPosition()).getCustomerName()));

                }

                custmsDialog.dismiss();

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
