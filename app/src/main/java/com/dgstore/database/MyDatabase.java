package com.dgstore.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dgstore.model.FavoriteProduct;
import com.dgstore.model.OrderHistory;
import com.dgstore.model.OrderProduct;
import com.dgstore.model.Product;
import com.dgstore.model.User;

@Database(entities = {User.class, Product.class, FavoriteProduct.class, OrderHistory.class }, version = 4)

public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase INSTANCE;

    public abstract DaoUser daoUser();
    public abstract DaoProduct daoProduct();
    public abstract DaoFavorite daoFavorite();
    public abstract DaoOrders daoOrders();

    public static MyDatabase getMyDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "user-database")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }

    public static void destroyInstance(){

        INSTANCE = null;
    }

}
