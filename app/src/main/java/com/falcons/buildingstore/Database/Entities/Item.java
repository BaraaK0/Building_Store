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

    @ColumnInfo(name = "CategoryId")
    public String categoryId;

    @ColumnInfo(name = "Price")
    public double price;

    @ColumnInfo(name = "Unit")
    private String unit;

    @ColumnInfo(name = "Image_Path")
    private String imagePath;

    @ColumnInfo(name = "Item_Kind")
    private String itemKind;

    @ColumnInfo(name = "Tax")
    private double tax;

    @ColumnInfo(name = "Qty")
    public double qty;

    @ColumnInfo(name = "taxPercent")
    private double taxPercent;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "taxValue")
    private double taxValue;

    @ColumnInfo(name = "area")
    public String area;
    public double getTaxPercent() {
        return taxPercent;
    }

    public double getAmount() {
        return amount;
    }

    public double getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(double taxValue) {
        this.taxValue = taxValue;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent;
    }

    @ColumnInfo(name = "Item_Discount",defaultValue = "0")
    double Discount;



    public Item(int id, String itemName, String itemOCode, String itemNCode, double aviqty, double price, String unit, String imagePath, String itemKind,  double qty, double discount, String area) {
        this.id = id;
        this.itemName = itemName;
        this.itemOCode = itemOCode;
        this.itemNCode = itemNCode;
        Aviqty = aviqty;
        this.price = price;
        this.unit = unit;
        this.imagePath = imagePath;
        this.itemKind = itemKind;

        this.qty = qty;
        Discount = discount;
        this.area = area;
    }

    public Item() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
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
