package com.example.sneakercity.Models;

import com.example.sneakercity.Helpes.UtilsHelper;

import org.parceler.Parcel;

import java.util.UUID;

@Parcel
public class User {

    private String name, lastName, city, country, community, address, address2, phone, postalCode, idAddress;

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public void saveUserInformation(boolean isNew){
        String randomId = idAddress;

        if (isNew){
            randomId = UUID.randomUUID().toString();
        }
        saveUserInformationDB(randomId);
    }

    private void saveUserInformationDB(String newId) {
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("name").setValue(name);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("lastName").setValue(lastName);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("city").setValue(city);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("country").setValue(country);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("community").setValue(community);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("address").setValue(address);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("address2").setValue(address2);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("phone").setValue(phone);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("userAddress").child(newId).child("postalCode").setValue(postalCode);
    }
}
