package com.example.cameratwo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

public class TranslucencyView extends android.support.v7.widget.AppCompatImageView {
    private Context mContext;
    private int[] mCircleLocation;


    public TranslucencyView(Context context) {
        this(context,null);
    }

    public TranslucencyView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TranslucencyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        initView();
    }


    private void initView(){
            setBackgroundColor(Color.parseColor("#7f000000"));
    }

    public void setCircleLocation(int[] location){
        this.mCircleLocation=location;
        invalidate();
    }
        @Override
        protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
            if(mCircleLocation!=null){
                //掏空一个圆形
                Paint paintarc=new Paint(Paint.ANTI_ALIAS_FLAG);//创建一个画笔实例
                PorterDuffXfermode porterDuffXfermode=new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
                paintarc.setXfermode(porterDuffXfermode);
                paintarc.setAntiAlias(true);
                RectF rectF=new RectF(mCircleLocation[0],mCircleLocation[1],mCircleLocation[2],mCircleLocation[3]);
                canvas.drawArc(rectF,0,360,true,paintarc);
                //画虚线
                Paint paintdashed=new Paint(Paint.ANTI_ALIAS_FLAG);
                paintdashed.setStyle(Paint.Style.STROKE);
                paintdashed.setColor(Color.WHITE);
                paintdashed.setStrokeWidth(5);
                PathEffect pathEffect=new DashPathEffect(new float[]{10,10},0);
                paintdashed.setPathEffect(pathEffect);
                canvas.drawArc(rectF,0,360,true,paintdashed);
                //画矩形框
                /** Paint paintrect=new Paint(Paint.ANTI_ALIAS_FLAG);
                 PorterDuffXfermode porterDuffXfermode1=new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
                 paintrect.setXfermode(porterDuffXfermode1);
                 paintrect.setAntiAlias(true);
                 //paintrect.setStrokeWidth(5);
                 canvas.drawRect(200, 400, 900, 1300, paintrect);*/
                //画椭圆
                Paint paintoval=new Paint(Paint.ANTI_ALIAS_FLAG);
                PorterDuffXfermode porterDuffXfermode2=new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
                paintoval.setXfermode(porterDuffXfermode2);
                paintoval.setAntiAlias(true);
                //paintoval.setStrokeWidth(10);
                canvas.drawOval(100,200,1000,1200,paintoval);
            }

        }
}
