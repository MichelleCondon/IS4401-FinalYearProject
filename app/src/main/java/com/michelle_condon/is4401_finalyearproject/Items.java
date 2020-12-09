
package com.michelle_condon.is4401_finalyearproject;

public class Items {

    private String name;
    private  String description;
    private String barcode;
    private String price;
    private String quantity;


    public Items(String name, String description, String barcode, String price, String quantity) {
        this.name = name;
        this.barcode = barcode;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Items(){

    }
}
