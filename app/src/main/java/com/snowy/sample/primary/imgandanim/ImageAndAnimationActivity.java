package com.snowy.sample.primary.imgandanim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

/**
 * Created by snowy on 16/1/27.
 */
public class ImageAndAnimationActivity extends MasterFragment implements View.OnClickListener{
    @Override
    protected int getLayoutId() {
        return R.layout.ac_animation_demo;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        findViewById(R.id.btn_animat_frame).setOnClickListener(this);
        findViewById(R.id.btn_animat_tween).setOnClickListener(this);
        findViewById(R.id.btn_animat_property).setOnClickListener(this);
        findViewById(R.id.btn_load_bigimg).setOnClickListener(this);
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_animat_frame: //帧动画
                start(new FrameAnimateActivity());
                break;
            case R.id.btn_animat_tween: //补间动画
                start(new TweenAnimateActivity());
                break;
            case R.id.btn_animat_property: //属性动画
                start(new PropertyAnimateActivity());
                break;
            case R.id.btn_load_bigimg:
                start(new ImageDemoActivity());//高效加载大图片
                break;
        }
    }
}
