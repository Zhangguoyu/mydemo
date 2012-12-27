package com.example.demo.downloader;

import java.util.concurrent.Executor;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

/**
 * 
 * @author Forcs
 * 
 * 异步下载器，可以同时执行多个下载任务
 *
 */
public class AsyncDownloader extends Downloader {
	
	private Context mContext;
	
	private Executor mExecutor = null;

	public AsyncDownloader(Context context) {
		super(context);
		mContext = context;
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mExecutor = AsyncTask.SERIAL_EXECUTOR;
		}
	}
	
	/**
	 * 设置下载任务是否队列化
	 * 队列化指的就是多个任务在一个队列中执行，只有在当前的任务完成后，才会开启下一个任务
	 * @param single true 表示队列化下载，false 表示异步下载
	 */
	public void setSerializable(boolean single) {
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			return;
		}
		
		if(single) {
			mExecutor = AsyncTask.SERIAL_EXECUTOR;
		} else {
			mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
		}
	}
	
	@Override
	public void download(Object token, String from, String to) {
		final DownloadInfo di = new DownloadInfo(token, from, to);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			new DownloadTask().executeOnExecutor(mExecutor, di);
		} else {
			new DownloadTask().execute(di);
		}
	}
	
	private class DownloadTask extends AsyncTask<DownloadInfo, Void, Void> {

		@Override
		protected Void doInBackground(DownloadInfo... params) {
			final DownloadInfo di = params[0];
			Downloader downloader = new Downloader(mContext);
			downloader.setUserAgent(getUserAgent());
			downloader.setDownloadListener(getDownloadListener());
			downloader.download(di.token, di.from, di.to);
			return null;
		}
		
	}
	
	private static class DownloadInfo {
		Object token;
		String from;
		String to;
		
		public DownloadInfo(Object t, String f, String to) {
			this.token = t;
			this.from = f;
			this.to = to;
		}
	}

}
