package com.iplusplus.aboutwish.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ListView.FixedViewInfo;

public class AWishListView extends FrameLayout {

	private static final int INPUT_VIEW_PADDING_BOTTOM = 50;

	private MyListView mListView;

	private MyEditText mInputView;
	
	private InputMethodManager mInputMethodManager;
	
	private InputViewLayoutInfo mInputViewLayoutInfo;
	
	private boolean mOnlyRequestInputViewLayout = false;
	
	private static class InputViewLayoutInfo {
		int bottom;
		boolean keepFixedPadding;
		int left;
		int right;
		int height;
	}

	public AWishListView(Context context) {
		super(context);
		initChilds(null);
	}

	public AWishListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initChilds(attrs);
	}

	public AWishListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initChilds(attrs);
	}

	private void initChilds(AttributeSet attrs) {
		
		mInputViewLayoutInfo = new InputViewLayoutInfo();
		mInputViewLayoutInfo.bottom = -1;
		mInputViewLayoutInfo.keepFixedPadding = true;
		
		final Context c = getContext();
		mListView = new MyListView(c, attrs);
		mInputView = new MyEditText(c, attrs);
		LayoutParams p = (LayoutParams) generateDefaultLayoutParams();
		
		addViewInLayout(mListView, -1, p);
		addViewInLayout(mInputView, -1, p);
		
		mInputMethodManager = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	public EditText getInputView() {
		return mInputView;
	}

	public ListView getListView() {
		return mListView;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		
		if(!mOnlyRequestInputViewLayout && !mInputView.isFocused()) {
			super.onLayout(changed, left, top, right, bottom);
		} else {
			mInputView.layout(mInputViewLayoutInfo.left, 
					mInputViewLayoutInfo.bottom-mInputViewLayoutInfo.height, 
					mInputViewLayoutInfo.right, 
					mInputViewLayoutInfo.bottom);
			mOnlyRequestInputViewLayout = false;
		}
	}

	@Override
	protected FrameLayout.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
	}

	@Override
	public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new LayoutParams(getContext(), attrs);
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return new LayoutParams(p);
	}

	@Override
	public void addView(View child) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addView(View child, int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addView(View child, int width, int height) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAllViews() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAllViewsInLayout() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeView(View view) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeViewAt(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeViewInLayout(View view) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeViews(int start, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeViewsInLayout(int start, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
		return (p instanceof LayoutParams) && super.checkLayoutParams(p);
	}

	public static class LayoutParams extends FrameLayout.LayoutParams {

		public LayoutParams(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public LayoutParams(ViewGroup.LayoutParams p) {
			super(p);
		}

		public LayoutParams(MarginLayoutParams p) {
			super(p);
		}

		public LayoutParams(int width, int height) {
			super(width, height);
		}

		public LayoutParams(int width, int height, int gravity) {
			super(width, height, gravity);
		}

	}
	
	private void onStartScroll(AbsListView view, View header) {
		mInputViewLayoutInfo.keepFixedPadding = 
				!(header.getBottom()-INPUT_VIEW_PADDING_BOTTOM < mInputViewLayoutInfo.bottom);
	}
	
	private void onScrolling(AbsListView view, View header, 
			int firstVisibleItem, boolean positiveDirection, int dy) {
		boolean needRequestLayout = false;
		if (firstVisibleItem == 0) {
			final int headerBottom = header.getBottom();
			int delta = headerBottom - mInputViewLayoutInfo.bottom;
			int newBottom;
			if(delta > INPUT_VIEW_PADDING_BOTTOM || mInputViewLayoutInfo.keepFixedPadding) {
				
				if(!mInputViewLayoutInfo.keepFixedPadding && delta > INPUT_VIEW_PADDING_BOTTOM) {
					mInputViewLayoutInfo.keepFixedPadding = true;
				}
				newBottom = headerBottom - INPUT_VIEW_PADDING_BOTTOM;
				
			} else {
				
				newBottom = mInputViewLayoutInfo.bottom;
				newBottom += dy;
				if(newBottom > mInputViewLayoutInfo.height + 20) {
					newBottom = mInputViewLayoutInfo.height + 20;
				} else if (newBottom < -1) {
					newBottom = -1;
				}
			}
			
			if(needRequestLayout = (newBottom != mInputViewLayoutInfo.bottom)) {
				mInputViewLayoutInfo.bottom = newBottom;
			}
			
		} else {
			
			if(mInputViewLayoutInfo.keepFixedPadding) {
				mInputViewLayoutInfo.keepFixedPadding = false;
			}
			
			int newBottom = mInputViewLayoutInfo.bottom;
			newBottom += dy;
			if(newBottom > mInputViewLayoutInfo.height + 20) {
				newBottom = mInputViewLayoutInfo.height + 20;
			} else if (newBottom < -1) {
				newBottom = -1;
			}
			
			if(needRequestLayout = (newBottom != mInputViewLayoutInfo.bottom)) {
				mInputViewLayoutInfo.bottom = newBottom;
			}
		}
		
		if(needRequestLayout) {
			mOnlyRequestInputViewLayout = true;
			requestLayout();
		}
	}
	
	private void onEndScroll(AbsListView view, View header) {
	}
	
	private class MyEditText extends EditText {

		public MyEditText(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		
		@Override
		protected void onLayout(boolean changed, int left, int top, int right,
				int bottom) {
			super.onLayout(changed, left, top, right, bottom);
			mInputViewLayoutInfo.left = left;
			mInputViewLayoutInfo.right = right;
			mInputViewLayoutInfo.height = bottom-top;
		}
		
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			return super.onKeyUp(keyCode, event);
		}
		
	}

	private class MyListView extends ListView implements OnScrollListener {

		private OnScrollListener mScrollListener;

		private final static int HEADER_HEIGHT = 400;

		private FrameLayout mHeader = null;
		
		private ArrayList<FixedViewInfo> mHeaderViewInfo;
		
		private int mActivePointerId = -1;
		
		private int mLastMotionY;
		
		private boolean mIsScrollingAlongPositiveDirection = true;
		
		private int mDeltaY;
		
		private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;

		public MyListView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init(context);
		}

		private void init(Context context) {
			
			mHeader = new FrameLayout(context);
			mHeader.setLayoutParams(new AbsListView.LayoutParams(
					LayoutParams.MATCH_PARENT, HEADER_HEIGHT));
			mHeader.setBackgroundColor(Color.CYAN);
			
			mHeaderViewInfo = new ArrayList<ListView.FixedViewInfo>();
			FixedViewInfo info = new FixedViewInfo();
			info.data = null;
			info.view = mHeader;
			info.isSelectable = false;
			mHeaderViewInfo.add(info);
			
			super.setOnScrollListener(this);
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			final int action = ev.getAction()&MotionEvent.ACTION_MASK;
			
			switch(action) {
			case MotionEvent.ACTION_DOWN:
				mLastMotionY = (int) ev.getY();
				mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
				break;
			case MotionEvent.ACTION_MOVE:
				if(mActivePointerId != -1) {
					final int y = (int) MotionEventCompat.getY(ev, 
							MotionEventCompat.findPointerIndex(ev, mActivePointerId));
					final int dy = y - mLastMotionY;
					mDeltaY = dy;
					mIsScrollingAlongPositiveDirection = dy > 0;
					mLastMotionY = y;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mActivePointerId = -1;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				final int index = MotionEventCompat.getActionIndex(ev);
				mActivePointerId = MotionEventCompat.getPointerId(ev, index);
				mLastMotionY = (int) MotionEventCompat.getY(ev, index);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				onSecondaryPointerUp(ev);
				break;
			}
			return super.onTouchEvent(ev);
		}
		
		private void onSecondaryPointerUp(MotionEvent ev) {
			final int index = MotionEventCompat.getActionIndex(ev);
			final int pointerId = MotionEventCompat.getPointerId(ev, index);
			if(pointerId != mActivePointerId) {
				int newPointerIndex = index==0?1:0;
				mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
				mLastMotionY = (int) MotionEventCompat.getY(ev, newPointerIndex);
			}
		}

		@Override
		public void setOnScrollListener(OnScrollListener l) {
			mScrollListener = l;
			super.setOnScrollListener(this);
		}
		
		@Override
		public void setAdapter(ListAdapter adapter) {
			super.setAdapter(new MyHeaderViewListAdapter(mHeaderViewInfo, adapter));
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

			onScrolling(view, mHeader, firstVisibleItem, mIsScrollingAlongPositiveDirection, mDeltaY);

			if (mScrollListener != null) {
				mScrollListener.onScroll(view, firstVisibleItem,
						visibleItemCount, totalItemCount);
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			
			if(mScrollState != scrollState) {
				if(scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					onEndScroll(view, mHeader);
				} else if (mScrollState == OnScrollListener.SCROLL_STATE_IDLE){
					onStartScroll(view, mHeader);
				}
			}
			mScrollState = scrollState;

			if (mScrollListener != null) {
				mScrollListener.onScrollStateChanged(view, scrollState);
			}
		}
		
		@Override
		public boolean performItemClick(View view, int position, long id) {
			clearInputViewFocus();
			return super.performItemClick(view, position, id);
		}
		
		@Override
		public boolean performLongClick() {
			clearInputViewFocus();
			return super.performLongClick();
		}
		
		@Override
		public boolean performClick() {
			clearInputViewFocus();
			return super.performClick();
		}
		
		private void clearInputViewFocus() {
			if(mInputMethodManager.isActive(mInputView)) {
				mInputMethodManager.hideSoftInputFromWindow(
						mInputView.getWindowToken(), 0);
			}
			if(mInputView.isFocused()) {
				mInputView.clearFocus();
			}
		}

	}
	
	private class MyHeaderViewListAdapter extends HeaderViewListAdapter {

		public MyHeaderViewListAdapter(
				ArrayList<FixedViewInfo> headerViewInfos, ListAdapter adapter) {
			super(headerViewInfos, null, adapter);
		}
		
	}


}
