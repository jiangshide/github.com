package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserTopic implements Serializable{

	private static final long serialVersionUID = 796690507139816800L;

	@SerializedName("id")
	public int id;
	@SerializedName("topicId")
	public String topicId;
	@SerializedName("likeNum")
	public int likeNum;
	@SerializedName("img")
	public String img;
	@SerializedName("des")
	public String des;
	@SerializedName("createTime")
	public long createTime;
	@SerializedName("updateTime")
	public long updateTime;
}
