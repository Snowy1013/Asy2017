package com.snowy.sample.primary.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zx on 17-3-31.
 */

public class ShareDemoActivity extends MasterFragment implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.ac_share_demo;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        findViewById(R.id.bt_share_text).setOnClickListener(this);
        findViewById(R.id.bt_share_text_chooser).setOnClickListener(this);
        findViewById(R.id.bt_share_image).setOnClickListener(this);
        findViewById(R.id.bt_share_images).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_share_text:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                intent1.setType("TEXT/PLAIN");
                getContext().startActivity(intent1);
                break;
            case R.id.bt_share_text_chooser:
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                intent2.setType("text/plain");
                getContext().startActivity(Intent.createChooser(intent2, "Send text to ..."));
                break;
            case R.id.bt_share_image:
                Intent intent3 = new Intent(Intent.ACTION_SEND);
                Uri uriToImage = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/cat.jpg"));
                intent3.putExtra(Intent.EXTRA_STREAM, uriToImage);
                intent3.setType("image/jpeg");
                getContext().startActivity(Intent.createChooser(intent3, "Send image to ..."));
                break;
            case R.id.bt_share_images:
                Uri uriToImage1 = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/cat.jpg"));
                Uri uriToImage2 = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/fengjing.jpg"));
                Uri uriToImage3 = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/snowy_cute6.jpg"));
                ArrayList<Uri> uris = new ArrayList<>();
                uris.add(uriToImage1);
                uris.add(uriToImage2);
                uris.add(uriToImage3);

                Intent intent4 = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent4.putExtra(Intent.EXTRA_STREAM, uris);
                intent4.setType("image/*");
                getContext().startActivity(Intent.createChooser(intent4, "Send images to ..."));
                break;
        }
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }
}
