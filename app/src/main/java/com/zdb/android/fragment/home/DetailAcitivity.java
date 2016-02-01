package com.zdb.android.fragment.home;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.utils.LogUtils;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.adapter.CusPageAdapter;
import com.zdb.android.adapter.HomeAdapter;
import com.zdb.android.adapter.HomeDetailParameterAdapter;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.HomeBean.HomeList;
import com.zdb.android.bean.HomeDetailParameterBean;
import com.zdb.android.view.CusViewPager;
import com.zdb.android.view.refresh.ZrcListView;

public class DetailAcitivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {

	@ViewInject(R.id.left)
	private ImageView back;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.right)
	private ImageView msg;

	@ViewInject(R.id.homeDetailList)
	private ZrcListView homeDetailList;

	private CusViewPager banner;
	private LinearLayout point;
	private TextView bannerTitle;
	private TextView homeDetailTitle, homeDetailDes, homeDetailSellPrice,
			homeDetailPrice, homeDetailShopNum, homeDetailShopName,
			homeDetailSpeed, homeDetailAttitude, homeDetailAgree;
	private Button homeDetailImgList, homeDetailComment, homeDetailParameter;
	private ImageView homeDetailPost, homeDetailShopIcon;
	private CusViewPager homeDetailPage;

	private List<View> arrView;
	private int rootV[] = { R.layout.tab_home_detail_imglist,
			R.layout.tab_home_detail_comment,
			R.layout.tab_home_detail_parameter };

	private CusPageAdapter mCusPageAdapter;

	private LinearLayout homeDetailImgListL;
	private ListView homeDetailParameterL;

	private HomeDetailParameterAdapter mHomeDetailParameterAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_home_detail);
		ViewUtils.inject(this);
		initTop();
		initChildView(initListView(homeDetailList, R.layout.tab_home_detail_nav));
	}

	private void initChildView(View v) {
		banner = (CusViewPager) v.findViewById(R.id.banner);
		point = (LinearLayout) v.findViewById(R.id.point);
		bannerTitle = (TextView) v.findViewById(R.id.bannerTitle);
		homeDetailTitle = (TextView) v.findViewById(R.id.homeDetailTitle);
		homeDetailDes = (TextView) v.findViewById(R.id.homeDetailDes);
		homeDetailSellPrice = (TextView) v
				.findViewById(R.id.homeDetailSellPrice);
		homeDetailPrice = (TextView) v.findViewById(R.id.homeDetailPrice);
		homeDetailShopNum = (TextView) v.findViewById(R.id.homeDetailShopNum);
		homeDetailShopName = (TextView) v.findViewById(R.id.homeDetailShopName);
		homeDetailSpeed = (TextView) v.findViewById(R.id.homeDetailSpeed);
		homeDetailAttitude = (TextView) v.findViewById(R.id.homeDetailAttitude);
		homeDetailAgree = (TextView) v.findViewById(R.id.homeDetailAgree);
		homeDetailPost = (ImageView) v.findViewById(R.id.homeDetailPost);
		homeDetailShopIcon = (ImageView) v
				.findViewById(R.id.homeDetailShopIcon);
		homeDetailImgList = (Button) v.findViewById(R.id.homeDetailImgList);
		homeDetailComment = (Button) v.findViewById(R.id.homeDetailComment);
		homeDetailParameter = (Button) v.findViewById(R.id.homeDetailParameter);
		homeDetailPage = (CusViewPager) v.findViewById(R.id.homeDetailPage);
		homeDetailList.addHeaderView(v);
		initHead();
	}

	private void initHead() {
		initPage();
		initContent();
	}

	private View getView(int layout) {
		return LayoutInflater.from(this).inflate(layout, null);
	}

	private void initPage() {
		arrView = new ArrayList<View>();
		int size = rootV.length;
		for (int i = 0; i < size; i++) {
			View v = getView(rootV[i]);
			switch (i) {
			case 0:
				homeDetailImgListL = (LinearLayout) v
						.findViewById(R.id.homeDetailImgListL);
				break;
			case 1:

				break;
			case 2:
				homeDetailParameterL = (ListView) v
						.findViewById(R.id.homeDetailParameterL);
				break;
			}
			arrView.add(v);
		}
		homeDetailPage
				.setAdapter(mCusPageAdapter = new CusPageAdapter(arrView));
		homeDetailPage.setOnPageChangeListener(this);
		LogUtils.e("-----------initPage(...)");
		onPageSelected(0);
	}

	private HomeAdapter mHomeAdapter;

	private void initContent() {
		homeDetailList.setAdapter(mHomeAdapter = new HomeAdapter(this));
		homeDetailList.refresh();
	}

	private void initTop() {
		Bundle b = getIntent().getExtras();
		HomeList hl = (HomeList) b.getSerializable("homeItem");
		title.setText("宝贝详情");
		back.setVisibility(View.VISIBLE);
		msg.setVisibility(View.GONE);
		back.setImageResource(R.drawable.btn_back);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		LogUtils.e("-----------v:" + v);
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		case R.id.homeDetailImgList:
			homeDetailPage.setCurrentItem(0);
			break;
		case R.id.homeDetailComment:
			homeDetailPage.setCurrentItem(1);
			break;
		case R.id.homeDetailParameter:
			homeDetailPage.setCurrentItem(2);
			break;
		}
	}

	public void homeDetailMsg(View v) {

	}

	public void homeDetailShop(View v) {

	}

	public void homeDetailAddShop(View v) {

	}

	public void homeDetailShoped(View v) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		LogUtils.e("--------arg0:" + arg0);
		switch (arg0) {
		case 0:
			setBtn(new Button[] { homeDetailImgList, homeDetailComment,
					homeDetailParameter }, new int[] {
					R.drawable.btn_border_bottom, R.drawable.translate,
					R.drawable.translate }, color(R.color.pink),
					color(R.color.gray_white), color(R.color.gray_white));
			imgList();
			break;
		case 1:
			setBtn(new Button[] { homeDetailImgList, homeDetailComment,
					homeDetailParameter }, new int[] { R.drawable.translate,
					R.drawable.btn_border_bottom, R.drawable.translate },
					color(R.color.gray_white), color(R.color.pink),
					color(R.color.gray_white));
			comment();
			break;
		case 2:
			setBtn(new Button[] { homeDetailImgList, homeDetailComment,
					homeDetailParameter }, new int[] { R.drawable.translate,
					R.drawable.translate, R.drawable.btn_border_bottom },
					color(R.color.gray_white), color(R.color.gray_white),
					color(R.color.pink));
			parameter();
			break;
		}
	}

	private void imgList() {
		homeDetailImgListL.removeAllViews();
	}

	private void comment() {

	}

	private void parameter() {
		homeDetailParameterL
				.setAdapter(mHomeDetailParameterAdapter = new HomeDetailParameterAdapter(
						this, getParameterArr()));
	}

	private String pName[] = { "产品名称：", "化妆品品类：", "规格类型：", "化妆品净含量：", "功效：",
			"适合肤质：", "品牌：", "雅蓝丝带单品：", "特殊用途化妆品批准文号：" };
	private String pDes[] = { "EsteeLauder/雅蓝丝带鲜品出炉", "精华水", "正常规格", "200ml",
			"均匀肤色 体谅肤色 补水 保湿 滋润 细夫", "任何肤质", "Estee Lauder/雅蓝丝带",
			"鲜亮焕彩精粹水(滋润型)", "无" };

	private List<HomeDetailParameterBean> getParameterArr() {
		List<HomeDetailParameterBean> arr = new ArrayList<HomeDetailParameterBean>();
		int size = pName.length;
		for (int i = 0; i < size; i++) {
			HomeDetailParameterBean hdpb = new HomeDetailParameterBean();
			hdpb.name = pName[i];
			hdpb.des = pDes[i];
			arr.add(hdpb);
		}
		return arr;
	}

	private void setBtn(Button[] b, int[] drawable, int... color) {
		for (int i = 0; i < b.length; i++) {
			b[i].setBackgroundResource(drawable[i]);
			b[i].setTextColor(color[i]);
			b[i].setPadding(20, 20, 20, 20);
			b[i].setOnClickListener(this);
			b[i].setTextSize(16);
		}
	}
}
