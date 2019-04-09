package com.sapl.distributormsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.distributormsdpharma.confiq.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by Sony on 02/02/2018.
 */

public class TABLE_SETTINGS {
    public static String NAME = "Settings";
    private static final String LOG_TAG = "TABLE_Settings";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(KeyID TEXT,KeyValue TEXT)";


    public static void insert_bulk_Settings(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_Settings->");

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " (" + "KeyID,KeyValue) VALUES(?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);

            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_Settings->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();


                    statement.bindString(1, jsonObject.getString("KeyID"));
                    statement.bindString(2, jsonObject.getString("KeyValue"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime insert_bulk_Settings->");
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            } catch (JSONException e) {
                db.close();
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_Settings--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_Settings exception--->" + e.getMessage());
        }
    }




    public static boolean tableHasData() {
        int num = -1;

        MyApplication.logi(LOG_TAG, "in tableHasData()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from Settings";
        MyApplication.logi("mk", "In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        num = c.getCount();
        MyApplication.logi("mk", "tableHasData m is count : " + num);
        if (num > 0) {
            MyApplication.logi("mk", "data is there : " + num);
            c.close();
            db.close();
            return true;
        } else {
            MyApplication.logi("mk", "NOO data  : " + num);
            c.close();
            db.close();
            return false;
        }
    }
    public static String getItemFilterValues() {
        MyApplication.logi(LOG_TAG, "in getItemFilterValues");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getItemFilterValues()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from Settings where KeyID = 'ITEMFILTER'";
        MyApplication.logi("mrunal","In getItemFilterValues query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF FILTER IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("KeyValue"));
                MyApplication.logi(LOG_TAG, "DATA IS-->" + resp);
            } while (c.moveToNext());

        }
        return resp;


    }



    public static String get_value_from_setting(String key) {
        MyApplication.logi(LOG_TAG, "get_value_from_setting " + key);
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();

        String resp = "";
        String querytogetuserdata = "SELECT * FROM " + NAME
                + " where KeyID ='" + key + "' limit 1";

        Cursor c = db.rawQuery(querytogetuserdata, null);
        int count = c.getCount();
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("KeyValue"));

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        MyApplication.logi(LOG_TAG, "res " + resp);
        return resp;
    }

    public static String getUomOrderBookCal() {
        MyApplication.logi(LOG_TAG, "in getUomOrderBookCal");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getUomOrderBookCal()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from Settings where KeyID = 'ORDBOOKCAL'";
        MyApplication.logi(LOG_TAG,"In getUomOrderBookCal query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "getUomOrderBookCal COUNT OF FILTER IS-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("KeyValue"));
                MyApplication.logi(LOG_TAG, "ORDBOOKCAL DATA IS-->" + resp);


                String[] split = resp.split(Pattern.quote("/"));
                String uom_val = split[0];
                MyApplication.set_session(MyApplication.SESSION_UOM_ORDER_1, uom_val);
                String uom_val_2 = split[1];
                MyApplication.set_session(MyApplication.SESSION_UOM_ORDER_2, uom_val_2);
                MyApplication.logi(LOG_TAG, "ORDBOOKCAL upm val 1-->" + uom_val + "ORDBOOKCAL uom val2  ->" + uom_val_2);

            } while (c.moveToNext());

        }
        return resp;


    }




    public static String getUomLabels() {
        MyApplication.logi(LOG_TAG, "in getUomLabels");
        int count;
        String resp = "";
        MyApplication.logi(LOG_TAG, "in getUomLabels()");
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        String query = "SELECT * from Settings where KeyID = 'ORDBOOKUOMLABEL'";
        MyApplication.logi(LOG_TAG,"In getUomLabels query :" + query);
        Cursor c = db.rawQuery(query, null);
        count = c.getCount();
        MyApplication.logi(LOG_TAG, "COUNT OF FILTER IS getUomLabels-->" + count);
        if (count > 0) {
            c.moveToFirst();
            do {
                resp = c.getString(c.getColumnIndexOrThrow("KeyValue"));

                String[] split = resp.split(Pattern.quote("/"));
                String uom_val1 = split[0];
                MyApplication.set_session(MyApplication.SESSION_UOM_VALUE_FIRST, uom_val1);
                String uom_val2 = split[1];
                MyApplication.set_session(MyApplication.SESSION_UOM_VALUE_SECOND, uom_val2);
                MyApplication.logi(LOG_TAG, "upm val 1-->" + uom_val1 + "uom val2  ->" + uom_val2);

                MyApplication.logi(LOG_TAG, "getUomLabels DATA IS-->" + resp);
            } while (c.moveToNext());

        }
        return resp;


    }
}
