package com.intcore.internship.ecommerce.ui.addresses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.AddressModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.MyViewHolder> {


    private ArrayList<AddressModel> list ;

    public AddressRecyclerAdapter(ArrayList<AddressModel> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AddressModel data = list.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView shortAddressTV ;
        private TextView longAddressTV ;

        private MyViewHolder(View v) {
            super(v);
            shortAddressTV = v.findViewById(R.id.shortAddressTV) ;
            longAddressTV = v.findViewById(R.id.longAddressTV) ;
        }

        private void bindData(AddressModel data) {
            shortAddressTV.setText(data.getCity());
            longAddressTV.setText(data.getApartment()+" "+data.getStreet()+", "+data.getCity()+".");
        }
    }


}