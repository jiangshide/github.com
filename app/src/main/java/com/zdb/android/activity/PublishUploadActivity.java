package com.zdb.android.activity;

import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.android.utils.AnimUtils;
import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.LogUtils;
import com.android.utils.SharedUtils;
import com.android.utils.ToastUtils;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.adapter.UploadImgAdapter;
import com.zdb.android.adapter.UploadImgAdapter.ImgProgressListener;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.Circle;
import com.zdb.android.bean.FriendBean;
import com.zdb.android.bean.ImageItem;
import com.zdb.android.bean.LocationBean;
import com.zdb.android.utils.Constant;
import com.zdb.android.utils.DialogUtils;
import com.zdb.android.utils.LocationUtils;

public class PublishUploadActivity extends BaseActivity implements
		OnItemClickListener, ImgProgressListener {

	@ViewInject(R.id.uploadTxt)
	private EditText uploadTxt;
	@ViewInject(R.id.uploadImgList)
	private GridView uploadImgList;

	private List<ImageItem> itemArr;
	private String msg;

	private UploadImgAdapter mUploadImgAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_upload);
		ViewUtils.inject(this);
		itemArr = (List<ImageItem>) getIntent().getExtras().getSerializable(
				"item");
		initData();
	}

	public void back(View v) {
		finish();
	}

	public void publish(View v) {
		msg = uploadTxt.getEditableText().toString().trim();
		if (TextUtils.isEmpty(msg)) {
			AnimUtils.anim(this, uploadTxt, R.anim.shake_x,
					getString(R.string.sayHint),
					getResources().getColor(R.color.red));
			return;
		}
		mUploadImgAdapter.uploadImg();
	}

	private void initData() {
		int size = itemArr.size();
		if (size < 9) {
			ImageItem item = new ImageItem();
			item.isSelected = false;
			item.imagePath = null;
			item.thumbnailPath = null;
			itemArr.add(item);
		}
		uploadImgList.setAdapter(mUploadImgAdapter = new UploadImgAdapter(this,
				itemArr, this));
		uploadImgList.setOnItemClickListener(this);
	}

	private void request(String imgUrl) {
		Circle c = new Circle();
		c.circleId = SharedUtils.instance.getString(this, Constant.USER_ID);
		c.img = imgUrl;
		c.msg = msg;
		c.device = android.os.Build.MODEL;
		LocationBean lb = LocationUtils.instance.getLocation();
		c.latitude = lb.longitude;
		c.longitude = lb.longitude;
		netProgress(HTTP_MOTHED.POST, getMap(c), FriendBean.class,
				Constant.ADD_CIRCLE, true);
	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		if (obj instanceof FriendBean) {
			FriendBean fb = (FriendBean) obj;
			if (fb.result == 0) {
				// finish();
				ToastUtils.instance.toast(this, "发布成功!");
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ToastUtils.instance.toast(this, "position:" + position);
		ImageItem item = mUploadImgAdapter.getImgItem(position);
		String path = item.imagePath;
		if (!TextUtils.isEmpty(path)) {
			DialogUtils.instance.showImg(this, itemArr, position);
		} else {

		}
	}

	@Override
	public void result(String imgUrl) {
		LogUtils.e("-----------imgUrl:" + imgUrl);
		request(imgUrl);
	}
}
