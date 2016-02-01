package com.zdb.android.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.utils.ToastUtils;
import com.android.utils.ValidateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.bean.HomeBean.HomeList;
import com.zdb.android.fragment.home.DetailAcitivity;
import com.zdb.android.utils.CusView;
import com.zdb.android.view.CircleImageView;
import com.zdb.android.view.PopUtils;
import com.zdb.android.view.WordWrapView;

public class HomeAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<HomeList> mArr;
	private String[] labelArr;

	public HomeAdapter(Activity a) {
		this.mActivity = a;
	}

	public void refresh(List<HomeList> arr) {
		if (ValidateUtils.isArr(mArr)) {
			mArr.clear();
			mArr = null;
		}
		this.mArr = arr;
		this.notifyDataSetChanged();
	}

	public void loadMore(List<HomeList> arr) {
		if (ValidateUtils.isArr(arr) && ValidateUtils.isArr(mArr)) {
			mArr.addAll(arr);
		} else {
			this.mArr = arr;
		}
		this.notifyDataSetChanged();
	}

	public HomeList getHomeItem(int position) {
		return mArr.get(position);
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
	public View getView(final int position, View v, ViewGroup parent) {
		ViewHolder vh = null;
		if (v == null) {
			v = LayoutInflater.from(mActivity).inflate(R.layout.tab_home_item,
					null);
			vh = new ViewHolder();
			vh.homeItemIcon = (CircleImageView) v
					.findViewById(R.id.homeItemIcon);
			vh.homeItemName = (TextView) v.findViewById(R.id.homeItemName);
			vh.homeItemSign = (TextView) v.findViewById(R.id.homeItemSign);
			vh.homeItemFollow = (ImageView) v.findViewById(R.id.homeItemFollow);
			vh.homeItemImg = (ImageView) v.findViewById(R.id.homeItemImg);
			vh.homeItemLabel = (LinearLayout) v
					.findViewById(R.id.homeItemLabel);
			vh.homeItemViewNum = (LinearLayout) v
					.findViewById(R.id.homeItemViewNum);
			vh.homeItemViewImg = (ImageView) v
					.findViewById(R.id.homeItemViewImg);
			vh.homeItemViewTxt = (TextView) v
					.findViewById(R.id.homeItemViewTxt);
			vh.homeItemMsgNum = (LinearLayout) v
					.findViewById(R.id.homeItemMsgNum);
			vh.homeItemMsgImg = (ImageView) v.findViewById(R.id.homeItemMsgImg);
			vh.homeItemMsgTxt = (TextView) v.findViewById(R.id.homeItemMsgTxt);
			vh.homeItemLike = (LinearLayout) v.findViewById(R.id.homeItemLike);
			vh.homeItemLikeImg = (ImageView) v
					.findViewById(R.id.homeItemLikeImg);
			vh.homeItemLikeTxt = (TextView) v
					.findViewById(R.id.homeItemLikeTxt);
			vh.homeItemIcon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastUtils.instance.toast(mActivity, "ICON");
				}
			});
			vh.homeItemImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle b = new Bundle();
					b.putSerializable("homeItem", getHomeItem(position));
					mActivity.startActivity(new Intent(mActivity,
							DetailAcitivity.class).putExtras(b));
				}
			});
			vh.homeItemFollow.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastUtils.instance.toast(mActivity, "follow");
				}
			});
			vh.homeItemViewNum.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastUtils.instance.toast(mActivity, "view");
				}
			});
			vh.homeItemMsgNum.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ToastUtils.instance.toast(mActivity, "msg");
				}
			});
			vh.homeItemLike.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastUtils.instance.toast(mActivity, "like");
				}
			});
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		ImageLoader.getInstance().displayImage(mArr.get(position).head,
				vh.homeItemIcon);
		vh.homeItemName.setText(mArr.get(position).title);
		vh.homeItemSign.setText(mArr.get(position).sign);
		ImageLoader.getInstance().displayImage(mArr.get(position).img,
				vh.homeItemImg);
		vh.homeItemLabel.removeAllViews();
		String label = mArr.get(position).label;
		if (TextUtils.isEmpty(label) && label.contains(",")) {
			labelArr = label.split(",");
			for (int i = 0; i < 4; i++)
				label(labelArr[i], vh.homeItemLabel);
		} else {
			label(label, vh.homeItemLabel);
		}

		vh.homeItemViewTxt.setText(mArr.get(position).viewNum + "");
		vh.homeItemMsgTxt.setText(mArr.get(position).msgNum + "");
		vh.homeItemLikeTxt.setText(mArr.get(position).likeNum + "");
		return v;
	}

	private void label(String label, View v) {
		CusView.addBtn(mActivity, label, v,
				mActivity.getResources().getColor(R.color.white))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						View popV = PopUtils.instance.showPop(mActivity,
								R.layout.pop_sign, v);
						WordWrapView popSign = (WordWrapView) popV
								.findViewById(R.id.popSign);
						int length = labelArr.length;
						for (int j = 0; j < length; j++)
							CusView.addBtn(
									mActivity,
									labelArr[j],
									popSign,
									mActivity.getResources().getColor(
											R.color.white));
					}

				});
	}

	private static class ViewHolder {
		CircleImageView homeItemIcon;
		TextView homeItemName;
		TextView homeItemSign;
		ImageView homeItemFollow;
		ImageView homeItemImg;
		LinearLayout homeItemLike, homeItemLabel, homeItemViewNum;
		ImageView homeItemViewImg;
		TextView homeItemViewTxt;
		LinearLayout homeItemMsgNum;
		ImageView homeItemMsgImg;
		TextView homeItemMsgTxt;
		ImageView homeItemLikeImg;
		TextView homeItemLikeTxt;
	}

	private void showInfo() {

	}

	private void addFollow() {

	}

	private void addView() {

	}

	private void viewMsg() {

	}

	private void addLike() {

	}
}
