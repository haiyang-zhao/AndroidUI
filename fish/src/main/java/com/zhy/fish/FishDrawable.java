package com.zhy.fish;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FishDrawable extends Drawable {


    private Path mPath;
    private Paint mPaint;

    //转弯更自然的重心(身体的中心点)
    private PointF middlePoint;
    //鱼的主要朝向角度
    private float fishMainAngle = 40;

    // 透明度
    private static final int OTHER_ALPHA = 110;
    // 鱼身的透明度
    private static final int BODY_ALPHA = 160;

    //鱼头直径
    private static final int HEAD_RADIUS = 100;
    // 鱼身长度
    private static final float BODY_LENGTH = HEAD_RADIUS * 3.2f;
    // 寻找鱼鳍起始点坐标的线长
    private static final float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    // 鱼鳍的长度
    private static final float FINS_LENGTH = 1.3f * HEAD_RADIUS;
    // 大圆的半径
    private static final float BIG_CIRCLE_RADIUS = 0.7f * HEAD_RADIUS;
    // 中圆的半径
    private static final float MIDDLE_CIRCLE_RADIUS = 0.6f * BIG_CIRCLE_RADIUS;
    // 小圆半径
    private static final float SMALL_CIRCLE_RADIUS = 0.4f * MIDDLE_CIRCLE_RADIUS;
    // --寻找尾部中圆圆心的线长
    private static final float FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS * (0.6f + 1);
    // --寻找尾部小圆圆心的线长
    private static final float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // --寻找大三角形底边中心点的线长
    private static final float FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.7f;

    private float currentValue = 0;
    private float freq = 1f;
    private PointF headPoint;

    public FishDrawable() {
        init();
    }

    private void init() {
        //路径
        mPath = new Path();
        //画笔
        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 画笔类型填充
        mPaint.setStyle(Paint.Style.FILL);
        // 防抖
        mPaint.setDither(true);
        // 设置颜色
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 21);
        // 与Point一样，只是坐标为浮点数,重心位于整个控件的中心，保证鱼旋转的空间
        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);

        // 1.2  1.5 ==》 10
        // 1.2*n = 整数 1.5 *n = 整数 ==》 公倍数
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 3600f);
        // 动画周期
        valueAnimator.setDuration(15 * 1000);
        // 重复的模式：重新开始
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        // 重复的次数
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // 插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                currentValue = (float) animator.getAnimatedValue();
                invalidateSelf();
            }
        });
        valueAnimator.start();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        float fishAngle = fishMainAngle;

        // sin(currentValue*2) ==> 0-3600 1s, 10次，20次
        float fishAngle = (float) (fishMainAngle + Math.sin(Math.toRadians(currentValue * 1.2 * freq)) * 10);
        //鱼头圆心坐标
        headPoint = calcPoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);

        // 画右鱼鳍
        PointF rightFinsPoint = calcPoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110);
        makeFins(canvas, rightFinsPoint, fishAngle, true);

        // 画左鱼鳍
        PointF leftFinsPoint = calcPoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110);
        makeFins(canvas, leftFinsPoint, fishAngle, false);

        PointF bodyBottomCenterPoint = calcPoint(headPoint, BODY_LENGTH, fishAngle - 180);

        // 画节肢1
        PointF middleCenterPoint = makeSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS,
                FIND_MIDDLE_CIRCLE_LENGTH, fishAngle, true);
        // 画节肢2
        makeSegment(canvas, middleCenterPoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS,
                FIND_SMALL_CIRCLE_LENGTH, fishAngle, false);


        float findEdgeLength = (float) Math.abs(Math.sin(Math.toRadians(currentValue * 1.5 * freq)) * BIG_CIRCLE_RADIUS);
        // 尾巴
        makeTriangle(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH, findEdgeLength, fishAngle);
        makeTriangle(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH - 10,
                findEdgeLength - 20, fishAngle);


        // 身体
        makeBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle);
    }

    /**
     * 设置透明度的方法
     */
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }
    /**
     * 设置了一个颜色过滤器，那么在绘制出来之前，被绘制内容的每一个像素都会被颜色过滤器改变
     */
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        // 设置颜色滤镜，一般情况下将此值设置给Paint
        mPaint.setColorFilter(colorFilter);

    }

    /**
     * 这个值，可以根据setAlpha中设置的值进行调整。比如，alpha == 0时设置为 PixelFormat.TRANSPARENT
     * 在alpha == 255时设置为PixelFormat.OPAQUE。在其他时候设置为 PixelFormat.TRANSLUCENT。
     * PixelFormat.OPAQUE：便是完全不透明，遮盖在他下面的所有内容
     * PixelFormat.TRANSPARENT：透明，完全不显示任何东西
     * PixelFormat.TRANSLUCENT：只有绘制的地方才覆盖底下的内容
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * 设置宽
     * 在View使用wrap_content的时候，设置固定宽度，默认为-1
     */
    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    /**
     * 设置高
     * 在View使用wrap_content的时候，设置固定高度，默认为-1
     */
    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    /**
     * 画鱼身体
     */
    private void makeBody(Canvas canvas, PointF headPoint, PointF bodyBottomCenterPoint, float fishAngle) {
        // 身体的四个点求出来
        PointF topLeftPoint = calcPoint(headPoint, HEAD_RADIUS, fishAngle + 90);
        PointF topRightPoint = calcPoint(headPoint, HEAD_RADIUS, fishAngle - 90);
        PointF bottomLeftPoint = calcPoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS,
                fishAngle + 90);
        PointF bottomRightPoint = calcPoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS,
                fishAngle - 90);

        // 二阶贝塞尔曲线的控制点 --- 决定鱼的胖瘦
        PointF controlLeft = calcPoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle + 130);
        PointF controlRight = calcPoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle - 130);

        // 绘制
        mPath.reset();
        mPath.moveTo(topLeftPoint.x, topLeftPoint.y);
        mPath.quadTo(controlLeft.x, controlLeft.y, bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.quadTo(controlRight.x, controlRight.y, topRightPoint.x, topRightPoint.y);
        mPaint.setAlpha(BODY_ALPHA);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     *  绘制三角形
     * @param canvas 画布
     * @param startPoint 启点
     * @param findCenterLength 寻找三角形底边中心点的线长
     * @param findEdgeLength 底边一半的长度
     * @param fishAngle 鱼朝向角度
     */
    private void makeTriangle(Canvas canvas, PointF startPoint, float findCenterLength,
                              float findEdgeLength, float fishAngle) {

        float triangleAngle = (float) (fishAngle + Math.sin(Math.toRadians(currentValue * 1.5 * freq)) * 35);

        // 三角形底边的中心坐标
        PointF centerPoint = calcPoint(startPoint, findCenterLength, triangleAngle - 180);
        // 三角形底边两点
        PointF leftPoint = calcPoint(centerPoint, findEdgeLength, triangleAngle + 90);
        PointF rightPoint = calcPoint(centerPoint, findEdgeLength, triangleAngle - 90);

        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.lineTo(leftPoint.x, leftPoint.y);
        mPath.lineTo(rightPoint.x, rightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * @param start  起始点坐标
     * @param length 要求的点到起始点的直线距离 -- 线长
     * @param angle  旋转角度
     * @return 计算结果点
     */
    public PointF calcPoint(PointF start, float length, float angle) {
        // x坐标 Math.toRadians 角度转弧度 --- sin\cos的参数是弧度制
        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        // y坐标 符合Android坐标的y轴朝下的标准
        float deltaY = (float) (Math.sin(Math.toRadians(angle - 180)) * length);
        return new PointF(start.x + deltaX, start.y + deltaY);
    }

    /**
     * 画鱼鳍
     *
     * @param canvas
     * @param start     起始坐标
     * @param fishAngle
     * @param isRight   是否是右鱼鳍
     */
    private void makeFins(Canvas canvas, PointF start, float fishAngle, boolean isRight) {
        float controlAngle = 115;
        // 鱼鳍的终点 --- 二阶贝塞尔曲线的终点
        PointF endPoint = calcPoint(start, FINS_LENGTH, fishAngle - 180);
        // 控制点
        PointF controlPoint = calcPoint(start, FINS_LENGTH * 1.8f,
                isRight ? fishAngle - controlAngle : fishAngle + controlAngle);
        // 绘制
        mPath.reset();
        // 将画笔移动到起始点
        mPath.moveTo(start.x, start.y);
        // 二阶贝塞尔曲线
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画节肢1,2(这两个摆动的时候是分开的，必须分开画)
     *
     * @param canvas 画布
     * @param bottomCenterPoint 梯形上底的中心点
     * @param bigRadius 梯形大圆的半径
     * @param smallRadius 梯形小圆的半径
     * @param findSmallCircleLength 寻找梯形小圆的线长
     * @param fishAngle 鱼头坐标
     * @param hasBigCircle 节肢是否需要绘制大圆，true绘制
     * @return 计算节肢1的时候需要返回梯形小圆的圆心点，这个是绘制节肢2和三角形的起始点
     */
    private PointF makeSegment(Canvas canvas, PointF bottomCenterPoint, float bigRadius, float smallRadius,
                               float findSmallCircleLength, float fishAngle, boolean hasBigCircle) {

        float segmentAngle;
        if (hasBigCircle) {
            // 节肢1
            segmentAngle = (float) (fishAngle + Math.cos(Math.toRadians(currentValue * 1.5 * freq)) * 15);
        } else {
            // 节肢2
            segmentAngle = (float) (fishAngle + Math.sin(Math.toRadians(currentValue * 1.5 * freq)) * 35);
        }

        // 梯形上底圆的圆心
        PointF upperCenterPoint = calcPoint(bottomCenterPoint, findSmallCircleLength,
                segmentAngle - 180);
        // 梯形的四个点
        PointF bottomLeftPoint = calcPoint(bottomCenterPoint, bigRadius, segmentAngle + 90);
        PointF bottomRightPoint = calcPoint(bottomCenterPoint, bigRadius, segmentAngle - 90);
        PointF upperLeftPoint = calcPoint(upperCenterPoint, smallRadius, segmentAngle + 90);
        PointF upperRightPoint = calcPoint(upperCenterPoint, smallRadius, segmentAngle - 90);

        if (hasBigCircle) {
            // 画大圆 --- 只在节肢1 上才绘画
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigRadius, mPaint);
        }
        // 画小圆
        canvas.drawCircle(upperCenterPoint.x, upperCenterPoint.y, smallRadius, mPaint);

        // 画梯形
        mPath.reset();
        mPath.moveTo(upperLeftPoint.x, upperLeftPoint.y);
        mPath.lineTo(upperRightPoint.x, upperRightPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        canvas.drawPath(mPath, mPaint);

        return upperCenterPoint;
    }


    public PointF getMiddlePoint() {
        return middlePoint;
    }

    public PointF getHeadPoint() {
        return this.headPoint;
    }

    public float getHEAD_RADIUS() {
        return HEAD_RADIUS;
    }

    public float getFrequence() {
        return freq;
    }

    public void setFrequence(float frequence) {
        this.freq = frequence;
    }

    public float getFishMainAngle() {
        return fishMainAngle;
    }

    public void setFishMainAngle(float fishMainAngle) {
        this.fishMainAngle = fishMainAngle;
    }
}
