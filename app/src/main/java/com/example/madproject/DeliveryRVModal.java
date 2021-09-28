package com.example.madproject;

import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryRVModal implements Parcelable {
    private String customerName;
    private String buildingNumber;
    private String streetName;
    private String city;
    private String contactNUmber;
    private String time;
    private String customerID;

    public DeliveryRVModal(){

    }

    public DeliveryRVModal(String customerName, String buildingNumber, String streetName, String city, String contactNUmber, String time, String customerID) {
        this.customerName = customerName;
        this.buildingNumber = buildingNumber;
        this.streetName = streetName;
        this.city = city;
        this.contactNUmber = contactNUmber;
        this.time = time;
        this.customerID = customerID;
    }

    protected DeliveryRVModal(Parcel in) {
        customerName = in.readString();
        buildingNumber = in.readString();
        streetName = in.readString();
        city = in.readString();
        contactNUmber = in.readString();
        time = in.readString();
        customerID = in.readString();
    }

    public static final Creator<DeliveryRVModal> CREATOR = new Creator<DeliveryRVModal>() {
        @Override
        public DeliveryRVModal createFromParcel(Parcel in) {
            return new DeliveryRVModal(in);
        }

        @Override
        public DeliveryRVModal[] newArray(int size) {
            return new DeliveryRVModal[size];
        }
    };



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNUmber() {
        return contactNUmber;
    }

    public void setContactNUmber(String contactNUmber) {
        this.contactNUmber = contactNUmber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customerName);
        dest.writeString(buildingNumber);
        dest.writeString(streetName);
        dest.writeString(city);
        dest.writeString(contactNUmber);
        dest.writeString(time);
        dest.writeString(customerID);
    }
}
