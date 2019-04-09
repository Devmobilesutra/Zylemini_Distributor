package com.sapl.distributormsdpharma.MyDatabase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.distributormsdpharma.confiq.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 02/02/2018.
 */

public class TABLE_PRICELIST_DETAILS {


    public static String NAME = "PriceListDetails";
    private static final String LOG_TAG = "TABLE_PriceList_Details";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(Id INTEGER, PriceListID SMALLINT, ItemID INTEGER, DiscoutType TEXT, DiscountRate INTEGER, " +
            "SalesRate INTEGER, FromDate DATETIME, ToDate DATETIME, Remarks TEXT, CreatedDate DATETIME, CreatedBy DATETIME, UpdatedDate DATETIME, UpdatedBy DATETIME)";


    public static void insert_bulk_PriceListDetails(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_PriceListDetails->");

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();


            String insert_sql = "insert into " + NAME + " (" + "ID,PriceListID,ItemID,DiscoutType,DiscountRate,SalesRate,FromDate,ToDate,Remarks,CreatedDate,CreatedBy,UpdatedDate,UpdatedBy) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_PriceListDetails->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {


                    jsonObject = jsonArray.getJSONObject(i);

                    statement.clearBindings();


                    statement.bindString(1, jsonObject.getString("ID"));
                    statement.bindString(2, jsonObject.getString("PriceListID"));
                    statement.bindString(3, jsonObject.getString("ItemID"));
                    statement.bindString(4, jsonObject.getString("DiscoutType"));
                    statement.bindString(5, jsonObject.getString("DiscountRate"));

                    statement.bindString(6, jsonObject.getString("SalesRate"));
                    statement.bindString(7, jsonObject.getString("FromDate"));
                    statement.bindString(8, jsonObject.getString("ToDate"));
                    statement.bindString(9, jsonObject.getString("Remarks"));
                    statement.bindString(10, jsonObject.getString("CreatedDate"));

                    statement.bindString(11, jsonObject.getString("CreatedBy"));
                    statement.bindString(12, jsonObject.getString("UpdatedDate"));
                    statement.bindString(13, jsonObject.getString("UpdatedBy"));


                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime insert_bulk_PriceListDetails->");
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            } catch (JSONException e) {
                db.close();
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_PriceListDetails--->" + e.getMessage());

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_PriceListDetails exception--->" + e.getMessage());
        }
    }

}
