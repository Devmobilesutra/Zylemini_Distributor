package com.sapl.distributormsdpharma.MyDatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.models.AproveOrderModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Acer on 20/06/2016.
 */
public class TABLE_ORDER_DETAILS {
    public static String LOG_TAG = "TABLE_ORDER_DETAILS";
    public static String NAME = "OrderDetails";

    public static String
            COL_ID = "ID",
            COL_ORDER_ID = "OrderId",
            COL_ITEM_ID = "ItemID",
            COL_ITEM_NAME = "item_Name",
            COL_LARGE_UNIT_QTY = "LargeUnitQty",
            COL_SMALL_UNIT_QTY = "SmallUnitQty",
            COL_FREE_LARGE_UNIT_QTY = "FreeLargeUnitQty",
            COL_FREE_SMALL_UNIT_QTY = "FreeSmallUnitQty",
            COL_RATE = "Rate",

            COL_DISCOUNTED_PRICE_OF_CASE = "discounted_price_cases",

            COL_AMOUNT = "Amount";

    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_ORDER_ID + " TEXT, "
            + COL_ITEM_ID + " TEXT, "
            + COL_LARGE_UNIT_QTY + " TEXT, "
            + COL_SMALL_UNIT_QTY + " TEXT, "
            + COL_FREE_LARGE_UNIT_QTY + " TEXT, "
            + COL_FREE_SMALL_UNIT_QTY + " TEXT, "
            + COL_RATE + " TEXT, "

            + COL_ITEM_NAME + " TEXT,"
            + COL_DISCOUNTED_PRICE_OF_CASE + " TEXT ,"

            + COL_AMOUNT + " TEXT )";

    public static void insertOrderDetails( JSONArray orderDetailsArray) {
        try {
            MyApplication.logi(LOG_TAG, " insertOrderDetails() -->  ");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = " insert into " + NAME + " ( "
                    // + COL_ID + ", "
                    + COL_ORDER_ID + ", "
                    + COL_ITEM_ID + ", "
                    + COL_LARGE_UNIT_QTY + ", "
                    + COL_SMALL_UNIT_QTY + ", "
                    + COL_FREE_LARGE_UNIT_QTY + ", "
                    + COL_FREE_SMALL_UNIT_QTY + ", "
                    + COL_RATE + ", "
                    + COL_AMOUNT + ") VALUES(?,?,?,?,?,?,?,?)";

            MyApplication.logi(LOG_TAG, "insertOrderDetails() insert_sql " + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = orderDetailsArray.length();
            MyApplication.logi(LOG_TAG, " insertOrderDetails() COUNT ISS-> " + count_array);
            try {
                for (int i = 0; i < count_array; i++) {
                    jsonObject = orderDetailsArray.getJSONObject(i);
                    MyApplication.logi(LOG_TAG, "\n insertOrderDetails(), orderMasterObject  -> " + jsonObject);
                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString(COL_ORDER_ID));
                    statement.bindString(2, jsonObject.getString(COL_ITEM_ID));
                    statement.bindString(3, jsonObject.getString(COL_LARGE_UNIT_QTY));
                    statement.bindString(4, jsonObject.getString(COL_SMALL_UNIT_QTY));
                    statement.bindString(5, jsonObject.getString(COL_FREE_LARGE_UNIT_QTY));
                    statement.bindString(6, jsonObject.getString(COL_FREE_SMALL_UNIT_QTY));
                    statement.bindString(7, jsonObject.getString(COL_RATE));
                    statement.bindString(8, jsonObject.getString(COL_AMOUNT));
                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "insertOrderDetails() EndTime->");
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();

            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "insertOrderDetails() JSONException --->" + e.getMessage());
            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insertOrderDetails() exception--->" + e.getMessage());
        }
    }

    public static List<AproveOrderModel> getOrdersToBeAprove(String orderId) {

        List<AproveOrderModel> orderReviewList = new ArrayList<>();
        SQLiteDatabase db = MyApplication.db.getWritableDatabase();
        MyApplication.logi(LOG_TAG, "in getOrdersToBeAprove()");

      // String query = "SELECT * FROM " + NAME + " WHERE " + COL_ORDER_ID + " = '" + orderId + "' ";

        //String query = "SELECT o.id, o.ItemID, p.Item as ItemName FROM OrderDetails as o, PItem as p WHERE o." + COL_ORDER_ID + " = '" + orderId + "' and o.ItemID = cast(p.ItemId as int)  ";
        String query = " SELECT  p.Item as ItemName,  o.* , OrderStatus.StatusDateTime " +
                " FROM OrderDetails as o, PItem as p, OrderStatus " +
                "WHERE o."+ COL_ORDER_ID + " = '" + orderId + "' and o.ItemID = cast(p.ItemId as int) " +
                " and OrderStatus.RetailerOrderMasterID = '"+orderId+"' ORDER BY o."+COL_ORDER_ID+" DESC ";

       /*String query = " SELECT  p.Item as ItemName, RetailerImages.ImageDetails, o.* " +
                " FROM OrderDetails as o, PItem as p, RetailerImages "+
                " WHERE o."+ COL_ORDER_ID + " = '" + orderId + "' and  o.ItemID = cast(p.ItemId as int)";
               // " LEFT JOIN "+TABLE_ITEM_IMAGES.NAME+" on cast("+TABLE_ITEM_IMAGES.NAME+".ImageId as int) = OrderDetails."+COL_ITEM_ID ;
               // "and o.ItemID = cast(i.ImageId as int)  ";*/

        MyApplication.logi("getOrdersToBeAprove() ", " query :" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi("getOrdersToBeAprove() ", "COUNT:  " + c.getCount());
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                MyApplication.logi(LOG_TAG," ITEM NAME:-->"+c.getString(c.getColumnIndexOrThrow("ItemName")));
                MyApplication.logi(LOG_TAG," ITEM ID:-->"+c.getString(c.getColumnIndexOrThrow(COL_ID)));
               // MyApplication.logi(LOG_TAG," ImageDetails:-->"+c.getString(c.getColumnIndexOrThrow("ImageDetails")));
               // byte[] imageDetails = c.getBlob(c.getColumnIndexOrThrow("ImageDetails"));

                String id = c.getString(c.getColumnIndexOrThrow(COL_ID));
                //String orderId = c.getString(c.getColumnIndexOrThrow(COL_ORDER_ID));
                String itemId = ""+c.getInt(c.getColumnIndexOrThrow(COL_ITEM_ID));
                String caseNo = c.getString(c.getColumnIndexOrThrow(COL_LARGE_UNIT_QTY));
                String bottleNo = c.getString(c.getColumnIndexOrThrow(COL_SMALL_UNIT_QTY));
                String freeCaseNo = c.getString(c.getColumnIndexOrThrow(COL_FREE_LARGE_UNIT_QTY));
                String freeBottleNo = c.getString(c.getColumnIndexOrThrow(COL_FREE_SMALL_UNIT_QTY));
                String rate = c.getString(c.getColumnIndexOrThrow(COL_RATE));
                String amount = c.getString(c.getColumnIndexOrThrow(COL_AMOUNT));
                //String productName = TABLE_PITEM.getProductName(itemId);
                String productName = c.getString(c.getColumnIndexOrThrow("ItemName"));

                byte[] imageBlob = TABLE_ITEM_IMAGES.getImagePath(itemId);
                String statusDateTime = c.getString(c.getColumnIndexOrThrow("StatusDateTime"));

                AproveOrderModel model = new AproveOrderModel(productName, "logo", caseNo, bottleNo,
                        freeCaseNo, freeBottleNo, amount, id, orderId, imageBlob, statusDateTime);
                orderReviewList.add(model);

            } while (c.moveToNext());
        }
        db.close();
        return orderReviewList;
    }

   /* public static void insert_bulk_OrderDetails(JSONArray jsonArray) {
        try {
            MyApplication.logi(LOG_TAG, "in insert_bulk_OrderStatus->");
            SQLiteDatabase db = MyApplication.db.getWritableDatabase();
            db.beginTransaction();

            String insert_sql = "insert into " + NAME + " ( "
                    + COL_ID + ", "
                    + COL_ORDER_ID + ", "
                    + COL_ITEM_ID + ", "
                    + COL_ITEM_NAME + ", "
                    + COL_QUANTITY_ONE + ", "
                    + COL_QUANTITY_TWO + ", "
                    + COL_RATE + ", "
                    + COL_AMOUNT + ") VALUES(?,?,?,?,?,?,?,?)";

            MyApplication.logi(LOG_TAG, "insert_sql insert_bulk_OrderStatus" + insert_sql);
            SQLiteStatement statement = db.compileStatement(insert_sql);
            JSONObject jsonObject = null;
            int count_array = jsonArray.length();
            MyApplication.logi(LOG_TAG, "COUNT ISS->" + count_array);
            try {
                for (int i = 0; i < count_array; i++) {

                    jsonObject = jsonArray.getJSONObject(i);
                    statement.clearBindings();
                    statement.bindString(1, jsonObject.getString(COL_ID));
                    statement.bindString(2, jsonObject.getString(COL_ORDER_ID));
                    statement.bindString(3, jsonObject.getString(COL_ITEM_ID));
                    statement.bindString(4, jsonObject.getString(COL_ITEM_NAME));
                    statement.bindString(5, jsonObject.getString(COL_QUANTITY_ONE));
                    statement.bindString(5, jsonObject.getString(COL_QUANTITY_TWO));
                    statement.bindString(5, jsonObject.getString(COL_RATE));
                    statement.bindString(5, jsonObject.getString(COL_AMOUNT));

                    statement.execute();
                }
                MyApplication.logi(LOG_TAG, "EndTime->");
                db.setTransactionSuccessful();
                db.endTransaction();

            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, "JSONException insert_bulk_OrderDetails--->" + e.getMessage());
            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insert_bulk_OrderDetails exception--->" + e.getMessage());
        }
    }*/

   public static void updateOrder(AproveOrderModel model){
       try {
           SQLiteDatabase db = MyApplication.db.getWritableDatabase();
           ContentValues cv = new ContentValues();

           cv.put(COL_LARGE_UNIT_QTY, model.getCase_no());
           cv.put(COL_SMALL_UNIT_QTY, model.getBoittle_no());
           cv.put(COL_FREE_LARGE_UNIT_QTY, model.getFree_case_no() );
           cv.put(COL_FREE_SMALL_UNIT_QTY, model.getFree_bottle_no() );
           cv.put(COL_AMOUNT , model.getPrice_of_product() );
           long res =  db.update(NAME, cv, COL_ID+" = ?", new String[]{model.getColomnId()+"" });
           MyApplication.logi(LOG_TAG, " updateOrder() Update RESULT --->" + res);
           db.close();

       } catch (Exception e) {
           MyApplication.logi(LOG_TAG, " updateOrder() exception--->" + e.getMessage());
       }
   }

    public static void dropTable(){
        MyApplication.logi(LOG_TAG, "in dropTable() ");
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        db.execSQL("DELETE FROM "+ NAME );
        db.close();
    }
    public static int insertOrderDetailsfinal(String order_id) {
        MyApplication.logi(LOG_TAG, "insertOrderDetailsfinal(), order_id------->" + order_id);

        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ContentValues initialValues1 = new ContentValues();

       /* String query = "INSERT INTO OrderDetails SELECT * FROM TempOrderDetails WHERE OrderId = " + order_id;*/

        String query = "INSERT INTO OrderDetails (orderId, ItemId, item_name, LargeUnitQty, SmallUnitQty, Rate, " +
                " discounted_price_cases, Amount, FreeLargeUnitQty, FreeSmallUnitQty) SELECT " +
                " orderid,itemID,item_name,LargeUnit,SmallUnit," +
                " rate,discounted_price_cases,Amount,FreeLargeUnitQty," +
                "        FreeSmallUnitQty FROM TempOrderDetails WHERE OrderId = " + order_id;

        MyApplication.logi(LOG_TAG, "insertOrderDetailsfinal(), query---->" + query);
        Cursor c = db.rawQuery(query, null);
        MyApplication.logi(LOG_TAG, "insertOrderDetailsfinal(), insertOrderDetailsfinal count---->" + c.getCount());
        return 0;

    }
}
