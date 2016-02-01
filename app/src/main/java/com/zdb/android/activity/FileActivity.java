package com.zdb.android.activity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.android.utils.DateUtils;
import com.android.utils.LogUtils;
import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.tools.HttpTools;
import com.google.gson.Gson;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zdb.android.R;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.FileInfo;

public class FileActivity extends BaseActivity {

	@ViewInject(R.id.photo)
	private ImageView photo;
	@ViewInject(R.id.photos)
	private ImageView photos;

	private String newName = "myfiles";
	// 要上传的本地文件路径
	private String uploadFile = "/data/data/com.xzq/htys.mp3";
	// 上传到服务器的指定位置
	private String actionUrl = "http://192.168.10.100:8080/upload";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_file);
		ViewUtils.inject(this);
	}

	private File outputImage;
	private Uri imageUri; // 图片路径
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;

	public void photo(View v) {
		// 创建File对象用于存储拍照的图片 SD卡根目录
		// File outputImage = new
		// File(Environment.getExternalStorageDirectory(),test.jpg);
		// 存储至DCIM文件夹
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		outputImage = new File(path, DateUtils.getDate() + ".jpg");
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
			outputImage.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 将File对象转换为Uri并启动照相程序
		imageUri = Uri.fromFile(outputImage);
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); // 照相
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 指定图片输出地址
		startActivityForResult(intent, TAKE_PHOTO); // 启动照相
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case TAKE_PHOTO:
			Intent i = new Intent("com.android.camera.action.CROP");
			i.setDataAndType(imageUri, "image/*");
			i.putExtra("scale", true);
			i.putExtra("aspectX", 1);
			i.putExtra("aspectY", 1);
			// 设置裁剪图片宽高
			i.putExtra("outputX", 340);
			i.putExtra("outputY", 340);
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			// 广播刷新相册
			Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			intentBc.setData(imageUri);
			this.sendBroadcast(intentBc);
			startActivityForResult(i, CROP_PHOTO);
			break;
		case CROP_PHOTO:
			Bitmap bitmap;
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(imageUri));
				photo.setImageBitmap(bitmap);
				uploadFile = outputImage.getAbsolutePath();
				LogUtils.e("---------------uploadFile:", uploadFile);
			} catch (FileNotFoundException e) {
				LogUtils.e(e, e.getMessage());
			}
			break;
		}
	}

	public void upload(View v) {
		HttpTools ht = new HttpTools(this);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("file", outputImage);
		ht.upload(actionUrl, params, new HttpCallback() {
			@Override
			public void onStart() {
				LogUtils.e("-----------onStart()");
			}

			@Override
			public void onResult(String arg0) {
				LogUtils.e("-------------arg0:" + arg0);
				FileInfo fi = new Gson().fromJson(arg0, FileInfo.class);
				String url = fi.url;
				LogUtils.e("-----------url:"+url);
				ImageLoader.getInstance().displayImage(url,
						photos);
			}

			@Override
			public void onLoading(long arg0, long arg1) {
				LogUtils.e("-----------arg0:" + arg0 + " | arg1:" + arg1);
			}

			@Override
			public void onFinish() {
				LogUtils.e("------------onFinish()");
			}

			@Override
			public void onError(Exception arg0) {
				LogUtils.e("-------------------arg0:" + arg0);
			}

			@Override
			public void onCancelled() {
				LogUtils.e("---------------onCancelled");
			}
		});
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// uploadFile();
		// }
		// }).start();
		// RequestParams params = new RequestParams();
		// params.addHeader("name", "value");
		// params.addQueryStringParameter("name", "value");
		//
		// // 只包含字符串参数时默认使用BodyParamsEntity，
		// // 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
		// params.addBodyParameter("name", "value");
		//
		// // 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
		// //
		// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
		// // 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
		// //
		// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
		// // 例如发送json参数：params.setBodyEntity(new
		// StringEntity(jsonStr,charset));
		// params.addBodyParameter("file", outputImage);
		// HttpUtils http = new HttpUtils();
		// http.send(HttpRequest.HttpMethod.POST, actionUrl, params,
		// new RequestCallBack<String>() {
		//
		// @Override
		// public void onStart() {
		// LogUtils.e("--------------onStart()");
		// }
		//
		// @Override
		// public void onLoading(long total, long current,
		// boolean isUploading) {
		// LogUtils.e("-------------total:"+current);
		// }
		//
		// @Override
		// public void onSuccess(ResponseInfo<String> responseInfo) {
		// LogUtils.e("------------responseInfo:",responseInfo.result);
		// }
		//
		// @Override
		// public void onFailure(
		// com.jsd.android.utils.exception.HttpException arg0,
		// String arg1) {
		// LogUtils.e("------------arg0:"+arg0+" | "+arg1);
		// }
		//
		// });
	}

	private void uploadFile() {
		String end = "/r/n";
		String Hyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设定传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设定DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(Hyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=/file1/;filename=/" + newName + "/" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(uploadFile);
			/* 设定每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据到缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将数据写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(Hyphens + boundary + Hyphens + end);
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			LogUtils.e("-------------ok!");
			ds.close();
		} catch (Exception e) {
			LogUtils.e(e, e.getMessage());
		}
	}

}
