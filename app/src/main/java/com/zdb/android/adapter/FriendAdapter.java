package com.zdb.android.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.utils.DateUtils;
import com.android.utils.LogUtils;
import com.android.utils.ValidateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.activity.FriendDataActivity;
import com.zdb.android.bean.Friend;
import com.zdb.android.view.CircleImageView;

public class FriendAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<Friend> mArr;

	public FriendAdapter(Activity a) {
		this.mActivity = a;
	}

	public void refresh(List<Friend> arr) {
		if (ValidateUtils.isArr(mArr)) {
			mArr.clear();
			mArr = null;
		}
		this.mArr = arr;
		LogUtils.e("-----------mArr:"+mArr.size());
		this.notifyDataSetChanged();
	}

	public void loadMore(List<Friend> arr) {
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
					R.layout.tab_friend_item, null);
			vh = new ViewHolder();
			vh.tflHead = (CircleImageView) v.findViewById(R.id.tflHead);
			vh.tflName = (TextView) v.findViewById(R.id.tflName);
			vh.tflSign = (TextView) v.findViewById(R.id.tflSign);
			vh.tflFollow = (TextView) v.findViewById(R.id.tflFollow);
			vh.tflMsgNum = (TextView) v.findViewById(R.id.tflMsgNum);
			vh.tflTime = (TextView) v.findViewById(R.id.tflTime);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		String url = mArr.get(position).head;
		LogUtils.e("----------url:" + url);
		ImageLoader.getInstance().displayImage(url, vh.tflHead);
		vh.tflName.setText(mArr.get(position).name + " ("
				+ mArr.get(position).type + ") ");
		vh.tflSign.setText(mArr.get(position).sign);
		vh.tflMsgNum.setText(mArr.get(position).msgNum + "");
		vh.tflTime.setText(DateUtils.getCusFormat(
				mArr.get(position).updateTime, "yyyy-MM-dd"));
		vh.tflHead.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.startActivity(new Intent(mActivity,
						FriendDataActivity.class));
			}
		});
		return v;
	}

	private static class ViewHolder {
		CircleImageView tflHead;
		TextView tflName, tflSign, tflFollow, tflMsgNum, tflTime;
	}
}
