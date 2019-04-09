package com.sapl.distributormsdpharma.MyDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.models.AproveOrderModel;
import com.sapl.distributormsdpharma.models.OrderDeliveryStatusModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MRUNAL on 08/02/2018.
 */

public class TABLE_RETAILER_ORDER_MASTER {
    public static String LOG_TAG = "TABLE_RETAILER_ORDER_MASTER";
    public static String NAME = "RetailerOrderMaster";

    public static String
            COL_ID = "ID",
            COL_RETAILER_ID = "RetailerId",
            COL_ORDER_ID = "OrderId",
            COL_ORDER_DATE = "OrderDate",
            COL_ORDER_STATUS = "OrderStatus",
            COL_ORDER_STATUS1 = "OrderStatus1",
            COL_ORDER_REMARKS = "OrderRemarks",
            COL_AMOUNT = "TotalAmount";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_RETAILER_ID + " TEXT, "
            + COL_ORDER_ID + " TEXT, "
            + COL_ORDER_DATE + " TEXT, "
            + COL_ORDER_STATUS + " TEXT, "
            + COL_AMOUNT + " TEXT, "
            + COL_ORDER_STATUS1 + " TEXT, "
            + COL_ORDER_REMARKS + " TEXT ) ";


    public static void insertOrderMaster(JSONArray orderDetailsArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insertOrderDetails-->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " ( "
                    // + COL_ID + ", "
                    + COL_RETAILER_ID + ", "
                    + COL_ORDER_ID + ", "
                    + COL_ORDER_DATE + ", "
                    + COL_ORDER_STATUS + ", "
                    + COL_AMOUNT + ", "
                    //+ COL_ORDER_STATUS1 + ", "
                    + COL_ORDER_REMARKS + ") VALUES(?,?,?,?,?,?)";

            MyApplication.logi(LOG_TAG, "insert_sql insertOrderDetails() " + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = orderDetailsArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {
                    jsonObject = orderDetailsArray.getJSONObject(i);
                    MyApplication.logi(LOG_TAG, "\n insertOrderMaster(), orderDetailsObject  ->" + jsonObject);
                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString(COL_RETAILER_ID));
                    statement.bindString(2, jsonObject.getString(COL_ORDER_ID));
                    statement.bindString(3, jsonObject.getString(COL_ORDER_DATE));
                    statement.bindString(4, jsonObject.getString(COL_ORDER_STATUS));
                    statement.bindString(5, jsonObject.getString(COL_AMOUNT));
                    //statement.bindString(6, jsonObject.getString(COL_ORDER_STATUS1));
                    statement.bindString(6, jsonObject.getString(COL_ORDER_REMARKS));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "insertOrderMaster() EndTime->");
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "insertOrderMaster() JSONException --->" + e.getMessage());
            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "insertOrderMaster() insertOrderDetailsexception--->" + e.getMessage());
        }
    }


    public static List<OrderDeliveryStatusModel> getNewOrderList() {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getNewOrderList()");

        // HERE filterColomn may be ="orderID" OR "shop_name" OR "CALENADER" ,, OR " shop & calender "
        String query;
        // ONLY FOR NEW ORDERS
        query = "SELECT count(d.ItemId) as count,  p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                " p.CustomerId as custId, o.* " +
                //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                //  TABLE_ORDER_STATUS.NAME +" as OS "+
                " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int)" +
                //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                "  and o.orderId = d.OrderId ORDER BY o.orderId ";

        //String query = "SELECT * FROM " + NAME;
        MyApplication.logi("getNewOrderList() ", "In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getNewOrderList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
               /* String retailerId = c.getString(c.getColumnIndexOrThrow(COL_RETAILER_ID));
                String shopName = "", mobNo = "";   // = retailerId+" Shop Name";
                ArrayList<String> data = TABLE_PCUSTOMER.getShopName(retailerId);
                if (data.size() > 0 && data != null) {
                    shopName = data.get(0) + "";
                    mobNo = data.get(1) + "";
                }*/

                String shopName = c.getString(c.getColumnIndexOrThrow("shopName"));
                String mobNo = c.getString(c.getColumnIndexOrThrow("mobNo"));
                String retId = c.getString(c.getColumnIndexOrThrow("custId"));

                //String totalQtys = "01"; //
                String totalQtys = c.getString(c.getColumnIndexOrThrow("count"));
                MyApplication.logi("getNewOrderList() ", "totalQtys:  " + totalQtys);
                String orderDate = c.getString(c.getColumnIndexOrThrow(COL_ORDER_DATE));
                orderDate = orderDate.substring(0, 10);
                String orderStatus = c.getString(c.getColumnIndexOrThrow(COL_ORDER_STATUS));

                /* String statusDate = c.getString(c.getColumnIndexOrThrow("statusDate"));
                statusDate = statusDate.substring(0, 10);
                 String orderStatus = c.getString(c.getColumnIndexOrThrow("statusID"));*/
                String amount = c.getString(c.getColumnIndexOrThrow(COL_AMOUNT));
                String orderId = c.getString(c.getColumnIndexOrThrow(COL_ORDER_ID));

                String statusDate = TABLE_ORDER_STATUS.getStatusDate(orderId);
                int statusSttusID = TABLE_ORDER_STATUS.getStatusID(orderId);

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(shopName, mobNo, statusDate,
                        statusSttusID + "", totalQtys, amount, orderId, retId);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        MyApplication.logi(LOG_TAG, " getNewOrderList() model is -->>" + orderStatusList.toString());
        return orderStatusList;
    }


    public static List<OrderDeliveryStatusModel> getOrderStatusList(String statusId, String filterColomn) {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getOrderStatusList()");

        String query = "";
        if (statusId.equals("*")) {  // ALL ORDERS
            if (filterColomn.equals("shop")) {
                query = "SELECT count(d.ItemId) as count, p.FIRMNAME as shopName,  p.ContactPerson as mobNo, " +
                        " p.CustomerId as custId, o.* " +
                        //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                        " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                        //  TABLE_ORDER_STATUS.NAME +" as OS "+
                        " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int)" +
                        //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                        "  and o.orderId = d.OrderId GROUP BY o.OrderId  ORDER BY p.FIRMNAME  ";

            } else if (filterColomn.equals("calender")) {
                query = "SELECT count(d.ItemId) as count, p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                        " p.CustomerId as custId, o.* " +
                        //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                        " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                        //  TABLE_ORDER_STATUS.NAME +" as OS "+
                        " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int)" +
                        //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                        "  and o.orderId = d.OrderId GROUP BY o.OrderId  ORDER BY o.OrderDate DESC ";
            } else {
                query = "SELECT count(d.ItemId) as count, p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                        " p.CustomerId as custId, o.* " +
                        //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                        " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                        //  TABLE_ORDER_STATUS.NAME +" as OS "+
                        " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int)" +
                        //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                        "  and o.orderId = d.OrderId GROUP BY o.OrderId  ORDER BY o.OrderDate DESC, p.FIRMNAME ";
            }
        } else {
            if (filterColomn.equals("shop")) {
                query = "SELECT count(d.ItemId) as count,  p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                        " p.CustomerId as custId, o.*, " +
                        " OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                        " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p, " + TABLE_ORDER_DETAILS.NAME + " as d,"
                        + TABLE_ORDER_STATUS.NAME + " as OS " +
                        " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int)" +
                        " and o." + COL_ORDER_ID + " = OS.RetailerOrderMasterID " +
                        " and o.OrderStatus = '" + statusId + "' and o.orderId = d.OrderId " +
                        " GROUP BY o.OrderId  ORDER BY p.FIRMNAME  ";
            } else if (filterColomn.equals("calender")) {
                query = "SELECT count(d.ItemId) as count,  p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                        " p.CustomerId as custId, o.*, " +
                        " OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                        " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p, " + TABLE_ORDER_DETAILS.NAME + " as d, " +
                        TABLE_ORDER_STATUS.NAME + " as OS " +
                        " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int)" +
                        " and o." + COL_ORDER_ID + " = OS.RetailerOrderMasterID " +
                        " and o.OrderStatus = '" + statusId + "' and o.orderId = d.OrderId " +
                        " GROUP BY o.OrderId  ORDER BY OS.StatusDateTime DESC ";
            } else {
                query = "SELECT count(d.ItemId) as count,  p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                        " p.CustomerId as custId, o.*, " +
                        " OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                        " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p, " + TABLE_ORDER_DETAILS.NAME + " as d, " +
                        TABLE_ORDER_STATUS.NAME + " as OS " +
                        " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int)" +
                        " and o." + COL_ORDER_ID + " = OS.RetailerOrderMasterID " +
                        " and o.OrderStatus = '" + statusId + "' and o.orderId = d.OrderId " +
                        " GROUP BY o.OrderId  ORDER BY OS.StatusDateTime DESC, p.FIRMNAME ";
            }
        }
        //String query = "SELECT * FROM " + NAME;
        MyApplication.logi("getOrderStatusList() ", "In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getOrderStatusList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
               /* String retailerId = c.getString(c.getColumnIndexOrThrow(COL_RETAILER_ID));
                String shopName = "", mobNo = "";   // = retailerId+" Shop Name";
                ArrayList<String> data = TABLE_PCUSTOMER.getShopName(retailerId);
                if (data.size() > 0 && data != null) {
                    shopName = data.get(0) + "";
                    mobNo = data.get(1) + "";
                }*/

                String shopName = c.getString(c.getColumnIndexOrThrow("shopName"));
                String mobNo = c.getString(c.getColumnIndexOrThrow("mobNo"));
                String retId = c.getString(c.getColumnIndexOrThrow("custId"));

                //String totalQtys = "01"; //
                String totalQtys = c.getString(c.getColumnIndexOrThrow("count"));
                MyApplication.logi("getOrderStatusList() ", "totalQtys:  " + totalQtys);
                String orderDate = c.getString(c.getColumnIndexOrThrow(COL_ORDER_DATE));
                orderDate = orderDate.substring(0, 10);
                String orderStatus = c.getString(c.getColumnIndexOrThrow(COL_ORDER_STATUS));

                /* String statusDate = c.getString(c.getColumnIndexOrThrow("statusDate"));
                statusDate = statusDate.substring(0, 10);
                 String orderStatus = c.getString(c.getColumnIndexOrThrow("statusID"));*/
                String amount = c.getString(c.getColumnIndexOrThrow(COL_AMOUNT));
                String orderId = c.getString(c.getColumnIndexOrThrow(COL_ORDER_ID));

                String statusDate = "";
                int statusSttusID = 0;
                if (statusId.equals("*")) {
                    // statusDate = TABLE_ORDER_STATUS.getStatusDate(orderId);
                    // statusSttusID = TABLE_ORDER_STATUS.getStatusID(orderId);
                    statusSttusID = c.getInt(c.getColumnIndexOrThrow(COL_ORDER_STATUS));
                    statusDate = c.getString(c.getColumnIndexOrThrow(COL_ORDER_DATE));
                    statusDate = statusDate.replace("T", " ");

                } else {
                    statusDate = c.getString(c.getColumnIndexOrThrow("statusDate"));
                    statusDate = statusDate.replace("T", " ");
                    statusSttusID = c.getInt(c.getColumnIndexOrThrow("statusID"));
                }

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(shopName, mobNo, statusDate,
                        statusSttusID + "", totalQtys, amount, orderId, retId);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        MyApplication.logi("JARVIS", " model is -->>" + orderStatusList.toString());
        return orderStatusList;
    }


    public static List<OrderDeliveryStatusModel> showStatusForOrders() {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in showStatusForOrders()");

       /*String query1 = " SELECT count(d.ItemId) as count,  p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                       " p.CustomerId as custId, o.* " +
                " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int) and o.OrderStatus = '1'  and o.orderId = d.OrderId group by o.orderId";*/

        String query = "SELECT count(d.ItemId) as count,  p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                " p.CustomerId as custId, o.* " +
                //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                //  TABLE_ORDER_STATUS.NAME +" as OS "+
                " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int)" +
                //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                //" and o.OrderStatus = '1'  and o.orderId = d.OrderId group by o.orderId";
                " and  o.orderId = d.OrderId group by o.orderId";

        //String query = "SELECT * FROM " + NAME;
        MyApplication.logi("showStatusForOrders() ", "In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("showStatusForOrders() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
               /* String retailerId = c.getString(c.getColumnIndexOrThrow(COL_RETAILER_ID));
                String shopName = "", mobNo = "";   // = retailerId+" Shop Name";
                ArrayList<String> data = TABLE_PCUSTOMER.getShopName(retailerId);
                if (data.size() > 0 && data != null) {
                    shopName = data.get(0) + "";
                    mobNo = data.get(1) + "";
                }*/

                String shopName = c.getString(c.getColumnIndexOrThrow("shopName"));
                String mobNo = c.getString(c.getColumnIndexOrThrow("mobNo"));
                String retId = c.getString(c.getColumnIndexOrThrow("custId"));

                //String totalQtys = "01"; //
                String totalQtys = c.getString(c.getColumnIndexOrThrow("count"));
                MyApplication.logi("getOrderStatusList() ", "totalQtys:  " + totalQtys);
                String orderDate = c.getString(c.getColumnIndexOrThrow(COL_ORDER_DATE));
                orderDate = orderDate.substring(0, 10);
                String orderStatus = c.getString(c.getColumnIndexOrThrow(COL_ORDER_STATUS));

                /* String statusDate = c.getString(c.getColumnIndexOrThrow("statusDate"));
                statusDate = statusDate.substring(0, 10);
                 String orderStatus = c.getString(c.getColumnIndexOrThrow("statusID"));*/
                String amount = c.getString(c.getColumnIndexOrThrow(COL_AMOUNT));
                String orderId = c.getString(c.getColumnIndexOrThrow(COL_ORDER_ID));

                String statusDate = TABLE_ORDER_STATUS.getStatusDate(orderId);
                int statusSttusID = TABLE_ORDER_STATUS.getStatusID(orderId);

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(shopName, mobNo, statusDate,
                        statusSttusID + "", totalQtys, amount, orderId, retId);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        MyApplication.logi(LOG_TAG + " showStatusForOrders() ", " model is -->> " + orderStatusList.toString());
        return orderStatusList;
    }


   /* public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + NAME + "(ID INTEGER, OrderID INTEGER, DistributorID INTEGER,RetailerID INTEGER,OrderDate DATE, " +
            "Amount NUMERIC, OrderStatus INTEGER, OrderRemarks TEXT, OrderRating TINYINT, UserID INTEGER)";*/


    /* public static void insert_bulk_RetailerOrderMaster(JSONArray jsonArray) {
         try {
             MyApplication.logi(LOG_TAG, "in insert_bulk_RetailerOrderMaster->");
             SQLiteDatabase db = MyApplication.db.getWritableDatabase();
             db.beginTransaction();

             String insert_sql = "insert into " + NAME + " (" + "ID,OrderID,DistributorID,RetailerID,OrderDate" +
                     "Amount,OrderStatus,OrderRemarks,OrderRating,UserID) VALUES(?,?,?,?,?,?,?,?,?,?)";
             MyApplication.logi(LOG_TAG, "insert_sql" + insert_sql);
             SQLiteStatement statement = db.compileStatement(insert_sql);
             JSONObject jsonObject = null;
             int count_array = jsonArray.length();
             MyApplication.logi(LOG_TAG, "COUNT ISS insert_bulk_RetailerOrderMaster->" + count_array);
             try{
             for (int i = 0; i < count_array; i++) {
                 jsonObject = jsonArray.getJSONObject(i);

                 statement.clearBindings();
                 statement.bindString(1, jsonObject.getString("ID"));
                 statement.bindString(2, jsonObject.getString("OrderID"));
                 statement.bindString(3, jsonObject.getString("DistributorID"));
                 statement.bindString(4, jsonObject.getString("RetailerID"));
                 statement.bindString(5, jsonObject.getString("OrderDate"));

                 statement.bindString(6, jsonObject.getString("Amount"));
                 statement.bindString(7, jsonObject.getString("OrderStatus"));
                 statement.bindString(8, jsonObject.getString("OrderRemarks"));
                 statement.bindString(9, jsonObject.getString("OrderRating"));
                 statement.bindString(10, jsonObject.getString("UserID"));

                 statement.execute();
             }
             MyApplication.logi(LOG_TAG, "EndTime insert_bulk_RetailerOrderMaster->");
             db.setTransactionSuccessful();
             db.endTransaction();

         } catch (JSONException e) {
             MyApplication.logi(LOG_TAG, "JSONException insert_bulk_RetailerOrderMaster--->" + e.getMessage());
         }
     } catch(
     Exception e)

     {
         MyApplication.logi(LOG_TAG, " insert_bulk_RetailerOrderMaster exception--->" + e.getMessage());
     }
 }*/
    public static int getPendingOrderCount() {
        MyApplication.logi(LOG_TAG, "in getPendingOrderCount()");

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        String sql_get_count = "select count(OrderStatus) as count from " + TABLE_RETAILER_ORDER_MASTER.NAME + " where OrderStatus ='1'";
        MyApplication.logi(LOG_TAG, "in sql_get_count()" + sql_get_count);
        int ret_count = 0;
        Cursor c = db.rawQuery(sql_get_count, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            ret_count = c.getInt(c.getColumnIndexOrThrow("count"));

        }
        c.close();
        db.close();
        MyApplication.logi(LOG_TAG, "ret_count" + ret_count);
        return ret_count;
    }

    public static void updateStatus(String appOrderid, String statusId) {
        MyApplication.logi(LOG_TAG, " updateStatus() appOrderid()-->" + appOrderid);

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        //String sql_get_count = "select count(OrderStatus) as count from " + TABLE_RETAILER_ORDER_MASTER.NAME + " where OrderStatus ='1'";
        ///MyApplication.logi(LOG_TAG, "in sql_get_count()" + sql_get_count);
        ContentValues cv = new ContentValues();
        cv.put(COL_ORDER_STATUS, statusId);
        long ret = db.update(NAME, cv, COL_ORDER_ID + " =? ", new String[]{appOrderid});
        db.close();
        MyApplication.logi(LOG_TAG, "updateStatus() RESULT    -->" + ret);
    }

    public static void dropTable() {
        MyApplication.logi(LOG_TAG, "in dropTable() ");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        db.execSQL("DELETE FROM " + NAME);
        db.close();
    }


    public static List<OrderDeliveryStatusModel> getAllOrderStatusList(String filter) {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi("\n.\n" + LOG_TAG, "in getAllOrderStatusList() ");

       /*String query1 = " SELECT count(d.ItemId) as count,  p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                       " p.CustomerId as custId, o.* " +
                " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                " WHERE o." + COL_RETAILER_ID + " = cast(p.CustomerId as int) and o.OrderStatus = '1'  and o.orderId = d.OrderId group by o.orderId";*/

        String query;
        if (filter.equals("shop")) {
            query = "SELECT count(d.ItemId) as count, p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                    " p.CustomerId as custId, o.* " +
                    //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                    " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                    //  TABLE_ORDER_STATUS.NAME +" as OS "+
                    " WHERE cast(o." + COL_RETAILER_ID + " as int) = cast(p.CustomerId as int)" +
                    //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                    "  and cast(o.orderId as int) = cast(d.OrderId as int) GROUP BY p.FIRMNAME ";
            //     COL_ORDER_DATE = "OrderDate",
        } else if (filter.equals("calender")) {
            query = "SELECT count(d.ItemId) as count, p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                    " p.CustomerId as custId, o.* " +
                    //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                    " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                    //  TABLE_ORDER_STATUS.NAME +" as OS "+
                    " WHERE cast(o." + COL_RETAILER_ID + " as int) = cast(p.CustomerId as int)" +
                    //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                    "  and cast(o.orderId as int) = cast(d.OrderId as int) GROUP BY o.OrderDate ";
            //     COL_ORDER_DATE = "OrderDate",
        } else { // SHOP & CALENDAR
            query = "SELECT count(d.ItemId) as count, p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                    " p.CustomerId as custId, o.* " +
                    //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                    " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                    //  TABLE_ORDER_STATUS.NAME +" as OS "+
                    " WHERE cast(o." + COL_RETAILER_ID + " as int) = cast(p.CustomerId as int)" +
                    //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                    "  and cast(o.orderId as int) = cast(d.OrderId as int) GROUP BY  p.FIRMNAME, o.OrderDate ";
            //     COL_ORDER_DATE = "OrderDate",
        }

        //String query = "SELECT * FROM " + NAME;
        MyApplication.logi("getAllOrderStatusList() ", "In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getAllOrderStatusList() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
               /* String retailerId = c.getString(c.getColumnIndexOrThrow(COL_RETAILER_ID));
                String shopName = "", mobNo = "";   // = retailerId+" Shop Name";
                ArrayList<String> data = TABLE_PCUSTOMER.getShopName(retailerId);
                if (data.size() > 0 && data != null) {
                    shopName = data.get(0) + "";
                    mobNo = data.get(1) + "";
                }*/

                String shopName = c.getString(c.getColumnIndexOrThrow("shopName"));
                String mobNo = c.getString(c.getColumnIndexOrThrow("mobNo"));
                String retId = c.getString(c.getColumnIndexOrThrow("custId"));

                //String totalQtys = "01"; //
                String totalQtys = c.getString(c.getColumnIndexOrThrow("count"));
                MyApplication.logi("getAllOrderStatusList() ", "totalQtys:  " + totalQtys);
                String orderDate = c.getString(c.getColumnIndexOrThrow(COL_ORDER_DATE));
                orderDate = orderDate.substring(0, 10);
                String orderStatus = c.getString(c.getColumnIndexOrThrow(COL_ORDER_STATUS));

                 /*String statusDate = c.getString(c.getColumnIndexOrThrow("statusDate"));
                 statusDate = statusDate.substring(0, 10);
                 String orderStatus = c.getString(c.getColumnIndexOrThrow("statusID"));*/


                String amount = c.getString(c.getColumnIndexOrThrow(COL_AMOUNT));
                String orderId = c.getString(c.getColumnIndexOrThrow(COL_ORDER_ID));

                //String statusDate = TABLE_ORDER_STATUS.getStatusDate(orderId);
                //int statusSttusID = TABLE_ORDER_STATUS.getStatusID(orderId);

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(shopName, mobNo, orderDate,
                        orderStatus + "", totalQtys, amount, orderId, retId);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        MyApplication.logi("\n." + LOG_TAG, "getAllOrderStatusList() model-->> " + orderStatusList.toString() + "\n.");
        return orderStatusList;
    }


    public static List<OrderDeliveryStatusModel> getAllOrderStatusListBetweenDates(String fromDate, String toDate) {
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi("\n.\n" + LOG_TAG, "in getAllOrderStatusListBetweenDates() ");

        String query;
        query = "SELECT count(d.ItemId) as count, p.FIRMNAME  as shopName,  p.ContactPerson as mobNo, " +
                " p.CustomerId as custId, o.* " +
                //" OS.StatusDateTime as statusDate, OS.StatusID as statusID " +
                " FROM " + NAME + " as o,   " + TABLE_PCUSTOMER.NAME + " as p,  " + TABLE_ORDER_DETAILS.NAME + " as d " +
                //  TABLE_ORDER_STATUS.NAME +" as OS "+
                " WHERE cast(o." + COL_RETAILER_ID + " as int) = cast(p.CustomerId as int)" +
                //" and o."+COL_ORDER_ID+" = 'OS.RetailerOrderMasterID' "+
                " AND  (o.OrderDate BETWEEN '" + fromDate + "' AND '" + toDate + "') " +
                "  and cast(o.orderId as int) = cast(d.OrderId as int) GROUP BY o.OrderDate ";
        //     COL_ORDER_DATE = "OrderDate",

        MyApplication.logi("getAllOrderStatusListBetweenDates() ", "In tableHasData query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getAllOrderStatusListBetweenDates() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {

                String shopName = c.getString(c.getColumnIndexOrThrow("shopName"));
                String mobNo = c.getString(c.getColumnIndexOrThrow("mobNo"));
                String retId = c.getString(c.getColumnIndexOrThrow("custId"));

                //String totalQtys = "01"; //
                String totalQtys = c.getString(c.getColumnIndexOrThrow("count"));
                MyApplication.logi("getAllOrderStatusList() ", "totalQtys:  " + totalQtys);
                String orderDate = c.getString(c.getColumnIndexOrThrow(COL_ORDER_DATE));
                orderDate = orderDate.substring(0, 10);
                String orderStatus = c.getString(c.getColumnIndexOrThrow(COL_ORDER_STATUS));

                 /*String statusDate = c.getString(c.getColumnIndexOrThrow("statusDate"));
                 statusDate = statusDate.substring(0, 10);
                 String orderStatus = c.getString(c.getColumnIndexOrThrow("statusID"));*/


                String amount = c.getString(c.getColumnIndexOrThrow(COL_AMOUNT));
                String orderId = c.getString(c.getColumnIndexOrThrow(COL_ORDER_ID));

                //String statusDate = TABLE_ORDER_STATUS.getStatusDate(orderId);
                //int statusSttusID = TABLE_ORDER_STATUS.getStatusID(orderId);

                OrderDeliveryStatusModel model = new OrderDeliveryStatusModel(shopName, mobNo, orderDate,
                        orderStatus + "", totalQtys, amount, orderId, retId);
                orderStatusList.add(model);

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        MyApplication.logi("\n." + LOG_TAG, "getAllOrderStatusListBetweenDates() model-->> " + orderStatusList.toString() + "\n.");
        return orderStatusList;
    }


    public static HashMap<String, String> getDeliveryStatusCount() {
        MyApplication.logi(LOG_TAG, "in getDeliveryStatusCount()");

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();

        int pendingCount = 0, deliveredCount = 0, rejectedCount = 0;

        String pendingCountUrl = " SELECT count(OrderStatus) as pendingCount FROM " + NAME +
                " where OrderStatus = '4' ";
        Cursor c = db.rawQuery(pendingCountUrl, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            pendingCount = c.getInt(c.getColumnIndexOrThrow("pendingCount"));
        }

        String rejectedCountUrl = " SELECT count(OrderStatus) as rejectedCount  FROM  " + NAME +
                " where OrderStatus = '3' ";
        c = db.rawQuery(rejectedCountUrl, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            rejectedCount = c.getInt(c.getColumnIndexOrThrow("rejectedCount"));
        }

        String deliveredCountUrl = " SELECT count(OrderStatus) as deliveredCount  FROM " + NAME +
                " where OrderStatus = '2' ";
        c = db.rawQuery(deliveredCountUrl, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            deliveredCount = c.getInt(c.getColumnIndexOrThrow("deliveredCount"));
        }

        c.close();
        db.close();
        MyApplication.logi(LOG_TAG, " getDeliveryStatusCount() : dichpatchCount" + pendingCount + ",   rejectedCount: " + rejectedCount);
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("dichpatchCount", pendingCount + ""); // 1
        hmap.put("rejectedCount", rejectedCount + "");  // 3
        hmap.put("deliveredCount", deliveredCount + "");  // accepted // delivered == 2
        return hmap;
    }


}
