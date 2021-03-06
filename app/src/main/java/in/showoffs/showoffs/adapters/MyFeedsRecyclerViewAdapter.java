package in.showoffs.showoffs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.showoffs.showoffs.R;
import in.showoffs.showoffs.fragments.FeedsFragment;
import in.showoffs.showoffs.fragments.dummy.DummyContent.DummyItem;
import in.showoffs.showoffs.utils.Utility;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link FeedsFragment.OnFeedListFragmentInteraction}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFeedsRecyclerViewAdapter extends RecyclerView.Adapter<MyFeedsRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final FeedsFragment.OnFeedListFragmentInteraction mListener;

    public MyFeedsRecyclerViewAdapter(List<DummyItem> items, FeedsFragment.OnFeedListFragmentInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feeds, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position >= getItemCount() -3){
            Toast.makeText(Utility.getBaseContext(), "Position : " + position, Toast.LENGTH_SHORT).show();
        }
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(5);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
