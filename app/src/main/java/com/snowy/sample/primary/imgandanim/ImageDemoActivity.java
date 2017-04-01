package com.snowy.sample.primary.imgandanim;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.snowy.sample.R;
import com.snowy.sample.utils.DensityUtils;

import org.ayo.component.MasterFragment;

import java.lang.ref.WeakReference;

/**
 * Created by zx on 17-3-28.
 */

public class ImageDemoActivity extends MasterFragment implements View.OnClickListener {

    private ImageView img_load_bigimg;
    private int[] resIds = new int[]{R.drawable.bluesky, R.drawable.tiger,
            R.drawable.caoyuan, R.drawable.green, R.drawable.road, R.drawable.beach,
            R.drawable.oscen, R.drawable.mountain, R.drawable.tree, R.drawable.oscen2};
    private ListView lv_load_images;

    private Bitmap mPlaceHolderBitmap;//定义一个图片加载过程中的占位符

    @Override
    protected int getLayoutId() {
        return R.layout.ac_image_demo;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        findViewById(R.id.bt_load_bigimg).setOnClickListener(this);
        findViewById(R.id.bt_load_imglist).setOnClickListener(this);
        img_load_bigimg = (ImageView) findViewById(R.id.img_load_bigimg);
        lv_load_images = (ListView) findViewById(R.id.lv_load_images);

        mPlaceHolderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_load_bigimg:
                lv_load_images.setVisibility(View.GONE);
                img_load_bigimg.setVisibility(View.VISIBLE);
                loadBigImage();
                break;
            case R.id.bt_load_imglist:
                img_load_bigimg.setVisibility(View.GONE);
                lv_load_images.setVisibility(View.VISIBLE);
                ImageAdapter adapter = new ImageAdapter(getContext(), resIds);
                lv_load_images.setAdapter(adapter);
                break;
        }
    }

    private void loadBigImage2(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)){
            BitmapWorkTask bitmapWorkTask = new BitmapWorkTask(getContext(), imageView);
            AsyncDrawable asyncDrawable = new AsyncDrawable(getResources(), mPlaceHolderBitmap, bitmapWorkTask);
            imageView.setImageDrawable(asyncDrawable);
            bitmapWorkTask.execute(resId, DensityUtils.dp2px(getContext(), 300), DensityUtils.dp2px(getContext(), 200));
        }
    }

    //检查是否有另一个正在执行的任务与该ImageView关联了起来，如果的确是这样，它通过执行cancel()方法来取消另一个任务
    private boolean cancelPotentialWork(int data, ImageView imageView){
        BitmapWorkTask bitmapWorkTask = getBitmapWorkTask(imageView);
        if (bitmapWorkTask != null) {
            int bitmapData = bitmapWorkTask.data;
            if (bitmapData == 0 || bitmapData != data) {
                bitmapWorkTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    //作检索AsyncTask是否已经被分配到指定的ImageView
    private BitmapWorkTask getBitmapWorkTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkTask();
            }
        }
        return null;
    }

    private void loadBigImage() {
        new AsyncTask<Void, Void, Bitmap>() {
            int reqHeight;
            int reqWidth;

            @Override
            protected void onPreExecute() {
                reqHeight = DensityUtils.dp2px(getContext(), 200);
                reqWidth = DensityUtils.dp2px(getContext(), 300);
            }

            @Override
            protected Bitmap doInBackground(Void... voids) {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true; //可以在解码的时候避免内存的分配，它会返回一个null的Bitmap，但是可以获取到 outWidth, outHeight 与 outMimeType。
//                int outWidth = options.outWidth;
//                int outHeight = options.outHeight;
//                String outMimeType = options.outMimeType;
                Bitmap bitmap = decodeSampleBitmapFromResource(getResources(), R.drawable.caoyuan, reqWidth, reqHeight);

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                img_load_bigimg.setImageBitmap(bitmap);
            }
        }.execute();
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }

    public int calculateInSampleSIze(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        /*if (width > reqWidth || height > reqHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;

            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }*/
        while (height / inSampleSize > reqHeight && width / inSampleSize > reqWidth) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }

    public Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSIze(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    class ImageAdapter extends BaseAdapter{
        private Context context;
        private int[] resIds;
        public ImageAdapter(Context context, int[] resIds){
            this.context = context;
            this.resIds = resIds;
        }
        @Override
        public int getCount() {
            return resIds.length;
        }

        @Override
        public Object getItem(int i) {
            return resIds[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            Log.i("snowy", "ImageAdapter: " + i);
            ViewHolder holder;
            if (convertView == null) {
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.item_listview_images, null);
                convertView = View.inflate(context, R.layout.item_listview_images, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.img_item_list);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            loadBigImage2(resIds[i], holder.imageView);
            return convertView;
        }

        class ViewHolder{
            ImageView imageView;
        }
    }

    public class BitmapWorkTask extends AsyncTask<Integer, Void, Bitmap> {
        private WeakReference<ImageView> imageViewReference;
//        private ImageView imageView;
        private Context mContext;
        public int data = 0;

        public BitmapWorkTask(Context context, ImageView imageView){
            this.mContext = context;
//            this.imageView = imageView;
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            Bitmap bitmap = decodeSampleBitmapFromResource(mContext.getResources(), data, params[1], params[2]);
            Log.i("snowy", "bitmap width = " + bitmap.getWidth() + " and heignt = " + bitmap.getHeight());
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }
            if (imageViewReference != null && bitmap != null) {
                ImageView imageView = imageViewReference.get();
                BitmapWorkTask bitmapWorkTask = getBitmapWorkTask(imageView);
                if (imageView != null && this == bitmapWorkTask) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
