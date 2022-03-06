package app.ejemplo.sneakercity.Models;

import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.UUID;

@IgnoreExtraProperties
public class Invoice {

    private String name, country, address, address2, postalCode, community, city, phone, idInvoice;

    public Invoice(){

    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
    }

    public void saveBillingAddressInformation(boolean isNew){
        String randimId = idInvoice;

        if (isNew){
            randimId = UUID.randomUUID().toString();
        }
        saveBillingAddressInformationDB(randimId);
    }

    private void saveBillingAddressInformationDB(String newId) {
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(newId).child("name").setValue(name);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(newId).child("country").setValue(country);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(newId).child("address").setValue(address);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(newId).child("address2").setValue(address2);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(newId).child("postalCode").setValue(postalCode);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(newId).child("community").setValue(community);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(newId).child("city").setValue(city);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("invoiceAddress").child(newId).child("phone").setValue(phone);
    }
}
