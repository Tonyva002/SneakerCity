package app.ejemplo.sneakercity.Models;

import app.ejemplo.sneakercity.Helpes.UtilsHelper;

import org.parceler.Parcel;

@Parcel
public class Cart {

    private String id_product, id_user, currency;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public void saveToCart(){

        // Asignar usuario automáticamente
        this.id_user = UtilsHelper.getUserId();

        // Validar moneda (por defecto USD si viene null)
        if (this.currency == null || this.currency.isEmpty()) {
            this.currency = "USD";
        }

        // Calcular total automáticamente
        this.totalPrice = this.price * this.quantity;

        // Guardar el objeto en Firebase
        UtilsHelper.getDatabase()
                .child("cart")
                .child(this.id_user)
                .child(this.id_product)
                .setValue(this);
    }

    public void decrementToCart(){
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("quantity").setValue(quantity);
        UtilsHelper.getDatabase().child("cart").child(UtilsHelper.getUserId())
                .child(id_product).child("totalPrice").setValue(totalPrice);
    }
}
