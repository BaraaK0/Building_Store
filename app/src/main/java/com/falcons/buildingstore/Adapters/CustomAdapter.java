package com.falcons.buildingstore.Adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;

    private List<Item> list;


    public CustomAdapter(@NonNull Context context, List<Item> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.catgspinnerrow,viewGroup, false);



            TextView  name = convertView.findViewById(R.id. name);
            TextView  arrow = convertView.findViewById(R.id. arrow);
            TextView  VECTOR = convertView.findViewById(R.id. VECTOR);
            if(position==0)name.setTextColor(Color.WHITE);
            name.setTextSize(18);
            if(position==0)
            {   arrow.setVisibility(View.VISIBLE);
                VECTOR.setVisibility(View.VISIBLE);}
         else{  arrow.setVisibility(View.INVISIBLE);
                VECTOR.setVisibility(View.INVISIBLE);
         }



            name.setText(list.get(position).getItemNCode());


        }
        return convertView;
}}