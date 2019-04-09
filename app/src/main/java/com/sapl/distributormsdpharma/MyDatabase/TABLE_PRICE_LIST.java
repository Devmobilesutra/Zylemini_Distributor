package com.sapl.distributormsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.distributormsdpharma.confiq.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 02/02/2018.
 */

public class TABLE_PRICE_LIST {

    public static String NAME = "PriceList";
    private static final String LOG_TAG = "TABLE_Price_List";

    public static String
            COL_ID = "Id",
            COL_DISTRIBUTER_ID = "DistributorId";


    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + " ("
            + COL_ID + " INTEGER , "
            + COL_DISTRIBUTER_ID + " TEXT);";


    public static void insert_bulk_PriceList(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_PriceList->");

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " (" + "Id,DistributorID) VALUES(?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_PriceList->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {

                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString("Id"));
                    statement.bindString(2, jsonObject.getString("DistributorID"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime insert_bulk_PriceList->");
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();


            } catch (JSONException e) {
                db.close();
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_PriceList--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_PriceList exception--->" + e.getMessage());
        }
    }
    public static String getPriceListIDOfDistributor(String dist_id) {

        MyApplication.logi(LOG_TAG, "in getPriceListOfDistributor");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getPriceListOfDistributor()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from PriceList where DistributorId =" + dist_id;
        MyApplication.logi("mrunal","In getPriceListOfDistributor query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF getPriceListOfDistributor IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("Id"));
                MyApplication.logi(LOG_TAG, "RESPPPPP-->" + resp);
            } while (c.moveToNext());
        }

        return resp;

    }
}
