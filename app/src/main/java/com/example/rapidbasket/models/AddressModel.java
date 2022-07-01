package com.example.rapidbasket.models;

public class AddressModel {

    String Address;
    boolean isSelected;

    public AddressModel() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
