package com.zdb.android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.SharedUtils;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.base.BaseActivity;

public class MoreSetActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.left)
	private ImageView left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.right)
	private ImageView right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_me_moreset);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		left.setVisibility(View.VISIBLE);
		left.setImageResource(R.drawable.btn_back);
		title.setText("设置");
		right.setVisibility(View.GONE);
		left.setOnClickListener(this);
	}

	public void exitApp(View v) {
		SharedUtils su = SharedUtils.instance;
		su.clear(this);
		this.finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		}
	}
}
