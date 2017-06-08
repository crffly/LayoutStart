package com.flashstart.layoutstart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.flashstart.layoutstart.StartApplication;


/**
 * @author crffly
 */
public class MobileUtil {

  private static String sdCardPath = null;
  private static String systemVersion = null;

  public static final double TT_MINUTE = 60 * 1000;
  public static final double TT_HOUR = (60 * TT_MINUTE);
  public static final double TT_DAY = (24 * TT_HOUR);
  public static final double TT_5_DAYS = (5 * TT_DAY);
  public static final double TT_WEEK = (7 * TT_DAY);
  public static final double TT_MONTH = (30.5 * TT_DAY);
  public static final double TT_YEAR = (365 * TT_DAY);

  public static final int kCacheDir = 1;
  public static final int kDownloadDir = 2;
  public static final int kProfileDir = 3;

  public static final String kAppName = "BCBApp";
  public final static int CUSTOMONLYID = 20001;
  public final static int CUSTOMONLYPARENTID = 5;
  private final static String CUSTOMFILEPATH_1 = "/data/data/";
  private final static String CUSTOMFILEPATH_2 = "/files/customtheme/";
  private final static String CUSTOMFILETYPE = ".png";
  public final static String CUSTOMBGFILETYPE = "_bg";
  public final static int PREBGWIDTH = 321;
  public final static int PREBGHEIGHT = 228;
  public static String mCurrentCPUInfo = null;

  public static String getLastBitFromUrl(final String url) {
    // return url.replaceFirst("[^?]*/(.*?)(?:\\?.*)","$1);" <-- incorrect
    return url.replaceFirst(".*/([^/?]+).*", "$1");
  }

  public static String stringByAddingPercentEscapesUsingEncoding(String input, String encoding)
      throws UnsupportedEncodingException {
    byte[] inputBytes = input.getBytes(encoding);
    StringBuilder stringBuilder = new StringBuilder(inputBytes.length);
    for (int i = 0; i < inputBytes.length; ++i) {
      int charByte = inputBytes[i] < 0 ? inputBytes[i] + 256 : inputBytes[i];
      if (charByte <= 0x20 || charByte >= 0x7F ||
          (charByte == 0x22 || charByte == 0x25 || charByte == 0x3C ||
              charByte == 0x3E || charByte == 0x20 || charByte == 0x5B ||
              charByte == 0x5C || charByte == 0x5D || charByte == 0x5E ||
              charByte == 0x60 || charByte == 0x7b || charByte == 0x7c ||
              charByte == 0x7d)) {
        stringBuilder.append(String.format("%%%02X", charByte));
      } else {
        stringBuilder.append((char) charByte);
      }
    }
    return stringBuilder.toString();
  }



  public static String getSdCardPath() {
    if ((sdCardPath == null) || (sdCardPath.equals(""))) {
      sdCardPath = Environment.getExternalStorageDirectory().getPath();
      if (sdCardPath.substring(sdCardPath.length() - 1).equals(File.separator) == false) {
        sdCardPath += File.separator;
      }
    }
    return sdCardPath;
  }


  public static boolean isSDCardExists() {
    if (android.os.Environment.getExternalStorageState()
        .equals(android.os.Environment.MEDIA_MOUNTED)) {
      return true;
    }
    return false;
  }

  public static String getDeviceType() {
    return android.os.Build.MODEL;
  }

  public static DisplayMetrics getDeviceDisplayMetrics() {

    Context cnt = StartApplication.getInstance();
    android.view.WindowManager windowsManager =
        (android.view.WindowManager) cnt.getSystemService(Context.WINDOW_SERVICE);
    android.view.Display display = windowsManager.getDefaultDisplay();
    DisplayMetrics outMetrics = new DisplayMetrics();
    display.getMetrics(outMetrics);

    return outMetrics;
  }

  public static int dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static int getDisplayWidth(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    return windowManager.getDefaultDisplay().getWidth();
  }

  public static int getDisplayHight(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    return windowManager.getDefaultDisplay().getHeight();
  }

  public static boolean isEmptyString(String strText) {
    if (strText == null || strText.length() == 0)
      return true;
    return false;
  }

  public static String formatRelativeTime(long dbTime) {
    if (dbTime > 0) {
      if (dbTime <= TT_MINUTE * 10) {
        return "刚刚";
      } else if (dbTime < TT_HOUR) {
        int mins = (int) (dbTime / TT_MINUTE);
        return String.format("%d分钟前", mins);
      } else if (dbTime < TT_DAY) {
        int hours = (int) ((dbTime + TT_HOUR / 2) / TT_HOUR);
        return String.format("%d小时前", hours);
      } else if (dbTime < TT_MONTH) {
        int days = (int) (dbTime / TT_DAY);
        return String.format("%d天前", days);

      } else if (dbTime < TT_YEAR) {
        int months = (int) (dbTime / TT_MONTH);
        return String.format("%d月前", months);

      } else {
        int years = (int) (dbTime / TT_YEAR);
        return String.format("%d年前", years);
      }
    } else {
      dbTime = -dbTime;

      if (dbTime <= TT_MINUTE * 10) {
        return "刚刚";

      } else if (dbTime < TT_HOUR) {
        int mins = (int) (dbTime / TT_MINUTE);
        return String.format("%d分钟前", mins);

      } else if (dbTime < TT_DAY) {
        int hours = (int) ((dbTime + TT_HOUR / 2) / TT_HOUR);
        return String.format("%d小时前", hours);
      } else if (dbTime < TT_MONTH) {
        int days = (int) (dbTime / TT_DAY);
        return String.format("%d天前", days);

      } else if (dbTime < TT_YEAR) {
        int months = (int) (dbTime / TT_MONTH);
        return String.format("%d月前", months);

      } else {
        int years = (int) (dbTime / TT_YEAR);
        return String.format("%d年前", years);
      }
    }
  }

  public static String fileSizeToString(long lFileSize) {
    long KB = 1024;
    long MB = KB * KB;
    long GB = MB * KB;

    if (lFileSize < 10) {
      return "0 KB";
    } else if (lFileSize < KB) {
      return "小于1KB";
    } else if (lFileSize < MB) {
      return String.format("%.1f KB", ((float) lFileSize) / (float) KB);
    } else if (lFileSize < GB) {
      return String.format("%.1f MB", ((float) lFileSize) / (float) MB);
    }

    return String.format("%.1f GB", ((float) lFileSize) / (float) GB);
  }

  public static boolean isUpdate(String strOldVer, String strNewVer) {
    String[] strArrayOld = strOldVer.split("\\.");
    String[] strArrayNew = strNewVer.split("\\.");

    for (int i = 0; i < strArrayNew.length; i++) {
      int nValue = Integer.valueOf(strArrayNew[i]).intValue();
      int nValue2 = Integer.valueOf(strArrayOld[i]).intValue();

      if (nValue > nValue2) {
        return true;
      }
    }
    return false;
  }

  /**
   * get File md5
   *
   * @param filename
   * @return
   */
  public static String md5sum(String filename) {
    InputStream fis = null;
    byte[] buffer = new byte[1024];
    int numRead = 0;
    MessageDigest md5;
    try {
      fis = new FileInputStream(filename);
      md5 = MessageDigest.getInstance("MD5");
      while ((numRead = fis.read(buffer)) > 0) {
        md5.update(buffer, 0, numRead);
      }
      return bytesToHexString(md5.digest());
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      try {
        if (fis != null) {
          fis.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static String bytesToHexString(byte[] bytes) {
    // http://stackoverflow.com/questions/332079
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(0xFF & bytes[i]);
      if (hex.length() == 1) {
        sb.append('0');
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  public static String geFileFromAssets(Context context, String fileName) {
    StringBuilder s = new StringBuilder("");
    try {
      InputStreamReader in =
          new InputStreamReader(context.getResources().getAssets().open(fileName));
      BufferedReader br = new BufferedReader(in);
      String line;
      while ((line = br.readLine()) != null) {
        s.append(line);
      }
      return s.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Check if the primary "external" storage device is available.
   *
   * @return
   */
  public static boolean hasSDCardMountedPhone() {
    String state = Environment.getExternalStorageState();
    if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * copy file
   *
   * @param oldPath
   * @param newPath
   * @return boolean
   */
  public static void copyFile(String oldPath, String newPath) {
    InputStream inStream = null;
    FileOutputStream fs = null;
    try {
      int byteread = 0;
      File oldfile = new File(oldPath);
      if (oldfile.exists()) {
        inStream = new FileInputStream(oldPath);
        fs = new FileOutputStream(newPath);
        byte[] buffer = new byte[1024];
        while ((byteread = inStream.read(buffer)) != -1) {
          fs.write(buffer, 0, byteread);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (inStream != null) {
        try {
          inStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fs != null) {
        try {
          fs.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 文件重名名
   *
   * @param path
   * @param oldname
   * @param newname
   */
  public static void renameFile(String path, String oldname, String newname) {
    if (!oldname.equals(newname)) {
      File oldfile = new File(path + "/" + oldname);
      File newfile = new File(path + "/" + newname);
      if (!newfile.exists()) {
        oldfile.renameTo(newfile);
      }
    }
  }

  /*
  * 获取屏幕宽度
  */
  public static int getScreenWidth(Context context) {
    DisplayMetrics dm = new DisplayMetrics();
    if (Build.VERSION.SDK_INT < 17) {
      //NEXUS 5 DisplayMetrics{density=3.0, width=1080, height=1776, scaledDensity=3.0, xdpi=442.451, ydpi=443.345}
      ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    } else {
      //NEXUS 5 DisplayMetrics{density=3.0, width=1080, height=1920, scaledDensity=3.0, xdpi=442.451, ydpi=443.345}
      ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(dm);
    }
    int width = dm.widthPixels;
    return width;
  }

  public static int getScreenWidth1(Context ctx) {
    WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
    int width = wm.getDefaultDisplay().getWidth();
    return width;
  }

  /**
   * 复制文件
   *
   * @param sourceFile
   * @param targetFile
   * @throws IOException
   */
  public static void copyFile(File sourceFile, File targetFile) throws IOException {
    if (!targetFile.getParentFile().exists()) {
      targetFile.getParentFile().mkdirs();
    }
    BufferedInputStream inBuff = null;
    BufferedOutputStream outBuff = null;
    try {
      // 新建文件输入流并对它进行缓冲
      inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

      // 新建文件输出流并对它进行缓冲
      outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

      // 缓冲数组
      byte[] b = new byte[1024];
      int len;
      while ((len = inBuff.read(b)) != -1) {
        outBuff.write(b, 0, len);
      }
      // 刷新此缓冲的输出流
      outBuff.flush();
    } finally {
      // 关闭流
      if (inBuff != null)
        inBuff.close();
      if (outBuff != null)
        outBuff.close();
    }
  }

  // 获取当前的输入法
  public static InputMethodInfo getMyImi(Context context, InputMethodManager imm) {
    final List<InputMethodInfo> imis = imm.getInputMethodList();
    for (int i = 0; i < imis.size(); ++i) {
      final InputMethodInfo imi = imis.get(i);
      if (imis.get(i).getPackageName().equals(context.getPackageName())) {
        return imi;
      }
    }
    return null;
  }


  public static int dpToPx(int dp) {
    return (int) (dp * StartApplication.getInstance().getScaleDensity() + 0.5f);
    //return dp;
  }


  /**
   * Convert Dp to Pixel
   */
  public static int dpToPx(float dp, Resources resources) {
    float px =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    return (int) px;
  }


  public static void savePreBitmap(Bitmap bitmapTMP) {
    File f = new File("mnt/sdcard/DCIM/test.png");
    if (!f.exists()) {
      try {
        f.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    FileOutputStream fOut = null;
    try {
      fOut = new FileOutputStream(f);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    bitmapTMP.compress(Bitmap.CompressFormat.PNG, 100, fOut);
    try {
      fOut.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      fOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Bitmap getCustomBitmap(String filePath) {
    Bitmap retBitmap = null;
    try {
      FileInputStream fileInputStream = new FileInputStream(filePath);
      BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
      retBitmap = BitmapFactory.decodeStream(bufferedInputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return retBitmap;
  }

  public static void RecursionDeleteFile(File file) {
    if (file.isFile()) {
      file.delete();
      return;
    }
    if (file.isDirectory()) {
      File[] childFile = file.listFiles();
      if (childFile == null || childFile.length == 0) {
        file.delete();
        return;
      }
      for (File f : childFile) {
        RecursionDeleteFile(f);
      }
      file.delete();
    }
  }

  public static void showInputMethod(EditText editText) {
    if (editText == null) {
      return;
    }
    editText.requestFocus();
    InputMethodManager inputMethodManager =
        (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
  }

  public static Bitmap getBitmapFromNativeResID(Context context, int resID) {
    Bitmap retBitmap = null;
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig = Bitmap.Config.RGB_565;
    options.inPurgeable = true;
    options.inInputShareable = true;
    InputStream inputStream = context.getResources().openRawResource(resID);
    retBitmap = BitmapFactory.decodeStream(inputStream);

    return retBitmap;
  }

  public static String getCpuString() {
    if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
      return "Intel";
    }

    String strInfo = "";
    RandomAccessFile reader = null;
    try {
      byte[] bs = new byte[1024];
      reader = new RandomAccessFile("/proc/cpuinfo", "r");
      reader.read(bs);
      String ret = new String(bs);
      int index = ret.indexOf(0);
      if (index != -1) {
        strInfo = ret.substring(0, index);
      } else {
        strInfo = ret;
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return strInfo;
  }

  public static String getCpuType() {
    if (mCurrentCPUInfo == null) {
      String strInfo = getCpuString();

      if (strInfo.contains("ARMv5")) {
        mCurrentCPUInfo = "armv5";
      } else if (strInfo.contains("ARMv6")) {
        mCurrentCPUInfo = "armv6";
      } else if (strInfo.contains("ARMv7")) {
        mCurrentCPUInfo = "armv7";
      } else if (strInfo.contains("Intel")) {
        mCurrentCPUInfo = "x86";
      } else {
        mCurrentCPUInfo = "unknown";
        return mCurrentCPUInfo;
      }

      if (strInfo.contains("neon")) {
        mCurrentCPUInfo += "_neon";
      } else if (strInfo.contains("vfpv3")) {
        mCurrentCPUInfo += "_vfpv3";
      } else if (strInfo.contains(" vfp")) {
        mCurrentCPUInfo += "_vfp";
      } else {
        mCurrentCPUInfo += "_none";
      }
    }

    return mCurrentCPUInfo;
  }

  /**
   * @return
   */
  public static boolean isGooglePhone() {
    String model = android.os.Build.MODEL;
    if (model.contains("Nexus")) {
      return true;
    }
    return false;
  }

  /**
   * @return
   */
  public static String pluginFilePath(Context context) {
    String pluginFilePath = null;
    try {
      String pluginName = ".FotoableKeyboardPlugins";
      boolean hasSdCard = false;
      if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        hasSdCard = true;
      }

      if (hasSdCard) {
        String extStorageDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File file = new File(extStorageDirectory);
        if (!file.exists()) {
          file.mkdirs();
        }
        pluginFilePath = extStorageDirectory + "/" + pluginName;
      } else {
        File file = context.getDir("Download", Context.MODE_WORLD_READABLE);
        if (!file.exists()) {
          file.mkdirs();
        }
        pluginFilePath = file.getAbsolutePath() + "/" + pluginName;
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return pluginFilePath;
  }

  public static void pluginWriteThemeId(Context context, String themeId) {
    String filePath = pluginFilePath(context);
    if (filePath != null) {
      try {
        File file = new File(filePath);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(themeId.getBytes(Charset.defaultCharset()));
        fileOutputStream.close();
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 是否包含emoji
   *
   * @param source
   * @return
   */
  public static boolean containsEmoji(String source) {
    int len = source.length();
    boolean isEmoji = false;
    for (int i = 0; i < len; i++) {
      char hs = source.charAt(i);
      if (0xd800 <= hs && hs <= 0xdbff) {
        if (source.length() > 1) {
          char ls = source.charAt(i + 1);
          int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
          if (0x1d000 <= uc && uc <= 0x1f77f) {
            return true;
          }
        }
      } else {
        // non surrogate
        if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
          return true;
        } else if (0x2B05 <= hs && hs <= 0x2b07) {
          return true;
        } else if (0x2934 <= hs && hs <= 0x2935) {
          return true;
        } else if (0x3297 <= hs && hs <= 0x3299) {
          return true;
        } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 ||
            hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
          return true;
        }
        if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
          char ls = source.charAt(i + 1);
          if (ls == 0x20e3) {
            return true;
          }
        }
      }
    }
    return isEmoji;
  }

  /**
   * 本次查询的就是针对 相机里面的图片进行搜查,获得最近一排的一张照片,的路径
   *
   * @return
   */
  public static String getLastPhotoByPath(Context context) {
    Cursor myCursor = null;
    String pathLast = "";
    // Create a Cursor to obtain the file Path for the large image
    String[] largeFileProjection = {
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.ImageColumns.ORIENTATION,
        MediaStore.Images.ImageColumns.DATE_TAKEN};
    String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
    myCursor = context.getContentResolver().query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        largeFileProjection, null, null, largeFileSort);

    if (myCursor.getCount() < 1) {
      myCursor.close();
      return pathLast;
    }
    while (myCursor.moveToNext()) {
      String data =
          myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
      File f = new File(data);
      //第一个图片文件，就是最近一次拍照的文件；
      if (f.exists()) {
        pathLast = f.getPath();
        myCursor.close();
        return pathLast;
      }
    }
    myCursor.close();
    return pathLast;
  }

  public static long getTimeInterval(long times) {
    return (System.currentTimeMillis() / 1000) - times;
  }

}


