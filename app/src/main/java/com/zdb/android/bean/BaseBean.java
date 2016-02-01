package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class BaseBean extends Result {

	private static final long serialVersionUID = -985647275441074331L;

	@SerializedName("res")
	public Response res;

	public class Response implements Serializable {

		private static final long serialVersionUID = 1872641981051494751L;

		@SerializedName("deviceInfo")
		public DeviceBean deviceInfo;
		@SerializedName("locationInfo")
		public LocationBean locationInfo;
	}
}
