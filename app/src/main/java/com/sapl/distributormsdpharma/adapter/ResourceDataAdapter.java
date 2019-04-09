package com.sapl.distributormsdpharma.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.confiq.MyApplication;
import com.sapl.distributormsdpharma.models.ResourceDataModel;

import java.util.List;

public class ResourceDataAdapter extends RecyclerView.Adapter<ResourceDataAdapter.MyViewHolder> {
    String LOG_TAG = "ResourceDataAdapter ";
    private List<ResourceDataModel> resourceDataList;
    Context context;

    public ResourceDataAdapter(Context context, List<ResourceDataModel> resourceDataList) {
        this.resourceDataList = resourceDataList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtResourceTitle, txtResourceDescription;
        public ImageView imgResource;

        public MyViewHolder(View view) {
            super(view);
            txtResourceTitle = (TextView) view.findViewById(R.id.txtResourceTitle);
            txtResourceDescription = (TextView) view.findViewById(R.id.txtResourceDescription);
            imgResource = (ImageView) view.findViewById(R.id.imgResource);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        ResourceDataModel model = resourceDataList.get(pos);
                        String url = model.getURL() + "";
                        if (!TextUtils.isEmpty(url)) {
                            if (!url.startsWith("http://") && !url.startsWith("https://"))
                                url = "http://" + url;
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            context.startActivity(browserIntent);
                        } else
                            MyApplication.displayMessage(context,"Invalid URL is provided...");
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_resource_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ResourceDataModel model = resourceDataList.get(position);

        holder.imgResource.clearAnimation();
        holder.imgResource.clearFocus();
        holder.imgResource.clearColorFilter();

        holder.txtResourceTitle.setText(model.getResourceName() + "");
        holder.txtResourceDescription.setText(model.getDescreption() + "");

        /*Glide.with(context).load(context.getResources().getIdentifier(model.getURL(),
                "drawable", context.getPackageName()))
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imgResource) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.imgResource.setImageDrawable(circularBitmapDrawable);
        }
        });*/


        Glide.with(context)
                .load(model.getURL())
                .fitCenter()
                .error(context.getResources().getIdentifier("zylemini_logo", "drawable", context.getPackageName()))
                .centerCrop()
                .into(holder.imgResource);
    }

    @Override
    public int getItemCount() {
        return resourceDataList == null ? 0 : resourceDataList.size();
    }


}

