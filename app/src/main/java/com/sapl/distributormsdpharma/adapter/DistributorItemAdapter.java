package com.sapl.distributormsdpharma.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.models.ItemModel;

import java.util.List;

/**
 * Created by Sony on 01/02/2018.
 */

public class DistributorItemAdapter  extends RecyclerView.Adapter<DistributorItemAdapter.MyViewHolder> {


    private List<ItemModel> dastributorList;
    Context context;

    public DistributorItemAdapter(Context context, List<ItemModel> dastributorList) {
        this.dastributorList = dastributorList;
        MyApplication.logi("Mrunal","DISTRIBUTIR LIST-->"+dastributorList);
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_specific_item_name,txt_case,txt_quantity,txt_Btl,txt_btl_quantity,txt_specific_item_cost, txt_card_number, txt_rupees;
        public CustomTextViewMedium txt_date, txt_order_status; //, balance; //address, contact
        public ImageView img_name;

        public MyViewHolder(View view) {
            super(view);

            txt_specific_item_name = view.findViewById(R.id.txt_specific_item_name);
            txt_specific_item_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


            txt_case= view.findViewById(R.id.txt_case);
            txt_case.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


            txt_quantity= view.findViewById(R.id.txt_quantity);
            txt_quantity.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));



            txt_Btl= view.findViewById(R.id.txt_Btl);
            txt_Btl.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));



            txt_btl_quantity= view.findViewById(R.id.txt_btl_quantity);
            txt_btl_quantity.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


            txt_specific_item_cost= view.findViewById(R.id.txt_specific_item_cost);
            txt_specific_item_cost.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


          /*  txt_date = view.findViewById(R.id.txt_date);
            txt_date.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));

            txt_order_status = view.findViewById(R.id.txt_order_status);
            txt_order_status.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


            txt_card_number = view.findViewById(R.id.txt_cart_number);
            txt_card_number.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


            txt_rupees = view.findViewById(R.id.txt_rupees);
            txt_rupees.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));


            img_name = (ImageView) view.findViewById(R.id.img_name);*/


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        ItemModel model = dastributorList.get(pos);
                       // Toast.makeText(v.getContext(), "You clicked " + model.getName(), Toast.LENGTH_SHORT).show();


                        //MyApplication.set_session("color","#0000FF");
                     /*   Intent intent = new Intent(context, ActivityAproveOrder.class);
                        context.startActivity(intent);
*/
                        // context.finish();

                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        final ItemModel model = dastributorList.get(position);
      /*  holder.img_name.clearAnimation();
        holder.img_name.clearFocus();
        holder.img_name.clearColorFilter();*/
        holder.txt_specific_item_name.setText(model.getitem_name());
        holder.txt_case.setText(model.getquantity_type());
        holder.txt_Btl.setText(model.getQuantity_sub_type());
        holder.txt_quantity.setText(model.getorderedQuantity_cases());
        holder.txt_btl_quantity.setText(model.getorderedQuantity_bottles());
        holder.txt_specific_item_cost.setText(model.getTotal_amt());
        // holder.img_name.setText(model.getImageName());


       /* Glide.with(context).load(context.getResources().getIdentifier(model.getDistImagePath(),
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_name) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img_name.setImageDrawable(circularBitmapDrawable);
            }
        });
*/
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
