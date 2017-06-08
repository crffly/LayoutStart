package com.flashstart.layoutstart.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Project: FotoIme
 * Description: com.fotoable.fotoime.ui.picture-UrlsTools
 * Author: danhantao
 * Update: danhantao(2015-07-20 13:34)
 * Email: danhantao@yeah.net
 */
public class UrlsTools {

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
    }
  }
}
