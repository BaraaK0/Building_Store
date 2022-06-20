package com.falcons.buildingstore.Database.Entities;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.falcons.buildingstore.Utilities.ExportData;

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


    @ColumnInfo(name = "Discount")
    private double discount;

    @ColumnInfo(name = "Tax")
    private double tax;

    @ColumnInfo(name = "TotalQty")
    private double TotalQty;



    @ColumnInfo(name = "Customer_ID")
    private int customerId;

    @ColumnInfo(name = "IS_Posted", defaultValue = "0")
    private int isPosted;
    @ColumnInfo(name = "ConfirmState")
    private int ConfirmState;


    @ColumnInfo(name = "subTotal")
    private double subTotal;

    @ColumnInfo(name = "UserNo")
    private String UserNo;

    @ColumnInfo(name = "NetTotal",defaultValue = "0")
    private double NetTotal;

    @ColumnInfo(name = "voucherDiscount")
    private double voucherDiscount;

    @ColumnInfo(name = "totalVoucherDiscount")
    private double totalVoucherDiscount;

    public double getTotalQty() {
        return TotalQty;
    }

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String userNo) {
        UserNo = userNo;
    }

    public void setTotalQty(double totalQty) {
        TotalQty = totalQty;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getNetTotal() {
        return NetTotal;
    }

    public void setNetTotal(double netTotal) {
        NetTotal = netTotal;
    }

    public double getVoucherDiscount() {
        return voucherDiscount;
    }

    public void setVoucherDiscount(double voucherDiscount) {
        this.voucherDiscount = voucherDiscount;
    }

    public double getTotalVoucherDiscount() {
        return totalVoucherDiscount;
    }

    public void setTotalVoucherDiscount(double totalVoucherDiscount) {
        this.totalVoucherDiscount = totalVoucherDiscount;
    }

    public int getConfirmState() {
        return ConfirmState;
    }

    public void setConfirmState(int confirmState) {
        ConfirmState = confirmState;
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


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
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
            obj.put("COMAPNYNO", ExportData.coNo);
            obj.put("VOUCHERNO", vhfNo);
            obj.put("VOUCHERTYPE", 508);

            obj.put("VOUCHERDATE", date);
            obj.put("SALESMANNO", UserNo);
            obj.put("VOUCHERDISCOUNT", discount);
            obj.put("VOUCHERDISCOUNTPERCENT", discount);

                obj.put("NOTES", "");


            obj.put("CACR", 1);
            obj.put("ISPOSTED", "0");
            obj.put("NETSALES", NetTotal);
            obj.put("CUSTOMERNO", customerId);
            obj.put("VOUCHERYEAR", 2022);

            obj.put("PAYMETHOD", "");


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
