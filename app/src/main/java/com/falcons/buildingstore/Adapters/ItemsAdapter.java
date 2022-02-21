package com.falcons.buildingstore.Adapters;


import static com.falcons.buildingstore.Activities.HomeActivity.itemsRecycler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.falcons.buildingstore.Activities.HomeActivity;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    List<Item> itemsList;
    Context context;
    int mExpandedPosition = -1;
    int previousExpandedPosition = -1;
    int index = 0;
    AppDatabase appDatabase;

    public ItemsAdapter(List<Item> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
        appDatabase = AppDatabase.getInstanceDatabase(context);
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


        UserLogs userLogs = appDatabase.userLogsDao().getLastuserLogin();

        int userPer = appDatabase.usersDao().getuserPer(userLogs.getUserName());
        if (userPer == 0)
            holder.Dis_Layout.setVisibility(View.GONE);
        else
            holder.Dis_Layout.setVisibility(View.VISIBLE);


        if (!itemsList.get(currPosition).getImagePath().equals(""))
            Picasso.get().load(itemsList.get(currPosition).getImagePath()).fit().centerCrop().into(holder.itemImage);

        holder.itemName.setText(itemsList.get(currPosition).getItemName());


        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL)
            holder.expandBtn.setRotationY(180);

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

        if (!itemsList.get(currPosition).getImagePath().equals(""))
            Picasso.get().load(itemsList.get(currPosition).getImagePath()).fit().centerCrop().into(holder.itemImg2);

        holder.itemNameTV.setText(itemsList.get(currPosition).getItemName());
        holder.itemCodeTV.setText(itemsList.get(currPosition).getItemNCode());
        holder.itemKindTV.setText(itemsList.get(currPosition).getItemKind());
        holder.itemTaxTV.setText(itemsList.get(currPosition).getTax());
        holder.itemQtyEdt.setText(itemsList.get(currPosition).getQty() + "");
        holder.itemDiscEdt.setText(itemsList.get(currPosition).getDiscount() + "");
        holder.itemPriceTV.setText(String.valueOf(itemsList.get(currPosition).getPrice()));
        holder.itemaviqtyTV.setText(String.valueOf(itemsList.get(currPosition).getAviqty()));
        holder.itemAreaEdt.setText(itemsList.get(currPosition).getArea());
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
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                //  openSalesDialog(currPosition);
                                addQTYandDis(currPosition, holder);
                                dialog.dismiss();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;

                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.add_item_to))
                        .setTitle(itemsList.get(currPosition).getItemName())
                        .setPositiveButton(context.getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(context.getString(R.string.no), dialogClickListener)
                        .show();


            }
        });
        holder.itemAreaEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    appDatabase.itemsDao().UpdateItemAria(holder.itemAreaEdt.getText().toString(), itemsList.get(currPosition).getArea());
                }
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
        ConstraintLayout Dis_Layout;
        ImageButton addToCartBtn;
        ImageView itemImg2;
        TextView itemNameTV, itemCodeTV, itemKindTV, itemTaxTV, itemPriceTV, itemaviqtyTV;
        EditText itemDiscEdt, itemQtyEdt, itemAreaEdt;
        RadioGroup unitRG;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Dis_Layout = itemView.findViewById(R.id.Dis_Layout);
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
            itemaviqtyTV = itemView.findViewById(R.id.itemaviqtyTV);

        }
    }

    public void openSalesDailog(int position) {
        {

            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.salesdailog);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.gravity = Gravity.CENTER;
            dialog.getWindow().setAttributes(lp);
            dialog.show();
            TextView save = dialog.findViewById(R.id.save);
            TextView cancelBtn = dialog.findViewById(R.id.cancelBtn);
            TextView itemname = dialog.findViewById(R.id.itemname);
            TextView itemno = dialog.findViewById(R.id.itemno);
            TextView aviqty = dialog.findViewById(R.id.aviqty);
            aviqty.setText(itemsList.get(position).getAviqty() + "");
            itemname.setText(itemsList.get(position).getItemName());
            itemno.setText(itemsList.get(position).getItemNCode());
            EditText ITEMqty = dialog.findViewById(R.id.ITEMqty);
            EditText ITEMdiscount = dialog.findViewById(R.id.ITEMdiscount);
            if (IsExistsInList(itemsList.get(position).getItemNCode())) {

                ITEMdiscount.setText(itemsList.get(index).getDiscount() + "");
                ITEMqty.setText(itemsList.get(index).getQty() + "");
            }

            if (!ITEMqty.getText().toString().equals("")
                    && !ITEMdiscount.getText().toString().equals(""))
                save.setEnabled(false);
            else save.setEnabled(true);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ITEMdiscount.getText().toString().equals("") &&
                            !ITEMqty.getText().toString().equals("")) {
                        itemsList.get(position).setDiscount(Double.parseDouble(ITEMdiscount.getText().toString()));
                        itemsList.get(position).setQty(Double.parseDouble(ITEMqty.getText().toString()));

                        Log.e("vocher_Items=", HomeActivity.vocher_Items.size() + "");
                        if (HomeActivity.vocher_Items.size() == 0) {
                            HomeActivity.vocher_Items.add(itemsList.get(position));
                            HomeActivity.item_count++;
                            HomeActivity.itemcount.setText(HomeActivity.item_count);
                            //  HomeActivity.  FillrecyclerView_Items(context,HomeActivity. vocher_Items);

                        } else {
                            if (!IsExistsInList(itemsList.get(position).getItemNCode())) // new item
                            {
                                Log.e("case2vocher_Items=", HomeActivity.vocher_Items.size() + "");
                                HomeActivity.vocher_Items.add(itemsList.get(position));
                                HomeActivity.vocher_Items.add(itemsList.get(position));
                                HomeActivity.item_count++;
                                HomeActivity.itemcount.setText(HomeActivity.item_count);

                                //      HomeActivity.voherItemAdapter.notifyItemInserted(HomeActivity.vocher_Items.size() - 1);
                            } else // item already added
                            {

                                Log.e("case3vocher_Items=", HomeActivity.vocher_Items.size() + "");

                                itemsList.get(index).setDiscount(Double.parseDouble(ITEMdiscount.getText().toString()));
                                itemsList.get(index).setQty(Double.parseDouble(ITEMqty.getText().toString()));
                                //   HomeActivity.voherItemAdapter.notifyItemChanged(index);


                            }

                        }
                        dialog.dismiss();

                    }
                }
            });

        }
    }

    public void addQTYandDis(int position, ViewHolder holder) {
        {


            if (!holder.itemQtyEdt.getText().toString().equals("")) {

                itemsList.get(position).setDiscount(Double.parseDouble(holder.itemDiscEdt.getText().toString()));
                itemsList.get(position).setQty(Double.parseDouble(holder.itemQtyEdt.getText().toString()));

                Log.e("vocher_Items=", HomeActivity.vocher_Items.size() + "");
                if (HomeActivity.vocher_Items.size() == 0) {
                    HomeActivity.vocher_Items.add(itemsList.get(position));
                    //  HomeActivity.  FillrecyclerView_Items(context,HomeActivity. vocher_Items);

                } else {
                    if (!IsExistsInList(itemsList.get(position).getItemNCode())) // new item
                    {
                        Log.e("case2vocher_Items=", HomeActivity.vocher_Items.size() + "");
                        HomeActivity.vocher_Items.add(itemsList.get(position));
                        //      HomeActivity.voherItemAdapter.notifyItemInserted(HomeActivity.vocher_Items.size() - 1);
                    } else // item already added
                    {

                        Log.e("case3vocher_Items=", HomeActivity.vocher_Items.size() + "");

                        itemsList.get(index).setDiscount(Double.parseDouble(holder.itemDiscEdt.getText().toString()));
                        itemsList.get(index).setQty(Double.parseDouble(holder.itemQtyEdt.getText().toString()));
                        //   HomeActivity.voherItemAdapter.notifyItemChanged(index);


                    }

                }

            } else
                holder.itemQtyEdt.setError(context.getResources().getString(R.string.Empty));


        }
    }

    boolean IsExistsInList(String ItemNCode) {
        index = 0;
        for (int i = 0; i < HomeActivity.vocher_Items.size(); i++)
            if (HomeActivity.vocher_Items.get(i).getItemNCode().equals(ItemNCode)) {
                index = i;
                return true;

            }

        return false;
    }
}
