package com.sapl.distributormsdpharma.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.activities.ActivityAproveOrder;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.models.DistributorModel;

import java.util.List;

/**
 * Created by Ganesh Borse on 20-Jan-18.
 */


public class DistributorDataAdapter extends RecyclerView.Adapter<DistributorDataAdapter.MyViewHolder> {
    private List<DistributorModel> dastributorList;
    Context context;

    public DistributorDataAdapter(Context context, List<DistributorModel> dastributorList) {
        this.dastributorList = dastributorList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_shop_name,txt_card_number,txt_rupees;
        public CustomTextViewMedium txt_date,txt_order_status; //, balance; //address, contact
        public ImageView img_name;

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


            img_name = (ImageView) view.findViewById(R.id.img_name);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        DistributorModel model = dastributorList.get(pos);
                        Toast.makeText(v.getContext(), "You clicked " + model.getName(), Toast.LENGTH_SHORT).show();


                        //MyApplication.set_session("color","#0000FF");
                        Intent intent = new Intent(context, ActivityAproveOrder.class);
                        context.startActivity(intent);

                        // context.finish();

                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shop_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DistributorModel model = dastributorList.get(position);
       /* holder.img_name.clearAnimation();
        holder.img_name.clearFocus();
        holder.img_name.clearColorFilter();*/
        holder.txt_shop_name.setText(model.getName());
        holder.txt_card_number.setText(model.getcartValue());
        holder.txt_date.setText(model.getdate());
        holder.txt_order_status.setText(model.getorderStatus());
        holder.txt_rupees.setText(model.getAmt());
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
        return dastributorList == null ? 0 : dastributorList.size();
    }
}