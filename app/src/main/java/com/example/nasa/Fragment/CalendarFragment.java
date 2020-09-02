package com.example.nasa.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nasa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarFragment extends Fragment {

    String date;

    Button set;
    CalendarView calendarView;
    Calendar calendar;
    TextView tcard;
    CardView cardView;
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
        tcard = (TextView) view.findViewById(R.id.tcard);
        cardView = (CardView) view.findViewById(R.id.cardView);
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(calendar.getTime());
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
        fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.trans));
        fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer,fragment)
                .addToBackStack("transaction")
                .addSharedElement(set, "btn")
                .addSharedElement(tcard,"tcard")
                .addSharedElement(cardView,"cardview")
                .commit();
    }
}
