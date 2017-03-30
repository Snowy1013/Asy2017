package org.ayo.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ayo.fragmentation.SupportFragment;

/**
 * 作为页面主体的Fragment，会被部署到一个TmplBaseActivity里，TmplBaseActivity会提前注册
 *
 * 打开方式有两种：
 * 1 普通方式，传入bundle，TmplBaseActivity会根据类名反射出Fragment实例，将bundle传给Fragment
 * 2 Schema方式，传入uri，ayo://tmpl-base-activity-standard?page=com.aa.bb.DetailFragment&id=1
 *
 * 和MasterActivity可以无缝切换
 *
 * 二者都通过getArguments取出bundle
 *
 */

public abstract class MasterFragment extends SupportFragment {

    private View _contentView;
    private MasterDelegate agent;

    public MasterDelegate getAgent(){
        return agent;
    }

    protected abstract int getLayoutId();
    protected abstract void onCreate2(View contentView, @Nullable Bundle savedInstanceState);
    protected abstract void onDestroy2();
    protected abstract void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState);

    @Nullable
    @Override
    final public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _contentView = inflater.inflate(getLayoutId(), null);
        agent = new MasterDelegate();
        agent.attach(getActivity());
        onCreate2(_contentView, savedInstanceState);
        return _contentView;
    }

    @Override
    final public void onDestroyView() {
        onDestroy2();
        super.onDestroyView();
    }

    private boolean isFirstCome = true;

    @Override
    final public void onSupportVisible() {
        super.onSupportVisible();
        if(!isFirstCome) onPageVisibleChanged(true, false, null);
        isFirstCome = false;
    }

    @Override
    final public void onSupportInvisible() {
        super.onSupportInvisible();
        onPageVisibleChanged(false, false, null);
    }

    @Override
    final public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        onPageVisibleChanged(true, true, savedInstanceState);
    }

    public <T> T id(int id){
        return (T) _contentView.findViewById(id);
    }

    public View findViewById(int id){
        return _contentView.findViewById(id);
    }

    public void finish(){
        getAgent().getActivity().finish();
    }

}
