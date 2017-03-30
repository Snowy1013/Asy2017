package com.snowy.sample.uicode;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ts on 16-7-27.
 */
public class FrameLayoutActivity extends MasterFragment {

    public int currentColor = 0;
    public int[] colors = new int[]{
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6
    };

    public int[] views = new int[]{
            R.id.tv_frame01,
            R.id.tv_frame02,
            R.id.tv_frame03,
            R.id.tv_frame04,
            R.id.tv_frame05,
            R.id.tv_frame06
    };

    TextView[] textViews = new TextView[views.length];

    android.os.Handler hander = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x123){
                for (int i=0; i<views.length; i++) {
                    textViews[i].setBackgroundResource(colors[(currentColor + i) % views.length]);
                }
                currentColor ++ ;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.ac_uicode_framelayout;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        for (int i=0; i<views.length; i++){
            textViews[i] = (TextView) findViewById(views[i]);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                hander.sendEmptyMessage(0x123);
            }
        }, 0, 500);
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }
}
