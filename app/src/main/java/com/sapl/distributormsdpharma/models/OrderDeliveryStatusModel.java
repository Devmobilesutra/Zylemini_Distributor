package com.sapl.distributormsdpharma.models;

/**
 * Created by Sony on 13/02/2018.
 */

public class OrderDeliveryStatusModel {
    String shopName;
    String mobileNo;
    String orderDate;
    String orderStatus;
    String totalQtys;
    String amount;
    String orderId;
    String retailerId;

    /*public OrderDeliveryStatusModel(String shopName, String mobileNo, String orderDate, String orderStatus, String totalQtys, String amount) {
        this.shopName = shopName;
        this.mobileNo = mobileNo;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalQtys = totalQtys;
        this.amount = amount;
    }*/


    public OrderDeliveryStatusModel(String shopName, String mobileNo,
                                    String orderDate, String orderStatus,
                                    String totalQtys, String amount,
                                    String orderId, String retailerId) {
        this.shopName = shopName;
        this.mobileNo = mobileNo;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalQtys = totalQtys;
        this.amount = amount;
        this.orderId = orderId;
        this.retailerId = retailerId;
    }

    @Override
    public String toString() {
        return "OrderDeliveryStatusModel{" +
                "shopName='" + shopName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalQtys='" + totalQtys + '\'' +
                ", amount='" + amount + '\'' +
                ", orderId='" + orderId + '\'' +
                ", retailerId='" + retailerId + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalQtys() {
        return totalQtys;
    }

    public void setTotalQtys(String totalQtys) {
        this.totalQtys = totalQtys;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }
}
