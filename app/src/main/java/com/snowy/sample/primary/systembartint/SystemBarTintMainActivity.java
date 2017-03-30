package com.snowy.sample.primary.systembartint;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

/**
 * Created by zx on 17-1-19.
 */

public class SystemBarTintMainActivity extends MasterFragment {
    private Button bt_systembar;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_systembar_main;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        bt_systembar = (Button) findViewById(R.id.bt_systembar);
        bt_systembar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SystemBarTintActivity.class));
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
