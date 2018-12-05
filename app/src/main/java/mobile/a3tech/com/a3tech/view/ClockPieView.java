package mobile.a3tech.com.a3tech.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.MyUtils;


public class ClockPieView extends View {

    private Paint textPaint;
    private Paint redPaint;
    private Paint linePaint;
    private Paint whitePaint;
    private int mViewWidth;
    private int mViewHeight;
    private int textSize;
    private int pieRadius;
    private Point pieCenterPoint;
    private Point tempPoint;
    private Point tempPointRight;
    private int lineLength;
    private float leftTextWidth;
    private float rightTextWidth;
    private float topTextHeight;
    private int lineThickness;
    private RectF cirRect;
    private Rect textRect;
    static final int DIV = 6;

    private ArrayList<ClockPieHelper> pieArrayList = new ArrayList<ClockPieHelper>();

    private final int TEXT_COLOR = Color.parseColor("#9B9A9B");
    private final int GRAY_COLOR = Color.parseColor("#D4D3D4");
    private final int RED_COLOR = Color.GREEN;

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
            for(ClockPieHelper pie : pieArrayList){
                pie.update();
                if(!pie.isAtRest()){
                    needNewFrame = true;
                }
            }
            if (needNewFrame) {
                postDelayed(this, 0);
            }
            invalidate();
        }
    };

    public ClockPieView(Context context){
        this(context,null);
    }
    public ClockPieView(Context context, AttributeSet attrs){
        super(context, attrs);
        textSize = MyUtils.sp2px(context, 18);
        lineThickness = MyUtils.dip2px(context, 2);
        lineLength = MyUtils.dip2px(context, 6);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(context.getResources().getColor(R.color.facebook_light));
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        Paint.FontMetrics fm = new Paint.FontMetrics();
        textPaint.getFontMetrics(fm);
        textRect = new Rect();
        textPaint.getTextBounds("18",0,1,textRect);
        redPaint = new Paint(textPaint);
        redPaint.setColor(context.getResources().getColor(R.color.orange));
        linePaint = new Paint(textPaint);
        linePaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        linePaint.setStrokeWidth(lineThickness);
        whitePaint = new Paint(linePaint);
        whitePaint.setColor(Color.WHITE);
        tempPoint = new Point();
        pieCenterPoint = new Point();
        tempPointRight = new Point();
        cirRect = new RectF();
        leftTextWidth = textPaint.measureText("18");
        rightTextWidth = textPaint.measureText("6");
        topTextHeight = textRect.height();
    }

    public void setDate(ArrayList<ClockPieHelper> helperList){
        if(helperList != null && !helperList.isEmpty()){
            int pieSize = pieArrayList.isEmpty()? 0:pieArrayList.size();
            for(int i=0;i<helperList.size();i++){
                if(i>pieSize-1){
//                    float mStart = helperList.get(i).getStart();
                    pieArrayList.add(new ClockPieHelper(0,0,helperList.get(i)));
                }else{
                    pieArrayList.set(i, pieArrayList.get(i).setTarget(helperList.get(i)));
                }
            }
            int temp = pieArrayList.size() - helperList.size();
            for(int i=0; i<temp; i++){
                pieArrayList.remove(pieArrayList.size()-1);
            }
        }else {
            pieArrayList.clear();
        }

        removeCallbacks(animator);
        post(animator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        if(pieArrayList != null){
            for(ClockPieHelper helper:pieArrayList){
                canvas.drawArc(cirRect,helper.getStart(),helper.getSweep(),true,redPaint);
            }
        }
    }

    private void drawBackground(Canvas canvas){
        for(int i=0; i<DIV; i++){
            tempPoint.set(pieCenterPoint.x-(int)(Math.sin(Math.PI / DIV * i)*(pieRadius+lineLength)),
                    pieCenterPoint.y-(int)(Math.cos(Math.PI / DIV * i)*(pieRadius+lineLength)));
            tempPointRight.set(
                    pieCenterPoint.x+(int)(Math.sin(Math.PI / DIV * i)*(pieRadius+lineLength)),
                    pieCenterPoint.y+(int)(Math.cos(Math.PI / DIV * i)*(pieRadius+lineLength)));
            canvas.drawLine(tempPoint.x,tempPoint.y,tempPointRight.x,tempPointRight.y,linePaint);
        }
        canvas.drawCircle(pieCenterPoint.x,pieCenterPoint.y,pieRadius+lineLength/2, whitePaint);
        canvas.drawCircle(pieCenterPoint.x,pieCenterPoint.y,pieRadius+lineThickness,linePaint);
        canvas.drawCircle(pieCenterPoint.x,pieCenterPoint.y,pieRadius,whitePaint);
        canvas.drawText("12", pieCenterPoint.x, topTextHeight, textPaint);
        canvas.drawText("6",pieCenterPoint.x,mViewHeight,textPaint);
        canvas.drawText("9",leftTextWidth/2,
                pieCenterPoint.y+textRect.height()/2,textPaint);
        canvas.drawText("3",mViewWidth-rightTextWidth/2,
                pieCenterPoint.y+textRect.height()/2,textPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        pieRadius = mViewWidth/2-lineLength*2-(int)(textPaint.measureText("18")/2);
        pieCenterPoint.set(mViewWidth/2-(int)rightTextWidth/2+(int)leftTextWidth/2,
                mViewHeight/2+textSize/2-(int)(textPaint.measureText("18")/2));
        cirRect.set(pieCenterPoint.x-pieRadius,
                pieCenterPoint.y-pieRadius,
                pieCenterPoint.x+pieRadius,
                pieCenterPoint.y+pieRadius);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec){
        int preferred = 3;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec){
        int preferred = mViewWidth;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred){
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int measurement;

        switch(View.MeasureSpec.getMode(measureSpec)){
            case View.MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case View.MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }


}