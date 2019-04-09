package com.sapl.distributormsdpharma.FirebaseMessage;

import android.provider.Settings;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sapl.distributormsdpharma.confiq.MyApplication;


import org.json.JSONObject;

import java.lang.invoke.MutableCallSite;

/**
 * Created by Shashi on 11/13/2017.
 */

public class FCMMessaging extends FirebaseInstanceIdService {
    public String LOG_TAG = "FCMMessaging ";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        MyApplication.logi(LOG_TAG, "In token refreshed() ");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        MyApplication.logi(LOG_TAG, "Refreshed token FOR Zylemini Distributor: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        MyApplication.logi(LOG_TAG, " sendRegistrationToServer() refreshedToken: --> " + refreshedToken);
        JSONObject jsonObject = new JSONObject();
        String deviceId = "";
        deviceId = Settings.Secure.getString(getApplication().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        MyApplication.logi(LOG_TAG, "DEVICE IIIIIDDD-->" + deviceId);
        MyApplication.set_session(MyApplication.SESSION_FCM_ID, refreshedToken);
        //  MyApplication.set_session(MyApplication.SESSION_DEVICE_ID,deviceId);
        MyApplication.logi(LOG_TAG, "FCM iD is -->" + refreshedToken);
        MyApplication.logi(LOG_TAG, "DEevice iD is -->" + deviceId);

    }
}