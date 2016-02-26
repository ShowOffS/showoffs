package in.showoffs.showoffs;

import android.app.Application;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import in.showoffs.showoffs.utils.Utility;

/**
 * Created by GRavi on 23-02-2016.
 */
public class ShowOffS extends Application {

    public static CallbackManager callbackManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Utility.setBaseContext(getBaseContext());

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        CustomActivityOnCrash.install(this);
    }
}
