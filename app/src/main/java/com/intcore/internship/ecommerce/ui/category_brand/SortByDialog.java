package com.intcore.internship.ecommerce.ui.category_brand;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.intcore.internship.ecommerce.R;

import androidx.annotation.NonNull;

public class SortByDialog extends Dialog {

    public interface ClickListener{
        void onSortOptionClicked(int option);
    }

    private ClickListener clickListener;

    public SortByDialog(@NonNull Context context, ClickListener clickListener) {
        super(context);
        setContentView(R.layout.dialog_sort_by);
        this.setOnShowListener(dialog -> {
            Window window = getWindow();
            if (window != null) {
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });

        this.clickListener = clickListener;
        setUpViews();
    }

    private void setUpViews() {
        final View.OnClickListener sortOptionClickedListener = v -> {
            if(clickListener!=null)
                clickListener.onSortOptionClicked(Integer.parseInt(v.getTag().toString()));
            dismiss();
        };
        findViewById(R.id.sortByNew).setOnClickListener(sortOptionClickedListener);
        findViewById(R.id.sortByPopular).setOnClickListener(sortOptionClickedListener);
        findViewById(R.id.sortByHighToLow).setOnClickListener(sortOptionClickedListener);
        findViewById(R.id.sortByLowToHigh).setOnClickListener(sortOptionClickedListener);

        findViewById(R.id.dialogButtonCancel).setOnClickListener(v -> dismiss());

    }
}
