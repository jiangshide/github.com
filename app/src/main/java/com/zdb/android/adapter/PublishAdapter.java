package com.zdb.android.adapter;

import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdb.android.R;
import com.zdb.android.bean.ImageBucket;
import com.zdb.android.bean.ImageItem;

public class PublishAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<ImageBucket> mArr;

	public PublishAdapter(Activity a) {
		this.mActivity = a;
	}

	public void update(List<ImageBucket> arr) {
		if (mArr != null) {
			mArr.clear();
			mArr = null;
		}
		this.mArr = arr;
		this.notifyDataSetChanged();
	}

	public List<ImageItem> selected(int position) {
		int size = mArr.size();
		List<ImageItem> arr = null;
		for (int i = 0; i < size; i++) {
			if (i == position) {
				if (mArr.get(i).isSlected)
					mArr.get(i).isSlected = false;
				else {
					mArr.get(i).isSlected = true;
					arr = mArr.get(i).imageList;
				}
			} else {
				mArr.get(i).isSlected = false;
			}
		}
		this.notifyDataSetChanged();
		return arr;
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
			v = LayoutInflater.from(mActivity).inflate(
					R.layout.album_list_view, null);
			vh = new ViewHolder();
			vh.albumListImg = (ImageView) v.findViewById(R.id.albumListImg);
			vh.albumListLine = (View) v.findViewById(R.id.albumListLine);
			vh.albumListTxt = (TextView) v.findViewById(R.id.albumListTxt);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		String url = mArr.get(position).imageList.get(0).thumbnailPath;
		if (!TextUtils.isEmpty(url))
			vh.albumListImg.setImageURI(Uri.parse(url));
		vh.albumListTxt.setText(mArr.get(position).bucketName);
		if (mArr.get(position).isSlected) {
			vh.albumListLine.setBackgroundResource(R.drawable.pink);
		} else {
			vh.albumListLine.setBackgroundResource(R.drawable.transparent);
		}
		return v;
	}

	public static class ViewHolder {
		ImageView albumListImg;
		View albumListLine;
		TextView albumListTxt;
	}
}
