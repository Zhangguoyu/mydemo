package com.iplusplus.aboutwish.core;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.iplusplus.aboutwish.Wish;
import com.iplusplus.aboutwish.core.WishService.ResultInfo;

public class WishManagerDelegate implements IWishManager, IWishService {
	
	public interface Handler {
		
		public void onHandlePost(Wish wish, ResultInfo result);
		
		public void onHandleSupport(Wish wish, ResultInfo result);
		
		public void onHandleRelay(Wish wish, ResultInfo result);
		
		public void onHandleRequestWishList(ResultInfo result);
		
	}
	
	private static final int TOKEN_POST = 1;
	private static final int TOKEN_SUPPORT = 2;
	private static final int TOKEN_RELAY = 3;
	private static final int TOKEN_REQUEST = 4;
	
	private IWishManager mManager;
	
	private WishService mService;
	
	private List<Handler> mHandlers;
	
	public WishManagerDelegate(IWishManager manager, WishService service) {
		mManager = manager;
		mService = service;
		
		mHandlers = new ArrayList<Handler>();
	}

	@Override
	public boolean post(Wish wish) {
		return mManager.post(wish);
	}

	@Override
	public int support(Wish wish) {
		return mManager.support(wish);
	}

	@Override
	public int replay(Wish wish) {
		return mManager.replay(wish);
	}

	@Override
	public int getWishCount() {
		return mManager.getWishCount();
	}

	@Override
	public Wish getWishAt(int position) {
		return mManager.getWishAt(position);
	}

	@Override
	public ResultInfo requestWishs() {
		return mService.requestWishs();
	}

	@Override
	public ResultInfo postWish(Wish wish) {
		return mService.postWish(wish);
	}

	@Override
	public ResultInfo postSupport(Wish wish) {
		return mService.postSupport(wish);
	}

	@Override
	public ResultInfo postReplay(Wish wish) {
		return mService.postReplay(wish);
	}
	
	public void handlePost(Wish wish) {
		
	}
	
	public void handleSupport(Wish wish) {
		
	}
	
	public void handleRelay(Wish wish) {
		
	}
	
	public void handleRequestWishList() {
		
	}
	
	public void registerHandler(Handler handler) {
		mHandlers.add(handler);
	}
	
	public void unregisterHandle(Handler handler) {
		mHandlers.remove(handler);
	}
	
	private static class TaskArg {
		int token;
		Wish wish;
	}
	
	private static class TaskResult extends TaskArg {
		ResultInfo result;
	}
	
	private class HandleTask extends AsyncTask<TaskArg, Void, TaskResult> {

		@Override
		protected TaskResult doInBackground(TaskArg... params) {
			TaskArg arg = params[0];
			final int token = arg.token;
			TaskResult result = new TaskResult();
			result.token = token;
			switch(token) {
			case TOKEN_POST:
				result.result = postWish(arg.wish);
				break;
			case TOKEN_SUPPORT:
				result.result = postSupport(arg.wish);
				break;
			case TOKEN_RELAY:
				result.result = postReplay(arg.wish);
				break;
			case TOKEN_REQUEST:
				result.result = requestWishs();
				break;
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			final ResultInfo res = result.result;
			for(Handler handler : mHandlers) {
				switch(result.token) {
					case TOKEN_POST:
						handler.onHandlePost(result.wish, res);
						break;
					case TOKEN_SUPPORT:
						handler.onHandleSupport(result.wish, res);
						break;
					case TOKEN_RELAY:
						handler.onHandleRelay(result.wish, res);
						break;
					case TOKEN_REQUEST:
						handler.onHandleRequestWishList(res);
						break;
				}
			}
		}
		
	}
	
}
