//package com.dgstore.database;
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//
//import com.dgstore.model.Product;
//
//import java.util.List;
//
//@Dao
//public interface DaoFavorite {
//
//    @Insert
//    void addFavorite(Product selectedFavorite);
//
//    @Insert
//    void addMoreFavorite(Product selectedFavorites);
//
//    @Insert
//    void addMoreListFavorite(List<Product> selectedListFavorites);
//
//    @Query("SELECT * FROM selectedFavoriteData")
//    List<Product> allSelectedFavorite();
//
//    @Update
//    void update(Product selectedListFavorites);
//
//    @Delete
//    void delete(Product selectedListFavorites);
//}
