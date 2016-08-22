package com.greysonparrelli.mynews.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

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
    public void showFeedItem(FeedItem item, View titleView) {
        Fragment fragment = FeedItemFragment.newInstance(item, ViewCompat.getTransitionName(titleView));

        // Prepare fragment transitions
        fragment.setEnterTransition(new Fade());
        fragment.setExitTransition(new Fade());

        Transition elementTransition = TransitionInflater.from(this).inflateTransition(R.transition.shift);
        fragment.setSharedElementEnterTransition(elementTransition);
        fragment.setSharedElementReturnTransition(elementTransition);

        // Perform transaction
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,fragment, FeedItemFragment.TAG)
                .addSharedElement(titleView, ViewCompat.getTransitionName(titleView))
                .addToBackStack(null)
                .commit();
    }

}
