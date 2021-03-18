package com.michelle_condon.is4401_finalyearproject.Models;

//Code below is based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

public class FetchData {
    //Declare variables
    String name;
    String description;
    String price;
    String quantity;
    String barcode;

    public FetchData() {
    }

    public FetchData(String name, String description, String price, String quantity, String barcode) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    //Getter
    public String getName() {
        return name;
    }

    //Setter
    public void setName(String name) {
        this.name = name;
    }

    //Getter
    public String getDescription() {
        return description;
    }

    //Setter
    public void setDescription(String description) {
        this.description = description;
    }

    //Getter
    public String getPrice() {
        return price;
    }

    //Setter
    public void setPrice(String price) {
        this.price = price;
    }

    //Getter
    public String getQuantity() {
        return quantity;
    }

    //Setter
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

//End