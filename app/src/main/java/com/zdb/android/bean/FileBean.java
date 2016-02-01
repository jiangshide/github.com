package com.zdb.android.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.android.utils.LogUtils;
import com.google.gson.annotations.SerializedName;

public class FileBean extends Result {

	private static final long serialVersionUID = -686974224624514238L;

	@SerializedName("fileInfo")
	public Info fileInfo;

	public class Info {
		@SerializedName("fileId")
		public int fileId;
		@SerializedName("fileName")
		public String fileName;
		@SerializedName("filePath")
		public String filePath;
		@SerializedName("url")
		public String url;
		@SerializedName("content")
		public String content;
		@SerializedName("size")
		public int size;
		@SerializedName("fileType")
		public int fileType;
	}

	public Info getInfo() {
		return new Info();
	}

	public static String getImgStr(File f) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(f);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		// BASE64Encoder encoder = new BASE64Encoder();
		return Base64.encodeToString(data, Base64.DEFAULT);// 返回Base64编码过的字节数组字符串
	}

	// base64字符串转化成图片
	public static Bitmap getStrImg(String filePath, String imgStr) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		try {
			// Base64解码
			byte[] b = Base64.decode(imgStr, Base64.DEFAULT);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			LogUtils.e("-------------filePath:"+filePath);
			OutputStream out = new FileOutputStream(filePath);
			out.write(b);
			out.flush();
			out.close();

			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} catch (Exception e) {
			LogUtils.e(e, e.getMessage());
			return null;
		}
	}
}
