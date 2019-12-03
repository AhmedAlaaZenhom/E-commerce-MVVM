package com.intcore.internship.ecommerce.ui.product;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.Utils.CommonUtils;
import com.intcore.internship.ecommerce.data.models.ColorModel;
import com.intcore.internship.ecommerce.data.models.SizeModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class AddCartCompletionDialog extends Dialog {

    // Views
    private LinearLayout sizesLL , colorsLL ;
    private List<TextView> sizesTVList, colorTVList;

    // Objects
    private List<SizeModel> sizeModelList ;
    private List<ColorModel> colorModelList ;
    private ClickListener clickListener ;
    private Integer selectedSizeID , selectedColorID ;

    public AddCartCompletionDialog(@NonNull Context context ,
                                   List<SizeModel> sizeModelList, List<ColorModel> colorModelList ,
                                   ClickListener clickListener) {
        super(context);
        setContentView(R.layout.dialog_product_specs);

        this.sizeModelList = sizeModelList ;
        this.colorModelList = colorModelList ;
        this.clickListener = clickListener ;

        setUpViews();

        this.setOnShowListener(dialog -> {
            Window window = getWindow();
            if (window != null) {
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    private void setUpViews() {

        // setting up sizes
        sizesLL = findViewById(R.id.sizesLL);
        if (sizeModelList == null || sizeModelList.isEmpty())
            sizesLL.addView(createNormalTextView(R.string.no_available_sizes));
        else {
            sizesTVList = new ArrayList<>() ;
            for (int i = 0; i < sizeModelList.size(); i++) {
                final TextView textView = createSizeTextView(i) ;
                sizesLL.addView(textView);
                sizesTVList.add(textView) ;
            }
        }


        // setting up colors
        colorsLL = findViewById(R.id.colorsLL);
        if (colorModelList == null || colorModelList.isEmpty())
            colorsLL.addView(createNormalTextView(R.string.no_available_colors));
        else {
            colorTVList = new ArrayList<>() ;
            for (int i = 0; i < colorModelList.size(); i++) {
                final TextView textView = createColorTextView(i);
                colorsLL.addView(textView);
                colorTVList.add(textView);
            }
        }


        // Setting up quantityET and addToCart button
        final EditText reportProblemET = (EditText) findViewById(R.id.quantityET);

        findViewById(R.id.addToCartTV).setOnClickListener(v -> {
            if (TextUtils.isEmpty(reportProblemET.getText().toString()) || clickListener == null)
                return;
            clickListener.onAddToCartClicked(
                    Integer.valueOf(reportProblemET.getText().toString()), selectedSizeID, selectedColorID);
            dismiss();
        });

    }

    private TextView createNormalTextView(int textRes) {
        final TextView textView = new TextView(getContext()) ;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
        textView.setLayoutParams(layoutParams);
        textView.setText(textRes);
        textView.setTextSize(13);
        return textView ;
    }

    private TextView createSizeTextView(final int index) {
        final SizeModel sizeModel = sizeModelList.get(index) ;
        if(index==0)
            selectedSizeID = sizeModel.getId();
        final TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) CommonUtils.getPxFromDp(getContext(), 40),
                (int) CommonUtils.getPxFromDp(getContext(), 40));
        layoutParams.setMarginStart((index==0)?0:(int) CommonUtils.getPxFromDp(getContext(), 8));
        textView.setLayoutParams(layoutParams);
        textView.setAllCaps(true);
        textView.setGravity(Gravity.CENTER);
        setSizeCircleState(textView,index==0);
        textView.setText(sizeModelList.get(index).getSize());
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setOnClickListener(v -> {
            selectedSizeID = sizeModel.getId() ;
            for(int i=0 ; i<sizesTVList.size() ;i++)
                setSizeCircleState(sizesTVList.get(i),false);
            setSizeCircleState(textView,true);
        });
        return textView;
    }

    private TextView createColorTextView(int index) {
        final ColorModel colorModel = colorModelList.get(index) ;
        if(index==0)
            selectedColorID = colorModel.getId();
        final TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) CommonUtils.getPxFromDp(getContext(), 40),
                (int) CommonUtils.getPxFromDp(getContext(), 40));
        layoutParams.setMarginStart((index==0)?0:(int) CommonUtils.getPxFromDp(getContext(), 8));
        textView.setLayoutParams(layoutParams);
        textView.setAllCaps(true);
        setColorCircleState(textView,colorModel.getColor(),index==0);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setOnClickListener(v -> {
            selectedColorID = colorModel.getId() ;
            for(int i=0 ; i<colorTVList.size() ;i++)
                setColorCircleState(colorTVList.get(i),colorModelList.get(i).getColor(),false);
            setColorCircleState(textView,colorModel.getColor(),true);
        });
        return textView;
    }

    private void setSizeCircleState(TextView textView, boolean selected){
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(selected?Color.BLACK:Color.WHITE);
        shape.setStroke((int) CommonUtils.getPxFromDp(getContext(), 1), Color.BLACK);
        textView.setBackground(shape);
        textView.setTextColor(selected?Color.WHITE:Color.BLACK);
    }

    private void setColorCircleState(TextView textView, String color , boolean selected){
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(Color.parseColor(color));
        shape.setStroke(selected?(int) CommonUtils.getPxFromDp(getContext(), 1):0, getContext().getResources().getColor(R.color.off_white));
        textView.setBackground(shape);
    }


    public interface ClickListener {
        void onAddToCartClicked(Integer quantity , Integer sizeID , Integer colorID) ;
    }

}