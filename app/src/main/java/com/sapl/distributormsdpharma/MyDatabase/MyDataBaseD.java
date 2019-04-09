package com.sapl.distributormsdpharma.MyDatabase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sapl.distributormsdpharma.confiq.MyApplication;

/**
 * Created by Shashi on 02-Feb-18.
 */

public class MyDataBaseD extends SQLiteOpenHelper {
    public static String LOG_TAG = "MyDataBaseD";
    public static String DATABASE_NAME = "db_chord_D.db";
    public static int DATABASE_VERSION = 1;

    public MyDataBaseD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        MyApplication.logi(LOG_TAG, " in Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MyApplication.logi(LOG_TAG, " in onCreate of database");
        try {

            db.execSQL(TABLE_MENU_MASTER.CREATE_TABLE);
            db.execSQL(TABLE_PCUSTOMER.CREATE_TABLE);
            db.execSQL(TABLE_PDISTRIBUTOR.CREATE_TABLE);
            db.execSQL(TABLE_TEMP_ORDER_DETAILS.CREATE_TABLE);
            db.execSQL(TABLE_TEMP_RETAILER_ORDER_MASTER.CREATE_TABLE);
            db.execSQL(TABLE_PITEM.CREATE_TABLE);
            db.execSQL(TABLE_PRICE_LIST.CREATE_TABLE);
            db.execSQL(TABLE_PRICELIST_DETAILS.CREATE_TABLE);
            db.execSQL(TABLE_SETTINGS.CREATE_TABLE);
            db.execSQL(TABLE_ORDER_STATUS.CREATE_TABLE);
            db.execSQL(TABLE_RETAILER_ORDER_MASTER.CREATE_TABLE);
            db.execSQL(TABLE_ORDER_DETAILS.CREATE_TABLE);
            db.execSQL(TABLE_ITEM_IMAGES.CREATE_TABLE);
            db.execSQL(TABLE_UOMMASTER.CREATE_TABLE);
            db.execSQL(TABLE_RESOURCE.CREATE_TABLE);


        } catch (SQLException e) {
            MyApplication.logi(LOG_TAG, "Exception for table creation -->" + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
