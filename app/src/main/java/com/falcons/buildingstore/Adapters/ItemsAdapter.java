package com.falcons.buildingstore.Adapters;


import static com.falcons.buildingstore.Activities.HomeActivity.itemsRecycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    List<Item> itemsList;
    Context context;
    int mExpandedPosition = -1;
    int previousExpandedPosition = -1;

    public ItemsAdapter(List<Item> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int currPosition = holder.getAdapterPosition();

        final boolean isExpanded = currPosition == mExpandedPosition;
        holder.expandedLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded) {
            previousExpandedPosition = currPosition;
            holder.parentLayout.setVisibility(View.GONE);
        } else {
            holder.parentLayout.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : currPosition;
                TransitionManager.beginDelayedTransition(itemsRecycler);
//                notifyItemChanged(previousExpandedPosition);
//                notifyItemChanged(currPosition);
                notifyDataSetChanged();
                itemsRecycler.smoothScrollToPosition(currPosition);
            }
        });

        Picasso.get().load(itemsList.get(currPosition).getImagePath()).fit().centerCrop().into(holder.itemImage);

        holder.itemName.setText(itemsList.get(currPosition).getItemName());

        holder.expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mExpandedPosition = isExpanded ? -1 : currPosition;
                TransitionManager.beginDelayedTransition(itemsRecycler);
//                notifyItemChanged(previousExpandedPosition);
//                notifyItemChanged(currPosition);
                notifyDataSetChanged();
                itemsRecycler.smoothScrollToPosition(currPosition);

            }
        });

        /////////// Expanded /////////////

        Picasso.get().load(itemsList.get(currPosition).getImagePath()).fit().centerCrop().into(holder.itemImg2);

        holder.itemNameTV.setText(itemsList.get(currPosition).getItemName());
        holder.itemCodeTV.setText(itemsList.get(currPosition).getItemNCode());
        holder.itemKindTV.setText(itemsList.get(currPosition).getItemKind());
        holder.itemTaxTV.setText(itemsList.get(currPosition).getTax());
        holder.itemPriceTV.setText(String.valueOf(itemsList.get(currPosition).getPrice()));


        holder.unitRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Add item to cart?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout parentLayout, expandedLayout;

        CircleImageView itemImage, expandBtn;
        TextView itemName;

        ImageButton addToCartBtn;
        ImageView itemImg2;
        TextView itemNameTV, itemCodeTV, itemKindTV, itemTaxTV, itemPriceTV;
        EditText itemDiscEdt, itemQtyEdt, itemAreaEdt;
        RadioGroup unitRG;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            expandBtn = itemView.findViewById(R.id.expandBtn);

            //// Expanded ////
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn);
            itemImg2 = itemView.findViewById(R.id.itemImg2);
            itemNameTV = itemView.findViewById(R.id.itemNameTV);
            itemCodeTV = itemView.findViewById(R.id.itemCodeTV);
            itemKindTV = itemView.findViewById(R.id.itemKindTV);
            itemTaxTV = itemView.findViewById(R.id.itemTaxTV);
            itemPriceTV = itemView.findViewById(R.id.itemPriceTV);
            itemDiscEdt = itemView.findViewById(R.id.itemDiscEdt);
            itemQtyEdt = itemView.findViewById(R.id.itemQtyEdt);
            itemAreaEdt = itemView.findViewById(R.id.itemAreaEdt);
            unitRG = itemView.findViewById(R.id.unitRG);


        }
    }

}
