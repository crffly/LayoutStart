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
import android.widget.TextView;

import com.flashstart.layoutstart.startItem.Start8Item;

import java.util.ArrayList;

public class HandleData {

    private static HandleData instance = null;

    public ArrayList<Start8Item> star8Layout = new ArrayList<Start8Item>();     // 八字基本数据结构

    public static HandleData getInstance() {

        if (instance == null)
        {
            instance = new HandleData();
        }
        return instance;
    }

    public void buildData(Start8Item sMainDay,ArrayList<Button>  layout8Start)
    {
        for (int i=0; i<8; i++)
        {
            Button btnItem = layout8Start.get(i);

            Start8Item sItem = (Start8Item) btnItem.getTag();

            if (sItem.isDayMain == false)
            {
                sItem.handleTenStyle(sMainDay.getValue());
            }
        }
    }

}
