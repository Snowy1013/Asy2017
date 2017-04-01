package com.snowy.sample.primary.imgandanim;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * Created by zx on 17-4-1.
 */

public class AsyncDrawable extends BitmapDrawable {
    private final WeakReference<ImageDemoActivity.BitmapWorkTask> bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap,
                         ImageDemoActivity.BitmapWorkTask bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference =
                new WeakReference(bitmapWorkerTask);
    }

    public ImageDemoActivity.BitmapWorkTask getBitmapWorkTask() {
        return bitmapWorkerTaskReference.get();
    }
}
