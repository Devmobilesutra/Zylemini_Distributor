package com.sapl.distributormsdpharma.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.activities.ActivitySelection;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.customView.CustomTextViewMedium;
import com.sapl.distributormsdpharma.models.BrandModel;

import java.util.List;

import static com.sapl.distributormsdpharma.activities.ActivitySelection.tabHost;


/**
 * Created by MRUNAL on 07-Feb-18.
 */

/*public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {*/

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {
    private List<BrandModel> selectionList;
    Context context;
    public static String LOG_TAG = "BrandAdapter";

    public BrandAdapter(Context context, List<BrandModel> selectionList) {
        this.selectionList = selectionList;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextViewMedium txt_selection_name, txt_selection_description, txt_selection_offer;

        public ImageView img_selection;

        public MyViewHolder(View view) {
            super(view);

            txt_selection_name = view.findViewById(R.id.txt_selection_name);
            txt_selection_description = view.findViewById(R.id.txt_selection_description);
            txt_selection_name.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
            txt_selection_description.setTextColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_TEXT_COLOR)));

            txt_selection_offer = view.findViewById(R.id.txt_selection_offer);
            img_selection = (ImageView) view.findViewById(R.id.img_selection);
            img_selection.setColorFilter(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
            /*txt_selection_name2 = view.findViewById(R.id.txt_selection_name2);
            txt_selection_description2 = view.findViewById(R.id.txt_selection_description2);
            txt_selection_offer2 = view.findViewById(R.id.txt_selection_offer2);
            img_selection2 = (ImageView) view.findViewById(R.id.img_selection2);*/


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        BrandModel model = selectionList.get(pos);

                        //Toast.makeText(v.getContext(), "You clicked brand -->" + model.getDivision_id(), Toast.LENGTH_SHORT).show();
                        MyApplication.set_session(MyApplication.SESSION_BRAND_ID, model.getDivision_id() + "");
                        MyApplication.set_session("new_brand_is_selected","yes");
                        if (tabHost.getCurrentTab() < 3) {


                            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(R.id.title);
                            tv.setText(model.getDivision());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                tabHost.getCurrentTabView().setBackground(context.getResources().getDrawable(R.drawable.background_border_white));
                            else
                                tabHost.getCurrentTabView().setBackgroundResource(R.drawable.border_background_processing);
                            tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
                            MyApplication.set_session("direct_item","no");
                           // ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("item_title"));
                        } else {
                            //ActivitySelection.txt_title_with_header.setText(MyApplication.get_session("item_title"));
                            /*Intent intent = new Intent(context, ActivityDistributorList.class);
                            //context.finish();
                            //context.overridePendingTransition(R.anim.fade_in_call, R.anim.fade_out_call);
                            context.startActivity(intent);*/
                        }

                    }
                }
            });
        }
    }

    @Override
    public BrandAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_selection, parent, false);
        return new BrandAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BrandAdapter.MyViewHolder holder, final int position) {
        final BrandModel model = selectionList.get(position);


        holder.txt_selection_name.setText(model.getDivision());



       /* holder.img_selection.clearAnimation();
        holder.img_selection.clearFocus();
        holder.img_selection.clearColorFilter();

        holder.txt_selection_name.setText(model.getSelectionName());
        holder.txt_selection_description.setText(model.getSelectionDescription());
        holder.txt_selection_offer.setText(model.getSelectionOffer());;

        Glide.with(context).load(context.getResources().getIdentifier(model.getSelectionImg(),
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_selection) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img_selection.setImageDrawable(circularBitmapDrawable);
            }
        });*/





        /*else {
            holder.img_selection2.clearAnimation();
            holder.img_selection2.clearFocus();
            holder.img_selection2.clearColorFilter();

            holder.txt_selection_name2.setText(model.getSelectionName());
            holder.txt_selection_description2.setText(model.getSelectionDescription());
            holder.txt_selection_offer2.setText(model.getSelectionOffer());;

            Glide.with(context).load(context.getResources().getIdentifier(model.getSelectionImg(),
                    "drawable", context.getPackageName()))
                    .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.img_selection2) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.img_selection2.setImageDrawable(circularBitmapDrawable);
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
        return selectionList == null ? 0 : selectionList.size();
    }
}