package com.snowy.sample.opensource.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.snowy.sample.R;
import com.snowy.sample.opensource.okhttp.download.DownLoadFirstActivity;

import org.ayo.component.MasterFragment;

/**
 * Created by zx on 16-9-16.
 */
public class HttpMainActivity extends MasterFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.ac_http_main;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        findViewById(R.id.bt_http_download).setOnClickListener(this);
        findViewById(R.id.bt_http_appstore).setOnClickListener(this);
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_http_download:
                start(new DownLoadFirstActivity());
                break;
            case R.id.bt_http_appstore:
                break;
        }
    }
}
