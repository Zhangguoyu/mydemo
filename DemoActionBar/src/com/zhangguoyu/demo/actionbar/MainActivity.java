package com.zhangguoyu.demo.actionbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private CActionBarImpl mActionBarImpl = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		
	}
	
	public CActionBar getSupportActionBar() {
		return mActionBarImpl;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
