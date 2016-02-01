package com.zdb.android.activity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.DateUtils;
import com.android.utils.MD5;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.MyApplication;
import com.zdb.android.R;
import com.zdb.android.adapter.PublishAdapter;
import com.zdb.android.adapter.PublishAlbumAdapter;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.ImageBucket;
import com.zdb.android.bean.ImageItem;
import com.zdb.android.utils.AlbumUtils;
import com.zdb.android.utils.Constant;

public class PublishActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	@ViewInject(R.id.back)
	public ImageView back;
	@ViewInject(R.id.submit)
	public Button submit;

	@ViewInject(R.id.albumImgList)
	private GridView albumImgList;
	@ViewInject(R.id.albumImgListItem)
	private GridView albumImgListItem;
	@ViewInject(R.id.albumImgSelected)
	private TextView albumImgSelected;

	private PublishAdapter mPublishAdapter;
	private PublishAlbumAdapter mPublishAlbumAdapter;

	private List<ImageItem> selectedArr;

	private int publishType;

	private Uri imageUri; // 图片路径
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int PIC = 3;
	private File outputImage;
	private String url;
	private String fromUrl;
	private int photoW = 200, photoH = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		publishType = getIntent().getIntExtra(Constant.PUBLISH_TYPE, 0);
		switch (publishType) {
		case 0:

			break;
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		}
		setContentView(R.layout.activity_publish);
		ViewUtils.inject(this);
		initTop();
	}

	public void camera(View v) {

	}

	public void initTop() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
		List<ImageBucket> tmpList = ((MyApplication) getApplication()).mAlbum;
		ImageBucket ib = new ImageBucket();
		ib.imageList = AlbumUtils.instance.allImg;
		ib.bucketName = "所有图片";
		ib.isSlected = true;
		tmpList.set(0, ib);
		initData(tmpList);
	}

	private void initData(List<ImageBucket> tmpList) {
		albumImgList.setOnItemClickListener(this);
		albumImgList.setAdapter(mPublishAdapter = new PublishAdapter(this));
		mPublishAdapter.update(tmpList);
		initItemData(tmpList.get(0).imageList);
	}

	private void initItemData(List<ImageItem> itemList) {
		albumImgListItem.setOnItemClickListener(this);
		albumImgListItem
				.setAdapter(mPublishAlbumAdapter = new PublishAlbumAdapter(this));
		mPublishAlbumAdapter.update(itemList);
	}

	File path;

	private void take(boolean isPic) {
		path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		outputImage = new File(path, MD5.encode(DateUtils.getDate()) + ".jpg");
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
			outputImage.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageUri = Uri.fromFile(outputImage);
		Intent i = new Intent();
		if (isPic) {
			i.setAction(Intent.ACTION_GET_CONTENT);
			i.setType("image/*");
			// i.putExtra("crop", "true");
			i.putExtra("output", imageUri);
			inputIntent(i);
			startActivityForResult(i, PIC);
		} else {
			i.setAction("android.media.action.IMAGE_CAPTURE");
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(i, TAKE_PHOTO);
		}
	}

	private void inputIntent(Intent i) {
		i.putExtra("outputX", photoW);
		i.putExtra("outputY", photoH);
		i.putExtra("outputFormat", "JPEG");
		i.putExtra("aspectX", 1);
		i.putExtra("aspectY", 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case TAKE_PHOTO:
			Intent i = new Intent("com.android.camera.action.CROP");
			i.setDataAndType(imageUri, "image/*");
			i.putExtra("scale", true);
			inputIntent(i);
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			intentBc.setData(imageUri);
			this.sendBroadcast(intentBc);
			startActivityForResult(i, CROP_PHOTO);
			break;
		case PIC:
			// upImg.setImageURI(imageUri);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.albumImgList:
			List<ImageItem> arr = mPublishAdapter.selected(position);
			initItemData(arr);
			break;
		case R.id.albumImgListItem:
			selectedArr = mPublishAlbumAdapter.selected(position);
			int size = selectedArr.size();
			albumImgSelected.setText("共选择: " + size);
			submit.setText("完成(" + size + ")");
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.submit:
			Bundle bundle = new Bundle();
			bundle.putSerializable("item", (Serializable) selectedArr);
			toPage(PublishUploadActivity.class, bundle, false);
			break;
		}
	}
}
