package com.sapl.distributormsdpharma.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PCUSTOMER;
import com.sapl.distributormsdpharma.MyDatabase.TABLE_PDISTRIBUTOR;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.activities.ActivityAproveOrder;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.models.OrderDeliveryStatusModel;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Ganesh Borse on 20-Jan-18.
 */


public class OrderDelStatusAdapter extends RecyclerView.Adapter<OrderDelStatusAdapter.MyViewHolder> {
    String  LOG_TAG= "OrderDelStatusAdapter ";
    private List<OrderDeliveryStatusModel> orderDeliveryStatusList;
    Context context;
    private final int CALL_REQUEST = 100;

    public OrderDelStatusAdapter(Context context, List<OrderDeliveryStatusModel> orderDeliveryStatusList) {
        this.orderDeliveryStatusList = orderDeliveryStatusList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_shop_name, txt_card_number, txt_rupees, img_rupee;
        public CustomTextViewMedium txt_date, txt_order_status; //, balance; //address, contact
        public ImageView img_name, img_call,  img_info ;//, img_vechle;
        // public CustomTextViewMedium processing, delivered;

        public MyViewHolder(View view) {
            super(view);
            txt_shop_name = view.findViewById(R.id.txt_shop_name);
            txt_shop_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

            txt_date = view.findViewById(R.id.txt_date);
            txt_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

            txt_order_status = view.findViewById(R.id.txt_order_status);
            txt_order_status.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

            txt_card_number = view.findViewById(R.id.txt_cart_number);
            txt_card_number.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

            txt_rupees = view.findViewById(R.id.txt_rupees);
            txt_rupees.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

            img_rupee = view.findViewById(R.id.img_rupee);
            img_rupee.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

            img_name = (ImageView) view.findViewById(R.id.img_name);
            img_call = (ImageView) view.findViewById(R.id.img_call);


            img_info =  (ImageView) view.findViewById(R.id.img_info);
            img_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        OrderDeliveryStatusModel model = orderDeliveryStatusList.get(pos);
                        //   String mobNo = ""+model.getMobileNo();
                        String outletInfo = TABLE_PCUSTOMER.getOutletInfo(model.getRetailerId());
                        displayInformation(outletInfo);
                    }
                }
            });


            img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        OrderDeliveryStatusModel model = orderDeliveryStatusList.get(pos);
                        //   String mobNo = ""+model.getMobileNo();
                        String outletInfo = TABLE_PCUSTOMER.getOutletInfo( model.getRetailerId() );
                        String mobNo = getMobileNo(outletInfo);
                        MyApplication.logi(LOG_TAG ," MOB NO: "+mobNo);

                        if( mobNo != null ) {
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        ActivityCompat.requestPermissions((Activity) context,
                                                new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST);
                                        return;
                                    }
                                    makeCall(mobNo);
                                } else {
                                    makeCall(mobNo);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            });

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        OrderDeliveryStatusModel model = dastributorList.get(pos);
                        //  Toast.makeText(v.getContext(), "You clicked " + model.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ActivityAproveOrder.class);
                        intent.putExtra("ORDER_ID", model.getOrderId()+"" );
                        intent.putExtra("TOTAL_PRICE", model.getAmount()+"" );
                        intent.putExtra("SHOP_NAME", model.getShopName()+"" );
                        intent.putExtra("RETAILER_ID", model.getRetailerId());  // TABLE_PCUSTOMER ==> CustomerId
                        if (model.getOrderStatus().contains("1")) {
                            intent.putExtra("STATUS", "Pending");
                        } else if (model.getOrderStatus().contains("2")) {
                            intent.putExtra("STATUS", "Delivered");
                        } else {
                            intent.putExtra("STATUS", "Rejected");
                        }
                        context.startActivity(intent);
                    }
                }
            });*/
        }
    }

    @Override
    public OrderDelStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shop_list, parent, false);
        return new OrderDelStatusAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderDelStatusAdapter.MyViewHolder holder, final int position) {
        final OrderDeliveryStatusModel model = orderDeliveryStatusList.get(position);

       /* holder.img_name.clearAnimation();
        holder.img_name.clearFocus();
        holder.img_name.clearColorFilter();*/

        holder.txt_shop_name.setText(model.getShopName());
        holder.txt_card_number.setText(model.getTotalQtys());
        holder.txt_date.setText(model.getOrderDate());
        holder.txt_order_status.setText(model.getOrderStatus());
        holder.txt_rupees.setText(model.getAmount());
        holder.txt_card_number.setText(model.getTotalQtys());
        MyApplication.logi(LOG_TAG,"count is -->"+model.getTotalQtys());

        if (model.getOrderStatus().contains("1")) {
            holder.txt_order_status.setText("Pending");
        } else if (model.getOrderStatus().contains("2")) {
            holder.txt_order_status.setText("Despatched");//  holder.txt_order_status.setText("Delivered");
        } else if (model.getOrderStatus().contains("3")) {
            holder.txt_order_status.setText("Rejected");
        }
        // holder.img_name.setText(model.getImageName());

     /*   Glide.with(context).load(context.getResources().getIdentifier(model.getDistImagePath(),
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_name) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img_name.setImageDrawable(circularBitmapDrawable);
            }
        });*/


        Glide.with(context)
                .load(context.getResources().getIdentifier("shop", "drawable", context.getPackageName()))
                .fitCenter()
                .centerCrop()
                .into(holder.img_name);

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
        return orderDeliveryStatusList == null ? 0 : orderDeliveryStatusList.size();
    }


   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults)
    {
        if(requestCode == CALL_REQUEST)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
              //  callPhoneNumber();
            }
            else
            {
               // Toast.makeText(MainActivity.this, getResources().getString(R.string.call_permission_denied_message), Toast.LENGTH_SHORT).show();
            }
        }
    }*/



    private String getMobileNo(String outlet_info) {
        /*String location = "";
        if (outlet_info.length() != 0) {
            String[] split = outlet_info.split(Pattern.quote("||"));

            String arrLocation[] = split[0].split("Area:");
            MyApplication.logi(LOG_TAG, "Area:->" + (arrLocation[1]));

            location = arrLocation[1];

            ///here set only frst form address

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
        }
        return location;*/

        String mobNo = "";
        if (outlet_info.length() != 0) {
            String[] split = outlet_info.split(Pattern.quote("||"));

            String contactString = split[2];
            contactString.replaceAll("\"","");
            contactString.replaceAll(" ","");
            String contactStringSplits[] = contactString.split(",");
            String mobile = contactStringSplits[0].replace("Contact No: Mobile-","");
            String landline = contactStringSplits[1].replace("Landline:","");

            if(mobile.length() >= 10 )
                mobNo = mobile;
            else
                mobNo = landline;
        }
        return mobNo;
    }


    public void displayInformation(String info) {
        info = info.replaceAll("\\|\\|", System.getProperty("line.separator"));
        MyApplication.logi(LOG_TAG ," displayInformation() INFO--> "+ info);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(context.getResources().getString(R.string.app_name))
                .setMessage("Information : " +System.getProperty("line.separator")+ info)
                .setIcon(R.drawable.distributor_call)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })   //Do nothing on no
                .show();
    }

    public void makeCall(final String mobNo){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                //  .setTitle(name.toString())
                .setMessage("Make Call" )
                .setIcon(R.drawable.distributor_call)
                .setPositiveButton(mobNo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + mobNo.trim()));
                        context.startActivity(callIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })                        //Do nothing on no
                .show();
    }
}
