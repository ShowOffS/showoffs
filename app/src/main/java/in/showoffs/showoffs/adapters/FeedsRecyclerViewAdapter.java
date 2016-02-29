package in.showoffs.showoffs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.showoffs.showoffs.R;
import in.showoffs.showoffs.fragments.DashboardFragment;
import in.showoffs.showoffs.interfaces.FeedFetchListener;
import in.showoffs.showoffs.models.Datum;
import in.showoffs.showoffs.models.Feeds;
import in.showoffs.showoffs.utils.Utility;

/**
 * Created by nagraj on 28/2/16.
 */
public class FeedsRecyclerViewAdapter extends RecyclerView.Adapter<FeedsRecyclerViewAdapter.ViewHolder> implements FeedFetchListener{

	Feeds feeds = null;
	Context context = null;
	String next = null;
	String previous = null;

	List<Datum> data = new ArrayList<>();

	public Feeds getFeeds() {
		return feeds;
	}

	public void setFeeds(Feeds feeds) {
		this.feeds = feeds;
		setPrevious(feeds.getPaging().getPrevious());
		add(feeds);
	}

	public FeedsRecyclerViewAdapter(Context context) {
		this.context = context;
	}

	public FeedsRecyclerViewAdapter(Feeds feeds) {
		this.feeds = feeds;
	}

	@Override
	public FeedsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.feed_item, parent, false);
		return new ViewHolder(view);
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public void add(Feeds feeds) {
		data.addAll(feeds.getData());
		setNext(feeds.getPaging().getNext());
	}

	@Override
	public void onBindViewHolder(FeedsRecyclerViewAdapter.ViewHolder holder, int position) {
		String profilePicUrl = Utility.getSharedPreferences().getString(Utility.CURRENT_PROFILE_PIC, null);
		if (profilePicUrl != null) {
			Picasso.with(getContext()).load(profilePicUrl).into(holder.profile);
		}
		if (getItem(position).getMessage() == null || getItem(position).getMessage().isEmpty()) {
			holder.textView.setVisibility(View.GONE);
		} else {
			holder.textView.setText(getItem(position).getMessage());
		}
		holder.feedStory.setText((getItem(position).getStory() != null) ? getItem(position).getStory() : (Profile.getCurrentProfile() != null ? Profile.getCurrentProfile().getName() : ""));
		holder.feedTimeStamp.setText(getItem(position).getUpdatedTime());
		holder.feedApplication.setText(getItem(position).getApplication() != null ? getItem(position).getApplication().getName() : "");
		if (getItem(position).getFullPicture() != null) {
			Picasso.with(getContext()).load(getItem(position).getFullPicture()).into(holder.feedImage);
			if (holder.feedImage.getVisibility() == View.GONE) {
				holder.feedImage.setVisibility(View.VISIBLE);
			}
		} else {
			holder.feedImage.setVisibility(View.GONE);
		}

		if (getItem(position).getLikes().getSummary().getTotalCount() == 0 && getItem(position).getComments().getSummary().getTotalCount() == 0) {
			holder.likesCommentsContainer.setVisibility(View.GONE);
		} else {
			if (getItem(position).getLikes().getSummary().getTotalCount() > 0) {
				holder.totalLikes.setText(
						getItem(position).getLikes().getSummary().getTotalCount() == 1
								? getItem(position).getLikes().getData().get(0).getName()
								: getItem(position).getLikes().getData().get(0).getName() + " and "
								+ (getItem(position).getLikes().getSummary().getTotalCount() - 1) + " Others"
				);
			} else {
				holder.totalLikes.setVisibility(View.GONE);
				holder.likeIcon.setVisibility(View.GONE);
			}

			if (getItem(position).getComments().getSummary().getTotalCount() > 0) {
				holder.totalComments.setText(getItem(position).getComments().getSummary().getTotalCount() + " Comments");
			} else {
				holder.totalComments.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public int getItemCount() {
		return data.size() > 0 ? data.size() : 0;
	}

	private Datum getItem(int position) {
		return data.get(position);
	}

	public Context getContext() {
		if (context == null)
			throw new RuntimeException("Please provide context to the FeedRecyclerViewAdapter");
		return context;
	}

	@Override
	public void onFeedFetchedListener(Feeds feeds) {
		DashboardFragment.mIsLoading = false;
		add(feeds);
		notifyDataSetChanged();
	}


	static class ViewHolder extends RecyclerView.ViewHolder {

		@Bind(R.id.textView)
		TextView textView;
		@Bind(R.id.profilePictureView)
		CircleImageView profile;
		@Bind(R.id.feed_story)
		TextView feedStory;
		@Bind(R.id.feed_time_stamp)
		TextView feedTimeStamp;
		@Bind(R.id.feed_application)
		TextView feedApplication;
		@Bind(R.id.feed_privacy)
		ImageView feedPrivacy;

		@Bind(R.id.feed_image)
		ImageView feedImage;
		@Bind(R.id.feed_likes_comments_layout)
		RelativeLayout likesCommentsContainer;
		@Bind(R.id.feed_total_likes)
		TextView totalLikes;
		@Bind(R.id.feed_total_comments)
		TextView totalComments;
		@Bind(R.id.feed_like_icon)
		ImageView likeIcon;
		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
