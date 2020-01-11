package com.intcore.internship.ecommerce.ui.addresses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.ui.commonClasses.Utils;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.MyViewHolder> {

    public interface Listener{
        void onAddressClicked(AddressModel addressModel);
    }

    private ArrayList<AddressModel> list ;
    private int parentHeight;
    private Listener listener ;

    public AddressRecyclerAdapter(Activity activity, ArrayList<AddressModel> list, Listener listener) {
        this.list = list;
        parentHeight = (int) (Utils.getDisplayMetrics(activity).widthPixels * 0.21);
        this.listener = listener ;
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
        private CardView parentCV;

        private MyViewHolder(View v) {
            super(v);
            shortAddressTV = v.findViewById(R.id.shortAddressTV) ;
            longAddressTV = v.findViewById(R.id.longAddressTV) ;
            parentCV = v.findViewById(R.id.parentCV);

            parentCV.getLayoutParams().height = parentHeight;
        }

        private void bindData(AddressModel data) {
            shortAddressTV.setText(data.getCity());
            longAddressTV.setText(data.getApartment()+" "+data.getStreet()+", "+data.getCity()+".");
            parentCV.setOnClickListener(v -> {
                if(listener!=null)
                    listener.onAddressClicked(data);
            });
        }
    }

}