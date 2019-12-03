package com.intcore.internship.ecommerce.ui.main.commons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.BrandModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class BrandRecyclerAdapter extends RecyclerView.Adapter<BrandRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onBrandClicked(BrandModel brandModel);
    }

    private ArrayList<BrandModel> list ;
    private ClickListener clickListener ;

    public BrandRecyclerAdapter(ArrayList<BrandModel> list, ClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public BrandRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_brand, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BrandModel data = list.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView brandIV;

        private MyViewHolder(View v) {
            super(v);
            brandIV = v.findViewById(R.id.brandIV) ;
        }

        private void bindData(BrandModel data) {
            PicassoHelper.loadImageWithCache(PicassoHelper.STORAGE_BASE_URL+data.getImageUrl(),brandIV);
            brandIV.setOnClickListener(v -> {
                if(clickListener!=null)
                    clickListener.onBrandClicked(data);
            });
        }
    }


}