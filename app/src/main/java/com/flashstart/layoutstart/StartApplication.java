package com.flashstart.layoutstart;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.flashstart.layoutstart.utils.MobileUtil;


public class StartApplication extends Application {

  private static StartApplication instance;

  public static StartApplication getInstance() {
    return instance;
  }

  private float scaleDensity;


  private static final String TAG = "StartApplication";

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;

      scaleDensity = MobileUtil.getDeviceDisplayMetrics().density;
  }

  public float getScaleDensity() {
    return scaleDensity;
  }

}
