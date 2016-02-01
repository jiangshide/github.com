package com.zdb.android.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.LogUtils;
import com.android.utils.SharedUtils;
import com.android.utils.TimeUtils;
import com.android.utils.TimeUtils.CountDownListener;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.MainActivity;
import com.zdb.android.MyApplication;
import com.zdb.android.R;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.SplashBean;
import com.zdb.android.bean.SplashBean.Poster;
import com.zdb.android.utils.Constant;

public class SplashActivity extends BaseActivity implements CountDownListener {

	@ViewInject(R.id.poster)
	public ImageView poster;
	@ViewInject(R.id.countDown)
	public TextView countDown;
	private int time = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		ViewUtils.inject(this);
		initData();
	}

	private void initData() {
		SharedUtils su = SharedUtils.instance;
		String posterStr = su.getString(this, Constant.POSTER);
		LogUtils.e("------posterStr:" + posterStr);
		if (!TextUtils.isEmpty(posterStr)) {
			ImageLoader.getInstance().displayImage(posterStr, poster);
		}
		countDown.setText(time + "");
		TimeUtils.instance.countDown(time, null, null, this);
		netProgress(HTTP_MOTHED.GET, null, SplashBean.class,
				Constant.GET_SPLASH, false);
		((MyApplication) getApplication()).initAlbum(false);
	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		if (obj instanceof SplashBean) {
			SplashBean sb = (SplashBean) obj;
			if (sb.result == 0 && sb.res != null) {
				Poster p = sb.res;
				String img = p.img;
				if (!TextUtils.isEmpty(img)) {
					SharedUtils.instance.putString(this, Constant.POSTER, img);
					SharedUtils.instance.putString(this,
							Constant.POSTER_DIALOG, img);
				}

			}
		}
	}

	@Override
	public void countDown(int time) {
		countDown.setText(time + "");
	}

	@Override
	public void timeFinish() {
		countDown.setText(0 + "");
		toPage(MainActivity.class, true);
	}
}
