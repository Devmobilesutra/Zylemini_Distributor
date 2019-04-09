package com.sapl.distributormsdpharma.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.adapter.DistributorDataAdapter;
import com.sapl.distributormsdpharma.adapter.OrderDelStatusAdapter;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomButtonBold;
import com.sapl.distributormsdpharma.customView.CustomTextViewRegular;
import com.sapl.distributormsdpharma.models.DistributorModel;
import com.sapl.distributormsdpharma.models.OrderDeliveryStatusModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityDeliveryStatus extends AppCompatActivity {
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_delivery_status);
        initComponents();
        initComponentListner();
        bindData();
    }

    private void initComponentListner() {
    }

    private void initComponents() {
    }

    private void bindData() {
        MyApplication.logi("bindData() ", "***************");
        List<OrderDeliveryStatusModel> orderStatusList = new ArrayList<>();
        //orderStatusList = TABLE_ORDER_DETAILS.getOrderStatusList();
        //orderStatusList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList();
        orderStatusList = TABLE_RETAILER_ORDER_MASTER.showStatusForOrders();
        MyApplication.logi("bindData() ", orderStatusList.toString());

        //orderStatusList = MyApplication.getOrderStatusList();

        if (orderStatusList != null && orderStatusList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            OrderDelStatusAdapter mAdapter = new OrderDelStatusAdapter(context, orderStatusList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
        else
            showNoRecordsDialogue("There is no record.");
    }

    public void showNoRecordsDialogue(String msg){

        CustomButtonBold btn_ok=null;
        CustomTextViewRegular tv_text=null;

        final Dialog dialog = new Dialog(context);
        dialog. requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.show_dialog_order);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        btn_ok  =(CustomButtonBold) dialog.findViewById(R.id.btn_ok);
        tv_text  = (CustomTextViewRegular) dialog.findViewById(R.id.tv_text);
        tv_text.setText(msg);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onBackPressed();
                /*Intent intent = new Intent(context, ActivityDashBoard.class);
                finish();
                overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                startActivity(intent);*/
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.closeOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityDeliveryStatus.this, ActivityDashBoard.class);
        finish();
        //overridePendingTransition(R.anim.fade_out_return, R.anim.fade_in_return);
        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
        startActivity(intent);
    }
}
