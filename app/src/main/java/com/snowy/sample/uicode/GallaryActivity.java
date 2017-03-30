package com.snowy.sample.uicode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

/**
 * Created by zx on 16-9-2.
 */
public class GallaryActivity extends MasterFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.ac_uicode_gallary;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }
}
