package mobile.a3tech.com.a3tech.adapter;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.Constant;

public class FullScreenImageAdapter extends PagerAdapter {
	private Activity _activity;
	private ArrayList<String> _imagePaths;
	private int height;
	private ImageLoader imageLoader;
	private LayoutInflater inflater;
	DisplayImageOptions options;
	private int width;

	public FullScreenImageAdapter(Activity activity,
			ArrayList<String> imagePaths, int width, int height) {
		this.options = new Builder().cacheInMemory().cacheOnDisc().build();
		this._activity = activity;
		this._imagePaths = imagePaths;
		this.imageLoader = ImageLoader.getInstance();
		this.width = width;
		this.height = height;
	}

	public int getCount() {
		return this._imagePaths.size();
	}

	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	public Object instantiateItem(ViewGroup container, int position) {
		this.inflater = (LayoutInflater) this._activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = this.inflater.inflate(
				R.layout.layout_fullscreen_image, container, false);
		ImageButton btnClose = (ImageButton) viewLayout
				.findViewById(R.id.btnClose);
		this.imageLoader.displayImage(
				new StringBuilder(Constant.CHECK_EDU_PIECES_URL).append(
						(String) this._imagePaths.get(position)).toString(),
				(ImageView) viewLayout.findViewById(R.id.imgDisplay),
				this.options);
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_activity.finish();

			}
		});
		((ViewPager) container).addView(viewLayout);
		return viewLayout;
	}

	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((RelativeLayout) object);
	}
}
