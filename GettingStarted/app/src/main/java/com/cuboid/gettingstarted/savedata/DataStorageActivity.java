package com.cuboid.gettingstarted.savedata;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cuboid.gettingstarted.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DataStorageActivity extends ActionBarActivity {
    private final String LOG_TAG = "com.cuboid.gettingstarted.savedata.ExternalDir";
    private int newHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);

        /**
         * Use this from an Activity if you need multiple
         * shared preference files identified by name,
         * which you specify with the first parameter.
         * You can call this from any Context in your app.
         */
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        /**
         *  Use this from an Activity if you need to use only
         *  one shared preference file for the activity. Because
         *  this retrieves a default shared preference file that
         *  belongs to the activity, you don't need to supply a name.
         */
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        //Retrieve value and load default to use in case no value loaded
        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default);
        newHighScore = pref.getInt(getString(R.string.saved_high_score), defaultValue);

    }

    @Override
    protected void onStop() {
        super.onPause();
        //This is the proper way to alter shared preference files
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_high_score), getRandomNumber());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "New High Score: " + newHighScore, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Internal Storage Space: " + getInternalStorageSpace(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "External Storage Space: " + getExternalStorageSpace(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_storage, menu);
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

    public int getRandomNumber() {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int min = 10;
        int max = 1000;
        int range = max - min;
        int randomNum = rand.nextInt(range + 1) + min;
        return randomNum;
    }

    /**
     * Internal storage:
     * <p>
     * It's always available.
     * Files saved here are accessible by only your app by default.
     * When the user uninstalls your app, the system removes all your app's files from internal storage.
     * <p/>
     * Internal storage is best when you want to be sure that neither the user nor other apps can access your files.
     * </p>
     */
    public void writeToInternalStorage() {
        // File file = new File(context.getFilesDir(), filename);

        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a File representing an internal directory for your app's
     * temporary cache files. Be sure to delete each file once it is no
     * longer needed and implement a reasonable size limit for the amount
     * of memory you use at any given time, such as 1MB. If the system
     * begins running low on storage, it may delete your cache files
     * without warning.
     */
    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
            e.printStackTrace();
        }
        return file;
    }

    /**
     * External storage:
     * <p/>
     * It's not always available, because the user can mount the external storage as
     * USB storage and in some cases remove it from the device.
     * It's world-readable, so files saved here may be read outside of your control.
     * When the user uninstalls your app, the system removes your app's files from
     * here only if you save them in the directory from getExternalFilesDir().
     * External storage is the best place for files that don't require access
     * restrictions and for files that you want to share with other apps or allow
     * the user to access with a computer.
     */

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicMusicStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    /**
     * If none of the pre-defined sub-directory names suit your files,
     * you can instead call getExternalFilesDir() and pass null.
     * This returns the root directory for your app's private
     * directory on the external storage.
     */
    public File getPrivateMusicStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_MUSIC), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    @TargetApi(9)
    public long getInternalStorageSpace() {
        File file = this.getTempFile(this, "onResume");

        long b2mb = 1000000;//bytes to megabytes
        long freeSpace = file.getFreeSpace() / b2mb;
        long totalSpace = file.getTotalSpace() / b2mb;

        file.delete();
        return totalSpace;
    }

    @TargetApi(9)
    public long getExternalStorageSpace() {
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "EXTERNAL STORAGE NOT WRITABLE", Toast.LENGTH_SHORT).show();
            return -1;
        }
        File file = this.getPublicMusicStorageDir("onResume2");

        long b2mb = 1000000;//bytes to megabytes
        long freeSpace = file.getFreeSpace() / b2mb;
        long totalSpace = file.getTotalSpace() / b2mb;
        file.delete();
        return totalSpace;
    }
}
