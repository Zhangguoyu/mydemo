package com.example.demo.downloader;

import java.io.File;

import com.example.demo.downloader.Downloader.DownloadListener;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements DownloadListener {
	
	private AsyncDownloader mDownloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mDownloader = new AsyncDownloader(this);
		mDownloader.setDownloadListener(this);
		mDownloader.setSerializable(true);
		mDownloader.download(0, 
				"http://img.cnbeta.com/newsimg/121227/1120170459448679.jpg", 
				Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "temp.jpg");
		mDownloader.download(1, 
				"http://img.cnbeta.com/newsimg/121227/1120171664232850.jpg", 
				Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "temp1.jpg");
		mDownloader.download(2, 
				"http://img.cnbeta.com/newsimg/121227/11201842070183991.jpg", 
				Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "temp2.jpg");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onDownloadStart(Object token) {
		Log.d("downloader", "onDownloadStart " + token);
	}

	@Override
	public void onDownloading(Object token, float percent) {
		Log.d("downloader", "onDownloading " + token + " " + percent);
	}

	@Override
	public void onDownloadEnd(Object token, boolean success, String to) {
		Log.d("downloader", "onDownloadEnd " + token + " " + success);
	}

}
