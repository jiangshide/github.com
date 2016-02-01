package com.zdb.android.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.utils.ValidateUtils;
import com.zdb.android.R;
import com.zdb.android.bean.TopicBean.Topic;

public class TopicAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<Topic> mArr;

	public TopicAdapter(Activity a) {
		this.mActivity = a;
	}

	public void refresh(List<Topic> arr) {
		if (ValidateUtils.isArr(mArr)) {
			mArr.clear();
			mArr = null;
		}
		this.mArr = arr;
		this.notifyDataSetChanged();
	}

	public void loadMore(List<Topic> arr) {
		if (ValidateUtils.isArr(arr) && ValidateUtils.isArr(mArr)) {
			mArr.addAll(arr);
		} else {
			this.mArr = arr;
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mArr != null ? mArr.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mArr != null ? mArr.get(position) : null;
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
					R.layout.tab_friend_data_topic_item, null);
			vh = new ViewHolder();
		}
		return v;
	}

	private static class ViewHolder {

	}
}
