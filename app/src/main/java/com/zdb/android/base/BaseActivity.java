package com.zdb.android.base;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.android.utils.HttpUtils;
import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.HttpUtils.OnResourceListener;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.google.gson.Gson;
import com.zdb.android.R;
import com.zdb.android.view.CusDialogView;
import com.zdb.android.view.refresh.SimpleFooter;
import com.zdb.android.view.refresh.SimpleHeader;
import com.zdb.android.view.refresh.ZrcListView;
import com.zdb.android.view.refresh.ZrcListView.OnStartListener;

public class BaseActivity extends Activity implements OnResourceListener {

	protected HttpUtils net = HttpUtils.instance;
	protected View headView;
	private Handler handler;
	protected boolean isRefresh;

	protected View view(int layout) {
		return LayoutInflater.from(this).inflate(layout, null);
	}
	
	protected int color(int color) {
		return getResources().getColor(color);
	}

	protected String str(int str) {
		return getResources().getString(str);
	}
	
	protected View initListView(ZrcListView listView, int headLayout) {
		handler = new Handler();

		float density = getResources().getDisplayMetrics().density;
//		listView.setFirstTopOffset((int) (50 * density));

		SimpleHeader header = new SimpleHeader(this);
		header.setTextColor(0xff0066aa);
		header.setCircleColor(0xff33bbee);
		listView.setHeadable(header);

		SimpleFooter footer = new SimpleFooter(this);
		footer.setCircleColor(0xff33bbee);
		listView.setFootable(footer);

		listView.setItemAnimForTopIn(R.anim.topitem_in);
		listView.setItemAnimForBottomIn(R.anim.bottomitem_in);

		listView.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				refresh();
			}
		});

		// 加载更多事件回调（可选）
		listView.setOnLoadMoreStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				loadMore();
			}
		});
		if (headView != null)
			listView.removeHeaderView(headView);
		return headView = LayoutInflater.from(this).inflate(headLayout, null);
	}

	private void refresh() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				LogUtils.e("-----------------refresh");
				onRefresh();
			}
		}, 2 * 1000);
	}

	protected void onRefresh() {

	}

	private void loadMore() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				LogUtils.e("-----------------loadMore");
				onLoadMore();
			}
		}, 2 * 1000);
	}

	protected void onLoadMore() {
	}

	protected void toPage(Object _class, boolean isFinish) {
		startActivity(new Intent(this, (Class<?>) _class));
		if (isFinish)
			this.finish();
	}

	protected void toPage(Object _class, Bundle b, boolean isFinish) {
		startActivity(new Intent(this, (Class<?>) _class).putExtras(b));
		if (isFinish)
			this.finish();
	}

	private CusDialogView mDialog;

	protected HashMap<String, Object> getMap(Object o) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(net.JSON, new Gson().toJson(o));
		return map;
	}
	
	protected void netProgress(HTTP_MOTHED method,
			HashMap<String, Object> params, Class cls, String sufx,
			boolean isProgress) {
		net.setSufix(sufx);
		net.sendMsg(method, params, this, cls);
		if (isProgress) {
			cancelProgress();
			mDialog = new CusDialogView(this, R.layout.loading);
			mDialog.show();
		}
	}

	public void cancelProgress() {
		if (mDialog != null) {
			mDialog.cancel();
			mDialog = null;
		}
	}

	@Override
	public void onDownloading(int downloadSize) {

	}

	@Override
	public void onSuccess(Object obj) {
		cancelProgress();
	}

	@Override
	public void onFailse(JException err) {
		cancelProgress();
	}

	@Override
	public void cancel() {
		cancelProgress();
	}

}
