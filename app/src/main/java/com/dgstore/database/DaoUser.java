package com.dgstore.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dgstore.model.User;

import java.util.List;

@Dao
public interface DaoUser {
    @Insert
    void addUser(User user);

    @Insert
    void addMoreUser(User  users);

    @Insert
    void addMoreListUser(List<User> kullanicilar);

    @Query("SELECT * FROM user")
    List<User> allUsers();

    @Query("SELECT * FROM user  where name LIKE  :name")
    User findUser(String name);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

}
