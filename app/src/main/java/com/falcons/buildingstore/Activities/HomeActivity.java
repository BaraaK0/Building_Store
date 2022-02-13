package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.falcons.buildingstore.Adapters.CustomAdapter;
import com.falcons.buildingstore.Adapters.VoherItemAdapter;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Adapters.ItemAdapter;
import com.falcons.buildingstore.Database.Entities.TempOrder;
import com.falcons.buildingstore.Adapters.CustomersAdapter;
import com.falcons.buildingstore.Adapters.ItemsAdapter;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    //    GridView gridView;
//    ArrayList<String> categories = new ArrayList<>();
//    public static RecyclerView recyclerView_Items;
//    public static VoherItemAdapter voherItemAdapter;
//    public static ArrayList<Item> vocher_Items = new ArrayList<>();
//    Button temporderbtn;
//    Spinner Catg_SP;
//    public static ArrayAdapter<String> arrayAdapter;
//    public static TextView customerNameTV;
//    FloatingActionButton addCustomerBtn;
//    static List<CustomerInfo> customersList;
    public static List<Item> itemList_rv;
    //    public static List<String> customerNames;
//    public static Dialog custmsDialog;
    private String ipAddress, ipPort, coNo;

    public ItemsAdapter itemsAdapter;

    public static RecyclerView itemsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

//        temporderbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0; i < vocher_Items.size(); i++) {
//                    TempOrder tempOrder = new TempOrder();
//                    tempOrder.setItemNo(vocher_Items.get(i).getItemNCode());
//                    tempOrder.setItemName(vocher_Items.get(i).getItemNCode());
//                    tempOrder.setQty(vocher_Items.get(i).getQty());
//                    tempOrder.setArea(vocher_Items.get(i).getArea());
//                    tempOrder.setTax(vocher_Items.get(i).getTax());
//                    tempOrder.setCustomerId(vocher_Items.get(i).getCus_Id());
//                    tempOrder.setDiscount(vocher_Items.get(i).getDiscount());
//                    tempOrder.setPrice(vocher_Items.get(i).getPrice());
//                    tempOrder.setTotal(vocher_Items.get(i).getPrice());
//                    tempOrder.setConfStatus(1);
//
//                    tempOrder.setDate("");
//                    tempOrder.setTime("");
//                }
//            }
//        });
//        recyclerView_Items = findViewById(R.id.recyclerView_Items);
        ArrayList<Item> itemList = new ArrayList<>();

        Item item = new Item();
        item.setItem_Name("Item Number 1");
        item.setItemNCode("111111");
        item.setPrice(10);
        item.setUnit("10");
        item.setItemKind("granet");
        item.setQty(10);
        item.setTax("4543");
        item.setImagePath("https://www.almrsal.com/wp-content/uploads/2019/08/%D8%A3%D8%B4%D9%87%D8%B1-%D8%A3%D9%86%D9%88%D8%A7%D8%B9-%D8%A7%D9%84%D8%A3%D9%86%D9%85%D9%8A.jpg");
        itemList.add(item);

        Item item2 = new Item();
        item2.setItem_Name("Item Number 2");
        item2.setItemNCode("2222222222");
        item2.setPrice(10);
        item2.setUnit("1");
        item2.setTax("23");
        item2.setItemKind("das");
        item2.setQty(10);
        item2.setImagePath("https://occ-0-1722-1723.1.nflxso.net/dnm/api/v6/E8vDc_W8CLv7-yMQu8KMEC7Rrr8/AAAABeV0Af4XqVIi8qSUEeV_llbkH9B-TyiTGukOX7pSFxAuAyoc9q-e--ErSFvK4dLjE7tYDAr1L0PXAja28cDsLWwGdA_A.jpg?r=43c");


        Item item3 = new Item();
        item3.setItem_Name("Item Number 3");
        item3.setItemNCode("333333333");
        item3.setPrice(10);
        item3.setUnit("0");
        item3.setItemKind("sda");
        item3.setTax("231");
        item3.setQty(10);
        item3.setImagePath("https://www.almrsal.com/wp-content/uploads/2019/08/%D8%A3%D8%B4%D9%87%D8%B1-%D8%A3%D9%86%D9%88%D8%A7%D8%B9-%D8%A7%D9%84%D8%A3%D9%86%D9%85%D9%8A.jpg");


        Item item4 = new Item(), item5 = new Item(), item6 = new Item(), item7 = new Item(), item8 = new Item(), item9 = new Item(), item10 = new Item();

        item4.setItem_Name("Item Number 4");
        item4.setImagePath("https://cdn.mos.cms.futurecdn.net/eVyt9jnUrLBSvSwW6pScj9.jpg");
        item4.setItemNCode("44444444");
        item4.setPrice(10);
        item4.setUnit("0");
        item4.setItemKind("df");
        item4.setTax("45");
        item4.setQty(56);

        item5.setItem_Name("Item Number 5");
        item5.setImagePath("https://i.pinimg.com/originals/62/3a/a8/623aa8f9933ee9a286871bf6e0782538.jpg");
        item5.setItemNCode("5555555555");
        item5.setPrice(89);
        item5.setUnit("7");
        item5.setItemKind("65");
        item5.setTax("451");
        item5.setQty(15);

        item6.setItem_Name("Item Number 6");
        item6.setImagePath("https://www.almrsal.com/wp-content/uploads/2019/08/%D8%A3%D8%B4%D9%87%D8%B1-%D8%A3%D9%86%D9%88%D8%A7%D8%B9-%D8%A7%D9%84%D8%A3%D9%86%D9%85%D9%8A.jpg");
        item6.setItemNCode("6666666666");
        item6.setPrice(7898);
        item6.setUnit("56");
        item6.setItemKind("lksjd");
        item6.setTax("787");
        item6.setQty(45);

        item7.setItem_Name("Item Number 7");
        item7.setImagePath("https://pbs.twimg.com/profile_images/1265442011987017728/UyunmQzA_400x400.jpg");
        item7.setItemNCode("77777777");
        item7.setPrice(65);
        item7.setUnit("34");
        item7.setItemKind("fhfg");
        item7.setTax("898");
        item7.setQty(23);


        item8.setItem_Name("Item Number 8");
        item8.setImagePath("https://www.almrsal.com/wp-content/uploads/2019/08/%D8%A3%D8%B4%D9%87%D8%B1-%D8%A3%D9%86%D9%88%D8%A7%D8%B9-%D8%A7%D9%84%D8%A3%D9%86%D9%85%D9%8A.jpg");
        item8.setItemNCode("77777777");
        item8.setPrice(65);
        item8.setUnit("34");
        item8.setItemKind("fhfg");
        item8.setTax("898");
        item8.setQty(23);

        item9.setItem_Name("Item Number 9");
        item9.setImagePath("https://www.almrsal.com/wp-content/uploads/2019/08/%D8%A3%D8%B4%D9%87%D8%B1-%D8%A3%D9%86%D9%88%D8%A7%D8%B9-%D8%A7%D9%84%D8%A3%D9%86%D9%85%D9%8A.jpg");
        item9.setItemNCode("77777777");
        item9.setPrice(65);
        item9.setUnit("34");
        item9.setItemKind("fhfg");
        item9.setTax("898");
        item9.setQty(23);

        item10.setItem_Name("Item Number 10");
        item10.setImagePath("https://cdn.vox-cdn.com/thumbor/Z6PAPzGfqK5n4Q7oBnUk5aNOL6Q=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/22814568/jbareham_210827_ecl1072_summer_streaming_2021_anime.jpg");
        item10.setItemNCode("77777777");
        item10.setPrice(65);
        item10.setUnit("34");
        item10.setItemKind("fhfg");
        item10.setTax("898");
        item10.setQty(23);

        itemList.add(item2);
        itemList.add(item3);

        itemList.add(item4);
        itemList.add(item5);
        itemList.add(item6);
        itemList.add(item7);
        itemList.add(item8);
        itemList.add(item9);
        itemList.add(item10);

        itemsAdapter = new ItemsAdapter(itemList, HomeActivity.this);
        itemsRecycler.setAdapter(itemsAdapter);
        itemsRecycler.setSaveEnabled(false);

//        Log.e("itemList", itemList.size() + "");
//        ItemAdapter itemItemAdapter = new ItemAdapter(HomeActivity.this, itemList);
//        gridView.setAdapter(itemItemAdapter);


//        Item item4 = new Item();
//        item4.setItemNCode("FILTERS");
//        item4.setPrice(10);
//        item4.setUnit("10");
//        itemList.add(0, item4);
//        CustomAdapter customAdapter = new CustomAdapter(HomeActivity.this, itemList);
//
//        Catg_SP.setAdapter(customAdapter);
//        Catg_SP.setSelection(0);
//
//        gridView.setNumColumns(itemList.size());
//
//
//        for (int i = 0; i < 100; i++) {
//
//            customersList.add(new CustomerInfo("Customer" + i, "078" + i + (i + 1) + (i + 1)));
//
//        }
//        customersList.add(new CustomerInfo("Customer" + 5, "078" + 5 + (5 + 1) + (5 + 1)));
//
////        fillCustomerNamesAdapter(HomeActivity.this);
//        addCustomerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                custmsDialog = new Dialog(HomeActivity.this);
//                custmsDialog.setCancelable(false);
//                custmsDialog.setContentView(R.layout.customers_dialog);
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.copyFrom(custmsDialog.getWindow().getAttributes());
//                lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.15);
//                lp.height = (int) (getResources().getDisplayMetrics().heightPixels / 1.7);
//                custmsDialog.getWindow().setAttributes(lp);
//                custmsDialog.show();
//
//                ImageButton closeBtn = custmsDialog.findViewById(R.id.closeBtn);
//                EditText searchCustomers = custmsDialog.findViewById(R.id.searchCustomers);
//
//                RecyclerView customersRV = custmsDialog.findViewById(R.id.customersRV);
//
//                CustomersAdapter customersAdapter = new CustomersAdapter(HomeActivity.this, customersList);
//
//                customersRV.setAdapter(customersAdapter);
//
//                searchCustomers.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                        String searchedName = s.toString().trim().toLowerCase();
//                        List<CustomerInfo> tempCustomers = new ArrayList<>();
//
//                        for (int c = 0; c < customersList.size(); c++) {
//
//                            if (customersList.get(c).getCustomerName().trim().toLowerCase().contains(searchedName)) {
//                                tempCustomers.add(customersList.get(c));
//                            }
//
//                        }
//
//                        CustomersAdapter customersAdapter2 = new CustomersAdapter(HomeActivity.this, tempCustomers);
//
//                        customersRV.setAdapter(customersAdapter2);
//
//                    }
//                });
//
//                closeBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        custmsDialog.dismiss();
//                    }
//                });
//
//            }
//        });


    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

//    public static void FillrecyclerView_Items(Context context, ArrayList<Item> items) {
//        recyclerView_Items.setLayoutManager(new LinearLayoutManager(context));
//        voherItemAdapter = new VoherItemAdapter(items, context);
//
//        recyclerView_Items.setAdapter(voherItemAdapter);
//
//
//        // customerNames_sp.add(getString(R.string.customerName).toString());
//
//
//    }


    void init() {

//        customerNameTV = findViewById(R.id.customerNameTV);
//        addCustomerBtn = findViewById(R.id.addCustomerBtn);
//
//        customersList = new ArrayList<>();
//        customerNames = new ArrayList<>();
        itemsRecycler = findViewById(R.id.itemsRecycler);
        itemList_rv = new ArrayList<>();

//        gridView = findViewById(R.id.simpleGridView);
//        Catg_SP = findViewById(R.id.Catg_SP);
//        temporderbtn = findViewById(R.id.temporderbtn);


        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");
        Log.e("IP_PREF2", ipAddress + "");
        Log.e("PORT_PREF2", ipPort);
        Log.e("CONO_PREF2", coNo);

    }

//    public static void fillCustomerNamesAdapter(Context context) {
//        Log.e("customerNames_sp==", customerNames.size() + "");
//        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, customerNames);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
////        customersSpinner.setAdapter(arrayAdapter);
////        customersSpinner.setSelection(0);
//    }
}