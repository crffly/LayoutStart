package com.flashstart.layoutstart.startItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.flashstart.layoutstart.utils.UrlsTools;
import com.flashstart.layoutstart.StartApplication;

/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2017
 * @创建人: WangFei
 * @创建时间: (2017-06-07 15:00)
 * @Email: wangfei@fotoable.com
 * <p>
 * 描述:com.flashstart.layoutstart.startItem-TenStyleData
 * <p>
 * *************************************************************************
 */
public class TenStyleData {

    private JSONArray  _rootTen = null;
    private JSONArray  _rootDown2Up = null;

    private static TenStyleData instance = null;

    public static TenStyleData getInstance() {

        if (instance == null)
        {
            instance = new TenStyleData();
        }
        return instance;
    }

    public TenStyleData()
    {
        String urls = UrlsTools.geFileFromAssets(StartApplication.getInstance().getApplicationContext(), "ten_style_list.json");
        try {
            JSONObject tenData = new JSONObject(urls);
            _rootTen = tenData.getJSONArray("root");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        urls = UrlsTools.geFileFromAssets(StartApplication.getInstance().getApplicationContext(), "down_contain_up.json");
        try {
            JSONObject downData = new JSONObject(urls);
            _rootDown2Up = downData.getJSONArray("root");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getTenData()
    {
        return _rootTen;
    }

    public JSONArray getDown2UpData()
    {
        return _rootDown2Up;
    }

}
