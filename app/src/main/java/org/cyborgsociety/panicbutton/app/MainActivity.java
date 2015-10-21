package org.cyborgsociety.panicbutton.app;

import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import org.cyborgsociety.panicbutton.app.utils.IAppItemPropertyInterface;
import org.cyborgsociety.panicbutton.app.utils.enums.ClickType;

import java.util.HashSet;


public class MainActivity extends AppCompatActivity implements IAppItemPropertyInterface {

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(null);
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
        } else if (id == R.id.action_quit){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private synchronized void saveAppSettingData(String appId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String deleteDataApp = preferences.getString(appId + ClickType.TYPE_DATA.name(), null);
        SharedPreferences.Editor editor = preferences.edit();

        Log.d(TAG, "saveAppSettingData appid: " + appId + " value: " + deleteDataApp);
        if (deleteDataApp == null){
            editor.putString(appId+ClickType.TYPE_DATA.name(), appId + ClickType.TYPE_DATA.name());
        } else {
            editor.remove(appId+ClickType.TYPE_DATA.name());
        }
        editor.apply();
    }

    private synchronized void saveAppSettingCache(String appId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String deleteCacheApp = preferences.getString(appId + ClickType.TYPE_CACHE.name(), null);
        SharedPreferences.Editor editor = preferences.edit();

        Log.d(TAG, "saveAppSettingCache appid: " + appId + "value: " + ClickType.TYPE_CACHE.name());

        if (deleteCacheApp == null){
            editor.putString(appId + ClickType.TYPE_CACHE.name(), appId + ClickType.TYPE_CACHE.name());
        } else {
            editor.remove(appId + ClickType.TYPE_CACHE.name());
        }
        //editor.putStringSet(ClickType.TYPE_CACHE.name(), deleteCacheApps);
        editor.apply();
    }


    @Override
    public void onAppItemPropertyChanged(String appId, ClickType type, boolean checked) {
        Log.d(TAG, "onAppItemPropertyChanged!! appID: " + appId + " clickType: " + type.toString() + " checked: " + checked);
        switch (type){
            case TYPE_DATA:
                saveAppSettingData(appId);
                break;
            case TYPE_CACHE:
                saveAppSettingCache(appId);
                break;
        }
    }
}
