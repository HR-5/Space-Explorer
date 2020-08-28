package com.example.nasa.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.nasa.R;

import java.util.Calendar;


public class CalendarFragment extends Fragment {

    String date;

    Button set;
    CalendarView calendarView;
    Calendar calendar;
    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);
        set = (Button) view.findViewById(R.id.set);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                if(calendar.getTimeInMillis()> cal.getTimeInMillis())
                    Toast.makeText(getContext(),"Date Invalid",Toast.LENGTH_SHORT).show();
                else
                showImg();
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                i1=i1+1;
                calendar = Calendar.getInstance();
                calendar.set(i,i1-1,i2,0,0,0);
                date = ""+i+"-"+ i1+"-"+ i2;
            }
        });
    }
    private void showImg(){
        ApodFragment fragment = ApodFragment.newInstance(date);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer,fragment).commit();
    }
}
