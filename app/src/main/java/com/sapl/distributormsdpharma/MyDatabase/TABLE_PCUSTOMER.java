package com.sapl.distributormsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.distributormsdpharma.confiq.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shashi on 02-Feb-18.
 */

public class TABLE_PCUSTOMER {
    public static String LOG_TAG = "TABLE_PCUSTOMER";
    public static String NAME = "PCustomer";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME + " (Id TEXT, " +
            "CustomerId TEXT, " +
            "UserId TEXT, " +
            "Party TEXT, " +
            "LicenceNo TEXT, " +
            "ONOFF TEXT, " +
            "ContactPerson TEXT, " +
            "GroupFirmId TEXT, " +
            "IsActive TEXT, " +
            "ERPCode TEXT, " +
            "Transfer TEXT, " +
            "DefaultDistributorId TEXT, " +
            "OnConsignmentSale TEXT, " +
            "CreationDate DATE, " +
            "VisitFrequency INTEGER, " +
            "LASTEDITDATE DATE, " +
            "RouteId INTEGER, " +
            "RouteName TEXT, " +
            "MDMID TEXT, " +
            "AREAID TEXT, " +
            "AREA TEXT, " +
            "AREAALIAS TEXT, " +
            "BRANCHID TEXT, " +
            "BRANCH TEXT, " +
            "BRANCHALIAS TEXT, " +
            "CUSTOMERCLASSID TEXT, " +
            "CUSTOMERCLASS TEXT, " +
            "CUSTOMERCLASSALIAS TEXT, " +
            "CUSTOMERCLASS2ID TEXT, " +
            "CUSTOMERCLASS2 TEXT, " +
            "CUSTOMERCLASS2ALIAS TEXT, " +
            "CUSTOMERGROUPID TEXT, " +
            "CUSTOMERGROUP TEXT, " +
            "CUSTOMERGROUPALIAS TEXT, " +
            "CUSTOMERSEGMENTID TEXT, " +
            "CUSTOMERSEGMENT TEXT, " +
            "CUSTOMERSEGMENTALIAS TEXT, " +
            "CUSTOMERSUBSEGMENTID TEXT, " +
            "CUSTOMERSUBSEGMENT TEXT, " +
            "CUSTOMERSUBSEGMENTALIAS TEXT, " +
            "LICENCETYPEID TEXT, " +
            "LICENCETYPE TEXT, " +
            "LICENCETYPEALIAS TEXT, " +
            "OCTROIZONEID TEXT, " +
            "OCTROIZONE TEXT, " +
            "OCTROIZONEALIAS TEXT, " +
            "OCTROIZONESEQUENCE TEXT, " +
            "OCTROIZONEALIASSEQUENCE TEXT, " +
            "CUSTOMERCLASSSEQUENCE TEXT, " +
            "CUSTOMERCLASSALIASSEQUENCE TEXT, " +
            "FIRMNAME TEXT, " +
            "PARTYHISTORY TEXT, "+
            "OUTLETINFO  TEXT );";


    public static void insert_bulk_PCustomer(JSONArray jsonArray) {
        try {

            MyApplication.logi(LOG_TAG, "in insert_bulk_PCustomer->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " ( "
                    + " Id,CustomerId,UserId,Party,LicenceNo,ONOFF,ContactPerson,GroupFirmId,IsActive,ERPCode,Transfer," +
                    "DefaultDistributorId,OnConsignmentSale,CreationDate,VisitFrequency,LASTEDITDATE,RouteId,RouteName," +
                    "MDMID,AREAID,AREA,AREAALIAS,BRANCHID,BRANCH,BRANCHALIAS,CUSTOMERCLASSID,CUSTOMERCLASS,CUSTOMERCLASSALIAS," +
                    "CUSTOMERCLASS2ID,CUSTOMERCLASS2,CUSTOMERCLASS2ALIAS,CUSTOMERGROUPID,CUSTOMERGROUP,CUSTOMERGROUPALIAS," +
                    "CUSTOMERSEGMENTID,CUSTOMERSEGMENT,CUSTOMERSEGMENTALIAS,CUSTOMERSUBSEGMENTID,CUSTOMERSUBSEGMENT," +
                    "CUSTOMERSUBSEGMENTALIAS,LICENCETYPEID,LICENCETYPE,LICENCETYPEALIAS,OCTROIZONEID,OCTROIZONE,OCTROIZONEALIAS," +
                    "OCTROIZONESEQUENCE,OCTROIZONEALIASSEQUENCE,CUSTOMERCLASSSEQUENCE,CUSTOMERCLASSALIASSEQUENCE,FIRMNAME," +
                    "PARTYHISTORY, OUTLETINFO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            MyApplication.logi(LOG_TAG, "insert_sql insert_bulk_PCustomer" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_PCustomer->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);
                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString("Id"));
                    statement.bindString(2, jsonObject.getString("CustomerId"));
                    statement.bindString(3, jsonObject.getString("UserId"));
                    statement.bindString(4, jsonObject.getString("Party"));
                    statement.bindString(5, jsonObject.getString("LicenceNo"));

                    statement.bindString(6, jsonObject.getString("ONOFF"));
                    statement.bindString(7, jsonObject.getString("ContactPerson"));
                    statement.bindString(8, jsonObject.getString("GroupFirmId"));
                    statement.bindString(9, jsonObject.getString("IsActive"));
                    statement.bindString(10, jsonObject.getString("ERPCode"));

                    statement.bindString(11, jsonObject.getString("Transfer"));
                    statement.bindString(12, jsonObject.getString("DefaultDistributorId"));
                    statement.bindString(13, jsonObject.getString("OnConsignmentSale"));
                    statement.bindString(14, jsonObject.getString("CreationDate"));
                    statement.bindString(15, jsonObject.getString("VisitFrequency"));

                    statement.bindString(16, jsonObject.getString("LASTEDITDATE"));
                    statement.bindString(17, jsonObject.getString("RouteId"));
                    statement.bindString(18, jsonObject.getString("RouteName"));
                    statement.bindString(19, jsonObject.getString("MDMID"));
                    statement.bindString(20, jsonObject.getString("AREAID"));

                    statement.bindString(21, jsonObject.getString("AREA"));
                    statement.bindString(22, jsonObject.getString("AREAALIAS"));
                    statement.bindString(23, jsonObject.getString("BRANCHID"));
                    statement.bindString(24, jsonObject.getString("BRANCH"));
                    statement.bindString(25, jsonObject.getString("BRANCHALIAS"));

                    statement.bindString(26, jsonObject.getString("CUSTOMERCLASSID"));
                    statement.bindString(27, jsonObject.getString("CUSTOMERCLASS"));
                    statement.bindString(28, jsonObject.getString("CUSTOMERCLASSALIAS"));
                    statement.bindString(29, jsonObject.getString("CUSTOMERCLASS2ID"));
                    statement.bindString(30, jsonObject.getString("CUSTOMERCLASS2"));

                    statement.bindString(31, jsonObject.getString("CUSTOMERCLASS2ALIAS"));
                    statement.bindString(32, jsonObject.getString("CUSTOMERGROUPID"));
                    statement.bindString(33, jsonObject.getString("CUSTOMERGROUP"));
                    statement.bindString(34, jsonObject.getString("CUSTOMERGROUPALIAS"));
                    statement.bindString(35, jsonObject.getString("CUSTOMERSEGMENTID"));

                    statement.bindString(36, jsonObject.getString("CUSTOMERSEGMENT"));
                    statement.bindString(37, jsonObject.getString("CUSTOMERSEGMENTALIAS"));
                    statement.bindString(38, jsonObject.getString("CUSTOMERSUBSEGMENTID"));
                    statement.bindString(39, jsonObject.getString("CUSTOMERSUBSEGMENT"));
                    statement.bindString(40, jsonObject.getString("CUSTOMERSUBSEGMENTALIAS"));

                    statement.bindString(41, jsonObject.getString("LICENCETYPEID"));
                    statement.bindString(42, jsonObject.getString("LICENCETYPE"));
                    statement.bindString(43, jsonObject.getString("LICENCETYPEALIAS"));
                    statement.bindString(44, jsonObject.getString("OCTROIZONEID"));
                    statement.bindString(45, jsonObject.getString("OCTROIZONE"));

                    statement.bindString(46, jsonObject.getString("OCTROIZONEALIAS"));
                    statement.bindString(47, jsonObject.getString("OCTROIZONESEQUENCE"));
                    statement.bindString(48, jsonObject.getString("OCTROIZONEALIASSEQUENCE"));
                    statement.bindString(49, jsonObject.getString("CUSTOMERCLASSSEQUENCE"));
                    statement.bindString(50, jsonObject.getString("CUSTOMERCLASSALIASSEQUENCE"));

                    statement.bindString(51, jsonObject.getString("FIRMNAME"));
                    statement.bindString(52, jsonObject.getString("PARTYHISTORY"));
                    statement.bindString(53, jsonObject.getString("OUTLETINFO"));


                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime insert_bulk_PCustomer->");
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            } catch (JSONException e) {
                db.close();
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_PCustomer--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_PCustomer exception--->" + e.getMessage());
        }
    }

    public static ArrayList<String> getShopName(String retailerId) {

        ArrayList<String> data = new ArrayList<>();
        String shopName = "", mobileNo = "";

        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getOrderStatusList()");

        String query = "SELECT * FROM " + NAME + " WHERE CustomerId = '" + retailerId + "' ";
        MyApplication.logi("getShopName() ", "In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("PCustomer Data getShopName() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            shopName = c.getString(c.getColumnIndexOrThrow("FIRMNAME"));
            mobileNo = c.getString(c.getColumnIndexOrThrow("ContactPerson"));
            data.add(shopName);
            data.add(mobileNo);
        }
        db.close();
        return data;
    }

    public static String getOutletInfo(String retId){
        String outlet_info = "";

        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, " getOutletInfo()  RETAILER_ID: ---> "+retId);

        String query = "SELECT * FROM " + NAME + " WHERE CustomerId = '" + retId + "' ";
        MyApplication.logi(LOG_TAG+" getOutletInfo() ", "In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi(LOG_TAG+" getOutletInfo() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            outlet_info = c.getString(c.getColumnIndexOrThrow("OUTLETINFO"));
        }
        db.close();
        MyApplication.logi(LOG_TAG+", getOutletInfo() INFO--> ", outlet_info);
        return outlet_info;
    }
    public static String getCustId() {
        MyApplication.logi(LOG_TAG, "IN CUCST ID");
        String custId = "";
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        String query = "SELECT  cast(CustomerId as int) as CustomerId  FROM " + NAME;
        MyApplication.logi(LOG_TAG,"querrryyyy--->"+query);
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            custId = c.getString(c.getColumnIndexOrThrow("CustomerId"));

        }
        MyApplication.logi(LOG_TAG, "IN CUCST ID custId--"+custId);
        return custId;
    }

}
