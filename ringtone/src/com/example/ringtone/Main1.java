package com.example.ringtone;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Main1 extends BaseActivity
{
	private CustomRelativeLayout mCustomRelativeLayout;
	private ImageButton mPlayOrPauseBtn;
	private boolean PauseState = true; //默认处于暂停状态
	private String url = "http://file.bmob.cn/M00/D6/06/oYYBAFR8H0mAYNbsAAaZy_LaGeI630.mp3";
	public static int buchang = 0;
	private CustomPlayer mPlayer;
	public static int ringTime = 18; //铃声的时长18秒
	public static boolean isPlaying = false;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		
		mCustomRelativeLayout = (CustomRelativeLayout)findViewById(R.id.custom1);
		mPlayOrPauseBtn = (ImageButton)findViewById(R.id.img_play_pause);
		mPlayOrPauseBtn.setOnClickListener(listener);
		
		mPlayer = new CustomPlayer(url, this);
		
		TelephonyManager telephonyManager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);  
        telephonyManager.listen(new CustomPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);  
	}
	
	private final class CustomPhoneListener extends android.telephony.PhoneStateListener {  
        @Override  
        public void onCallStateChanged(int state, String incomingNumber) {  
            switch (state) {  
            case TelephonyManager.CALL_STATE_RINGING: //电话来了  
                mPlayer.callIsComing();  
                break;  
            case TelephonyManager.CALL_STATE_IDLE:  //通话结束  
                mPlayer.callIsDown();  
                break;  
            }  
        }  
    }  
	
	OnClickListener listener = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.img_play_pause:
				if(PauseState)
				{
					mPlayOrPauseBtn.setBackgroundResource(R.drawable.music_pause);	
					Log.i("ABC", "play  the music");
				}
				else {
					mPlayOrPauseBtn.setBackgroundResource(R.drawable.music_play);
					Log.i("ABC", "pause the music");
				}
				Thread mThread = new Thread(mRunnable);
				mThread.start();
				break;

			default:
				break;
			}
			
		}
	};
	
	Runnable mRunnable = new Runnable() {
		
		@Override
		public void run() {
			if(PauseState) {
				PauseState = false;
				mPlayer.play();
				isPlaying = true;
			}
			else {
				PauseState = true;
				mPlayer.pause();
				isPlaying = false;
			}
		}
	};
	
	Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:
				mCustomRelativeLayout.invalidate();
				break;
			case 1:
				mCustomRelativeLayout.invalidate();
			default:
				break;
			}
		}
	};
}
