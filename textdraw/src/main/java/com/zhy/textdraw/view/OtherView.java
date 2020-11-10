package com.zhy.textdraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import static com.zhy.textdraw.Utils.dp2px;

public class OtherView extends View {
    private static final String TAG = "OtherView";
    private Paint mPaint;
    private Paint mLinePaint;
    private Paint mMetricsPaint;
    private String mText;
    private Rect mTextBounds;
    private Paint.FontMetrics mFontMetrics;
    private float mMeasureWidth;

    private float xOffset = 0;

    public OtherView(Context context) {
        super(context);
        init();
    }

    public OtherView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OtherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public OtherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(dp2px(150));

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(1);

        mTextBounds = new Rect();
        mFontMetrics = new Paint.FontMetrics();

        mMetricsPaint = new Paint();
        mMetricsPaint.setStyle(Paint.Style.FILL);
        mMetricsPaint.setTextSize(dp2px(20));
        mText = "hjkpg";
//        mText = "ཁྱོད་བདེ་མོ།";//藏文
//        mText = "السلام عليكم";
//        mText = "jEh";
//        mText = "世界真大";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //baseline 设置为y轴中间
        float x = (float) (getWidth() - mTextBounds.width()) / 2;
        mLinePaint.setColor(Color.BLACK);
        float baseline = getHeight() / 2 -(mFontMetrics.descent + mFontMetrics.ascent) / 2;
        canvas.drawText(mText, x, baseline, mPaint);


        drawMetrics(canvas, baseline, 0, "baseline", Color.RED);
        drawMetrics(canvas, baseline, mFontMetrics.top, "top", Color.BLACK);
        drawMetrics(canvas, baseline, mFontMetrics.bottom, "bottom", Color.BLUE);
        drawMetrics(canvas, baseline, mFontMetrics.ascent, "ascent", Color.CYAN);
        drawMetrics(canvas, baseline, mFontMetrics.descent, "descent", Color.GREEN);

        String overview = "mFontMetrics = {ascent: " + mFontMetrics.ascent + " ,bottom: " + mFontMetrics.bottom + " ,descent: " + mFontMetrics.descent
                + " ,leading: " + mFontMetrics.leading + " ,top: " + mFontMetrics.top;

        mMetricsPaint.setColor(Color.YELLOW);
        mMetricsPaint.getTextBounds(overview, 0, overview.length(), mTextBounds);
        float dx = (getWidth() - mTextBounds.width()) / 2;
        float dy = (baseline + mFontMetrics.top +mTextBounds.height()) / 2;
        canvas.drawText(overview, dx, dy, mMetricsPaint);


    }

    private void drawMetrics(Canvas canvas, float baseline, float property, String name, int color) {
        float y = baseline + property;
        mLinePaint.setColor(color);
        canvas.drawLine(0, y, getWidth(), y, mLinePaint);
        mMetricsPaint.setColor(color);
        canvas.drawText(name, xOffset, y, mMetricsPaint);
        xOffset += mMetricsPaint.measureText(name);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //1.先测量文字
        measureText();
        //2. 测量自身
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //3. 保持测量尺寸
        setMeasuredDimension(width, height);
    }

    private void measureText() {
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
        Log.i(TAG, "mTextBounds = " + mTextBounds);
        mMeasureWidth = mPaint.measureText(mText);
        Log.i(TAG, "mMeasureWidth = " + mMeasureWidth);

        int textWidth = mTextBounds.width();
        Log.i(TAG, "textWidth = " + textWidth);
        int textHeight = mTextBounds.height();
        Log.i(TAG, "textheight = " + textHeight);

        mPaint.getFontMetrics(mFontMetrics);
        float textFontHeight = mFontMetrics.bottom - mFontMetrics.top;
        Log.i(TAG, "textFontHeight = " + textFontHeight);
        float textFontHeight1 = mFontMetrics.descent - mFontMetrics.ascent;
        Log.i(TAG, "textFontHeight1 = " + textFontHeight1);
//        public float ascent;
//        public float bottom;
//        public float descent;
//        public float leading;
//        public float top;
        Log.i(TAG, "mFontMetrics = {ascent: " + mFontMetrics.ascent + " ,bottom: " + mFontMetrics.bottom + " ,descent: " + mFontMetrics.descent
                + " ,leading: " + mFontMetrics.leading + " ,top: " + mFontMetrics.top);
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "measureWidth: EXACTLY");
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = (int) (mMeasureWidth + .5f) + getPaddingLeft() + getPaddingRight();
                break;
        }
        //如果是AT_MOST,不能超过父布局的尺寸
        result = (mode == MeasureSpec.AT_MOST) ? Math.min(result, size) : result;
        return result;
    }

    private int measureHeight(final int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "measureHeight: EXACTLY");
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = (int) (mFontMetrics.descent - mFontMetrics.ascent + .5f) + getPaddingTop() + getPaddingBottom();
                break;
        }
        //如果是AT_MOST,不能超过父布局的尺寸
        result = (mode == MeasureSpec.AT_MOST) ? Math.min(result, size) : result;
        return result;
    }
}