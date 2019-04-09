package com.sapl.distributormsdpharma.models;

/**
 * Created by Sony on 01/02/2018.
 */

public class ItemModel {




    String item_name = "";
    String quantity_type = ""; //case or bottle
    String quantity_sub_type = "";
    String orderedQuantity_cases= ""; //15
    String orderedQuantity_free_cases =""; // after + i.e 1
    String orderedQuantity_bottles=""; //10
    String orderedQuantity_free_bottles = ""; //after + i.e 1
    String total_amt = "";

    public String getOrderedQuantity_free_bottles() {
        return orderedQuantity_free_bottles;
    }

    public void setOrderedQuantity_free_bottles(String orderedQuantity_free_bottles) {
        this.orderedQuantity_free_bottles = orderedQuantity_free_bottles;
    }

    public ItemModel(String item_name, String quantity_type,String quantity_sub_type,String orderedQuantity_cases, String orderedQuantity_free_cases, String orderedQuantity_bottles, String orderedQuantity_free_bottles,String total_amt) {
        this.item_name = item_name;
        this.quantity_type = quantity_type;
        this.orderedQuantity_cases = orderedQuantity_cases;
        this.orderedQuantity_free_cases = orderedQuantity_free_cases; //not displayed
        this.orderedQuantity_bottles = orderedQuantity_bottles;
        this.quantity_sub_type = quantity_sub_type;
        this.orderedQuantity_free_bottles = orderedQuantity_free_bottles; //not displayed
        this.total_amt = total_amt;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public String getQuantity_sub_type() {
        return quantity_sub_type;
    }

    public void setQuantity_sub_type(String quantity_sub_type) {
        this.quantity_sub_type = quantity_sub_type;
    }

    public String getitem_name() {
        return item_name;
    }

    public void setitem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getquantity_type() {
        return quantity_type;
    }

    public void setquantity_type(String quantity_type) {
        this.quantity_type = quantity_type;
    }

    public String getorderedQuantity_cases() {
        return orderedQuantity_cases;
    }

    public void setorderedQuantity_cases(String orderedQuantity_cases) {
        this.orderedQuantity_cases = orderedQuantity_cases;
    }

    public String getorderedQuantity_free_cases() {
        return orderedQuantity_free_cases;
    }

    public void setorderedQuantity_free_cases(String orderedQuantity_free_cases) {
        this.orderedQuantity_free_cases = orderedQuantity_free_cases;
    }

    public String getorderedQuantity_bottles() {
        return orderedQuantity_bottles;
    }

    public void setorderedQuantity_bottles(String orderedQuantity_bottles) {
        this.orderedQuantity_bottles = orderedQuantity_bottles;
    }


    @Override
    public String toString() {
        return "ItemModel{" +
                "item_name='" + item_name + '\'' +
                ", quantity_type='" + quantity_type + '\'' +
                ", orderedQuantity_cases='" + orderedQuantity_cases + '\'' +
                ", orderedQuantity_free_cases='" + orderedQuantity_free_cases + '\'' +
                ", orderedQuantity_bottles='" + orderedQuantity_bottles + '\'' +
                ", orderedQuantity_free_bottles='" + orderedQuantity_free_bottles + '\'' +
                '}';
    }

}
