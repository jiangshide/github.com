package com.zdb.android.activity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.android.utils.DateUtils;
import com.android.utils.LogUtils;
import com.android.utils.MD5;
import com.android.utils.SharedUtils;
import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.tools.HttpTools;
import com.google.gson.Gson;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.FileInfo;
import com.zdb.android.view.CusDialogView;

public class UploadActivity extends BaseActivity implements OnClickListener,
		OnSeekBarChangeListener {

	@ViewInject(R.id.left)
	private ImageView back;
	@ViewInject(R.id.title)
	private TextView title;

	@ViewInject(R.id.upImg)
	private ImageView upImg;

	private Uri imageUri; // 图片路径
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int PIC = 3;
	private File outputImage;
	private String url;
	private String fromUrl;
	private int photoW = 200, photoH = 200;

	private CusDialogView mCusDialogView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		ViewUtils.inject(this);
		initTop();
		Intent i = getIntent();
		url = i.getStringExtra("url");
		fromUrl = i.getStringExtra("fromUrl");
		if (!TextUtils.isEmpty(fromUrl)) {
			ImageLoader.getInstance().displayImage(fromUrl, upImg);
		}
	}

	private void initTop() {
		back.setImageResource(R.drawable.btn_back);
		title.setText("秀一秀");
		back.setOnClickListener(this);
	}

	private TextView picWValue, picHValue;

	public void upImg(View v) {
		cancelDialog();
		mCusDialogView = new CusDialogView(this,
				R.layout.activity_upload_dialog);
		mCusDialogView.setPos(Gravity.BOTTOM);
		View view = mCusDialogView.getView();
		picWValue = (TextView) view.findViewById(R.id.picWValue);
		picHValue = (TextView) view.findViewById(R.id.picHValue);
		SeekBar picW = (SeekBar) view.findViewById(R.id.picW);
		SeekBar picH = (SeekBar) view.findViewById(R.id.picH);
		((Button) view.findViewById(R.id.take)).setOnClickListener(this);
		((Button) view.findViewById(R.id.pic)).setOnClickListener(this);
		picW.setOnSeekBarChangeListener(this);
		picH.setOnSeekBarChangeListener(this);
		picW.setProgress(photoW);
		picH.setProgress(photoH);
		picWValue.setText(photoW + "");
		picHValue.setText(photoH + "");
		mCusDialogView.show();
	}

	private void cancelDialog() {
		if (mCusDialogView != null) {
			mCusDialogView.cancel();
			mCusDialogView = null;
		}
	}

	public void upload(View v) {
		uploadImg();
	}

	File path;

	private void take(boolean isPic) {
		cancelDialog();
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
			i.putExtra("crop", "true");
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
		case CROP_PHOTO:
			upImg.setImageURI(imageUri);
		case PIC:
			upImg.setImageURI(imageUri);
			break;
		}
	}

	private void uploadImg() {
		HttpTools ht = new HttpTools(this);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", outputImage);
		ht.upload(SharedUtils.instance.getString(this, "http_file"), params,
				new HttpCallback() {
					@Override
					public void onStart() {
						LogUtils.e("-----------onStart()");
					}

					@Override
					public void onResult(String arg0) {
						LogUtils.e("-------------arg0:" + arg0);
						FileInfo fi = new Gson().fromJson(arg0, FileInfo.class);
						SharedUtils.instance.putString(getBaseContext(), url,
								fi.url);
						finish();
					}

					@Override
					public void onLoading(long arg0, long arg1) {
						LogUtils.e("-----------arg0:" + arg0 + " | arg1:"
								+ arg1);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		case R.id.take:
			take(false);
			break;
		case R.id.pic:
			take(true);
			break;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		LogUtils.e("--------------progress:" + progress);
		switch (seekBar.getId()) {
		case R.id.picW:
			photoW = progress;
			picWValue.setText(photoW + "");
			break;
		case R.id.picH:
			photoH = progress;
			picHValue.setText(photoH + "");
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}
}
