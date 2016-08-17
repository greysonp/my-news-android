package com.greysonparrelli.mynews.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.greysonparrelli.mynews.utils.NetworkUtil;
import com.greysonparrelli.mynews.R;
import com.greysonparrelli.mynews.adapters.FeedItemAdapter;
import com.greysonparrelli.mynews.models.Feed;
import com.greysonparrelli.mynews.models.FeedItem;

import java.io.IOException;

import okhttp3.Call;

public class FeedFragment extends Fragment {

    public static String TAG = "FeedListFragment";

    private RecyclerView mList;
    private FeedItemAdapter mAdapter;
    private Feed mFeed;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof FeedController)) {
            throw new IllegalStateException("Parent activity must implement the FeedController interface.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = (RecyclerView) view.findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new FeedItemAdapter((FeedController) getActivity());
        mList.setAdapter(mAdapter);

        NetworkUtil.getFeed("http://www.theverge.com/rss/index.xml", new NetworkUtil.FeedCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Feed feed) {
                mFeed = feed;
                mAdapter.setFeedItems(mFeed.getEntries());
            }
        });
    }

    public interface FeedController {
        void showFeedItem(FeedItem item);
    }
}
