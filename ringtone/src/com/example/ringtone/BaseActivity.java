package com.example.ringtone;

import cn.bmob.v3.Bmob;
import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity
{
	private String appId = "4971b0c98421cfe513e6ee1602590fd1";
	
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //��ʼ�� Bmob SDK����һ������Ϊ�����ģ��ڶ�������ΪApplication ID
	    Bmob.initialize(this, appId);
	    
	}
}
