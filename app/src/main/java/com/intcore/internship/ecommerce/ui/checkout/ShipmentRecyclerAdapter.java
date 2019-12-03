package com.intcore.internship.ecommerce.ui.checkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.CartItemModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShipmentRecyclerAdapter extends RecyclerView.Adapter<ShipmentRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CartItemModel> list;

    public ShipmentRecyclerAdapter(Context context, ArrayList<CartItemModel> list) {
        this.context = context;
        this.list = list;
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
        }

        private void bindData(CartItemModel data) {
            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL + data.getProductModel().getDefaultImage(), productIV);
            productNameTV.setText(data.getProductModel().getNameEN());
            productPriceTV.setText("$" + String.format("%.1f", data.getProductModel().getPrice()));
            quantityTV.setText(context.getString(R.string.quantity)+" "+String.valueOf(data.getQuantity()));
        }
    }

}