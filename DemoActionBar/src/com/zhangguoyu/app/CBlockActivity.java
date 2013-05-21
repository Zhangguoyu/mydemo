package com.zhangguoyu.app;

import com.zhangguoyu.demo.actionbar.R;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

public class CBlockActivity extends CActivity {
	
	private CBlock mBlock = null;
	
	private static final String STACK_TAG = "cn.emoney.level2";
    private static final String META_DATA_KEY_BLOCK = "block";
    private static final String META_DATA_KEY_BLOCKS = "blocks";
    private static final String META_DATA_KEY_LAYOUT = "layout";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			ActivityInfo info = getPackageManager().getActivityInfo(
					getComponentName(), PackageManager.GET_META_DATA);
			final Bundle metaData = info.metaData;
			String blockClassName = metaData.getString(META_DATA_KEY_BLOCK);
			int blockLayoutResId = metaData.getInt(META_DATA_KEY_LAYOUT);
			int blocksResId = metaData.getInt(META_DATA_KEY_BLOCKS);
			String[] blockClassNames = getResources().getStringArray(blocksResId);
			Class<?> blockClass = getClassLoader().loadClass(blockClassName);
			mBlock = (CBlock) blockClass.newInstance();
			mBlock.setTitle(getTitle());
			mBlock.setContentView(blockLayoutResId);
			
			FrameFragment frame = FrameFragment.newInstance();
			frame.wrap(mBlock);
			
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.main_frame, frame)
				.commit();
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public CBlock getBlock() {
		return mBlock;
	}
	
	public void setBlock(CBlock block) {
		
		mBlock = block;
		FrameFragment frame = FrameFragment.newInstance();
		frame.wrap(mBlock);

		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.main_frame, frame)
			.commit();
	}

    public void startBlock(Object tag, Bundle args) {
    }

    public void startBlock(int id, Bundle args) {
    }
	
	private static class FrameFragment extends CBlockFragment {
		
		static FrameFragment newInstance() {
			return new FrameFragment();
		}
		
	}

}
