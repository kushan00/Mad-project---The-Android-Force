package com.example.madproject;

import android.os.Parcel;
import android.os.Parcelable;

public class BuySelectedFood implements Parcelable {

    private String foodtype;
    private String quantity;
    private String additional;
    private String id;

    public BuySelectedFood(){

    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BuySelectedFood(String foodtype, String quantity, String additional, String id) {
        this.foodtype = foodtype;
        this.quantity = quantity;
        this.additional = additional;
        this.id = id;
    }

    protected BuySelectedFood(Parcel in) {
        foodtype = in.readString();
        quantity = in.readString();
        additional = in.readString();
        id = in.readString();
    }

    public static final Creator<BuySelectedFood> CREATOR = new Creator<BuySelectedFood>() {
        @Override
        public BuySelectedFood createFromParcel(Parcel in) {
            return new BuySelectedFood(in);
        }

        @Override
        public BuySelectedFood[] newArray(int size) {
            return new BuySelectedFood[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(foodtype);
        parcel.writeString(quantity);
        parcel.writeString(additional);
        parcel.writeString(id);
    }
}
