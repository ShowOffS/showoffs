package in.showoffs.showoffs.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.showoffs.showoffs.R;

public class Dashboard extends BaseActivity implements AppBarLayout.OnOffsetChangedListener{

    private AccountHeader headerResult;
    private Drawer result;
    private ProfileTracker profileTracker;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;

    private Target target;

    @Bind(R.id.profilePictureView)
    CircleImageView profilePictureView;
    @Bind(R.id.main_imageview_placeholder)
    ImageView backdrop;
    @Bind(R.id.main_username)
    TextView username;
    @Bind(R.id.main_status)
    TextView status;
    Toolbar toolbar;

    @Bind(R.id.splash)
    ImageView splash;

    @Bind(R.id.main_collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.main_framelayout_title)
    FrameLayout titleFrame;
    private LoginManager loginManager;
    private CallbackManager callbackManager;

    /*@Bind(R.id.appbar)
    AppBarLayout appBar;

    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);

        toolbar.setTitle("");
        mAppBarLayout.addOnOffsetChangedListener(this);

        setSupportActionBar(toolbar);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);


        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                updateUI();
                // It's possible that we were waiting for Profile to be populated in order to
                // post a status update.
            //    handlePendingAction();
            }
        };

        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),Profile.getCurrentProfile().getId());
        Bundle parameters = new Bundle();
        parameters.putString("fields","cover");
        request.setParameters(parameters);
        request.setCallback(new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                String url = "";
                try {
                    JSONObject jsonObject = (JSONObject) response.getJSONObject().get("cover");
                    Picasso.with(Dashboard.this).load(jsonObject.get("source").toString()).into(backdrop);
                    Snackbar.make(toolbar, jsonObject.get("source").toString(), Snackbar.LENGTH_INDEFINITE).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        request.executeAsync();
        /*new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                Profile.getCurrentProfile().getId() + "?fields=cover",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Snackbar.make(toolbar,response.toString(),Snackbar.LENGTH_INDEFINITE).show();
                    }
                }
        ).executeAsync();*/


        target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        profilePictureView.setImageBitmap(bitmap);
//                        backdrop.setImageBitmap(bitmap);
                        int defaultColor = getResources().getColor(R.color.colorPrimary);
                        toolbar.setBackgroundColor(palette.getDarkMutedColor(defaultColor));
                        titleFrame.setBackgroundColor(palette.getDarkMutedColor(defaultColor));
                        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(defaultColor));
                        collapsingToolbarLayout.setContentScrimColor(palette.getDarkMutedColor(defaultColor));
                        getWindow().setStatusBarColor(palette.getDarkMutedColor(defaultColor));
                        splash.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                splash.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

//        setUpDrawer(savedInstanceState);
    }

    private void setUpDrawer(Bundle savedInstanceState) {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                        //        .withHeaderBackground(R.drawable.splash)
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withFullscreen(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new SectionDrawerItem().withName("Header"),
                        new SecondaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName("Help").withIcon(FontAwesome.Icon.faw_question).withEnabled(false),
                        new SecondaryDrawerItem().withName("Open Source").withIcon(FontAwesome.Icon.faw_github),
                        new SecondaryDrawerItem().withName("Contact").withIcon(FontAwesome.Icon.faw_bullhorn)
                )
                .withSavedInstance(savedInstanceState)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    private void updateUI() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            Picasso.with(this).load(profile.getProfilePictureUri(200,200)).into(target);
//            Picasso.with(this).load(profile.getProfilePictureUri(250,250)).into(backdrop);
            username.setText(profile.getName());
            status.setText(profile.getLinkUri().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            startActivity(new Intent(this,ScrollingActivity.class));
            /*getLoginManager().logOut();
            SharedPreferences getPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor e = getPrefs.edit();
            e.putBoolean("loggedIn", false);

            //  Apply changes
            e.apply();

            startActivity(new Intent(Dashboard.this, LoginDispatcher.class));*/
            changeApp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    LoginManager getLoginManager() {
        if (loginManager == null) {
            loginManager = LoginManager.getInstance();
        }
        return loginManager;
    }

    private void changeApp(){
        FacebookSdk.setApplicationId("252112158183306");
        callbackManager = CallbackManager.Factory.create();
        loginManager = getLoginManager();
        loginManager.logInWithReadPermissions(
                this,
                Arrays.asList("public_profile","user_friends"));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor e = getPrefs.edit();
                e.putString("252112158183306", loginResult.getAccessToken().toString());


                //  Apply changes
                e.apply();
                Snackbar.make(toolbar,loginResult.getAccessToken().toString(), Snackbar.LENGTH_INDEFINITE).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        Bundle parameters = new Bundle();
        parameters.putInt("logging_in", (AccessToken.getCurrentAccessToken() != null) ? 0 : 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /*public  void expandToolbar(Bitmap bmp) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setTopAndBottomOffset(0);
        behavior.onNestedPreScroll(rootLayout, appBar, null, 0, bmp.getHeight() - 512, new int[2]);
        params.setBehavior(behavior);
        appBar.setLayoutParams(params);
    }*/
}
