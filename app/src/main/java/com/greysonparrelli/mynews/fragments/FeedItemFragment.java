package com.greysonparrelli.mynews.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.greysonparrelli.mynews.R;
import com.greysonparrelli.mynews.models.FeedItem;
import com.greysonparrelli.mynews.views.FeedItemWebView;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class FeedItemFragment extends Fragment {

    public static String TAG = "FeedItemFragment";

    private static String KEY_FEED_ITEM = "feed_item";

    private FeedItem mFeedItem;
    private FeedItemWebView mContentView;
    private TextView mTitleView;


    public static FeedItemFragment newInstance(FeedItem item) {
        FeedItemFragment fragment = new FeedItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_FEED_ITEM, item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedItem = getArguments().getParcelable(KEY_FEED_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleView = (TextView) view.findViewById(R.id.title);

        mContentView = (FeedItemWebView) view.findViewById(R.id.content);
        mContentView.getSettings().setUseWideViewPort(false);
        mContentView.setScrollContainer(false);
        mContentView.setBackgroundColor(Color.TRANSPARENT);
        WebView.setWebContentsDebuggingEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        mTitleView.setText(mFeedItem.getTitle());
        mContentView.setHtmlContent(mFeedItem.getContent());
    }

    @Override
    public void onStop() {
        super.onStop();

        mContentView.setHtmlContent(null);
    }
}
