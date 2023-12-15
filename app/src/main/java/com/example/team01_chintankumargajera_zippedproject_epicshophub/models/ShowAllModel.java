package com.example.team01_chintankumargajera_zippedproject_epicshophub.models;

public class ShowAllModel {
    String description;
    String name;
    String rating;
    int price;
    String img_url;
    String type;

    // Add a no-argument constructor
    public ShowAllModel() {
        // Default constructor required for Firestore
    }

    public ShowAllModel(String description, String name, String rating, int price, String img_url, String type) {
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.img_url = img_url;
        this.type = type;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
