package com.zhangguoyu.widget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.zhangguoyu.app.CBlock;
import com.zhangguoyu.app.CBlockFragment;
import com.zhangguoyu.demo.actionbar.R;
import com.zhangguoyu.demo.actionbar.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

public class CBlockPager extends ViewPager {
	
	private static final HashMap<String, Class<?>> sClassMap =
        new HashMap<String, Class<?>>();
	
	private FragmentManager mFragmentManager = null;
	private static final int DEFAULT_PAGE_LIMIT = 5;
	private int mPageLimit = DEFAULT_PAGE_LIMIT;
	private DefaultFragmentPagerAdapter mDefaultAdapter = null;
	
	private static final String XML_BLOCK_PAGE = "BlockPager";
	private static final String XML_BLOCK_ITEM = "Block";
	
	public CBlockPager(Context context) {
		super(context);
		setOffscreenPageLimit(mPageLimit);
	}

	public CBlockPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOffscreenPageLimit(mPageLimit);
	}

	public void addBlockFromResource(int resId) {
		ensureFragmentManagerExsit();
		setDefaultAdapter();
		List<CBlockInfo> blocks = new ArrayList<CBlockInfo>();
		inflateBlockFromXml(getContext(), resId, blocks);
		mDefaultAdapter.addBlocks(blocks);
	}

	public void addBlock(CBlock block, Bundle arg) {
		ensureFragmentManagerExsit();
		setDefaultAdapter();
		mDefaultAdapter.addBlock(block, arg);
	}
	
	private void ensureFragmentManagerExsit() {
		if (mFragmentManager == null) {
			throw new IllegalStateException("Please call setSupportFragmentManager() before adding block");
		}
	}
	
	private void setDefaultAdapter() {
		if(mDefaultAdapter == null) {
			mDefaultAdapter = new DefaultFragmentPagerAdapter(mFragmentManager);
		}
		setAdapter(mDefaultAdapter);
	}
	
	public void setSupportFragmentManager(FragmentManager fm) {
		mFragmentManager = fm;
	}
	
	private class CBlockInfo {
		CBlock block;
		Bundle arg;
		
		CBlockInfo(CBlock blc) {
			block = blc;
		}
		
		CBlockInfo(CBlock blc, Bundle ag) {
			block = blc;
			arg = ag;
		}
	}
	
	private void inflateBlockFromXml(Context context, int xmlResId, List<CBlockInfo> outBlocks) {
		final Context c = context;
		final ClassLoader loader = c.getClassLoader();
		XmlResourceParser parser = c.getResources().getXml(xmlResId);
		AttributeSet attrs = Xml.asAttributeSet(parser);
		try {
			int eventType = parser.getEventType();
			String tagName;
			do {
	            if (eventType == XmlPullParser.START_TAG) {
	                tagName = parser.getName();
	                if (tagName.equals(XML_BLOCK_PAGE)) {
	                    eventType = parser.next();
	                    break;
	                }
	                
	                throw new RuntimeException("Resolve block xml "+xmlResId+" exception " + tagName);
	            }
	            eventType = parser.next();
	        } while (eventType != XmlPullParser.END_DOCUMENT);
			
			while(eventType != XmlPullParser.END_DOCUMENT) {
				switch(eventType) {
					case XmlPullParser.START_TAG:
						tagName = parser.getName();
						if(tagName.equals(XML_BLOCK_ITEM)) {
							TypedArray a = c.obtainStyledAttributes(
									attrs, R.styleable.CBlockPagerXml);
							final String className = a.getString(R.styleable.CBlockPagerXml_className);
							final String title = a.getString(R.styleable.CBlockPagerXml_title);
							final int layoutResId = a.getResourceId(R.styleable.CBlockPagerXml_layout, 0);
                            final int blockId = a.getResourceId(R.styleable.CBlockPagerXml_id, CBlock.NO_ID);
                            final Object blockTag = a.getString(styleable.CBlockPagerXml_tag);
							
							Class<?> clazz = sClassMap.get(className);
				            if (clazz == null) {
				                clazz = loader.loadClass(className);
				                sClassMap.put(className, clazz);
				            }
				            CBlock block = (CBlock)clazz.newInstance();
				            block.setId(blockId)
                                    .setTag(blockTag)
                                    .setTitle(title);
				            if (layoutResId > 0) {
				            	block.setContentView(layoutResId);
				            }
				            
				            outBlocks.add(new CBlockInfo(block));
							a.recycle();
						}
						break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private class DefaultFragmentPagerAdapter extends FragmentStatePagerAdapter {
		
		private List<CBlockInfo> mBlocks = null;

		public DefaultFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			mBlocks = new ArrayList<CBlockPager.CBlockInfo>();
		}
		
		public void addBlock(CBlock block, Bundle arg) {
			CBlockInfo info = new CBlockInfo(block, arg);
			mBlocks.add(info);
			notifyDataSetChanged();
		}
		
		public void addBlocks(List<CBlockInfo> blocks) {
			mBlocks = blocks;
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int position) {
			CBlockInfo info = mBlocks.get(position);
			CBlockFragment f = (CBlockFragment) CBlockFragment.instantiate(
					getContext(), CBlockFragment.class.getName(), info.arg);
			f.wrap(info.block);
			return f;
		}

		@Override
		public int getCount() {
			if(mBlocks != null) {
				return mBlocks.size();
			}
			return 0;
		}
		
	}

}
