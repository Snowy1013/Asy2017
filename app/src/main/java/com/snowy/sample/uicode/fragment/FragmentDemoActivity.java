package com.snowy.sample.uicode.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;


/**
 * Created by Snowy on 16/1/20.
 */
public class FragmentDemoActivity extends MasterFragment implements View.OnClickListener{
    @Override
    protected int getLayoutId() {
        return R.layout.ac_fragment_demo;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        findViewById(R.id.btn_fragment_static).setOnClickListener(this);
        findViewById(R.id.btn_fragment_dynamic).setOnClickListener(this);
        findViewById(R.id.btn_fragment_communicate).setOnClickListener(this);
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
            case R.id.btn_fragment_static:
                start(new FragmentStaticActivity());
                break;
            case R.id.btn_fragment_dynamic:
                start(new FragmentDynamicActivity());
                break;
            case R.id.btn_fragment_communicate:
//              ActivityAttacher.startActivity(getActivity(), FragmentCommunActivity.class);
                Intent intent = new Intent(getActivity(), FragmentCommunActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
