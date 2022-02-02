package com.falcons.buildingstore.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Items_Table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Item_Name")
    private String itemName;

    @ColumnInfo(name = "Item_OCode")
    private String itemOCode;

    @ColumnInfo(name = "Item_NCode")
    public String itemNCode;

    @ColumnInfo(name = "Avi_Qty")
    public double Aviqty;




    @ColumnInfo(name = "Price")
    public double price;


    @ColumnInfo(name = "Unit")
    private String unit;

    @ColumnInfo(name = "Image_Path")
    private String imagePath;

    @ColumnInfo(name = "Item_Kind")
    private String itemKind;

    @ColumnInfo(name = "Tax")
    private String tax;
    @ColumnInfo(name = "Qty")
    public double qty;
    @ColumnInfo(name = "Item_Discount")
    double Discount;
    @ColumnInfo(name = "CUS_ID")
    public int Cus_Id;

    public String area;

    public Item(int id, String itemName, String itemOCode, String itemNCode, double aviqty, double price, String unit, String imagePath, String itemKind, String tax, double qty, double discount, int cus_Id, String area) {
        this.id = id;
        this.itemName = itemName;
        this.itemOCode = itemOCode;
        this.itemNCode = itemNCode;
        Aviqty = aviqty;
        this.price = price;
        this.unit = unit;
        this.imagePath = imagePath;
        this.itemKind = itemKind;
        this.tax = tax;
        this.qty = qty;
        Discount = discount;
        Cus_Id = cus_Id;
        this.area = area;
    }

    public Item() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getItemKind() {
        return itemKind;
    }

    public int getCus_Id() {
        return Cus_Id;
    }

    public String getItemOCode() {
        return itemOCode;
    }

    public void setItemOCode(String itemOCode) {
        this.itemOCode = itemOCode;
    }

    public String getItemNCode() {
        return itemNCode;
    }

    public void setItemNCode(String itemNCode) {
        this.itemNCode = itemNCode;
    }


    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getPrice() {
        return price;
    }



    public void setItem_Name(String item_Name) {
        this.itemName = item_Name;
    }

    public void setCus_Id(int cus_Id) {
        Cus_Id = cus_Id;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public double getAviqty() {
        return Aviqty;
    }

    public void setAviqty(double aviqty) {
        Aviqty = aviqty;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}
