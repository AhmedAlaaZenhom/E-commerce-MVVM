package com.intcore.internship.ecommerce.ui.main.wishlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class WishListRecyclerAdapter extends RecyclerView.Adapter<WishListRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onItemClicked(ProductModel productModel);

        void onRemoveItemClicked(ProductModel productModel);
    }

    private ArrayList<ProductModel> list;
    private ClickListener clickListener;
    private int parentHeight;
    private boolean isCurrentLocaleAR;

    WishListRecyclerAdapter(boolean isCurrentLocaleAR, Activity activity, ArrayList<ProductModel> list, ClickListener clickListener) {
        this.isCurrentLocaleAR = isCurrentLocaleAR;
        this.list = list;
        this.clickListener = clickListener;
        parentHeight = (int) (Utils.getDisplayMetrics(activity).widthPixels * 0.32);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_wish_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductModel data = list.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView parentCV;
        private ImageView productIV;
        private TextView productNameTV;
        private TextView productPriceTV;
        private ImageView removeFromCartIV;

        private MyViewHolder(View v) {
            super(v);
            parentCV = v.findViewById(R.id.parentCV);
            productIV = v.findViewById(R.id.productIV);
            productNameTV = v.findViewById(R.id.productNameTV);
            productPriceTV = v.findViewById(R.id.productPriceTV);
            removeFromCartIV = v.findViewById(R.id.removeFromCartIV);

            parentCV.getLayoutParams().height = parentHeight;
        }

        private void bindData(ProductModel data) {
            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL + data.getDefaultImage(),
                    productIV,
                    PicassoHelper.JUST_FIT,
                    null,
                    null);

            productNameTV.setText(isCurrentLocaleAR ? data.getNameAR() : data.getNameEN());
            productPriceTV.setText("$" + String.format("%.1f", data.getPrice()));

            parentCV.setOnClickListener(v -> {
                if (clickListener != null)
                    clickListener.onItemClicked(data);
            });

            removeFromCartIV.setOnClickListener(v -> {
                if (clickListener != null)
                    clickListener.onRemoveItemClicked(data);
            });
        }
    }

}
