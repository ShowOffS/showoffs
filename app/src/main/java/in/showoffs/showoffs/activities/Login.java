package in.showoffs.showoffs.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.showoffs.showoffs.R;
import in.showoffs.showoffs.ShowOffS;
import in.showoffs.showoffs.utils.FButils;
import in.showoffs.showoffs.utils.LoginDispatcher;

public class Login extends BaseActivity {
    @Bind(R.id.loginButton)
    LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loginButton.setReadPermissions(Arrays.asList("user_photos","user_posts"));

        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(ShowOffS.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                FButils.saveAccessToken(accessToken);

                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor e = getPrefs.edit();
                e.putBoolean("loggedIn", true);

                //  Apply changes
                e.apply();

                startActivity(new Intent(Login.this, LoginDispatcher.class));
                finish();
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShowOffS.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        super.onPostCreate(savedInstanceState);
        float fbIconScale = 1.45F;
        Drawable drawable = this.getResources().getDrawable(
                com.facebook.R.drawable.com_facebook_button_icon);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * fbIconScale),
                (int) (drawable.getIntrinsicHeight() * fbIconScale));
        loginButton.setCompoundDrawables(drawable, null, null, null);
        loginButton.setCompoundDrawablePadding(this.getResources().
                getDimensionPixelSize(R.dimen.fb_margin_override_textpadding));
        loginButton.setPadding(
                this.getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_lr),
                this.getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_top),
                0,
                this.getResources().getDimensionPixelSize(
                        R.dimen.fb_margin_override_bottom));
    }
}
