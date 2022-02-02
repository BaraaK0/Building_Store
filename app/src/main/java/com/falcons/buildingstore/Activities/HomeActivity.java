package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.falcons.buildingstore.Adapters.CustomAdapter;
import com.falcons.buildingstore.Adapters.VoherItemAdapter;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Adapters.ItemAdapter;
import com.falcons.buildingstore.Database.Entities.TempOrder;
import com.falcons.buildingstore.Adapters.CustomersAdapter;
import com.falcons.buildingstore.Adapters.ItemsAdapter;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<String>catgoris=new ArrayList<>();
    public static   RecyclerView recyclerView_Items;
    public static    VoherItemAdapter voherItemAdapter;
  public static   ArrayList<Item>vocher_Items=new ArrayList<>();
  Button temporderbtn;
    Spinner Catg_SP;
    public static ArrayAdapter<String> arrayAdapter;
    public static Spinner customersSpinner;
    FloatingActionButton addCustomerBtn;
    static List<CustomerInfo> customersList_sp;
    public static List<Item> itemList_rv;
    public static List<String> customerNames_sp;
    public static Dialog custmsDialog;


    public static    ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        temporderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<vocher_Items.size();i++) {
                    TempOrder tempOrder = new TempOrder();
                    tempOrder.setItemNo(vocher_Items.get(i).getItemNCode());
                    tempOrder.setItemName(vocher_Items.get(i).getItemNCode());
                    tempOrder.setQty(vocher_Items.get(i).getQty());
                    tempOrder.setArea(vocher_Items.get(i).getArea());
                    tempOrder.setTax(vocher_Items.get(i).getTax());
                    tempOrder.setCustomerId(vocher_Items.get(i).getCus_Id());
                    tempOrder.setDiscount(vocher_Items.get(i).getDiscount());
                    tempOrder.setPrice(vocher_Items.get(i).getPrice());
                    tempOrder.setTotal(vocher_Items.get(i).getPrice());
                    tempOrder.setConfStatus(1);

                    tempOrder.setDate("");
                    tempOrder.setTime("");
                }
            }
        });
        recyclerView_Items=findViewById(R.id.recyclerView_Items);
        ArrayList<Item> itemList=new ArrayList<>();

        Item item=new Item();
        item.setItemNCode("234755966");
        item.setPrice(10);
        item.setUnit("10");
        item.setItemKind("granet");
        item.setQty(10);
        itemList.add(item);
        Item item2=new Item();
        item2.setItemNCode("123456538");
        item2.setPrice(10);
        item2.setUnit("1");
        item2.setItemKind("granet");
        item2.setQty(10);

        Item item3=new Item();
        item3.setItemNCode("983578445");
        item3.setPrice(10);
        item3.setUnit("0");
        item3.setItemKind("granet");
        item3.setQty(10);

        itemList.add(item2);
        itemList.add(item3);


        Log.e("itemList",itemList.size()+"");
     ItemAdapter itemItemAdapter=new ItemAdapter( HomeActivity.this,itemList);
        gridView.setAdapter( itemItemAdapter);




        Item item4=new Item();
        item4.setItemNCode("FILTERS");
        item4.setPrice(10);
        item4.setUnit("10");
        itemList.add(0,  item4);
        CustomAdapter customAdapter=new  CustomAdapter ( HomeActivity.this,itemList);

        Catg_SP.setAdapter( customAdapter);
        Catg_SP.setSelection(0);

        gridView.setNumColumns(  itemList.size());


        for (int i = 0; i < 5; i++) {

            customerNames_sp.add("Customer" + i);
            customersList_sp.add(new CustomerInfo("Customer" + i, "078" + i + (i + 1) + (i + 1)));

        }
        customersList_sp.add(new CustomerInfo("Customer" + 5, "078" + 5 + (5 + 1) + (5 + 1)));


        fillcustomerNamesadapter(HomeActivity.this);
        addCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                custmsDialog = new Dialog(HomeActivity.this);
                custmsDialog.setCancelable(false);
                custmsDialog.setContentView(R.layout.customers_dialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(custmsDialog.getWindow().getAttributes());
                lp.width = (int)(getResources().getDisplayMetrics().widthPixels/1.15);
                lp.height = (int)(getResources().getDisplayMetrics().heightPixels/1.7);
                custmsDialog.getWindow().setAttributes(lp);
                custmsDialog.show();

                ImageButton closeBtn = custmsDialog.findViewById(R.id.closeBtn);

                RecyclerView customersRV = custmsDialog.findViewById(R.id.customersRV);

                CustomersAdapter customersAdapter = new CustomersAdapter(HomeActivity.this, customersList_sp);

                customersRV.setAdapter(customersAdapter);

                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        custmsDialog.dismiss();
                    }
                });

            }
        });











    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public static void  FillrecyclerView_Items(Context context, ArrayList<Item>items){
        recyclerView_Items  .setLayoutManager(new LinearLayoutManager(context));
       voherItemAdapter=new VoherItemAdapter(items,context);

        recyclerView_Items.setAdapter(voherItemAdapter);



       // customerNames_sp.add(getString(R.string.customerName).toString());




    }



    void init() {

        customersSpinner = findViewById(R.id.customersSpinner);
        addCustomerBtn = findViewById(R.id.addCustomerBtn);

        customersList_sp = new ArrayList<>();
        customerNames_sp = new ArrayList<>();
        itemList_rv = new ArrayList<>();

        gridView=findViewById(R.id.simpleGridView);
        Catg_SP=findViewById(R.id.Catg_SP);
        temporderbtn=findViewById(R.id.temporderbtn);
        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        String ipAddress = sharedPref.getString(IP_PREF, "");

        Log.e("IP_PREF2", ipAddress + "");
        Log.e("PORT_PREF2", sharedPref.getInt(PORT_PREF, 0) + "");
        Log.e("CONO_PREF2", sharedPref.getInt(CONO_PREF, 0) + "");

    }
public  static void fillcustomerNamesadapter(Context context){
        Log.e("customerNames_sp==",customerNames_sp.size()+"");
       arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, customerNames_sp);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        customersSpinner.setAdapter(arrayAdapter);
        customersSpinner.setSelection(0);
}
}