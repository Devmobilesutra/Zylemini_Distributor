package com.sapl.distributormsdpharma.models;

/**
 * Created by Ganesh Borse on 12-Feb-18.
 */

public class AproveOrderModel {

    String product_name;
    String product_img_path;
    String case_no;
    String boittle_no;
    String free_bottle_no;
    String price_of_product;
    byte[] imageBlob;

    public String getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(String statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

    String statusDateTime;


    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getColomnId() {
        return colomnId;
    }

    public void setColomnId(String colomnId) {
        this.colomnId = colomnId;
    }

    String orderId;
    String colomnId;
    String free_case_no;

    @Override
    public String toString() {
        return "AproveOrderModel{" +
                "product_name='" + product_name + '\'' +
                ", product_img_path='" + product_img_path + '\'' +
                ", case_no='" + case_no + '\'' +
                ", boittle_no='" + boittle_no + '\'' +
                ", free_bottle_no='" + free_bottle_no + '\'' +
                ", price_of_product='" + price_of_product + '\'' +
                ", orderId='" + orderId + '\'' +
                ", colomnId='" + colomnId + '\'' +
                ", free_case_no='" + free_case_no + '\'' +
                '}';
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img_path() {
        return product_img_path;
    }

    public void setProduct_img_path(String product_img_path) {
        this.product_img_path = product_img_path;
    }

    public String getCase_no() {
        return case_no;
    }

    public void setCase_no(String case_no) {
        this.case_no = case_no;
    }

    public String getBoittle_no() {
        return boittle_no;
    }

    public void setBoittle_no(String boittle_no) {
        this.boittle_no = boittle_no;
    }

    public String getFree_case_no() {
        return free_case_no;
    }

    public void setFree_case_no(String free_case_no) {
        this.free_case_no = free_case_no;
    }

    public String getFree_bottle_no() {
        return free_bottle_no;
    }

    public void setFree_bottle_no(String free_bottle_no) {
        this.free_bottle_no = free_bottle_no;
    }

    public String getPrice_of_product() {
        return price_of_product;
    }

    public void setPrice_of_product(String price_of_product) {
        this.price_of_product = price_of_product;
    }


    public AproveOrderModel(String product_name, String product_img_path, String case_no, String boittle_no, String free_case_no, String free_bottle_no, String price_of_product) {
        this.product_name = product_name;
        this.product_img_path = product_img_path;
        this.case_no = case_no;
        this.boittle_no = boittle_no;
        this.free_case_no = free_case_no;
        this.free_bottle_no = free_bottle_no;
        this.price_of_product = price_of_product;
    }


    public AproveOrderModel(String product_name, String product_img_path, String case_no, String boittle_no,
                            String free_case_no, String free_bottle_no, String price_of_product,
                            String colomnId, String orderId, byte[] imageBlob, String statusDateTime) {
        this.product_name = product_name;
        this.product_img_path = product_img_path;
        this.case_no = case_no;
        this.boittle_no = boittle_no;
        this.free_case_no = free_case_no;
        this.free_bottle_no = free_bottle_no;
        this.price_of_product = price_of_product;
        this.colomnId = colomnId;
        this.orderId = orderId;
        this.imageBlob = imageBlob;
        this.statusDateTime = statusDateTime;
    }
}
