package com.example.madproject;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentDetialsRVModal implements Parcelable {

    private String username;
    private String Fullname;
    private String Address;
    private String Phone;
    private String Bankname;
    private String AccNumber;
    private String Cvv;
    private String RegID;

    public   PaymentDetialsRVModal(){

    }

    public PaymentDetialsRVModal(String username, String fullname, String address, String phone, String bankname, String accNumber, String cvv, String regID) {
        this.username = username;
        Fullname = fullname;
        Address = address;
        Phone = phone;
        Bankname = bankname;
        AccNumber = accNumber;
        Cvv = cvv;
        RegID = regID;
    }

    protected PaymentDetialsRVModal(Parcel in) {
        username = in.readString();
        Fullname = in.readString();
        Address = in.readString();
        Phone = in.readString();
        Bankname = in.readString();
        AccNumber = in.readString();
        Cvv = in.readString();
        RegID = in.readString();
    }

    public static final Creator<PaymentDetialsRVModal> CREATOR = new Creator<PaymentDetialsRVModal>() {
        @Override
        public PaymentDetialsRVModal createFromParcel(Parcel in) {
            return new PaymentDetialsRVModal(in);
        }

        @Override
        public PaymentDetialsRVModal[] newArray(int size) {
            return new PaymentDetialsRVModal[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBankname() {
        return Bankname;
    }

    public void setBankname(String bankname) {
        Bankname = bankname;
    }

    public String getAccNumber() {
        return AccNumber;
    }

    public void setAccNumber(String accNumber) {
        AccNumber = accNumber;
    }

    public String getCvv() {
        return Cvv;
    }

    public void setCvv(String cvv) {
        Cvv = cvv;
    }

    public String getRegID() {
        return RegID;
    }

    public void setRegID(String regID) {
        RegID = regID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(Fullname);
        parcel.writeString(Address);
        parcel.writeString(Phone);
        parcel.writeString(Bankname);
        parcel.writeString(AccNumber);
        parcel.writeString(Cvv);
        parcel.writeString(RegID);
    }
}
