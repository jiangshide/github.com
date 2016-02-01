package com.zdb.android.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zdb.android.R;
import com.zdb.android.bean.ShopCartBean.ShopCart;

public class ShopCartAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<ShopCart> mArr;

	public ShopCartAdapter(Activity a) {
		this.mActivity = a;
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
			v = LayoutInflater.from(mActivity).inflate(R.layout.shop_cart_item,
					null);
			vh = new ViewHolder();

			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		return v;
	}

	private static class ViewHolder {

	}
}
