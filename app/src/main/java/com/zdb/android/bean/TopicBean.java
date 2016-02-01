package com.zdb.android.bean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TopicBean extends Result implements Serializable {

	private static final long serialVersionUID = 6445559574379051247L;

	@SerializedName("res")
	public Response res;

	public class Response implements Serializable {
		private static final long serialVersionUID = -1071877592499369434L;
		@SerializedName("friend")
		public List<Topic> topic;
	}

	public class Topic implements Serializable {

		private static final long serialVersionUID = 7065728979473111073L;

		
	}
}
