package com.falcons.buildingstore.Adapters;


import static android.content.Context.MODE_PRIVATE;
import static com.falcons.buildingstore.Activities.HomeActivity.badge;
import static com.falcons.buildingstore.Activities.HomeActivity.itemsRecycler;
import static com.falcons.buildingstore.Activities.HomeActivity.vocher_Items;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.falcons.buildingstore.Activities.HomeActivity;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final List<Item> itemsList;
    private final Context context;
    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;
    private int index = 0;
    private final AppDatabase appDatabase;

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

        holder.setIsRecyclable(false);

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

        if (holder.parentLayout.getVisibility() == View.VISIBLE) {

            holder.itemView.setOnClickListener(v -> {
                mExpandedPosition = isExpanded ? -1 : currPosition;
                TransitionManager.beginDelayedTransition(itemsRecycler);
//                notifyItemChanged(previousExpandedPosition);
//                notifyItemChanged(currPosition);
                notifyDataSetChanged();
                itemsRecycler.smoothScrollToPosition(currPosition);
            });


            if (!itemsList.get(currPosition).getImagePath().trim().equals("")) {

                SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
                String ipAddress = sharedPref.getString(IP_PREF, "");
                String ipPort = sharedPref.getString(PORT_PREF, "");

                /* TODO UNCOMMENT THIS */
//            String imagePath = "http://" + ipAddress + ":" + ipPort + "/Falcons/IMG/" + itemsList.get(currPosition).getImagePath();
                String imagePath = "http://10.0.0.22:8086/Falcons/IMG/" + itemsList.get(currPosition).getImagePath();

                Picasso.get().load(imagePath).into(holder.itemImage);

            } else {

//            holder.itemImage.setBackgroundColor(context.getResources().getColor(R.color.white));

                holder.itemImage.setImageDrawable(
                        ResourcesCompat.getDrawable(context.getResources(), R.drawable.image_not_available, context.getTheme()));

            }

            holder.itemName.setText(itemsList.get(currPosition).getItemName());


            if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL)
                holder.expandBtn.setRotationY(180);

            holder.expandBtn.setOnClickListener(v -> {

                mExpandedPosition = isExpanded ? -1 : currPosition;
                TransitionManager.beginDelayedTransition(itemsRecycler);
//                notifyItemChanged(previousExpandedPosition);
//                notifyItemChanged(currPosition);
                notifyDataSetChanged();
                itemsRecycler.smoothScrollToPosition(currPosition);

            });

        }


        /////////// Expanded /////////////

        if (holder.expandedLayout.getVisibility() == View.VISIBLE) {

            UserLogs userLogs = appDatabase.userLogsDao().getLastuserLogin();

            try {
                int userPer = appDatabase.usersDao().getuserPer(userLogs.getUserName());
                if (userPer == 0)
                    holder.Dis_Layout.setVisibility(View.GONE);
                else
                    holder.Dis_Layout.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {

            }

            holder.itemAreaEdt.setImeOptions(EditorInfo.IME_ACTION_DONE);

            holder.itemAreaEdt.setOnKeyListener((v, keyCode, event) -> {

                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        keyCode == EditorInfo.IME_ACTION_DONE ||
                        keyCode == EditorInfo.IME_ACTION_NEXT ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    holder.addToCartBtn.requestFocus();

                    return true;

                }

                return false;
            });

            if (!itemsList.get(currPosition).getImagePath().trim().equals("")) {

                SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
                String ipAddress = sharedPref.getString(IP_PREF, "");
                String ipPort = sharedPref.getString(PORT_PREF, "");

                /* TODO UNCOMMENT THIS */
//            String imagePath = "http://" + ipAddress + ":" + ipPort + "/Falcons/IMG/" + itemsList.get(currPosition).getImagePath();
                String imagePath = "http://10.0.0.22:8086/Falcons/IMG/" + itemsList.get(currPosition).getImagePath();

                Picasso.get().load(imagePath).into(holder.itemImg2);

            } else {

//            holder.itemImg2.setBackgroundColor(context.getResources().getColor(R.color.white));

                holder.itemImg2.setImageDrawable(
                        ResourcesCompat.getDrawable(context.getResources(), R.drawable.image_not_available, context.getTheme()));

            }

            holder.itemNameTV.setText(itemsList.get(currPosition).getItemName());
            holder.itemCodeTV.setText(itemsList.get(currPosition).getItemOCode());
            holder.itemKindTV.setText(itemsList.get(currPosition).getItemKind());
            holder.itemTaxTV.setText(itemsList.get(currPosition).getTax() + "");
            holder.itemQtyEdt.setText(itemsList.get(currPosition).getQty() + "");
            if(itemsList.get(currPosition).getDiscount()==0)
                holder.itemDiscEdt.setText("0");
                else
            holder.itemDiscEdt.setText(itemsList.get(currPosition).getDiscount() + "");
            holder.itemPriceTV.setText(String.valueOf(itemsList.get(currPosition).getPrice()));
            holder.itemaviqtyTV.setText(String.valueOf(itemsList.get(currPosition).getAviqty()));
            holder.itemAreaEdt.setText(itemsList.get(currPosition).getArea());

            List<String> itemUnits = new ArrayList<>();

            itemUnits.add("One Unit");
            itemUnits.addAll(appDatabase.itemUnitsDao().getItemUnits(itemsList.get(currPosition).getItemOCode()));

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, itemUnits);

            holder.unitSpinner.setAdapter(arrayAdapter);
            holder.unitSpinner.setSelection(0);

            holder.addToCartBtn.setOnClickListener(v -> {


                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            //  openSalesDialog(currPosition);
                            addQTYandDis(currPosition, holder);
                            dialog.dismiss();
                            break;


                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            dialog.dismiss();
                            break;

                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.add_item_to))
                        .setTitle(itemsList.get(currPosition).getItemName())
                        .setPositiveButton(context.getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(context.getString(R.string.no), dialogClickListener)
                        .show();


            });

        }

//        holder.itemAreaEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (!editable.toString().equals("")) {
//                    appDatabase.itemsDao().UpdateItemAria(holder.itemAreaEdt.getText().toString(), itemsList.get(currPosition).getArea());
//                }
//            }
//        });

//        holder.itemAreaEdt.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        holder.itemAreaEdt.setOnKeyListener((v, keyCode, event) -> {
//
//            if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
//                    keyCode == EditorInfo.IME_ACTION_DONE ||
//                    keyCode == EditorInfo.IME_ACTION_NEXT ||
//                    event.getAction() == KeyEvent.ACTION_DOWN &&
//                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//
//                holder.addToCartBtn.requestFocus();
//
//                return true;
//
//            }
//
//            return false;
//        });

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
        Button addToCartBtn;
        ImageView itemImg2;
        TextView itemNameTV, itemCodeTV, itemKindTV, itemTaxTV, itemPriceTV, itemaviqtyTV;
        EditText itemDiscEdt, itemQtyEdt, itemAreaEdt;
        RadioGroup unitRG;
        Spinner unitSpinner;

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
//            unitRG = itemView.findViewById(R.id.unitRG);
            unitSpinner = itemView.findViewById(R.id.unitSpinner);
            itemaviqtyTV = itemView.findViewById(R.id.itemaviqtyTV);

        }
    }


    private void addQTYandDis(int position, ViewHolder holder) {
        {

            if (!holder.itemDiscEdt.getText().toString().equals("") &&
                    !holder.itemQtyEdt.getText().toString().equals("") &&
                    Double.parseDouble(holder.itemQtyEdt.getText().toString()) != 0) {


                Log.e("vocher_Items=", HomeActivity.vocher_Items.size() + "");
                if (HomeActivity.vocher_Items.size() == 0) {

                    itemsList.get(position).setDiscount(Double.parseDouble(holder.itemDiscEdt.getText().toString()) / 100);
                    itemsList.get(position).setQty(Double.parseDouble(holder.itemQtyEdt.getText().toString()));
                    itemsList.get(position).setArea(holder.itemAreaEdt.getText().toString());
                    itemsList.get(position).setUnit(holder.unitSpinner.getSelectedItem().toString());

                    double num_items = 1;
                    String selectedUnit = holder.unitSpinner.getSelectedItem().toString()+"";
                    if (holder.unitSpinner.getSelectedItemId() != 0) {

                        num_items = appDatabase.itemUnitsDao().getConvRate(holder.itemCodeTV.getText().toString(), selectedUnit);

                    }

                    itemsList.get(position).setAmount(itemsList.get(position).getPrice() * itemsList.get(position).getQty() * num_items);
                    vocher_Items.add(itemsList.get(position));
                    badge.setNumber(vocher_Items.size());

                    //  HomeActivity.  FillrecyclerView_Items(context,HomeActivity. vocher_Items);


                } else {
                    if (!IsExistsInList(itemsList.get(position).getItemOCode())) // new item
                    {

                        itemsList.get(position).setDiscount(Double.parseDouble(holder.itemDiscEdt.getText().toString()) / 100);
                        itemsList.get(position).setQty(Double.parseDouble(holder.itemQtyEdt.getText().toString()));
                        itemsList.get(position).setArea(holder.itemAreaEdt.getText().toString());

                        itemsList.get(position).setUnit(holder.unitSpinner.getSelectedItem().toString());

                        double num_items = 1;
                        String selectedUnit = holder.unitSpinner.getSelectedItem().toString()+"";
                        if (holder.unitSpinner.getSelectedItemId() != 0) {

                            num_items = appDatabase.itemUnitsDao().getConvRate(holder.itemCodeTV.getText().toString(), selectedUnit);

                        }

                        itemsList.get(position).setAmount(itemsList.get(position).getPrice() * itemsList.get(position).getQty() * num_items);

                        Log.e("case2vocher_Items=", vocher_Items.size() + "");
                        vocher_Items.add(itemsList.get(position));
                        badge.setNumber(vocher_Items.size());
                        //      HomeActivity.voherItemAdapter.notifyItemInserted(HomeActivity.vocher_Items.size() - 1);
                    } else // item already added
                    {

                        Log.e("case3vocher_Items=", HomeActivity.vocher_Items.size() + "");
                        HomeActivity.vocher_Items.remove(index);
                        itemsList.get(position).setDiscount(Double.parseDouble(holder.itemDiscEdt.getText().toString()) / 100);
                        itemsList.get(position).setQty(Double.parseDouble(holder.itemQtyEdt.getText().toString()));
                        itemsList.get(position).setArea(holder.itemAreaEdt.getText().toString());

                        itemsList.get(position).setUnit(holder.unitSpinner.getSelectedItem().toString());

                        double num_items = 1;
                        String selectedUnit = holder.unitSpinner.getSelectedItem().toString()+"";
                        if (holder.unitSpinner.getSelectedItemId() != 0) {

                            num_items = appDatabase.itemUnitsDao().getConvRate(holder.itemCodeTV.getText().toString(), selectedUnit);

                        }

                        itemsList.get(position).setAmount(itemsList.get(position).getPrice() * itemsList.get(position).getQty() * num_items);

                        HomeActivity.vocher_Items.add(itemsList.get(position));
                        badge.setNumber(vocher_Items.size());
                        Log.e("case3vocher_Items=", vocher_Items.size() + "");

                        //   HomeActivity.voherItemAdapter.notifyItemChanged(index);


                    }

                }

            } else
                holder.itemQtyEdt.setError(context.getResources().getString(R.string.Empty));


        }
    }


    boolean IsExistsInList(String ItemOCode) {
        index = 0;
        boolean exists = false;
        for (int i = 0; i < vocher_Items.size(); i++)
            if (vocher_Items.get(i).getItemOCode().equals(ItemOCode)) {
                index = i;
                exists = true;
                break;

            }

        return exists;
    }
}
