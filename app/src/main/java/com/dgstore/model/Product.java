package com.dgstore.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "selectedProductData")
public class Product {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    int id;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    String title;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    String price;

    @ColumnInfo(name = "piece")
    @SerializedName("piece")
    int piece;

    @ColumnInfo(name = "category")
    @SerializedName("category")
    String category;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    String description;

    @ColumnInfo(name = "image")
    @SerializedName("image")
    String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", piece=" + piece +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}


