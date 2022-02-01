package com.falcons.buildingstore.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Items_Table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Item_OCode")
    public String itemOCode;

    @ColumnInfo(name = "Item_NCode")
    public String itemNCode;

    @ColumnInfo(name = "Avi_Qty")
    public double Aviqty;

    @ColumnInfo(name = "Price")
    public String price;

    @ColumnInfo(name = "Unit")
    public String unit;

    @ColumnInfo(name = "Image_Path")
    public String imagePath;

    @ColumnInfo(name = "Item_Kind")
    public String itemKind;

    @ColumnInfo(name = "Tax")
    public String tax;

    @ColumnInfo(name = "Qty")
    public double qty;
    @ColumnInfo(name = "Item_Discount")
    double Discount;
    public String area;
    public Item(int id, String itemOCode, String itemNCode, double qty, String price, String unit, String imagePath, String itemKind, String tax) {
        this.id = id;
        this.itemOCode = itemOCode;
        this.itemNCode = itemNCode;
        this.qty = qty;
        this.price = price;
        this.unit = unit;
        this.imagePath = imagePath;
        this.itemKind = itemKind;
        this.tax = tax;
    }

    public Item(String itemOCode, String itemNCode, double qty, String price, String unit, String imagePath, String itemKind, String tax) {
        this.itemOCode = itemOCode;
        this.itemNCode = itemNCode;
        this.qty = qty;
        this.price = price;
        this.unit = unit;
        this.imagePath = imagePath;
        this.itemKind = itemKind;
        this.tax = tax;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getItemKind() {
        return itemKind;
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
