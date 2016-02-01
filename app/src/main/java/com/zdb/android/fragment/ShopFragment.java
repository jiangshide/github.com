package com.zdb.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Paint;
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
import com.zdb.android.activity.ShopCartActivity;
import com.zdb.android.adapter.CusPageAdapter;
import com.zdb.android.adapter.ShopAdapter;
import com.zdb.android.base.BaseFragment;
import com.zdb.android.bean.ShopBean;
import com.zdb.android.bean.ShopBean.Ad;
import com.zdb.android.bean.ShopBean.Indulgence;
import com.zdb.android.bean.ShopBean.Prefecture;
import com.zdb.android.bean.ShopBean.Recommend;
import com.zdb.android.utils.Constant;
import com.zdb.android.utils.CusView;
import com.zdb.android.view.CusViewPager;
import com.zdb.android.view.refresh.ZrcListView;
import com.zdb.android.view.refresh.ZrcListView.OnItemClickListener;

public class ShopFragment extends BaseFragment implements OnClickListener,
		OnItemClickListener, OnPageChangeListener {

	@ViewInject(R.id.left)
	private ImageView left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.right)
	private ImageView shop;

	@ViewInject(R.id.shopList)
	private ZrcListView shopList;

	private CusViewPager banner;
	private LinearLayout point;
	private TextView bannerTitle;
	private ImageView shopIndulgenceLeftImg, shopIndulgenceRightImg;
	private TextView shopIndulgenceLeftPrice, shopIndulgenceRightPrice;
	private LinearLayout shopRecommendL;

	private int pageId = 0;
	private int nums = 10;

	private ShopAdapter mShopAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view(inflater, R.layout.tab_shop);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ViewUtils.inject(this);
		initTop();
	}

	private void initTop() {
		title.setText("美肤购");
		left.setVisibility(View.GONE);
		shop.setVisibility(View.VISIBLE);
		shop.setImageResource(R.drawable.shop);
		shop.setOnClickListener(this);
		initChildView(initListView(shopList, R.layout.tab_shop_nav));
	}

	private void initChildView(View v) {
		banner = (CusViewPager) v.findViewById(R.id.banner);
		point = (LinearLayout) v.findViewById(R.id.point);
		bannerTitle = (TextView) v.findViewById(R.id.bannerTitle);
		shopIndulgenceLeftImg = (ImageView) v
				.findViewById(R.id.shopIndulgenceLeftImg);
		shopIndulgenceRightImg = (ImageView) v
				.findViewById(R.id.shopIndulgenceRightImg);
		shopIndulgenceLeftPrice = (TextView) v
				.findViewById(R.id.shopIndulgenceLeftPrice);
		shopIndulgenceRightPrice = (TextView) v
				.findViewById(R.id.shopIndulgenceRightPrice);
		shopRecommendL = (LinearLayout) v.findViewById(R.id.shopRecommendL);
		shopList.addHeaderView(v);
		showShop();
	}

	private void showShop() {
		shopList.setAdapter(mShopAdapter = new ShopAdapter(getActivity()));
		shopList.refresh();
		shopList.setOnItemClickListener(this);
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
		netProgress(HTTP_MOTHED.GET, null, ShopBean.class, Constant.TAB_SHOP,
				true);
	}

	private void requestMore() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageId * nums);
		map.put("end", nums);
		netProgress(HTTP_MOTHED.GET, map, ShopBean.class, Constant.TAB_SHOP,
				false);
		pageId++;
	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		LogUtils.e("------------obj:" + obj);
		if (isRefresh) {
			if (obj instanceof ShopBean) {
				ShopBean sb = (ShopBean) obj;
				if (sb.res != null && sb.result == 0) {
					List<Ad> ad = sb.res.shopAd;
					if (ValidateUtils.isArr(ad)) {
						initAd(ad);
					}
					List<Indulgence> indulgence = sb.res.indulgence;
					if (ValidateUtils.isArr(indulgence)) {
						initIndulgence(indulgence);
					}
					List<Recommend> recommend = sb.res.recommend;
					if (ValidateUtils.isArr(recommend)) {
						initRecommend(recommend);
					}
					List<Prefecture> prefecture = sb.res.prefecture;
					if (ValidateUtils.isArr(prefecture)) {
						mShopAdapter.refresh(prefecture);
						shopList.setRefreshSuccess("加载成功"); // 通知加载成功
						int size = prefecture.size();
						if (size >= nums)
							shopList.startLoadMore(); // 开启LoadingMore功能
					} else {
						shopList.setRefreshFail(sb.msg);
					}
				} else {
					shopList.setRefreshFail(sb.msg);
				}
			} else {
				shopList.setRefreshFail("加载失败");
			}
		} else {
			if (obj instanceof ShopBean) {
				ShopBean sb = (ShopBean) obj;
				if (sb.res != null && sb.result == 0) {
					List<Prefecture> prefecture = sb.res.prefecture;
					if (ValidateUtils.isArr(prefecture)) {
						mShopAdapter.loadMore(prefecture);
						shopList.setLoadMoreSuccess();
					} else {
						shopList.stopLoadMore();
					}
				} else {
					shopList.stopLoadMore();
				}
			} else {
				shopList.stopLoadMore();
			}
		}
	}

	private void initRecommend(List<Recommend> recommend) {
		shopRecommendL.removeAllViews();
		int size = recommend.size();
		for (int i = 0; i < size; i++) {
			View v = view(R.layout.tab_shop_recommend);
			ImageView shopRecommendLogo = (ImageView) v
					.findViewById(R.id.shopRecommendLogo);
			ImageView shopRecommendImg = (ImageView) v
					.findViewById(R.id.shopRecommendImg);
			TextView shopRecommendDes = (TextView) v
					.findViewById(R.id.shopRecommendDes);
			TextView shopRecommendPrice = (TextView) v
					.findViewById(R.id.shopRecommendPrice);
			TextView shopRecommendMaketPrice = (TextView) v
					.findViewById(R.id.shopRecommendMaketPrice);
			LinearLayout shopRecommendLabel = (LinearLayout) v
					.findViewById(R.id.shopRecommendLabel);
			ImageLoader.getInstance().displayImage(recommend.get(i).logo,
					shopRecommendLogo);
			ImageLoader.getInstance().displayImage(recommend.get(i).img,
					shopRecommendImg);
			shopRecommendDes.setText(recommend.get(i).des);
			shopRecommendPrice.setText("¥" + recommend.get(i).price);
			shopRecommendMaketPrice.setText("¥" + recommend.get(i).maketPrice);
			shopRecommendMaketPrice.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			String label = recommend.get(i).label;
			if (!TextUtils.isEmpty(label)) {
				if (label.contains(",")) {
					String[] labelArr = label.split(",");
					int length = labelArr.length;
					for (int j = 0; j < length; j++) {
						CusView.addBtn(getActivity(), labelArr[j],
								shopRecommendLabel, color(R.color.white));
					}
				} else {
					CusView.addBtn(getActivity(), label, shopRecommendLabel,
							color(R.color.white));
				}
			}
			TextView txt = new TextView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 5);
			txt.setBackgroundResource(R.drawable.transparent);
			txt.setLayoutParams(params);
			shopRecommendL.addView(v);
			shopRecommendL.addView(txt);

		}
	}

	private void initIndulgence(List<Indulgence> arr) {
		int size = arr.size();
		for (int i = 0; i < size; i++) {
			Indulgence indulgence = arr.get(i);
			String price = indulgence.price;
			String img = indulgence.img;
			if (i == 0) {
				shopIndulgenceLeftPrice.setText(TextUtils.isEmpty(price) ? "0"
						: price);
				ImageLoader.getInstance().displayImage(img,
						shopIndulgenceLeftImg);
			} else if (i == 1) {
				shopIndulgenceRightPrice.setText(TextUtils.isEmpty(price) ? "0"
						: price);
				ImageLoader.getInstance().displayImage(img,
						shopIndulgenceRightImg);
			}

		}
	}

	private List<Ad> mAdArr;

	private void initAd(List<Ad> adArr) {
		mAdArr = adArr;
		List<ImageView> imgList = new ArrayList<ImageView>();
		int size = adArr.size();
		for (int i = 0; i < size; i++) {
			ImageView img = new ImageView(getActivity());
			LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					210);
			img.setLayoutParams(mParams);
			img.setScaleType(ScaleType.FIT_XY);
			imgList.add(img);
			String url = adArr.get(i).img;
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
		// initPoint(0);
	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);
		LogUtils.e("-----------err:" + err);
		if (isRefresh) {
			shopList.setRefreshFail(err.msg);
		} else {
			shopList.stopLoadMore();
		}
	}

	@Override
	public void onClick(View v) {
		// toPage(LoginActivity.class, false);
		switch (v.getId()) {
		case R.id.right:
			toPage(ShopCartActivity.class, false);
			break;
		}
	}

	@Override
	public void onItemClick(ZrcListView parent, View view, int position, long id) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
	}

}
