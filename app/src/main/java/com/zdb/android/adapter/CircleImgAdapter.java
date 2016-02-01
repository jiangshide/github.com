package com.zdb.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;

public class CircleImgAdapter extends BaseAdapter {

	private Activity mActivity;
	private String[] mArr;

	public CircleImgAdapter(Activity a, String[] arr) {
		this.mActivity = a;
		this.mArr = arr;
	}

	@Override
	public int getCount() {
		return mArr != null ? mArr.length : 0;
	}

	@Override
	public Object getItem(int position) {
		return mArr != null ? mArr[position] : null;
	}

	@Override
	public long getItemId(int position) {
		return mArr != null ? position : 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder vh = null;
		if (v == null) {
			v = LayoutInflater.from(mActivity).inflate(
					R.layout.tab_friend_circle_item_img, null);
			vh = new ViewHolder();
			vh.circleItemImg = (ImageView) v.findViewById(R.id.circleItemImg);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		ImageLoader.getInstance()
				.displayImage(mArr[position], vh.circleItemImg);
		return v;
	}

	private static class ViewHolder {
		ImageView circleItemImg;
	}
}
