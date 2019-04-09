package com.sapl.distributormsdpharma.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_SETTINGS;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_TEMP_ORDER_DETAILS;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomButtonRegular;
import com.sapl.distributormsdpharma.customView.CustomEditTextMedium;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.models.OrderReviewModel;

import java.util.List;
import java.util.regex.Pattern;

import static com.sapl.distributormsdpharma.activities.ActivityPreviewOrder.txt_total_price;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

public class OrderReviewAdapter extends RecyclerView.Adapter<OrderReviewAdapter.MyViewHolder> {
    private List<OrderReviewModel> orderReviewList;
    Context context;
    private static final String LOG_TAG = "OrderReviewAdapter";
    public  String lable = "Total Price: " +" ₹ " + " ";
    String location = "";
    public static ImageView img_edt, img_delete;
    String  ORDBOOKUOMLABEL = "",biguom = "",smalluom="";

    public OrderReviewAdapter(Context context, List<OrderReviewModel> orderReviewList) {
        this.orderReviewList = orderReviewList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_product_name, txt_price_of_product,txt_free_bottle_no, txt_free_case_no;
        public CustomTextViewMedium txt_case_label, txt_bottle_label, txt_free_case_label, txt_free_bottle_label;
        public ImageView img_product;
        public CustomEditTextMedium txt_bottle_no, txt_case_no;


        public MyViewHolder(View view) {
            super(view);
            getUomLabels();
            txt_product_name = view.findViewById(R.id.txt_product_name);
            txt_price_of_product = view.findViewById(R.id.txt_price_of_product);
            txt_case_no = view.findViewById(R.id.txt_case_no);
           // txt_case_no.setBackground(context.getResources().getDrawable(R.drawable.background_border_black));
            txt_bottle_no = view.findViewById(R.id.txt_bottle_no);
            //txt_bottle_no.setBackground(context.getResources().getDrawable(R.drawable.background_border_black));

            txt_free_case_no = view.findViewById(R.id.txt_free_case_no);
            //txt_free_case_no.setBackground(context.getResources().getDrawable(R.drawable.background_border_black));
            txt_free_bottle_no = view.findViewById(R.id.txt_free_bottle_no);
            //txt_free_bottle_no.setBackground(context.getResources().getDrawable(R.drawable.background_border_black));
            img_product = (ImageView) view.findViewById(R.id.img_product);
            img_product.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

            img_edt = (ImageView) view.findViewById(R.id.img_edt);
            img_delete = (ImageView) view.findViewById(R.id.img_delete);


            // img_edt.setVisibility(View.VISIBLE);
            img_delete.setVisibility(View.VISIBLE);


            /*if(MyApplication.get_session("edit_delete_visibility").equalsIgnoreCase("true"))
            {
                img_edt.setVisibility(View.VISIBLE);
                img_delete.setVisibility(View.VISIBLE);
            }*/



            if (biguom.equalsIgnoreCase("")) {
                MyApplication.logi(LOG_TAG, "Big uom is empty");
                txt_case_no.setBackgroundColor(context.getResources().getColor(R.color.grey_300));
                txt_case_no.setClickable(false);
                txt_case_no.setEnabled(false);
            } else if (smalluom.equalsIgnoreCase("")) {
                MyApplication.logi(LOG_TAG, "small uom is empty");
                txt_bottle_no.setBackgroundColor(context.getResources().getColor(R.color.grey_300));
                txt_bottle_no.setClickable(false);
                txt_bottle_no.setEnabled(false);
            } else {
                MyApplication.logi(LOG_TAG, "both are there not is empty");
            }



            txt_product_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
            txt_case_no.setTextColor(context.getResources().getColor(R.color.grey_500));
            txt_free_case_no.setTextColor(context.getResources().getColor(R.color.grey_500));
            txt_bottle_no.setTextColor(context.getResources().getColor(R.color.grey_500));
            txt_free_bottle_no.setTextColor(context.getResources().getColor(R.color.grey_500));
            txt_price_of_product.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR )));

            txt_case_label = view.findViewById(R.id.txt_case_label);
            txt_bottle_label = view.findViewById(R.id.txt_bottle_label);
            txt_free_case_label = view.findViewById(R.id.txt_free_case_label);
            txt_free_bottle_label = view.findViewById(R.id.txt_free_bottle_label);

            txt_case_label.setTextColor(context.getResources().getColor(R.color.grey_500));
            txt_bottle_label.setTextColor(context.getResources().getColor(R.color.grey_500));
            txt_free_case_label.setTextColor(context.getResources().getColor(R.color.grey_500));
            txt_free_bottle_label.setTextColor(context.getResources().getColor(R.color.grey_500));


            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confirm Delete")
                                .setMessage("Do you want to delete order?")
                                .setIcon(android.R.drawable.ic_delete)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                OrderReviewModel model = orderReviewList.get(pos);
                                                long ret = TABLE_TEMP_ORDER_DETAILS.deletePreviewOrder(model.getItem_id(), MyApplication.get_session(MyApplication.SESSION_ORDER_ID));

                                                MyApplication.logi(LOG_TAG, "in delete-->" + pos);

                                                orderReviewList.remove(pos);
                                                OrderReviewAdapter.this.notifyItemRemoved(pos);
                                                MyApplication.displayMessage(context, "Order deleted successfully.");

                                                String total = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                                                txt_total_price.setText(lable+total);

                                            }
                                        }).setNegativeButton("Cancel", null) // Do nothing on no
                                .show();


                    }


                }
            });


            txt_case_no.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        OrderReviewModel model = orderReviewList.get(pos);
                        // editOrder(model.getCase_no(), model.getFree_case_no(),
                        //       model.getBoittle_no(), model.getFree_bottle_no(), model.getPrice_of_product());
                        MyApplication.logi(LOG_TAG, "txt_case_no modellll->>" + model.toString());
                        // editOrder(model, pos);


                        //    model.setBoittle_no("" + txt_bottle_no.getText().toString().trim());
                        model.setCase_no("" + txt_case_no.getText().toString().trim());
                        //  model.setFree_bottle_no("" + txt_free_bottle_no.getText().toString().trim());
                        //model.setFree_case_no("" + txt_free_case_no.getText().toString().trim());


                        double single_case_value_after_discount = model.getDiscounted_single_case_rate();
                        double single_btl_value_after_discount = model.getSingle_btl_price();
                        int edited_case_value = 0, edited_btl_value = 0;

                        if (!txt_case_no.getText().toString().trim().equalsIgnoreCase("")) {
                            edited_case_value = Integer.parseInt(txt_case_no.getText().toString().trim());
                            model.setCase_no(edited_case_value + "");
                        }


                        if (!txt_bottle_no.getText().toString().trim().equalsIgnoreCase("")) {
                            edited_btl_value = Integer.parseInt(txt_bottle_no.getText().toString().trim());
                            model.setBoittle_no(edited_btl_value + "");

                        }
                        double final_case_val = single_case_value_after_discount * edited_case_value;
                        double final_btl_val = single_btl_value_after_discount * edited_btl_value;
                        MyApplication.logi(LOG_TAG, "final_case_val" + final_case_val);
                        MyApplication.logi(LOG_TAG, "final_btl_val" + final_btl_val);
                        double total_price_of_product = Double.parseDouble(String.valueOf(final_btl_val + final_case_val));
                        MyApplication.logi(LOG_TAG, "total_price_of_product--------" + total_price_of_product);
                        String amt1 = String.format("%.2f", total_price_of_product);
                        MyApplication.logi(LOG_TAG, "amt1 total_price_of_product--------" + amt1);
                        model.setPrice_of_product(amt1);
                        //model.setPrice_of_product(String.valueOf(final_btl_val + final_case_val));
                        txt_price_of_product.setText(model.getPrice_of_product());

                        long ret1 = TABLE_TEMP_ORDER_DETAILS.insertOrderDetails(MyApplication.get_session(MyApplication.SESSION_ORDER_ID), model.getItem_id(), model.getProduct_name(), String.valueOf(edited_case_value), String.valueOf(edited_btl_value),
                                String.valueOf(single_btl_value_after_discount), String.valueOf(single_case_value_after_discount), Float.parseFloat(model.getPrice_of_product()), "0", "0", "update_data");

                        if (ret1 == 0) {
                            MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS successfully inserted " + ret1);
                            //  Toast.makeText(context, "Updated data successfully...", Toast.LENGTH_SHORT).show();


                        } else {
                            MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS not successfully inserted " + ret1);
                        }

                        orderReviewList.remove(pos);
                        //   OrderReviewAdapter.this.notifyItemRemoved(pos);
                        orderReviewList.add(pos, model);

                        //  OrderReviewAdapter.this.notifyDataSetChanged();
                        //  OrderReviewAdapter.this.notifyItemInserted(pos);
                        // popupWindow.dismiss();
                        //  MyApplication.displayMessage(context, "Order is successfully updated.");


                        String sum = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                        txt_total_price.setText(lable+sum);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            txt_bottle_no.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        OrderReviewModel model = orderReviewList.get(pos);
                        // editOrder(model.getCase_no(), model.getFree_case_no(),
                        //       model.getBoittle_no(), model.getFree_bottle_no(), model.getPrice_of_product());
                        MyApplication.logi(LOG_TAG, "txt_bottle_no modellll->>" + model.toString());
                        // editOrder(model, pos);


                        model.setBoittle_no("" + txt_bottle_no.getText().toString().trim());
                        //  model.setCase_no("" + txt_case_no.getText().toString().trim());
                        //model.setFree_bottle_no("" + txt_free_bottle_no.getText().toString().trim());
                        //model.setFree_case_no("" + txt_free_case_no.getText().toString().trim());


                        double single_case_value_after_discount = model.getDiscounted_single_case_rate();
                        double single_btl_value_after_discount = model.getSingle_btl_price();
                        int edited_case_value = 0, edited_btl_value = 0;

                        if (!txt_case_no.getText().toString().trim().equalsIgnoreCase("")) {
                            edited_case_value = Integer.parseInt(txt_case_no.getText().toString().trim());
                            model.setCase_no(edited_case_value + "");
                        }


                        if (!txt_bottle_no.getText().toString().trim().equalsIgnoreCase("")) {
                            edited_btl_value = Integer.parseInt(txt_bottle_no.getText().toString().trim());
                            model.setBoittle_no(edited_btl_value + "");

                        }
                        double final_case_val = single_case_value_after_discount * edited_case_value;
                        double final_btl_val = single_btl_value_after_discount * edited_btl_value;
                        MyApplication.logi(LOG_TAG, "final_case_val" + final_case_val);
                        MyApplication.logi(LOG_TAG, "final_btl_val" + final_btl_val);
                        double total_price_of_product = Double.parseDouble(String.valueOf(final_btl_val + final_case_val));
                        MyApplication.logi(LOG_TAG, "total_price_of_product--------" + total_price_of_product);
                        String amt1 = String.format("%.2f", total_price_of_product);
                        MyApplication.logi(LOG_TAG, "amt1 total_price_of_product--------" + amt1);
                        model.setPrice_of_product(amt1);
                        //model.setPrice_of_product(String.valueOf(final_btl_val + final_case_val));
                        txt_price_of_product.setText(model.getPrice_of_product());

                        long ret1 = TABLE_TEMP_ORDER_DETAILS.insertOrderDetails(MyApplication.get_session(MyApplication.SESSION_ORDER_ID), model.getItem_id(), model.getProduct_name(), String.valueOf(edited_case_value), String.valueOf(edited_btl_value),
                                String.valueOf(single_btl_value_after_discount), String.valueOf(single_case_value_after_discount), Float.parseFloat(model.getPrice_of_product()), "0", "0", "update_data");

                        if (ret1 == 0) {
                            MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS successfully inserted " + ret1);
                            //  Toast.makeText(context, "Updated data successfully...", Toast.LENGTH_SHORT).show();


                        } else {
                            MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS not successfully inserted " + ret1);
                        }

                        orderReviewList.remove(pos);
                        //   OrderReviewAdapter.this.notifyItemRemoved(pos);
                        orderReviewList.add(pos, model);

                        //   OrderReviewAdapter.this.notifyDataSetChanged();
                        //  OrderReviewAdapter.this.notifyItemInserted(pos);
                        // popupWindow.dismiss();
                        //  MyApplication.displayMessage(context, "Order is successfully updated.");


                        String sum = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                        txt_total_price.setText(lable+sum);


                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



           /* img_edt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        OrderReviewModel model = orderReviewList.get(pos);
                        // editOrder(model.getCase_no(), model.getFree_case_no(),
                        //       model.getBoittle_no(), model.getFree_bottle_no(), model.getPrice_of_product());
                        MyApplication.logi(LOG_TAG, "modellll->>" + model.toString());
                        editOrder(model, pos);
                    }
                }
            });*/


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        OrderReviewModel model = orderReviewList.get(pos);

                    }
                }
            });
        }
    }

    @Override
    public OrderReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_order_submit, parent, false);
        return new OrderReviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderReviewAdapter.MyViewHolder holder, final int position) {
        final OrderReviewModel model = orderReviewList.get(position);
        MyApplication.logi(LOG_TAG, "model--->" + model);
        MyApplication.logi(LOG_TAG, "MKKK case value ----->" + model.getCase_no());
        MyApplication.logi(LOG_TAG, "MKKK btl value ----->" + model.getBoittle_no());
        //  MyApplication.set_session("model_item_id",model.getItem_id());


        MyApplication.logi(LOG_TAG, "MKKK PRODUCT case value ----->" + model.getCase_no());
        MyApplication.logi(LOG_TAG, "MKKK PRODUCT btl value ----->" + model.getBoittle_no());


        holder.txt_bottle_no.setText(model.getBoittle_no());
        holder.txt_case_no.setText(model.getCase_no());


        MyApplication.logi(LOG_TAG, "MKKK holder txt_bottle_no PRODUCT case value ----->" + holder.txt_bottle_no.getText());
        MyApplication.logi(LOG_TAG, "MKKK holder txt_case_no PRODUCT btl value ----->" + holder.txt_case_no.getText());


        img_delete.setTag(position);
        holder.img_product.clearAnimation();
        holder.img_product.clearFocus();


        // MyApplication.log("NAME: ---------->  "+model.getName()+" ****************");

        holder.txt_free_case_no.setText(model.getFree_case_no());
        holder.txt_free_bottle_no.setText(model.getFree_bottle_no());
        MyApplication.logi(LOG_TAG, "PRODUCT NAME ----->" + model.getProduct_name());
        holder.txt_product_name.setText(model.getProduct_name());


        holder.txt_case_label.setText(MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_FIRST));
        holder.txt_bottle_label.setText(MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_SECOND));
        holder.txt_free_case_label.setText("Free " + MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_FIRST));
        holder.txt_free_bottle_label.setText("Free " + MyApplication.get_session(MyApplication.SESSION_UOM_VALUE_SECOND));


        holder.txt_price_of_product.setText("₹ " + model.getPrice_of_product());


        if (model.getProduct_img_path() == null) {
            Glide.with(context)
                    .load(R.drawable.app_icon)
                    .asBitmap()
                    .error(R.drawable.app_icon)
                    .placeholder(R.drawable.app_icon)
                    .into(holder.img_product);
        } else {
            holder.img_product.clearColorFilter();
            byte[] decodeString = Base64.decode(model.getProduct_img_path(), Base64.DEFAULT);
            Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            Glide.with(context)
                    .load(decodeString)
                    .asBitmap()
                    //.transform(new CircleTransform(context))
                    .placeholder(R.drawable.app_icon)
                    .into(holder.img_product);
        }
       /* Glide.with(context).load(context.getResources().getIdentifier(model.getProduct_img_path(),
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_product) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img_product.setImageDrawable(circularBitmapDrawable);
            }
        });*/

        /*Glide.with(context)
                .load(context.getResources().getIdentifier(model.getDistImagePath(), "drawable", context.getPackageName()))
                .fitCenter()
                .into(holder.img_name);*/

        // if (f.exists() && f.isFile()) {
         /* Glide.with(context).load(context.getResources().getIdentifier(model.getDistImagePath(), "drawable", context.getPackageName()))
                // .placeholder(R.drawable.ic_action_name)
                //.error(R.drawable.ic_action_name)
                .transform(new RoundedTransformation(70, 0, context.getResources().getIdentifier(model.getDistImagePath(), "drawable", context.getPackageName()))
                        .resizeDimen(R.dimen.dp60, R.dimen.dp60)
                        .centerCrop()
                        .into(holder.img_name));*/
        //}


          /*  public int getImage(String imageName) {

                int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

                return drawableResourceId;
            }*/
    }

    @Override
    public int getItemCount() {
        return orderReviewList == null ? 0 : orderReviewList.size();
    }


    public void editOrder(final OrderReviewModel model, final int pos
                          /*String caseNo, String freeCase, String bottleNo, String freeBottleNo, String price*/) {

        MyApplication.logi(LOG_TAG, "IN EDIT ORDER");
        // LayoutInflater layoutInflater =LayoutInflater.from(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //(LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.dialog_edit_order, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        final CustomEditTextMedium edt_case_no, edt_bottle_no, txt_free_case_no, txt_free_bottle_no;
        final CustomTextViewMedium txt_title, txt_product_name, txt_case_lable, txt_free_case_lable;
        final CustomTextViewMedium txt_bottle_lable, txt_free_bottle_lable;

        edt_case_no = popupView.findViewById(R.id.edt_case_no);
        edt_bottle_no = popupView.findViewById(R.id.edt_bottle_no);

        txt_title = popupView.findViewById(R.id.txt_title);
        txt_product_name = popupView.findViewById(R.id.txt_product_name);
        txt_case_lable = popupView.findViewById(R.id.txt_case_lable);
        txt_free_case_lable = popupView.findViewById(R.id.txt_free_case_lable);
        txt_free_case_no = popupView.findViewById(R.id.txt_free_case_no);

        txt_bottle_lable = popupView.findViewById(R.id.txt_bottle_lable);
        txt_free_bottle_lable = popupView.findViewById(R.id.txt_free_bottle_lable);
        txt_free_bottle_no = popupView.findViewById(R.id.txt_free_bottle_no);

        edt_case_no.setText(model.getCase_no());
        edt_bottle_no.setText(model.getBoittle_no());
        txt_free_case_no.setText(model.getFree_case_no());
        txt_free_bottle_no.setText(model.getFree_bottle_no());
        txt_product_name.setText(model.getProduct_name());

        txt_case_lable.setTextColor(context.getResources().getColor(R.color.heading_background));
        txt_bottle_lable.setTextColor(context.getResources().getColor(R.color.heading_background));
        txt_free_bottle_lable.setTextColor(context.getResources().getColor(R.color.heading_background));
        txt_free_bottle_no.setTextColor(context.getResources().getColor(R.color.heading_background));

        txt_free_case_no.setTextColor(context.getResources().getColor(R.color.heading_background));
        txt_free_case_lable.setTextColor(context.getResources().getColor(R.color.heading_background));

        ImageView img_back = (ImageView) popupView.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  popupWindow.dismiss();
            }
        });


        CustomButtonRegular btn_update = popupView.findViewById(R.id.btn_update);
        // if button is clicked, close the custom dialog

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setBoittle_no("" + edt_bottle_no.getText().toString().trim());
                model.setCase_no("" + edt_case_no.getText().toString().trim());
                model.setFree_bottle_no("" + txt_free_bottle_no.getText().toString().trim());
                model.setFree_case_no("" + txt_free_case_no.getText().toString().trim());


                double single_case_value_after_discount = model.getDiscounted_single_case_rate();
                double single_btl_value_after_discount = model.getSingle_btl_price();
                int edited_case_value = 0, edited_btl_value = 0;

                if (!edt_case_no.getText().toString().trim().equalsIgnoreCase("")) {
                    edited_case_value = Integer.parseInt(edt_case_no.getText().toString().trim());

                    model.setCase_no(edited_case_value + "");
                }


                if (!edt_bottle_no.getText().toString().trim().equalsIgnoreCase("")) {
                    edited_btl_value = Integer.parseInt(edt_bottle_no.getText().toString().trim());
                    model.setBoittle_no(edited_btl_value + "");

                }
                double final_case_val = single_case_value_after_discount * edited_case_value;
                double final_btl_val = single_btl_value_after_discount * edited_btl_value;
                MyApplication.logi(LOG_TAG, "final_case_val" + final_case_val);
                MyApplication.logi(LOG_TAG, "final_btl_val" + final_btl_val);
                double total_price_of_product = Double.parseDouble(String.valueOf(final_btl_val + final_case_val));
                MyApplication.logi(LOG_TAG, "total_price_of_product--------" + total_price_of_product);
                String amt1 = String.format("%.2f", total_price_of_product);
                MyApplication.logi(LOG_TAG, "amt1 total_price_of_product--------" + amt1);
                model.setPrice_of_product(amt1);
                //model.setPrice_of_product(String.valueOf(final_btl_val + final_case_val));


                long ret1 = TABLE_TEMP_ORDER_DETAILS.insertOrderDetails(MyApplication.get_session(MyApplication.SESSION_ORDER_ID), model.getItem_id(), model.getProduct_name(), String.valueOf(edited_case_value), String.valueOf(edited_btl_value),
                        String.valueOf(single_btl_value_after_discount), String.valueOf(single_case_value_after_discount), Float.parseFloat(model.getPrice_of_product()), "0", "0", "update_data");

                if (ret1 == 0) {
                    MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS successfully inserted " + ret1);
                    //  Toast.makeText(context, "Updated data successfully...", Toast.LENGTH_SHORT).show();


                } else {
                    MyApplication.logi(LOG_TAG, "TABLE_TEMP_ORDER_DETAILS not successfully inserted " + ret1);
                }

                orderReviewList.remove(pos);
                OrderReviewAdapter.this.notifyItemRemoved(pos);
                orderReviewList.add(pos, model);
                OrderReviewAdapter.this.notifyDataSetChanged();
                OrderReviewAdapter.this.notifyItemInserted(pos);
                popupWindow.dismiss();
                MyApplication.displayMessage(context, "Order is successfully updated.");


                String sum = TABLE_TEMP_ORDER_DETAILS.getSumOfAllItems(MyApplication.get_session(MyApplication.SESSION_ORDER_ID));
                //txt_total_price.setText(sum);
                txt_total_price.setText(lable+sum);
            }
        });


        edt_case_no.setMinWidth(30);
        edt_bottle_no.setMinWidth(30);

        txt_free_case_no.setMinWidth(30);
        txt_free_bottle_no.setMinWidth(30);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            edt_case_no.setBackground(context.getResources().getDrawable(R.drawable.background_border));
            edt_bottle_no.setBackground(context.getResources().getDrawable(R.drawable.background_border));

            txt_free_case_no.setBackground(context.getResources().getDrawable(R.drawable.background_border));
            txt_free_bottle_no.setBackground(context.getResources().getDrawable(R.drawable.background_border));
        } else {
            txt_free_case_no.setBackgroundResource(R.drawable.background_border);
            txt_free_bottle_no.setBackgroundResource(R.drawable.background_border);
        }

    }

    private void getUomLabels() {


        ORDBOOKUOMLABEL = TABLE_SETTINGS.get_value_from_setting("ORDBOOKUOMLABEL"); //"CS/BTL"
        MyApplication.logi(LOG_TAG,"ORDBOOKUOMLABEL----->"+ORDBOOKUOMLABEL);

        String ORDBOOKUOMLABEL_3 = "CS/";
         String ORDBOOKUOMLABEL_2 = "/BTL";
        String ORDBOOKUOMLABEL_1 = "CS/BTL";

        try {
            MyApplication.logi(LOG_TAG, "ORDBOOKUOMLABEL---->" + ORDBOOKUOMLABEL);
            String[] split = ORDBOOKUOMLABEL.split(Pattern.quote("/"));

            if (split.length == 1) {
                MyApplication.logi(LOG_TAG, "spilt length is 1");
                biguom = split[0];
            } else {
                biguom = split[0];
                MyApplication.logi(LOG_TAG, "ORDBOOKUOMLABEL biguom---->" + biguom);
                smalluom = split[1];
                MyApplication.logi(LOG_TAG, "ORDBOOKUOMLABEL smalluom---->" + smalluom);

            }
        } catch (Exception e) {
            MyApplication.logi(LOG_TAG, "ORDBOOKUOMLABEL exception---->" + e.getMessage());
        }

    }
}