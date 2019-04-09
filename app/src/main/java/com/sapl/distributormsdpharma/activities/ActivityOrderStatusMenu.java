package com.sapl.distributormsdpharma.activities;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.confiq.MyApplication;

public class ActivityOrderStatusMenu extends TabActivity {

    public static TabHost tabHost;
    Context context;
    String LOG_TAG = "ActivityStatusTabs";
    ImageView img_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status_menu);
        context = this;

        //initComponents();
        //initComponentListner();
        initTabHost();
        initComponants();
        initComponantListner();
    }

    public void initComponants(){
        img_menu = findViewById(R.id.img_menu);
    }


    private void initTabHost() {
        MyApplication.logi(LOG_TAG, "initTabHost() ");
        tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        TabHost.TabSpec spec;
        Intent intent;

        spec = tabHost.newTabSpec("0");
        // Create a new TabSpec using tab host
        spec.setIndicator("All");
        intent = new Intent(getApplicationContext(), AcitivityTabAllStatusList.class);
        //intent = new Intent(getApplicationContext(), ActivityAproveOrder.class);
        spec.setContent(intent);
        tabHost.addTab(spec);


        // Do the same for the other tabs
        spec = tabHost.newTabSpec("1"); // Create a new TabSpec using tab host
        spec.setIndicator("Pending");
        intent = new Intent(getApplicationContext(), ActivityTabPendingStatus.class);
        //intent = new Intent(getApplicationContext(), ActivityForgotPassword.class);
        spec.setContent(intent);
        tabHost.addTab(spec);


        spec = tabHost.newTabSpec("2");
        //spec.setIndicator("Accepted");
        spec.setIndicator("Dispatched");
        intent = new Intent(getApplicationContext(), ActivityTabAcceptedStatus.class);
        spec.setContent(intent);
        tabHost.addTab(spec);


        spec = tabHost.newTabSpec("3");
        spec.setIndicator("Rejected");
        intent = new Intent(getApplicationContext(), ActivityTabRejectedStatus.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setPadding(0, 0, 0, 0);
        tabHost.setCurrentTab(0);

        tabHost.getTabWidget().setStripEnabled(false);
        // tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#ffffff"));


        for (int j = 0; j < tabHost.getTabWidget().getChildCount(); j++) {

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
            tv.setSingleLine();
        }

        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
        tv.setTextColor(Color.BLACK);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String arg0) {

                int tab = tabHost.getCurrentTab();

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
//            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    //  tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#0074C1"));
                    tv.setTextColor(Color.parseColor("#ffffff"));
                }

                // When tab is selected
                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
                tv.setTextColor(Color.BLACK);
            }
        });
    }

    public void initComponantListner(){

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), ActivityDashBoard.class));
    }
}
