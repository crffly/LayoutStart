package com.flashstart.layoutstart.startItem;


/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2017
 * @创建人: WangFei
 * @创建时间: (2017-06-06 15:43)
 * @Email: wangfei@fotoable.com
 * <p>
 * 描述:com.flashstart.layoutstart.startItem-Start8Item
 * <p>
 * *************************************************************************
 */

import android.util.Log;

import com.flashstart.layoutstart.startItem.startContents.STAR_TIME_INDEX;
import com.flashstart.layoutstart.startItem.TenStyleData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Start8Item {

    private String value = "";
    public String tenStyle = "";
    public STAR_TIME_INDEX uTimeIndex = STAR_TIME_INDEX.uYEAR;      // 天干地支时序标识

    public boolean  isUp = true;    // 是否为天干
    public boolean  isDayMain = false; // 是否为日主

    private String  _containUp = "";    // 地支藏干数据

    public ArrayList<ContainUpData> listData = new ArrayList<ContainUpData>();

    public Start8Item()
    {
    }

    public Start8Item(boolean bIsUp, STAR_TIME_INDEX nIndex)
    {
        isUp = bIsUp;
        uTimeIndex = nIndex;
    }

    public Start8Item(boolean bIsUp, STAR_TIME_INDEX nIndex, boolean bIsSelf)
    {
        isUp = bIsUp;
        uTimeIndex = nIndex;
        isDayMain = bIsSelf;

        if (bIsSelf == true)
        {
            tenStyle = "日主";
        }
    }

    public void clear()
    {
        _containUp = "";
        listData.clear();
    }

    public void setValue(String strValue)
    {
        value = strValue;

        buildContainUp();

        if (isDayMain == false)
        {
            tenStyle = _containUp;
        }
    }

    public  String getValue()
    {
        return value;
    }

    // 计算十神样式
    //
    public String handleTenStyle(String strMainDay)
    {
        if (isUp)
        {
            tenStyle = handleUpData(strMainDay, value);
        }
        else
        {

            handleDownData(strMainDay);
        }

        return tenStyle;
    }

    private String handleUpData(String strMainDay, String strValue)
    {
        // 传进来的参数是日主
        //
        final JSONArray root = TenStyleData.getInstance().getTenData();

        // 当前组合值
        //
        String strCurrentLayout = strMainDay + strValue;

        try {
            for (int i=0; i<root.length(); i++)
            {
                JSONObject itemObj = root.getJSONObject(i);

                String strName = itemObj.getString("name");

                JSONArray itemList = itemObj.getJSONArray("list");

                for (int j=0; j<itemList.length(); j++)
                {
                    JSONObject tenItem = itemList.getJSONObject(j);

                    String strOrg = tenItem.getString("item");
                    String strNext = tenItem.getString("up");

                    String strItemLayout = strOrg + strNext;

                    if (strCurrentLayout.compareTo(strItemLayout) == 0)
                    {
                        return strName;
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void handleDownData(String strMainDay)
    {
        // 重建地支的时候必须清除一次
        //
        listData.clear();

        for (int i=0; i<_containUp.length(); i++)
        {
            String strUp = _containUp.substring(i,i+1);

            String strTen = handleUpData(strMainDay,strUp);

            listData.add(new ContainUpData(strUp,strTen));
        }
    }

    private void buildContainUp()
    {
        final JSONArray root = TenStyleData.getInstance().getDown2UpData();

        try {
            for (int i=0; i<root.length(); i++)
            {
                JSONObject itemObj = root.getJSONObject(i);

                String strName = itemObj.getString("name");

                if (value.compareTo(strName) == 0)
                {
                    _containUp = itemObj.getString("up");
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 地支藏干数据结构
    //
    public class ContainUpData
    {
        public String leftData = null;
        public String rightData = null;

        public ContainUpData(String strLeft,String strRight)
        {
            leftData = strLeft;
            rightData = strRight;
        }
    }

}
