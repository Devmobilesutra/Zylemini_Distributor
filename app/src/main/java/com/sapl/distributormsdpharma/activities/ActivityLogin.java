package com.sapl.distributormsdpharma.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_ITEM_IMAGES;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.distributormsdpharma.ServerCall.MyListener;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomButtonRegular;
import com.sapl.distributormsdpharma.customView.CustomEditTextMedium;
import com.sapl.distributormsdpharma.customView.CustomTextViewRegular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;


public class ActivityLogin extends AppCompatActivity {

    CustomTextViewRegular txt_sign_up_link, txt_forgot_password;
    CustomButtonRegular btn_login;
    CustomEditTextMedium edt_username, edt_password, edt_client_id;
    Context context;
    String str_user_name = "", str_password = "", str_client_id = "";
    static String LOG_TAG = "ActivityLogin";
    private int permission_count = 5;
    String deviceID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        initComponants();
        initComponantListner();

        // TAKE PERMISSIOM FROM THE DEVICES ABOVE LOLILOP
        if (MyApplication.is_marshmellow()) {
            if (getPermissionCount() > 0)
                check_app_persmission();
        }
    }

    public void initComponants() {

        findViewById(R.id.rel_toolbar).setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_client_id = findViewById(R.id.edt_client_id);

        // edt_password.setInputType(InputType.TYPE_CLASS_TEXT |  InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        btn_login = findViewById(R.id.btn_login);
        txt_sign_up_link = findViewById(R.id.txt_sign_up_link);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
    }

    public void initComponantListner() {

        // VALIDATES LOGIN CREDITENTIALS AND RETURNS RESPECTIVE ORDER DATA AND IMAGE DATA OF PRODUCTS AT THE VERY FIRST TIME IF ITS VALID
        // OTHERWISE GIVES APPROPRIATE MESSAGE.
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceID = MyApplication.get_session(MyApplication.SESSION_DEVICE_ID);

                str_user_name = edt_username.getText().toString().trim() + "";
                str_password = edt_password.getText().toString().trim() + "";
                str_client_id = edt_client_id.getText().toString().trim() + "";

                if (!TextUtils.isEmpty(str_user_name) && !TextUtils.isEmpty(str_password) && !TextUtils.isEmpty(str_client_id)) {
                    if (MyApplication.isOnline(context)) {
                        boolean has_data = TABLE_SETTINGS.tableHasData();

                        if (has_data == false) {
                        /*String url = "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?" +
                                "user=2222222222&password=retailer&deviceid=1fd1148b7a8d6a44&clientid=1&token="
                                + MyApplication.get_session(MyApplication.SESSION_FCM_ID);*/
                            if (!TextUtils.isEmpty("" + MyApplication.get_session(MyApplication.SESSION_FCM_ID))) {


                                MyApplication.set_session(MyApplication.SESSION_USER_NAME_LOGIN, str_user_name);
                                MyApplication.set_session(MyApplication.SESSION_PASSWORD_LOGIN, str_password);
                                MyApplication.set_session(MyApplication.SESSION_CLIENT_ID_LOGIN, str_client_id);

                                String url = "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?" +
                                        "user=" + str_user_name + "&password=" + str_password + "&deviceid=" + deviceID + "&clientid=" + str_client_id
                                        + "&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=3";

                                MyApplication.logi(LOG_TAG, "login url ---->  " + url);
                                MyApplication.logi(LOG_TAG, "URL_GET_DATA DEVICE ID---->  " + deviceID);
                                MyApplication.logi(LOG_TAG, "FCM ID-->" + "FCM ID--> " + MyApplication.get_session(MyApplication.SESSION_FCM_ID));

                                HashMap<String, String> hashmap = new HashMap<String, String>();
                                //  hashmap.put("clientid", "1");  ??how to send client_id
                                new HTTPVollyRequest(1, null, 0, "Please Wait...", context,
                                        url, getdataRespListner, hashmap);
                            } else
                                MyApplication.displayMessage(context, "It seems that your internet connection is slow. Please try again.");
                        } else {

                            MyApplication.logi(LOG_TAG, "SESSIONS: userName: " + MyApplication.get_session(MyApplication.SESSION_USER_NAME)
                                    + ", Password: " + MyApplication.get_session(MyApplication.SESSION_PASSWORD) + ", ClientID: " +
                                    MyApplication.get_session(MyApplication.SESSION_CLIENT_ID));
                            if (str_client_id.equals(MyApplication.get_session(MyApplication.SESSION_CLIENT_ID)) &&
                                    str_password.equals(MyApplication.get_session(MyApplication.SESSION_PASSWORD)) &&
                                    str_user_name.equals(MyApplication.get_session(MyApplication.SESSION_USER_NAME))) {
                                Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                                finish();
                                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                                startActivity(i);
                            } else
                                MyApplication.displayMessage(context, "Please enter the correct login credientials.");
                        }
                    } else
                        MyApplication.displayMessage(context, "Please start your internet connection and try again.");
                } else
                    MyApplication.displayMessage(context, "Please enter all the fields.");

            }
        });

        txt_sign_up_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });
        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
               /* Intent i = new Intent(getApplicationContext(), ActivityForgotPassword.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(i);*/
            }
        });
    }


    MyListener getdataRespListner = new MyListener() {

        @Override
        public void success(Object obj) {
            try {
                MyApplication.logi(LOG_TAG, "getRespListner");
                JSONObject resObj = new JSONObject(obj.toString());

                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("\n.\n. " + LOG_TAG, "getRespListner resp is " + resObj.toString() + "\n.\n");
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {

                        MyApplication.set_session(MyApplication.SESSION_USER_NAME, str_user_name);
                        MyApplication.set_session(MyApplication.SESSION_PASSWORD, str_password);
                        MyApplication.set_session(MyApplication.SESSION_CLIENT_ID, str_client_id);

                        // MyApplication.logi(LOG_TAG, "OTP DATA IS-->" + resObj.getString("data"));
                        // otp_data = resObj.getString("data");

                        ///MyApplication.logi(LOG_TAG, "Message " + resObj.getString("message"));
                        MyApplication.logi(LOG_TAG, "getRespListner() successss " + status);

                        MyApplication.insertServerData(resObj.getString("data"));
                        // showDialogOK("Successfully Register", context, resObj.getString("response_status"));
                        MyApplication.createDataBase();

                        // CALLING IMAGE FOR PRODUCTS
                        //String urlImage = "http://zylemdemo.com/RetailerOrdering/api/Login/GetImages?user=8551020051&password=default&deviceid=1fd1148b7a8d6a44&clientid=1&token=Token1&userType=4";
                        String urlImage = "http://zylemdemo.com/RetailerOrdering/api/Login/GetImages?user=" + str_user_name +
                                "&password=" + str_password + "&deviceid="+deviceID+"&clientid=" + str_client_id +
                                "&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=4";
                        // String url = "http://zylemdemo.com/RetailerOrdering/api/Login/GetImages?user="+MyApplication.get_session(MyApplication.SESSION_REGISTRATION_NO)+"&password="+MyApplication.get_session(MyApplication.SESSION_PASSWORD)+"&deviceid="+MyApplication.get_session(MyApplication.SESSION_DEVICE_ID)+"&clientid=1&token=Token1&userType=4";
                        MyApplication.logi(LOG_TAG, "url for images  isss->" + urlImage);
                        HashMap<String, String> hashmapImage = new HashMap<String, String>();

                        new HTTPVollyRequest(1, null, 0, "Please wait loading images..", context,
                                urlImage, getImageResponse, hashmapImage);



                        /*Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                        finish();
                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        startActivity(i);*/


                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "getRespListner unsuccesss: " + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "getRespListner error isss->>>> " + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                        MyApplication.displayMessage(context, resObj.getString("message") + "\n" + resObj.getString("error"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRespListner failure ");

           /* final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("Some problem has occured..Please try after 15-20 minuts");


            dlgAlert.setTitle("Zylemini");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //dismiss the dialog
                        }
                    });
            dlgAlert.setCancelable(false);
            dlgAlert.show();
*/

        }
    };

    MyListener getOrderdataRespListner = new MyListener() {

        @Override
        public void success(Object obj) {
            try {
                MyApplication.logi("JARVIS", "getOrderdataRespListner");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("getOrderdataRespListner", "Response: " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {

                        MyApplication.logi(LOG_TAG, "successss" + status);
                        JSONObject djsonDataObject = resObj.getJSONObject("data");
                        JSONArray orderMaster = djsonDataObject.getJSONArray("OrderMaster");
                        JSONArray orderDetails = djsonDataObject.getJSONArray("OrderDetails");
                        JSONArray OrderStatus = djsonDataObject.getJSONArray("OrderStatus");

                        //MyApplication.logi("\n getOrderdataRespListner OrderMaster-->" , orderMaster.toString() );
                        //MyApplication.logi("\n getOrderdataRespListner OrderDetails-->" , orderDetails.toString() );

                        TABLE_RETAILER_ORDER_MASTER.insertOrderMaster(orderMaster);
                        TABLE_ORDER_DETAILS.insertOrderDetails(orderDetails);
                        TABLE_ORDER_STATUS.insert_bulk_OrderStatus(OrderStatus);

                        String distId = TABLE_PDISTRIBUTOR.getDistributorId();
                        MyApplication.set_session(MyApplication.SESSION_DISTRIBUTOR_ID, distId);

                        Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                        finish();
                        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                        startActivity(i);



                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                        MyApplication.displayMessage(context, resObj.getString("message") + "\n" + resObj.getString("error"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRespListner failure ");

        }
    };


    MyListener getImageResponse = new MyListener() {

        @Override
        public void success(Object obj) {
            try {
                MyApplication.logi("JARVIS", "getImageResponse");
                JSONObject resObj = new JSONObject(obj.toString());
                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("JARVIS", "getImageResponse resp otp is " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {
                        MyApplication.logi(LOG_TAG, "image DATA IS-->" + resObj.getString("data"));

                        JSONArray jsonArray = new JSONArray(resObj.getString("data"));
                        MyApplication.logi(LOG_TAG, "image DATA IS jsonArray-->" + jsonArray);
                        TABLE_ITEM_IMAGES.insertImageData(jsonArray);
                        ///MyApplication.logi(LOG_TAG, "Message " + resObj.getString("message"));
                        MyApplication.logi(LOG_TAG, "getImageResponse successss" + status);

                        //  CALLING ANOTHER WEB SERVICE TO GET DATA ABOUT ORDERS
                        /*String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=1234567890&password=sman" +
                                "&deviceid="+"1fd1148b7a8d6a44"+"&clientid=1&token=" +
                                MyApplication.get_session(MyApplication.SESSION_DEVICE_ID) + "&userType=3";*/
                        String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=" + str_user_name +
                                "&password=" + str_password + "&deviceid=" + deviceID + "&clientid=" + str_client_id + "&token=" +
                                MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=3";
                        // "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?user=2222222222&password=retailer&deviceid=1fd1148b7a8d6a44&clientid=1&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID);

                        MyApplication.logi(LOG_TAG, "url isss->" + url);
                        MyApplication.logi(LOG_TAG, "URL_GET_DATA DEVICE ID---->" + MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
                        MyApplication.logi(LOG_TAG, "FCM ID-->" + "FCM ID-->" + MyApplication.get_session(MyApplication.SESSION_FCM_ID));
                        HashMap<String, String> hashmap = new HashMap<String, String>();

                        //  hashmap.put("clientid", "1");  ??how to send client_id


                        new HTTPVollyRequest(1, null, 1, "", context,
                                url, getOrderdataRespListner, hashmap);

                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "getImageResponse unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "getImageResponse error isss->>>>" + error);
                        //showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                        MyApplication.displayMessage(context, resObj.getString("message") + "\n" + resObj.getString("error"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRespListner failure ");

            final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            dlgAlert.setMessage("Some problem has occured..Please try after some time");


            dlgAlert.setTitle("Retailer");
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
    };


    public int getPermissionCount() {
        int count = 5;
        int permission_granted = PackageManager.PERMISSION_GRANTED;
        int camera_permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);
        if (camera_permission == permission_granted)
            count -= 1;
        int storage_permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (storage_permission == permission_granted)
            count -= 1;
        int call_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        if (call_permission == permission_granted)
            count -= 1;
        int coarse_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (coarse_permission == permission_granted)
            count -= 1;
        int fine_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (fine_permission == permission_granted)
            count -= 1;
        return count;
    }


    @TargetApi(23)
    private void check_app_persmission() {
        permission_count = 5;
        int permission_granted = PackageManager.PERMISSION_GRANTED;
        MyApplication.logi(LOG_TAG, "PermissionGrantedCode->" + permission_granted);

        int storage_permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        MyApplication.logi(LOG_TAG, "StoragePermission->" + storage_permission);
        if (storage_permission == permission_granted)
            permission_count -= 1;

        int camera_permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);
        MyApplication.logi(LOG_TAG, "CameraPermission->" + camera_permission);
        if (camera_permission == permission_granted)
            permission_count -= 1;

        int location_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        MyApplication.logi(LOG_TAG, "location_permission->" + location_permission);
        if (location_permission == permission_granted)
            permission_count -= 1;

        int location2_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        MyApplication.logi(LOG_TAG, "location_permission->" + location_permission);
        if (location2_permission == permission_granted)
            permission_count -= 1;

        int call_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        MyApplication.logi(LOG_TAG, "call_permission->" + call_permission);
        if (call_permission == permission_granted)
            permission_count -= 1;

        MyApplication.logi(LOG_TAG, "PermissionCount->" + permission_count);

        if (permission_count > 0) {
            String permissionArray[] = new String[permission_count];

            for (int i = 0; i < permission_count; i++) {
                MyApplication.logi(LOG_TAG, "i->" + i);

                if (storage_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        permissionArray[i] = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
                        MyApplication.logi(LOG_TAG, "i->WRITE_EXTERNAL_STORAGE");
                        // break;
                    }
                }

                if (camera_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(android.Manifest.permission.CAMERA)) {
                        permissionArray[i] = Manifest.permission.CAMERA;
                        MyApplication.logi(LOG_TAG, "i->CAMERA");
                        //break;
                    }
                }
                if (location_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        permissionArray[i] = Manifest.permission.ACCESS_FINE_LOCATION;
                        MyApplication.logi(LOG_TAG, "i->ACCESS_FINE_LOCATION");
                        //break;
                    }
                }
                if (location2_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        permissionArray[i] = Manifest.permission.ACCESS_COARSE_LOCATION;
                        MyApplication.logi(LOG_TAG, "i->ACCESS_COARSE_LOCATION");
                        //break;
                    }
                }
                if (call_permission != permission_granted) {
                    if (!Arrays.asList(permissionArray).contains(Manifest.permission.CALL_PHONE)) {
                        permissionArray[i] = Manifest.permission.CALL_PHONE;
                        MyApplication.logi(LOG_TAG, "i->CALL_PHONE");
                        //break;
                    }
                }

            }
            MyApplication.logi(LOG_TAG, "PermissionArray->" + Arrays.deepToString(permissionArray));

            requestPermissions(permissionArray, permission_count);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Close_Application))
                .setMessage(getResources().getString(R.string.do_you_want_exit))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getResources().getString(R.string.Exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Yes button clicked, do something

                                // System.runFinalization();
                                System.exit(0);
                                //finish();
                                //finish();
                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel), null) // Do nothing on no
                .show();
    }

}