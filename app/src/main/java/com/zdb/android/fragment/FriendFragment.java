package com.zdb.android.fragment;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.android.utils.ValidateUtils;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.MainActivity;
import com.zdb.android.R;
import com.zdb.android.adapter.CircleAdapter;
import com.zdb.android.adapter.FriendAdapter;
import com.zdb.android.base.BaseFragment;
import com.zdb.android.bean.Circle;
import com.zdb.android.bean.CircleBean;
import com.zdb.android.bean.Friend;
import com.zdb.android.bean.FriendBean;
import com.zdb.android.utils.Constant;
import com.zdb.android.utils.DialogUtils;
import com.zdb.android.utils.GestureListener;
import com.zdb.android.view.face.FaceRelativeLayout;
import com.zdb.android.view.refresh.ZrcListView;
import com.zdb.android.view.refresh.ZrcListView.OnItemClickListener;

public class FriendFragment extends BaseFragment implements OnClickListener,
		OnItemClickListener {

	@ViewInject(R.id.myFriend)
	private TextView myFriend;
	@ViewInject(R.id.myCircle)
	private TextView myCircle;

	@ViewInject(R.id.friendList)
	private ZrcListView friendList;
	@ViewInject(R.id.publish)
	private Button publish;

	@ViewInject(R.id.faceId)
	private FaceRelativeLayout faceId;

	private int index = 0;
	private int pageId = 0;
	private int nums = 10;

	private FriendAdapter mFriendAdapter;
	private CircleAdapter mCircleAdapter;

	private List<Friend> mFriendArr;
	private List<Circle> mCircleArr;

	private String sufix;
	private Class _class;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view(inflater, R.layout.tab_friend);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ViewUtils.inject(this);
		myFriend.setOnClickListener(this);
		myCircle.setOnClickListener(this);
		initTop(0);
	}

	private void initTop(int i) {
		index = i;
		if (i == 0) {
			myFriend.setTextColor(color(R.color.pink));
			myCircle.setTextColor(color(R.color.white));
			myFriend.setBackgroundResource(R.drawable.btn_half_circle_left);
			myCircle.setBackgroundResource(R.drawable.transparent);
			((MainActivity) getActivity()).showMenu(true);
			initChildFriend(initListView(friendList, R.layout.tab_friend_nav));
		} else if (i == 1) {
			myFriend.setTextColor(color(R.color.white));
			myCircle.setTextColor(color(R.color.pink));
			myFriend.setBackgroundResource(R.drawable.transparent);
			myCircle.setBackgroundResource(R.drawable.btn_half_circle_right);
			((MainActivity) getActivity()).showMenu(false);
			initChildCircle(headView = initListView(friendList,
					R.layout.tab_friend_circle));
		}
		friendList.setOnTouchListener(new GestureListener(getActivity()) {
			@Override
			public boolean left() {
				initTop(1);
				return super.left();
			}

			@Override
			public boolean right() {
				initTop(0);
				return super.right();
			}
		});
	}

	@Override
	public void onBack(int keyCode, KeyEvent event) {
		super.onBack(keyCode, event);
		faceId.setVisibility(View.GONE);
	}

	private void initChildFriend(View v) {
		LinearLayout tabFriendSearchL = (LinearLayout) v
				.findViewById(R.id.tabFriendSearchL);
		tabFriendSearchL.setOnClickListener(this);
		friendList.addHeaderView(v);
		sufix = Constant.GET_FRIEND;
		_class = FriendBean.class;
		faceId.setVisibility(View.GONE);
		publish.setVisibility(View.GONE);
		showFriend();
	}

	private void initChildCircle(View v) {
		publish.setOnClickListener(this);
		friendList.addHeaderView(v);
		sufix = Constant.GET_CIRCLE;
		faceId.setVisibility(View.GONE);
		publish.setVisibility(View.VISIBLE);
		_class = CircleBean.class;
		showCircle();
	}

	private void showFriend() {
		friendList
				.setAdapter(mFriendAdapter = new FriendAdapter(getActivity()));
		friendList.refresh();
		friendList.setOnItemClickListener(this);
		LogUtils.e("----------sufix:" + sufix);
		request();
	}

	private void showCircle() {
		friendList.setAdapter(mCircleAdapter = new CircleAdapter(getActivity(),
				faceId));
		friendList.refresh();
		friendList.setOnItemClickListener(this);
		LogUtils.e("----------sufix:" + sufix);
		request();
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
		netProgress(HTTP_MOTHED.GET, null, _class, sufix, true);
	}

	private void requestMore() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageId * nums);
		map.put("end", nums);
		netProgress(HTTP_MOTHED.GET, map, _class, sufix, false);
		pageId++;
	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		if (obj instanceof FriendBean) {
			if (isRefresh) {
				FriendBean fb = (FriendBean) obj;
				if (fb.result == 0 && fb.friend != null) {
					mFriendArr = fb.friend;
					if (ValidateUtils.isArr(mFriendArr) && index == 0) {
						mFriendAdapter.refresh(mFriendArr);
						friendList.setRefreshSuccess("加载成功"); // 通知加载成功
						int size = mFriendArr.size();
						if (size >= nums)
							friendList.startLoadMore(); // 开启LoadingMore功能
					} else {
						friendList.setRefreshFail("获取数据为空!");
					}
				} else {
					friendList.setRefreshFail(fb.msg);
				}
			} else {
				FriendBean fb = (FriendBean) obj;
				if (fb.friend != null && fb.result == 0) {
					List<Friend> arr = fb.friend;
					if (ValidateUtils.isArr(arr)) {
						mFriendAdapter.loadMore(arr);
						friendList.setLoadMoreSuccess();
					} else {
						friendList.stopLoadMore();
					}
				} else {
					friendList.stopLoadMore();
				}
			}
		} else if (obj instanceof CircleBean) {
			if (isRefresh) {
				CircleBean fb = (CircleBean) obj;
				if (fb.result == 0 && fb.circle != null) {
					mCircleArr = fb.circle;
					if (ValidateUtils.isArr(mCircleArr) && index == 1) {
						mCircleAdapter.refresh(mCircleArr);
						friendList.setRefreshSuccess("加载成功"); // 通知加载成功
						int size = mCircleArr.size();
						if (size >= nums)
							friendList.startLoadMore(); // 开启LoadingMore功能
					} else {
						friendList.setRefreshFail("获取数据为空!");
					}
				} else {
					friendList.setRefreshFail(fb.msg);
				}
			} else {
				CircleBean fb = (CircleBean) obj;
				if (fb.circle != null && fb.result == 1) {
					List<Circle> arr = fb.circle;
					if (ValidateUtils.isArr(arr)) {
						mCircleAdapter.loadMore(arr);
						friendList.setLoadMoreSuccess();
					} else {
						friendList.stopLoadMore();
					}
				} else {
					friendList.stopLoadMore();
				}
			}
		} else {
			friendList.stopLoadMore();
		}
	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);

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
		case R.id.publish:
			DialogUtils.instance.publish(getActivity());
			break;
		case R.id.tabFriendSearchL:
			DialogUtils.instance.addUser(getActivity(), mFriendArr);
			break;
		}
	}

	@Override
	public void onItemClick(ZrcListView parent, View view, int position, long id) {

	}
}
