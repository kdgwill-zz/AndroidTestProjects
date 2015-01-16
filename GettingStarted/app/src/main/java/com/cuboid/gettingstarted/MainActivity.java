package com.cuboid.gettingstarted;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.cuboid.gettingstarted.actionBar.TestActionBarActivity;
import com.cuboid.gettingstarted.fragment.FragmentedActivity;
import com.cuboid.gettingstarted.implicit_intent.ImplicitIntentActivity;
import com.cuboid.gettingstarted.intro.DisplayMessageActivity;
import com.cuboid.gettingstarted.lifecycle.LifeCycleActivity;
import com.cuboid.gettingstarted.savedata.DataStorageActivity;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.cuboid.gettingstarted.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Start method tracing that the activity ends during onDestroy()
        android.os.Debug.startMethodTracing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * Called when the user clicks the Send button
     */
    public void sendMessage(View view) {
        //Do something
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        this.startActivity(intent);
    }

    public void startIntent(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.startActivityIntent:
                intent = new Intent(this, TestActionBarActivity.class);
                break;
            case R.id.startLifecycleIntent:
                intent = new Intent(this, LifeCycleActivity.class);
                break;
            case R.id.startFragmentedIntent:
                intent = new Intent(this, FragmentedActivity.class);
                break;
            case R.id.startDataStorageIntent:
                intent = new Intent(this, DataStorageActivity.class);
                break;
            case R.id.startImplicitIntentIntent:
                intent = new Intent(this, ImplicitIntentActivity.class);
                break;
            default:
                break;
        }
        this.startActivity(intent);
    }
}
