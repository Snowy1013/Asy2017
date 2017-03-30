package com.snowy.sample.primary.imgandanim;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

/**
 * Created by snowy on 16/1/27.
 */
public class FrameAnimateActivity extends MasterFragment implements View.OnClickListener{
    private Button btn_frame_anim_play;
    private Button btn_frame_anim_stop;
    private ImageView img_anim_frame;
    private AnimationDrawable animationDrawable;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_frame_animate;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
//        setTitle("帧动画（Frame）");
        btn_frame_anim_play = (Button) findViewById(R.id.btn_frame_anim_play);
        btn_frame_anim_stop = (Button) findViewById(R.id.btn_frame_anim_stop);
        img_anim_frame = (ImageView) findViewById(R.id.img_anim_frame);
        img_anim_frame.setBackgroundResource(R.drawable.frame_animate);//将frame_animate设为ImageView的背景
        animationDrawable = (AnimationDrawable) img_anim_frame.getBackground();//获取ImagView的背景并将其转换成AnimationDrawable
        btn_frame_anim_play.setOnClickListener(this);
        btn_frame_anim_stop.setOnClickListener(this);
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_frame_anim_play:
                animationDrawable.start();
                break;
            case R.id.btn_frame_anim_stop:
                animationDrawable.stop();
                break;
        }
    }
}
