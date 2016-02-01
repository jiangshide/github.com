package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class User implements Serializable{

	private static final long serialVersionUID = -7077143184411759559L;

	@SerializedName("id")
	public int id;
	@SerializedName("userId")
	public String userId;
	@SerializedName("name")
	public String name;
	@SerializedName("nickName")
	public String nickName;
	@SerializedName("password")
	public String password;
	@SerializedName("realName")
	public String realName;
	@SerializedName("head")
	public String head;
	@SerializedName("userSign")
	public String userSign;
	@SerializedName("fansNum")
	public int fansNum;
	@SerializedName("attentioned")
	public int attentioned;
	@SerializedName("sex")
	public int sex;
	@SerializedName("age")
	public int age;
	@SerializedName("birthday")
	public String birthday;
	@SerializedName("education")
	public String education;
	@SerializedName("nation")
	public String nation;
	@SerializedName("idcard")
	public String idcard;
	@SerializedName("tel")
	public String tel;
	@SerializedName("mobile")
	public String mobile;
	@SerializedName("birthdayArr")
	public String birthdayArr;
	@SerializedName("mailArr")
	public String mailArr;
	@SerializedName("currArr")
	public String currArr;
	@SerializedName("latitude")
	public String latitude;
	@SerializedName("longitude")
	public String longitude;
	@SerializedName("income")
	public String income;
	@SerializedName("hobby")
	public String hobby;
	@SerializedName("special")
	public String special;
	@SerializedName("token")
	public String token;
	@SerializedName("identifyCode")
	public String identifyCode;
	@SerializedName("createTime")
	public long createTime;
	@SerializedName("updateTime")
	public long updateTime;
}
