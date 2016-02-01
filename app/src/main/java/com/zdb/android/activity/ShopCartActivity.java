package com.zdb.android.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.JException;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.adapter.ShopCartAdapter;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.ShopBean;
import com.zdb.android.utils.Constant;
import com.zdb.android.view.refresh.ZrcListView;

public class ShopCartActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.left)
	private ImageView left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.right)
	private ImageView right;

	@ViewInject(R.id.shopCartList)
	private ZrcListView shopCartList;
	@ViewInject(R.id.shopCartAll)
	private CheckBox shopCartAll;
	@ViewInject(R.id.shopCartMoney)
	private TextView shopCartMoney, shopCartFreight;
	@ViewInject(R.id.shopCartSettlement)
	private Button shopCartSettlement;

	private ShopCartAdapter mShopCartAdapter;

	private int pageId = 0;
	private int nums = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_cart);
		ViewUtils.inject(this);
		initTop();
	}

	private void initTop() {
		left.setVisibility(View.VISIBLE);
		right.setVisibility(View.GONE);
		left.setImageResource(R.drawable.btn_back);
		title.setText("购物车");
		left.setOnClickListener(this);
		shopCartSettlement.setOnClickListener(this);
		initData();
	}

	private void initData() {
		shopCartList.setAdapter(mShopCartAdapter = new ShopCartAdapter(this));
		shopCartList.refresh();
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

	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		case R.id.shopCartSettlement:

			break;
		}
	}

}
