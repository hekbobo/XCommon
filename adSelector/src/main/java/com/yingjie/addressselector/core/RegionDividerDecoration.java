package com.yingjie.addressselector.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Draws a single divider for each list item with horizontal insets.
 */
public class RegionDividerDecoration extends RecyclerView.ItemDecoration {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int insetStartPx;
    private final int insetEndPx;
    private final int dividerHeightPx;

    public RegionDividerDecoration(@ColorInt int color, int insetStartPx, int insetEndPx, int dividerHeightPx) {
        this.insetStartPx = insetStartPx;
        this.insetEndPx = insetEndPx;
        this.dividerHeightPx = dividerHeightPx;
        paint.setColor(color);
    }

    public void setColor(@ColorInt int color) {
        paint.setColor(color);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = dividerHeightPx;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft() + insetStartPx;
        int right = parent.getWidth() - parent.getPaddingRight() - insetEndPx;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom();
            int bottom = top + dividerHeightPx;
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
