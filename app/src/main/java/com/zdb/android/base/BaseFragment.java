package com.zdb.android.base;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.utils.HttpUtils;
import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.HttpUtils.OnResourceListener;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.android.utils.NetStateUtils.NetType;
import com.jsd.android.view.BaseFragmentActivity;
import com.jsd.android.view.BaseFragmentActivity.OnBackListener;
import com.zdb.android.MyApplication;
import com.zdb.android.MyApplication.NetListener;
import com.zdb.android.R;
import com.zdb.android.view.refresh.SimpleFooter;
import com.zdb.android.view.refresh.SimpleHeader;
import com.zdb.android.view.refresh.ZrcListView;
import com.zdb.android.view.refresh.ZrcListView.OnStartListener;

public abstract class BaseFragment extends Fragment implements OnBackListener,
		OnResourceListener, NetListener {

	protected Activity mActivity;
	protected HttpUtils net = HttpUtils.instance;
	protected View headView;
	protected LinearLayout noNetL;
	private Handler handler;
	protected boolean isRefresh;

	protected View view(int layout) {
		return LayoutInflater.from(getActivity()).inflate(layout, null);
	}

	protected View view(LayoutInflater inflater, int layout) {
		return inflater.inflate(layout, null);
	}

	protected int color(int color) {
		return getActivity().getResources().getColor(color);
	}

	protected String str(int str) {
		return getActivity().getResources().getString(str);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
		if (mActivity == null)
			return;
		((MyApplication) getActivity().getApplication()).registetNet(this);
		((BaseFragmentActivity) mActivity).onBack(this);
	}

	@Override
	public void onNetType(NetType type, boolean isNet) {
		if (noNetL != null) {
			if (isNet) {
				noNetL.setVisibility(View.GONE);
			} else {
				noNetL.setVisibility(View.VISIBLE);
			}
		}
	}

	protected View initListView(ZrcListView listView, int headLayout) {
		handler = new Handler();

		float density = getResources().getDisplayMetrics().density;
		listView.setFirstTopOffset((int) (50 * density));

		SimpleHeader header = new SimpleHeader(getActivity());
		header.setTextColor(0xff0066aa);
		header.setCircleColor(0xff33bbee);
		listView.setHeadable(header);

		SimpleFooter footer = new SimpleFooter(getActivity());
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
		return headView = LayoutInflater.from(getActivity()).inflate(
				headLayout, null);
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

	@Override
	public void onBack(int keyCode, KeyEvent event) {
	}

	protected void toPage(Object _class, boolean isFinish) {
		startActivity(new Intent(mActivity, (Class<?>) _class));
		if (isFinish)
			mActivity.finish();
	}

	protected void toPage(Object _class, Bundle b, boolean isFinish) {
		startActivity(new Intent(mActivity, (Class<?>) _class).putExtras(b));
		if (isFinish)
			mActivity.finish();
	}

	protected void netProgress(HTTP_MOTHED method,
			HashMap<String, Object> params, Class cls, String sufx,
			boolean refresh) {
		this.isRefresh = refresh;
		net.setSufix(sufx);
		net.sendMsg(method, params, this, cls);
	}

	@Override
	public void onDownloading(int downloadSize) {
	}

	@Override
	public void onSuccess(Object obj) {
	}

	@Override
	public void onFailse(JException err) {
	}

	@Override
	public void cancel() {
	}
}
