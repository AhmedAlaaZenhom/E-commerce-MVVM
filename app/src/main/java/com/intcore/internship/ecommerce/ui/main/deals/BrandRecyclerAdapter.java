package com.intcore.internship.ecommerce.ui.main.deals;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.BrandModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BrandRecyclerAdapter extends RecyclerView.Adapter<BrandRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onBrandClicked(BrandModel brandModel);
    }

    private ArrayList<BrandModel> list;
    private ClickListener clickListener;
    private int parentHeight, parentWidth, dividerSpacing;

    public BrandRecyclerAdapter(Activity activity, ArrayList<BrandModel> list, ClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
        parentHeight = Utils.getDisplayMetrics(activity).widthPixels / 2;
        parentWidth = parentHeight;
        dividerSpacing = (int) Utils.convertDpToPixel(activity, 8);
    }

    @NonNull
    @Override
    public BrandRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_brand, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BrandModel data = list.get(position);
        holder.bindData(data, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView parentCV;
        private ImageView brandIV;

        private MyViewHolder(View v) {
            super(v);
            parentCV = v.findViewById(R.id.parentCV);
            brandIV = v.findViewById(R.id.brandIV);
        }

        private void bindData(BrandModel data, int position) {

            final CardView.LayoutParams layoutParams = new CardView.LayoutParams(parentWidth, parentHeight);
            if (position == 0)
                layoutParams.setMarginStart(dividerSpacing);
            layoutParams.setMarginEnd(dividerSpacing);
            parentCV.setLayoutParams(layoutParams);

            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL + data.getImageUrl(),
                    brandIV,
                    PicassoHelper.CENTER_INSIDE,
                    null,
                    null);

            brandIV.setOnClickListener(v -> {
                if (clickListener != null)
                    clickListener.onBrandClicked(data);
            });
        }
    }

}