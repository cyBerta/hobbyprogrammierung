package org.cyborgsociety.panicbutton.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import org.cyborgsociety.panicbutton.app.utils.IAppDataAdapterCallbackInterface;
import org.cyborgsociety.panicbutton.app.utils.IAppItemPropertyInterface;
import org.cyborgsociety.panicbutton.app.utils.enums.ClickType;

import java.util.zip.Inflater;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link IAppItemPropertyInterface}
 * interface.
 */


public class ItemFragment extends ListFragment implements AdapterView.OnItemClickListener, IAppDataAdapterCallbackInterface {
    private static final String TAG = ItemFragment.class.getName();

    private IAppItemPropertyInterface mListener;


    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;


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
        View view = inflater.inflate(R.layout.fragment_item_list, container, true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        this.setEmptyText("NO DATA");


    }

    @Override
    public void onResume() {
        super.onResume();

        mListener = (IAppItemPropertyInterface) getActivity();
        mAdapter = new AppDataAdapter(getActivity());
        Log.d(TAG, "mAdapter isEmpty: " + mAdapter.isEmpty());
        setListAdapter(mAdapter);
        ((AppDataAdapter)mAdapter).setAppDataAdapterCallbackinterface(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        setListAdapter(null);
        ((AppDataAdapter)mAdapter).setAppDataAdapterCallbackinterface(null);

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


    @Override
    public void onCustomItemClicked(String appId, ClickType type, boolean checked) {
        if (mListener != null && mAdapter != null){
            Log.d(TAG, "onCustomItemClicked: " + appId + "checked: " + checked);
            mListener.onAppItemPropertyChanged(appId, type, checked);
        } else {
            Log.d(TAG, (mListener == null ? "mListener is null!" : "mListener is notNull"));
        }
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



}
