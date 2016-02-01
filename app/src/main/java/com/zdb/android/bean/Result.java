package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Result implements Serializable {

	private static final long serialVersionUID = 2122875449706390095L;

	@SerializedName("result")
	public int result;
	@SerializedName("version")
	public String version;
	@SerializedName("msg")
	public String msg;
	@SerializedName("time")
	public long time;
}
