package in.showoffs.showoffs.models;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import in.showoffs.showoffs.interfaces.PostMessageListener;
import in.showoffs.showoffs.utils.FButils;

/**
 * Created by nagraj on 27/2/16.
 */
public class Post {
	private String appId = null;
	private String message = null;
	private String profileId = null;
	private Activity activity = null;
	private Fragment fragment = null;
	private android.support.v4.app.Fragment supportFragment = null;
	private PostMessageListener postMessageListener = null;


	public String getAppId() {
		return appId;
	}

	public Post setAppId(String appId) {
		this.appId = appId;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Post setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getProfileId() {
		return profileId;
	}

	public Post setProfileId(String profileId) {
		this.profileId = profileId;
		return this;
	}

	public Activity getActivity() {
		return activity;
	}

	public Post setActivity(Activity activity) {
		this.activity = activity;
		return this;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public Post setFragment(Fragment fragment) {
		this.fragment = fragment;
		return this;
	}

	public android.support.v4.app.Fragment getSupportFragment() {
		return supportFragment;
	}

	public Post setSupportFragment(android.support.v4.app.Fragment supportFragment) {
		this.supportFragment = supportFragment;
		return this;
	}

	public PostMessageListener getPostMessageListener() {
		return postMessageListener;
	}

	public Post setPostMessageListener(PostMessageListener postMessageListener) {
		this.postMessageListener = postMessageListener;
		return this;
	}

	public void submit() {
		validate();

		GraphRequest request = new GraphRequest(FButils.getAccessToken(appId), profileId + "/feed");
		Bundle parameters = new Bundle();
		parameters.putString("message", message);
		request.setHttpMethod(HttpMethod.POST);
		request.setParameters(parameters);
		request.setCallback(new GraphRequest.Callback() {
			@Override
			public void onCompleted(GraphResponse response) {
				if (response.getError() == null) {
					postMessageListener.posted(true);
				}else{
					postMessageListener.posted(false);
				}
			}
		});
		request.executeAsync();

	}

	private void validate() {
		if(appId == null) {throw new RuntimeException("Please provide appId");}
		if(message == null) {throw new RuntimeException("Please provide a message");}
		if (profileId == null) profileId = Profile.getCurrentProfile().getId();
		if(postMessageListener == null) {
			if (activity != null && activity instanceof PostMessageListener) {
				postMessageListener = (PostMessageListener) activity;
			} else if (fragment != null && fragment instanceof PostMessageListener) {
				postMessageListener = (PostMessageListener) fragment;
			} else if (supportFragment != null && supportFragment instanceof PostMessageListener) {
				postMessageListener = (PostMessageListener) supportFragment;
			} else {
				throw new ClassCastException("Must implement PostMessageListener");
			}
		}
	}
}
