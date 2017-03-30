package org.ayo.component;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.ayo.fragmentation.SwipeBackActivity;

/**
 * 真正的Activity，也可以通过schema打开，不需要传page了
 * ayo://tmpl-base-activity-standard?id=1
 *
 */

public abstract class MasterActivity extends SwipeBackActivity {

    private View _contentView;
    private Bundle bundle;
    private MasterDelegate agent;

    public MasterDelegate getAgent(){
        return agent;
    }

    @Override
    public boolean isSwipebackEnabled() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agent = new MasterDelegate();
        agent.attach(this);
    }


    public <T> T id(int id){
        return (T) _contentView.findViewById(id);
    }

    public Activity getActivity(){
        return this;
    }
}
