package org.cyborgsociety.panicbutton.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.cyborgsociety.panicbutton.app.model.AppContent;
import org.cyborgsociety.panicbutton.app.model.AppItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by richy on 05.09.15.
 */
public class AppDataAdapter extends BaseAdapter
{

    private static final String TAG = AppDataAdapter.class.getName();
    private List<AppItem> appItems = new ArrayList<AppItem>();
    private Context context;
    private AppContent appContentProvider;


    public AppDataAdapter(Context context, List<AppItem> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.appItems = data;

    }

    public AppDataAdapter(Context context){
        this.context = context;
        /*AsyncTaskRunner taskRunner = new AsyncTaskRunner();
        taskRunner.execute();*/

        PackageManager pm = context.getPackageManager();
            /*
             If flag PackageManager.GET_UNINSTALLED_PACKAGES has been set, a list of all
             applications including those deleted with {@code DONT_DELETE_DATA} (partially
             installed apps with data directory) will be returned.
             */
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        Log.d(TAG, "applist size: " + apps.size());
        ArrayList resultList = new ArrayList<AppItem>();

        for(ApplicationInfo app : apps) {
            // public AppItem(String id, String appDescription, boolean deleteCache, boolean deleteData, String dataDir ) {

            AppItem item = new AppItem(app.className, pm.getApplicationLabel(app).toString(), false, false, app.dataDir);
            resultList.add(item);

        }

        Log.d(TAG, "resultList size: " + resultList.size());


        this.appItems = resultList;
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
            }
        });
        CheckBox deleteData = (CheckBox) convertView.findViewById(R.id.cb_data);
        deleteData.setOnClickListener(new View.OnClickListener(){

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                item.setDeleteData(!item.isDeleteData());
                Log.d(TAG, item.getAppDescription() + " onClick called: " + item.isDeleteData());
            }
        });
        appDescription.setText(item.getAppDescription());
        deleteCache.setChecked(item.isDeleteCache());
        deleteData.setChecked(item.isDeleteData());
        return convertView;
    }


    private class AsyncTaskRunner extends AsyncTask<String, ArrayList<AppItem>, ArrayList<AppItem>>{

        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            Log.d(TAG, "TODO: SHOW PROGRESS DIALOG - start fetching data");
        }


        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<AppItem> doInBackground(String... params) {

            PackageManager pm = context.getPackageManager();
            /*
             If flag PackageManager.GET_UNINSTALLED_PACKAGES has been set, a list of all
             applications including those deleted with {@code DONT_DELETE_DATA} (partially
             installed apps with data directory) will be returned.
             */
            List<ApplicationInfo> apps = pm.getInstalledApplications(0);
            Log.d(TAG, "applist size: " + apps.size());
            ArrayList resultList = new ArrayList<AppItem>();

            for(ApplicationInfo app : apps) {
                // public AppItem(String id, String appDescription, boolean deleteCache, boolean deleteData, String dataDir ) {

                AppItem item = new AppItem(app.className, pm.getApplicationLabel(app).toString(), false, false, app.dataDir);
                resultList.add(item);

            }

            Log.d(TAG, "resultList size: " + resultList.size());


            return resultList;
        }

        protected void onPostExecute(ArrayList<AppItem> result) {
            // execution of result of Long time consuming operation
            AppDataAdapter.this.appItems = result;
            Log.d(TAG, "fetching finished... AppDataAdapter.this.appItems.size = " + AppDataAdapter.this.appItems.size());
        }



    }
    
}
