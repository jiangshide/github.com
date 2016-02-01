package com.zdb.android.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.MainActivity;
import com.zdb.android.R;
import com.zdb.android.adapter.CusPageAdapter;
import com.zdb.android.base.BaseFragment;
import com.zdb.android.view.refresh.ZrcListView;

public class CircleFragment extends BaseFragment implements
		OnPageChangeListener, OnClickListener {

	@ViewInject(R.id.myFriend)
	private TextView myFriend;
	@ViewInject(R.id.myCircle)
	private TextView myCircle;

	@ViewInject(R.id.friendPage)
	private ViewPager friendPage;
	private List<View> viewArr;
	private int[] view = { R.layout.tab_friend_list, R.layout.tab_friend_circle };

	private ZrcListView friendList, circleList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view(R.layout.tab_friend);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ViewUtils.inject(this);
		initTop(0);
	}

	private void initTop(int i) {
		myCircle.setOnClickListener(this);
		myFriend.setOnClickListener(this);
		if (i == 0) {
			myFriend.setTextColor(color(R.color.pink));
			myCircle.setTextColor(color(R.color.white));
			myFriend.setBackgroundResource(R.drawable.btn_half_circle_left);
			myCircle.setBackgroundResource(R.drawable.transparent);
			((MainActivity) getActivity()).showMenu(true);
			friendPage.setCurrentItem(0);
		} else if (i == 1) {
			myFriend.setTextColor(color(R.color.white));
			myCircle.setTextColor(color(R.color.pink));
			myFriend.setBackgroundResource(R.drawable.transparent);
			myCircle.setBackgroundResource(R.drawable.btn_half_circle_right);
			((MainActivity) getActivity()).showMenu(false);
			friendPage.setCurrentItem(1);
		}
	}
	
	private void initData() {
		viewArr = new ArrayList<View>();
		int length = view.length;
		for (int i = 0; i < length; i++) {
			View v = LayoutInflater.from(mActivity).inflate(view[i], null);
			if (i == 0) {
				friendList = (ZrcListView) v.findViewById(R.id.friendList);
				initChildFriend(initListView(friendList,
						R.layout.tab_friend_nav));
			} else {
				circleList = (ZrcListView) v.findViewById(R.id.circleList);
				initChildCircle(initListView(circleList,
						R.layout.tab_friend_nav));
			}
			viewArr.add(v);
		}
		friendPage.setAdapter(new CusPageAdapter(viewArr));
		friendPage.setOnPageChangeListener(this);
		friendPage.setCurrentItem(0);
	}

	private void initChildFriend(View v) {
		LinearLayout tabFriendSearchL = (LinearLayout) v
				.findViewById(R.id.tabFriendSearchL);
		tabFriendSearchL.setOnClickListener(this);
		friendList.addHeaderView(v);
		// sufix = Constant.GET_FRIEND;
		// _class = FriendBean.class;
		// faceId.setVisibility(View.GONE);
		// publish.setVisibility(View.GONE);
		// showFriend();
	}

	private void initChildCircle(View v) {
		// publish.setOnClickListener(this);
		circleList.addHeaderView(v);
		// sufix = Constant.GET_CIRCLE;
		// faceId.setVisibility(View.GONE);
		// publish.setVisibility(View.VISIBLE);
		// _class = CircleBean.class;
		// showCircle();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == 0) {
			friendPage.setCurrentItem(0);
		} else {
			friendPage.setCurrentItem(1);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myFriend:
			initTop(0);
			break;
		case R.id.myCircle:
			initTop(1);
			break;
		}
	}
}
