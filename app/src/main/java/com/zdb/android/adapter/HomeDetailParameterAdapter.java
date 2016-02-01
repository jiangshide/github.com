package com.zdb.android.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zdb.android.R;
import com.zdb.android.bean.HomeDetailParameterBean;

public class HomeDetailParameterAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<HomeDetailParameterBean> mArr;

	public HomeDetailParameterAdapter(Activity a,
			List<HomeDetailParameterBean> arr) {
		this.mActivity = a;
		this.mArr = arr;
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
					R.layout.tab_home_detail_parameter_item, null);
			vh = new ViewHolder();
			vh.homeDetailParamterName = (TextView) v
					.findViewById(R.id.homeDetailParamterName);
			vh.homeDetailParamterDes = (TextView) v
					.findViewById(R.id.homeDetailParamterDes);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		vh.homeDetailParamterName.setText(mArr.get(position).name);
		vh.homeDetailParamterDes.setText(mArr.get(position).des);
		return v;
	}

	private static class ViewHolder {
		TextView homeDetailParamterName, homeDetailParamterDes;
	}
}
