package com.intcore.internship.ecommerce.ui.checkout;

import android.app.Activity;
import android.content.Context;
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

public class ShipmentRecyclerAdapter extends RecyclerView.Adapter<ShipmentRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CartItemModel> list;
    private int parentHeight;
    private boolean isCurrentLocaleAR;

    ShipmentRecyclerAdapter(boolean isCurrentLocaleAR, Activity activity, ArrayList<CartItemModel> list) {
        this.isCurrentLocaleAR = isCurrentLocaleAR;
        this.context = activity;
        this.list = list;
        parentHeight = (int) (Utils.getDisplayMetrics(activity).widthPixels * 0.28);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_shipment_cart_item, parent, false);
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
        private ImageView productIV;
        private TextView productNameTV;
        private TextView productPriceTV;
        private TextView quantityTV;

        private MyViewHolder(View v) {
            super(v);
            productIV = v.findViewById(R.id.productIV);
            productNameTV = v.findViewById(R.id.productNameTV);
            productPriceTV = v.findViewById(R.id.productPriceTV);
            quantityTV = v.findViewById(R.id.quantityTV);

            v.findViewById(R.id.parentCV).getLayoutParams().height = parentHeight;
        }

        private void bindData(CartItemModel data) {
            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL + data.getProductModel().getDefaultImage(),
                    productIV,
                    PicassoHelper.JUST_FIT,
                    null,
                    null);

            productNameTV.setText(isCurrentLocaleAR ? data.getProductModel().getNameAR() : data.getProductModel().getNameEN());
            productPriceTV.setText("$" + String.format("%.1f", data.getProductModel().getPrice()));
            quantityTV.setText(context.getString(R.string.quantity) + " " + String.valueOf(data.getQuantity()));
        }

    }

}