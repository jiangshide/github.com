package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Friend implements Serializable {

	private static final long serialVersionUID = 898372616421054574L;
	
	@SerializedName("id")
	public int id;

	@SerializedName("head")
	public String head;

	@SerializedName("name")
	public String name;

	@SerializedName("type")
	public String type;

	@SerializedName("sign")
	public String sign;

	@SerializedName("msgNum")
	public int msgNum;

	@SerializedName("createTime")
	public long createTime;

	@SerializedName("updateTime")
	public long updateTime;
}
