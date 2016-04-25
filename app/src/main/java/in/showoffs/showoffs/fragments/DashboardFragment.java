package in.showoffs.showoffs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.showoffs.showoffs.R;
import in.showoffs.showoffs.adapters.FeedsRecyclerViewAdapter;
import in.showoffs.showoffs.interfaces.ChangeAppListener;
import in.showoffs.showoffs.interfaces.FeedFetchListener;
import in.showoffs.showoffs.interfaces.PostMessageListener;
import in.showoffs.showoffs.models.Feeds;
import in.showoffs.showoffs.models.Post;
import in.showoffs.showoffs.utils.FButils;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment implements ChangeAppListener, PostMessageListener, FeedFetchListener {

	@Bind(R.id.status_text)
	EditText status;
	@Bind(R.id.feedsRecyclerView)
	RecyclerView recyclerView;
	LinearLayoutManager layoutManager;
	private CallbackManager callbackManager;
	private FeedsRecyclerViewAdapter recyclerViewAdapter;
	public static boolean mIsLoading = true;

	public boolean isLoading = true;

	@Bind(R.id.swipeRefreshFeeds)
	SwipeRefreshLayout swipeRefreshLayout;

	// region Listeners
	private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			super.onScrollStateChanged(recyclerView, newState);
		}

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			int visibleItemCount = layoutManager.getChildCount();
			int totalItemCount = layoutManager.getItemCount();
			int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

			if (!mIsLoading) {
				if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
						&& firstVisibleItemPosition >= 0) {
//					loadMoreItems();
                    Log.d("visibleItemCount : " , visibleItemCount + "");
                    Toast.makeText(getContext(), "visibleItemCount : " + visibleItemCount + "", Toast.LENGTH_SHORT).show();
                }
			}
		}
	};

	private void loadMoreItems() {
		mIsLoading = true;
		FButils.getPaginatedFeed(recyclerViewAdapter, recyclerViewAdapter.getNext());
	}

	public DashboardFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
		ButterKnife.bind(this, view);
		FButils.getFeed(this);
		layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);
		recyclerViewAdapter = new FeedsRecyclerViewAdapter(getContext());
		recyclerView.setAdapter(recyclerViewAdapter);
		recyclerView.setHasFixedSize(false);
		recyclerView.setNestedScrollingEnabled(true);
		recyclerView.addOnScrollListener(mRecyclerViewOnScrollListener);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getFeed();
				swipeRefreshLayout.setRefreshing(true);
				isLoading = true;
			}
		});
		return view;
	}

	@OnClick(R.id.post)
	public void postStatus(View view) {
		String appId = "308396479178993";
		if (FButils.hasPublishPermissions(appId)) {
			//	FButils.postMessage(appId, status.getText().toString(), this);
			new Post()
					.setAppId(appId)
					.setMessage(status.getText().toString())
					.setPostMessageListener(this)
					.submit();

		} else {
			callbackManager = CallbackManager.Factory.create();
			FButils.changeApp(appId, this, callbackManager);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (callbackManager != null) {
			callbackManager.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void appChanged() {
		Snackbar.make(getView(), "Abb karo post...", Snackbar.LENGTH_INDEFINITE).show();
	}

	@Override
	public void posted(boolean success) {
		if (success) {
			Toast.makeText(DashboardFragment.this.getContext(), "Successfully posted", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(DashboardFragment.this.getContext(), "Error occurred. Try Again.", Toast.LENGTH_SHORT).show();
		}
	}

	public void getFeed() {
		FButils.getFeed(this);
	}

	@Override
	public void onFeedFetchedListener(Feeds feeds) {
		recyclerViewAdapter.setFeeds(feeds);
		recyclerViewAdapter.notifyDataSetChanged();
		isLoading = false;
		mIsLoading = false;
		if (swipeRefreshLayout != null)
			swipeRefreshLayout.setRefreshing(false);
	}
}
