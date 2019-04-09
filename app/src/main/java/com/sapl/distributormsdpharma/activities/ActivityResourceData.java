package com.sapl.distributormsdpharma.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.sapl.distributormsdpharma.MyDatabase.TABLE_RESOURCE;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.adapter.ResourceDataAdapter;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.models.ResourceDataModel;

import java.util.ArrayList;

public class ActivityResourceData extends AppCompatActivity {

    public String LOG_TAG = "ActivityResourceData";
    public String resouceParentId = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_data);
        context = this;

        if (getIntent().getExtras() != null)
            if (getIntent().getExtras().containsKey("RESOURCE_TYPE"))
                resouceParentId = getIntent().getExtras().getString("RESOURCE_TYPE");

        MyApplication.logi(LOG_TAG, "onCreate(), resouceParentId: " + resouceParentId);
    }

    public void initBindData() {
        //Toast.makeText(context, "resouceParentId : --> " + resouceParentId, Toast.LENGTH_SHORT).show();
        MyApplication.logi(LOG_TAG, "initBindData(), resouceParentId: " + resouceParentId);
        if (!TextUtils.isEmpty(resouceParentId)) {
            ArrayList<ResourceDataModel> resourceDataList = TABLE_RESOURCE.getResourceChildrenData(resouceParentId);

            if (resourceDataList != null && resourceDataList.size() > 0) {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setItemViewCacheSize(200);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView.smoothScrollBy(0, 10);
                ResourceDataAdapter mAdapter = new ResourceDataAdapter(context, resourceDataList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            } else
                MyApplication.displayMessage(context, getResources().getString(R.string.no_resource_data));
        } else
            MyApplication.displayMessage(context, getResources().getString(R.string.no_resource_data));
        //MyApplication.Error_dialog("RESOURCE TYPE NOT SENT...", context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBindData();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), ActivityDashBoard.class));
    }
}
