package com.falcons.buildingstore.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Temp_Orders")
public class TempOrder {

    @PrimaryKey(autoGenerate = true)
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
    private String qty;

    @ColumnInfo(name = "Discount")
    private String discount;

    @ColumnInfo(name = "Tax")
    private String tax;

    @ColumnInfo(name = "Area")
    private String area;

    @ColumnInfo(name = "Total")
    private String total;

    @ColumnInfo(name = "Price")
    private String price;

    @ColumnInfo(name = "Customer_ID")
    private int customerId;

    @ColumnInfo(name = "Confirm_Status", defaultValue = "0")
    private int confStatus;

    public TempOrder(int vhfNo, String date, String time, String itemNo, String itemName, String qty, String discount, String tax, String area, String total, String price, int customerId, int confStatus) {
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
        this.confStatus = confStatus;
    }

    public TempOrder(String date, String time, String itemNo, String itemName, String qty, String discount, String tax, String area, String total, String price, int customerId, int confStatus) {
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
        this.confStatus = confStatus;
    }

    public TempOrder() {
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getConfStatus() {
        return confStatus;
    }

    public void setConfStatus(int confStatus) {
        this.confStatus = confStatus;
    }
}
