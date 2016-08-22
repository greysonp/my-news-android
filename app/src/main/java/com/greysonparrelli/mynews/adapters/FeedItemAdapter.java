package com.greysonparrelli.mynews.adapters;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greysonparrelli.mynews.R;
import com.greysonparrelli.mynews.fragments.FeedFragment;
import com.greysonparrelli.mynews.models.FeedItem;
import com.greysonparrelli.mynews.utils.HtmlUtil;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class FeedItemAdapter extends RecyclerView.Adapter<FeedItemAdapter.FeedItemViewHolder> {

    private List<FeedItem> mFeedItem;
    private FeedFragment.FeedController mController;

    public FeedItemAdapter(FeedFragment.FeedController controller) {
        mController = controller;
        mFeedItem = new ArrayList<>();
    }

    @Override
    public FeedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_item, parent, false);
        return new FeedItemViewHolder(view, mController);
    }

    @Override
    public void onBindViewHolder(FeedItemViewHolder holder, int position) {
        holder.bind(mFeedItem.get(position), position);
    }

    @Override
    public void onViewRecycled(FeedItemViewHolder holder) {
        holder.recycle();
    }

    @Override
    public int getItemCount() {
        return mFeedItem.size();
    }

    public void setFeedItems(List<FeedItem> feedItems) {
        mFeedItem = feedItems;
        notifyDataSetChanged();
    }

    public static class FeedItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mThumbnail;
        private TextView mTitle;
        private TextView mSnippet;
        private FeedFragment.FeedController mController;

        public FeedItemViewHolder(View itemView, FeedFragment.FeedController controller) {
            super(itemView);
            mController = controller;

            mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mSnippet = (TextView) itemView.findViewById(R.id.snippet);
        }

        public void bind(final FeedItem feedItem, int position) {
            mTitle.setText(feedItem.title);
            ViewCompat.setTransitionName(
                    mTitle,
                    itemView.getContext().getString(R.string.title_transition_name) + position);
            mSnippet.setText(HtmlUtil.getText(feedItem.content));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mController.showFeedItem(feedItem, mTitle);
                }
            });

            List<String> imageUrls = HtmlUtil.getImgUrls(feedItem.content);
            if (imageUrls.size() > 0) {
                Glide.with(itemView.getContext())
                        .load(imageUrls.get(0))
                        .centerCrop()
                        .bitmapTransform(new RoundedCornersTransformation(itemView.getContext(), 20, 0))
                        .into(mThumbnail);
            }
        }

        public void recycle() {
            itemView.setOnClickListener(null);
        }
    }
}
