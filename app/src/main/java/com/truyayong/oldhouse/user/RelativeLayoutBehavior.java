package com.truyayong.oldhouse.user;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.truyayong.oldhouse.utils.DensityUtil;

/**
 * Created by alley_qiu on 2017/2/7.
 */

public class RelativeLayoutBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {

    Context mContext;

    public RelativeLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        return true;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RelativeLayout child, View dependency) {

        float max = DensityUtil.dp2px(mContext, 100) + DensityUtil.getStatusBarHeight(mContext);
        if (dependency.getY() > 0) {
            float scale = ((dependency.getY() - max) / (DensityUtil.dp2px(mContext, 180) - max));
            child.setAlpha(scale);
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
