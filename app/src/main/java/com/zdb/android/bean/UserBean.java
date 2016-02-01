package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserBean extends Result implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("res")
	public User user;
//	@SerializedName("res")
//	public UserAlbum album;
//	@SerializedName("res")
//	public UserTopic topic;
//	@SerializedName("res")
//	public UserComment comment;
}
