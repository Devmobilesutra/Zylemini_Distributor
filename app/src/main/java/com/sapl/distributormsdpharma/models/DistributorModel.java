package com.sapl.distributormsdpharma.models;

/**
 * Created by Ganesh Borse on 20-Jan-18.
 */

public class DistributorModel {

    String name = "";
    String date = "";
    String orderStatus = "";
    String cartValue ="";
    String distImagePath="";
    String amt="";



    public DistributorModel(String name, String date, String orderStatus, String cartValue, String distImagePath, String amt) {
        this.name = name;
        this.date = date;
        this.orderStatus = orderStatus;
        this.cartValue = cartValue;
        this.distImagePath = distImagePath;
        this.amt = amt;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getorderStatus() {
        return orderStatus;
    }

    public void setorderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getcartValue() {
        return cartValue;
    }

    public void setcartValue(String cartValue) {
        this.cartValue = cartValue;
    }

    public String getDistImagePath() {
        return distImagePath;
    }

    public void setDistImagePath(String distImagePath) {
        this.distImagePath = distImagePath;
    }


    @Override
    public String toString() {
        return "DistributorModel{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }


}
