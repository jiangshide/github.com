package com.zdb.android.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import ch.halcyon.squareprogressbar.SquareProgressBar;

import com.android.utils.LogUtils;
import com.android.utils.SharedUtils;
import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.tools.HttpTools;
import com.google.gson.Gson;
import com.zdb.android.R;
import com.zdb.android.bean.FileInfo;
import com.zdb.android.bean.ImageItem;

public class UploadImgAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<ImageItem> mArr;
	private ImgProgressListener mImgProgressListener;
	private String uploadUrl;

	public UploadImgAdapter(Activity a, List<ImageItem> arr,
			ImgProgressListener ipl) {
		this.mActivity = a;
		this.mArr = arr;
		this.mImgProgressListener = ipl;
	}

	public ImageItem getImgItem(int position) {
		return mArr.get(position);
	}

	public void uploadImg() {
		mArr.get(0).isUploaded = true;
		notifyDataSetChanged();
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
					R.layout.album_upload_view, null);
			vh = new ViewHolder();
			vh.albumUploadImg = (SquareProgressBar) v
					.findViewById(R.id.albumUploadImg);
			vh.albumUploadImg.setColor("#FF0000");
			vh.albumUploadImg.setProgress(10);
			vh.albumUploadImg.setWidth(2);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		String url = mArr.get(position).thumbnailPath;
		if (!TextUtils.isEmpty(url)) {
			Bitmap bmp = BitmapFactory.decodeFile(url);
			vh.albumUploadImg.setImageBitmap(bmp);
		} else {
			vh.albumUploadImg.setImage(R.drawable.add);
		}
		String path = mArr.get(position).imagePath;
		if (mArr.get(position).isUploaded && path != null) {
			upload(vh.albumUploadImg, path, position);
		}
		return v;
	}

	private static class ViewHolder {
		SquareProgressBar albumUploadImg;
	}

	private void upload(final SquareProgressBar spb, String path,
			final int position) {
		HttpTools ht = new HttpTools(mActivity);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", new File(path));
		ht.upload(SharedUtils.instance.getString(mActivity, "http_file"),
				params, new HttpCallback() {
					@Override
					public void onStart() {
						LogUtils.e("-----------onStart()");
						spb.setColor("#998877");
						spb.setProgress(0);
						spb.setWidth(2);
						notifyDataSetChanged();
					}

					@SuppressWarnings("unused")
					@Override
					public void onResult(String arg0) {
						LogUtils.e("-------------arg0:" + arg0);
						FileInfo fi = new Gson().fromJson(arg0, FileInfo.class);
						String url = fi.url;
						if (!TextUtils.isEmpty(uploadUrl)) {
							if (!TextUtils.isEmpty(url)) {
								uploadUrl = new StringBuffer()
										.append(uploadUrl).append(",")
										.append(url).toString();
							}
						} else {
							if (!TextUtils.isEmpty(url)) {
								uploadUrl = new StringBuffer().append(url)
										.toString();
							}
						}
						mArr.get(position).imagePath = null;
						mArr.get(position).isUploaded = false;
						boolean isRequest = true;
						for (ImageItem item : mArr) {
							if (item.isUploaded = false && TextUtils
									.isEmpty(item.imagePath)) {
								item.isUploaded = true;
								isRequest = false;
								break;
							}
						}
						if (isRequest) {
							mImgProgressListener.result(uploadUrl);
						}
						notifyDataSetChanged();
					}

					@Override
					public void onLoading(long arg0, long arg1) {
						int curr = (int) ((Double.parseDouble(arg1 + "") / Double
								.parseDouble(arg0 + "")) * 100);
						LogUtils.e("-----------arg0:" + arg0 + " | arg1:"
								+ arg1 + " | curr:" + curr);
						spb.setColor("#FF0000");
						spb.setProgress(curr);
						spb.setWidth(2);
						notifyDataSetChanged();
					}

					@Override
					public void onFinish() {
						LogUtils.e("------------onFinish()");
					}

					@Override
					public void onError(Exception arg0) {
						LogUtils.e("-------------------arg0:" + arg0);
					}

					@Override
					public void onCancelled() {
						LogUtils.e("---------------onCancelled");
					}
				});
	}

	public interface ImgProgressListener {
		public void result(String imgUrl);
	}
}
