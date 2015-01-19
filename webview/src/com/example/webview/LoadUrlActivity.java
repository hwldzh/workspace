package com.example.webview;

import android.os.Bundle;
import android.webkit.WebView;


public class LoadUrlActivity extends BaseActivity {

	private WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadurltest);
		
		mWebView = (WebView)findViewById(R.id.webview);
	} 
	
}
