package com.falcons.buildingstore.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Spinner;

import com.falcons.buildingstore.Adapters.CustomAdapter;
import com.falcons.buildingstore.Adapters.VoherItemAdapter;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Adapters.ItemAdapter;
import com.falcons.buildingstore.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<String>catgoris=new ArrayList<>();
    public static   RecyclerView recyclerView_Items;
    public static    VoherItemAdapter voherItemAdapter;
  public static   ArrayList<Item>vocher_Items=new ArrayList<>();
    Spinner Catg_SP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView=findViewById(R.id.simpleGridView);
        Catg_SP=findViewById(R.id.Catg_SP);
        recyclerView_Items=findViewById(R.id.recyclerView_Items);
        ArrayList<Item> itemList=new ArrayList<>();

        Item item=new Item();
        item.setItemNCode("234755966");
        item.setPrice("10");
        item.setUnit("10");
        item.setItemKind("granet");
        item.setQty(10);
        itemList.add(item);
        Item item2=new Item();
        item2.setItemNCode("123456538");
        item2.setPrice("10");
        item2.setUnit("1");
        item2.setItemKind("granet");
        item2.setQty(10);

        Item item3=new Item();
        item3.setItemNCode("983578445");
        item3.setPrice("10");
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
        item4.setPrice("10");
        item4.setUnit("10");
        itemList.add(0,  item4);
        CustomAdapter customAdapter=new  CustomAdapter ( HomeActivity.this,itemList);

        Catg_SP.setAdapter( customAdapter);
        Catg_SP.setSelection(0);

        gridView.setNumColumns(  itemList.size());


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
    }
}