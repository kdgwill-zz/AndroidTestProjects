package com.cuboid.gettingstarted.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cuboid.gettingstarted.R;

/**
 * Note: When you add a fragment to an activity layout by defining the fragment in the layout XML file,
 * you cannot remove the fragment at runtime. If you plan to swap your fragments in and out during user
 * interaction, you must add the fragment to the activity when the activity first starts,
 * as shown in the next lesson.
 */
public class FragmentedActivity extends ActionBarActivity
        implements HeadlinesFragment.OnHeadlineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmented);

        /**
         * Because the fragment has been added to the FrameLayout container at
         * runtime—instead of defining it in the activity's layout with a <fragment>
         *     element—the activity can remove the fragment and replace it with a
         *     different one.
         */

        //Check that the activity is using the layout version with
        //the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {
            //However, if we're being restored from a previous state,
            //then we don't need to do anything and should return or else
            //we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            //Create a new Fragment to be placed in the activity layout
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            //In case this activity was started with special instructions from an
            //Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            //Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, firstFragment).commit();
                    //.add(R.id.fragment_container, firstFragment).commit();
            //USING ADD INSTEAD OF REPLACE ADDS NEW FRAGMENTS EVERYTIME ORIENTATION IS CHANGED
        }
    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        Toast.makeText(FragmentedActivity.this, "Position[" + position + "]", Toast.LENGTH_SHORT).show();


        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);
        boolean isSinglePane = findViewById(R.id.fragment_container) != null;
        if (articleFrag != null && !isSinglePane) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            articleFrag.updateArticleView(position);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragmented, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
