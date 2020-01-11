package com.intcore.internship.ecommerce.ui.main.cart;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.CartItemModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.Utils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onCartItemQuantityIncrement(CartItemModel cartItemModel);
        void onCartItemQuantityDecrement(CartItemModel cartItemModel);
        void onRemoveFromCartClicked(CartItemModel cartItemModel);
    }

    private ArrayList<CartItemModel> list;
    private ClickListener clickListener;
    private int parentHeight;
    private boolean isCurrentLocaleAR;

    public CartRecyclerAdapter(boolean isCurrentLocaleAR, Activity activity, ArrayList<CartItemModel> list, ClickListener clickListener) {
        this.isCurrentLocaleAR = isCurrentLocaleAR;
        this.list = list;
        this.clickListener = clickListener;
        parentHeight = (int) (Utils.getDisplayMetrics(activity).widthPixels * 0.30);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_cart_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CartItemModel data = list.get(position);
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
        private ImageView addQtyIV ;
        private TextView quantityTV ;
        private ImageView minusQtyIV ;

        private MyViewHolder(View v) {
            super(v);
            productIV = v.findViewById(R.id.productIV);
            productNameTV = v.findViewById(R.id.productNameTV);
            productPriceTV = v.findViewById(R.id.productPriceTV);
            productPriceBeforeOfferTV = v.findViewById(R.id.productPriceBeforeOfferTV);
            removeFromCartIV = v.findViewById(R.id.removeFromCartIV);
            addQtyIV = v.findViewById(R.id.addQtyIV);
            quantityTV = v.findViewById(R.id.quantityTV);
            minusQtyIV = v.findViewById(R.id.minusQtyIV);

            v.findViewById(R.id.parentCV).getLayoutParams().height = parentHeight;
        }

        private void bindData(CartItemModel data) {
            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL + data.getProductModel().getDefaultImage(),
                    productIV,
                    PicassoHelper.JUST_FIT,
                    null,
                    null);

            productNameTV.setText(isCurrentLocaleAR?data.getProductModel().getNameAR():data.getProductModel().getNameEN());
            productPriceTV.setText("$" + String.format("%.1f", data.getProductModel().getPrice()));
            quantityTV.setText(String.valueOf(data.getQuantity()));

            addQtyIV.setOnClickListener(v -> {
                if(clickListener!=null)
                    clickListener.onCartItemQuantityIncrement(data);
            });
            minusQtyIV.setOnClickListener(v -> {
                if(clickListener!=null&&data.getQuantity()>1)
                    clickListener.onCartItemQuantityDecrement(data);
            });
            removeFromCartIV.setOnClickListener(v -> {
                if(clickListener!=null)
                    clickListener.onRemoveFromCartClicked(data);
            });
        }
    }

}
