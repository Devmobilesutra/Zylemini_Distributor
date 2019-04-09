package com.sapl.distributormsdpharma.FirebaseMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_STATUS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.activities.ActivityDashBoard;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.confiq.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.id.message;

/**
 * Created by Shashi on 11/13/2017.
 */

public class FirebaseService extends FirebaseMessagingService {
    public String LOG_TAG = "FirebaseService ";
    Context context = this;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        context = this;
        MyApplication.logi(LOG_TAG, "onMessageReceived() data payload: ");
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        MyApplication.logi(LOG_TAG, "onMessageReceived() From:--> " + remoteMessage.getFrom());

        // Check if message contains a data payload.

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            MyApplication.logi(LOG_TAG, "onMessageReceived(), Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            //  Log.d("JARVIS", "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                MyApplication.logi(LOG_TAG, " responce is  ->" + remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        MyApplication.logi(LOG_TAG, "handleDataMessage(), push json: " + json.toString());
        String notification_flag = "";
        Intent resultIntent = null;
        String message_event = "";
        // String title = "";
        String message = "";
        try {
            JSONObject root = json.getJSONObject("JSONResult");
            MyApplication.logi(LOG_TAG, "handleDataMessage(),  root-->" + root.toString()+"\n.");
            notification_flag = root.getString("Notification_flag");
            message = root.getString("message");
            MyApplication.logi(LOG_TAG, "handleDataMessage(),  message-->" + message);
            MyApplication.logi(LOG_TAG, "handleDataMessage(), notification_flag: " + notification_flag);

            // WHEN THE NEW ORDER ARRIVES
            if (notification_flag.equalsIgnoreCase("Order_Placed")) {
                MyApplication.logi(LOG_TAG, "handleDataMessage() CHECKING FLAGS:   Order placed.  --->  TRUE");

                JSONObject jsonOrderData = root.getJSONObject("data");
                MyApplication.logi(LOG_TAG, "handleDataMessage(),  jsonOrderData-->" + jsonOrderData.toString()+"\n.");

                JSONArray orderMaster = jsonOrderData.getJSONArray("OrderMaster");
                JSONArray orderStatus =  jsonOrderData.getJSONArray("OrderStatus");
                JSONArray orderDetails = jsonOrderData.getJSONArray("OrderDetails");

                MyApplication.logi(LOG_TAG, "handleDataMessage(),  OrderMaster-->" + orderMaster.toString());
                MyApplication.logi(LOG_TAG, "handleDataMessage(),  OrderStatus-->" + orderStatus.toString());
                MyApplication.logi(LOG_TAG, "handleDataMessage(),  orderDetails-->" + orderDetails.toString());

                TABLE_RETAILER_ORDER_MASTER.insertOrderMaster(orderMaster);
                TABLE_ORDER_DETAILS.insertOrderDetails(orderDetails);
                TABLE_ORDER_STATUS.insert_bulk_OrderStatus(orderStatus);
            }

            generateNotification(message, notification_flag);

            /*if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent("pushNotification");
                pushNotification.putExtra("message", title);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


                if (notification_flag.equalsIgnoreCase("message")) {
                    resultIntent = new Intent(getApplicationContext(), ActivityDashBoard.class);
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("title", title);
                }
                showNotificationMessage(getApplicationContext(), getResources().getString(R.string.app_name), notification_flag, 1, resultIntent);
            } else {
                if (notification_flag.equalsIgnoreCase("message")) {
                    resultIntent = new Intent(getApplicationContext(), ActivityDashBoard.class);
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("title", title);
                }
                showNotificationMessage(getApplicationContext(), getResources().getString(R.string.app_name), message, 1, resultIntent);
            }*/
        } catch (JSONException e) {
            MyApplication.logi(LOG_TAG,"handleDataMessage() JSONException ERROR  "+e.getMessage());

        } catch (Exception e) {
            MyApplication.logi(LOG_TAG,"handleDataMessage() Exception ERROR  "+e.getMessage());
        }

    }


    private void showNotificationMessage(Context context, String title, String message, int timeStamp, Intent intent) {
        MyApplication.logi(LOG_TAG, " showNotificationMessage()--> " + intent.getExtras().getString("title"));
        intent.getExtras().getString("title");
        NotificationUtils notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    static void broadcast(Context context, String flag, Bundle bundle) {
        MyApplication.logi("FirebaseService ", "in broadcast() flag: " + flag);
        Intent intent = new Intent(flag);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    private void generateNotification(String str_message, String titleFlag) {
        MyApplication.logi(LOG_TAG + " in generateNotification()\n", "titl->" + titleFlag + ",  messahe " + message);
        String subtitle = str_message;
        Intent intent;
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(titleFlag).setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        //intent = new Intent(context, ActivityListRow.class);
        intent = new Intent(getApplicationContext(), ActivityDashBoard.class);
        intent.putExtra("message", message);
        intent.putExtra("title", titleFlag);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        mBuilder.setTicker(subtitle);

        mBuilder.setContentText(subtitle);
        mBuilder.setSmallIcon(R.mipmap.logo);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(3, mBuilder.build());
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

        MyApplication.logi(LOG_TAG+ "handleIntent() ", "*********");
    }

}
