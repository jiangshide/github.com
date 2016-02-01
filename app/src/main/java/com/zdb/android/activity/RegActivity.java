package com.zdb.android.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.AnimUtils;
import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.android.utils.MD5;
import com.android.utils.SharedUtils;
import com.android.utils.TimeUtils;
import com.android.utils.ToastUtils;
import com.google.gson.Gson;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.IdentifyCodeBean;
import com.zdb.android.bean.User;
import com.zdb.android.bean.UserBean;
import com.zdb.android.utils.Constant;

public class RegActivity extends BaseActivity implements
		OnCheckedChangeListener, OnClickListener {

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
	@ViewInject(R.id.identifyCode)
	private EditText identifyCode;
	@ViewInject(R.id.spreeImg)
	private CheckBox spreeImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		ViewUtils.inject(this);
		initTop();
	}

	private void initTop() {
		left.setVisibility(View.VISIBLE);
		right.setVisibility(View.GONE);
		title.setText("注册");
		left.setImageResource(R.drawable.btn_back);
		setListener();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		toPage(LoginActivity.class, true);
	}

	private void setListener() {
		left.setOnClickListener(this);
		spreeImg.setOnCheckedChangeListener(this);
	}

	public void sendIdentifyCode(View v) {
		String nameStr = name.getEditableText().toString().trim();
		if (TextUtils.isEmpty(nameStr)) {
			AnimUtils.anim(this, name, R.anim.shake_x,
					getString(R.string.loginNameTips1), getResources()
							.getColor(R.color.red));
			return;
		}
		TimeUtils.instance.countDown(10, getString(R.string.sendIdentifyCode),
				(Button) v);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("username", nameStr);
		netProgress(HTTP_MOTHED.GET, map, IdentifyCodeBean.class,
				Constant.IDENTIFYCODE, false);
	}

	public void reg(View v) {
		String nameStr = name.getEditableText().toString().trim();
		String pswStr = psw.getEditableText().toString().trim();
		String identifyCodeStr = identifyCode.getEditableText().toString()
				.trim();
		if (TextUtils.isEmpty(nameStr)) {
			AnimUtils.anim(this, name, R.anim.shake_x,
					getString(R.string.loginNameTips1), getResources()
							.getColor(R.color.red));
			return;
		}
		if (TextUtils.isEmpty(pswStr)) {
			AnimUtils.anim(this, psw, R.anim.shake_x,
					getString(R.string.loginNameTips1), getResources()
							.getColor(R.color.red));
			return;
		}
		if (TextUtils.isEmpty(identifyCodeStr)) {
			AnimUtils.anim(this, psw, R.anim.shake_x,
					getString(R.string.loginNameTips1), getResources()
							.getColor(R.color.red));
			return;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		User u = new User();
		u.name = nameStr;
		u.password = MD5.encode(pswStr);
		u.identifyCode = identifyCodeStr;
		String userStr = new Gson().toJson(u);
		map.put(net.JSON, userStr);
		netProgress(HTTP_MOTHED.POST, map, UserBean.class, Constant.REG, true);
	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		LogUtils.e("-----------obj:" + obj);
		if (obj instanceof IdentifyCodeBean) {
			IdentifyCodeBean icb = (IdentifyCodeBean) obj;
			if (icb.res != null) {
				String code = icb.res.identifyCode;
				identifyCode.setText(code != null ? code : "");
			}
		} else if (obj instanceof UserBean) {
			UserBean ub = (UserBean) obj;
			if (ub.result == 0 && ub.user != null) {
				User u = ub.user;
				SharedUtils.save(this, u, Constant.JSON_USER);
				SharedUtils su = SharedUtils.instance;
				su.putString(this, Constant.USER_ID, u.userId);
				su.putString(this, Constant.TOKEN, u.token);
				finish();
			}
		}
	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);
		ToastUtils.instance.toast(this, err.msg);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked)
			spreeImg.setBackgroundResource(R.drawable.duihao_3x);
		else
			spreeImg.setBackgroundResource(R.drawable.duihaohui_3x);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			toPage(LoginActivity.class, true);
			break;
		}
	}
}
