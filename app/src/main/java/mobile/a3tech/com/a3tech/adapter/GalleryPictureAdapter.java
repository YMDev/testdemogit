package mobile.a3tech.com.a3tech.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.YoutubeActivity;
import mobile.a3tech.com.a3tech.images.Image;
import mobile.a3tech.com.a3tech.utils.Constant;

public class GalleryPictureAdapter extends BaseAdapter {
	private Context context;
	private final Image[] galleryPictures;
	private GridView gridView;
	public ImageLoader imageLoader;
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc()
			.build();
	private View.OnClickListener showVideoListener = new View.OnClickListener() {
		public void onClick(View v) {
		}
	};

	public GalleryPictureAdapter(Context paramContext,
			Image[] paramArrayOfImage, GridView paramGridView) {
		this.context = paramContext;
		this.galleryPictures = paramArrayOfImage;
		this.imageLoader = ImageLoader.getInstance();
		this.gridView = paramGridView;
	}

	@Override
	public int getCount() {
		return this.galleryPictures.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = (LayoutInflater) this.context
				.getSystemService("layout_inflater");

		if (convertView == null) {
			if (this.galleryPictures[position].getType().equals("v")) {
				convertView = layoutInflater.inflate(R.layout.grid_item_video,
						null);
				ImageView iv_gallery_video = (ImageView) convertView
						.findViewById(R.id.iv_gallery_video);
				TextView localTextView = (TextView) convertView
						.findViewById(R.id.duration);
				RelativeLayout rl_video = (RelativeLayout) convertView
						.findViewById(R.id.rl_video);
				rl_video.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramAnonymousView) {
						String img = galleryPictures[position].getImg();
						Bundle bundle = new Bundle();
						bundle.putString("vid", img);
						Intent mainIntent = new Intent(context,
								YoutubeActivity.class);
						mainIntent.putExtras(bundle);
						context.startActivity(mainIntent);
					}
				});

				localTextView.bringToFront();
				localTextView.setText(this.galleryPictures[position]
						.getDuration());
				this.imageLoader.displayImage(Constant.CHECK_EDU_PICTURES_ASSOCIATIONS_URL
						+ galleryPictures[position].getImg() + ".jpg",
						iv_gallery_video, this.options);
			} else {
				convertView = layoutInflater.inflate(
						R.layout.grid_item_gallery, null);
				ImageView iv_gallery_image = (ImageView) convertView
						.findViewById(R.id.iv_gallery_image);
				this.imageLoader.displayImage(Constant.CHECK_EDU_PICTURES_ASSOCIATIONS_URL
						+ this.galleryPictures[position].getImg(),
						iv_gallery_image, this.options);
			}

		}
		return convertView;
	}

}
