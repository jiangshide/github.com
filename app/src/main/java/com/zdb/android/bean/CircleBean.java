package com.zdb.android.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CircleBean extends Result {

	private static final long serialVersionUID = -2017588791347353365L;

	@SerializedName("res")
	public List<Circle> circle;
}
