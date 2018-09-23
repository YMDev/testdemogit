package mobile.a3tech.com.a3tech.capturephoto.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import mobile.a3tech.com.a3tech.R;

public class CapturePhoto {
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    public static final int PICK_IMAGE = 2;
    public static final int SHOT_IMAGE = 1;
    private int actionCode;
    private Activity activity;
    private AlbumStorageDirFactory mAlbumStorageDirFactory;
    private String mCurrentPhotoPath;

    public CapturePhoto(Activity activity) {
        this.mAlbumStorageDirFactory = null;
        this.activity = activity;
        if (VERSION.SDK_INT >= 8) {
            this.mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            this.mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    public int getActionCode() {
        return this.actionCode;
    }

    public void dispatchTakePictureIntent(int actionCode, int requestCode) {
        Intent takePictureIntent = null;
        this.actionCode = actionCode;
        switch (actionCode) {
            case SHOT_IMAGE /*1*/:
                takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                try {
                    File f = setUpPhotoFile();
                    this.mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra("output", Uri.fromFile(f));
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    this.mCurrentPhotoPath = null;
                    break;
                }
            case PICK_IMAGE /*2*/:
                takePictureIntent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
                break;
        }
        if (takePictureIntent != null) {
            this.activity.startActivityForResult(takePictureIntent, requestCode);
        }
    }

    public Bitmap handleCameraPhoto(ImageView imageView) {
        if (this.mCurrentPhotoPath == null) {
            return null;
        }
        Options bmOptions = new Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPreferredConfig = Config.RGB_565;
        bmOptions.inDither = true;
        Bitmap bmp = BitmapFactory.decodeFile(this.mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(ScalingUtilities.createScaledBitmap(bmp, imageView.getWidth(), imageView.getHeight(), ScalingUtilities.ScalingLogic.CROP, ScalingUtilities.getFileOrientation(this.mCurrentPhotoPath)));
        imageView.setVisibility(View.VISIBLE);
        galleryAddPic();
        this.mCurrentPhotoPath = null;
        return bmp;
    }

    public Bitmap handleMediaPhoto(Uri targetUri, ImageView imageView) {
        Bitmap bmp = null;
        try {
            bmp = Media.getBitmap(this.activity.getContentResolver(), targetUri);
            imageView.setImageBitmap(ScalingUtilities.createScaledBitmap(bmp, imageView.getWidth(), imageView.getHeight(), ScalingUtilities.ScalingLogic.CROP, ScalingUtilities.getGalleryOrientation(this.activity, targetUri)));
            imageView.setVisibility(View.VISIBLE);
            return bmp;
        } catch (Exception e) {
            return bmp;
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        mediaScanIntent.setData(Uri.fromFile(new File(this.mCurrentPhotoPath)));
        this.activity.sendBroadcast(mediaScanIntent);
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        this.mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile() throws IOException {
        return File.createTempFile(new StringBuilder(JPEG_FILE_PREFIX).append(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())).append("_").toString(), JPEG_FILE_SUFFIX, getAlbumDir());
    }

    private String getAlbumName() {
        return this.activity.getString(R.string.album_name);
    }

    private File getAlbumDir() {
        File storageDir = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            storageDir = this.mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());
            if (!(storageDir == null || storageDir.mkdirs() || storageDir.exists())) {
                Log.d("CameraSample", "failed to create directory");
                return null;
            }
        }
        Log.v(this.activity.getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        return storageDir;
    }
}
