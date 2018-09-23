package mobile.a3tech.com.a3tech.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

public class ImageManager {
	private static ImageManager instance;

	public static ImageManager getInstance() {
		if(instance==null)
			instance = new ImageManager();
		return instance;
	}

	public static void setInstance(ImageManager instance) {
		ImageManager.instance = instance;
	}
	
	public String getString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = null;
		try {
			System.gc();
			return Base64.encodeToString(b, 0);
		} catch (Exception e) {
			e.printStackTrace();
			return temp;
		} catch (OutOfMemoryError e2) {
			baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 50, baos);
			return Base64.encodeToString(baos.toByteArray(), 0);
		}
	}

}
