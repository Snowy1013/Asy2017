package com.snowy.sample.primary.print;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.Nullable;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.snowy.sample.R;

import org.ayo.component.MasterFragment;

/**
 * Created by zx on 17-3-23.
 */

public class PrintDemoActivity extends MasterFragment implements View.OnClickListener {

    private WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_print_demo;
    }

    @Override
    protected void onCreate2(View contentView, @Nullable Bundle savedInstanceState) {
        findViewById(R.id.btn_print_photo).setOnClickListener(this);
        findViewById(R.id.btn_print_html).setOnClickListener(this);
        findViewById(R.id.btn_print_own).setOnClickListener(this);
    }

    @Override
    protected void onDestroy2() {

    }

    @Override
    protected void onPageVisibleChanged(boolean visible, boolean isFirstTimeVisible, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_print_photo:
                doPhotoPrint();
                break;
            case R.id.btn_print_html:
                doHtmlPrint();
                break;
            case R.id.btn_print_own:
//                doOwnPrint();
                break;
        }
    }

    private void doPhotoPrint(){
        PrintHelper photoPrint = new PrintHelper(getActivity());
        photoPrint.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowy_cute1);
        photoPrint.printBitmap("cute1.jpg - test print", bitmap);
    }

    private void doHtmlPrint(){
        WebView webView = new WebView(getActivity());
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("snowy", "page finish loading " + url);
                //请确保在WebViewClient)中的onPageFinished()方法内调用创建打印任务的方法。
                // 如果没有等到页面加载完毕就进行打印，打印的输出可能会不完整或空白，甚至可能会失败。
                createWebviewPrint(view);
                mWebView = null;
            }
        });

        if (isNetworkConnected(getActivity())) {
            // Print an existing web page (remember to request INTERNET permission!):
            webView.loadUrl("http://developer.android.com/about/index.html");
        } else {
            String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                    "testing, testing...</p></body></html>";
            webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
        }

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        //保留了一个WebView对象实例的引用，这样能够确保它不会在打印任务创建之前就被垃圾回收器所回收。
        // 在编写代码时请务必这样做，否则打印的进程可能会无法继续执行。
        mWebView = webView;
    }

    @TargetApi(19)
    private void doOwnPrint(){
        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
        // Set job name, which will be displayed in the print queue
        String jobName = getActivity().getString(R.string.app_name) + " Document";
        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document
        printManager.print(jobName, new MyPrintDocumentAdapter(getActivity()), null);
    }

    @TargetApi(19)
    private void createWebviewPrint(WebView webView){
        //Get a PrintManager Instance
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);

        //Get a Print adapter instance
        PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getActivity().getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printDocumentAdapter, new PrintAttributes.Builder().build());
    }

    /**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    @TargetApi(19)
    class MyPrintDocumentAdapter extends PrintDocumentAdapter {
        private int totalPages = 12;
        private Context mContext;
        private PrintedPdfDocument mPdfDocument;

        public MyPrintDocumentAdapter(Context context){
            this.mContext = context;
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
            // Create a new PdfDocument with the requested page attributes
            mPdfDocument = new PrintedPdfDocument(mContext, newAttributes);
            // Respond to cancellation request
            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }

            // Compute the expected number of printed pages
            int pages = computePageCount(newAttributes);
            if (pages > 0) {
                // Return print information to print framework
                PrintDocumentInfo info = new PrintDocumentInfo
                        .Builder("print_output.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(pages)
                        .build();
                // Content layout reflow is complete
                callback.onLayoutFinished(info, true);
            } else {
                // Otherwise report an error to the print framework
                callback.onLayoutFailed("Page count calculation failed.");
            }

        }

        @Override
        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
            // Iterate over each page of the document,
            // check if it's in the output range.
            /*for (int i = 0; i < totalPages; i++) {
                // Check to see if this page is in the output range.
                if (containsPage(pageRanges, i)) {
                    // If so, add it to writtenPagesArray. writtenPagesArray.size()
                    // is used to compute the next output page index.
                    writtenPagesArray.append(writtenPagesArray.size(), i);
                    PdfDocument.Page page = mPdfDocument.startPage(i);

                    // check for cancellation
                    if (cancellationSignal.isCanceled()) {
                        callback.onWriteCancelled();
                        mPdfDocument.close();
                        mPdfDocument = null;
                        return;
                    }

                    // Draw page content for printing
                    drawPage(page);

                    // Rendering is complete, so page can be finalized.
                    mPdfDocument.finishPage(page);
                }
            }

            // Write PDF document to file
            try {
                mPdfDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                mPdfDocument.close();
                mPdfDocument = null;
            }
            PageRange[] writtenPages = computeWrittenPages();
            // Signal the print framework the document is complete
            callback.onWriteFinished(writtenPages);*/
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }

        private int computePageCount(PrintAttributes printAttributes){
            int itemsPerPage = 4; //default item count for portrait mode
            PrintAttributes.MediaSize pageSize = printAttributes.getMediaSize();
            if (!pageSize.isPortrait()) {
                // Six items per page in landscape orientation
                itemsPerPage = 6;
            }

            // Determine number of print items
            int printItemCount = 12;    //getPrintItemCount();

            return (int) Math.ceil(printItemCount / itemsPerPage);
        }

        private void drawPage(PdfDocument.Page page) {
            Canvas canvas = page.getCanvas();

            // units are in points (1/72 of an inch)
            int titleBaseLine = 72;
            int leftMargin = 54;

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(36);
            canvas.drawText("Test Title", leftMargin, titleBaseLine, paint);

            paint.setTextSize(11);
            canvas.drawText("Test paragraph", leftMargin, titleBaseLine + 25, paint);

            paint.setColor(Color.BLUE);
            canvas.drawRect(100, 100, 172, 172, paint);
        }
    }
}
