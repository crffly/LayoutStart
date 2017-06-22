package com.flashstart.layoutstart.startItem;

/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2017
 * @创建人: WangFei
 * @创建时间: (2017-06-14 11:52)
 * @Email: wangfei@fotoable.com
 * <p>
 * 描述:com.flashstart.layoutstart.startItem-HandleData
 * <p>
 * *************************************************************************
 */

import android.widget.Button;
import com.flashstart.layoutstart.startItem.TenStyleData;

import com.flashstart.layoutstart.startItem.Start8Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HandleData {

    private static HandleData instance = null;

    public ArrayList<String> upMerge = new ArrayList<String>();
    public ArrayList<String> downMerge = new ArrayList<String>();

    public static HandleData getInstance() {

        if (instance == null)
        {
            instance = new HandleData();
        }
        return instance;
    }

    public String getUpList()
    {
        String strValue = "";

        for (int i=0; i<upMerge.size(); i++)
        {
            strValue =  strValue + "  " + upMerge.get(i);
        }

        return strValue;
    }

    public String getDownList()
    {
        String strValue = "";

        for (int i=0; i<downMerge.size(); i++)
        {
            strValue =  strValue + "  " + downMerge.get(i);
        }

        return strValue;
    }

    public void buildData(Start8Item sMainDay,ArrayList<Button>  layout8Start)
    {
        // 建立十神表
        //
        for (int i=0; i<8; i++)
        {
            Button btnItem = layout8Start.get(i);

            Start8Item sItem = (Start8Item) btnItem.getTag();

            if (sItem.isDayMain == false)
            {
                sItem.handleTenStyle(sMainDay.getValue());
            }
        }

        // 建立天干合化表
        //
        upMerge.clear();

        for (int i=1; i<4; i++)
        {
            Button btnItemCur = layout8Start.get(i);
            Button btnItemPre = layout8Start.get(i-1);

            Start8Item sItemCur = (Start8Item) btnItemCur.getTag();
            Start8Item sItemPre = (Start8Item) btnItemPre.getTag();

            if (sItemCur.getValue().compareTo(sItemPre.getValue()) == 0)
            {
                continue;
            }

            String strLeft =  sItemPre.getValue() + sItemCur.getValue();

            String strResult = findUp(strLeft);

            if (strResult != null)
            {
                upMerge.add(strLeft+"=" + strResult);
            }
        }

        // 建立地支合化表
        //
        downMerge.clear();
        for (int i=5; i<8; i++)
        {
            Button btnItemCur = layout8Start.get(i);
            Button btnItemPre = layout8Start.get(i-1);

            Start8Item sItemCur = (Start8Item) btnItemCur.getTag();
            Start8Item sItemPre = (Start8Item) btnItemPre.getTag();

            if (sItemCur.getValue().compareTo(sItemPre.getValue()) == 0)
            {
                continue;
            }

            String strLeft =  sItemPre.getValue() + sItemCur.getValue();

            String strResult = findUp(strLeft);

            if (strResult != null)
            {
                downMerge.add(strLeft+"=" + strResult);
            }
        }

        for (int i=6; i<8; i++)
        {
            Button btnItemCur = layout8Start.get(i);
            Button btnItemPre = layout8Start.get(i-1);
            Button btnItemFrist = layout8Start.get(i-2);

            Start8Item sItemCur = (Start8Item) btnItemCur.getTag();
            Start8Item sItemPre = (Start8Item) btnItemPre.getTag();
            Start8Item sItemFrist = (Start8Item) btnItemFrist.getTag();

            if (sItemCur.getValue().compareTo(sItemPre.getValue()) == 0 ||
                    sItemCur.getValue().compareTo(sItemFrist.getValue()) == 0)
            {
                continue;
            }

            String strLeft =  sItemFrist.getValue() + sItemPre.getValue() + sItemCur.getValue();

            String strResult = findUp(strLeft);

            if (strResult != null)
            {
                downMerge.add(strLeft+"=" + strResult);
            }
        }
    }

    private String findUp(String strValue)
    {
        try {

            JSONArray root = TenStyleData.getInstance().getMergeData();

            for (int i=0; i<root.length(); i++)
            {
                JSONObject fiveItem = root.getJSONObject(i);

                String strName = fiveItem.getString("name");

                JSONArray fiveList = fiveItem.getJSONArray("list");

                for (int j=0; j<fiveList.length(); j++)
                {
                    JSONObject mergeItem = fiveList.getJSONObject(j);

                    String strItem = mergeItem.getString("merge");

                    if (strValue.length() == strItem.length())
                    {
                        boolean bRel = true;
                        for (int k = 0; k < strValue.length(); k++) {
                            String strSub = strValue.substring(k, k + 1);

                            if (strItem.indexOf(strSub) == -1) {
                                bRel = false;
                                break;
                            }
                        }

                        if (bRel == false) {
                            continue;
                        }
                        return strName;
                    }
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
