package com.dgstore.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "orderHistory")
public class OrderHistory {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    int id;

    @ColumnInfo(name = "orderNumber")
    long orderNumber;

    @ColumnInfo(name = "totalAmount")
    double totalAmount;

    @TypeConverters(OrderProductListConverter.class)
    @ColumnInfo(name = "orderProductList")
    List<OrderProduct> orderProductList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }
}
