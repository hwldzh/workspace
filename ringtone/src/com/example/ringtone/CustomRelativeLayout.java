package com.example.ringtone;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class CustomRelativeLayout extends RelativeLayout
{
	private Paint mPaint;
	private int mBgColor;
	private int progress = 0;
	private int i=0;
	
	public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		mPaint = new Paint();
		mBgColor = getResources().getColor(R.color.blue);
	}
	
	public CustomRelativeLayout(Context context, AttributeSet attrs) 
	{  
        this(context, attrs, 0);//调用自己的构造函数  
    } 

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		float mHeight = getHeight();
		float mWidth = getWidth();
		if(Main1.isPlaying) {
			progress += mWidth/(Main1.ringTime-1);
			
			mPaint.setColor(mBgColor);
			mPaint.setAntiAlias(true);
			mPaint.setStyle(Paint.Style.FILL);
			
			canvas.drawRect(0, 0, progress, mHeight, mPaint);
			i++;
			Log.i("ABC","i="+i);
		}
		else if(Main1.isPlaying ==false && CustomPlayer.completed == true) {
			mPaint.setColor(getResources().getColor(R.color.white));
			canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
		}
	}
	

}
