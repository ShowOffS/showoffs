package in.showoffs.showoffs.utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import in.showoffs.showoffs.activities.BaseActivity;
import in.showoffs.showoffs.activities.Login;

/**
 * Created by GRavi on 23-02-2016.
 */
public abstract class ShowOffSLoginDispatcher extends BaseActivity{

    protected abstract Class<?> getTargetClass();

    private static final int LOGIN_REQUEST = 0;
    private static final int TARGET_REQUEST = 1;

    private static final String LOG_TAG = "ParseLoginDispatch";

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runDispatch();
    }

    @Override
    final protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode);
        if (requestCode == LOGIN_REQUEST && resultCode == RESULT_OK) {
            runDispatch();
        } else {
            finish();
        }
    }

    /**
     * Override this to generate a customized intent for starting ParseLoginActivity.
     * However, the preferred method for configuring Parse Login UI components is by
     * specifying activity options in AndroidManifest.xml, not by overriding this.
     *
     * @return Intent that can be used to start ParseLoginActivity
     */
    protected Intent getLoginIntent() {
        Intent intent = new Intent(this, Login.class);
        //    intent.putExtras(config.toBundle());
        return intent;
    }



    protected Intent getDashboardIntent(){
        Intent intent = new Intent(this,getTargetClass());
        return intent;
    }

    private void runDispatch() {
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        boolean isLoggedIn = getPrefs.getBoolean("loggedIn", false);
        if (isLoggedIn) {
            startActivityForResult(new Intent(this, getTargetClass()), TARGET_REQUEST);
        } else {

            startActivityForResult(getLoginIntent(), LOGIN_REQUEST);
        }
    //    startIntro();
    }

    private void debugLog(String message) {

    }

  /*  private void startIntro(){
        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(ShowOffSLoginDispatcher.this, Intro.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();
    }*/
}
