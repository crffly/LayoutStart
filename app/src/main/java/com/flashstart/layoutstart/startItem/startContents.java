package com.flashstart.layoutstart.startItem;

/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2017
 * @创建人: WangFei
 * @创建时间: (2017-06-06 15:34)
 * @Email: wangfei@fotoable.com
 * <p>
 * 描述:com.flashstart.layoutstart.startItem-startContents
 * <p>
 * *************************************************************************
 */
public class startContents {

    // 天干地支表
    //
    public static final String[] g_sUp = {"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
    public static final String[] g_sDown = {"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};

    public enum STAR_TIME_INDEX {

        uYEAR(0),uMOON(1),uDAY(2),uHOUR(3);

        private final int value;

        STAR_TIME_INDEX(int nValue)
        {
            value = nValue;
        }

        public int getValue() {
            return value;
        }
    }

    public static final int MAX_UP = 10;
    public static final int MAX_DOWN = 12;
}
