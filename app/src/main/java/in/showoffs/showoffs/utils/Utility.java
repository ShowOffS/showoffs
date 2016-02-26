package in.showoffs.showoffs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.Validate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by GRavi on 26-02-2016.
 */
public class Utility {

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

    public static Context getBaseContext() {
        return baseContext;
    }

    public static void setBaseContext(Context baseContext) {
        Utility.baseContext = baseContext;
    }
}
