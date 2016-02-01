package com.zdb.android.utils;

import java.util.Random;

import android.content.Context;

import com.zdb.android.R;

public class RandomUtils {

	public static String getColor(Context context) {
		String[] colorArr = context.getResources().getStringArray(R.array.colorArr);
		return colorArr[new Random().nextInt(colorArr.length)];
	}
}
