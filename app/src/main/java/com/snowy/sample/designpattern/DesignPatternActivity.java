package com.snowy.sample.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.snowy.sample.R;
import com.snowy.sample.designpattern.builderpattern.BuilderPatternActivity;

import org.ayo.component.MasterFragment;
import org.ayo.fragmentation.SupportFragment;

/**
 * Created by zx on 16-10-9.
 */
public class DesignPatternActivity extends MasterFragment {

    ListView lv_design_pattern;
    String TAG = "DesignPatternActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.ac_design_pattern_demo;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        lv_design_pattern = (ListView) findViewById(R.id.lv_design_pattern);

        String[] itemTitles = new String[] {"建造模式"};
        final SupportFragment[] fragments = new SupportFragment[] {new BuilderPatternActivity()};

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_list_main, itemTitles);
        lv_design_pattern.setAdapter(adapter);
        lv_design_pattern.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                start(fragments[i]);
//                getActivity().startActivity(new Intent(getActivity(), classes[i]));
            }
        });
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }
}
