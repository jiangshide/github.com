package com.zdb.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.android.utils.TimeUtils;
import com.android.utils.TimeUtils.CountDownListener;
import com.android.utils.ValidateUtils;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.activity.GuideActivity;
import com.zdb.android.activity.TestActivity;
import com.zdb.android.activity.TryActivity;
import com.zdb.android.activity.WarActivity;
import com.zdb.android.adapter.CusPageAdapter;
import com.zdb.android.adapter.HomeAdapter;
import com.zdb.android.base.BaseFragment;
import com.zdb.android.bean.HomeBean;
import com.zdb.android.bean.HomeBean.Banner;
import com.zdb.android.bean.HomeBean.HomeList;
import com.zdb.android.utils.Constant;
import com.zdb.android.utils.LocationUtils;
import com.zdb.android.view.CusViewPager;
import com.zdb.android.view.refresh.ZrcListView;

public class HomeFragment extends BaseFragment implements OnClickListener,
		OnPageChangeListener {

	@ViewInject(R.id.left)
	private ImageView back;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.right)
	private ImageView msg;

	@ViewInject(R.id.homeList)
	private ZrcListView listView;

	private int pageId = 0;
	private int nums = 10;

	private CusViewPager banner;
	private LinearLayout point;
	private TextView bannerTitle;
	private LinearLayout navTest;
	private LinearLayout navWar;
	private LinearLayout navGuide;
	private LinearLayout navTry;

	private HomeAdapter mHomeAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view(inflater, R.layout.tab_home);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ViewUtils.inject(this);
		initTop();
		setListener();
		initChildView(initListView(listView, R.layout.tab_home_nav));
		long v1 = 155848l;
		long v2 = 1012673l;
		double s = Double.parseDouble(v1+"") / Double.parseDouble(v2+"");
		int s1 = (int) (s*100);
		int curr = (int) ((155848 / 1012673) * 100);
		LogUtils.e("-----------arg0:" + 1012673 + " | arg1:"
				+ 155848 + " | curr:" + curr+" | s:"+s+" | s1:"+s1);
	}

	private void initChildView(View v) {
		noNetL = (LinearLayout) v.findViewById(R.id.noNetL);
		banner = (CusViewPager) v.findViewById(R.id.banner);
		point = (LinearLayout) v.findViewById(R.id.point);
		bannerTitle = (TextView) v.findViewById(R.id.bannerTitle);
		navTest = (LinearLayout) v.findViewById(R.id.navTest);
		navWar = (LinearLayout) v.findViewById(R.id.navWar);
		navGuide = (LinearLayout) v.findViewById(R.id.navGuide);
		navTry = (LinearLayout) v.findViewById(R.id.navTry);
		listView.addHeaderView(v);
		initNav();
		showShop();
	}

	private void showShop() {
		listView.setAdapter(mHomeAdapter = new HomeAdapter(getActivity()));
		listView.refresh();
	}

	private void initPoint(int pos) {
		if (point != null)
			point.removeAllViews();
		int size = mBannerArr.size();
		for (int i = 0; i < size; i++) {
			ImageView img = new ImageView(getActivity());
			if (i == pos) {
				bannerTitle.setText(mBannerArr.get(i).title);
				img.setImageResource(R.drawable.round_white);
			} else
				img.setImageResource(R.drawable.round_red);
			point.addView(img);
		}
	}

	private void initNav() {
		navTest.setOnClickListener(this);
		navWar.setOnClickListener(this);
		navGuide.setOnClickListener(this);
		navTry.setOnClickListener(this);
	}

	private void initTop() {
		title.setText("战痘吧");
		back.setVisibility(View.VISIBLE);
		msg.setVisibility(View.VISIBLE);
		back.setImageResource(R.drawable.seach);
		msg.setImageResource(R.drawable.msg);
	}

	private void setListener() {
		back.setOnClickListener(this);
		msg.setOnClickListener(this);
	}

	private List<Banner> mBannerArr;

	private void initBanner(List<Banner> bannerArr) {
		mBannerArr = bannerArr;
		List<ImageView> imgList = new ArrayList<ImageView>();
		int size = bannerArr.size();
		for (int i = 0; i < size; i++) {
			ImageView img = new ImageView(getActivity());
			LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					210);
			img.setLayoutParams(mParams);
			img.setScaleType(ScaleType.FIT_XY);
			imgList.add(img);
			String title = bannerArr.get(i).des;
			bannerTitle.setText(title != null ? title : "");
			String url = bannerArr.get(i).img;
			if (!TextUtils.isEmpty(url))
				ImageLoader.getInstance().displayImage(url, img);
		}
		banner.setOnPageChangeListener(this);
		banner.setAdapter(new CusPageAdapter(imgList));
		TimeUtils.instance.cycleTime(size, 3, new CountDownListener() {
			@Override
			public void countDown(int time) {
				banner.setCurrentItem(time, true);
			}

			@Override
			public void timeFinish() {
			}
		});
		initPoint(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.right:
			break;
		case R.id.left:

			break;
		case R.id.navTest:
			toPage(TestActivity.class, false);
			break;
		case R.id.navWar:
			toPage(WarActivity.class, false);
			break;
		case R.id.navGuide:
			toPage(GuideActivity.class, false);
			break;
		case R.id.navTry:
			toPage(TryActivity.class, false);
			break;
		}
	}

	@Override
	protected void onRefresh() {
		super.onRefresh();
		request();
	}

	@Override
	protected void onLoadMore() {
		super.onLoadMore();
		requestMore();
	}

	private void request() {
		netProgress(HTTP_MOTHED.GET, null, HomeBean.class, Constant.HOME_SHOP,
				true);
	}

	private void requestMore() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageId * nums);
		map.put("end", nums);
		netProgress(HTTP_MOTHED.GET, map, HomeBean.class,
				Constant.HOME_SHOP_LIST, false);
		pageId++;
	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		LogUtils.e("------------obj:" + obj);
		if (isRefresh) {
			if (obj instanceof HomeBean) {
				HomeBean hb = (HomeBean) obj;
				if (hb.res != null && hb.result == 0) {
					List<HomeList> shop = hb.res.homeList;
					if (ValidateUtils.isArr(shop)) {
						List<Banner> banner = hb.res.banner;
						if (ValidateUtils.isArr(banner)) {
							initBanner(banner);
						}
						mHomeAdapter.refresh(shop);
						listView.setRefreshSuccess("加载成功"); // 通知加载成功
						int size = shop.size();
						if (size >= nums)
							listView.startLoadMore(); // 开启LoadingMore功能
					} else {
						listView.setRefreshFail(hb.msg);
					}
				} else {
					listView.setRefreshFail(hb.msg);
				}

			} else {
				listView.setRefreshFail("加载失败");
			}
		} else {
			if (obj instanceof HomeBean) {
				HomeBean hb = (HomeBean) obj;
				if (hb.res != null && hb.result == 0) {
					List<HomeList> shop = hb.res.homeList;
					if (ValidateUtils.isArr(shop)) {
						mHomeAdapter.loadMore(shop);
						listView.setLoadMoreSuccess();
					} else {
						listView.stopLoadMore();
					}
				} else {
					listView.stopLoadMore();
				}
			} else {
				listView.stopLoadMore();
			}
		}

	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);
		LogUtils.e("-----------err:" + err);
		if (isRefresh) {
			listView.setRefreshFail(err.msg);
		} else {
			listView.stopLoadMore();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		initPoint(arg0);
	}
}
