package com.intcore.internship.ecommerce.ui.orders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.OrderModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.Utils;
import com.vinay.stepview.HorizontalStepView;
import com.vinay.stepview.models.Step;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<OrderModel> list ;
    private boolean isCurrentLocaleAR;
    private int screenWidth;

    public OrdersRecyclerAdapter(Activity activity, boolean isCurrentLocaleAR, ArrayList<OrderModel> list) {
        this.context = activity;
        this.isCurrentLocaleAR = isCurrentLocaleAR;
        this.list = list;
        screenWidth = Utils.getDisplayMetrics(activity).widthPixels ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderModel data = list.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView orderDateTV ;
        private ImageView productIV ;
        private TextView productNameTV ;
        private TextView productPriceTV ;
        private HorizontalStepView stepView;

        private MyViewHolder(View v) {
            super(v);
            orderDateTV = v.findViewById(R.id.orderDateTV) ;
            productIV = v.findViewById(R.id.productIV) ;
            productNameTV = v.findViewById(R.id.productNameTV) ;
            productPriceTV = v.findViewById(R.id.productPriceTV) ;
            stepView = v.findViewById(R.id.stepView) ;
        }

        private void bindData(OrderModel data) {
            orderDateTV.setText(data.getCreatedAt());
            PicassoHelper.loadImageWithCache(PicassoHelper.STORAGE_BASE_URL
                    +data.getOrderItemModels().get(0).getProductModel().getImageModelList().get(0).getImage(),
                    productIV,
                    PicassoHelper.JUST_FIT,
                    null,
                    null);
            productNameTV.setText(isCurrentLocaleAR?data.getOrderItemModels().get(0).getProductModel().getNameAR():data.getOrderItemModels().get(0).getProductModel().getNameEN());
            productPriceTV.setText("$" + String.format("%.1f", data.getTotalPriceAmmount()));

            // StepView
            List<Step> checkoutStatusList = new ArrayList<>();
            Step shipping = new Step(context.getString(R.string.proccessing), Step.State.COMPLETED);
            Step payment = new Step(context.getString(R.string.shipped), Step.State.NOT_COMPLETED);
            Step confirmation = new Step(context.getString(R.string.delivered), Step.State.NOT_COMPLETED);
            checkoutStatusList.add(shipping);
            checkoutStatusList.add(payment);
            checkoutStatusList.add(confirmation);
            stepView.setSteps(checkoutStatusList) ;
            stepView.setTextSize(12);
            stepView.setNotCompletedLineColor(Color.GRAY);
            stepView.setCompletedLineColor(context.getResources().getColor(R.color.colorPrimary));
            stepView.setNotCompletedStepTextColor(Color.GRAY);
            stepView.setCompletedStepTextColor(Color.BLACK);
            final GradientDrawable shapeNotCompleted = new GradientDrawable();
            shapeNotCompleted.setShape(GradientDrawable.OVAL);
            shapeNotCompleted.setColor(Color.GRAY);
            stepView.setCompletedStepIcon(context.getResources().getDrawable(R.drawable.checked));
            stepView.setNotCompletedStepIcon(shapeNotCompleted);

            stepView.setLineLengthPx((int)(screenWidth*0.27));
            stepView.setCircleRadiusPx((int)(screenWidth*0.027));
        }
    }

}