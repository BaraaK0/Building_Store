package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.falcons.buildingstore.DataManipulation.ExportData;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Adapters.ItemsAdapter;
import com.falcons.buildingstore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    //    GridView gridView;
//    ArrayList<String> categories = new ArrayList<>();
//    public static RecyclerView recyclerView_Items;
//    public static VoherItemAdapter voherItemAdapter;
   public static ArrayList<Item> vocher_Items = new ArrayList<>();
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

    BottomNavigationView bottom_navigation;
    ExportData exportData;
    public static  TextView itemcount;
    public static  int   item_count=0;
    public static  ArrayList<Item> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

    itemList = new ArrayList<>();

        Item item = new Item();
        item.setItem_Name("Item Number 1");
        item.setItemNCode("111111");
        item.setPrice(10);
        item.setUnit("10");
        item.setItemKind("granet");
        item.setAviqty(10);
        item.setTax("4543");
        item.setImagePath("https://img.freepik.com/free-photo/modern-office-building-low-angle-view-skyscrapers-city-singapore-modern-office-building-low-angle-view-skyscrapers-city-singapore_231208-1463.jpg?size=626&ext=jpg");
        itemList.add(item);

        Item item2 = new Item();
        item2.setItem_Name("Item Number 2");
        item2.setItemNCode("2222222222");
        item2.setPrice(10);
        item2.setUnit("1");
        item2.setTax("23");
        item2.setItemKind("das");
        item2.setAviqty(10);
        item2.setImagePath("https://i.pinimg.com/originals/d2/5b/75/d25b7588872867460b4628c508ab1eab.jpg");


        Item item3 = new Item();
        item3.setItem_Name("Item Number 3");
        item3.setItemNCode("333333333");
        item3.setPrice(10);
        item3.setUnit("0");
        item3.setItemKind("sda");
        item3.setTax("231");
        item3.setAviqty(10);
        item3.setImagePath("https://i.pinimg.com/originals/1d/37/a7/1d37a7adf8302ac094de5b515159fc72.jpg");


        Item item4 = new Item(), item5 = new Item(), item6 = new Item(), item7 = new Item(), item8 = new Item(), item9 = new Item(), item10 = new Item();

        item4.setItem_Name("Item Number 4");
        item4.setImagePath("https://images.adsttc.com/media/images/5a03/7af5/b22e/38c7/1f00/00d2/large_jpg/Hufton___Crow.jpg?1510177522");
        item4.setItemNCode("44444444");
        item4.setPrice(10);
        item4.setUnit("0");
        item4.setItemKind("df");
        item4.setTax("45");
        item4.setAviqty(56);

        item5.setItem_Name("Item Number 5");
        item5.setImagePath("https://i.pinimg.com/originals/62/3a/a8/623aa8f9933ee9a286871bf6e0782538.jpg");
        item5.setItemNCode("5555555555");
        item5.setPrice(89);
        item5.setUnit("7");
        item5.setItemKind("65");
        item5.setTax("451");
        item5.setAviqty(15);

        item6.setItem_Name("Item Number 6");
        item6.setImagePath("https://www.almrsal.com/wp-content/uploads/2019/08/%D8%A3%D8%B4%D9%87%D8%B1-%D8%A3%D9%86%D9%88%D8%A7%D8%B9-%D8%A7%D9%84%D8%A3%D9%86%D9%85%D9%8A.jpg");
        item6.setItemNCode("6666666666");
        item6.setPrice(7898);
        item6.setUnit("56");
        item6.setItemKind("lksjd");
        item6.setTax("787");
        item6.setAviqty(45);

        item7.setItem_Name("Item Number 7");
        item7.setImagePath("https://pbs.twimg.com/profile_images/1265442011987017728/UyunmQzA_400x400.jpg");
        item7.setItemNCode("77777777");
        item7.setPrice(65);
        item7.setUnit("34");
        item7.setItemKind("fhfg");
        item7.setTax("898");
        item7.setAviqty(23);


        item8.setItem_Name("Item Number 8");
        item8.setImagePath("https://www.almrsal.com/wp-content/uploads/2019/08/%D8%A3%D8%B4%D9%87%D8%B1-%D8%A3%D9%86%D9%88%D8%A7%D8%B9-%D8%A7%D9%84%D8%A3%D9%86%D9%85%D9%8A.jpg");
        item8.setItemNCode("77777777");
        item8.setPrice(65);
        item8.setUnit("34");
        item8.setItemKind("fhfg");
        item8.setTax("898");
        item8.setAviqty(23);

        item9.setItem_Name("Item Number 9");
        item9.setImagePath("https://www.almrsal.com/wp-content/uploads/2019/08/%D8%A3%D8%B4%D9%87%D8%B1-%D8%A3%D9%86%D9%88%D8%A7%D8%B9-%D8%A7%D9%84%D8%A3%D9%86%D9%85%D9%8A.jpg");
        item9.setItemNCode("77777777");
        item9.setPrice(65);
        item9.setUnit("34");
        item9.setItemKind("fhfg");
        item9.setTax("898");
        item9.setAviqty(23);

        item10.setItem_Name("Item Number 10");
        item10.setImagePath("https://cdn.vox-cdn.com/thumbor/Z6PAPzGfqK5n4Q7oBnUk5aNOL6Q=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/22814568/jbareham_210827_ecl1072_summer_streaming_2021_anime.jpg");
        item10.setItemNCode("77777777");
        item10.setPrice(65);
        item10.setUnit("34");
        item10.setItemKind("fhfg");
        item10.setTax("898");
        item10.setAviqty(23);

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

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.cartPage:
                                Intent intent=new Intent(HomeActivity.this,BasketActivity.class);
                                startActivity(intent);

                                break;
                            case R.id.  exportdata:
                                exportData . exportSalesVoucherM();
                                break;
                            case R.id. reportPage:
                                Intent intent1=new Intent(HomeActivity.this,ShowPreviousOrder.class);
                                startActivity(intent1);
                                break;
                            case R.id. addPage:

                                final Dialog dialog = new Dialog(HomeActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(true);
                                dialog.setContentView(R.layout.adddailog);
                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.gravity = Gravity.CENTER;
                                dialog.getWindow().setAttributes(lp);
                                dialog.show();

                              dialog.findViewById(R.id.addCustomer).setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      Intent intent2=new Intent(HomeActivity.this,AddNewCustomer.class);
                                      startActivity(intent2);
                                      dialog.dismiss();
                                  }
                              });
                                dialog.findViewById(R.id.adduser).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent2=new Intent(HomeActivity.this,AddNewUser.class);
                                        startActivity(intent2);
                                        dialog.dismiss();
                                    }
                                });

                                break;
                        }
                        return false;
                    }
                });
    }



    void init() {
        exportData=new ExportData(HomeActivity.this);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        itemsRecycler = findViewById(R.id.itemsRecycler);
        itemList_rv = new ArrayList<>();
        itemcount= findViewById(R.id.itemcount);
        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");
        Log.e("IP_PREF2", ipAddress + "");
        Log.e("PORT_PREF2", ipPort);
        Log.e("CONO_PREF2", coNo);

    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        int id = item.getItemId();
//        Log.e("id", "onNavigationItemSelected " + id);
//        switch (id) {
//
//            case R.id.cartPage:
//                Intent intent=new Intent(HomeActivity.this,BasketActivity.class);
//                startActivity(intent);
//            break;
//
//        }
//
//
//        return true;
//
//    }


}