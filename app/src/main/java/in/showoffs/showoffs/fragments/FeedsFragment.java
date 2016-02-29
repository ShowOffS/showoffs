package in.showoffs.showoffs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.showoffs.showoffs.R;
import in.showoffs.showoffs.adapters.FeedsRecyclerViewAdapter;
import in.showoffs.showoffs.custom.WrappingLinearLayoutManager;
import in.showoffs.showoffs.fragments.dummy.DummyContent.DummyItem;
import in.showoffs.showoffs.interfaces.ChangeAppListener;
import in.showoffs.showoffs.interfaces.FeedFetchListener;
import in.showoffs.showoffs.interfaces.PostMessageListner;
import in.showoffs.showoffs.models.Feeds;
import in.showoffs.showoffs.utils.FButils;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FeedsFragment extends Fragment implements ChangeAppListener, PostMessageListner, FeedFetchListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    @Bind(R.id.feeds_recycler_view)
    RecyclerView recyclerView;

    WrappingLinearLayoutManager wrappingLinearLayoutManager;

    FeedsRecyclerViewAdapter recyclerViewAdapter;

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
        recyclerViewAdapter = new FeedsRecyclerViewAdapter(view.getContext());
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void appChanged() {

    }

    @Override
    public void onFeedFetchedListener(Feeds feeds) {
        recyclerViewAdapter.setFeeds(feeds);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void posted(boolean success) {

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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
