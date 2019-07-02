package eu.homegear.sathya.homegearlauncher;

import android.content.Intent;
import android.webkit.JavascriptInterface;

/**
 * Created by sathya on 13.10.2016.
 */

class WebAppInterface {
    private final MainActivity mActivity;

    WebAppInterface(MainActivity a) {
        mActivity = a;
    }

    @JavascriptInterface
    public void startApp(String name)
    {
        Intent launchIntent = mActivity.getPackageManager().getLaunchIntentForPackage(name);
        if (launchIntent != null) {
            mActivity.startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }
}
