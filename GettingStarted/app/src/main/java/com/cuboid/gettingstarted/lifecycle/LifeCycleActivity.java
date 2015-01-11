package com.cuboid.gettingstarted.lifecycle;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cuboid.gettingstarted.R;

public class LifeCycleActivity extends ActionBarActivity {

    static final String STATE_SCORE = "playerScore";
    public TextView mTextView;
    public int mCurrentScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, getMethodName(), Toast.LENGTH_SHORT).show();
        // Set the user interface layout for this Activity
        // The layout file is defined in the project res/layout/main_activity.xml file
        setContentView(R.layout.activity_life_cycle);

        // Initialize member TextView so we can manipulate it later
        mTextView = (TextView) findViewById(R.id.lifeCycleText);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // For the main activity, make sure the app icon in the action bar
            // does not behave as a button
            getSupportActionBar().setHomeButtonEnabled(false);
        }

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState == null) {
            mCurrentScore = 0;
        }


        // Start method tracing that the activity ends during onDestroy()
        //android.os.Debug.startMethodTracing();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, getMethodName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, getMethodName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, getMethodName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop(); // Always call the superclass method first
        Toast.makeText(this, getMethodName(), Toast.LENGTH_SHORT).show();

//        // Save the note's current draft, because the activity is stopping
//        // and we want to be sure the current note progress isn't lost.
//        ContentValues values = new ContentValues();
//        values.put(NotePad.Notes.COLUMN_NAME_NOTE, getCurrentNoteText());
//        values.put(NotePad.Notes.COLUMN_NAME_TITLE, getCurrentNoteTitle());
//
//        getContentResolver().update(
//                mUri,    // The URI for the note to update.
//                values,  // The map of column names and new values to apply to them.
//                null,    // No SELECT criteria are used.
//                null     // No WHERE columns are used.
//        );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, getMethodName(), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, getMethodName(), Toast.LENGTH_SHORT).show();
        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Instead of restoring the state during onCreate() you may choose to implement onRestoreInstanceState(),
     * which the system calls after the onStart() method. The system calls onRestoreInstanceState()
     * only if there is a saved state to restore, so you do not need to check whether the Bundle is null:
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        mCurrentScore = savedInstanceState.getInt(STATE_SCORE);

        TextView textView = (TextView) findViewById(R.id.lifeCycleText);
        String tvs = textView.getText().toString();
        mCurrentScore++;
        textView.setText(tvs + "\t" + mCurrentScore);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_life_cycle, menu);
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

    public void endLifeCycleIntent(View view) {
        finish();
    }

    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }
}
