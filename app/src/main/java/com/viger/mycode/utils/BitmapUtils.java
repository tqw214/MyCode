package com.viger.mycode.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileOutputStream;

/**
 * bitmap工具类
 */
public class BitmapUtils {

    /**
     * 通过sdcard对一张图片进行二次采样并返回bitmap
     *
     * @param path
     * @param FACTOR
     *            要设定的像素阀值
     * @return
     */
    public static Bitmap getBitmap(String path, int FACTOR)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 当设置inJustDecodeBounds为true代表只解码bitmap的边框
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // 通过第一次采样将图片的宽和高放置到Options对象中输出给后面使用
        int width = options.outWidth;
        int height = options.outHeight;

        int scaleWidth = width / FACTOR;// 宽的压缩比
        int scaleHeight = height / FACTOR;// 高的压缩比
        int scale = scaleHeight;
        // 哪个边的压缩比大,就选哪个,目的是为了尽可能将高和宽都压缩到500像素以内
        if (scaleWidth > scaleHeight)
        {
            scale = scaleWidth;
        }
//        Log.i("info", "width=" + width + ";height=" + height);
        // 压缩比,用于缩小图片的宽和高的倍数
        options.inSampleSize = scale + 1;// 加1的目的是为了补充余数的误差
        options.inJustDecodeBounds = false;
        /**
         * inPreferredConfig设置图片的质量: ARGB_8888:默认的图片质量,也是最好的质量,32位,4个字节
         * ARGB_4444:是ARGB_8888占用内存的一半,但质量比较低,不推荐使用
         * RGB_565:不带透明度,是ARGB_8888占用内存的一半,质量不错,推荐使用
         */
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        // Log.i("info","Bitmap.width="+bm.getWidth()+";Bitmap.height="+bm.getHeight());
        /**
         * 3.0之后使用getByteCount方法来获取Bitmap占用内存大小,
         * 之前使用bm.getRowBytes()*bm.getHeight() ===>使用每行所占用的字节数*行数
         */
        // Log.i("info",bm.getByteCount()+"");
        return bm;
    }

    /**
     * 通过网络请求返回的字节数据进行二次采样并返回bitmap
     *
     * @param bytes
     * @param FACTOR
     *            要设定的像素阀值
     * @return
     */
    public static Bitmap getBitmap(byte[] bytes, int FACTOR)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 当设置inJustDecodeBounds为true代表只解码bitmap的边框
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        // 通过第一次采样将图片的宽和高放置到Options对象中输出给后面使用
        int width = options.outWidth;
        int height = options.outHeight;

        int scaleWidth = width / FACTOR;// 宽的压缩比
        int scaleHeight = height / FACTOR;// 高的压缩比
        int scale = scaleHeight;
        // 哪个边的压缩比大,就选哪个,目的是为了尽可能将高和宽都压缩到500像素以内
        if (scaleWidth > scaleHeight)
        {
            scale = scaleWidth;
        }
//        Log.i("info", "width=" + width + ";height=" + height);
        // 压缩比,用于缩小图片的宽和高的倍数
        options.inSampleSize = scale + 1;// 加1的目的是为了补充余数的误差
        options.inJustDecodeBounds = false;
        /**
         * inPreferredConfig设置图片的质量: ARGB_8888:默认的图片质量,也是最好的质量,32位,4个字节
         * ARGB_4444:是ARGB_8888占用内存的一半,但质量比较低,不推荐使用
         * RGB_565:不带透明度,是ARGB_8888占用内存的一半,质量不错,推荐使用
         */
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                options);
//        Log.i("info",
//                "Bitmap.width=" + bm.getWidth() + ";Bitmap.height="
//                        + bm.getHeight());
        /**
         * 3.0之后使用getByteCount方法来获取Bitmap占用内存大小,
         * 之前使用bm.getRowBytes()*bm.getHeight() ===>使用每行所占用的字节数*行数
         */
//        Log.i("info", bm.getByteCount() + "");
        return bm;
    }

    /**
     *
     * @param bm
     * @param file
     */
    public static void saveBitmap(Bitmap bm, File file)
    {
        try
        {
            String path = file.getAbsolutePath();
            String fatherPath = path.substring(0, path.lastIndexOf("/"));
            File fatherDir = new File(fatherPath);
            if (!fatherDir.exists())
            {
                fatherDir.mkdirs();
            }
            if (!file.exists())
            {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            if (bm != null)
            {
                bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
