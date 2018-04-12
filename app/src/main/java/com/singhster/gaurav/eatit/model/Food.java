package com.singhster.gaurav.eatit.model;

/**
 * Created by gaurav on 3/12/2018.
 */

public class Food {

   private String Description,Image,MenuId,Name,Price,Discount;

    public Food() {
    }

    public Food(String description, String image, String menuId, String name, String price,String discount) {
        Description = description;
        Image = image;
        MenuId = menuId;
        Name = name;
        Price = price;
        Discount=discount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {

        Price = discount;
    }
}
