package com.zdb.android.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.utils.LogUtils;
import com.android.utils.SharedUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.activity.PublishActivity;
import com.zdb.android.activity.VoiceActivity;
import com.zdb.android.bean.Friend;
import com.zdb.android.bean.ImageItem;
import com.zdb.android.view.CusDialogView;
import com.zdb.android.view.DragImageView;
import com.zdb.android.view.FlowLayout;
import com.zdb.android.view.pinyin.ClearEditText;

public enum DialogUtils implements OnClickListener, OnCancelListener {

	instance;
	private CusDialogView cdv;
	private Activity mActivity;

	public void exit(Activity a) {
		this.mActivity = a;
		cancel();
		cdv = new CusDialogView(a, R.layout.exit);
		View exit = cdv.getView();
		ImageView exitPoster = (ImageView) exit.findViewById(R.id.exitPoster);
		Button exitSure = (Button) exit.findViewById(R.id.exitSure);
		Button exitCancel = (Button) exit.findViewById(R.id.exitCancel);
		String posterD = SharedUtils.instance.getString(mActivity,
				Constant.POSTER_DIALOG);
		if (!TextUtils.isEmpty(posterD)) {
			ImageLoader.getInstance().displayImage(posterD, exitPoster);
		}
		exitPoster.setOnClickListener(this);
		exitSure.setOnClickListener(this);
		exitCancel.setOnClickListener(this);
		cdv.show();
	}

	private int window_width;
	private int window_height;
	private int state_height;

	public void showImg(Activity a, List<ImageItem> itemArr, int position) {
		this.mActivity = a;
		cancel();
		cdv = new CusDialogView(a, R.layout.show_img);
		View showImg = cdv.getView();
		LinearLayout showImgList = (LinearLayout) showImg
				.findViewById(R.id.showImgList);
		showImgList.removeAllViews();
		DisplayMetrics dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		window_width = dm.widthPixels;
		window_height = dm.heightPixels;
		ViewTreeObserver vto;
		int size = itemArr.size();
		for (int i = 0; i < size; i++) {
			ImageItem item = itemArr.get(i);
			String urlStr = item.imagePath;
			LogUtils.e("---------urlStr:" + urlStr + " | window_width:"
					+ window_width + " | window_height:" + window_height);
			if (!TextUtils.isEmpty(urlStr)) {
				View v = LayoutInflater.from(a).inflate(R.layout.show_img_item,
						null);
				final DragImageView showImgListItem = (DragImageView) v
						.findViewById(R.id.showImgListItem);
				try {
					Bitmap bitmap = GetLocalOrNetBitmap(urlStr);
					LogUtils.e("-----------bitmap:" + bitmap);
					bitmap = getBitmap(bitmap, window_width, window_height);
					LogUtils.e("------------size:" + bitmap.getByteCount());
					showImgListItem.setImageBitmap(bitmap);
					showImgListItem.setmActivity(mActivity);
					vto = showImgListItem.getViewTreeObserver();
					vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

						@Override
						public void onGlobalLayout() {
							if (state_height == 0) {
								Rect frame = new Rect();
								mActivity.getWindow().getDecorView()
										.getWindowVisibleDisplayFrame(frame);
								state_height = frame.top;
								showImgListItem.setScreen_H(window_height
										- state_height);
								showImgListItem.setScreen_W(window_width);
							}
						}
					});
				} catch (Exception e) {
					LogUtils.e("-----------e:" + e.getMessage());
					e.printStackTrace();
				}
				showImgList.addView(v);
			}
		}
		cdv.show();
	}

	public Bitmap GetLocalOrNetBitmap(String path) {
		Bitmap bitmap = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(path);
			} else {
				LogUtils.e("----------file not!");
			}
		} catch (Exception e) {
			LogUtils.e("---1-------e:" + e.getMessage());
		}
		return bitmap;
	}

	private void copy(InputStream in, OutputStream out) throws IOException {
		byte[] b = new byte[Constant.IO_BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

	public Bitmap getBitmap(Bitmap bitmap, int screenWidth, int screenHight) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scale = (float) screenWidth / w;
		float scale2 = (float) screenHight / h;

		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}

	public void publish(Activity a) {
		this.mActivity = a;
		cancel();
		cdv = new CusDialogView(a, R.style.defaultTheme1, R.layout.publish);
		View publish = cdv.getView();
		Button publishImg = (Button) publish.findViewById(R.id.publishImg);
		Button publishAudio = (Button) publish.findViewById(R.id.publishAudio);
		Button publishVideo = (Button) publish.findViewById(R.id.publishVideo);
		publishImg.setOnClickListener(this);
		publishAudio.setOnClickListener(this);
		publishVideo.setOnClickListener(this);
		cdv.show();
	}

	private FlowLayout tagWords;

	public void addUser(Activity a, List<Friend> arr) {
		cancel();
		cdv = new CusDialogView(a, R.layout.tab_friend_user);
		cdv.setOnCancelListener(this);
		cdv.setPos(Gravity.TOP);
		View user = cdv.getView();
		ClearEditText searchName = (ClearEditText) user
				.findViewById(R.id.searchName);
		Button searchCancel = (Button) user.findViewById(R.id.searchCancel);
		searchCancel.setOnClickListener(this);
		tagWords = (FlowLayout) user.findViewById(R.id.tagWords);
		List<String> textList;
		List<String> leftextList;
		List<String> righttextList;
		textList = new ArrayList<String>();
		leftextList = new ArrayList<String>();
		righttextList = new ArrayList<String>();
		addTexts(textList, leftextList, righttextList);
		for (int i = 0; i < textList.size(); i++) {
			tagWords.addText(textList.get(i));
		}
		for (int i = 0; i < leftextList.size(); i++) {
			tagWords.addLeftText(leftextList.get(i));
		}
		for (int i = 0; i < righttextList.size(); i++) {
			tagWords.addRightText(righttextList.get(i));
		}
		cdv.show();
	}

	private void addTexts(List<String> textList, List<String> leftextList,
			List<String> righttextList) {
		textList.add("一路狂奔");
		textList.add("后宫:帝王之妻");
		textList.add("宝贝和我");
		textList.add("甜心巧克力");
		textList.add("恐怖故事");
		textList.add("百万爱情宝贝");
		textList.add("别跟我谈高富帅");
		textList.add("甜蜜十八岁");
		textList.add("终结者");

		leftextList.add("百万爱情宝贝");
		leftextList.add("别跟我谈高富帅");
		leftextList.add("甜蜜十八岁");
		leftextList.add("金钱的味道");
		leftextList.add("艳遇");
		leftextList.add("痛症");
		leftextList.add("危险关系");
		leftextList.add("夺宝联盟");
		leftextList.add("101次求婚");
		leftextList.add("富春山居图");

		righttextList.add("艳遇");
		righttextList.add("痛症");
		righttextList.add("危险关系");
		righttextList.add("今天");
		righttextList.add("小时代");
		righttextList.add("致我们将死的青春");
		righttextList.add("金钱的味道");
		righttextList.add("101次求婚");
	}

	public void addImg() {

	}

	private void stopTag() {
		if (tagWords != null) {
			tagWords.stop();
			tagWords = null;
		}
	}

	private void cancel() {
		stopTag();
		if (cdv != null)
			cdv.cancel();
		cdv = null;
	}

	@Override
	public void onClick(View v) {
		Intent mIt = new Intent();
		switch (v.getId()) {
		case R.id.exitPoster:
			cancel();
			break;
		case R.id.exitSure:
			cancel();
			mActivity.finish();
			break;
		case R.id.exitCancel:
			cancel();
			break;
		case R.id.publishImg:
			cancel();
			mIt.setClass(mActivity, PublishActivity.class);
			mIt.putExtra(Constant.PUBLISH_TYPE, 0);
			mActivity.startActivity(mIt);
			break;
		case R.id.publishAudio:
			cancel();
			mIt.setClass(mActivity, VoiceActivity.class);
			mIt.putExtra(Constant.PUBLISH_TYPE, 1);
			mActivity.startActivity(mIt);
			break;
		case R.id.publishVideo:
			cancel();
			mIt.setClass(mActivity, PublishActivity.class);
			mIt.putExtra(Constant.PUBLISH_TYPE, 3);
			mActivity.startActivity(mIt);
			break;
		case R.id.searchCancel:
			cancel();
			break;
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		cancel();
	}
}
