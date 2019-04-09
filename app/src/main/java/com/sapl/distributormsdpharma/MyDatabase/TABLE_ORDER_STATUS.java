package com.sapl.distributormsdpharma.MyDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.distributormsdpharma.confiq.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by MRUNAL on 08/02/2018.
 */

public class TABLE_ORDER_STATUS {
    public static String LOG_TAG = "TABLE_ORDER_STATUS";
    public static String NAME = "OrderStatus";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " RetailerOrderMasterID TEXT, " +
            " StatusID INTEGER, " +
            " StatusDateTime DATE, " +
            " Remarks TEXT )";


    public static void updateStatus(String appOrderid, String orderStatus) {
        MyApplication.logi(LOG_TAG, " updateStatus() appOrderid()-->" + appOrderid);

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        //String sql_get_count = "select count(OrderStatus) as count from " + TABLE_RETAILER_ORDER_MASTER.NAME + " where OrderStatus ='1'";
        ///MyApplication.logi(LOG_TAG, "in sql_get_count()" + sql_get_count);
        ContentValues cv = new ContentValues();
        cv.put("StatusID", orderStatus);
        long ret = db.update(TABLE_ORDER_STATUS.NAME, cv, "RetailerOrderMasterID=?", new String[]{appOrderid});
        db.close();
        MyApplication.logi(LOG_TAG, "updateStatus() RESULT    -->" + ret);
    }

    public static void insert_bulk_OrderStatus(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_OrderStatus->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " ( RetailerOrderMasterID,StatusID,StatusDateTime,Remarks)" +
                    " VALUES(?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql insert_bulk_OrderStatus" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_OrderStatus->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();
                   // statement.bindString(1, jsonObject.getString("ID"));
                    statement.bindString(1, jsonObject.getString("OrderId"));
                    statement.bindString(2, jsonObject.getString("StatusID"));
                    statement.bindString(3, jsonObject.getString("StatusDateTime"));
                    statement.bindString(4, jsonObject.getString("Remarks"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime insert_bulk_OrderStatus->");
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_OrderStatus--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_OrderStatus exception--->" + e.getMessage());
        }
    }

    public static int getPendingOrderCount() {
        MyApplication.logi(LOG_TAG, "in getPendingOrderCount()");

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        String sql_get_count = "select count(StatusID) as count from " + TABLE_ORDER_STATUS.NAME + " where StatusID ='1'";
        MyApplication.logi(LOG_TAG, "in sql_get_count()" + sql_get_count);
        int ret_count = 0;
        Cursor c = db.rawQuery(sql_get_count, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            ret_count = c.getInt(c.getColumnIndexOrThrow("count"));
        }
        db.close();
        MyApplication.logi(LOG_TAG, "ret_count" + ret_count);
        return ret_count;
    }

    public static String getStatusDate(String orderId){
        MyApplication.logi(LOG_TAG, "in getStatusDate()");

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        String sql_get_count = " select StatusDateTime from " + TABLE_ORDER_STATUS.NAME +
                " where RetailerOrderMasterID = '"+orderId+"' ";
        MyApplication.logi(LOG_TAG, "in getStatusDate sql_get_count()" + sql_get_count);
        String statusDate = "1"; //PENDING MEANS NOT APPROVAL YET // NEW ORDER
        Cursor c = db.rawQuery(sql_get_count, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            statusDate = c.getString(c.getColumnIndexOrThrow("StatusDateTime"));
            //statusDate = statusDate.substring(0, 10);  // JUST A DATE
            statusDate = statusDate.replace("T"," ");
        }
        c.close();
        db.close();
        MyApplication.logi(LOG_TAG, " getStatusDate: statusDate" + statusDate);
        return statusDate;
    }

    public static int getStatusID(String orderId){

        MyApplication.logi("\n.\n"+LOG_TAG, "in getStatusDate()");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        String sql_get_count = "select StatusID from " + TABLE_ORDER_STATUS.NAME +
                " where RetailerOrderMasterID = '"+orderId+"' ";
        MyApplication.logi(LOG_TAG, "in getStatusDate sql_get_count()" + sql_get_count);
        int StatusID = 0;
        Cursor c = db.rawQuery(sql_get_count, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            StatusID = c.getInt(c.getColumnIndexOrThrow("StatusID"));
        }
        c.close();
        db.close();
        MyApplication.logi("\n.\n"+LOG_TAG, " getStatusID() : StatusID--> " + StatusID);
        return StatusID;
    }

    public static void dropTable(){
        MyApplication.logi(LOG_TAG, "in dropTable() ");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        db.execSQL("DELETE FROM "+ NAME );
        db.close();
    }

    public static  HashMap<String, String> getDeliveryStatusCount(){
        MyApplication.logi(LOG_TAG, "in getDeliveryStatusCount()");

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        int dichpatchCount = 0, deliveredCount=0, rejectedCount=0;

        String dichpatchCountUrl = " SELECT count(StatusID) as pendingCount FROM " + TABLE_ORDER_STATUS.NAME +
                " where StatusID = '2' ";
        Cursor c = db.rawQuery(dichpatchCountUrl, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            dichpatchCount = c.getInt(c.getColumnIndexOrThrow("pendingCount"));
        }

        String rejectedCountUrl = " SELECT count(StatusID) as rejectedCount  FROM  " + TABLE_ORDER_STATUS.NAME +
                " where StatusID = '3' ";
        c = db.rawQuery(rejectedCountUrl, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            rejectedCount = c.getInt(c.getColumnIndexOrThrow("rejectedCount"));
        }

        String deliveredCountUrl = " SELECT count(StatusID) as deliveredCount  FROM " + TABLE_ORDER_STATUS.NAME +
                " where StatusID = '4' ";
        c = db.rawQuery(deliveredCountUrl, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            deliveredCount = c.getInt(c.getColumnIndexOrThrow("deliveredCount"));
        }

        c.close();
        db.close();
        MyApplication.logi(LOG_TAG, " getDeliveryStatusCount() : dichpatchCount" + dichpatchCount+",   rejectedCount: "+rejectedCount);
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("dichpatchCount", dichpatchCount+"");
        hmap.put("rejectedCount", rejectedCount+"");
        hmap.put("deliveredCount", deliveredCount+"");
        return hmap;
    }

    public static int insertDataBeforeSync(String orderId,
                                           String orderDate,
                                           String orderStatus,
                                           String orderRemarks ) {

        MyApplication.logi(LOG_TAG, "insertDataBeforeSync STATUS");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues initialValues1 = new ContentValues();

        initialValues1.put("RetailerOrderMasterID", orderId);
        initialValues1.put("StatusDateTime", orderDate);
        initialValues1.put("StatusID", orderStatus);
        initialValues1.put("Remarks", orderRemarks);

        MyApplication.logi(LOG_TAG, "insertDataBeforeSync() ->" + initialValues1);


        long ret = db.insert(NAME, null, initialValues1);
        MyApplication.logi(LOG_TAG, "insertDataBeforeSync() ret->" + ret);
        if (ret > 0) {
            MyApplication.logi(LOG_TAG, "insertDataBeforeSync(), Successfull ret ->" + ret);
            return 0;
        } else {
            MyApplication.logi(LOG_TAG, "insertDataBeforeSync(), Not successfiull ret->" + ret);
            return 1;
        }

    }
}
