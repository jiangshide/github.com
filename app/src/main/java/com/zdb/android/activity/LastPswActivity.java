package com.zdb.android.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.utils.AnimUtils;
import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.JException;
import com.android.utils.LogUtils;
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

public class LastPswActivity extends BaseActivity implements OnClickListener {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last_psw);
		ViewUtils.inject(this);
		initTop();
	}

	private void initTop() {
		left.setVisibility(View.VISIBLE);
		right.setVisibility(View.GONE);
		title.setText("找回密码");
		left.setOnClickListener(this);
		left.setImageResource(R.drawable.btn_back);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		}
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

	public void submit(View v) {
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
		u.password = pswStr;
		u.identifyCode = identifyCodeStr;
		String userStr = new Gson().toJson(u);
		map.put(net.JSON, userStr);
		netProgress(HTTP_MOTHED.POST, map, UserBean.class, Constant.UPDATE,
				true);
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
			ToastUtils.instance.toast(this, ub.msg);
		}
	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);
		ToastUtils.instance.toast(this, err.msg);
	}

}
