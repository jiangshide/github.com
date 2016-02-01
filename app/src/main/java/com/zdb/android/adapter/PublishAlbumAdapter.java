package com.zdb.android.adapter;

import java.util.ArrayList;
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
import com.zdb.android.bean.ImageItem;

public class PublishAlbumAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<ImageItem> mArr;
	// private HashMap<String, ImageItem> map;
	private List<ImageItem> selectedArr;

	public PublishAlbumAdapter(Activity a) {
		this.mActivity = a;
	}

	public void update(List<ImageItem> arr) {
		if (mArr != null) {
			mArr.clear();
			mArr = null;
		}
		if (selectedArr != null) {
			selectedArr.clear();
			selectedArr = null;
		}
		this.mArr = arr;
		selectedArr = new ArrayList<ImageItem>();
		this.notifyDataSetChanged();
	}

	public List<ImageItem> selected(int position) {
		int size = selectedArr.size();
		if (size < 9) {
			if (mArr.get(position).isSelected) {
				mArr.get(position).isSelected = false;
				selectedArr.remove(mArr.get(position));
			} else {
				mArr.get(position).isSelected = true;
				selectedArr.add(mArr.get(position));
			}
			this.notifyDataSetChanged();
		}
		return selectedArr;
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
			v = LayoutInflater.from(mActivity).inflate(R.layout.publish_album,
					null);
			vh = new ViewHolder();
			vh.albumRadio = (ImageView) v.findViewById(R.id.albumRadio);
			vh.albumImg = (ImageView) v.findViewById(R.id.albumImg);
			vh.albumTxt = (TextView) v.findViewById(R.id.albumTxt);
			vh.albumTxt.setVisibility(View.GONE);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		String url = mArr.get(position).thumbnailPath;
		if (!TextUtils.isEmpty(url))
			vh.albumImg.setImageURI(Uri.parse(url));
		if (mArr.get(position).isSelected) {
			vh.albumRadio.setImageResource(R.drawable.single_selected);
		} else {
			vh.albumRadio.setImageResource(R.drawable.single_selecte);
		}
		return v;
	}

	public static class ViewHolder {
		ImageView albumImg, albumRadio;
		TextView albumTxt;
	}
}
