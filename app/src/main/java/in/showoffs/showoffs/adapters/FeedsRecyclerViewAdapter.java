package in.showoffs.showoffs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.showoffs.showoffs.R;
import in.showoffs.showoffs.models.Feeds;

/**
 * Created by nagraj on 28/2/16.
 */
public class FeedsRecyclerViewAdapter extends RecyclerView.Adapter<FeedsRecyclerViewAdapter.ViewHolder>{

	Feeds feeds = null;

	public Feeds getFeeds() {
		return feeds;
	}

	public void setFeeds(Feeds feeds) {
		this.feeds = feeds;
	}

	public FeedsRecyclerViewAdapter(){}

	public FeedsRecyclerViewAdapter(Feeds feeds) {
		this.feeds = feeds;
	}

	@Override
	public FeedsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.feed_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(FeedsRecyclerViewAdapter.ViewHolder holder, int position) {
		holder.textView.setText(feeds.getData().get(position).getMessage());
	}

	@Override
	public int getItemCount() {
		return feeds!=null?feeds.getData().size():0;
	}



	static class ViewHolder extends RecyclerView.ViewHolder{

		@Bind(R.id.textView)
		TextView textView;
		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this,itemView);
		}
	}
}
