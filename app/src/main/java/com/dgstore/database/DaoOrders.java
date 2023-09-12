package com.dgstore.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dgstore.model.FavoriteProduct;
import com.dgstore.model.OrderHistory;
import com.dgstore.model.OrderProduct;
import com.dgstore.model.Product;
import com.dgstore.ui.fragment.OrderFragment;

import java.util.List;
@Dao
public interface DaoOrders {
    @Insert
    void addOrder(OrderHistory orderProduct);

    @Insert
    void addMoreProduct(OrderHistory  orderProduct);

    @Insert
    void addMoreListProduct(List<OrderHistory> orderProductList);

    @Query("SELECT * FROM orderHistory")
    List<OrderHistory> allOrderProduct();

    @Query("SELECT * FROM orderHistory where id = :orderId")
    OrderHistory getOrderId (int orderId);

    @Update
    void update(OrderHistory orderProduct);

    @Delete
    void delete(OrderHistory orderProduct);
}
