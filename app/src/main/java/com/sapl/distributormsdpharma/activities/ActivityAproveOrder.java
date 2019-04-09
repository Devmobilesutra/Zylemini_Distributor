package com.sapl.distributormsdpharma.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.distributormsdpharma.ServerCall.MyListener;
import com.sapl.distributormsdpharma.adapter.AproveOrderAdapter;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomButtonBold;
import com.sapl.distributormsdpharma.customView.CustomButtonRegular;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.customView.CustomTextViewRegular;
import com.sapl.distributormsdpharma.models.AproveOrderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityAproveOrder extends AppCompatActivity {
    Context context;
    CustomTextViewMedium txt_distinct_shop_name, txt_amt;   // txt_item_name, txt_qty;

    //  CircularTextView txt_qty;
    ImageView img_back;
    String LOG_TAG = "ActivityAproveOrder ";

    //CARD VIEW  Order Data
    ImageView img_call, img_product;
    CustomTextViewMedium txt_toolbar_title,txt_product_name, txt_case_label, txt_case_no, txt_free_case_label, txt_free_case_no,
            txt_bottle_label, txt_bottle_no, txt_free_bottle_label, txt_free_bottle_no, txt_price_of_product;
    String orderId = "", shopName = "", retailerId = "", totalAmount = "", mobNo="";
    CustomButtonRegular btn_accept, btn_reject;
    List<AproveOrderModel> orderReviewList = null;
    Dialog dialog=null;
    CustomButtonBold btn_ok=null;
    CustomTextViewRegular tv_text=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprove_order);
        context = this;

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("ORDER_ID")) {
            orderId = getIntent().getExtras().getString("ORDER_ID");
            totalAmount = getIntent().getExtras().getString("TOTAL_PRICE");
            shopName = getIntent().getExtras().getString("SHOP_NAME");
            retailerId = getIntent().getExtras().getString("RETAILER_ID");
            mobNo = getIntent().getExtras().getString("MOBILE_NO");
            MyApplication.logi(LOG_TAG, "orderId =  ORDER_ID    -->" + orderId + ",  RETAILER_ID:= " + retailerId);
        }

        initComponants();

        initComponantListner();

        bindData();
    }

    public void initComponants() {
        txt_toolbar_title = findViewById(R.id.txt_toolbar_title);
        txt_toolbar_title.setText(getResources().getString(R.string.approve_order_title));

        txt_distinct_shop_name = findViewById(R.id.txt_distinct_shop_name);
        txt_distinct_shop_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

        txt_amt = findViewById(R.id.txt_amt_total);
        txt_amt.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

        // txt_item_name = findViewById(R.id.txt_item_name);
        //txt_item_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        //txt_qty = findViewById(R.id.txt_qty);
        //txt_qty.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        img_back = findViewById(R.id.img_back);
        btn_accept = findViewById(R.id.btn_accept);
        btn_reject = findViewById(R.id.btn_reject);

        img_call = (ImageView) findViewById(R.id.img_call);

       /* txt_qty = findViewById(R.id.txt_qty);
        txt_qty.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_qty.setTextColor(getResources().getColor(R.color.white));
       */
    }


    public void initComponantListner() {
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(mobNo);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAproveOrder.this, ActivityNewOrders.class);
                startActivity(intent);
                finish();
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.logi(LOG_TAG, " CALL BTN ACCEPT ORDER....");

                if (MyApplication.isOnline(context)) {
                    //  CALLING ORDER ACCEPT WEB SERVICE
                    String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/OrderStatus";
                /*String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/OrderStatus?LoginID=8551020065&password=default" +
                        "&deviceid=1fd1148b7a8d6a44&clientid=1&userType=3";*/
                    MyApplication.logi(LOG_TAG, "url isss->" + url);
                    //MyApplication.logi(LOG_TAG, "URL_GET_DATA DEVICE ID---->" + MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
                    //MyApplication.logi(LOG_TAG, "FCM ID-->" + "FCM ID-->" + MyApplication.get_session(MyApplication.SESSION_FCM_ID));

                    HashMap<String, String> hashmap = new HashMap<String, String>();
                    JSONObject jsonBody = new JSONObject();
                    JSONArray data = new JSONArray();
                    JSONObject dataObj = new JSONObject();
                    JSONArray OrderStatus = new JSONArray();
                    try {
                        dataObj.put("LoginID", ""+MyApplication.get_session(MyApplication.SESSION_USER_NAME));
                        dataObj.put("Password", ""+MyApplication.get_session(MyApplication.SESSION_PASSWORD));
                        dataObj.put("DeviceId", "1fd1148b7a8d6a44");
                        dataObj.put("ClientId", ""+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID));
                        dataObj.put("UserType", "3");
                       /* dataObj.put("LoginID", "8551020065");
                        dataObj.put("Password", "default");
                        dataObj.put("DeviceId", "1fd1148b7a8d6a44");
                        dataObj.put("ClientId", "1");
                        dataObj.put("UserType", "3");*/
                        data.put(dataObj);
                        jsonBody.put("data", data);
                        orderReviewList = TABLE_ORDER_DETAILS.getOrdersToBeAprove(orderId);
                        MyApplication.logi("JARVIS", "list data is-->" + orderReviewList.toString());

                        if ( orderReviewList.size() > 0) {
                            JSONObject orderObj = new JSONObject();
                            orderObj.put("AppOrderId", orderId);
                            orderObj.put("Retailerid", retailerId);
                            orderObj.put("StatusId", "2");
                            //orderObj.put("StatusDateTime", orderReviewList.get(i).getStatusDateTime());
                            orderObj.put("StatusDateTime", MyApplication.getCurrentDateTime());
                            orderObj.put("Remarks", "");
                            OrderStatus.put(orderObj);
                        }
                        jsonBody.put("OrderStatus", OrderStatus);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MyApplication.logi("JARVIS", " json is -->" + jsonBody.toString());
                    new HTTPVollyRequest(2, jsonBody, 0, "Please wait, accepting request...", context,
                            url, getAcceptOrderdataRespListner, hashmap);
                }
                else
                    MyApplication.displayMessage(context, "Please start your internet connection and try again.");

            }
        });
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.logi(LOG_TAG, " CALL BTN REJECT ORDER.....");
                if (MyApplication.isOnline(context)) {
                    //  CALLING ORDER ACCEPT WEB SERVICE
                    String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/OrderStatus";
                /*String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/OrderStatus?LoginID=8551020065&password=default" +
                        "&deviceid=1fd1148b7a8d6a44&clientid=1&userType=3";*/
                    MyApplication.logi(LOG_TAG, "url isss->" + url);
                    //MyApplication.logi(LOG_TAG, "URL_GET_DATA DEVICE ID---->" + MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
                    //MyApplication.logi(LOG_TAG, "FCM ID-->" + "FCM ID-->" + MyApplication.get_session(MyApplication.SESSION_FCM_ID));
                    HashMap<String, String> hashmap = new HashMap<String, String>();

                    JSONObject jsonBody = new JSONObject();
                    JSONArray data = new JSONArray();
                    JSONObject dataObj = new JSONObject();
                    JSONArray OrderStatus = new JSONArray();

                    try {

                        dataObj.put("LoginID", ""+MyApplication.get_session(MyApplication.SESSION_USER_NAME));
                        dataObj.put("Password", ""+MyApplication.get_session(MyApplication.SESSION_PASSWORD));
                        dataObj.put("DeviceId", "1fd1148b7a8d6a44");
                        dataObj.put("ClientId", ""+MyApplication.get_session(MyApplication.SESSION_CLIENT_ID));
                        dataObj.put("UserType", "3");
                        data.put(dataObj);
                        jsonBody.put("data", data);

                        orderReviewList = TABLE_ORDER_DETAILS.getOrdersToBeAprove(orderId);
                        if ( orderReviewList.size() > 0 ) {
                            JSONObject orderObj = new JSONObject();
                            orderObj.put("AppOrderId", orderId);
                            orderObj.put("Retailerid", retailerId);
                            orderObj.put("StatusId", "3");  // REJECT = 3
                            //orderObj.put("StatusDateTime", orderReviewList.get(i).getStatusDateTime());
                            orderObj.put("StatusDateTime", MyApplication.getCurrentDateTime());
                            orderObj.put("Remarks", "");
                            OrderStatus.put(orderObj);
                        }
                        jsonBody.put("OrderStatus", OrderStatus);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new HTTPVollyRequest(2, jsonBody, 0, "Please wait, rejecting request...", context,
                            url, getRejectOrderdataRespListner, hashmap);
                }
                else
                    MyApplication.displayMessage(context, "Please start your internet connection and try again.");

            }
        });
    }


    MyListener getAcceptOrderdataRespListner = new MyListener() {

        @Override
        public void success(Object obj) {
            try {
                MyApplication.logi("JARVIS", "getAcceptOrderdataRespListner");
                JSONObject resObj = new JSONObject(obj.toString());
                MyApplication.logi("getAcceptOrderdataRespListner", "Response: " + resObj.toString());

                // no need to UPDATE RECORD for STATUS, ACCEPTANCE
                // SET IT TO 1, PENDING

                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("getOrderdataRespListner", "Response: " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {
                        MyApplication.logi(LOG_TAG, "successss" + status);

                        JSONArray djsonDataObject = resObj.getJSONArray("data");
                        String message = "";
                        for(int i=0; i< djsonDataObject.length();i++){
                            JSONObject jsonObject = djsonDataObject.getJSONObject(i);

                           TABLE_RETAILER_ORDER_MASTER.updateStatus(jsonObject.getString("AppOrderid"), "2");
                           TABLE_ORDER_STATUS.updateStatus(jsonObject.getString("AppOrderid"), "2");

                            message = jsonObject.getString("Message");
                        }
                        dialog = new Dialog(context);
                        dialog. requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setContentView(R.layout.show_dialog_order);
                        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        btn_ok  =(CustomButtonBold) dialog.findViewById(R.id.btn_ok);
                        tv_text  = (CustomTextViewRegular) dialog.findViewById(R.id.tv_text);
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(context, ActivityNewOrders.class);
                                finish();
                                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                                startActivity(intent);
                            }
                        });
                        tv_text.setText(message);

                        dialog.setCancelable(false);
                        dialog.show();
                        dialog.closeOptionsMenu();

                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                        MyApplication.displayMessage(context, resObj.getString("response_message") );
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getAcceptOrderdataRespListner failure ");

        }
    };



    MyListener getRejectOrderdataRespListner = new MyListener() {

        @Override
        public void success(Object obj) {
            try {
                MyApplication.logi(LOG_TAG, " getRejectOrderdataRespListner");
                JSONObject resObj = new JSONObject(obj.toString());
                MyApplication.logi("getRejectOrderdataRespListner", "Response: " + resObj.toString());
                // no need to UPDATE RECORD for STATUS, ACCEPTANCE
                // SET IT TO 1, PENDING // 2  Delivered   // 3 REJECTED

                if (resObj != null && resObj.has("success")) {
                    MyApplication.logi("getRejectOrderdataRespListner", "Response: " + resObj.toString());
                    String status = resObj.getString("success");
                    if (status.equalsIgnoreCase("true")) {
                        MyApplication.logi(LOG_TAG, "successss" + status);

                        JSONArray djsonDataObject = resObj.getJSONArray("data");
                        String message = "";
                        for(int i=0; i< djsonDataObject.length();i++){
                            JSONObject jsonObject = djsonDataObject.getJSONObject(i);

                            TABLE_RETAILER_ORDER_MASTER.updateStatus(jsonObject.getString("AppOrderid"), "3");
                            TABLE_ORDER_STATUS.updateStatus(jsonObject.getString("AppOrderid"), "3");
                            message = jsonObject.getString("Message");
                        }

                        dialog = new Dialog(context);
                        dialog. requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setContentView(R.layout.show_dialog_order);
                        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        btn_ok  =(CustomButtonBold) dialog.findViewById(R.id.btn_ok);
                        tv_text  = (CustomTextViewRegular) dialog.findViewById(R.id.tv_text);

                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(context, ActivityDashBoard.class);
                                finish();
                                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                                startActivity(intent);
                            }
                        });
                        tv_text.setText(message);
                        dialog.setCancelable(false);
                        dialog.show();
                        dialog.closeOptionsMenu();

                    } else if (status.equalsIgnoreCase("false")) {
                        MyApplication.logi(LOG_TAG, "unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                        MyApplication.displayMessage(context, resObj.getString("response_message") );
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                MyApplication.logi(LOG_TAG,"excepion is -->"+e.getMessage());
            }
        }

        @Override
        public void failure(Object obj) {
            MyApplication.logi(LOG_TAG, "getRejectOrderdataRespListner failure ");
        }
    };


    public void bindData() {

        txt_distinct_shop_name.setText(shopName + "");
        txt_amt.setText(getResources().getString(R.string.rupee_sign) + " " + totalAmount);

        orderReviewList = new ArrayList<>();
        //orderReviewList = MyApplication.getOrdersToBeAprove(orderId);
        orderReviewList = TABLE_ORDER_DETAILS.getOrdersToBeAprove(orderId);
        //orderReviewList = TABLE_PDISTRIBUTOR.getDistributorName();
        MyApplication.logi(LOG_TAG, "\n bindData(),  distributorList -->" + orderReviewList);

        if (orderReviewList != null && orderReviewList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            AproveOrderAdapter mAdapter = new AproveOrderAdapter(context, orderReviewList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityAproveOrder.this, ActivityNewOrders.class);
        finish();
        //overridePendingTransition(R.anim.fade_out_return, R.anim.fade_in_return);
        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
        startActivity(intent);
    }

    public void makeCall(final String mobNo){

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CALL_PHONE}, 100);
                    return;
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    //  .setTitle(name.toString())
                    .setMessage("Make Call" )
                    .setIcon(R.drawable.distributor_call)
                    .setPositiveButton(mobNo, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + mobNo.trim()));
                            context.startActivity(callIntent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
