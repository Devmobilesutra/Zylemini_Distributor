package com.sapl.distributormsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.distributormsdpharma.confiq.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shashi on 02-Feb-18.
 */

public class TABLE_MENU_MASTER {

    public static String LOG_TAG = "TABLE_MENU_MASTER";
    public static String NAME = "MenuMaster";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(SeqNo TEXT, UserType TINYINT, MenuKey TEXT, LabelName TEXT, IsActive TEXT)";


    public static void insert_bulk_MenuMaster(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_MenuMaster->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "SeqNo,UserType,MenuKey,LabelName,IsActive) VALUES(?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString("SeqNo"));
                    statement.bindString(2, jsonObject.getString("UserType"));
                    statement.bindString(3, jsonObject.getString("MenuKey"));
                    statement.bindString(4, jsonObject.getString("LabelName"));
                    statement.bindString(5, jsonObject.getString("IsActive"));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime->");
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            } catch (JSONException e) {
                db.close();
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_MenuMaster--->" + e.getMessage());
            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_MenuMaster exception--->" + e.getMessage());
        }
    }



}
