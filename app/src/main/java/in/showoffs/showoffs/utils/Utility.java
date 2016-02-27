package in.showoffs.showoffs.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.internal.Validate;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;

import in.showoffs.showoffs.interfaces.ChangeAppListener;
import in.showoffs.showoffs.interfaces.PostMessageListner;
import in.showoffs.showoffs.interfaces.StatusReceivedListener;

/**
 * Created by GRavi on 26-02-2016.
 */
public class Utility {

	static StatusReceivedListener statusReceivedListener = null;
	static ChangeAppListener changeAppListener = null;
	static PostMessageListner postMessageListner = null;

	private static Context baseContext = null;

	// Constants related to JSON serialization.
	private static final int CURRENT_JSON_FORMAT = 1;
	private static final String VERSION_KEY = "version";
	private static final String EXPIRES_AT_KEY = "expires_at";
	private static final String PERMISSIONS_KEY = "permissions";
	private static final String DECLINED_PERMISSIONS_KEY = "declined_permissions";
	private static final String TOKEN_KEY = "token";
	private static final String SOURCE_KEY = "source";
	private static final String LAST_REFRESH_KEY = "last_refresh";
	private static final String APPLICATION_ID_KEY = "application_id";
	public static final String USER_ID_KEY = "user_id";
	private static LoginManager loginManager;

	static AccessToken createFromJSONObject(JSONObject jsonObject) throws JSONException {
		int version = jsonObject.getInt(VERSION_KEY);
		if (version > CURRENT_JSON_FORMAT) {
			throw new FacebookException("Unknown AccessToken serialization format.");
		}

		String token = jsonObject.getString(TOKEN_KEY);
		Date expiresAt = new Date(jsonObject.getLong(EXPIRES_AT_KEY));
		JSONArray permissionsArray = jsonObject.getJSONArray(PERMISSIONS_KEY);
		JSONArray declinedPermissionsArray = jsonObject.getJSONArray(DECLINED_PERMISSIONS_KEY);
		Date lastRefresh = new Date(jsonObject.getLong(LAST_REFRESH_KEY));
		AccessTokenSource source = AccessTokenSource.valueOf(jsonObject.getString(SOURCE_KEY));
		String applicationId = jsonObject.getString(APPLICATION_ID_KEY);
		String userId = jsonObject.getString(USER_ID_KEY);

		return new AccessToken(
				token,
				applicationId,
				userId,
				com.facebook.internal.Utility.jsonArrayToStringList(permissionsArray),
				com.facebook.internal.Utility.jsonArrayToStringList(declinedPermissionsArray),
				source,
				expiresAt,
				lastRefresh);
	}

	static JSONObject toJSONObject(AccessToken accessToken) throws JSONException {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(VERSION_KEY, CURRENT_JSON_FORMAT);
		jsonObject.put(TOKEN_KEY, accessToken.getToken());
		jsonObject.put(EXPIRES_AT_KEY, accessToken.getExpires().getTime());
		JSONArray permissionsArray = new JSONArray(accessToken.getPermissions());
		jsonObject.put(PERMISSIONS_KEY, permissionsArray);
		JSONArray declinedPermissionsArray = new JSONArray(accessToken.getDeclinedPermissions());
		jsonObject.put(DECLINED_PERMISSIONS_KEY, declinedPermissionsArray);
		jsonObject.put(LAST_REFRESH_KEY, accessToken.getLastRefresh().getTime());
		jsonObject.put(SOURCE_KEY, accessToken.getSource().name());
		jsonObject.put(APPLICATION_ID_KEY, accessToken.getApplicationId());
		jsonObject.put(USER_ID_KEY, accessToken.getUserId());

		return jsonObject;
	}

	public static void saveAccessToken(AccessToken accessToken) {
		Validate.notNull(accessToken, "accessToken");

		JSONObject jsonObject = null;
		try {
			jsonObject = Utility.toJSONObject(accessToken);
			SharedPreferences getPrefs = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor e = getPrefs.edit();
			e.putString(accessToken.getApplicationId(), jsonObject.toString())
					.apply();
		} catch (JSONException e) {
			// Can't recover
		}
	}

	public static AccessToken getAccessToken(String appId) {
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String jsonString = getPrefs.getString(appId, null);
		if (jsonString != null) {
			try {
				JSONObject jsonObject = new JSONObject(jsonString);
				return createFromJSONObject(jsonObject);
			} catch (JSONException e) {
				return null;
			}
		}
		return null;
	}

	public static boolean hasPublishPermissions(String appId) {
		AccessToken accessToken = getAccessToken(appId);
		return accessToken != null && accessToken.getPermissions().contains("publish_actions");

	}

	public static Context getBaseContext() {
		return baseContext;
	}

	public static void setBaseContext(Context baseContext) {
		Utility.baseContext = baseContext;
	}

	public static void getStatus(final Context context) {
		GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), Profile.getCurrentProfile().getId() + "/feed");
		Bundle parameters = new Bundle();
		parameters.putString("limit", "1");
		request.setParameters(parameters);
		request.setCallback(new GraphRequest.Callback() {
			@Override
			public void onCompleted(GraphResponse response) {
				try {
					JSONArray jsonArray = null;
					JSONObject jsonObject = null;
					if (response != null)
						jsonArray = (JSONArray) response.getJSONObject().get("data");
					if (jsonArray != null)
						jsonObject = (JSONObject) jsonArray.get(0);

					if (context instanceof StatusReceivedListener) {
						statusReceivedListener = (StatusReceivedListener) context;
					} else {
						throw new ClassCastException("Must Implement Status Received Listener");
					}
					statusReceivedListener.gotStatus(jsonObject.getString("message"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		request.executeAsync();
	}

	public static void changeApp(String appId, Activity activity, CallbackManager callbackManager) {
		changeApp(appId, activity, null, null, callbackManager);
	}

	public static void changeApp(String appId, Fragment fragment, CallbackManager callbackManager) {
		changeApp(appId, null, fragment, null, callbackManager);
	}

	public static void changeApp(String appId, android.support.v4.app.Fragment supportFragment, CallbackManager callbackManager) {
		changeApp(appId, null, null, supportFragment, callbackManager);
	}

	static void changeApp(String appId, final Activity activity, Fragment fragment, android.support.v4.app.Fragment supportFragment, CallbackManager callbackManager) {

		if (activity != null && activity instanceof ChangeAppListener) {
			changeAppListener = (ChangeAppListener) activity;
		} else if (fragment != null && fragment instanceof ChangeAppListener) {
			changeAppListener = (ChangeAppListener) fragment;
		} else if (supportFragment != null && supportFragment instanceof ChangeAppListener) {
			changeAppListener = (ChangeAppListener) supportFragment;
		} else {
			throw new ClassCastException("Must implement ChangeAppListener");
		}

		FacebookSdk.setApplicationId(appId);
		loginManager = getLoginManager();
		if (activity != null) {
			loginManager.logInWithPublishPermissions(activity, Arrays.asList("publish_actions"));
		} else if (fragment != null) {
			loginManager.logInWithPublishPermissions(fragment, Arrays.asList("publish_actions"));
		} else if (supportFragment != null) {
			loginManager.logInWithPublishPermissions(supportFragment, Arrays.asList("publish_actions"));
		} else {
			Toast.makeText(getBaseContext(), "Error Occured", Toast.LENGTH_SHORT).show();
			return;
		}

		loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				AccessToken accessToken = loginResult.getAccessToken();
				Utility.saveAccessToken(accessToken);
				changeAppListener.appChanged();
			}

			@Override
			public void onCancel() {

			}

			@Override
			public void onError(FacebookException error) {

			}
		});
	}

	public static void postMessage(String appId, String message, Activity activity) {
		postMessage(appId, message, activity, null, null);
	}

	public static void postMessage(String appId, String message, Fragment fragment) {
		postMessage(appId, message, null, fragment, null);
	}

	public static void postMessage(String appId, String message, android.support.v4.app.Fragment supportFragment) {
		postMessage(appId, message, null, null, supportFragment);
	}

	static void postMessage(String appId, String message, Activity activity, Fragment fragment, android.support.v4.app.Fragment supportFragment) {
		if (activity != null && activity instanceof PostMessageListner) {
			postMessageListner = (PostMessageListner) activity;
		} else if (fragment != null && fragment instanceof PostMessageListner) {
			postMessageListner = (PostMessageListner) fragment;
		} else if (supportFragment != null && supportFragment instanceof PostMessageListner) {
			postMessageListner = (PostMessageListner) supportFragment;
		} else {
			throw new ClassCastException("Must implement ChangeAppListener");
		}
		GraphRequest request = new GraphRequest(Utility.getAccessToken(appId), Profile.getCurrentProfile().getId() + "/feed");
		Bundle parameters = new Bundle();
		parameters.putString("message", message);
		request.setHttpMethod(HttpMethod.POST);
		request.setParameters(parameters);
		request.setCallback(new GraphRequest.Callback() {
			@Override
			public void onCompleted(GraphResponse response) {
				if (postMessageListner != null) {
					postMessageListner.posted(true);
				}
			}
		});
		request.executeAsync();
	}

	static LoginManager getLoginManager() {
		if (loginManager == null) {
			loginManager = LoginManager.getInstance();
		}
		return loginManager;
	}
}
