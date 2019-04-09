package com.sapl.distributormsdpharma.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.sapl.distributormsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.adapter.ShopListOrderAdapter;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomButtonBold;
import com.sapl.distributormsdpharma.customView.CustomButtonRegular;
import com.sapl.distributormsdpharma.customView.CustomTextViewRegular;
import com.sapl.distributormsdpharma.models.OrderDeliveryStatusModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityNewOrders extends AppCompatActivity {
    Context context = null;
    CustomButtonRegular btn_accept, btn_reject;
    ImageView img_menu;
    String LOG_TAG = "ActivityNewOrders ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        context = this;

        initComponents();
        initComponentListner();
        bindData();
    }


    private void initComponents() {
        btn_accept = findViewById(R.id.btn_accept);
        //  btn_accept.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

        btn_reject = findViewById(R.id.btn_reject);
        //img_menu.findViewById(R.id.img_menu);
        //   btn_reject.setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

        img_menu = findViewById(R.id.img_menu);
    }

    private void initComponentListner() {

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), " MENU is clicked", Toast.LENGTH_SHORT).show();
                MyApplication.displayMessage(context, "This feature is coming soon...");
            }
        });
    }

    private void bindData() {
        /*List<DistributorModel> distributorList = new ArrayList<>();
        distributorList = MyApplication.getOrderStatusList();
        distributorList = MyApplication.getOrderStatusList();
        if (distributorList != null && distributorList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            DistributorDataAdapter mAdapter = new DistributorDataAdapter(context, distributorList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }*/
        List<OrderDeliveryStatusModel> orderShopList = new ArrayList<>();
        // GET ALL THE NEW ORDERS BY DATEWISE
        orderShopList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList("1", "calender" );
        MyApplication.logi("\n.\n" + LOG_TAG + " bindData() ", orderShopList.toString() + "\n.");

        //orderStatusList = MyApplication.getOrderStatusList();
        if (orderShopList != null && orderShopList.size() > 0) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            ShopListOrderAdapter mAdapter = new ShopListOrderAdapter(context, orderShopList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        } else
            showNoRecordsDialogue(getResources().getString(R.string.no_pending_records));
    }

    public void showNoRecordsDialogue(String msg) {

        CustomButtonBold btn_ok = null;
        CustomTextViewRegular tv_text = null;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.show_dialog_order);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        btn_ok = (CustomButtonBold) dialog.findViewById(R.id.btn_ok);
        tv_text = (CustomTextViewRegular) dialog.findViewById(R.id.tv_text);
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
        Intent intent = new Intent(ActivityNewOrders.this, ActivityDashBoard.class);
        finish();
        //overridePendingTransition(R.anim.fade_out_return, R.anim.fade_in_return);
        overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  callPhoneNumber();
            } else {
                // Toast.makeText(MainActivity.this, getResources().getString(R.string.call_permission_denied_message), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
