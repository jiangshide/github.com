package com.zdb.android.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zdb.android.view.StyleButton;

public class CusView {

	public static Button addBtn(Context c, String name, View v, int txtColor) {
		StyleButton lable = new StyleButton(c);
		lable.setText(name);
		lable.setRadius(15);
		// if (txtColor == -1)
		// lable.setTextNormalPressedcolor(
		// Color.parseColor(RandomUtils.getColor(c)),
		// Color.parseColor(RandomUtils.getColor(c)));
		// else
		lable.setTextNormalPressedcolor(txtColor, txtColor);
		lable.setBgNormalPressedcolor(
				Color.parseColor(RandomUtils.getColor(c)),
				Color.parseColor(RandomUtils.getColor(c)));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		lable.setPadding(10, 0, 10, 0);
		params.setMargins(10, 0, 10, 0);
		lable.setLayoutParams(params);
		((ViewGroup) v).addView(lable);
		return lable;
	}
}
