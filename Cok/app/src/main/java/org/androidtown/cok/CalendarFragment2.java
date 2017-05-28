package org.androidtown.cok;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Created by LEE on 2017-05-23.
 */

public class CalendarFragment2 extends Fragment {
    CalendarView calendarView;
    Context mainContext;
    Intent m_intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.calendar3,container,false);
        calendarView = (CalendarView)rootView.findViewById(R.id.calendar2);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Bundle bundle = m_intent.getExtras();
                bundle.putInt("Year",year);
                bundle.putInt("Month",month+1);
                bundle.putInt("Day",dayOfMonth);
                m_intent.putExtras(bundle);
            }
        });
        return rootView;

    }
    public CalendarFragment2(Context _context,Intent intent){
        mainContext = _context;
        m_intent=intent;
    }
}
