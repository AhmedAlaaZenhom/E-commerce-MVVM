package com.intcore.internship.ecommerce.ui.main.mainScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.CategoryModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SideBarCategoriesAdapter extends RecyclerView.Adapter<SideBarCategoriesAdapter.MyViewHolder> {

    public interface ClickListener {
        void onCategoryClicked(CategoryModel categoryModel);
    }

    private ArrayList<CategoryModel> list;
    private ClickListener clickListener;

    public SideBarCategoriesAdapter(ArrayList<CategoryModel> list, ClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_category_side_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryModel data = list.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryNameTV;

        private MyViewHolder(View v) {
            super(v);
            categoryNameTV = v.findViewById(R.id.categoryNameTV);
        }

        private void bindData(CategoryModel data) {
            categoryNameTV.setText(data.getNameEN());
            categoryNameTV.setOnClickListener(v -> {
                if (clickListener != null)
                    clickListener.onCategoryClicked(data);
            });
        }
    }

}
