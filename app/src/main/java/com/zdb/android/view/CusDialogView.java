package com.zdb.android.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zdb.android.R;

public class CusDialogView extends Dialog {

	private View mView;
	private int mAnim;
	private int mPos = Gravity.CENTER;

	public CusDialogView(Context c, int layout) {
		super(c, R.style.defaultTheme);
		initView(c, layout);
	}

	public CusDialogView(Context c, int theme, int layout) {
		super(c, theme);
		initView(c, layout);
	}

	public CusDialogView(Context c, int theme, int layout, int anim) {
		super(c, theme);
		initView(c, layout);
		this.mAnim = anim;
	}

	private void initView(Context c, int layout) {
		mAnim = R.style.defaultAnim;
		mView = LayoutInflater.from(c).inflate(layout, null);
	}

	public void setPos(int pos) {
		mPos = pos;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(mView);
		Window window = this.getWindow();
		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.gravity = mPos;
		window.setWindowAnimations(mAnim);
		window.setAttributes(params);
	}

	public View getView() {
		return mView;
	}
}
