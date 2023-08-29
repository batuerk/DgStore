package com.dgstore;

import android.content.Context;
import android.view.View;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class,}, version = 1)

public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase INSTANCE;

    public abstract DaoUser daoUser();

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
