package com.zhy.textdraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class SimpleColorChangeTextView extends AppCompatTextView {

    private float percent;
    private String mText = "世界真大";

    public SimpleColorChangeTextView(@NonNull Context context) {
        super(context);
    }

    public SimpleColorChangeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleColorChangeTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制文字
        Paint paint = new Paint();
        paint.setTextSize(80);
        float baseLine = 100;

        canvas.drawText(mText, 0, baseLine, paint);

        drawCenterXLine(canvas);
        drawCenterYLine(canvas);
        float x = getWidth() / 2;
        //默认Align LEFT
        canvas.drawText(mText, x, baseLine, paint);
        //文字对齐方式有两种
        //1.设置文字对齐
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mText, x, baseLine + paint.getFontSpacing(), paint);

        //RIGHT
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(mText, x, baseLine + paint.getFontSpacing() * 2, paint);
        //文字绘制在View中心
        drawCenterText(canvas);
        //绘制渐变颜色的文字
        drawGradientText(canvas);

    }

    private void drawGradientText(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        //文字高度的计算
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //文字居中的baseline
        float baseline = getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        float width = paint.measureText(mText);
        float x = (getWidth() - width) / 2;
        canvas.clipRect(x, 0, x + width * percent, getHeight());
        canvas.drawText(mText, x, baseline, paint);
        canvas.restore();
    }


    private void drawCenterText(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        //文字高度的计算
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //文字居中的baseline
        float baseline = getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        float width = paint.measureText(mText);
        float x = (getWidth() - width) / 2;
        canvas.clipRect(x + width * percent, 0, x + width, getHeight());
        canvas.drawText(mText, x, baseline, paint);
        canvas.restore();
    }

    private void drawCenterXLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);

    }

    private void drawCenterYLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);

    }
}
