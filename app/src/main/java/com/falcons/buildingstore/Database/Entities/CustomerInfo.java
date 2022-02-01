package com.falcons.buildingstore.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Customers_Info")
public class CustomerInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Customer_ID")
    private int customerId;

    @ColumnInfo(name = "Customer_Name")
    private String customerName;

    @ColumnInfo(name = "Phone_No")
    private String phoneNo;

    public CustomerInfo(int customerId, String customerName, String phoneNo) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
    }

    public CustomerInfo(String customerName, String phoneNo) {
        this.customerName = customerName;
        this.phoneNo = phoneNo;
    }

    public CustomerInfo() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
