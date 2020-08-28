package com.example.nasa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.nasa.Fragment.SearchFragment;
import com.example.nasa.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageView imageView = (ImageView) findViewById(R.id.searc);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main();
            }
        });
        SearchFragment fragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer,fragment)
                .commit();
    }
    private void main(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
