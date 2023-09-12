package com.dgstore.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class OrderProduct {

    @SerializedName("title")
    String title;

    @SerializedName("price")
    String price;

    @SerializedName("piece")
    int piece;

    @SerializedName("category")
    String category;

    @SerializedName("description")
    String description;

    @SerializedName("image")
    String image;

    boolean isAddedFavorite = false;

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

    public boolean getIsAddedFavorite() {
        return isAddedFavorite;
    }

    public void setIsAddedFavorite(boolean isAddedFavorite) {
        this.isAddedFavorite = isAddedFavorite;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", piece=" + piece +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", isAddedFavorite=" + isAddedFavorite +
                '}';
    }
}

