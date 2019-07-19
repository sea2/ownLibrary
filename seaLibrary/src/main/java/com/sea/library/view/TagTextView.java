package com.sea.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sea.library.util.DensityUtil;


/**
 * Created by lhy on 2019/7/19.
 */
public class TagTextView extends TextView {

    public TagTextView(Context context) {
        this(context, null);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
                int width = getWidth();
        if (width < DensityUtil.dp2px(30)) setVisibility(GONE);
    }
}


