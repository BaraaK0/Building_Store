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
    private String itemNCode;

    @ColumnInfo(name = "Qty")
    private String qty;

    @ColumnInfo(name = "Price")
    private String price;

    @ColumnInfo(name = "Unit")
    private String unit;

    @ColumnInfo(name = "Image_Path")
    private String imagePath;

    @ColumnInfo(name = "Item_Kind")
    private String itemKind;

    @ColumnInfo(name = "Tax")
    private String tax;

    public Item(int id, String itemName, String itemOCode, String itemNCode, String qty, String price, String unit, String imagePath, String itemKind, String tax) {
        this.id = id;
        this.itemName = itemName;
        this.itemOCode = itemOCode;
        this.itemNCode = itemNCode;
        this.qty = qty;
        this.price = price;
        this.unit = unit;
        this.imagePath = imagePath;
        this.itemKind = itemKind;
        this.tax = tax;
    }

    public Item(String itemName, String itemOCode, String itemNCode, String qty, String price, String unit, String imagePath, String itemKind, String tax) {
        this.itemName = itemName;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

}
