package mobile.a3tech.com.a3tech.capturephoto.util;

import org.codehaus.jackson.org.objectweb.asm.Opcodes;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.games.multiplayer.Participant;

public class ScalingUtilities {

    public enum ScalingLogic {
        CROP,
        FIT
    }

    public static Bitmap decodeResource(Resources res, int resId, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScalingLogic scalingLogic, int rotate) {
        Matrix matrix = new Matrix();
        matrix.preRotate((float) rotate);
        Bitmap rotatedBitmap = Bitmap.createBitmap(unscaledBitmap, 0, 0, unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), matrix, true);
        Rect srcRect = calculateSrcRect(rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Log.i("srcRect", srcRect.toString());
        Rect dstRect = calculateDstRect(rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Log.i("dstRect", dstRect.toString());
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.RGB_565);
        new Canvas(scaledBitmap).drawBitmap(rotatedBitmap, srcRect, dstRect, new Paint(2));
        return scaledBitmap;
    }

    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.FIT) {
            if (((float) srcWidth) / ((float) srcHeight) > ((float) dstWidth) / ((float) dstHeight)) {
                return srcWidth / dstWidth;
            }
            return srcHeight / dstHeight;
        } else if (((float) srcWidth) / ((float) srcHeight) > ((float) dstWidth) / ((float) dstHeight)) {
            return srcHeight / dstHeight;
        } else {
            return srcWidth / dstWidth;
        }
    }

    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingLogic.CROP) {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
        float dstAspect = ((float) dstWidth) / ((float) dstHeight);
        if (((float) srcWidth) / ((float) srcHeight) > dstAspect) {
            int srcRectWidth = (int) (((float) srcHeight) * dstAspect);
            int srcRectLeft = (srcWidth - srcRectWidth) / 2;
            return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
        }
        int srcRectHeight = (int) (((float) srcWidth) / dstAspect);
        int scrRectTop = (srcHeight - srcRectHeight) / 2;
        return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
    }

    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingLogic.FIT) {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
        float srcAspect = ((float) srcWidth) / ((float) srcHeight);
        if (srcAspect > ((float) dstWidth) / ((float) dstHeight)) {
            return new Rect(0, 0, dstWidth, (int) (((float) dstWidth) / srcAspect));
        }
        return new Rect(0, 0, (int) (((float) dstHeight) * srcAspect), dstHeight);
    }

    public static int getGalleryOrientation(Context context, Uri path) {
        Cursor cursor = context.getContentResolver().query(path, new String[]{"orientation"}, null, null, null);
        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static int getFileOrientation(String path) {
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
        } catch (Exception e) {
            return 0;
        }
    }
}
