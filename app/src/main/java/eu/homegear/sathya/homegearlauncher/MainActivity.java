package eu.homegear.sathya.homegearlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN  |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_main);

        WebView view = findViewById(R.id.main_view);
        view.getSettings().setBlockNetworkImage(false);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setDomStorageEnabled(true);
        //Fully zoom out
        view.getSettings().setLoadWithOverviewMode(true);
        //Behave as mobile browser
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setSupportZoom(true);

        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line
        //view.getSettings().setAppCachePath("/data/data/"+ getPackageName() +"/cache");
        view.getSettings().setAllowFileAccess(true);
        view.getSettings().setAppCacheEnabled(true);

        view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        final Activity activity = this;
        view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });
        HomegearWebViewClient webViewClient = new HomegearWebViewClient(this);
        view.setWebViewClient(webViewClient);
        view.addJavascriptInterface(new WebAppInterface(this), "HomegearLauncher");

        view.loadUrl(String.valueOf(R.string.homegear_url));
        /* Start Chrome
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView view = findViewById(R.id.main_view);

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(view.canGoBack()) view.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
