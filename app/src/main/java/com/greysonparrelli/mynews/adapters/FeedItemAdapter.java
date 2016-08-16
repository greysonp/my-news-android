package com.greysonparrelli.mynews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greysonparrelli.mynews.R;
import com.greysonparrelli.mynews.fragments.FeedFragment;
import com.greysonparrelli.mynews.models.FeedItem;

import java.util.ArrayList;
import java.util.List;

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
        holder.bind(mFeedItem.get(position));
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

        private TextView mTitle;
        private TextView mLink;
        private FeedFragment.FeedController mController;

        public FeedItemViewHolder(View itemView, FeedFragment.FeedController controller) {
            super(itemView);
            mController = controller;

            mTitle = (TextView) itemView.findViewById(R.id.title);
            mLink = (TextView) itemView.findViewById(R.id.link);
        }

        public void bind(final FeedItem feedItem) {
            mTitle.setText(feedItem.getTitle());
            mLink.setText(feedItem.getLink());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mController.showFeedItem(feedItem);
                }
            });
        }

        public void recycle() {
            itemView.setOnClickListener(null);
        }
    }
}
