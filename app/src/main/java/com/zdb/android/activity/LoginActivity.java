package com.zdb.android.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.AnimUtils;
import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.android.utils.MD5;
import com.android.utils.SharedUtils;
import com.android.utils.ToastUtils;
import com.google.gson.Gson;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.User;
import com.zdb.android.bean.UserBean;
import com.zdb.android.utils.Constant;

public class LoginActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.left)
	private ImageView left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.right)
	protected ImageView right;

	@ViewInject(R.id.name)
	private EditText name;
	@ViewInject(R.id.psw)
	private EditText psw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
		initTop();
	}

	private void initTop() {
		left.setVisibility(View.VISIBLE);
		right.setVisibility(View.GONE);
		left.setOnClickListener(this);
		left.setImageResource(R.drawable.btn_back);
	}

	public void login(View v) {
		String nameStr = name.getEditableText().toString().trim();
		String pswStr = psw.getEditableText().toString().trim();
		LogUtils.e("--------nameStr", nameStr, " | pswStr:", pswStr);
		if (TextUtils.isEmpty(nameStr)) {
			AnimUtils.anim(this, name, R.anim.shake_x,
					getString(R.string.loginNameTips1), getResources()
							.getColor(R.color.red));
			return;
		}
		// if (!ValidateUtils.isMobile(nameStr) &&
		// !ValidateUtils.isEmail(nameStr)) {
		// AnimUtils.anim(this, name, R.anim.shake_x,
		// getString(R.string.loginNameTips2), getResources()
		// .getColor(R.color.red));
		// return;
		// }

		if (TextUtils.isEmpty(pswStr)) {
			AnimUtils.anim(this, psw, R.anim.shake_x,
					getString(R.string.loginPswTips1),
					getResources().getColor(R.color.red));
			return;
		}
		if (pswStr.length() < 6) {
			AnimUtils.anim(this, psw, R.anim.shake_x,
					getString(R.string.loginPswTips2),
					getResources().getColor(R.color.red));
			return;
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		User u = new User();
		u.name = nameStr;
		u.password = MD5.encode(pswStr);
		String user = new Gson().toJson(u);
		map.put(net.JSON, user);
		netProgress(HTTP_MOTHED.POST, map, UserBean.class, Constant.LOGIN, true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		}
	}

	public void regUser(View v) {
		toPage(RegActivity.class, true);
	}

	public void lastPsw(View v) {
		toPage(LastPswActivity.class, true);
	}

	public void bindQQ(View v) {

	}

	public void bindWeixin(View v) {

	}

	public void bindWeibo(View v) {

	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		LogUtils.e("---------obj:" + obj);
		if (obj instanceof UserBean) {
			UserBean ub = (UserBean) obj;
			if (ub.result == 0 && ub.user != null) {
				User u = ub.user;
				SharedUtils.save(this, u, Constant.JSON_USER);
				SharedUtils su = SharedUtils.instance;
				su.putString(this, Constant.USER_ID, u.userId);
				su.putString(this, Constant.TOKEN, u.token);
				finish();
			} else
				ToastUtils.instance.toast(getBaseContext(), ub.msg);
		}
	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);
		ToastUtils.instance.toast(getBaseContext(), err.msg);
	}

}
