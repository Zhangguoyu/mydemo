package com.example.demo.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 
 * @author Forcs
 * 
 *         下载器，根据指定的URL下载文件，并将文件保存到指定的路径下
 * 
 */
public class Downloader {

	/**
	 * 
	 * 下载监听器，用它监听下载过程
	 * 
	 */
	public interface DownloadListener {

		/**
		 * 下载开始时被回调
		 * 
		 * @param token
		 *            下载标记，可以通过这个标记区分下载的对象
		 */
		public void onDownloadStart(Object token);

		/**
		 * 下载过程中被回调
		 * 
		 * @param token
		 *            下载标记，可以通过这个标记区分下载的对象
		 * @param percent
		 *            下载进度 00.00
		 */
		public void onDownloading(Object token, float percent);

		/**
		 * 下载结束后被回调
		 * 
		 * @param token
		 *            下载标记，可以通过这个标记区分下载的对象
		 * @param success
		 *            标识是伐下载完成，true表示完成，false表示由于其他原因产生异常而导致下载终止，未完成
		 * @param to
		 *            保存的路径
		 */
		public void onDownloadEnd(Object token, boolean success, String to);

	}

	private static final String LOG_TAG = "Downloader";

	private static final int MSG_DOWNLOAD_START = 0;
	private static final int MSG_DOWNLOADING = 1;
	private static final int MSG_DOWNLOAD_END = 2;

	private DownloadListener mDownloadListener;

	private Context mContext;

	private String mUserAgent;

	public Downloader(Context context) {
		mContext = context;
		mUserAgent = "com.example.demo.downloader";
	}

	/**
	 * 设置User Agent
	 * 
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent) {
		mUserAgent = userAgent;
	}

	/**
	 * 返回User Agent
	 * 
	 * @return
	 */
	public String getUserAgent() {
		return mUserAgent;
	}

	/**
	 * 设置下载监听器
	 * 
	 * @param l
	 *            监听器
	 */
	public void setDownloadListener(DownloadListener l) {
		mDownloadListener = l;
	}

	/**
	 * 返回下载监听器
	 * 
	 * @return
	 */
	public DownloadListener getDownloadListener() {
		return mDownloadListener;
	}

	/**
	 * 下载
	 * 
	 * @param token
	 *            下载标记，可以通过这个标记区分下载的对象
	 * @param from
	 *            下载URL
	 * @param to
	 *            保存路径
	 */
	public void download(Object token, String from, String to) {
		downloadInner(token, from, to);
	}

	protected void downloadInner(Object token, String from, String to) {

		sendStartingDownloadMessage(token);

		File toFile = new File(to);
		if (!prepare(toFile)) { // 准备失败
			Log.w(LOG_TAG, "Cann't prepare file " + to);
			sendEndingDownloadMessage(token, false, to);
		}

		boolean result = false;

		AndroidHttpClient client = AndroidHttpClient.newInstance(mUserAgent,
				mContext);
		HttpGet request = new HttpGet(from);
		try {
			HttpResponse response = client.execute(request);

			int code = response.getStatusLine().getStatusCode();
			if (code < HttpStatus.SC_OK
					|| code >= HttpStatus.SC_MULTIPLE_CHOICES) {
				// 网络异常
				Log.w(LOG_TAG, "Download false!!!ERROR STATUS CODE " + code);
				sendEndingDownloadMessage(token, false, to);
				return;
			}

			HttpEntity entity = response.getEntity();
			if (entity == null) {
				// 获取不到HTTP Entity
				Log.w(LOG_TAG, "Download false!!! entity is null");
				sendEndingDownloadMessage(token, false, to);
				return;
			}

			// 读取输入流，并保存到指定路径下的文件中
			InputStream in = entity.getContent();
			final long totalLength = entity.getContentLength();
			FileOutputStream out = new FileOutputStream(toFile);
			byte[] buffer = new byte[8 * 1024 * 1024];
			int len = -1;
			long currentLength = 0;
			while ((len = in.read(buffer)) != -1) {
				currentLength += len;
				out.write(buffer, 0, len);

				sendDownloadingMessage(token, currentLength, totalLength);
			}
			out.flush();

			out.close();
			in.close();

			result = true;
		} catch (IOException e) {
			result = false;
			e.printStackTrace();
		} finally {
			client.close();
		}

		sendEndingDownloadMessage(token, result, to);
	}

	private boolean prepare(File toFile) {
		final File file = toFile;
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			if (!parent.mkdirs()) {
				return false;
			}
		}

		try {
			if (file.exists()) {
				file.delete();
			}
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	private void sendDownloadingMessage(Object token, long currentLength,
			long totalLength) {
		Message msg = mHandler.obtainMessage(MSG_DOWNLOADING);
		DownloadInfo di = new DownloadInfo();
		di.token = token;
		di.currentLength = currentLength;
		di.totalLength = totalLength;
		msg.obj = di;
		mHandler.sendMessage(msg);
	}

	private void sendStartingDownloadMessage(Object token) {
		Message msg = mHandler.obtainMessage(MSG_DOWNLOAD_START);
		DownloadInfo di = new DownloadInfo();
		di.token = token;
		msg.obj = di;
		mHandler.sendMessage(msg);
	}

	private void sendEndingDownloadMessage(Object token, boolean success,
			String to) {
		Message msg = mHandler.obtainMessage(MSG_DOWNLOAD_END);
		DownloadInfo di = new DownloadInfo();
		di.token = token;
		di.success = success;
		di.to = to;
		msg.obj = di;
		mHandler.sendMessage(msg);
	}

	private Handler mHandler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(Message msg) {
			final DownloadInfo di = (DownloadInfo) msg.obj;
			final Object token = di.token;

			switch (msg.what) {
			case MSG_DOWNLOAD_START:
				if (mDownloadListener != null) {
					mDownloadListener.onDownloadStart(token);
				}
				break;

			case MSG_DOWNLOADING:
				final long currentLength = di.currentLength;
				final long totalLength = di.totalLength;
				if (mDownloadListener != null) {
					mDownloadListener.onDownloading(token,
							(float) currentLength * 100 / totalLength);
				}
				break;

			case MSG_DOWNLOAD_END:
				final boolean success = di.success;
				final String to = di.to;
				if (mDownloadListener != null) {
					mDownloadListener.onDownloadEnd(token, success, to);
				}
				break;
			}
		}
	};

	private static class DownloadInfo {
		Object token;
		long totalLength;
		long currentLength;
		boolean success;
		String to;
	}

}
