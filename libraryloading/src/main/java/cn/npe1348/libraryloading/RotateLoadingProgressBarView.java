package cn.npe1348.libraryloading;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RotateLoadingProgressBarView extends View {
    private Context context;
    private int mProgressColor = Color.RED;
    private int mBorderWidth = 6;

    private Paint mPaint;

    private int mCurrentProgress = 0;

    private Paint getPaintByColor(int color){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2px(mBorderWidth));
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    public int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * context.getResources().getDisplayMetrics().density);
    }
    public RotateLoadingProgressBarView(Context context) {
        this(context,null);
    }

    public RotateLoadingProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotateLoadingProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RotateLoadingProgressBarView);
        mProgressColor = array.getColor(R.styleable.RotateLoadingProgressBarView_rotateLoadingProgressColor,mProgressColor);
        mBorderWidth = array.getDimensionPixelSize(R.styleable.RotateLoadingProgressBarView_rotateLoadingBorderWidth,mBorderWidth);
        array.recycle();

        mPaint = getPaintByColor(mProgressColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //宽高不一致,取最小值,确保是个正方形
        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float strokeWidth = mPaint.getStrokeWidth();
        //画进度圆弧
        RectF rectF = new RectF(strokeWidth,strokeWidth,getWidth()-strokeWidth,getHeight()-strokeWidth);
        Log.e("startLoading","mCurrentProgress="+mCurrentProgress);
        canvas.drawArc(rectF,mCurrentProgress,315,false,mPaint);

        RectF rectF1 = new RectF(strokeWidth+30,strokeWidth+30,getWidth()-strokeWidth-30,getHeight()-strokeWidth-30);
        canvas.drawArc(rectF1,360-mCurrentProgress,45,false,mPaint);

    }
    private ValueAnimator mValueAnimator;
    public void startLoading(){
        //属性动画
        mValueAnimator = ObjectAnimator.ofFloat(0,360);
        mValueAnimator.setDuration(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                //Log.e("startLoading","progress="+progress);
                RotateLoadingProgressBarView.this.mCurrentProgress = (int)progress;
                invalidate();
            }
        });
        mValueAnimator.setRepeatCount(100000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.start();
    }

    public void stopLoading(){
        if (null != mValueAnimator && mValueAnimator.isStarted()){
            mValueAnimator.cancel();
        }
    }



}
