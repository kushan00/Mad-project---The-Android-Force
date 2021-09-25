package com.example.madproject;

public class model
{

    model(){

    }

    String name , description  , price ,furl;
//creatte construct
    public model(String name, String description, String price, String furl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.furl = furl;
    }

    public String getName() {
        return name;
    }
    //comitt

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }
}
