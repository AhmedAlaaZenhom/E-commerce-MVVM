package com.intcore.internship.ecommerce.ui.commonClasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.CategoryModel;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onCategoryClicked(CategoryModel categoryModel);
    }

    public static final int MODE_HORIZONTAL = 0, MODE_VERTICAL = 1;

    private ArrayList<CategoryModel> list;
    private ClickListener clickListener;
    private int rvMode;
    private int parentHeight, parentWidth, dividerSpacing;
    private boolean isCurrentLocaleAR;

    public CategoryRecyclerAdapter(boolean isCurrentLocaleAR, Activity activity, int rvMode, ArrayList<CategoryModel> list, ClickListener clickListener) {
        this.isCurrentLocaleAR = isCurrentLocaleAR;
        this.rvMode = rvMode;
        this.list = list;
        this.clickListener = clickListener;
        dividerSpacing = (int) Utils.convertDpToPixel(activity, 8);
        parentWidth = rvMode == MODE_HORIZONTAL ?
                (int) (Utils.getDisplayMetrics(activity).widthPixels * 0.80)
                : Utils.getDisplayMetrics(activity).widthPixels - (2 * dividerSpacing);
        parentHeight = (int) (parentWidth * 0.45);
    }

    @Override
    public CategoryRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryRecyclerAdapter.MyViewHolder holder, int position) {
        CategoryModel data = list.get(position);
        holder.bindData(data, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout categoryRL;
        private TextView categoryNameTV;
        private ImageView categoryIV;
        private CardView parentCV;

        private MyViewHolder(View v) {
            super(v);
            parentCV = v.findViewById(R.id.parentCV);
            categoryRL = v.findViewById(R.id.categoryRL);
            categoryIV = v.findViewById(R.id.categoryIV);
            categoryNameTV = v.findViewById(R.id.categoryNameTV);
        }

        private void bindData(CategoryModel data, int position) {
            CardView.LayoutParams layoutParams = new CardView.LayoutParams(parentWidth, parentHeight);
            if (position == 0 || rvMode == MODE_VERTICAL)
                layoutParams.setMarginStart(dividerSpacing);
            if (rvMode == MODE_VERTICAL)
                layoutParams.topMargin = dividerSpacing;
            layoutParams.setMarginEnd(dividerSpacing);
            parentCV.setLayoutParams(layoutParams);

            categoryNameTV.setText(isCurrentLocaleAR ? data.getNameAR() : data.getNameEN());

            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL + data.getImageURL(),
                    categoryIV,
                    PicassoHelper.CENTER_CROP,
                    null,
                    null);

            categoryRL.setOnClickListener(v -> {
                if (clickListener != null)
                    clickListener.onCategoryClicked(data);
            });
        }

    }

}
