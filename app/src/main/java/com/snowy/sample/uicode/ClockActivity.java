package com.snowy.sample.uicode;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

/**
 * Created by ts on 16-7-27.
 */
public class ClockActivity extends MasterFragment {
    Button bt_clock;
    Chronometer chronometer;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_uicode_clock;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        bt_clock = (Button) findViewById(R.id.bt_clock);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        bt_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) bt_clock.getText();
                if(str.equals("启动")){
                    bt_clock.setText("停止");
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }else {
                    bt_clock.setText("启动");
                    chronometer.stop();
                }
            }
        });
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }
}
