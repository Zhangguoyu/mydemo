package com.zhangguoyu.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zhangguoyu.app.CActionBar.CTab;
import com.zhangguoyu.demo.actionbar.R;
import com.zhangguoyu.widget.CMenu;
import com.zhangguoyu.widget.CMenuItem;
import com.zhangguoyu.widget.CSubMenu;

public class CActivity extends FragmentActivity {
	
	private FrameLayout mMainFrame = null;
	private CActionBarImpl mActionBarImpl = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ensureInflateFrame();
	}
	
	@Override
	public void setContentView(int layoutResID) {
		getLayoutInflater().inflate(layoutResID, mMainFrame);
	}
	
	@Override
	public void setContentView(View view) {
		mMainFrame.addView(view);
	}
	
	@Override
	public void setContentView(View view, LayoutParams params) {
		mMainFrame.addView(view, params);
	}
	
	private void ensureInflateFrame() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.cframe);
		mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
		prepareActionBar();
	}
	
	private void prepareActionBar() {
		mActionBarImpl = new CActionBarImpl(this);
		Button back = new Button(this);
		back.setText("<");
		ImageView home = new ImageView(this);
		home.setImageResource(R.drawable.ic_launcher);
		mActionBarImpl.setBackButton(back);
		mActionBarImpl.setLogoView(home);
		mActionBarImpl.setTitle("Demo");
		mActionBarImpl.addTab(mActionBarImpl.buildTab().setTitle("Demo"))
			.addTab(mActionBarImpl.buildTab().setTitle("Demo").setIcon(R.drawable.ic_launcher), 0)
			.addTab(mActionBarImpl.buildTab().setTitle("Demo"));

        CMenu optionsMenu = mActionBarImpl.newMenu();
        dispatchCreateOptionsMene(optionsMenu);

		CMenu menu = mActionBarImpl.newMenu();
		dispatchCreateNavigationMenu(menu);
	}
	
	public void onTabSelected(CTab selectedTab) {
	}
	
	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		mActionBarImpl.setTitle(title);
	}
	
	@Override
	public void setTitle(int titleId) {
		super.setTitle(titleId);
		mActionBarImpl.setTitle(titleId);
	}

    public void dispatchCreateOptionsMene(CMenu menu) {
        if (onCreateOptionsMenu(menu)) {
            mActionBarImpl.inflatdOptionsBarByMenu(menu);
        }
    }
	
	public void dispatchCreateNavigationMenu(CMenu menu) {
		if(onCreateNavigationMenu(menu)) {
			mActionBarImpl.inflatedNavigationBarByMenu(menu);
		}
	}
	
	public boolean onCreateNavigationMenu(CMenu menu) {
        CSubMenu subMenu = menu.addSubMenu(
                R.string.demo_menu, R.drawable.ic_launcher);
        subMenu.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu.add(R.string.demo_menu, R.drawable.ic_launcher);

        CSubMenu subMenu2 = menu.addSubMenu(
                R.string.demo_menu, R.drawable.ic_launcher);
        subMenu2.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu2.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu2.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu2.add(R.string.demo_menu, R.drawable.ic_launcher);
        subMenu2.add(R.string.demo_menu, R.drawable.ic_launcher);

		menu.add(R.string.demo_menu, R.drawable.ic_launcher);
		menu.add(R.string.demo_menu, R.drawable.ic_launcher);
		menu.add(R.string.demo_menu2, R.drawable.ic_launcher);
        menu.add(R.string.demo_menu, R.drawable.ic_launcher);
        menu.add(R.string.demo_menu2, R.drawable.ic_launcher);
        menu.add(R.string.demo_menu, R.drawable.ic_launcher);
        menu.add(R.string.demo_menu2, R.drawable.ic_launcher);
        menu.add(R.string.demo_menu, R.drawable.ic_launcher);
		return true;
	}
	
	public boolean onPrepareNavigationMenu(CMenu item) {
		return false;
	}
	
	public void onNavigationMenuItemSelected(CMenuItem item) {
	}
	
	public boolean onCreateOptionsMenu(CMenu menu) {
		return false;
	}
	
	public boolean onPrepareOptionsMenu(CMenu menu) {

		return true;
	}
	
	public void onOptionsMenuItemSelected(CMenuItem item) {
	}

    public void onBackButtonClick() {
    }

    public CActionBar getSupportActionBar() {
        return mActionBarImpl;
    }

}
