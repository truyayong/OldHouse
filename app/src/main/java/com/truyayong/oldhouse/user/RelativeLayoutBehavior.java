package com.truyayong.oldhouse.user;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by alley_qiu on 2017/2/7.
 */

public class RelativeLayoutBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {

    public RelativeLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
