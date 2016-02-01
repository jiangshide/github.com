package com.zdb.android.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.utils.HttpUtils.OnResourceListener;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.android.utils.SharedUtils;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.activity.LoginActivity;
import com.zdb.android.activity.MoreSetActivity;
import com.zdb.android.activity.UserActivity;
import com.zdb.android.base.BaseFragment;
import com.zdb.android.bean.User;
import com.zdb.android.utils.Constant;
import com.zdb.android.view.CircleImageView;

public class MeFragment extends BaseFragment implements OnClickListener,
		OnResourceListener {

	@ViewInject(R.id.left)
	private ImageView left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.right)
	private ImageView goods;
	@ViewInject(R.id.meSet)
	private LinearLayout meSet;

	@ViewInject(R.id.headMe)
	private CircleImageView headMe;

	private User u;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view(inflater, R.layout.tab_me);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ViewUtils.inject(this);
		initTop();
	}

	@Override
	public void onResume() {
		super.onResume();
		initContent();
	}

	private void initContent() {
		headMe.setOnClickListener(this);
		String headImg = null;
		u = (User) SharedUtils.instance.get(getActivity(), true,
				Constant.JSON_USER, User.class);
		if (u == null)
			return;
		headImg = u.head;
		LogUtils.e("-----------headImg:" + headImg);
		if (!TextUtils.isEmpty(headImg)) {
			ImageLoader.getInstance().displayImage(headImg, headMe);
		}
	}

	private void initTop() {
		title.setText("我的");
		left.setVisibility(View.GONE);
		goods.setVisibility(View.VISIBLE);
		goods.setImageResource(R.drawable.msg);
		goods.setOnClickListener(this);
		meSet.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.right:
			break;
		case R.id.headMe:
			if (u == null) {
				toPage(LoginActivity.class, false);
			} else {
				toPage(UserActivity.class, false);
			}
			break;
		case R.id.meSet:
			toPage(MoreSetActivity.class, false);
			break;
		}
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
