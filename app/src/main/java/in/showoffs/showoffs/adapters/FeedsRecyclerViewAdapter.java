package in.showoffs.showoffs.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import in.showoffs.showoffs.R;
import in.showoffs.showoffs.fragments.DashboardFragment;
import in.showoffs.showoffs.interfaces.FeedFetchListener;
import in.showoffs.showoffs.interfaces.PostBoxListener;
import in.showoffs.showoffs.interfaces.QuickPostListener;
import in.showoffs.showoffs.models.Datum;
import in.showoffs.showoffs.models.Feeds;
import in.showoffs.showoffs.utils.Utility;

/**
 * Created by nagraj on 28/2/16.
 */
public class FeedsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FeedFetchListener {

    Feeds feeds = null;
    Context context = null;
    String next = null;
    String previous = null;
    String profilePicUrl = null;

    QuickPostListener quickPostListener = null;

    List<Datum> data = new ArrayList<>();
    private PostBoxListener postBoxListener = null;

    enum ViewType {
        PHOTO(1), STATUS(2), POSTBOX(0);
        private final int value;

        ViewType(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public Feeds getFeeds() {
        return feeds;
    }

    public void setFeeds(Feeds feeds) {
        profilePicUrl = Utility.getSharedPreferences().getString(Utility.CURRENT_PROFILE_PIC, null);
        this.feeds = feeds;
        setPrevious(feeds.getPaging().getPrevious());
        add(feeds);
    }

    public FeedsRecyclerViewAdapter(Context context) {
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.POSTBOX.getValue()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.postbox, parent, false);
            return new ViewHolderPostBox(view);
        } else if (viewType == ViewType.PHOTO.getValue()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_item_photo, parent, false);
            return new ViewHolderWithPhoto(view);
        } else if (viewType == ViewType.STATUS.getValue()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_item_status, parent, false);
            return new ViewHolderStatus(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == ViewType.PHOTO.getValue()) {
            ViewHolderWithPhoto holder = (ViewHolderWithPhoto) viewHolder;
            if (profilePicUrl != null) {
                Picasso.with(getContext()).load(profilePicUrl).into(holder.profile);
            }

            if (getItem(position).getMessage() == null || getItem(position).getMessage().isEmpty()) {
                holder.textView.setVisibility(View.GONE);
            } else {
                holder.textView.setText(getItem(position).getMessage());
                holder.textView.setVisibility(View.VISIBLE);
            }

            holder.feedStory.setText((getItem(position).getStory() != null) ? getItem(position).getStory() : (Profile.getCurrentProfile() != null ? Profile.getCurrentProfile().getName() : ""));
            holder.feedTimeStamp.setText(getItem(position).getUpdatedTime());
            holder.feedApplication.setText(getItem(position).getApplication() != null ? getItem(position).getApplication().getName() : "");

            Uri uri = Uri.parse(getItem(position).getFullPicture());
            holder.feedImage.setImageURI(uri);

            if (getItem(position).getLikes().getSummary().getTotalCount() == 0 && getItem(position).getComments().getSummary().getTotalCount() == 0) {
                holder.likesCommentsContainer.setVisibility(View.GONE);

            } else {
                holder.likesCommentsContainer.setVisibility(View.VISIBLE);

                if (getItem(position).getLikes().getSummary().getTotalCount() >= 1) {
                    holder.totalLikes.setText(
                            getItem(position).getLikes().getSummary().getTotalCount() == 1
                                    ? getItem(position).getLikes().getData().get(0).getName()
                                    : getItem(position).getLikes().getData().get(0).getName() + " and "
                                    + (getItem(position).getLikes().getSummary().getTotalCount() - 1) + " Others"
                    );
                    holder.totalLikes.setVisibility(View.VISIBLE);
                    holder.likeIcon.setVisibility(View.VISIBLE);
                } else {
                    holder.totalLikes.setVisibility(View.GONE);
                    holder.likeIcon.setVisibility(View.GONE);
                }

                if (getItem(position).getComments().getSummary().getTotalCount() > 0) {
                    holder.totalComments.setText(getItem(position).getComments().getSummary().getTotalCount() + " Comments");
                    holder.totalComments.setVisibility(View.VISIBLE);
                } else {
                    holder.totalComments.setVisibility(View.GONE);
                }
            }
        } else if (getItemViewType(position) == ViewType.STATUS.getValue()) {
            ViewHolderStatus holder = (ViewHolderStatus) viewHolder;
            if (profilePicUrl != null) {
                Picasso.with(getContext()).load(profilePicUrl).into(holder.profile);
            }

            if (getItem(position).getMessage() == null || getItem(position).getMessage().isEmpty()) {
                holder.textView.setVisibility(View.GONE);
            } else {
                holder.textView.setText(getItem(position).getMessage());
                holder.textView.setVisibility(View.VISIBLE);
            }

            holder.feedStory.setText((getItem(position).getStory() != null) ? getItem(position).getStory() : (Profile.getCurrentProfile() != null ? Profile.getCurrentProfile().getName() : ""));
            holder.feedTimeStamp.setText(getItem(position).getUpdatedTime());
            holder.feedApplication.setText(getItem(position).getApplication() != null ? getItem(position).getApplication().getName() : "");

            if (getItem(position).getLikes().getSummary().getTotalCount() == 0 && getItem(position).getComments().getSummary().getTotalCount() == 0) {
                holder.likesCommentsContainer.setVisibility(View.GONE);

            } else {
                holder.likesCommentsContainer.setVisibility(View.VISIBLE);

                if (getItem(position).getLikes().getSummary().getTotalCount() >= 1) {
                    holder.totalLikes.setText(
                            getItem(position).getLikes().getSummary().getTotalCount() == 1
                                    ? getItem(position).getLikes().getData().get(0).getName()
                                    : getItem(position).getLikes().getData().get(0).getName() + " and "
                                    + (getItem(position).getLikes().getSummary().getTotalCount() - 1) + " Others"
                    );
                    holder.totalLikes.setVisibility(View.VISIBLE);
                    holder.likeIcon.setVisibility(View.VISIBLE);
                } else {
                    holder.totalLikes.setVisibility(View.GONE);
                    holder.likeIcon.setVisibility(View.GONE);
                }

                if (getItem(position).getComments().getSummary().getTotalCount() > 0) {
                    holder.totalComments.setText(getItem(position).getComments().getSummary().getTotalCount() + " Comments");
                    holder.totalComments.setVisibility(View.VISIBLE);
                } else {
                    holder.totalComments.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setPostBoxListener(PostBoxListener postBoxListener) {
        this.postBoxListener = postBoxListener;
    }

    public void setQuickPostListener(QuickPostListener quickPostListener){
        this.quickPostListener = quickPostListener;
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
        notifyDataSetChanged();
    }

    public void addLatestFeeds(Feeds feeds) {
        if (feeds.getPaging() != null) {
            setPrevious(feeds.getPaging().getPrevious());
            data.addAll(0, feeds.getData());
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size() > 0 ? data.size() + 1 : 1;
    }

    private Datum getItem(int position) {
        return data.get(position - 1);
    }

    public Context getContext() {
        if (context == null)
            throw new RuntimeException("Please provide context to the FeedRecyclerViewAdapter");
        return context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ViewType.POSTBOX.getValue();
        } else if (getItem(position).getFullPicture() != null) {
            return ViewType.PHOTO.getValue();
        } else {
            return ViewType.STATUS.getValue();
        }
    }

    @Override
    public void onFeedFetchedListener(Feeds feeds) {
        DashboardFragment.mIsLoading = false;
        add(feeds);
        notifyDataSetChanged();
    }


    static class ViewHolderWithPhoto extends RecyclerView.ViewHolder {

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
        SimpleDraweeView feedImage;
        @Bind(R.id.feed_likes_comments_layout)
        RelativeLayout likesCommentsContainer;
        @Bind(R.id.feed_total_likes)
        TextView totalLikes;
        @Bind(R.id.feed_total_comments)
        TextView totalComments;
        @Bind(R.id.feed_like_icon)
        ImageView likeIcon;
        @Bind(R.id.card)
        CardView cardView;

        public ViewHolderWithPhoto(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ViewHolderStatus extends RecyclerView.ViewHolder {

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

        @Bind(R.id.feed_likes_comments_layout)
        RelativeLayout likesCommentsContainer;
        @Bind(R.id.feed_total_likes)
        TextView totalLikes;
        @Bind(R.id.feed_total_comments)
        TextView totalComments;
        @Bind(R.id.feed_like_icon)
        ImageView likeIcon;

        @Bind(R.id.card)
        CardView cardView;

        public ViewHolderStatus(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderPostBox extends RecyclerView.ViewHolder {

        @Bind(R.id.status_text)
        EditText statusText;
        @Bind(R.id.postbox_post)
        Button post;
        @Bind(R.id.status_button)
        Button statusButton;
        @Bind(R.id.photo_button)
        Button photoButton;
        @Bind(R.id.checkin_button)
        Button checkinButton;

        public ViewHolderPostBox(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.postbox_post)
        public void quickPost(View view){
            if(null != quickPostListener){
                quickPostListener.quickPost(statusText.getText().toString());
            }else {
                throw new ClassCastException("QuickPostListener must be set from the calling fragment / activity.");
            }
        }

        @OnClick({R.id.status_button, R.id.photo_button, R.id.checkin_button})
        public void postBox(View view) {
            if (null != postBoxListener) {
                postBoxListener.postBoxListener(view.getId());
            }else {
                throw new ClassCastException("PostBoxListener must be set from the calling fragment / activity.");
            }
        }
    }
}
