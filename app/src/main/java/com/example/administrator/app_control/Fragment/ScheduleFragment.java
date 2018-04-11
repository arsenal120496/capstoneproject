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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.administrator.app_control.Activity.AddReminderActivity;
import com.example.administrator.app_control.Activity.R;
import com.example.administrator.app_control.Other.AlarmCursorApdater;
import com.example.administrator.app_control.Other.AlarmReminderContract;
import com.example.administrator.app_control.Other.AlarmReminderDbHelper;


public class ScheduleFragment extends Fragment {
    private FloatingActionButton btnAddRemider;
    AlarmCursorApdater alarmCursorApdater;
    AlarmReminderDbHelper alarmReminderDbHelper;
    private FloatingActionButton btnFab;
    ListView itemListView;
    ProgressDialog prgDialog;
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


        View emptyView = getActivity().findViewById(R.id.empty_view);
        itemListView.setEmptyView(emptyView);

        alarmCursorApdater = new AlarmCursorApdater(getContext(),null);
        itemListView.setAdapter(alarmCursorApdater);

        context = this.getContext();
        btnAddRemider = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        btnAddRemider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddReminderActivity.class);
                startActivity(intent);
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

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        String[] projection = {
//                AlarmReminderContract.AlarmRemiderEntry._ID,
//                AlarmReminderContract.AlarmRemiderEntry.KEY_NAME,
//                AlarmReminderContract.AlarmRemiderEntry.KEY_TIME,
//                AlarmReminderContract.AlarmRemiderEntry.KEY_REPEAT,
//                AlarmReminderContract.AlarmRemiderEntry.KEY_REPEAT_NO,
//                AlarmReminderContract.AlarmRemiderEntry.KEY_REPEAT_TYPE,
//                AlarmReminderContract.AlarmRemiderEntry.KEY_ACTIVE
//
//        };
//        return new CursorLoader(getContext(),   // Parent activity context
//                AlarmReminderContract.AlarmRemiderEntry.CONTENT_URI,   // Provider content URI to query
//                projection,             // Columns to include in the resulting Cursor
//                null,                   // No selection clause
//                null,                   // No selection arguments
//                null);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        alarmCursorApdater.swapCursor(cursor);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        alarmCursorApdater.swapCursor(null);
//    }

}