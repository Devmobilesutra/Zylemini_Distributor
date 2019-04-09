package com.sapl.distributormsdpharma.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.sapl.distributormsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.adapter.OrderDelStatusAdapter;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomButtonBold;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.customView.CustomTextViewRegular;
import com.sapl.distributormsdpharma.models.OrderDeliveryStatusModel;
import java.util.ArrayList;
import java.util.List;

public class ActivityTabPendingStatus extends AppCompatActivity {
    RecyclerView recyclerView = null;
    Context context = null;
    String LOG_TAG = "ActivityTabPendingStatus ";
    CustomTextViewMedium txt_filter_lable;
    ImageView img_filter;
    List<OrderDeliveryStatusModel> orderShopList = new ArrayList<>();
    List<OrderDeliveryStatusModel> orderFilterList;
    String statusId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_pending_status);
        context = this;

        initComponants();
        initComponantsListner();
        // initBindData();
    }

    public void initComponants(){
        img_filter = (ImageView) findViewById(R.id.img_filter);
        txt_filter_lable = findViewById(R.id.txt_filter_lable);
        txt_filter_lable.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    public void initComponantsListner(){
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v1 = img_filter;
                PopupMenu popup = new PopupMenu(ActivityTabPendingStatus.this, v1);
                popup.getMenuInflater().inflate(R.menu.menu_filter, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        orderFilterList = new ArrayList<>();

                        if (item.toString().equals(getResources().getString(R.string.shop))) {
                            //Toast.makeText(getApplicationContext(),"U SELECTES "+ item.toString(),Toast.LENGTH_LONG).show();
                            //orderFilterList = orderShopList;
                            orderFilterList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, "shop");
                            initBindData(orderFilterList);
                        }
                        if (item.toString().equals(getResources().getString(R.string.calender))) {
                            // Toast.makeText(getApplicationContext(),"U SELECTES "+ item.toString(),Toast.LENGTH_LONG).show();
                            //orderFilterList = TABLE_RETAILER_ORDER_MASTER.getAllOrderStatusList("calender");
                            // orderShopList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, "calender" );
                            initBindData(orderShopList);
                        }
                        if (item.toString().equals(getResources().getString(R.string.shop_calender))) {
                            // Toast.makeText(getApplicationContext(),"U SELECTES "+ item.toString(),Toast.LENGTH_LONG).show();
                            //orderFilterList = TABLE_RETAILER_ORDER_MASTER.getAllOrderStatusList("shop_calender");
                            orderFilterList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, "shop_calender");
                            initBindData(orderFilterList);
                        }
                        txt_filter_lable.setText(item.toString()+"");
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        txt_filter_lable.setText(getResources().getString(R.string.calender));
        orderShopList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, "calender" );
        initBindData(orderShopList);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), ActivityDashBoard.class));
    }

    public void initBindData(List<OrderDeliveryStatusModel> orderShopList){

        MyApplication.logi(LOG_TAG + " bindData() ", orderShopList.toString());
        //orderStatusList = MyApplication.getOrderStatusList();

        if (orderShopList != null && orderShopList.size() > 0) {

            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemViewCacheSize(200);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.smoothScrollBy(0, 10);
            OrderDelStatusAdapter mAdapter = new OrderDelStatusAdapter(context, orderShopList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        } else
            showNoRecordsDialogue(getResources().getString(R.string.no_data));
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
                // onBackPressed();
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

}
