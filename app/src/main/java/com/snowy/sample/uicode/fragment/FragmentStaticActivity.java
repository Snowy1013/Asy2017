package com.snowy.sample.uicode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;


/**
 * Created by Snowy on 16/1/20.
 */
public class FragmentStaticActivity extends MasterFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.ac_fragment_static;
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
