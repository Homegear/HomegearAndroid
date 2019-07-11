package eu.homegear.sathya.homegearlauncher;

import android.net.http.SslError;
import android.os.CountDownTimer;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/**
 * Created by sathya on 12.10.2016.
 */

class HomegearWebViewClient extends WebViewClient {
    private final MainActivity mActivity;

    HomegearWebViewClient(MainActivity a) {
        mActivity = a;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //if (Uri.parse(url).getHost().equals("192.168.178.100")) return false;
        return false;
    }

    @Override
    public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed() ;
    }

    @Override
    public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.loadUrl(mActivity.getResources().getString(R.string.homegear_url));
                    }
                });
            }
        }.start();
        Toast.makeText(mActivity, "Error: " + description, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        //Toast.makeText(mActivity, "Page fully loaded.", Toast.LENGTH_LONG).show();
    }
}
