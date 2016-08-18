package com.greysonparrelli.mynews.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.greysonparrelli.mynews.R;
import com.greysonparrelli.mynews.fragments.FeedItemFragment;
import com.greysonparrelli.mynews.fragments.FeedFragment;
import com.greysonparrelli.mynews.models.FeedItem;


/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class MainActivity extends AppCompatActivity implements FeedFragment.FeedController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(
                    R.id.fragment_container,
                    FeedFragment.newInstance(),
                    FeedFragment.TAG).commit();
        }
    }

    @Override
    public void showFeedItem(FeedItem item) {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_container,
                FeedItemFragment.newInstance(item),
                FeedItemFragment.TAG).addToBackStack(null).commit();
    }

}
