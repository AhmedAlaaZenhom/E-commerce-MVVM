package com.intcore.internship.ecommerce.ui.commonClasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onProductImageClicked(ProductModel productModel);

        void onProductFavClicked(ProductModel productModel);
    }

    public static final int MODE_HORIZONTAL = 0, MODE_GRID = 1;

    private int rvMode;
    private ArrayList<ProductModel> list;
    private ClickListener clickListener;
    private int parentHeight, parentWidth, horizontalModeSpacing, gridModeSpacing;
    private boolean isCurrentLocaleAR;

    public ProductRecyclerAdapter(boolean isCurrentLocaleAR, Activity activity, int rvMode, ArrayList<ProductModel> list, ClickListener clickListener) {
        this.isCurrentLocaleAR = isCurrentLocaleAR;
        this.rvMode = rvMode;
        this.list = list;
        this.clickListener = clickListener;
        parentHeight = Utils.getDisplayMetrics(activity).widthPixels / 2;
        parentWidth = (int) (parentHeight * 0.8);
        horizontalModeSpacing = (int) Utils.convertDpToPixel(activity, 8);
        // Remainder space divided by two for each span item
        gridModeSpacing = (int) (parentHeight * 0.2 / 2);
    }

    public int getGridModeSpacing() {
        return gridModeSpacing;
    }

    @Override
    public ProductRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductRecyclerAdapter.MyViewHolder holder, int position) {
        ProductModel data = list.get(position);
        holder.bindData(data, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout parentCL;
        private ImageView productIV;
        private ImageView favIV;
        private TextView productNameTV;
        private TextView productPriceTV;

        private MyViewHolder(View v) {
            super(v);
            parentCL = v.findViewById(R.id.parentCL);
            productIV = v.findViewById(R.id.productIV);
            favIV = v.findViewById(R.id.favIV);
            productNameTV = v.findViewById(R.id.productNameTV);
            productPriceTV = v.findViewById(R.id.productPriceTV);
        }

        private void bindData(ProductModel data, int position) {

            final ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(parentWidth, parentHeight);
            if (rvMode == MODE_HORIZONTAL) {
                if (position == 0)
                    layoutParams.setMarginStart(horizontalModeSpacing);
                layoutParams.setMarginEnd(horizontalModeSpacing);
            }
            parentCL.setLayoutParams(layoutParams);

            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL + data.getDefaultImage(),
                    productIV,
                    PicassoHelper.CENTER_INSIDE,
                    null,
                    null);

            favIV.setImageResource(data.isFav() ? R.drawable.heart_filled : R.drawable.heart_empty);
            productNameTV.setText(isCurrentLocaleAR ? data.getNameAR() : data.getNameEN());
            productPriceTV.setText("$" + String.format("%.1f", data.getPrice()));

            productIV.setOnClickListener(v -> {
                if (clickListener != null)
                    clickListener.onProductImageClicked(data);
            });
            favIV.setOnClickListener(v -> {
                data.setFav(!data.isFav());
                notifyDataSetChanged();
                if (clickListener != null)
                    clickListener.onProductFavClicked(data);
            });
        }
    }

}
