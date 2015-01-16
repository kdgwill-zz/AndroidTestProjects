package com.cuboid.gettingstarted.implicit_intent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cuboid.gettingstarted.R;

import org.apache.http.protocol.HTTP;

import java.util.Calendar;
import java.util.List;

public class ImplicitIntentActivity extends ActionBarActivity {

    public static final int PICK_CONTACT_REQUEST = 1;  // The request code
    private boolean appChooserOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);
    }

    @Override
    protected void onStart() {
        ToggleButton tb = (ToggleButton) findViewById(R.id.toggle_app_chooser);
        appChooserOn = tb.isChecked();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_implicit_intent, menu);
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

    public void toggleAppChooser(View view) {
        appChooserOn = ((ToggleButton) view).isChecked();
    }

    @TargetApi(14)
    public void buttonPress(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button_dial:
                Uri number = Uri.parse("tel:5551234");
                intent = new Intent(Intent.ACTION_DIAL, number);
                break;
            case R.id.button_map:
                // Map point based on address
                Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
                // Or map point based on latitude/longitude
                // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
                intent = new Intent(Intent.ACTION_VIEW, location);
                break;
            case R.id.button_web:
                Uri webpage = Uri.parse("http://www.android.com");
                intent = new Intent(Intent.ACTION_VIEW, webpage);
                break;
            case R.id.button_email:
                intent = new Intent(Intent.ACTION_SEND);
                // The intent does not have a URI, so declare the "text/plain" MIME type
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jon@example.com"}); // recipients
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Email message text");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
                // You can also attach multiple items by passing an ArrayList of Uris
                break;
            case R.id.button_calendar:
                intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2012, 0, 19, 7, 30);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2012, 0, 19, 10, 30);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
                intent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
                break;
            default:
                Toast.makeText(ImplicitIntentActivity.this, "Unknown Function", Toast.LENGTH_SHORT).show();
                return;
        }
        //default prevent null intent
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;
        if (!isIntentSafe) {
            Toast.makeText(ImplicitIntentActivity.this, "Unknown Handler", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!appChooserOn) {
            startActivity(intent);
        } else {
            // Always use string resources for UI text.
            // This says something like "Share this photo with"
            String title = ((Button) view).getText().toString();
            title += " App Chooser";
            // Create intent to show chooser
            Intent chooser = Intent.createChooser(intent, title);

            // Verify the intent will resolve to at least one activity
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
        }
    }

    /////////////////////////////////////////////
    //////////////SECTION 2
    /////////////////////////////////////////////

    public void getContactInfo(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
        //RESULT RETURNED IN onActivityResult
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                // Do something with the phone number...
                Toast.makeText(this, number, Toast.LENGTH_LONG).show();
            }
        }
    }
}
