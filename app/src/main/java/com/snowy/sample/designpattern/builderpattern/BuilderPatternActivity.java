package com.snowy.sample.designpattern.builderpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snowy.sample.R;
import com.snowy.sample.eventbus.EventBean;
import com.snowy.sample.utils.DateUtils;

import org.ayo.component.MasterFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;

/**
 * Created by zx on 16-10-9.
 */
public class BuilderPatternActivity extends MasterFragment implements View.OnClickListener {
    Button bt_builder;
    TextView tv_builder;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_builder_pattern;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        bt_builder = (Button) findViewById(R.id.bt_builder);
        tv_builder = (TextView) findViewById(R.id.tv_builder);

        bt_builder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_builder:
                long beginDate = 0;
                long endDate = 0;
                try {
                    beginDate = DateUtils.stringToLong("2016-10-09", "yyyy-MM-dd");
                    endDate = DateUtils.stringToLong("2036-10-09", "yyyy-MM-dd");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //创建构建器对象
                InsuranceContract.ConcreteBuilder builder = new InsuranceContract.ConcreteBuilder("100011020161009", beginDate, endDate);
                //设置需要的数据，然后构建保险合同对象
                InsuranceContract insuranceContract = builder.setPersonName("乔晓明").setOtherData("this is just a test").build();
                //操作保险合同对象的方法
                insuranceContract.someOperation();
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        tv_builder.setText(event.getMsg());
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }
}
