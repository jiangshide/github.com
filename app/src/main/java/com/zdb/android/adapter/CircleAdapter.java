package com.zdb.android.adapter;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.utils.LogUtils;
import com.android.utils.ToastUtils;
import com.android.utils.ValidateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.bean.Circle;
import com.zdb.android.view.CircleImageView;
import com.zdb.android.view.face.FaceRelativeLayout;

public class CircleAdapter extends BaseAdapter {

	private Activity mActivity;

	private List<Circle> mArr;

	private FaceRelativeLayout faceId;

	public CircleAdapter(Activity a, FaceRelativeLayout fi) {
		this.mActivity = a;
		this.faceId = fi;
	}

	public void refresh(List<Circle> arr) {
		if (ValidateUtils.isArr(mArr)) {
			mArr.clear();
			mArr = null;
		}
		this.mArr = arr;
		LogUtils.e("-------------mArr:" + mArr.size());
		this.notifyDataSetChanged();
	}

	public void loadMore(List<Circle> arr) {
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
					R.layout.tab_friend_circle_item, null);
			vh = new ViewHolder();
			vh.circleIcon = (CircleImageView) v.findViewById(R.id.circleIcon);
			vh.citcleName = (TextView) v.findViewById(R.id.citcleName);
			vh.citcleContent = (TextView) v.findViewById(R.id.citcleContent);
			vh.circleTime = (TextView) v.findViewById(R.id.circleTime);
			vh.circleFrom = (TextView) v.findViewById(R.id.circleFrom);
			vh.circleCommentTxt = (TextView) v
					.findViewById(R.id.circleCommentTxt);
			vh.circlePraiseTxt = (TextView) v
					.findViewById(R.id.circlePraiseTxt);
			vh.circleImg = (GridView) v.findViewById(R.id.circleImg);
			vh.circleComment = (LinearLayout) v
					.findViewById(R.id.circleComment);
			vh.circlePraise = (LinearLayout) v.findViewById(R.id.circlePraise);
			vh.circlePraiseList = (LinearLayout) v
					.findViewById(R.id.circlePraiseList);
			vh.circleCommentImg = (ImageView) v
					.findViewById(R.id.circleCommentImg);
			vh.circlePraiseImg = (ImageView) v
					.findViewById(R.id.circlePraiseImg);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		String head = mArr.get(position).head;
		ImageLoader.getInstance().displayImage(head, vh.circleIcon);
		String name = mArr.get(position).name;
		vh.citcleName.setText(!TextUtils.isEmpty(name) ? name : "");
		String content = mArr.get(position).msg;
		vh.citcleContent.setText(!TextUtils.isEmpty(content) ? content : "");
		String img = mArr.get(position).img;
		if (!TextUtils.isEmpty(img)) {
			String[] imgArr = img.split(",");
			vh.circleImg.setAdapter(mCircleImgAdapter = new CircleImgAdapter(
					mActivity, imgArr));
		}
		String time = mArr.get(position).createTime;
		vh.circleTime.setText(time);
		vh.circleComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				faceId.setVisibility(View.VISIBLE);
				ToastUtils.instance.toast(mActivity, "v:" + v);
			}
		});
		vh.circlePraise.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtils.instance.toast(mActivity, "v:" + v);
			}
		});
		String from = mArr.get(position).device;
		vh.circleFrom.setText(TextUtils.isEmpty(from) ? "" : from);
		return v;
	}

	private CircleImgAdapter mCircleImgAdapter;

	private static class ViewHolder {
		CircleImageView circleIcon;
		TextView citcleName, citcleContent, circleTime, circleFrom,
				circleCommentTxt, circlePraiseTxt;
		GridView circleImg;
		LinearLayout circleComment, circlePraise, circlePraiseList;
		ImageView circleCommentImg, circlePraiseImg;

	}
}
