package com.intcore.internship.ecommerce.ui.main.commons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onProductImageClicked(ProductModel productModel);

        void onProductFavClicked(ProductModel productModel);
    }

    private ArrayList<ProductModel> list ;
    private ClickListener clickListener ;

    public ProductRecyclerAdapter(ArrayList<ProductModel> list, ClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
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
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView productIV ;
        private ImageView favIV ;
        private TextView productNameTV ;
        private TextView productPriceTV ;

        private MyViewHolder(View v) {
            super(v);
            productIV = v.findViewById(R.id.productIV) ;
            favIV =  v.findViewById(R.id.favIV) ;
            productNameTV = v.findViewById(R.id.productNameTV) ;
            productPriceTV =  v.findViewById(R.id.productPriceTV) ;
        }

        private void bindData(ProductModel data) {
            PicassoHelper.loadImageWithCache(PicassoHelper.STORAGE_BASE_URL+data.getDefaultImage(),productIV);
            favIV.setImageResource(data.isFav()?R.drawable.heart_filled:R.drawable.heart_empty);
            productNameTV.setText(data.getNameEN());
            productPriceTV.setText("$" + String.format("%.1f", data.getPrice()));

            productIV.setOnClickListener(v -> {
                if(clickListener!=null)
                    clickListener.onProductImageClicked(data);
            });
            favIV.setOnClickListener(v -> {
                if(clickListener!=null)
                    clickListener.onProductFavClicked(data);
            });
        }
    }


}
