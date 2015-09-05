package org.cyborgsociety.panicbutton.app;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.cyborgsociety.panicbutton.app.model.AppContent;
import org.cyborgsociety.panicbutton.app.model.AppItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = ItemFragment.class.getName();

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
   // private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static ItemFragment newInstance(){
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // Set the adapter
        //mListView = (AbsListView) view.findViewById(android.R.id.list);
        // ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
       // mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        this.setEmptyText("NO DATA");

        mAdapter = new AppDataAdapter(getActivity());
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            Log.d(TAG, "Item no " + position + "was clicked.");
            mListener.onFragmentInteraction(((AppItem) mAdapter.getItem(position)).getId());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = getListView().getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }
    private class AsyncTaskRunner extends AsyncTask<String, ArrayList<AppItem>, ArrayList<AppItem>> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "TODO: SHOW PROGRESS DIALOG - start fetching data");
        }

        @Override
        protected ArrayList<AppItem> doInBackground(String... params) {

            PackageManager pm = getContext().getPackageManager();
            /*
             If flag PackageManager.GET_UNINSTALLED_PACKAGES has been set, a list of all
             applications including those deleted with {@code DONT_DELETE_DATA} (partially
             installed apps with data directory) will be returned.
             */
            List<ApplicationInfo> apps = pm.getInstalledApplications(0);
            Log.d(TAG, "applist size: " + apps.size());
            ArrayList resultList = new ArrayList<AppItem>();

            for(ApplicationInfo app : apps) {
                AppItem item = new AppItem(app.className, pm.getApplicationLabel(app).toString(), false, false, app.dataDir);
                resultList.add(item);

            }

            Log.d(TAG, "resultList size: " + resultList.size());
            return resultList;
        }

        protected void onPostExecute(ArrayList<AppItem> result) {
            // execution of result of Long time consuming operation
          //  ((AppDataAdapter)mAdapter) = result;
            //TODO: implement onPostExecute!!
           // Log.d(TAG, "fetching finished... AppDataAdapter.this.appItems.size = " + AppDataAdapter.this.appItems.size());
        }



    }
}
