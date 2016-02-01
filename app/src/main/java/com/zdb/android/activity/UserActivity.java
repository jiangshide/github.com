package com.zdb.android.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.android.utils.SharedUtils;
import com.android.utils.ToastUtils;
import com.google.gson.Gson;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.FileBean;
import com.zdb.android.bean.User;
import com.zdb.android.bean.UserBean;
import com.zdb.android.utils.Constant;
import com.zdb.android.view.CircleImageView;
import com.zdb.android.view.WorkDialog;
import com.zdb.android.view.wheek.WheelView.OnWheelListener;

public class UserActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {

	@ViewInject(R.id.left)
	private ImageView left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.right)
	protected ImageView right;

	@ViewInject(R.id.pHeadImg)
	private CircleImageView pHeadImg;
	@ViewInject(R.id.pNickName)
	private EditText pNickName;
	@ViewInject(R.id.pRealName)
	private EditText pRealName;
	@ViewInject(R.id.pUserSign)
	private EditText pUserSign;
	@ViewInject(R.id.pSex)
	private RadioGroup pSex;
	@ViewInject(R.id.pAge)
	private Button pAge;
	@ViewInject(R.id.pBirthday)
	private Button pBirthday;
	@ViewInject(R.id.pEducation)
	private Button pEducation;
	@ViewInject(R.id.pNation)
	private Button pNation;
	@ViewInject(R.id.pTel)
	private EditText pTel;
	@ViewInject(R.id.pMobile)
	private EditText pMobile;
	@ViewInject(R.id.pBirthdayArr)
	private EditText pBirthdayArr;
	@ViewInject(R.id.pMailArr)
	private EditText pMailArr;
	@ViewInject(R.id.pIncome)
	private EditText pIncome;
	@ViewInject(R.id.pHobby)
	private EditText pHobby;

	private int pSexl;
	private String headImg;

	private WorkDialog mWorkDialog;
	private User u;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		ViewUtils.inject(this);
		initTop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		headImg = SharedUtils.instance.getString(this, "headImg");
		if (!TextUtils.isEmpty(headImg)) {
			ImageLoader.getInstance().displayImage(headImg, pHeadImg);
		}
	}

	private void initTop() {
		left.setVisibility(View.VISIBLE);
		right.setVisibility(View.VISIBLE);
		title.setText("完善资料");
		left.setImageResource(R.drawable.btn_back);
		right.setImageResource(R.drawable.btn_back_sel);
		setListener();
		u = (User) SharedUtils.instance.get(this, true, Constant.JSON_USER,
				User.class);
		initData(u);
	}

	private void initData(User u) {
		headImg = u.head;
		LogUtils.e("-----------------headImg.length:" + headImg.length());
		if (!TextUtils.isEmpty(headImg)) {
			ImageLoader.getInstance().displayImage(headImg, pHeadImg);
		}
		String nickName = u.nickName;
		pNickName.setText(TextUtils.isEmpty(nickName) ? "" : nickName);
		String realName = u.realName;
		pRealName.setText(TextUtils.isEmpty(realName) ? "" : realName);
		String userSign = u.userSign;
		pUserSign.setText(TextUtils.isEmpty(userSign) ? "" : userSign);
		pSexl = u.sex;
		if (pSexl == 0) {
			((RadioButton) pSex.getChildAt(0)).setChecked(true);
			((RadioButton) pSex.getChildAt(1)).setChecked(false);
		} else {
			((RadioButton) pSex.getChildAt(1)).setChecked(true);
			((RadioButton) pSex.getChildAt(0)).setChecked(false);
		}
		int age = u.age;
		if (age > 9 && age < 100)
			pAge.setText(age + "");
		String birtday = u.birthday;
		pBirthday.setText(TextUtils.isEmpty(birtday) ? "" : birtday);
		String education = u.education;
		pEducation.setText(TextUtils.isEmpty(education) ? "" : education);
		String nation = u.nation;
		pNation.setText(TextUtils.isEmpty(nation) ? "" : nation);
		String tel = u.tel;
		pTel.setText(TextUtils.isEmpty(tel) ? "" : tel);
		String mobile = u.mobile;
		pMobile.setText(TextUtils.isEmpty(mobile) ? "" : mobile);
		String birthdayArr = u.birthdayArr;
		pBirthdayArr.setText(TextUtils.isEmpty(birthdayArr) ? "" : birthdayArr);
		String mailArr = u.mailArr;
		pMailArr.setText(TextUtils.isEmpty(mailArr) ? "" : mailArr);
		String income = u.income;
		pIncome.setText(TextUtils.isEmpty(income) ? "" : income);
		String hobby = u.hobby;
		pHobby.setText(TextUtils.isEmpty(hobby) ? "" : hobby);

	}

	private void setListener() {
		left.setOnClickListener(this);
		pSex.setOnCheckedChangeListener(this);
	}

	public void pHeadImg(View v) {
		Bundle b = new Bundle();
		b.putString("url", "headImg");
		toPage(UploadActivity.class, b, false);
	}

	public void pAge(final View v) {
		cancelDialog();
		mWorkDialog = new WorkDialog(this, "年龄", Constant.getAge(),
				new OnWheelListener() {
					@Override
					public void onWheelResult(int index, int pos, String result) {
						((Button) v).setText(result);
					}
				}, 0);
		mWorkDialog.show();
	}

	private String year, month, day;

	public void pBirthday(final View v) {
		cancelDialog();
		mWorkDialog = new WorkDialog(this, "生日", Constant.pBirthday,
				new OnWheelListener() {
					@Override
					public void onWheelResult(int index, int pos, String result) {
						StringBuffer sb = new StringBuffer();
						switch (index) {
						case 0:
							year = result;
							break;
						case 1:
							month = result;
							break;
						case 2:
							day = result;
							break;
						}
						if (!TextUtils.isEmpty(year)) {
							sb.append(year);
						}
						if (!TextUtils.isEmpty(month)) {
							sb.append("-").append(month);
						}
						if (!TextUtils.isEmpty(day)) {
							sb.append("-").append(day);
						}
						((Button) v).setText(sb.toString());

					}
				}, -1);
		mWorkDialog.show();
	}

	public void pEducation(final View v) {
		cancelDialog();
		mWorkDialog = new WorkDialog(this, "学历", Constant.educationArr,
				new OnWheelListener() {
					@Override
					public void onWheelResult(int index, int pos, String result) {
						((Button) v).setText(result);
					}
				}, 0);
		mWorkDialog.show();
	}

	public void pNation(final View v) {
		cancelDialog();
		mWorkDialog = new WorkDialog(this, "民族", Constant.pNationArr,
				new OnWheelListener() {
					@Override
					public void onWheelResult(int index, int pos, String result) {
						((Button) v).setText(result);
					}
				}, 0);
		mWorkDialog.show();
	}

	private void cancelDialog() {
		if (mWorkDialog != null) {
			mWorkDialog.cancel();
			mWorkDialog = null;
		}
	}

	public void prefect(View v) {
		String pNickNameStr = pNickName.getEditableText().toString().trim();
		String pRealNameStr = pRealName.getEditableText().toString().trim();
		String pUserSignStr = pUserSign.getEditableText().toString().trim();
		String pAgeStr = pAge.getText().toString().trim();
		String pBirthdayStr = pBirthday.getText().toString().trim();
		String pEducationStr = pEducation.getText().toString().trim();
		String pNationStr = pNation.getText().toString().trim();
		String pTelStr = pTel.getEditableText().toString().trim();
		String pMobileStr = pMobile.getEditableText().toString().trim();
		String pBirthdayArrStr = pBirthdayArr.getEditableText().toString()
				.trim();
		String pMailArrStr = pMailArr.getEditableText().toString().trim();
		String pIncomeStr = pIncome.getEditableText().toString().trim();
		String pHobbyStr = pHobby.getEditableText().toString().trim();

		HashMap<String, Object> map = new HashMap<String, Object>();
		if (!TextUtils.isEmpty(headImg))
			u.head = headImg;
		u.nickName = pNickNameStr;
		u.realName = pRealNameStr;
		u.userSign = pUserSignStr;
		u.sex = pSexl;
		u.age = Integer.parseInt(pAgeStr);
		u.birthday = pBirthdayStr;
		u.education = pEducationStr;
		u.nation = pNationStr;
		u.tel = pTelStr;
		u.mobile = pMobileStr;
		u.birthdayArr = pBirthdayArrStr;
		u.mailArr = pMailArrStr;
		u.income = pIncomeStr;
		u.hobby = pHobbyStr;
		String json = new Gson().toJson(u);
		LogUtils.e("------------json:" + json);
		map.put(net.JSON, json);
		netProgress(HTTP_MOTHED.POST, map, UserBean.class,
				Constant.USER_UPDATE, true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		case R.id.right:
			// DialogUtils.instance.addUser(a, arr);
			break;
		}
	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		LogUtils.e("-----------obj:" + obj);
		if (obj instanceof UserBean) {
			UserBean ub = (UserBean) obj;
			if (ub.result == 0 && ub.user != null) {
				User u = ub.user;
				SharedUtils.save(this, u, Constant.JSON_USER);
				finish();
			} else {
				ToastUtils.instance.toast(this, ub.msg);
			}
		} else if (obj instanceof FileBean) {

		}
	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);
		ToastUtils.instance.toast(this, err.msg);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.pSex0:
			pSexl = 0;
			break;
		case R.id.pSex1:
			pSexl = 1;
			break;
		}
	}
}
