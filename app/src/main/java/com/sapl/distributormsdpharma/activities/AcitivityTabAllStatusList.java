package com.sapl.distributormsdpharma.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.sapl.distributormsdpharma.MyDatabase.TABLE_RETAILER_ORDER_MASTER;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.adapter.OrderDelStatusAdapter;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomButtonBold;
import com.sapl.distributormsdpharma.customView.CustomEditTextMedium;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.customView.CustomTextViewRegular;
import com.sapl.distributormsdpharma.models.OrderDeliveryStatusModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AcitivityTabAllStatusList extends AppCompatActivity {

    RecyclerView recyclerView = null;
    Context context = null;
    String LOG_TAG = "AcitivityTabAllStatusList ";
    CustomTextViewMedium txt_filter_lable;
    ImageView img_filter;
    List<OrderDeliveryStatusModel> orderShopList = new ArrayList<>();
    List<OrderDeliveryStatusModel> orderFilterList;
    String statusId = "*", from_date = "", to_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_tab_all_status_list);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        context = this;

        initComponants();
        initComponantsListner();
        // initBindData();
    }

    public void initComponants() {
        img_filter = (ImageView) findViewById(R.id.img_filter);
        txt_filter_lable = findViewById(R.id.txt_filter_lable);
        txt_filter_lable.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    public void initComponantsListner() {

        // TO APPLAY FILTER ON DATA BY ENTERABLE SHOPNAME AND BETWEEN THE TWO INPUT DATES
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v1 = img_filter;
                PopupMenu popup = new PopupMenu(AcitivityTabAllStatusList.this, v1);
                popup.getMenuInflater().inflate(R.menu.menu_filter, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        orderFilterList = new ArrayList<>();

                        if (item.toString().equals(getResources().getString(R.string.shop))) {
                            // WILL DISPLAY ALL THE DATA WHERE SHOPNAME CONTAINS GIVEN INPUT
                            showDialogueEditTextFilter(getResources().getString(R.string.enter_shopname_filter));
                            // orderFilterList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, "shop");
                            // initBindData(orderFilterList);
                        }
                        if (item.toString().equals(getResources().getString(R.string.calender))) {
                            // Toast.makeText(getApplicationContext(),"U SELECTES "+ item.toString(),Toast.LENGTH_LONG).show();
                            //orderFilterList = TABLE_RETAILER_ORDER_MASTER.getAllOrderStatusList("calender");
                            // orderShopList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, "calender" );
                            initBindData(orderShopList);

                            select_date(getResources().getString(R.string.select_from_date));
                            //select_date(getResources().getString(R.string.select_to_date));
                        }
                        if (item.toString().equals(getResources().getString(R.string.shop_calender))) {
                            // Toast.makeText(getApplicationContext(),"U SELECTES "+ item.toString(),Toast.LENGTH_LONG).show();
                            //orderFilterList = TABLE_RETAILER_ORDER_MASTER.getAllOrderStatusList("shop_calender");
                            orderFilterList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, "shop_calender");
                            initBindData(orderFilterList);
                        }
                        txt_filter_lable.setText(item.toString() + "");

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
        orderShopList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, "calender");
        initBindData(orderShopList);
        //orderShopList = TABLE_RETAILER_ORDER_MASTER.getOrderStatusList(statusId, getResources().getString(R.string.shop));  // BYDEFAULT FILTER SORT BY SHOP_NAME
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), ActivityDashBoard.class));
    }

    public void initBindData(List<OrderDeliveryStatusModel> orderShopList) {

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

    public void showDialogueEditTextFilter(String hint) {
        CustomButtonBold btn_ok = null;
        CustomEditTextMedium edt_filter = null;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_dialog_edittext_filter);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        btn_ok = (CustomButtonBold) dialog.findViewById(R.id.btn_ok);
        btn_ok.setVisibility(View.GONE);

        edt_filter = (CustomEditTextMedium) dialog.findViewById(R.id.edt_filter);
        edt_filter.setHint(hint);

        edt_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String enteredName = charSequence.toString().trim();
                if (!TextUtils.isEmpty(enteredName)) {
                    if (orderShopList != null && orderShopList.size() > 0) {
                        orderFilterList = new ArrayList<>();
                        for (int j = 0; j < orderShopList.size(); j++) {
                            if (orderShopList.get(j).getShopName().toLowerCase().contains(enteredName.toLowerCase()))
                                orderFilterList.add(orderShopList.get(j));
                        }
                        if (orderFilterList != null && orderFilterList.size() > 0) {
                            initBindData(orderFilterList);
                            txt_filter_lable.setText(enteredName);
                            Toast.makeText(context, "Data found...", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Sorry, No search found.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    initBindData(orderShopList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.setCanceledOnTouchOutside(true);
        //dialog.setCancelable(false);
        dialog.show();
        dialog.closeOptionsMenu();
    }

    public void select_date(final String msg) {
        // TODO Auto-generated method stub
        // To show current date in the datepicker
        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker,
                                  int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                String display_date = "", selected_date = "";

                selectedmonth = selectedmonth + 1;
                if (selectedday < 10) {
                    selected_date = selectedyear + "-" + selectedmonth + "-" + "0" + selectedday;
                    display_date = "0" + selectedday + "-" + selectedmonth + "-" + selectedyear;
                }
                if (selectedmonth < 10) {
                            /*selected_date = "0" + selectedmonth + "/"+ selectedday + "/" + selectedyear;*/
                    selected_date = selectedyear + "-" + "0" + selectedmonth + "-" + selectedday;
                    display_date = selectedday + "-" + "0" + selectedmonth + "-" + selectedyear;
                }
                if (selectedmonth < 10 && selectedday < 10) {
                            /*selected_date = "0" + selectedmonth + "/0"+ selectedday + "/" + selectedyear;*/
                    selected_date = selectedyear + "-0" + selectedmonth + "-0" + selectedday;
                    display_date = "0" + selectedday + "-0" + selectedmonth + "-" + selectedyear;
                }
                if (selectedmonth > 9 && selectedday > 9) {
                    selected_date = selectedyear + "-" + selectedmonth + "-" + selectedday;
                    display_date = "" + selectedday + "-" + selectedmonth + "-" + selectedyear;
                }

                //txt_filter_lable.setText(display_date);
                if (msg.equalsIgnoreCase(getResources().getString(R.string.select_from_date))) {
                    from_date = selected_date;
                    select_date(getResources().getString(R.string.select_to_date));
                } else {
                    to_date = selected_date;
                    if (!TextUtils.isEmpty(from_date) && !TextUtils.isEmpty(to_date)) {
                        orderFilterList = TABLE_RETAILER_ORDER_MASTER.getAllOrderStatusListBetweenDates(from_date, to_date);
                        if (orderFilterList != null && orderFilterList.size() > 0) {
                            initBindData(orderFilterList);
                            Toast.makeText(context, "Data found", Toast.LENGTH_SHORT).show();
                            txt_filter_lable.setText(from_date + "  --  " + to_date);
                        } else {
                            Toast.makeText(context, "Sorry, No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    if (msg.equalsIgnoreCase(getResources().getString(R.string.select_from_date)))
                        from_date = "";
                    else
                        to_date = "";
                }
            }
        });
        //mDatePicker.setTitle("Select date to be search.");
        mDatePicker.setMessage(msg);
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePicker.show();
    }
}
