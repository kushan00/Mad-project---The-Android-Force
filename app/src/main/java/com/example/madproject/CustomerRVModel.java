package com.example.madproject;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerRVModel implements Parcelable {
    private String fullName;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String customerNic;
    private String customerID;

    public  CustomerRVModel(){

    }

    public CustomerRVModel(String customerName,String fullName, String customerAddress, String customerPhone, String customerEmail, String customerNic, String customerID) {
        this.customerName = customerName;
        this.fullName = fullName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerNic = customerNic;
        this.customerID = customerID;
    }

    protected CustomerRVModel(Parcel in) {
        customerName = in.readString();
        fullName = in.readString();
        customerAddress = in.readString();
        customerPhone = in.readString();
        customerEmail = in.readString();
        customerNic = in.readString();
        customerID = in.readString();
    }

    public static final Creator<CustomerRVModel> CREATOR = new Creator<CustomerRVModel>() {
        @Override
        public CustomerRVModel createFromParcel(Parcel in) {
            return new CustomerRVModel(in);
        }

        @Override
        public CustomerRVModel[] newArray(int size) {
            return new CustomerRVModel[size];
        }
    };

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(){
        this.fullName = fullName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerNic() {
        return customerNic;
    }

    public void setCustomerNic(String customerNic) {
        this.customerNic = customerNic;
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
        dest.writeString(fullName);
        dest.writeString(customerAddress);
        dest.writeString(customerPhone);
        dest.writeString(customerEmail);
        dest.writeString(customerNic);
        dest.writeString(customerID);
    }
}
