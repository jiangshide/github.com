package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserComment implements Serializable{

	private static final long serialVersionUID = 4718146640607801393L;

	@SerializedName("id")
	public int id;
	@SerializedName("commentId")
	public String commentId;
	@SerializedName("head")
	public String head;
	@SerializedName("msg")
	public String msg;
	@SerializedName("img")
	public String img;
	@SerializedName("createTime")
	public long createTime;
	@SerializedName("updateTime")
	public long updateTime;
}
