package com.example.administrator.app_control.Fragment;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.app_control.Activity.AddReminderActivity;
import com.example.administrator.app_control.Activity.R;
import com.example.administrator.app_control.Other.AlarmCursorApdater;
import com.example.administrator.app_control.Other.AlarmReminderContract;
import com.example.administrator.app_control.Other.AlarmReminderDbHelper;
import com.example.administrator.app_control.Other.Item;
import com.example.administrator.app_control.Other.ItemListView;

import java.util.ArrayList;


public class ScheduleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private FloatingActionButton btnAddRemider;
    AlarmCursorApdater mCursorAdapter;
    ListView itemListView;
    ArrayList<Item> arr = null;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_schedule, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        itemListView = (ListView) getActivity().findViewById(R.id.listview);
        arr = ItemListView.getList();
        if(arr == null){
            View emptyView = getActivity().findViewById(R.id.empty_view);
            itemListView.setEmptyView(emptyView);
        } else {
            mCursorAdapter = new AlarmCursorApdater(getActivity(), null);
            itemListView.setAdapter(mCursorAdapter);
        }

        mCursorAdapter = new AlarmCursorApdater(getContext(),null);
        itemListView.setAdapter(mCursorAdapter);

        context = this.getContext();
        btnAddRemider = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        btnAddRemider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddScheduleFragment fragment2 = new AddScheduleFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment2);
                fragmentTransaction.commit();
            }
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), AddReminderActivity.class);

                Uri curVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmRemiderEntry.CONTENT_URI,l);

                intent.setData(curVehicleUri);

                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                AlarmReminderContract.AlarmRemiderEntry._ID,
                AlarmReminderContract.AlarmRemiderEntry.KEY_NAME,
                AlarmReminderContract.AlarmRemiderEntry.KEY_TIME,
                AlarmReminderContract.AlarmRemiderEntry.KEY_REPEAT,
                AlarmReminderContract.AlarmRemiderEntry.KEY_REPEAT_TYPE
        };
        return new CursorLoader(getContext(),   // Parent activity context
                AlarmReminderContract.AlarmRemiderEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

}
