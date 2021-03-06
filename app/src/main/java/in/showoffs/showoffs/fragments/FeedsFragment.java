package in.showoffs.showoffs.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import in.showoffs.showoffs.activities.Dashboard;
import in.showoffs.showoffs.adapters.FeedsRecyclerViewAdapter;
import in.showoffs.showoffs.interfaces.ChangeAppListener;
import in.showoffs.showoffs.interfaces.FeedFetchListener;
import in.showoffs.showoffs.interfaces.PostBoxListener;
import in.showoffs.showoffs.interfaces.PostMessageListener;
import in.showoffs.showoffs.interfaces.QuickPostListener;
import in.showoffs.showoffs.models.Feeds;
import in.showoffs.showoffs.models.Post;
import in.showoffs.showoffs.utils.FButils;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFeedListFragmentInteraction}
 * interface.
 */
public class FeedsFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener,ChangeAppListener, PostMessageListener, FeedFetchListener, QuickPostListener, PostBoxListener {

	// TODO: Customize parameter argument names
	private static final String ARG_COLUMN_COUNT = "column-count";
	// TODO: Customize parameters
	private int mColumnCount = 1;
	private OnFeedListFragmentInteraction mListener;
	private CallbackManager callbackManager;
	@Bind(R.id.feeds_recycler_view)
	RecyclerView recyclerView;

	LinearLayoutManager layoutManager;

	CollapsingToolbarLayout collapsingToolbarLayout = null;

	boolean mIsLoading = false;

	public boolean isLoading = true;

	@Bind(R.id.status_text)
	EditText status;
	@Bind(R.id.swipeRefreshFeeds)
	SwipeRefreshLayout swipeRefreshLayout;

	FeedsRecyclerViewAdapter recyclerViewAdapter;

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
					loadMoreItems();
				}
			}
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public FeedsFragment() {
	}

	// TODO: Customize parameter initialization
	@SuppressWarnings("unused")
	public static FeedsFragment newInstance(int columnCount) {
		FeedsFragment fragment = new FeedsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_COLUMN_COUNT, columnCount);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_feeds_list, container, false);
		ButterKnife.bind(this, view);
		FButils.getFeed(this);
		// Set the adapter
		layoutManager = new LinearLayoutManager(view.getContext());
		recyclerViewAdapter = new FeedsRecyclerViewAdapter(view.getContext());
        recyclerViewAdapter.setQuickPostListener(this);
        recyclerViewAdapter.setPostBoxListener(this);
		if (mColumnCount <= 1) {
			recyclerView.setLayoutManager(layoutManager);
		} else {
			recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
		}
		recyclerView.setNestedScrollingEnabled(true);
		recyclerView.setAdapter(recyclerViewAdapter);
		recyclerView.addOnScrollListener(mRecyclerViewOnScrollListener);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(true);
			//	isLoading = true;
				getFeed();
			}
		});

		return view;
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFeedListFragmentInteraction) {
			mListener = (OnFeedListFragmentInteraction) context;
			collapsingToolbarLayout = ((Dashboard)mListener).getCollapsingToolbarLayout();
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFeedListFragmentInteraction");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	private void loadMoreItems() {
		mIsLoading = true;
		FButils.getPaginatedFeed(this, recyclerViewAdapter.getNext());
	}

	public void getFeed() {
		if (recyclerViewAdapter.getItemCount() == 0) {
			FButils.getFeed(this);
		} else {
			FButils.getPaginatedFeed(this, recyclerViewAdapter.getPrevious(), "since");

		}
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
			Toast.makeText(FeedsFragment.this.getContext(), "Successfully posted", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(FeedsFragment.this.getContext(), "Error occurred. Try Again.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onFeedFetchedListener(Feeds feeds) {
		if (mIsLoading) {
			recyclerViewAdapter.add(feeds);
			mIsLoading = false;
		} else if (swipeRefreshLayout.isRefreshing()) {
			recyclerViewAdapter.addLatestFeeds(feeds);
			recyclerViewAdapter.notifyDataSetChanged();
			if (swipeRefreshLayout != null)
				swipeRefreshLayout.setRefreshing(false);
		} else {
			recyclerViewAdapter.setFeeds(feeds);
			recyclerViewAdapter.notifyDataSetChanged();
			isLoading = false;
			if (swipeRefreshLayout != null)
				swipeRefreshLayout.setRefreshing(false);
		}
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
		mListener.onListFragmentInteraction(offset);
		if (collapsingToolbarLayout.getHeight() + offset < 2 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
			swipeRefreshLayout.setEnabled(false);
		} else {
			swipeRefreshLayout.setEnabled(true);
		}
	}

    @Override
    public void quickPost(String status) {
        String appId = "308396479178993";
        if (FButils.hasPublishPermissions(appId)) {
            //	FButils.postMessage(appId, status.getText().toString(), this);
            new Post()
                    .setAppId(appId)
                    .setMessage(status)
                    .setPostMessageListener(this)
                    .submit();

        } else {
            callbackManager = CallbackManager.Factory.create();
            FButils.changeApp(appId, this, callbackManager);
        }
    }

    @Override
    public void postBoxListener(int viewId) {
        switch (viewId) {
            case R.id.status_button:{
                Snackbar.make(getView(), "Status Button", Snackbar.LENGTH_INDEFINITE).show();
                break;
            }
            case R.id.photo_button:{
                Snackbar.make(getView(), "Photo Button", Snackbar.LENGTH_INDEFINITE).show();
                break;
            }
            case R.id.checkin_button:{
                Snackbar.make(getView(), "Checkin Button", Snackbar.LENGTH_INDEFINITE).show();
                break;
            }
        }
    }


    /**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFeedListFragmentInteraction {
		// TODO: Update argument type and name
		void onListFragmentInteraction(int offset);
	}
}
