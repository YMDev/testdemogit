package mobile.a3tech.com.a3tech.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

/**
 * Created by SPIA on 13/04/2017.
 */

public class ImagesStuffs {


    public static Bitmap getProfileDefaultPicture(Context context, String name){
        final LetterTileProvider tileProvider = new LetterTileProvider(context);
        final Bitmap letterTile = tileProvider.getLetterTile(name, name, 88, 88);
        return letterTile;
    }
    public static  byte[] bitMapToBytes(Bitmap photo){
        if(photo != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        }
        return null;
    }

    public static Drawable bitMapToDrawable(Resources res, Bitmap photo){
        Drawable image = new BitmapDrawable(res, photo);
        return image;
    }

    public  static Bitmap bytesToBitmap(byte[] bitmapdata){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        return bitmap;
    }

    public static Drawable bytesToDrawable(byte[] bytes){
        return new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

    }
    public static Bitmap loadImageFromStorage(String path, String picName)
    {
        try {
            File f=new File(path, picName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static void deleteImageFromStorage(String path, String picName)
    {

        try {
            File f=new File(path, picName);
            if(f != null){
                f.delete();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void deleteRecordFromStorage(String path)
    {

        try {
            File f=new File(path);
            if(f != null){
                f.delete();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void scaleImage(ImageView view, Resources rs) throws NoSuchElementException  {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable

        }

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(250, rs);


        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;


        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);


        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        if( view.getLayoutParams() instanceof  RelativeLayout.LayoutParams){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.width = width;
            params.height = height;
            view.setLayoutParams(params);
        }else if(view.getLayoutParams() instanceof  LinearLayout.LayoutParams){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.width = width;
            params.height = height;
            view.setLayoutParams(params);
        }


    }

    private static int dpToPx(int dp, Resources rs) {
        float density = rs.getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    public static int ContrastColor(int color)
    {
        int d = 0;
        //if (red*0.299 + green*0.587 + blue*0.114) > 186 use #000000 else use #ffffff
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - ( 0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color))/255;

        if (a < 0.5)
            //d = 0; // bright colors - black font
            return Color.BLACK;
        else
            //d = 255; // dark colors - white font
            return Color.WHITE;
        //	return  Color.argb(d,d,d,d);
    }

    public static Bitmap compressImages(Bitmap bitmap, Resources rs){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) 800) / width;
        float scaleHeight = ((float) 600) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
        }

    public static int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }
}
