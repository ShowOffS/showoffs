package in.showoffs.showoffs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by nagraj on 27/2/16.
 */
public class Utility {
	public static final String COVER_URL = "cover_url";
	public static final String CURRENT_STATUS ="current_status";
	public static final String CURRENT_PROFILE_PIC = "current_profile_pic_url";
    public static final String CURRENT_APP_ID = "current_app";
    public static final String CURRENT_APP_NAME = "current_app_name";
	static Context baseContext = null;

	public static SharedPreferences getSharedPreferences(){
		if(getBaseContext() == null) throw new RuntimeException("Utility class has not been initialized.");
		return PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
	}

	public static void savePreference(String key, Object value) {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		if (value instanceof String) {
			editor.putString(key,(String) value);
		}else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		}else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}

		editor.apply();

	}

	public static String getParametersFromUrl(String url,String param) {
		try {
			Pattern p = Pattern.compile("(?<=" +param + "=).*?(?=&|$)");
			Matcher m = p.matcher(url);
			while (m.find()) {
				System.out.println(m.group());
				return m.group();
			}
		} catch (PatternSyntaxException ex) {
			Log.d("getParametersFromUrl :", "Couldn't get required parameter value");
		}
		return null;
	}

	public static Context getBaseContext() {
		return baseContext;
	}

	public static void setBaseContext(Context context){baseContext = context;}
}
