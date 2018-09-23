package mobile.a3tech.com.a3tech.images;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.org.objectweb.asm.Opcodes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.games.multiplayer.Participant;

public class Util {
    public static final int MAX_IMAGE_SIZE = 102400;

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(contentUri, new String[]{"_data"}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            String string = cursor.getString(column_index);
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static int getImageRotation(String path) {
        try {
            switch (new ExifInterface(path).getAttributeInt("Orientation", 1)) {
                case Participant.STATUS_DECLINED /*3*/:
                    return Opcodes.GETFIELD;
                case Participant.STATUS_UNRESPONSIVE /*6*/:
                    return 90;
                case 8 /*8*/:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            return 0;
        }
    }

    public static void deleteTempImage(String name) {
        try {
            File file = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/.KhodaraTempImages/").append(name).append(".jpg").toString());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            Log.e("TAG", "cannot save the temp bitmap ");
        }
    }

    public static String saveImage(Bitmap bmp, String name) {
        try {
            bmp = scaleBitmap(bmp, MAX_IMAGE_SIZE);
            File folder = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/.KhodaraTempImages").toString());
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String fname = new StringBuilder(String.valueOf(name)).append(".jpg").toString();
            FileOutputStream out = new FileOutputStream(new File(folder, fname));
            bmp.compress(CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            return fname;
        } catch (Exception e) {
            Log.e("TAG", "cannot save the bitmap ");
            return null;
        }
    }

    public static int getImageByteSize(Bitmap bmp) {
        try {
            File folder = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/.KhodaraTempImages").toString());
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(folder, System.currentTimeMillis() + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            int size = Integer.parseInt(String.valueOf(file.length()));
            file.delete();
            Log.e("TAG", "size image =" + size);
            return size;
        } catch (Exception e) {
        	e.printStackTrace();
            return 0;
        }
    }

    public static Bitmap getBitmap(String path, int reqByteSize) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        if (options.outWidth > 0) {
            Bitmap bmp1 = getScaledBitmap(path, options, 1000, (float) options.outWidth, (float) options.outHeight);
            if (getImageByteSize(bmp1) <= reqByteSize) {
                Log.e("TAG", "iteration 1");
                return bmp1;
            }
            bmp1.recycle();
            Log.e("TAG", "iteration 2");
            return getScaledBitmap(path, options, 900, (float) options.outWidth, (float) options.outHeight);
        }
        Log.e("TAG", "getBitmap error, options.outWidth=0");
        return null;
    }

    public static Bitmap scaleBitmap(Bitmap bmp, int reqByteSize) {
        if (getImageByteSize(bmp) <= reqByteSize) {
            return bmp;
        }
        int firstItreationWidth = bmp.getWidth() - (bmp.getWidth() / 6);
        int secondItreationWidth = bmp.getWidth() - (bmp.getWidth() / 3);
        Bitmap bmp1 = Bitmap.createScaledBitmap(bmp, firstItreationWidth, (bmp.getHeight() / bmp.getWidth()) * firstItreationWidth, true);
        if (getImageByteSize(bmp1) <= reqByteSize) {
            return bmp1;
        }
        if (bmp1 != bmp) {
            bmp1.recycle();
        }
        return Bitmap.createScaledBitmap(bmp, secondItreationWidth, (bmp.getHeight() / bmp.getWidth()) * secondItreationWidth, true);
    }

    public static Bitmap getScaledBitmap(String path, Options options, int reqWidth, float bmpWidth, float bmpHeight) {
        options.inSampleSize = calculateInSampleSize(options, reqWidth, (int) (((float) reqWidth) * (bmpHeight / bmpWidth)));
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        Bitmap res = Bitmap.createScaledBitmap(bmp, reqWidth, (int) (((float) reqWidth) * (bmpHeight / bmpWidth)), true);
        if (bmp != res) {
            bmp.recycle();
        }
        return res;
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap rotateBitmap(Bitmap b, int degrees) {
        if (degrees == 0 || b == null) {
            return b;
        }
        Matrix m = new Matrix();
        m.setRotate((float) degrees, ((float) b.getWidth()) / 2.0f, ((float) b.getHeight()) / 2.0f);
        try {
            Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
            if (b == b2) {
                return b;
            }
            b.recycle();
            return b2;
        } catch (OutOfMemoryError e) {
            return b;
        }
    }

    public static Bitmap adjustImageOrientation(String path, Bitmap bitmap) {
        try {
            int rotation = 0;
            switch (new ExifInterface(path).getAttributeInt("Orientation", 1)) {
                case Participant.STATUS_DECLINED /*3*/:
                    rotation = Opcodes.GETFIELD;
                    break;
                case Participant.STATUS_UNRESPONSIVE /*6*/:
                    rotation = 90;
                    break;
                case 8 /*8*/:
                    rotation = 270;
                    break;
            }
            bitmap = rotateBitmap(bitmap, rotation);
        } catch (IOException e) {
        }
        return bitmap;
    }

    public static boolean isConnectedToInternet() {
        try {
            HttpURLConnection urlc = (HttpURLConnection) new URL("http://www.google.com").openConnection();
            urlc.setRequestMethod("GET");
            urlc.setConnectTimeout(6000);
            urlc.setReadTimeout(6000);
            urlc.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isServerReachable(String serverAddress) {
        try {
            HttpURLConnection urlc = (HttpURLConnection) new URL("http://" + serverAddress).openConnection();
            urlc.setRequestMethod("GET");
            urlc.setConnectTimeout(6000);
            urlc.setReadTimeout(6000);
            urlc.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFileExist(String filePath) {
        if (new File(filePath).exists()) {
            return true;
        }
        return false;
    }

    public static boolean downloadAndSaveFile(String downloadUrl, String savePath, String fileName) {
        try {
            InputStream is = new URL(downloadUrl).openConnection().getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 5120);
            File dir = new File(savePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buff = new byte[5120];
            while (true) {
                int len = is.read(buff);
                if (len == -1) {
                    fos.flush();
                    fos.close();
                    is.close();
                    return true;
                }
                fos.write(buff, 0, len);
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static void deleteDirectory(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        dir.delete();
    }
}
