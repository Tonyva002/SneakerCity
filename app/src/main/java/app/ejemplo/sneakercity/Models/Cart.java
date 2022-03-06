package app.ejemplo.sneakercity.Models;

import app.ejemplo.sneakercity.Helpes.UtilsHelper;

import org.parceler.Parcel;

@Parcel
public class Cart {

    private String id_product, id_user;
    private double price, totalPrice;
    private int quantity;

    public Cart(){

    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void saveToCart(){
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("id_user").setValue(UtilsHelper.getUserId());
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("id_product").setValue(id_product);
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("quantity").setValue(quantity);
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("price").setValue(price);
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("totalPrice").setValue(totalPrice);

    }

    public void descrementToCart(){
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("quantity").setValue(quantity);
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("totalPrice").setValue(totalPrice);
    }
}
