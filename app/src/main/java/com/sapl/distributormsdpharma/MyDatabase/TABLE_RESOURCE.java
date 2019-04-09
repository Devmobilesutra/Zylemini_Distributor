package com.sapl.distributormsdpharma.MyDatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.models.ResourceDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TABLE_RESOURCE {

    public static String NAME = "resources";
    private static final String LOG_TAG = "TABLE_RESOURCE ";
    public static String
            COL_ID = "ID",
            COL_RESOURCE_NAME = "ResourceName",
            COL_PARENT_RESOURCE_ID = "ParentResourceID",
            COL_URL = "URL",
            COL_DESCRIPTON = "Descreption",
            COL_FILENAME = "FileName",
            COL_SEQUENCE_NO = "SequenceNo",
            COL_IS_DOWNLOADABLE = "IsDownloadable",
            COL_RESOURCE_TYPE = "ResourceType",
            COL_CREATED_DATE = "CreatedDate",
            COL_LAST_UPDATED_DATE = "LastUpdatedDate";


    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_RESOURCE_NAME + " TEXT, "
            + COL_PARENT_RESOURCE_ID + " TEXT, "
            + COL_URL + " TEXT, "
            + COL_DESCRIPTON + " TEXT, "
            + COL_FILENAME + " TEXT, "
            + COL_SEQUENCE_NO + " TEXT, "
            + COL_IS_DOWNLOADABLE + " TEXT, "
            + COL_RESOURCE_TYPE + " TEXT, "
            + COL_CREATED_DATE + " TEXT, "
            + COL_LAST_UPDATED_DATE + " TEXT);";


    public static void insertBulkResources(JSONArray jsonArray) {
        try {
            int len = jsonArray.length();

            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.delete(NAME, null, null);
            db.beginTransaction();

            String insert_sql = "INSERT INTO " + NAME + " ( "
                    + COL_ID + ", "
                    + COL_RESOURCE_NAME + ", "
                    + COL_PARENT_RESOURCE_ID + ", "
                    + COL_URL + ", "
                    + COL_DESCRIPTON + ", "
                    + COL_FILENAME + " , "
                    + COL_SEQUENCE_NO + " , "
                    + COL_IS_DOWNLOADABLE + " , "
                    + COL_RESOURCE_TYPE + " , "
                    + COL_CREATED_DATE + " , "
                    + COL_LAST_UPDATED_DATE +
                    ") VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG , "bulkResources(),  ArrayLen->" + count_array);

            for (int i = 0; i < count_array; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString(COL_ID);
                String resourceName = jsonObject.getString(COL_RESOURCE_NAME);
                String parentResourceId = jsonObject.getString(COL_PARENT_RESOURCE_ID);
                String url = jsonObject.getString(COL_URL);
                String description = jsonObject.getString(COL_DESCRIPTON);
                String fileName = jsonObject.getString(COL_FILENAME);
                String sequenceNo = jsonObject.getString(COL_SEQUENCE_NO);
                String isDownloadable = jsonObject.getString(COL_IS_DOWNLOADABLE);
                String resourceType = jsonObject.getString(COL_RESOURCE_TYPE);
                String createdDate = jsonObject.getString(COL_CREATED_DATE);
                String lastUpdate = jsonObject.getString(COL_LAST_UPDATED_DATE);

                statement.clearBindings();
                statement.bindString(1, id);
                statement.bindString(2, resourceName);
                statement.bindString(3, parentResourceId);
                statement.bindString(4, url);
                statement.bindString(5, description);
                statement.bindString(6, fileName);
                statement.bindString(7, sequenceNo);
                statement.bindString(8, isDownloadable);
                statement.bindString(9, resourceType);
                statement.bindString(10, createdDate);
                statement.bindString(11, lastUpdate);

                statement.execute();
            }
            MyApplication.logi(LOG_TAG , "bulkResources(), EndTime->");
            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (JSONException e) {
            MyApplication.logi(LOG_TAG, "bulkResources() Exception ERROR: " + e.getMessage());
        }
    }


    public static ArrayList<HashMap<String, String>> getReport(String startDateQueryDate, String endDateQueryDate) {
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
        //String startDateQueryDate="01-Apr-2016",endDateQueryDate="31-Apr-2016";

        HashMap<String, String> map = new HashMap<String, String>();


        //yes   String querytogetuserdata = "Select Executive,Target_Points,TotalPoints,Bucket,TargetAcheived,TeamAcheived,FromDate,ToDate from SIPREPORT where UserId=33226 and FromDate ='01-Apr-2016'";
        //  String querytogetuserdata="Select  Executive,sum(Target_Points) as t_points ,sum(TotalPoints)  as total_points ,Bucket,TargetAcheived,TeamAcheived,FromDate,ToDate from SIPREPORT where FromDate BETWEEN '" + startDateQueryDate + "' AND '" + endDateQueryDate + "'";
        String querytogetuserdata = "";
        MyApplication.logi(LOG_TAG, "QUERY1->  " + querytogetuserdata);
        Cursor c = db.rawQuery(querytogetuserdata, null);
        int count = c.getCount();
        MyApplication.logi(LOG_TAG, "count-> " + count + "");
        if (count > 0) {
            c.moveToFirst();
            do {
                // String executive = c.getString(c.getColumnIndexOrThrow(COL_Executive));
                String querytogetuserdata1 = "";
                MyApplication.logi(LOG_TAG, "QUERY2->" + querytogetuserdata1);
                Cursor c1 = db.rawQuery(querytogetuserdata1, null);
                int count1 = c1.getCount();
                MyApplication.logi(LOG_TAG, "count1->" + count1 + "");
                if (count1 > 0) {
                    c1.moveToFirst();
                    do {
                        map = new HashMap<String, String>();
                        map.put("executive", c1.getString(c1.getColumnIndexOrThrow(COL_RESOURCE_NAME)));
                        res.add(map);
                    } while (c1.moveToNext());
                }
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return res;
    }


    public static ArrayList<ResourceDataModel> getResourceParentTab() {
        ArrayList<ResourceDataModel> resArray = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        String query = " SELECT * FROM " + NAME + " WHERE " + COL_PARENT_RESOURCE_ID + " ='" + null + "' ";
        Cursor c = db.rawQuery(query, null);
        int count = c.getCount();
        MyApplication.logi(LOG_TAG, " getResourceParentTab(), QUERY1->  " + query + ", count-> " + count);

        MyApplication.logi(LOG_TAG, " getResourceParentTab(), count-> " + count + "");
        if (count > 0) {
            ResourceDataModel resModel;
            String id = "", resourceName = "", parentResourceID = "", URL = "", descreption = "",
                    fileName = "", sequenceNo = "", isDownloadable = "", resourceType = "",
                    createdDate = "", lastUpdatedDate = "";
            c.moveToFirst();
            do {
                id = c.getString(c.getColumnIndexOrThrow(COL_ID));
                resourceName = c.getString(c.getColumnIndexOrThrow(COL_RESOURCE_NAME));
                parentResourceID = c.getString(c.getColumnIndexOrThrow(COL_PARENT_RESOURCE_ID));
                URL = c.getString(c.getColumnIndexOrThrow(COL_URL));
                descreption = c.getString(c.getColumnIndexOrThrow(COL_DESCRIPTON));
                fileName = c.getString(c.getColumnIndexOrThrow(COL_FILENAME));
                sequenceNo = c.getString(c.getColumnIndexOrThrow(COL_SEQUENCE_NO));
                isDownloadable = c.getString(c.getColumnIndexOrThrow(COL_IS_DOWNLOADABLE));
                resourceType = c.getString(c.getColumnIndexOrThrow(COL_RESOURCE_TYPE));
                createdDate = c.getString(c.getColumnIndexOrThrow(COL_CREATED_DATE));
                lastUpdatedDate = c.getString(c.getColumnIndexOrThrow(COL_LAST_UPDATED_DATE));
                resModel = new ResourceDataModel(id, resourceName, parentResourceID, URL, descreption,
                        fileName, sequenceNo, isDownloadable, resourceType,
                        createdDate, lastUpdatedDate);
                resArray.add(resModel);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return resArray;
    }


    public static ArrayList<ResourceDataModel> getResourceChildrenData(String parentResourceId) {
        ArrayList<ResourceDataModel> resArray = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        String query = " SELECT * FROM " + NAME + " WHERE " + COL_PARENT_RESOURCE_ID
                + " ='" + parentResourceId + "' ORDER BY " + COL_SEQUENCE_NO;
        Cursor c = db.rawQuery(query, null);
        int count = c.getCount();
        MyApplication.logi(LOG_TAG, " getResourceParentTab(), QUERY1->  " + query + ", count-> " + count);

        MyApplication.logi(LOG_TAG, " getResourceParentTab(), count-> " + count + "");
        if (count > 0) {
            ResourceDataModel resModel;
            String id = "", resourceName = "", parentResourceID = "", URL = "", descreption = "",
                    fileName = "", sequenceNo = "", isDownloadable = "", resourceType = "",
                    createdDate = "", lastUpdatedDate = "";
            c.moveToFirst();
            do {
                id = c.getString(c.getColumnIndexOrThrow(COL_ID));
                resourceName = c.getString(c.getColumnIndexOrThrow(COL_RESOURCE_NAME));
                parentResourceID = c.getString(c.getColumnIndexOrThrow(COL_PARENT_RESOURCE_ID));
                URL = c.getString(c.getColumnIndexOrThrow(COL_URL));
                descreption = c.getString(c.getColumnIndexOrThrow(COL_DESCRIPTON));
                fileName = c.getString(c.getColumnIndexOrThrow(COL_FILENAME));
                sequenceNo = c.getString(c.getColumnIndexOrThrow(COL_SEQUENCE_NO));
                isDownloadable = c.getString(c.getColumnIndexOrThrow(COL_IS_DOWNLOADABLE));
                resourceType = c.getString(c.getColumnIndexOrThrow(COL_RESOURCE_TYPE));
                createdDate = c.getString(c.getColumnIndexOrThrow(COL_CREATED_DATE));
                lastUpdatedDate = c.getString(c.getColumnIndexOrThrow(COL_LAST_UPDATED_DATE));
                resModel = new ResourceDataModel(id, resourceName, parentResourceID, URL, descreption,
                        fileName, sequenceNo, isDownloadable, resourceType,
                        createdDate, lastUpdatedDate);

                resArray.add(resModel);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return resArray;
    }

}