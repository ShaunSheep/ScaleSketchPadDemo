package sketchpad.jackyoung.com.scalesketchpaddemo3.workbox;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 炒饭 on 2017/8/13
 */
public class BitmapUtils {


    public static void saveBitmap(Bitmap bitmap, String fileName, Context context){

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String path=getSavePath(context)+ File.separator+fileName+"_"+timeStamp+".jpg";

        try {
            FileOutputStream fileOutputStream=new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public static File getSavePath(Context context){
        if(isExternalStorageWritable()){
            return Environment.getExternalStorageDirectory();
        }
        return context.getCacheDir();
    }

    public static Bitmap getBitmapPathFromData(Intent intent, Context context){

        Uri uri=intent.getData();
        String[] filePathColumes={MediaStore.Images.Media.DATA};

        Cursor cursor=context.getContentResolver().query(uri,filePathColumes,null,null,null);
        cursor.moveToFirst();

        int columnIndex=cursor.getColumnIndex(filePathColumes[0]);
        String picturePath=cursor.getString(columnIndex);

        return decodeSampledBitmapFromResource(picturePath,720,720);
    }


    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
