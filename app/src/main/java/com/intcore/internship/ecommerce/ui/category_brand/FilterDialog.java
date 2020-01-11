package com.intcore.internship.ecommerce.ui.category_brand;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.BrandModel;
import com.intcore.internship.ecommerce.data.models.SubCategoryModel;
import com.intcore.internship.ecommerce.ui.commonClasses.Utils;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FilterDialog extends Dialog {

    public interface ClickListener {
        void onFilterClicked(int minPrice, int maxPrice, Integer selectedSubCategoryID, Integer selectedBrandID);
    }

    private boolean isCurrentLocaleAR;
    private ClickListener clickListener;
    private int minPrice, maxPrice;
    private Integer selectedSubCategoryID, selectedBrandID;
    private List<SubCategoryModel> subCategoryModelArrayList;
    private List<BrandModel> brandModelArrayList;
    private List<TextView> categoriesTVs, brandsTVs;

    public FilterDialog(@NonNull Context context,
                        boolean isCurrentLocaleAr,
                        int minPrice, int maxPrice,
                        @NonNull List<SubCategoryModel> subCategoryModelArrayList,
                        @NonNull List<BrandModel> brandModelArrayList,
                        ClickListener clickListener) {
        super(context);
        setContentView(R.layout.dialog_filter);
        this.setOnShowListener(dialog -> {
            Window window = getWindow();
            if (window != null) {
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });

        this.isCurrentLocaleAR = isCurrentLocaleAr;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.subCategoryModelArrayList = subCategoryModelArrayList;
        this.brandModelArrayList = brandModelArrayList;
        this.clickListener = clickListener;
        setUpViews();
    }

    private void setUpViews() {
        // SeekBar
        final TextView minPriceTV = findViewById(R.id.minPriceTV);
        final TextView maxPriceTV = findViewById(R.id.maxPriceTV);
        minPriceTV.setText(String.valueOf(minPrice));
        maxPriceTV.setText(String.valueOf(maxPrice));
        final RangeSeekBar seekBar = findViewById(R.id.rangeSeekBar);
        seekBar.setScaleX(isCurrentLocaleAR?-1:1);
        seekBar.setSeekBarMode(RangeSeekBar.SEEKBAR_MODE_RANGE);
        seekBar.setRange(minPrice, maxPrice, 10);
        seekBar.setProgress(minPrice, maxPrice);
        seekBar.setEnableThumbOverlap(false);
        seekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                minPrice = (int) leftValue;
                maxPrice = (int) rightValue;
                minPriceTV.setText(String.valueOf(minPrice));
                maxPriceTV.setText(String.valueOf(maxPrice));
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        final int spacing = (int) Utils.convertDpToPixel(getContext(), 8);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Sub categories
        categoriesTVs = new ArrayList<>();
        final LinearLayout subCategoriesLL = findViewById(R.id.subCategoriesLL);
        for (int i = 0; i < subCategoryModelArrayList.size(); i++) {
            final TextView textView = new TextView(getContext());
            if (i != 0)
                layoutParams.setMarginStart(spacing);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(spacing, spacing, spacing, spacing);
            int finalI = i;
            textView.setOnClickListener(v -> {
                resetSubCategoriesTextViews();
                int selectedID = subCategoryModelArrayList.get(finalI).getId();
                if (selectedSubCategoryID != null && selectedSubCategoryID == selectedID)
                    selectedSubCategoryID = null;
                else {
                    selectedSubCategoryID = selectedID;
                    textView.setBackgroundResource(R.drawable.shape_border_red);
                }
            });
            textView.setText(isCurrentLocaleAR ? subCategoryModelArrayList.get(i).getNameAR() : subCategoryModelArrayList.get(i).getNameEN());
            categoriesTVs.add(textView);
            subCategoriesLL.addView(textView);
        }
        resetSubCategoriesTextViews();

        // Brands
        brandsTVs = new ArrayList<>();
        final LinearLayout brandsLL = findViewById(R.id.brandsLL);
        for (int i = 0; i < brandModelArrayList.size(); i++) {
            final TextView textView = new TextView(getContext());
            if (i != 0)
                layoutParams.setMarginStart(spacing);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(spacing, spacing, spacing, spacing);
            int finalI = i;
            textView.setOnClickListener(v -> {
                resetBrandsTextViews();
                int selectedID = brandModelArrayList.get(finalI).getId();
                if (selectedBrandID != null && selectedBrandID == selectedID)
                    selectedBrandID = null;
                else {
                    selectedBrandID = selectedID;
                    textView.setBackgroundResource(R.drawable.shape_border_red);
                }
            });
            textView.setText(isCurrentLocaleAR ? brandModelArrayList.get(i).getNameAR() : brandModelArrayList.get(i).getNameEN());
            brandsTVs.add(textView);
            brandsLL.addView(textView);
        }
        resetBrandsTextViews();

        findViewById(R.id.filterTV).setOnClickListener(v -> {
            if (clickListener != null)
                clickListener.onFilterClicked(minPrice, maxPrice, selectedSubCategoryID, selectedBrandID);
            dismiss();
        });

        findViewById(R.id.cancelTV).setOnClickListener(v -> dismiss());

    }

    private void resetSubCategoriesTextViews() {
        for (int i = 0; i < categoriesTVs.size(); i++) {
            categoriesTVs.get(i).setBackgroundResource(R.drawable.shape_border_black);
        }
    }

    private void resetBrandsTextViews() {
        for (int i = 0; i < brandsTVs.size(); i++) {
            brandsTVs.get(i).setBackgroundResource(R.drawable.shape_border_black);
        }
    }

}