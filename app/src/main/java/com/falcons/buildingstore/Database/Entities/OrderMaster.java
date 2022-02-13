package com.falcons.buildingstore.Database.Entities;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "Orders_Master")
public class OrderMaster {

    @PrimaryKey
    @ColumnInfo(name = "VHFNO")
    private int vhfNo;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "Time")
    private String time;


    @ColumnInfo(name = "Qty")
    private double qty;

    @ColumnInfo(name = "Discount")
    private double discount;

    @ColumnInfo(name = "Tax")
    private String tax;

    @ColumnInfo(name = "Total")
    private double total;

    @ColumnInfo(name = "Price")
    private double price;

    @ColumnInfo(name = "Customer_ID")
    private int customerId;

    @ColumnInfo(name = "IS_Posted", defaultValue = "0")
    private int isPosted;

    public int getVhfNo() {
        return vhfNo;
    }

    public void setVhfNo(int vhfNo) {
        this.vhfNo = vhfNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(int isPosted) {
        this.isPosted = isPosted;
    }

    public JSONObject getJSONObjectDelphi() {
        JSONObject obj = new JSONObject();
        String voucherDateFormet="";
        //"JSN":[{"COMAPNYNO":290,"VOUCHERYEAR":"2021","VOUCHERNO":"1212","VOUCHERTYPE":"3","VOUCHERDATE":"24/03/2020",
        //      "SALESMANNO":"5","CUSTOMERNO":"123456","VOUCHERDISCOUNT":"50",
        //    "VOUCHERDISCOUNTPERCENT":"10","NOTES":"AAAAAA","CACR":"1","ISPOSTED":"0","PAYMETHOD":"1","NETSALES":"150.720"}]}
        try {
            obj.put("COMAPNYNO", "");
            obj.put("VOUCHERNO", vhfNo);
            obj.put("VOUCHERTYPE", 508);

            obj.put("VOUCHERDATE", date);
            obj.put("SALESMANNO", 1);
            obj.put("VOUCHERDISCOUNT", discount);
            obj.put("VOUCHERDISCOUNTPERCENT", discount);

                obj.put("NOTES", "");


            obj.put("CACR", 1);
            obj.put("ISPOSTED", "0");
            obj.put("NETSALES", total);
            obj.put("CUSTOMERNO", customerId);
            obj.put("VOUCHERYEAR", 2022);

            obj.put("PAYMETHOD", "");


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
