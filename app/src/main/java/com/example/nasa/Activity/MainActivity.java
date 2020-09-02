package com.example.nasa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.nasa.API.GetDataService;
import com.example.nasa.Fragment.CalendarFragment;
import com.example.nasa.Fragment.SearchFragment;
import com.example.nasa.Model.Apod;
import com.example.nasa.Network.RetrofitClientInstance;
import com.example.nasa.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.searc);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        CalendarFragment fragment = new CalendarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer,fragment)
                .commit();
    }

    private void search(){
        Intent intent = new Intent(this,SearchActivity.class);
        Pair pair = Pair.create((View) imageView,"search");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);
        startActivity(intent,options.toBundle());
    }
}
