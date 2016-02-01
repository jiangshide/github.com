package com.zdb.android.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class CusPageAdapter extends PagerAdapter {

	private List<?> mView;

	public CusPageAdapter(List<?> v) {
		this.mView = v;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) mView.get(position));// 删除页卡
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
		container.addView((View) mView.get(position), 0);// 添加页卡
		return mView.get(position);
	}

	@Override
	public int getCount() {
		return mView.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
