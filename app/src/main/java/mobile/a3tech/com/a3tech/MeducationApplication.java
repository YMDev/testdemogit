package mobile.a3tech.com.a3tech;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.facebook.FacebookSdk;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MeducationApplication extends Application {
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
		FacebookSdk.sdkInitialize(getApplicationContext());
		
		
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().handleSlowNetwork(true);
		
	}
}
