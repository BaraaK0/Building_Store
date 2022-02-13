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
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

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


    }



    void init() {

        bottom_navigation = findViewById(R.id.bottom_navigation);
        itemsRecycler = findViewById(R.id.itemsRecycler);
        itemList_rv = new ArrayList<>();

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");
        Log.e("IP_PREF2", ipAddress + "");
        Log.e("PORT_PREF2", ipPort);
        Log.e("CONO_PREF2", coNo);

    }


}