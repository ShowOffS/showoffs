package in.showoffs.showoffs.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.showoffs.showoffs.R;
import in.showoffs.showoffs.fragments.CategoryFragment;
import in.showoffs.showoffs.fragments.FeedsFragment;
import in.showoffs.showoffs.interfaces.GetProfilePicListener;
import in.showoffs.showoffs.interfaces.StatusReceivedListener;
import in.showoffs.showoffs.utils.FButils;
import in.showoffs.showoffs.utils.Utility;

public class Dashboard extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, StatusReceivedListener, GetProfilePicListener {

	private AccountHeader headerResult;
	private Drawer result;
	private ProfileTracker profileTracker;

	private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
	private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.9f;
	private static final int ALPHA_ANIMATIONS_DURATION = 200;

	private boolean mIsTheTitleVisible = false;
	private boolean mIsTheTitleContainerVisible = true;

	private LinearLayout mTitleContainer;
	@Bind(R.id.title)
	TextView mTitle;
	private AppBarLayout mAppBarLayout;

	@Bind(R.id.main_framelayout_title)
	FrameLayout tileFrame;

	private Target target;

	@Bind(R.id.profilePictureView)
	CircleImageView profilePictureView;
	@Bind(R.id.backdrop)
	ImageView backdrop;
	Toolbar toolbar;

//	@Bind(R.id.splash)
//	ImageView splash;

	@Bind(R.id.main_username)
	TextView username;
	@Bind(R.id.main_status)
	TextView status;

	@Bind(R.id.toolbar_layout)
	CollapsingToolbarLayout collapsingToolbarLayout;

	private LoginManager loginManager;
	private CallbackManager callbackManager;

	@Bind(R.id.app_bar)
	AppBarLayout appBar;

	@Bind(R.id.root_layout)
	CoordinatorLayout rootLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_dashboard);
		ButterKnife.bind(this);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

		toolbar.setTitle("");
		mAppBarLayout.addOnOffsetChangedListener(this);

		setSupportActionBar(toolbar);
		startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, FeedsFragment.newInstance(1))
                .commit();

		profileTracker = new ProfileTracker() {
			@Override
			protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
				updateUI();
				// It's possible that we were waiting for Profile to be populated in order to
				// post a status update.
				//    handlePendingAction();
			}
		};

		FButils.getStatus(this);

		setCoverPhoto();

		target = new Target() {
			@Override
			public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
				Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
					public void onGenerated(Palette palette) {
						profilePictureView.setImageBitmap(bitmap);
//                        backdrop.setImageBitmap(bitmap);
						int defaultColor = getResources().getColor(R.color.colorPrimary);
					//	backdrop.setBackgroundColor(palette.getDarkMutedColor(defaultColor));
						collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(defaultColor));
						collapsingToolbarLayout.setContentScrimColor(palette.getDarkMutedColor(defaultColor));
					}
				});
			}

			@Override
			public void onBitmapFailed(Drawable errorDrawable) {
//				splash.setVisibility(View.GONE);
			}

			@Override
			public void onPrepareLoad(Drawable placeHolderDrawable) {

			}
		};

//        setUpDrawer(savedInstanceState);
	}

	private void setCoverPhoto() {
		final String cachedUrl = Utility.getSharedPreferences().getString(Utility.COVER_URL, null);
		if(cachedUrl != null){
			Picasso.with(Dashboard.this).load(cachedUrl).into(backdrop);
		}
		GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), Profile.getCurrentProfile().getId());
		Bundle parameters = new Bundle();
		parameters.putString("fields", "cover");
		request.setParameters(parameters);
		request.setCallback(new GraphRequest.Callback() {
			@Override
			public void onCompleted(GraphResponse response) {
				String url = "";
				try {
					if(response == null || response.getJSONObject() == null) return;
					JSONObject jsonObject = (JSONObject) response.getJSONObject().get("cover");
					url = jsonObject.get("source").toString();

					if (cachedUrl != null && url != null) {
						if (!cachedUrl.equals(url)) {
							Picasso.with(Dashboard.this).load(url).into(backdrop);
							Utility.savePreference(Utility.COVER_URL, url);
						}else{
							Log.d("setCoverPhoto", "Cover Image not changed...");
						}
					}else{
						Utility.savePreference(Utility.COVER_URL, url);
						Picasso.with(Dashboard.this).load(url).into(backdrop);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});
		request.executeAsync();
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
//		profileTracker.stopTracking();
	}

	private void updateUI() {
		Profile profile = Profile.getCurrentProfile();
		if (profile != null) {
			FButils.getProfilePicture(this);
			username.setText(profile.getName());
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
			CategoryFragment categoryFragment = new CategoryFragment();
			categoryFragment.show(getSupportFragmentManager(),"BOTTOM");
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

//		if (collapsingToolbarLayout.getHeight() + offset < 2 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
//			swipeRefreshLayout.setEnabled(false);
//		} else {
//			swipeRefreshLayout.setEnabled(true);
//		}
	}

	private void handleToolbarTitleVisibility(float percentage) {
		if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

			if (!mIsTheTitleVisible) {
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
			if (mIsTheTitleContainerVisible) {
				startAlphaAnimation(tileFrame, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
				mIsTheTitleContainerVisible = false;
			}

		} else {

			if (!mIsTheTitleContainerVisible) {
				startAlphaAnimation(tileFrame, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
				mIsTheTitleContainerVisible = true;
			}
		}
	}

	public static void startAlphaAnimation(View v, long duration, int visibility) {
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

//	private void changeApp(String appId) {
//		FacebookSdk.setApplicationId(appId);
//		callbackManager = CallbackManager.Factory.create();
//		loginManager = getLoginManager();
//		loginManager.logInWithReadPermissions(
//				this,
//				Arrays.asList("public_profile", "user_friends"));
//		loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//			@Override
//			public void onSuccess(LoginResult loginResult) {
//				AccessToken accessToken = loginResult.getAccessToken();
//				FButils.saveAccessToken(accessToken);
//				AccessToken accessToken1 = FButils.getAccessToken(FacebookSdk.getApplicationId());
//				Snackbar.make(toolbar, accessToken1.getToken(), Snackbar.LENGTH_INDEFINITE).show();
//			}
//
//			@Override
//			public void onCancel() {
//
//			}
//
//			@Override
//			public void onError(FacebookException error) {
//
//			}
//		});
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (callbackManager != null)
			callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void gotStatus(String statusMessage) {
		status.setText(statusMessage);
	}

	@Override
	public void gotProfilePic(String url) {
		Picasso.with(this).load(url).into(target);
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
