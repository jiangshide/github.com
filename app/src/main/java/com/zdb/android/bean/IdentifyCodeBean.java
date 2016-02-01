package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class IdentifyCodeBean extends Result implements Serializable {

	private static final long serialVersionUID = -6353722977312923092L;

	@SerializedName("res")
	public Response res;
	
	public class Response {
		@SerializedName("identifyCode")
		public String identifyCode;
	}
}
