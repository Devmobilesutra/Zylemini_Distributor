package com.sapl.distributormsdpharma.confiq;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;


import com.sapl.distributormsdpharma.MyDatabase.MyDataBaseD;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_MENU_MASTER;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PCUSTOMER;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PITEM;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PRICELIST_DETAILS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PRICE_LIST;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_UOMMASTER;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.models.DashBoardData;
import com.sapl.distributormsdpharma.models.DistributorModel;
import com.sapl.distributormsdpharma.models.ItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ganesh Borse on 19-Jan-18.
 */

public class MyApplication extends Application {

    static String LOG_TAG = "Cordz-D";
    static Context context = null;
    private static ProgressDialog proDialog = null;
    //Shared Preferences Variables
    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    public static String SessionKey = "j5aD9uweHEAncWdj";//Must have 16 character session key
    public static AESAlgorithm aes;
    private String PREFS_NAME = "dsfwr34r334_r4#23e2e";
    public static String data_from_server = "";
    static JSONObject json;
    static JSONObject jsonobject;
    static JSONArray jsonarray;
    static String ResponseSuccessString = "";

    public static final String server_url = "http://zylemdemo.com/RetailerOrdering/api/";
    public static final String url_getData = server_url + "Login/Retailer";
    public static final String urlPlaceOrder = server_url + "RetailerRegistraion/OrderPlacing";

    public static String SESSION_HEADING_BACKGROUND_COLOR = "heading_background_color";
    public static String SESSION_HEADING_TEXT_COLOR = "heading_text_color";
    public static String SESSION_FCM_ID = "session_fcm_id";
    public static String SESSION_DEVICE_ID = "session_device_id";
    public static String SESSION_USER_NAME = "user_name";
    public static String SESSION_PASSWORD = "password";
    public static String SESSION_CLIENT_ID = "client_id";
    public static String SESSION_ORDER_ID_FROM_CART = "order_id_from_cart";
    public static String SESSION_RECEIVED_ORDERS = "received_orders";
    public static String SESSION_DELIVERY_STATUS = "del_status";
    public static String SESSION_RESOURCE_PAGE = "resource_page";
    public static String SESSION_UPDATE_PROFILE = "updtae_profile";
    public static String SESSION_PRICE_LIST = "price_list";
    public static String SESSION_REVIEW_RATING = "review_rating";
    public static String SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE = "SESSION_TOTAL_RPS_FOR_DASHBOARD_VALUE";
    public static String SESSION_SUBMIT_ORDER_CARTS_COUNT = "SESSION_SUBMIT_ORDER_CARTS_COUNT";
    public static String SESSION_DATE_FOR_LAST_ORDER_PLACED = "SESSION_DATE_FOR_LAST_ORDER_PLACED";

    public static String SESSION_FLAG_USER_TYPE = "user_type";
    public static String SESSION_VALUE_FROM_DB = "db_value";
    public static String SESSION_DISTRIBUTOR_ID = "session_distributor_id";
    public static String SESSION_FILTER_DIVISION = "session_filter_division";
    public static String SESSION_GROUP_ID = "session_grp_id";
    public static String SESSION_SUB_GROUP_ID = "session_sub_grp";
    public static String SESSION_BRAND_ID = "sessio_brand_id";
    public static String SESSION_UOM_VALUE_FIRST = "session_uom_val_1";
    public static String SESSION_UOM_VALUE_SECOND = "session_uom_val_2";
    public static String SESSION_DIST_PRICE_LIST_ID = "session_price_list_id";
    public static String SESSION_ORDER_ID = "session_order_id";
    public static String SESSION_UOM_ORDER_1 = "SESSION_UOM_ORDER_1";
    public static String SESSION_UOM_ORDER_2 = "SESSION_UOM_ORDER_2";
    public static String SESSION_ITEM_ID = "SESSION_ITEM_ID";
    public static String SESSION_DISTRIBUTER_NAME = "session_dist_name";
    public static String SESSION_ORDER_DATE = "SESSION_ORDER_DATE";
    public static String SESSION_DIVISION_ID = "division__id";

    public static String SESSION_USER_NAME_LOGIN = "session_user_name_login";
    public static String SESSION_PASSWORD_LOGIN = "session_pwd_login";
    public static String SESSION_CLIENT_ID_LOGIN = "clientId_login";


    public static MyDataBaseD db;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;


        if (db == null) {
            db = new MyDataBaseD(context);
            //SQLiteDatabase db1 =
            db.getWritableDatabase();
            //db.getReadableDatabase();

            sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            editor = sharedPref.edit();
            aes = new AESAlgorithm();
        }
        setColor();
        String deviceId = "";
        //deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceId = "1fd1148b7a8d6a44";
        MyApplication.set_session(MyApplication.SESSION_DEVICE_ID, deviceId);
        MyApplication.logi(LOG_TAG, "onCreate() DeviceID-->" + deviceId);
    }

    public void setColor() {
        set_session(SESSION_HEADING_BACKGROUND_COLOR, "#019998");
        set_session(SESSION_HEADING_TEXT_COLOR, "#FFFFFF");
    }

    public static void set_session(String key, String value) {
        logi(LOG_TAG, "set_session(), Key=" + key + ", value=" + value);
        String temp_key = aes.Encrypt(key);
        String temp_value = aes.Encrypt(value);
        MyApplication.editor.putString(temp_key, temp_value);
        MyApplication.editor.commit();
    }

    public static String get_session(String key) {
        String temp_key = aes.Encrypt(key);
        if (sharedPref.contains(temp_key)) {
            //d(LOG_TAG+", get_session() "+aes.Decrypt(sharedPref.getString(temp_key, "")));
            return aes.Decrypt(sharedPref.getString(temp_key, ""));
        } else
            return "";
    }

    public static void e(String msg) {
        if (msg.length() > 4000) {
            Log.e(LOG_TAG, msg);
            e(msg.substring(4000));
        } else {
            Log.e(LOG_TAG, msg);
        }
    }

    public static boolean is_marshmellow() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * print debug message
     */
    public static void d(String msg) {
        if (msg.length() > 4000) {
            Log.d(LOG_TAG, msg);
            d(msg.substring(4000));
        } else {
            Log.d(LOG_TAG, msg);
        }
    }

    public static List<DistributorModel> getOrderStatusList() {    //shop list
        ArrayList<DistributorModel> list = new ArrayList<>();
        DistributorModel model = new DistributorModel("Shop Name 1", "23-Jan-2018", "OrderPending", "7", "ic_launcher", "2000");
        list.add(model);
        model = new DistributorModel("Shop Name 2", "09-Feb-2018", "Received", "9", "ic_launcher", "1000");
        list.add(model);
        return list;
    }

    public static List<ItemModel> getItemList() {

        ArrayList<ItemModel> list = new ArrayList<>();
        ItemModel model = new ItemModel("Oil", "Case", "btls", "12", "2",
                "3", "0", "8000");
        list.add(model);

        model = new ItemModel("Oil", "Case", "btls", "19", "0",
                "3", "0", "3000");
        list.add(model);
        return list;
    }


    public static void logi(String TAG, String str) {
        if (str.length() > 10000000) {
            Log.i(LOG_TAG, TAG + "->" + str.substring(0, 10000000));
            logi(LOG_TAG, str.substring(10000000));
        } else
            Log.i(LOG_TAG, TAG + "->" + str);
    }


    public static void showDialog(Context mContext, String message) {
        MyApplication.logi(LOG_TAG, " in showDialog() message-" + message);
        try {
            if (mContext != null) {
                if (proDialog == null)
                    proDialog = ProgressDialog.show(mContext, null, message);
                proDialog.setCancelable(false);
            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " showDialog() Exception --> " + e.getMessage());
        }
    }


    public static void stopLoading() {
        try {
            if (proDialog != null)
                proDialog.dismiss();
            proDialog = null;
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " stopLoading() Exception --> " + e.getMessage());
        }
    }


    public static void createDataBase() {

        MyApplication.logi(LOG_TAG, " createDatabase() ");
        File sd = Environment.getExternalStorageDirectory();
        boolean success = true;
        if (!sd.exists()) {
            success = sd.mkdir();
        }
        if (success) {

            // File data = Environment.getDataDirectory();
            FileChannel source = null;
            FileChannel destination = null;
            //     String currentDBPath = "/data/user/0/com.example.r_b_k.recyclerview/databases/DB3";
            String currentDBPath = "/data/data/com.sapl.distributormsdpharma/databases/db_chord_D.db";
            //   = "/data/"+ getApplicationContext().getPackageName() +"/"+"DB1";
            String backupDBPath = "db_chord_D";
                    /* File tmpDir = new File(backupDBPath);
                     boolean exists = tmpDir.exists();
                     if(!exists){
                          sd = new File(sd,backupDBPath);
                     }*/
            File currentDB = new File(currentDBPath);
            File backupDB = new File(sd, backupDBPath);
            try {
                source = new FileInputStream(currentDB).getChannel();
                // PrintWriter out = new PrintWriter( source+".db");
                destination = new FileOutputStream(backupDB + ".db").getChannel();
                destination.transferFrom(source, 0, source.size());

                source.close();
                destination.close();
                //       Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                MyApplication.logi(LOG_TAG, " createDataBase(), Exception --> " + e.getMessage());
            }
        }
    }


    public static int insertServerData(String Response) {
        MyApplication.logi(LOG_TAG, "IN insertServerDta---->" + Response.toString());
        //  MyApplication.logi(LOG_TAG, "LENGTH IS-->" + Response.length());

        if (Response.length() != 0) {

            try {
                json = new JSONObject(Response);
                // ResponseSuccessString = json.getString("success");
                //   MyApplication.logi(LOG_TAG, "ResponseSuccessString-->" + ResponseSuccessString);

            /*    if (ResponseSuccessString.equals("false")) {
                    String erros = json.getString("error");
                    MyApplication.set_session("ERROR", erros);
                    return 0;
                } else {*/
                //write a function to delete if user is present
                /***********Parsing data start from here ***************/


                /***********PDistributor***********/

                if (json.has("PDistributor")) {

                    jsonarray = json.getJSONArray("PDistributor");
                    if (jsonarray.length() != 0) {
                        TABLE_PDISTRIBUTOR.insert_bulk_PDistributor(jsonarray);
                    }
                }

                /**************PItem***********/

                if (json.has("PItem")) {
                    jsonarray = json.getJSONArray("PItem");
                    if (jsonarray.length() != 0) {
                        TABLE_PITEM.insert_bulk_PItem(jsonarray);
                    }

                }
                /***********Settings*************/

                if (json.has("Settings")) {
                    jsonarray = json.getJSONArray("Settings");
                    if (jsonarray.length() != 0) {
                        TABLE_SETTINGS.insert_bulk_Settings(jsonarray);
                    }
                }

                /*********MenuMaster***********/
                if (json.has("MenuMaster")) {
                    jsonarray = json.getJSONArray("MenuMaster");
                    if (jsonarray.length() != 0) {
                        TABLE_MENU_MASTER.insert_bulk_MenuMaster(jsonarray);
                    }
                }

                /**********PriceList************/
                if (json.has("PriceList")) {
                    jsonarray = json.getJSONArray("PriceList");
                    if (jsonarray.length() != 0) {
                        TABLE_PRICE_LIST.insert_bulk_PriceList(jsonarray);
                    }
                }

                /*********PriceListDetails***********/
                if (json.has("PriceListDetails")) {
                    jsonarray = json.getJSONArray("PriceListDetails");
                    if (jsonarray.length() != 0) {
                        TABLE_PRICELIST_DETAILS.insert_bulk_PriceListDetails(jsonarray);
                    }
                }

                /*************PCustomer*******************/
                if (json.has("PCustomer")) {
                    jsonarray = json.getJSONArray("PCustomer");
                    if (jsonarray.length() != 0) {
                        TABLE_PCUSTOMER.insert_bulk_PCustomer(jsonarray);
                    }
                }

                if (json.has("UomMaster")) {
                    jsonarray = json.getJSONArray("UomMaster");
                    if (jsonarray.length() != 0) {
                        TABLE_UOMMASTER.insert_bulk_UomMaster(jsonarray);
                    }
                }

                return 1;

                //  }
            } catch (JSONException e) {
                MyApplication.logi(LOG_TAG, " insertServerData(), Exception --> " + e.getMessage());
                return 0;
            } catch (Exception e) {
                MyApplication.logi(LOG_TAG, " insertServerData(), Exception --> " + e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static void displayMessage(final Context context, String msg) {
        // MyApplication.log(LOG_TAG, "in showDDOK()message=" + message);
        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

        dlgAlert.setMessage(msg);
        dlgAlert.setTitle(context.getResources().getString(R.string.app_name));
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(false);
        dlgAlert.show();
    }

    public static ArrayList getValuesFromDb() {
        MyApplication.logi(LOG_TAG, "in getValuesFromDb");
        String resp = "";
        SQLiteDatabase db = MyApplication.db.getReadableDatabase();
        ArrayList<DashBoardData> data = new ArrayList<>();
        // User user=new User();
        String querytogetuserdata = "SELECT * FROM " + TABLE_MENU_MASTER.NAME
                + " where UserType ='2'";
        try {
            Cursor c = db.rawQuery(querytogetuserdata, null);
            int count = c.getCount();

            if (count > 0) {
                c.moveToFirst();
                do {
                    data.add(new DashBoardData(c.getString(c.getColumnIndexOrThrow("LabelName")),
                            c.getString(c.getColumnIndexOrThrow("IsActive")),
                            c.getString(c.getColumnIndexOrThrow("MenuKey"))));

                } while (c.moveToNext());
            }
            MyApplication.logi(LOG_TAG, "RESP ARE-->" + data.toString());
            if (data.size() != 0) {
                MyApplication.set_session(MyApplication.SESSION_VALUE_FROM_DB, "Y");
            }
            c.close();
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, " insertServerData(), Exception --> " + e.getMessage());
        }
        return data;
    }

    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = df.format(c.getTime());
        formattedDate = formattedDate.replace(" ", "T");
        logi(LOG_TAG, "getCurrentDateTime()   formattedDate: " + formattedDate);

        return formattedDate;
    }

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        logi(" getCurrentDate(), ", "Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        MyApplication.logi("getCurrentDate(), ", " formattedDate-->" + formattedDate);
        return formattedDate;
    }


    public static String dateFormatwithT() {

        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") {
        };

        String formated_date = df.format(new Date());
        MyApplication.logi(LOG_TAG, "dateFormatwithT(), formated_date-->" + formated_date);
        return formated_date;
    }

    public static String dateFormat() {
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss") {
        };

        String formated_date = df.format(new Date());
        MyApplication.logi(LOG_TAG, "formated_date-->" + formated_date);
        return formated_date;
    }

}
