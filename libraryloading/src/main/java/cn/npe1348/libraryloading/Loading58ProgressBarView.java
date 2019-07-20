package cn.npe1348.libraryloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class Loading58ProgressBarView extends View {
    private static final int SQUARE = 0;
    private static final int CIRCLE = 1;
    private static final int TRIANGLE = 2;

    private Context context;
    private int mSquareColor = Color.RED;
    private int mCircleColor = Color.GREEN;
    private int mTriangleColor = Color.BLUE;
    private Paint mSquarePaint, mCirclePaint, mTrianglePaint;

    private int mForm = SQUARE;

    private int count = 0;

    private Path mPath;


    private Paint getPaintByColor(int color){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }
    public Loading58ProgressBarView(Context context) {
        this(context,null);
    }

    public Loading58ProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Loading58ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Loading58ProgressBarView);
        mSquareColor = array.getColor(R.styleable.Loading58ProgressBarView_squareColor,mSquareColor);
        mCircleColor = array.getColor(R.styleable.Loading58ProgressBarView_circleColor,mCircleColor);
        mTriangleColor = array.getColor(R.styleable.Loading58ProgressBarView_triangleColor,mTriangleColor);
        array.recycle();

        mSquarePaint = getPaintByColor(mSquareColor);
        mCirclePaint = getPaintByColor(mCircleColor);
        mTrianglePaint = getPaintByColor(mTriangleColor);
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
        int radius = getWidth()/2;
        Log.e("onDraw","mForm="+mForm);
        if (SQUARE == mForm){
            canvas.drawRect(new Rect(0,0,getWidth(),getHeight()),mSquarePaint);
        }else if(CIRCLE == mForm){
            canvas.drawCircle(getWidth()/2,getHeight()/2,getHeight()/2,mCirclePaint);
        }else if(TRIANGLE == mForm){
            int width = getWidth();
            Log.e("onDraw","width="+width);
            double temp = Math.pow(width,2)-Math.pow(width/2,2);
            Log.e("onDraw","temp="+temp+",Math.pow(width,2)="+Math.pow(width,2)+",Math.pow(width/2,2)="+Math.pow(width/2,2)+",Math.sqrt(temp)="+Math.sqrt(temp));
            int mTriangleHeight = (int)(Math.sqrt(temp));
            //实例化路径
            if (null == mPath) {
                mPath = new Path();
                mPath.moveTo(0, width / 2 + mTriangleHeight / 2);// 此点为多边形的起点
                mPath.lineTo(width, width / 2 + mTriangleHeight / 2);
                mPath.lineTo(width / 2, width / 2 - mTriangleHeight / 2);
                mPath.close(); // 使这些点构成封闭的多边形
            }
            canvas.drawPath(mPath, mTrianglePaint);
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Loading58ProgressBarView.this.mForm = count%3;
            invalidate();
            count++;
        }
    };
    private Timer timer;
    public void startLoading(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        };
        timer = new Timer();
        timer.schedule(task,0,1000);
    }

    public void stopLoading(){
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }



}
