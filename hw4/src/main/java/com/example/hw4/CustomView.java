package com.example.hw4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    public interface TouchActionListener {
        void onTouchDown(int x, int y);
    }

    private TouchActionListener touchActionListener;

    private RectF oval;

    private Paint paintForGreenArc;
    private Paint paintForYellowArc;
    private Paint paintForBlueArc;
    private Paint paintForRedArc;
    private Paint paintForCircle;

    private int centerOfWidth;
    private int centerOfHeight;

    private int radiusForSmallCircle;
    private int purpleColorForSmallCircle;
    private int radiusForBigCircle = 400;

    private int greenColorForArc;
    private int yellowColorForArc;
    private int blueColorForArc;
    private int redColorForArc;
    private int orangeColorForChange;
    private int blackColorForChange;

    private int x;
    private int y;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        createRectFAndPaint(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
        createRectFAndPaint(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
            radiusForSmallCircle = typedArray.getDimensionPixelSize(R.styleable.CustomView_radius_for_small_circle, 0);
            purpleColorForSmallCircle = typedArray.getColor(R.styleable.CustomView_purple_circle, 0);

            greenColorForArc = typedArray.getColor(R.styleable.CustomView_green_arc, 0);
            yellowColorForArc = typedArray.getColor(R.styleable.CustomView_yellow_arc, 0);
            blueColorForArc = typedArray.getColor(R.styleable.CustomView_blue_arc, 0);
            redColorForArc = typedArray.getColor(R.styleable.CustomView_red_arc, 0);

            orangeColorForChange = typedArray.getColor(R.styleable.CustomView_orange_change,0);
            blackColorForChange = typedArray.getColor(R.styleable.CustomView_black_change, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerOfWidth = w / 2;
        centerOfHeight = h / 2;
    }

    private void createRectFAndPaint(AttributeSet attrs) {
        if (attrs != null) {
            oval = new RectF();

            paintForGreenArc = new Paint();
            paintForYellowArc = new Paint();
            paintForBlueArc = new Paint();
            paintForRedArc = new Paint();
            paintForCircle = new Paint();

            paintForYellowArc.setColor(yellowColorForArc);
            paintForBlueArc.setColor(blueColorForArc);
            paintForRedArc.setColor(redColorForArc);
            paintForGreenArc.setColor(greenColorForArc);
            paintForCircle.setColor(purpleColorForSmallCircle);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        oval.set(centerOfWidth - radiusForBigCircle, centerOfHeight - radiusForBigCircle,
                centerOfWidth + radiusForBigCircle, centerOfHeight + radiusForBigCircle);

        canvas.drawArc(oval, 0, 90, true, paintForYellowArc);
        canvas.drawArc(oval, 90, 90, true, paintForBlueArc);
        canvas.drawArc(oval, 180, 90, true, paintForRedArc);
        canvas.drawArc(oval, 270, 90, true, paintForGreenArc);

        canvas.drawCircle(centerOfWidth, centerOfHeight, radiusForSmallCircle, paintForCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();
        y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (touchActionListener != null) {
                touchActionListener.onTouchDown(x, y);
            }
            defineTouchPointAndChangeColor();
        }
        return super.onTouchEvent(event);
    }

    private void defineTouchPointAndChangeColor() {
        if (((Math.pow((x - getCenterOfWidth()), 2)) + (Math.pow((y - getCenterOfHeight()), 2)) <=
                Math.pow(getRadiusForBigCircle(), 2)) &&

                ((Math.pow((x - getCenterOfWidth()), 2)) + (Math.pow((y - getCenterOfHeight()), 2)) >
                        Math.pow(getRadiusForSmallCircle(), 2))) {

            if (x > getCenterOfWidth() && y > getCenterOfHeight()) {
                touchYellowArc();
            } else if (x < getCenterOfWidth() && y > getCenterOfHeight()) {
                touchBlueArc();
            } else if (x < getCenterOfWidth() && y < getCenterOfHeight()) {
                touchRedArc();
            } else if (x > getCenterOfWidth() && y < getCenterOfHeight()) {
                touchGreenArc();
            }
        }

        if ( (Math.pow((x - getCenterOfWidth()), 2)) + (Math.pow((y - getCenterOfHeight()), 2)) <
                Math.pow(getRadiusForSmallCircle(), 2) )  {
            touchPurpleSmallCircle();
        }
    }

    private void touchYellowArc() {
        paintForYellowArc.setColor(orangeColorForChange);
        invalidate();
    }

    private void touchBlueArc() {
        paintForBlueArc.setColor(orangeColorForChange);
        invalidate();
    }

    private void touchRedArc() {
        paintForRedArc.setColor(orangeColorForChange);
        invalidate();
    }

    private void touchGreenArc() {
        paintForGreenArc.setColor(orangeColorForChange);
        invalidate();
    }

    private void touchPurpleSmallCircle() {
        paintForYellowArc.setColor(blackColorForChange);
        paintForBlueArc.setColor(blackColorForChange);
        paintForRedArc.setColor(blackColorForChange);
        paintForGreenArc.setColor(blackColorForChange);
        invalidate();
    }

    public void setTouchActionListener(TouchActionListener touchActionListener) {
        this.touchActionListener = touchActionListener;
    }

    private int getCenterOfWidth() {
        return centerOfWidth;
    }

    private int getCenterOfHeight() {
        return centerOfHeight;
    }

    private int getRadiusForSmallCircle() {
        return radiusForSmallCircle;
    }

    private int getRadiusForBigCircle() {
        return radiusForBigCircle;
    }
}
