package org.cyborgsociety.panicbutton.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.cyborgsociety.panicbutton.app.model.AppItem;
import org.cyborgsociety.panicbutton.app.utils.IAppDataAdapterCallbackInterface;
import org.cyborgsociety.panicbutton.app.utils.IAppItemPropertyInterface;
import org.cyborgsociety.panicbutton.app.utils.enums.ClickType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by richy on 05.09.15.
 */
public class AppDataAdapter extends BaseAdapter
{

    private static final String TAG = AppDataAdapter.class.getName();
    private List<AppItem> appItems = new ArrayList<AppItem>();
    private Context context;
    private IAppDataAdapterCallbackInterface callbackListener;



    public AppDataAdapter(Context context){
        this.context = context;
        PackageManager pm = context.getPackageManager();
            /*
             If flag PackageManager.GET_UNINSTALLED_PACKAGES has been set, a list of all
             applications including those deleted with {@code DONT_DELETE_DATA} (partially
             installed apps with data directory) will be returned.
             */
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        Log.d(TAG, "applist size: " + apps.size());
        ArrayList resultList = new ArrayList<AppItem>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
      //  HashSet<String> deleteCacheApps = (HashSet<String>) preferences.getStringSet(ClickType.TYPE_CACHE.name(), new HashSet<String>());
      //  HashSet<String> deleteDataApps = (HashSet<String>) preferences.getStringSet(ClickType.TYPE_DATA.name(), new HashSet<String>());

        for(ApplicationInfo app : apps) {
            if (app.className != null){
                AppItem item = new AppItem(app.className, pm.getApplicationLabel(app).toString(), false, false, app.dataDir);
               // item.setDeleteCache(deleteCacheApps.contains(item.getId()) ? true : false);
               // item.setDeleteData(deleteDataApps.contains(item.getId()) ? true : false);
                item.setDeleteCache((preferences.getString(item.getId()+ClickType.TYPE_CACHE.name(), null) != null) ? true : false);
                item.setDeleteData((preferences.getString(item.getId()+ClickType.TYPE_DATA.name(), null) != null) ? true : false);

                Log.d(TAG, "appItem: " + item.getId() + " deleteCache: " + item.isDeleteCache());
                Log.d(TAG, "appItem: "+ item.getId() +" deleteData: : " + item.isDeleteData());
                resultList.add(item);
            }
        }

        Log.d(TAG, "resultList size: " + resultList.size());
        this.appItems = resultList;
    }


    public void setAppDataAdapterCallbackinterface(IAppDataAdapterCallbackInterface interfaceImpl){
        this.callbackListener = interfaceImpl;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return appItems.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return appItems.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return appItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listitem, null);
        }

        final AppItem item  = appItems.get(position);
        TextView appDescription = (TextView) convertView.findViewById(R.id.tv_appDescription);
        CheckBox deleteCache = (CheckBox) convertView.findViewById(R.id.cb_cache);
        deleteCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setDeleteCache(!item.isDeleteCache());
                Log.d(TAG, item.getAppDescription() + " deleteCache called: " + item.isDeleteCache());
                callbackListener.onCustomItemClicked(item.getId(), ClickType.TYPE_CACHE, item.isDeleteCache());
            }
        });
        CheckBox deleteData = (CheckBox) convertView.findViewById(R.id.cb_data);
        deleteData.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                item.setDeleteData(!item.isDeleteData());
                Log.d(TAG, item.getAppDescription() + " deleteData called: " + item.isDeleteData());
                callbackListener.onCustomItemClicked(item.getId(), ClickType.TYPE_DATA, item.isDeleteCache());

            }
        });
        appDescription.setText(item.getAppDescription());
        deleteCache.setChecked(item.isDeleteCache());
        deleteData.setChecked(item.isDeleteData());
        return convertView;
    }



    
}
