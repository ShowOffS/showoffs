package in.showoffs.showoffs;

import android.app.Application;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * Created by GRavi on 23-02-2016.
 */
public class ShowOffS extends Application {

    public static CallbackManager callbackManager;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        CustomActivityOnCrash.install(this);
    }
}
