package com.zhy.recyclerview;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StartDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "StartDecoration";
    private int headHeight;
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint.FontMetrics fontMetrics;
    private Rect mTextRect;

    public StartDecoration() {
        this.headHeight = dp2px(60);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(dp2px(30));
        mTextPaint.setAntiAlias(true);
        fontMetrics = mTextPaint.getFontMetrics();
        mTextRect = new Rect();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int top = parent.getPaddingTop();
        int right = parent.getWidth() - parent.getPaddingRight();
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof StartAdapter) {
            StartAdapter startAdapter = (StartAdapter) adapter;

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = parent.getChildAt(i);
                int position = parent.getChildLayoutPosition(childView);
                boolean groupHeader = startAdapter.isGroupHeader(position);
                String groupName = startAdapter.getGroupName(position);
                //子View是否超出Top边线
                boolean topCanDraw = childView.getTop() - parent.getPaddingTop() - headHeight >= 0;
                int bottom = parent.getBottom() - parent.getPaddingBottom();
                boolean bottomCanDraw = i == 0 || (childView.getTop() <= bottom
                        + (groupHeader ? headHeight : 0));
                boolean canDraw = topCanDraw & bottomCanDraw;
                if (!canDraw) {
                    continue;
                }
                if (groupHeader) {
                    float groupHeaderBottom = childView.getTop() > bottom ? bottom : childView.getTop();
                    c.drawRect(left, childView.getTop() - headHeight, right, groupHeaderBottom, mPaint);
                    if (childView.getTop() > bottom) {
                        c.clipRect(left, top, right, groupHeaderBottom);
                    }
                    drawGroupName(c, left, childView.getTop() - headHeight, right, headHeight, groupName);
                } else {
                    c.drawRect(left, childView.getTop() - 10, right, childView.getTop(), mPaint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof StartAdapter) {
            StartAdapter startAdapter = (StartAdapter) adapter;
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();
            //可见区域内的第一个Item
            int position = ((LinearLayoutManager)
                    parent.getLayoutManager()).findFirstVisibleItemPosition();
            View itemView = parent.findViewHolderForAdapterPosition(position).itemView;
            //当第二个是组的头时
            boolean isGroupHeader = startAdapter.isGroupHeader(position + 1);
            String groupName = startAdapter.getGroupName(position);
            if (isGroupHeader) {
                int bottom = Math.min(headHeight, itemView.getBottom() - top);
                c.drawRect(left, top, right, top + bottom, mPaint);
                c.clipRect(left, top, right, top + bottom);
                drawGroupName(c, left, top, right, bottom - top, groupName);
            } else {
                c.drawRect(left, top, right, top + headHeight, mPaint);
                drawGroupName(c, left, top, right, headHeight, groupName);
            }

        }
    }

    private void drawGroupName(@NonNull Canvas c, int left, int top, int right, int itemHeight, String groupName) {
        mTextPaint.getTextBounds(groupName, 0, groupName.length(), mTextRect);
        float x = left + ((right - left) - mTextRect.width()) / 2f;
        //dy时中线到baseline的距离
        float dy = -(fontMetrics.descent + fontMetrics.ascent) / 2;
        float y = top + itemHeight / 2f + dy;
        c.drawText(groupName, x, y, mTextPaint);

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof StartAdapter) {
            int position = parent.getChildLayoutPosition(view);
            boolean groupHeader = ((StartAdapter) adapter).isGroupHeader(position);

            if (groupHeader) {
                outRect.set(0, headHeight, 0, 0);
            } else {
                outRect.set(0, 10, 0, 0);
            }
        }
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

}
