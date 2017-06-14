package com.flashstart.layoutstart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.Button;
import android.widget.TextView;
import android.widget.GridView;
import com.flashstart.layoutstart.startItem.Start8Item;
import com.flashstart.layoutstart.startItem.startContents;
import com.wx.wheelview.widget.WheelViewDialog;
import com.flashstart.layoutstart.startItem.HandleData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Button>  _layout8Start = new ArrayList<Button>();
    private ArrayList<TextView>  _layoutTenStyle = new ArrayList<TextView>();

    private GridView    _gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnItem = null;

        // 4个天干
        //
        BuildLayout(R.id.btn_year_up,true, startContents.STAR_TIME_INDEX.uYEAR);
        BuildLayout(R.id.btn_moon_up,true, startContents.STAR_TIME_INDEX.uMOON);
        BuildLayout(R.id.btn_day_up,true, startContents.STAR_TIME_INDEX.uDAY);
        BuildLayout(R.id.btn_hour_up,true, startContents.STAR_TIME_INDEX.uHOUR);

        // 4个地支
        //
        BuildLayout(R.id.btn_year_down,false, startContents.STAR_TIME_INDEX.uYEAR);
        BuildLayout(R.id.btn_moon_down,false, startContents.STAR_TIME_INDEX.uMOON);
        BuildLayout(R.id.btn_day_down,false, startContents.STAR_TIME_INDEX.uDAY);
        BuildLayout(R.id.btn_hour_down,false, startContents.STAR_TIME_INDEX.uHOUR);

        // 建立天干4个十神
        //
        BuildTenLayout(R.id.tv_ten_up_year);
        BuildTenLayout(R.id.tv_ten_up_moon);
        BuildTenLayout(R.id.tv_ten_up_day);
        BuildTenLayout(R.id.tv_ten_up_hour);

        for (int i=0; i<8; i++)
        {
            btnItem = _layout8Start.get(i);

            btnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showDialog(view);
                }
            });
        }

        // 开始计算十神排布
        //
        Button btnStart = (Button) findViewById(R.id.btn_done);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleTenStyle();
            }
        });
    }

    private void BuildLayout(int btnId, boolean bUp, startContents.STAR_TIME_INDEX timeIndex)
    {
        Button btnItem = (Button) findViewById(btnId);
        _layout8Start.add(btnItem);

        boolean isDayMain = false;
        if (startContents.STAR_TIME_INDEX.uDAY == timeIndex && bUp == true)
        {
            isDayMain = true;
        }
        Start8Item sItem = new Start8Item(bUp, timeIndex,isDayMain);

        if (bUp)
        {
            sItem.setValue(startContents.g_sUp[timeIndex.getValue()]);
        }
        else
        {
            sItem.setValue(startContents.g_sDown[timeIndex.getValue()]);
        }

        btnItem.setText(sItem.getValue());

        btnItem.setTag(sItem);
    }

    private void BuildTenLayout(int btnId)
    {
        TextView tvItem = (TextView) findViewById(btnId);
        _layoutTenStyle.add(tvItem);
    }

    public void showDialog(View view) {

        final Button btnItem = (Button)view;
        final Start8Item sItem = (Start8Item)btnItem.getTag();

        int nCount = startContents.MAX_UP;

        if (sItem.isUp == false)
        {
            nCount = startContents.MAX_DOWN;
        }
        WheelViewDialog dialog = new WheelViewDialog(this);
        dialog.setTitle("选择").setItems(createMainDatas(sItem)).setButtonText("确定").setDialogStyle(Color
                .parseColor("#6699ff")).setCount(5).show();

        dialog.setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener()
        {
            @Override
            public void onItemClick(int var1, String var2)
            {
                if (sItem.isUp)
                {
                    sItem.setValue(startContents.g_sUp[var1]);
                }
                else
                {
                    sItem.setValue(startContents.g_sDown[var1]);
                }

                btnItem.setText(sItem.getValue());
                btnItem.setTextColor(Color.RED);
            }

        });
    }

    private List<String> createMainDatas(Start8Item sItem) {

        if (sItem.isUp)
        {
            return Arrays.asList(startContents.g_sUp);
        }
        return Arrays.asList(startContents.g_sDown);
    }

    private void handleTenStyle()
    {
        Button btnMainDay = _layout8Start.get(2);

        Start8Item sMainDay = (Start8Item)btnMainDay.getTag();

        // 建立各种数据结构
        //
        HandleData.getInstance().buildData(sMainDay,_layout8Start);

        // 更新十神显示UI
        //
        for (int i=0; i<8; i++)
        {
            Button btnItem = _layout8Start.get(i);

            Start8Item sItem = (Start8Item) btnItem.getTag();

            if (sItem.isUp)
            {
                TextView tvItem = _layoutTenStyle.get(i);
                tvItem.setText(sItem.tenStyle);
            }
            else
            {
                layoutUIDownTen(sItem);
            }
        }

        // 更新天干地支合化UI
        //
    }

    private void clearData()
    {
        for (int i=0; i<8; i++)
        {
            Button btnItem = _layout8Start.get(i);

            Start8Item sItem = (Start8Item) btnItem.getTag();

            sItem.clear();
        }
    }

    private void layoutUIDownTen(Start8Item iItem)
    {
        if (iItem.uTimeIndex == startContents.STAR_TIME_INDEX.uYEAR)
        {
            buildDown(R.id.tv_down_year_left1, R.id.tv_down_year_right1, 0, iItem);
            buildDown(R.id.tv_down_year_left2, R.id.tv_down_year_right2, 1, iItem);
            buildDown(R.id.tv_down_year_left3, R.id.tv_down_year_right3, 2, iItem);
        }

        if (iItem.uTimeIndex == startContents.STAR_TIME_INDEX.uMOON)
        {
            buildDown(R.id.tv_down_moon_left1, R.id.tv_down_moon_right1, 0, iItem);
            buildDown(R.id.tv_down_moon_left2, R.id.tv_down_moon_right2, 1, iItem);
            buildDown(R.id.tv_down_moon_left3, R.id.tv_down_moon_right3, 2, iItem);
        }

        if (iItem.uTimeIndex == startContents.STAR_TIME_INDEX.uDAY)
        {
            buildDown(R.id.tv_down_day_left1, R.id.tv_down_day_right1, 0, iItem);
            buildDown(R.id.tv_down_day_left2, R.id.tv_down_day_right2, 1, iItem);
            buildDown(R.id.tv_down_day_left3, R.id.tv_down_day_right3, 2, iItem);
        }

        if (iItem.uTimeIndex == startContents.STAR_TIME_INDEX.uHOUR)
        {
            buildDown(R.id.tv_down_hour_left1, R.id.tv_down_hour_right1, 0, iItem);
            buildDown(R.id.tv_down_hour_left2, R.id.tv_down_hour_right2, 1, iItem);
            buildDown(R.id.tv_down_hour_left3, R.id.tv_down_hour_right3, 2, iItem);
        }
    }

    private void buildDown(int nLeft, int nRight, int nIndex, Start8Item iItem)
    {
        TextView tvLeft = (TextView) findViewById(nLeft);
        TextView tvRight = (TextView) findViewById(nRight);

        if (nIndex >= iItem.listData.size()) {

            tvLeft.setText("");
            tvRight.setText("");
            return;
        }

        Start8Item.ContainUpData uData = iItem.listData.get(nIndex);

        tvLeft.setText(uData.leftData);
        tvRight.setText(uData.rightData);
    }
}
