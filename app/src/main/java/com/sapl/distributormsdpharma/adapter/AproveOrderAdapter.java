package com.sapl.distributormsdpharma.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_ORDER_DETAILS;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomButtonRegular;
import com.sapl.distributormsdpharma.customView.CustomEditTextMedium;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.models.AproveOrderModel;

import java.util.List;

/**
 * Created by Ganesh Borse on 12-Feb-18.
 */

public class AproveOrderAdapter extends RecyclerView.Adapter<AproveOrderAdapter.MyViewHolder> {
    private List<AproveOrderModel> orderReviewList;
    Context context;
    private static final String LOG_TAG = "DistributorDataAdapter";
    String location = "";

    public AproveOrderAdapter(Context context, List<AproveOrderModel> orderReviewList) {
        this.orderReviewList = orderReviewList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_product_name, txt_price_of_product, txt_case_no, txt_bottle_no,
                txt_free_case_no, txt_free_bottle_no;
        public CustomTextViewMedium txt_case_label, txt_bottle_label, txt_free_case_label, txt_free_bottle_label;
        public ImageView img_product, img_edt; //, img_delete;

        public MyViewHolder(View view) {
            super(view);

            txt_product_name = view.findViewById(R.id.txt_product_name);
            txt_price_of_product = view.findViewById(R.id.txt_price_of_product);
            txt_case_no = view.findViewById(R.id.txt_case_no);
            txt_bottle_no = view.findViewById(R.id.txt_bottle_no);
            txt_free_case_no = view.findViewById(R.id.txt_free_case_no);
            txt_free_bottle_no = view.findViewById(R.id.txt_free_bottle_no);

            img_product = (ImageView) view.findViewById(R.id.img_product);
            img_edt = (ImageView) view.findViewById(R.id.img_edt);
            //img_delete = (ImageView) view.findViewById(R.id.img_delete);


            txt_product_name.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_case_no.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_free_case_no.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_bottle_no.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_free_bottle_no.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_price_of_product.setTextColor(context.getResources().getColor(R.color.heading_background));

            txt_case_label = view.findViewById(R.id.txt_case_label);
            txt_bottle_label = view.findViewById(R.id.txt_bottle_label);
            txt_free_case_label = view.findViewById(R.id.txt_free_case_label);
            txt_free_bottle_label = view.findViewById(R.id.txt_free_bottle_label);

            txt_case_label.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_bottle_label.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_free_case_label.setTextColor(context.getResources().getColor(R.color.heading_background));
            txt_free_bottle_label.setTextColor(context.getResources().getColor(R.color.heading_background));


            img_edt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        AproveOrderModel model = orderReviewList.get(pos);

                        editOrder(model, pos);
                    }
                }
            });

           /* img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        orderReviewList.remove(pos);
                        AproveOrderAdapter.this.notifyItemRemoved(pos);
                        MyApplication.displayMessage(context, "Order deleted successfully.");

                    }
                }
            });*/

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        AproveOrderModel model = orderReviewList.get(pos);
                        // Toast.makeText(v.getContext(), "You clicked " + model.getName(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(v.getContext(), "You clicked" + model.getDist_id(), Toast.LENGTH_SHORT).show();

                        /* String outlet_info = TABLE_PDISTRIBUTOR.getOutletInfo(MyApplication.get_session(MyApplication.SESSION_DISTRIBUTOR_ID));
                         MyApplication.logi(LOG_TAG,"OUTLET DATA IS-->"+outlet_info);
                         if(outlet_info.length() !=0 ){
                             String[] split = outlet_info.split(Pattern.quote("||"));

                             String arrLocation[] = split[0].split("Area:");
                             MyApplication.logi(LOG_TAG, "Area:->" + (arrLocation[1]));

                             MyApplication.logi(LOG_TAG,"SPILTED IS-->"+split.toString());
                             int len = split.length;
                             String contactinfo2 = "";
                             for (int i = 0; i < len; i++) {
                                 if (i == 0) {
                                     contactinfo2 = split[i];
                                 } else if (i == len - 1) {

                                     String OriginalString = split[i];
                                     MyApplication.logi(LOG_TAG, "OriginalString" + OriginalString);
                                     OriginalString = OriginalString.replaceAll(" ", "");
                                     String arrCom[] = OriginalString.split(",");
                                     MyApplication.logi(LOG_TAG, "OriginalString" + Arrays.deepToString(arrCom));

                                     String arrMob[] = arrCom[0].split("Mobile-");
                                     MyApplication.logi(LOG_TAG, "arrMob->" + Arrays.deepToString(arrMob));
                                     String arLand[] = arrCom[1].split("Landline:");
                                     MyApplication.logi(LOG_TAG, "arLand->" + Arrays.deepToString(arLand));

                                     break;

                                 } else {
                                     contactinfo2 = contactinfo2 + "|" + split[i];
                                 }
                             }
                         }
*/
                    }
                }
            });
        }
    }

    @Override
    public AproveOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_aprove_order, parent, false);
        return new AproveOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AproveOrderAdapter.MyViewHolder holder, final int position) {
        final AproveOrderModel model = orderReviewList.get(position);

        holder.img_product.clearAnimation();
        holder.img_product.clearFocus();
        holder.img_product.clearColorFilter();

        // MyApplication.log("NAME: ---------->  "+model.getName()+" ****************");
        holder.txt_bottle_no.setText(model.getBoittle_no());
        holder.txt_case_no.setText(model.getCase_no());
        holder.txt_free_case_no.setText(model.getFree_case_no());
        holder.txt_free_bottle_no.setText(model.getFree_bottle_no());

        holder.txt_product_name.setText(model.getProduct_name());
        holder.txt_price_of_product.setText("₹ " + model.getPrice_of_product());

       /* String outlet_info = TABLE_PDISTRIBUTOR.getOutletInfo();
        MyApplication.logi(LOG_TAG, "OUTLET DATA IS-->" + outlet_info);*/
        /*if (outlet_info.length() != 0) {
            String[] split = outlet_info.split(Pattern.quote("||"));


            String arrLocation[] = split[0].split("Area:");
            MyApplication.logi(LOG_TAG, "Area:->" + (arrLocation[1]));

            location = arrLocation[1];

            ///here set only frst form address
            holder.txt_distributor_subtitle.setText(location);

            MyApplication.logi(LOG_TAG, "SPILTED IS-->" + split.toString());
            int len = split.length;
            String contactinfo2 = "";
            for (int i = 0; i < len; i++) {
                if (i == 0) {
                    contactinfo2 = split[i];
                } else if (i == len - 1) {

                    String OriginalString = split[i];
                    MyApplication.logi(LOG_TAG, "OriginalString" + OriginalString);
                    OriginalString = OriginalString.replaceAll(" ", "");
                    String arrCom[] = OriginalString.split(",");
                    MyApplication.logi(LOG_TAG, "OriginalString" + Arrays.deepToString(arrCom));

                    String arrMob[] = arrCom[0].split("Mobile-");
                    MyApplication.logi(LOG_TAG, "arrMob->" + Arrays.deepToString(arrMob));
                    String arLand[] = arrCom[1].split("Landline:");
                    MyApplication.logi(LOG_TAG, "arLand->" + Arrays.deepToString(arLand));

                    break;

                } else {
                    contactinfo2 = contactinfo2 + "|" + split[i];
                }
            }
        }*/


        // holder.img_name.setText(model.getImageName());




        if (model.getImageBlob() == null) {
            Glide.with(context).load(context.getResources().getIdentifier(model.getProduct_img_path(),
                    "mipmap", context.getPackageName()))
                    .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_product) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.img_product.setImageDrawable(circularBitmapDrawable);
                }
            });
        }else {
            byte[] decodeString = Base64.decode(model.getImageBlob(), Base64.DEFAULT);
            //Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            Glide.with(context)
                    .load(decodeString)
                    .asBitmap()
                    .error(R.drawable.app_icon)
                    .placeholder(R.drawable.app_icon)
                    .into(holder.img_product);
        }


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


    public void editOrder(final AproveOrderModel model, final int pos) {

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

        final CustomEditTextMedium edt_case_no, edt_bottle_no;
        final CustomTextViewMedium txt_title, txt_product_name, txt_case_lable, txt_free_case_lable, txt_free_case_no;
        final CustomTextViewMedium txt_bottle_lable, txt_free_bottle_lable, txt_free_bottle_no;

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
        edt_case_no.setMinWidth(40);
        edt_bottle_no.setMinWidth(40);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            edt_case_no.setBackground(context.getResources().getDrawable(R.drawable.background_border));
            edt_bottle_no.setBackground(context.getResources().getDrawable(R.drawable.background_border));
            txt_free_case_no.setBackground(context.getResources().getDrawable(R.drawable.background_border));
            txt_free_bottle_no.setBackground(context.getResources().getDrawable(R.drawable.background_border));
        } else {
            edt_case_no.setBackgroundResource(R.drawable.background_border);
            edt_bottle_no.setBackgroundResource(R.drawable.background_border);
            txt_free_case_no.setBackgroundResource(R.drawable.background_border);
            txt_free_bottle_no.setBackgroundResource(R.drawable.background_border);
        }

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
                popupWindow.dismiss();
            }
        });

        CustomButtonRegular btn_update = popupView.findViewById(R.id.btn_update);
        // if button is clicked, close the custom dialog
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bottelNo= 0, caseNo = 0, freeBottel = 0, freeCase =0;

                if(!TextUtils.isEmpty(edt_bottle_no.getText())){
                    bottelNo = Integer.parseInt(edt_bottle_no.getText().toString().trim());
                    freeBottel = Integer.parseInt(txt_free_bottle_no.getText().toString().trim());
                }

                if(!TextUtils.isEmpty(edt_case_no.getText())){
                    caseNo = Integer.parseInt(edt_case_no.getText().toString().trim());
                    freeCase = Integer.parseInt(txt_free_case_no.getText().toString().trim());
                }

                model.setBoittle_no("" +bottelNo );
                model.setFree_bottle_no("" +freeBottel );
                model.setCase_no("" +caseNo );
                model.setFree_case_no("" +freeCase );

                TABLE_ORDER_DETAILS.updateOrder(model);

                orderReviewList.remove(pos);
                AproveOrderAdapter.this.notifyItemRemoved(pos);
                orderReviewList.add(pos, model);
                AproveOrderAdapter.this.notifyDataSetChanged();
                AproveOrderAdapter.this.notifyItemInserted(pos);
                popupWindow.dismiss();
                MyApplication.displayMessage(context, "Order is successfully updated.");

                // Toast.makeText(getApplicationContext(), "Data get Updated successfully.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}