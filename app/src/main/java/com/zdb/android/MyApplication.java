package com.zdb.android;

import com.android.utils.JApplication;
import com.android.utils.NetStateUtils.NetType;
import com.android.volley.ext.tools.HttpTools;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zdb.android.bean.ImageBucket;
import com.zdb.android.utils.AlbumUtils;
import com.zdb.android.utils.LocationUtils;

import java.util.List;

public class MyApplication extends JApplication {

    private NetListener mNetListener;
    private AlbumUtils album = AlbumUtils.instance;
    public List<ImageBucket> mAlbum;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpTools.init(this);
        configImageLoader();
        LocationUtils.instance.init(this);
        album.init(this);
    }

    public void initAlbum(final boolean isFlash) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAlbum = album.getImagesBucketList(isFlash);
            }
        }).start();
    }

    public void clear() {
        LocationUtils.instance.cancel();
    }

    public void registetNet(NetListener nl) {
        this.mNetListener = nl;
    }

    @Override
    public void onDisConnect() {
        super.onDisConnect();
        if (mNetListener != null)
            mNetListener.onNetType(null, false);
    }

    @Override
    public void onConnect(NetType type) {
        super.onConnect(type);
        if (mNetListener != null)
            mNetListener.onNetType(type, true);
    }

    public interface NetListener {
        public void onNetType(NetType type, boolean isNet);
    }

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this.getApplicationContext())
                .defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
}
