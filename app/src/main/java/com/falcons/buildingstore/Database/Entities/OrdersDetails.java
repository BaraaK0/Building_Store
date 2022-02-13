package com.falcons.buildingstore.Database.Entities;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "Orders_Details")
public class OrdersDetails {
    @PrimaryKey
    @ColumnInfo(name = "VHFNO")
    private int vhfNo;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "Time")
    private String time;

    @ColumnInfo(name = "Item_No")
    private String itemNo;

    @ColumnInfo(name = "Item_Name")
    private String itemName;

    @ColumnInfo(name = "Qty")
    private double qty;

    @ColumnInfo(name = "Discount")
    private double discount;

    @ColumnInfo(name = "Tax")
    private String tax;

    @ColumnInfo(name = "Area")
    private String area;

    @ColumnInfo(name = "Total")
    private double total;

    @ColumnInfo(name = "Price")
    private double price;

    @ColumnInfo(name = "Customer_ID")
    private int customerId;
    @ColumnInfo(name = "Unit")
    private int Unit;
    @ColumnInfo(name = "IS_Posted", defaultValue = "0")
    private int isPosted;

    public OrdersDetails() {
    }

    public OrdersDetails(int vhfNo, String date, String time, String itemNo, String itemName, double qty, double discount, String tax, String area, double total, double price, int customerId, int unit, int isPosted) {
        this.vhfNo = vhfNo;
        this.date = date;
        this.time = time;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.qty = qty;
        this.discount = discount;
        this.tax = tax;
        this.area = area;
        this.total = total;
        this.price = price;
        this.customerId = customerId;
        Unit = unit;
        this.isPosted = isPosted;
    }

    public int getUnit() {
        return Unit;
    }

    public void setUnit(int unit) {
        Unit = unit;
    }


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

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
        try {
            obj.put("VOUCHERNO", vhfNo+"");
            obj.put("VOUCHERTYPE", "");
            obj.put("ITEMNO", itemNo);
            obj.put("UNIT", Unit);
            obj.put("QTY", qty);
            obj.put("UNITPRICE", price);
            obj.put("BONUS", "");
            obj.put("ITEMDISCOUNTVALUE", discount);
            obj.put("ITEMDISCOUNTPRC", discount);
            obj.put("VOUCHERDISCOUNT", discount);
            obj.put("TAXVALUE", tax);
            obj.put("TAXPERCENT", tax);
            obj.put("COMAPNYNO", "");
            obj.put("ISPOSTED", "0");
            obj.put("VOUCHERYEAR", "2022");
            obj.put("ITEM_DESCRITION", "");
            obj.put("SERIAL_CODE", "");
            obj.put("ITEM_SERIAL_CODE", "");

            obj.put("WHICHUNIT", "");
            obj.put("WHICHUNITSTR", "");

          obj.put("WHICHUQTY", "0");

            obj.put("ENTERQTY", "");
            obj.put("ENTERPRICE", "");
            obj.put("UNITBARCODE", "");
            obj.put("CALCQTY", "");
            obj.put("ORGVHFNO", "");






        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
