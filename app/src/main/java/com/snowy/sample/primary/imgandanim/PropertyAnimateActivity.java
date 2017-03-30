package com.snowy.sample.primary.imgandanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

/**
 * Created by snowy on 16/1/27.
 */
public class PropertyAnimateActivity extends MasterFragment implements View.OnClickListener {
    private ImageView img_anim_ball;
    private RelativeLayout rl_anim_blueball;
    private float rl_anim_blueball_height;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_property_animate;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
//        setTitle("属性动画（Property）");
        img_anim_ball = (ImageView) findViewById(R.id.img_anim_ball);
        rl_anim_blueball = (RelativeLayout) findViewById(R.id.rl_anim_blueball);

//        rl_anim_blueball_height = rl_anim_blueball.getHeight();//得到的值是0，为什么？xml文件中设置的明明是300dp啊
        rl_anim_blueball_height = rl_anim_blueball.getLayoutParams().height; //为什么要用这个方法获取高呢？

        findViewById(R.id.iv_rotate_property).setOnClickListener(this);
        findViewById(R.id.iv_scale_property).setOnClickListener(this);
        findViewById(R.id.bt_vertical_property).setOnClickListener(this);
        findViewById(R.id.bt_paowuxian_property).setOnClickListener(this);
        findViewById(R.id.bt_fadeout_property).setOnClickListener(this);
        findViewById(R.id.bt_together_property).setOnClickListener(this);
        findViewById(R.id.bt_playmulti_property).setOnClickListener(this);
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
            case R.id.iv_rotate_property:
                rotateAnimRun(view);
                break;
            case R.id.iv_scale_property:
                scaleAnimRun(view);
                break;
            case R.id.bt_vertical_property:
                verticalRun();
                break;
            case R.id.bt_paowuxian_property:
                paowuxianRun();
                break;
            case R.id.bt_fadeout_property:
                fadeoutRun();
                break;
            case R.id.bt_together_property:
                togetherRun();
                break;
            case R.id.bt_playmulti_property:
                playWithAfter();
                break;
            default:
                break;
        }
    }

    //旋转
    public void rotateAnimRun(final View view){
        //"rotationX"绕x轴旋转
        ObjectAnimator.ofFloat(view, "rotationX", 0.0f, 360.0f).setDuration(500).start();
    }

    //缩小，淡出点击事件
    public void scaleAnimRun(final View view){
        //缩小，淡出
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "ssss", 1.0f, 0.3f, 1.0f).setDuration(3000);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);

                System.out.println(cVal);
            }
        });
        //缩小，淡出
        /*PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0.3f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0.1f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0.1f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(3000).start();*/
    }

    //小球垂直下落点击事件, 自由落体
    public void verticalRun() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,
                rl_anim_blueball_height - img_anim_ball.getHeight());

//        System.out.println("小球下落的高度为：" + (rl_anim_blueball_height
//                - img_anim_ball.getHeight()) + "\nRelativeLayout的高度为：" + rl_anim_blueball_height);

        valueAnimator.setTarget(img_anim_ball);
        valueAnimator.setDuration(2000).start();
        //添加监听事件
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                img_anim_ball.setTranslationY((Float) valueAnimator.getAnimatedValue());
            }
        });
    }


    /**
     * 实现抛物线的效果，水平方向100px/s，垂直方向加速度200px/s
     * 根据时间的变化，横向和纵向的移动速率是不同的，我们该咋实现呢？
     * 此时就要重写TypeValue的时候了，因为我们在时间变化的同时，需要返回给对象两个值，x当前位置，y当前位置
     *
     * 因为ofInt,ofFloat等无法使用，我们自定义了一个TypeValue，每次根据当前时间返回一个PointF对象，
     * （PointF和Point的区别就是x,y的单位一个是float,一个是int;RectF,Rect也是）
     * PointF中包含了x,y的当前位置～然后我们在监听器中获取，动态设置属性
     */
    //小球抛物线下落点击事件
    public void paowuxianRun() {
        final ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

//                System.out.println("fraction" + fraction); //这里的fraction是什么？部分？

                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) valueAnimator.getAnimatedValue();
                img_anim_ball.setX(point.x);
                img_anim_ball.setY(point.y);
            }
        });
    }

    /**
     * 对于动画，一般都是一些辅助效果，比如我要删除个元素，我可能希望是个淡出的效果，
     * 但是最终还是要删掉，并不是你透明度没有了，还占着位置，所以我们需要知道动画如何结束。
     * 所以我们可以添加一个动画的监听
     */
    //小球淡出点击事件
    public void fadeoutRun() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img_anim_ball, "alpha", 0.1f);
        objectAnimator.setDuration(1000);
        //所以我们可以添加一个动画的监听
        /*objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                System.out.println("动画开始了");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                System.out.println("动画结束了");
                //动画结束时删除
                ViewGroup parent = (ViewGroup) img_anim_ball.getParent();//获取父窗体
                if(parent != null)
                    parent.removeView(img_anim_ball); //从父窗体删除
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                System.out.println("动画取消了");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                System.out.println("动画重复了");
            }
        });*/

        //这么长的代码可以如此优化
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束时删除
                ViewGroup parent = (ViewGroup) img_anim_ball.getParent();//获取父窗体
                if(parent != null)
                    parent.removeView(img_anim_ball); //从父窗体删除
            }
        });
        objectAnimator.start();
    }

    //多动画
    public void togetherRun() {
        RelativeLayout parent = (RelativeLayout) img_anim_ball.getParent();//获取父窗体
        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        if(parent != null) {
            parent.removeView(img_anim_ball);
            img_anim_ball.setLayoutParams(layoutParams);
            parent.addView(img_anim_ball);
        } //以上代码目的是把小球放到父窗体中间

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(img_anim_ball, "scaleX", 1.0f, 2f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(img_anim_ball, "scaleY", 1.0f, 2f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new LinearInterpolator());
        //两个动画同时执行
        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        animatorSet.start();
    }

    //多动画按次序执行
    public void playWithAfter() {
        float rl_anim_blueball_width = rl_anim_blueball.getWidth();//在Activity的onCreate方法中执行此方法获取空间宽高为0，所以在此获取
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(img_anim_ball, "x", 0f, rl_anim_blueball_width/2);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(img_anim_ball, "y", 0f, rl_anim_blueball_height/2);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(img_anim_ball, "scaleX", 1.0f, 2.0f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(img_anim_ball, "scaleY", 1.0f, 2.0f);

        /**
         * animator1,animator2同时执行
         * 接着animator3，animator4同时执行
         */
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1).with(animator2);
        animatorSet.play(animator3).with(animator4);
        animatorSet.play(animator3).after(animator2);
        animatorSet.setDuration(3000);
        animatorSet.start();
    }
}
