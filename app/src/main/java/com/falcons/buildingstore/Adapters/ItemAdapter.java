package com.falcons.buildingstore.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.falcons.buildingstore.Activities.HomeActivity;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;

import java.util.List;

public  class ItemAdapter{
//    private List<Item> list;
//    Context context;
//  int  index=0;
//
//    public ItemAdapter(@NonNull Context context, List<Item> list){
//
//        this.list = list;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return  list.size(); //returns total of items in the list
//    }
//    @Override
//    public Object getItem(int i) {
//        return list.get(i);
//    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).
//                    inflate(R.layout.itemrow, parent, false);
//
//            TextView itemNCode = convertView.findViewById(R.id.itemNCode);
//            TextView tax = convertView.findViewById(R.id.tax);
//            TextView price = convertView.findViewById(R.id.price);
////            TextView unit = convertView.findViewById(R.id.unit);
//            TextView itemKind = convertView.findViewById(R.id.itemKind);
//            TextView area = convertView.findViewById(R.id.area);
//            TextView qty = convertView.findViewById(R.id.qty);
//            TextView salesDailog =  convertView.findViewById(R.id.salesDailog);
//            RadioButton RB_box=  convertView.findViewById(R.id.RB_box);
//            RadioButton RB_Mt=  convertView.findViewById(R.id.RB_Mt);
//            salesDailog.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    final Dialog dialog = new Dialog(context);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
//                    dialog.setContentView(R.layout.salesdailog);
//                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                    lp.copyFrom(dialog.getWindow().getAttributes());
//                    lp.gravity = Gravity.CENTER;
//                    dialog.getWindow().setAttributes(lp);
//                    dialog.show();
//                    TextView save=dialog.findViewById(R.id.save);
//                    TextView cancelBtn=dialog.findViewById(R.id.cancelBtn);
//
//                    EditText ITEMqty=dialog.findViewById(R.id.ITEMqty);
//                    EditText ITEMdiscount=dialog.findViewById(R.id.ITEMdiscount);
//                    if(IsExistsInList( list.get(position).getItemNCode()))
//                    {
//
//                        ITEMdiscount.setText(list.get(index).getDiscount()+"");
//                    ITEMqty.setText(list.get(index).getQty()+"");
//                    }
//
//                  if( ! ITEMqty.getText().toString().equals("")
//                && !ITEMdiscount.getText().toString().equals(""))
//                      save.setEnabled(false);
//                  else save.setEnabled(true);
//
//                    cancelBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog.dismiss();
//                        }
//                    });
//                    save.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
////                            Vocher vocher=new Vocher();
////                            vocher.setItemCode(list.get(position).getItemNCode());
////                            vocher.setCustomerId("1");
////                            vocher.setQty(Double.parseDouble(ITEMqty.getText().toString()));
////                            vocher.setDiscount(Double.parseDouble(ITEMdiscount.getText().toString()));
////
//                            list.get(position).setDiscount(Double.parseDouble(ITEMdiscount.getText().toString()));
//                            list.get(position).setQty(Double.parseDouble(ITEMqty.getText().toString()));
//
////                           Log.e("vocher_Items=",  HomeActivity. vocher_Items.size()+"");
////                        if(    HomeActivity. vocher_Items.size()==0)
////
////                        {
////                            HomeActivity. vocher_Items.add(list.get(position));
////                            HomeActivity.  FillrecyclerView_Items(context,HomeActivity. vocher_Items);
////
////                        }
////
////                        else
////                        {
////                                   if(!IsExistsInList( list.get(position).getItemNCode())) // new item
////                                   {
//////                                       Log.e("case2vocher_Items=",  HomeActivity. vocher_Items.size()+"");
//////                                       HomeActivity.vocher_Items.add(list.get(position));
//////                                       HomeActivity.voherItemAdapter.notifyItemInserted(HomeActivity.vocher_Items.size() - 1);
////                                   }
////
////                                   else // item already added
////                                   {
////
//////                                       Log.e("case3vocher_Items=",  HomeActivity. vocher_Items.size()+"");
////
////                                       list.get(index).setDiscount(Double.parseDouble(ITEMdiscount.getText().toString()));
////                                       list.get(index).setQty(Double.parseDouble(ITEMqty.getText().toString()));
//////                                       HomeActivity.voherItemAdapter.notifyItemChanged(index);
////
////
////                                   }
////
////                        }
//                            dialog.dismiss();
//
//                        }
//                    });
//
//                }
//            });
//            ImageView imageView =convertView.findViewById(R.id.image);
//
//
//            itemNCode.setText(list.get(position).getItemNCode());
//            tax.setText(list.get(position).getTax());
//            price.setText(1+"");
//
//                if(list.get(position).getUnit().equals("1"))
//                    RB_box.setChecked(true);
//                else
//                    RB_Mt.setChecked(true);
//
//
//            itemKind.setText(list.get(position).getItemKind());
//            area.setText(area.getText().toString());
//            qty.setText(list.get(position).getAviqty()+"");
//
//
//      //      imageView.setImageBitmap(list.get(position).getLogoimage());
//            //    imageView.setBackground(context.getDrawable(R.drawable.arabescatobreccia));
//
//        }
//        return convertView;
//
//    }
//
//
//boolean IsExistsInList(String ItemNCode ){
////    index=0;
////        for(int i=0;i< HomeActivity. vocher_Items.size();i++)
////            if(HomeActivity. vocher_Items.get(i).getItemNCode().equals(ItemNCode))
////            {
////                index=i;
////                return true;
////
////            }
//
//        return false;
//}


}

