package com.zdb.android.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.ValidateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.bean.ShopBean.Prefecture;

public class ShopAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<Prefecture> mArr;

	public ShopAdapter(Activity a) {
		this.mActivity = a;
	}

	public void refresh(List<Prefecture> arr) {
		if (ValidateUtils.isArr(mArr)) {
			mArr.clear();
			mArr = null;
		}
		this.mArr = arr;
		this.notifyDataSetChanged();
	}

	public void loadMore(List<Prefecture> arr) {
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
			v = LayoutInflater.from(mActivity).inflate(R.layout.tab_shop_item,
					null);
			vh = new ViewHolder();
			vh.shopItemImg = (ImageView) v.findViewById(R.id.shopItemImg);
			vh.shopItemStar = (ImageView) v.findViewWithTag(R.id.shopItemStar);
			vh.shopItemPost = (ImageView) v.findViewById(R.id.shopItemPost);
			vh.shopItemTitle = (TextView) v.findViewById(R.id.shopItemTitle);
			vh.shopItemPerson = (TextView) v.findViewById(R.id.shopItemPerson);
			vh.shopItemPrice = (TextView) v.findViewById(R.id.shopItemPrice);
			vh.shopItemMaketPrice = (TextView) v
					.findViewById(R.id.shopItemMaketPrice);
			vh.shopItemGo = (TextView) v.findViewById(R.id.shopItemGo);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		ImageLoader.getInstance().displayImage(mArr.get(position).img,
				vh.shopItemImg);
		vh.shopItemTitle.setText(mArr.get(position).title);
		vh.shopItemPerson.setText(mArr.get(position).shopNums + "人已购买");
		vh.shopItemPrice.setText("¥" + mArr.get(position).price);
		vh.shopItemMaketPrice.setText("¥" + mArr.get(position).maketPrice);
		vh.shopItemMaketPrice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		if (mArr.get(position).post) {
			vh.shopItemPost.setImageResource(R.drawable.freeshop);
		} else {
			vh.shopItemPost.setImageResource(R.drawable.transparent);
		}
		return v;
	}

	private static class ViewHolder {
		ImageView shopItemImg, shopItemStar, shopItemPost;
		TextView shopItemTitle, shopItemPerson, shopItemPrice,
				shopItemMaketPrice, shopItemGo;
	}
}
