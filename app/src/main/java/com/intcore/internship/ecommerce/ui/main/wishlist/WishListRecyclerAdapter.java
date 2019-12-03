package com.intcore.internship.ecommerce.ui.main.wishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WishListRecyclerAdapter extends RecyclerView.Adapter<WishListRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onBuyItemNowClicked(ProductModel productModel);
        void onRemoveItemClicked(ProductModel productModel);
    }

    private ArrayList<ProductModel> list;
    private ClickListener clickListener;

    public WishListRecyclerAdapter(ArrayList<ProductModel> list, ClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
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

        private ImageView productIV ;
        private TextView productNameTV ;
        private TextView productPriceTV ;
        private TextView productPriceBeforeOfferTV ;
        private ImageView removeFromCartIV ;
        private Button buyNowBtn ;

        private MyViewHolder(View v) {
            super(v);
            productIV = v.findViewById(R.id.productIV);
            productNameTV = v.findViewById(R.id.productNameTV);
            productPriceTV = v.findViewById(R.id.productPriceTV);
            productPriceBeforeOfferTV = v.findViewById(R.id.productPriceBeforeOfferTV);
            removeFromCartIV = v.findViewById(R.id.removeFromCartIV);
            buyNowBtn = v.findViewById(R.id.buyNowBtn);
        }

        private void bindData(ProductModel data) {
            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL+data.getDefaultImage(),productIV);
            productNameTV.setText(data.getNameEN());
            productPriceTV.setText("$" + String.format("%.1f", data.getPrice()));
            buyNowBtn.setOnClickListener(v -> {
                if(clickListener!=null)
                    clickListener.onBuyItemNowClicked(data);
            });
            removeFromCartIV.setOnClickListener(v -> {
                if(clickListener!=null)
                    clickListener.onRemoveItemClicked(data);
            });
        }
    }

}
