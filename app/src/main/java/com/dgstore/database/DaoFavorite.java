package com.dgstore.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dgstore.model.FavoriteProduct;

import java.util.List;

@Dao
public interface DaoFavorite {

    @Insert
    void addFavorite(FavoriteProduct selectedFavorite);

    @Insert
    void addMoreFavorite(FavoriteProduct selectedFavorites);

    @Insert
    void addMoreListFavorite(List<FavoriteProduct> selectedListFavorites);

    @Query("SELECT * FROM selectedFavoriteData")
    List<FavoriteProduct> allSelectedFavorite();

    @Query("SELECT * FROM selectedFavoriteData where id = :favoriteId")
    FavoriteProduct getFavoriteId (int favoriteId);

    @Update
    void update(FavoriteProduct selectedListFavorites);

    @Delete
    void delete(FavoriteProduct selectedListFavorites);
}
