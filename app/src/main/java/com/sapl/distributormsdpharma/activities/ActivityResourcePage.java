package com.sapl.distributormsdpharma.activities;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.sapl.distributormsdpharma.MyDatabase.TABLE_RESOURCE;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.models.ResourceDataModel;

import java.util.ArrayList;

public class ActivityResourcePage extends TabActivity {

    public static TabHost tabHost;
    Context context;
    String LOG_TAG = "ActivityResourcePage ";
    TextView txt_heading;
    ImageView img_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_page);

        context = this;
        initTabHost();
        initComponants();
        initComponantListner();
    }

    public void initComponants() {
       // txt_heading = (TextView) findViewById(R.id.txt_heading);
        //txt_heading.setVisibility(View.VISIBLE);
        //txt_heading.setText("Resource Page");  //(MyApplication.db.getLabelNameOfMenuKey("Resource Page"));
        img_menu = (ImageView) findViewById(R.id.img_menu);
    }

    public void initComponantListner() {
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                finish();
                startActivity(i);
            }
        });
    }

    private void initTabHost() {
        MyApplication.logi(LOG_TAG, "initTabHost() ");
        tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        TabHost.TabSpec spec;
        Intent intent;


        ArrayList<ResourceDataModel> resourceList = TABLE_RESOURCE.getResourceParentTab();
        if (resourceList.size() > 0) {
            for (int i = 0; i < resourceList.size(); i++) {

                spec = tabHost.newTabSpec(resourceList.get(i).getId());  // 0
                // Create a new TabSpec using tab host
                spec.setIndicator(resourceList.get(i).getResourceName());        // VIDEO
                intent = new Intent(getApplicationContext(), ActivityResourceData.class);
                intent.putExtra("RESOURCE_TYPE", resourceList.get(i).getId());
                spec.setContent(intent);
                tabHost.addTab(spec);
            }

            tabHost.getTabWidget().setDividerDrawable(null);
            tabHost.setPadding(0, 0, 0, 0);
            tabHost.setCurrentTab(0);

            tabHost.getTabWidget().setStripEnabled(false);
            // tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#ffffff"));


            for (int j = 0; j < tabHost.getTabWidget().getChildCount(); j++) {

                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
                tv.setTextColor(Color.BLACK);
                tv.setSingleLine();
            }

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
            tabHost.getCurrentTabView().setBackgroundColor(getResources().getColor(R.color.light_blue_300));

            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String arg0) {

                    for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                        tv.setTextColor(Color.BLACK);
                        tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.blue));
                    }

                    // When tab is selected
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
                    tv.setTextColor(Color.WHITE);
                    tabHost.getCurrentTabView().setBackgroundColor(getResources().getColor(R.color.light_blue_300));
                }
            });

        } else {

        }
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), ActivityDashBoard.class));
    }
}
