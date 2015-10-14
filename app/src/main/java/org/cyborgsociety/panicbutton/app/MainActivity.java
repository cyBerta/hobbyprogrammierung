package org.cyborgsociety.panicbutton.app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import org.cyborgsociety.panicbutton.app.utils.IFragmentInteractionCallbackListener;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements IFragmentInteractionCallbackListener{

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        } else if (id == R.id.action_delete){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveAppSettingData(String appId, boolean data){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        HashSet<String> deleteDataApps = (HashSet<String>) preferences.getStringSet("data", new HashSet<String>());
        if (data){
            deleteDataApps.add(appId);
        } else {
            deleteDataApps.remove(appId);
        }
        editor.putStringSet("data", deleteDataApps);
        editor.commit();
    }

    private void saveAppSettingCache(String appId, boolean cache){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        HashSet<String> deleteCacheApps = (HashSet<String>) preferences.getStringSet("cache", new HashSet<String>());
        if (cache){
            deleteCacheApps.add(appId);
        } else {
            deleteCacheApps.remove(appId);
        }
        editor.putStringSet("cache", deleteCacheApps);
        editor.commit();
    }


    @Override
    public void onFragmentItemClicked(String appId, ClickType type, boolean checked) {
        switch (type){
            case TYPE_DATA:
                saveAppSettingData(appId, checked);
                break;
            case TYPE_CACHE:
                saveAppSettingCache(appId, checked);
                break;
        }
        //saveAppSetting();
    }
}
