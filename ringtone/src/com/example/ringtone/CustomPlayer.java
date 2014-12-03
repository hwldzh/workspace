package com.example.ringtone;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;

public class CustomPlayer implements OnBufferingUpdateListener, OnCompletionListener,  MediaPlayer.OnPreparedListener {
	
	private MediaPlayer mPlayer;
	private String ringPath;
	private boolean pause = false;
	private Timer mTimer = new Timer();
	private Main1 activity;
	public static boolean completed = false;
	
	public CustomPlayer(String ringPath, Main1 activity) {
		this.ringPath = ringPath;
		this.activity = activity;
		try {  
            mPlayer = new MediaPlayer();  
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
            mPlayer.setOnBufferingUpdateListener(this);  
            mPlayer .setOnPreparedListener(this);  
        } catch (Exception e) {  
            Log.e("mediaPlayer", "error", e);  
        }  
		mTimer.schedule(mTimerTask, 0, 1000);
	}
	
	@Override
	public void onCompletion(MediaPlayer mp)
	{
		mPlayer.release();
		mPlayer = null;
		Log.i("ABC", "onCompleted");
		activity.mHandler.sendEmptyMessage(1);
		completed = true;
		Main1.isPlaying = false;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrepared(MediaPlayer mp)
	{
		mp.start();
		Log.i("ABC", "onprepared");
		
	}
	
	TimerTask mTimerTask = new TimerTask() {  
        @Override  
        public void run() {  
            if (mPlayer == null)  
                return;  
            if (mPlayer.isPlaying()) {  
                activity.mHandler.sendEmptyMessage(0);  
            }  
        }  
    };  
	
	public void play() {
		if(!pause) {
			playOnNet(0);
			Log.i("ABC", "play on net");
		}
		else {
			mPlayer.start();
		}
	}
	
	public void pause() {
		if(mPlayer.isPlaying()) {
			mPlayer.pause();
			pause= true;
		}
	}
	
	public void callIsComing() {  
        if (mPlayer.isPlaying()) {  
//            playPosition = mPlayer.getCurrentPosition();// 获得当前播放位置 
//            mPlayer.stop();  
        	mPlayer.pause();
        }  
    }  
	
	public void callIsDown() {  
//        if (playPosition > 0) {  
//            playNet(playPosition);  
//            playPosition = 0;  
//        }  
		mPlayer.start();
    }  
	
	private void playOnNet(int position) {
		try {
			mPlayer.reset();
			mPlayer.setDataSource(ringPath);
			mPlayer.prepare();
			mPlayer.start();
			mPlayer.setOnCompletionListener(new CustomPlayer(ringPath, activity));
//			mPlayer.setOnPreparedListener(new OnPreparedListener() 
//			{
//				@Override
//				public void onPrepared(MediaPlayer mp)
//				{
//					mPlayer.start();
//					Log.i("ABC", "play");
//				}
//			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
