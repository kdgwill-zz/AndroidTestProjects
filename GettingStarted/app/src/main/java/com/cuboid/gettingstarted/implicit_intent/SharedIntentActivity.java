package com.cuboid.gettingstarted.implicit_intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cuboid.gettingstarted.R;

public class SharedIntentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_intent);


        // Get the intent that started this activity
        Intent intent = getIntent();
        Uri data = intent.getData();

        Intent result = null;
        // Figure out what to do based on the intent type
        if (intent.getType().indexOf("image/") != -1) {
            // Handle intents with image data ...
            result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
        } else if (intent.getType().equals("text/plain")) {
            // Handle intents with text ...
            // Create intent to deliver some kind of result data
            result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
        }

        if (result != null) {
            setResult(Activity.RESULT_OK, result);
        }
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shared_intent, menu);
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
