package com.example.ringtone;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		File newSoundFile = new File(Environment.getExternalStorageDirectory(), "hongdou.mp3");
		Uri mUri = Uri.parse("android.resource://com.example.ringtone/"+R.raw.hongdou);
		ContentResolver mCr = getContentResolver();
		AssetFileDescriptor soundFile;
		try 
		{
		       soundFile= mCr.openAssetFileDescriptor(mUri, "r");
		   } catch (FileNotFoundException e) 
		   {
		       soundFile=null;      
		}

	   try {
	      byte[] readData = new byte[1024];
	      FileInputStream fis = soundFile.createInputStream();
	      FileOutputStream fos = new FileOutputStream(newSoundFile);
	      int i = fis.read(readData);

	      while (i != -1) {
	        fos.write(readData, 0, i);
	        i = fis.read(readData);
	      }

	      fos.close();
	   } catch (IOException io) 
	   {
	   }
	   
	   ContentValues values = new ContentValues();
	   values.put(MediaStore.MediaColumns.DATA, newSoundFile.getAbsolutePath());
	   values.put(MediaStore.MediaColumns.TITLE, "hongdou");
	   values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/ogg/mp3");
	   values.put(MediaStore.MediaColumns.SIZE, newSoundFile.length());
	   values.put(MediaStore.Audio.Media.ARTIST, R.string.app_name);
	   values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
	   values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
	   values.put(MediaStore.Audio.Media.IS_ALARM, false);
	   values.put(MediaStore.Audio.Media.IS_MUSIC, false);

	   Uri uri = MediaStore.Audio.Media.getContentUriForPath(newSoundFile.getAbsolutePath());
	   mCr.delete(uri, MediaStore.MediaColumns.TITLE + "=\"" + newSoundFile.getName() + "\"", null);

	   Uri newUri = mCr.insert(uri, values);
	  
	   RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, newUri);
	   
	   
	}
}
