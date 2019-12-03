package com.intcore.internship.ecommerce.ui.main.commons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {

    public interface ClickListener {
        void onCategoryClicked(CategoryModel categoryModel);
    }

    private ArrayList<CategoryModel> list ;
    private ClickListener clickListener ;

    public CategoryRecyclerAdapter(ArrayList<CategoryModel> list, ClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
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
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout categoryRL ;
        private TextView categoryNameTV ;
        private ImageView categoryIV;

        private MyViewHolder(View v) {
            super(v);
            categoryRL = v.findViewById(R.id.categoryRL) ;
            categoryIV = v.findViewById(R.id.categoryIV) ;
            categoryNameTV = v.findViewById(R.id.categoryNameTV );
        }

        private void bindData(CategoryModel data) {
            categoryNameTV.setText(data.getNameEN());
            PicassoHelper.loadImageWithCache(PicassoHelper.STORAGE_BASE_URL+data.getImageURL(), categoryIV);
            categoryRL.setOnClickListener(v -> {
                if(clickListener!=null)
                    clickListener.onCategoryClicked(data);
            });
        }
    }


}
