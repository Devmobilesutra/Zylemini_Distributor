package com.sapl.distributormsdpharma.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_TEMP_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.ServerCall.HTTPVollyRequest;
import com.sapl.distributormsdpharma.ServerCall.MyListener;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CircularTextView;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.models.DashBoardData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ActivityDashBoard extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    CircularTextView txt_order_booked_no, txt_order_cart_no, txt_delivered_no, txt_delivery_pending_no, txt_order_rejected_no, txt_dispatch_no, txt_rejected_no,
            txt_resource_product_no, txt_resource_video_no, txt_resource_new_product_no, txt_delivered_no1, txt_delivered_no2;
    CardView card_order_booking, card_received_booking, card_delivery_status, card_resource_page, card_update_profile, card_view_rating, card_price_list;
    CustomTextViewMedium txt_order_rs, txt_order_booking_date, txt_order_booking, txt_received_orders, txt_order_status, txt_resource_page, txt_update_profile, txt_price_list, txt_review_rating;
    CustomTextViewMedium txt_received_orders_date, txt_order_status_date, txt_resource_date, txt_profile_update_date, txt_last_update1, txt_last_update, txt_last_update2, txt_profile_update_date1;
    CustomTextViewMedium txt_new_order, img_order_delivery, img_order_delivery1, img_order_delivery2, txt_dispatch, txt_delivered, txt_rejected;
    ImageView img_order_status, img_resource, img_menu, img_order_booking, img_order_cart, img_order_rs;
    static String LOG_TAG = "ActivityDashBoard ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        context = this;

        initComponants();
        initComponantListner();
        getdata();
        setDeliveryStatusCount();

        // TO GET THE REFRESHED ORDERS, SWIPE DOWN LISTNER CALLES onRefresh().
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        TABLE_SETTINGS.getUomLabels();
        TABLE_SETTINGS.getUomOrderBookCal();
    }

    public void setDeliveryStatusCount() {

        //HashMap<String, String> hmap  = TABLE_ORDER_STATUS.getDeliveryStatusCount();
        HashMap<String, String> hmap = TABLE_RETAILER_ORDER_MASTER.getDeliveryStatusCount();
        /*if(hmap.containsKey("dichpatchCount"))
            txt_dispatch_no.setText(hmap.get("dichpatchCount").toString());
        if(hmap.containsKey("rejectedCount"))
            txt_rejected_no.setText(hmap.get("rejectedCount").toString());
        if(hmap.containsKey("rejectedCount"))
            txt_delivered_no.setText(hmap.get("deliveredCount").toString());*/

        if (hmap.containsKey("deliveredCount"))
            txt_dispatch_no.setText(hmap.get("deliveredCount").toString());
        if (hmap.containsKey("rejectedCount"))
            txt_rejected_no.setText(hmap.get("rejectedCount").toString());
        if (hmap.containsKey("dichpatchCount"))
            txt_delivered_no.setText(hmap.get("dichpatchCount").toString());
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {

        if (MyApplication.isOnline(context)) {
            swipeRefreshLayout.setRefreshing(true);
            //  CALLING ANOTHER WEB SERVICE TO GET DATA ABOUT ORDERS
            String deviceID = MyApplication.get_session(MyApplication.SESSION_DEVICE_ID);

            String url = "http://zylemdemo.com/RetailerOrdering/api/GetOrders/RetailerOrder?user=" +
                    MyApplication.get_session(MyApplication.SESSION_USER_NAME) + "&password=" +
                    MyApplication.get_session(MyApplication.SESSION_PASSWORD) + "&deviceid=" + deviceID + "&clientid=" + MyApplication.get_session(MyApplication.SESSION_CLIENT_ID) + "&token=" +
                    MyApplication.get_session(MyApplication.SESSION_FCM_ID) + "&userType=3";
            // "http://zylemdemo.com/RetailerOrdering/api/Login/Retailer?user=2222222222&password=retailer&deviceid=1fd1148b7a8d6a44&clientid=1&token=" + MyApplication.get_session(MyApplication.SESSION_FCM_ID);

            MyApplication.logi(LOG_TAG, "url isss->" + url);
            MyApplication.logi(LOG_TAG, "URL_GET_DATA DEVICE ID---->" + MyApplication.get_session(MyApplication.SESSION_DEVICE_ID));
            MyApplication.logi(LOG_TAG, "FCM ID-->" + "FCM ID-->" + MyApplication.get_session(MyApplication.SESSION_FCM_ID));
            HashMap<String, String> hashmap = new HashMap<String, String>();

            //  hashmap.put("clientid", "1");  ??how to send client_id

            new HTTPVollyRequest(1, null, 1, "", context,
                    url, getOrderdataRespListner, hashmap);
        }
        //else MyApplication.displayMessage(context, "Please start your internet connection and try again.");

    }

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

                        TABLE_RETAILER_ORDER_MASTER.dropTable();
                        TABLE_ORDER_DETAILS.dropTable();
                        TABLE_ORDER_STATUS.dropTable();

                        TABLE_RETAILER_ORDER_MASTER.insertOrderMaster(orderMaster);
                        TABLE_ORDER_DETAILS.insertOrderDetails(orderDetails);
                        TABLE_ORDER_STATUS.insert_bulk_OrderStatus(OrderStatus);

                        getdata();
                        setDeliveryStatusCount();

                        swipeRefreshLayout.setRefreshing(false);

                    } else if (status.equalsIgnoreCase("false")) {
                        swipeRefreshLayout.setRefreshing(false);
                        MyApplication.logi(LOG_TAG, "unsuccesss" + status);
                        String error = resObj.get("error").toString();
                        MyApplication.logi(LOG_TAG, "error isss->>>>" + error);
                        // showDialogOK(resObj.getString("response_message"), context, resObj.getString("response_status"));
                    }
                }
            } catch (JSONException e) {
                swipeRefreshLayout.setRefreshing(false);
                MyApplication.logi(LOG_TAG, " JSONException failure " + e.getMessage());
            }
        }

        @Override
        public void failure(Object obj) {
            swipeRefreshLayout.setRefreshing(false);
            MyApplication.logi(LOG_TAG, "getRespListner failure ");
        }
    };


    public void initComponants() {

        card_order_booking = findViewById(R.id.card_order_booking);
        txt_order_booking = findViewById(R.id.txt_order_booking);
        txt_order_booking_date = findViewById(R.id.txt_order_booking_date);
        img_order_cart = findViewById(R.id.img_order_cart);
        txt_order_booked_no = findViewById(R.id.txt_order_booked_no);
        txt_order_rs = findViewById(R.id.txt_order_rs);
        img_order_rs = findViewById(R.id.img_order_rs);

        img_menu = findViewById(R.id.img_menu);
        card_received_booking = findViewById(R.id.card_received_orders);
        card_delivery_status = findViewById(R.id.card_delivery_status);
        card_resource_page = findViewById(R.id.card_resource_page);
        card_update_profile = findViewById(R.id.card_update_profile);
        card_price_list = findViewById(R.id.card_price_list);
        card_view_rating = findViewById(R.id.card_view_rating);


        txt_received_orders = findViewById(R.id.txt_received_orders);
        txt_received_orders_date = findViewById(R.id.txt_received_orders_date);
        txt_new_order = findViewById(R.id.txt_new_order);
        txt_order_cart_no = findViewById(R.id.txt_order_cart_no);
      /*  img_order_delivery = findViewById(R.id.img_order_delivery);
        img_order_delivery1 = findViewById(R.id.img_order_delivery1);
        img_order_delivery2 = findViewById(R.id.img_order_delivery2);*/

        txt_received_orders.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        txt_received_orders_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        txt_new_order.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        txt_order_cart_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_order_cart_no.setTextColor(getResources().getColor(R.color.white));
        txt_order_cart_no.setText(TABLE_RETAILER_ORDER_MASTER.getPendingOrderCount() + "");
       /* img_order_delivery.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


        img_order_delivery1.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        img_order_delivery2.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

     */
        txt_order_status = findViewById(R.id.txt_order_status);
       /* txt_delivered_no = findViewById(R.id.txt_delivered_no);
        txt_delivered_no1 = findViewById(R.id.txt_delivered_no1);
        txt_delivered_no2 = findViewById(R.id.txt_delivered_no2);*/
        //  txt_delivery_pending_no= findViewById(R.id.txt_delivery_pending_no);
        //  txt_order_rejected_no= findViewById(R.id.txt_order_rejected_no);
        txt_order_status_date = findViewById(R.id.txt_order_status_date);

        txt_order_status.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        txt_order_status_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        /*txt_delivered_no.setStrokeColor("#FF4081");
        txt_delivered_no.setTextColor(getResources().getColor(R.color.white));
        txt_delivered_no1.setStrokeColor("#008000");
        txt_delivered_no1.setTextColor(getResources().getColor(R.color.white));
        txt_delivered_no2.setStrokeColor("#e51c23");
        txt_delivered_no2.setTextColor(getResources().getColor(R.color.white));*/
        //txt_delivery_pending_no.setStrokeColor("#FFFF00");
        // txt_delivery_pending_no.setTextColor(getResources().getColor(R.color.white));
        // txt_order_rejected_no.setStrokeColor("#FF0000");
        // txt_order_rejected_no.setTextColor(getResources().getColor(R.color.white));


        txt_resource_page = findViewById(R.id.txt_resource_page);
        txt_resource_page.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        txt_resource_date = findViewById(R.id.txt_resource_date);
        txt_resource_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        txt_resource_product_no = findViewById(R.id.txt_resource_product_no);
        txt_resource_video_no = findViewById(R.id.txt_resource_video_no);
        txt_resource_new_product_no = findViewById(R.id.txt_resource_new_product_no);

        txt_resource_product_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_resource_product_no.setTextColor(getResources().getColor(R.color.white));

        txt_order_booked_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_order_booked_no.setTextColor(getResources().getColor(R.color.white));

        txt_dispatch_no = findViewById(R.id.txt_dispatch_no);
        txt_dispatch_no.setTextColor(getResources().getColor(R.color.white));
        txt_dispatch_no.setStrokeColor("#ffab40");

        txt_delivered_no = findViewById(R.id.txt_delivered_no);
        txt_delivered_no.setTextColor(getResources().getColor(R.color.white));
        txt_delivered_no.setStrokeColor("#008000");

        txt_rejected_no = findViewById(R.id.txt_rejected_no);
        txt_rejected_no.setTextColor(getResources().getColor(R.color.white));
        txt_rejected_no.setStrokeColor("#c41411");

        txt_resource_video_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_resource_video_no.setTextColor(getResources().getColor(R.color.white));
        txt_resource_new_product_no.setStrokeColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR));
        txt_resource_new_product_no.setTextColor(getResources().getColor(R.color.white));

        txt_update_profile = findViewById(R.id.txt_update_profile);
        txt_update_profile.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


        txt_price_list = findViewById(R.id.txt_price_list);
        txt_price_list.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


        txt_review_rating = findViewById(R.id.txt_review_rating);
        txt_review_rating.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


        txt_order_booking.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        txt_order_rs.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


        txt_dispatch = findViewById(R.id.txt_dispatch);
        txt_dispatch.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        txt_delivered = findViewById(R.id.txt_delivered);
        txt_delivered.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        txt_rejected = findViewById(R.id.txt_rejected);
        txt_rejected.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


        txt_profile_update_date = findViewById(R.id.txt_profile_update_date);
        txt_profile_update_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


        txt_profile_update_date1 = findViewById(R.id.txt_profile_update_date1);
        txt_profile_update_date1.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        txt_last_update = findViewById(R.id.txt_last_update);
        txt_last_update.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        txt_last_update1 = findViewById(R.id.txt_last_update1);
        txt_last_update1.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

        txt_last_update2 = findViewById(R.id.txt_last_update2);
        txt_last_update2.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

    }

    public void initComponantListner() {


        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });


        card_received_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityNewOrders.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
            }
        });

        card_delivery_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityOrderStatusMenu.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), " card_delivery_status is Pressed", Toast.LENGTH_SHORT).show();
            }
        });

        card_resource_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyApplication.displayMessage(context, "This feature is coming soon...");
                Intent intent = new Intent(getApplicationContext(), ActivityResourcePage.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
            }
        });

        card_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });
        txt_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });

        card_price_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });
        txt_price_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });


        card_view_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });

        txt_review_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });

        card_order_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyApplication.set_session("distributor_list", "card_order_booking");
                //Intent intent = new Intent(getApplicationContext(), ActivityDistributorList.class);
                Date d = new Date();
                CharSequence current_date_check = DateFormat.format("yyMMdd", d.getTime());
                CharSequence current_date_old = DateFormat.format("yyMMddHHmmss", d.getTime());
                MyApplication.logi(LOG_TAG, "Currentdatenew-->" + current_date_old);
                if (!MyApplication.get_session(MyApplication.SESSION_ORDER_ID).contains(current_date_check)) {
                    MyApplication.set_session(MyApplication.SESSION_ORDER_ID, current_date_old + "");
                    String str_date = MyApplication.dateFormatwithT();
                    MyApplication.set_session(MyApplication.SESSION_ORDER_DATE, str_date);

                    int ret_order_master = TABLE_TEMP_RETAILER_ORDER_MASTER.insertTempOrderMaster(
                            MyApplication.get_session(MyApplication.SESSION_ORDER_ID),
                            MyApplication.get_session(MyApplication.SESSION_DISTRIBUTOR_ID),
                            "0", str_date,
                            "0", "Pending",
                            "NA", "NA", "");
                }

                Intent intent = new Intent(getApplicationContext(), ActivitySelection.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);
            }
        });
    }


    private void getdata() {

        int main_cart_count = TABLE_PDISTRIBUTOR.countOfAddToCardItems();
        txt_order_booked_no.setText(main_cart_count + "");
        MyApplication.logi(LOG_TAG, "getdata(),  NO OF ORDERS ADDED TO CART -->" + main_cart_count);

        String total = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
        txt_order_rs.setText(total);  // TOTAL PRICE OF ORDERS IN THE CART

        // ORDER BOOKING NUMBER
        /*int main_cart_count = TABLE_PDISTRIBUTOR.countOfAddToCardItems();
        txt_no_of_product_taken.setText(main_cart_count + "");
        MyApplication.logi(LOG_TAG, "COUNT OF CARTS MAIN IS-->" + main_cart_count);*/


        txt_order_cart_no.setText(TABLE_RETAILER_ORDER_MASTER.getPendingOrderCount() + "");

        MyApplication.logi(LOG_TAG, "in getdata() ");
        if (!MyApplication.get_session(MyApplication.SESSION_VALUE_FROM_DB).equalsIgnoreCase("Y")) {
            ArrayList<DashBoardData> values = MyApplication.getValuesFromDb();
            for (DashBoardData data : values) {
                if (data.getMenu_key().equalsIgnoreCase("RECEIVEORDER")) {
                    MyApplication.set_session(MyApplication.SESSION_RECEIVED_ORDERS, data.getLabel_name());
                }
                if (data.getMenu_key().equalsIgnoreCase("ORDERDELSTATUS")) {
                    MyApplication.set_session(MyApplication.SESSION_DELIVERY_STATUS, data.getLabel_name());
                }
                if (data.getMenu_key().equalsIgnoreCase("RESOURCEPAGE")) {
                    MyApplication.set_session(MyApplication.SESSION_RESOURCE_PAGE, data.getLabel_name());
                }
                if (data.getMenu_key().equalsIgnoreCase("PROFILE")) {
                    MyApplication.set_session(MyApplication.SESSION_UPDATE_PROFILE, data.getLabel_name());
                }
                if (data.getMenu_key().equalsIgnoreCase("PRICELIST")) {
                    MyApplication.set_session(MyApplication.SESSION_PRICE_LIST, data.getLabel_name());
                }
                if (data.getMenu_key().equalsIgnoreCase("RATING")) {
                    MyApplication.set_session(MyApplication.SESSION_REVIEW_RATING, data.getLabel_name());
                }
            }
            MyApplication.logi(LOG_TAG + " getdata()", "data is -> " + values);
            setLabelName();
        } else {
            MyApplication.logi(LOG_TAG, " getdata() in ELSE OF getdata");
            setLabelName();
        }
    }

    public void setLabelName() {
        txt_received_orders.setText(MyApplication.get_session(MyApplication.SESSION_RECEIVED_ORDERS));
        txt_order_status.setText(MyApplication.get_session(MyApplication.SESSION_DELIVERY_STATUS));
        txt_resource_page.setText(MyApplication.get_session(MyApplication.SESSION_RESOURCE_PAGE));
        txt_update_profile.setText(MyApplication.get_session(MyApplication.SESSION_UPDATE_PROFILE));
        txt_price_list.setText(MyApplication.get_session(MyApplication.SESSION_PRICE_LIST));
        txt_review_rating.setText(MyApplication.get_session(MyApplication.SESSION_REVIEW_RATING));
    }

    @Override
    public void onBackPressed() {
        // TO BE EXIT FROM APP
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Close_Application))
                .setMessage(getResources().getString(R.string.do_you_want_exit))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getResources().getString(R.string.Exit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //System.exit(0);
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                    }
                }).setNegativeButton(getResources().getString(R.string.cancel), null)
                .show();

    }
}
