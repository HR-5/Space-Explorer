package com.example.nasa.Fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nasa.API.GetDataService;
import com.example.nasa.Model.Apod;
import com.example.nasa.Network.RetrofitClientInstance;
import com.example.nasa.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApodFragment extends Fragment {

    String date;
    ImageView img;
    WebView webView;
    Button back;
    public ApodFragment() {
        // Required empty public constructor
    }


    public static ApodFragment newInstance(String date) {
        ApodFragment fragment = new ApodFragment();
        Bundle args = new Bundle();
        args.putString("date",date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString("date");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apod,container,false);
        img = (ImageView) view.findViewById(R.id.imageView);
        webView = (WebView) view.findViewById(R.id.web);
        back = (Button) view.findViewById(R.id.back);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Apod> apodCall = null;
        apodCall = service.getApod(date, getString(R.string.api_key));
        apodCall.enqueue(new Callback<Apod>() {
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                if(response.isSuccessful()){
                    Apod apod = response.body();
                    if(apod.getMedia_type().equals("image")) {
                        String url = apod.getUrl();
                        displayImg(url);
                    }
                    else {
                        String url = apod.getUrl();
                        displayVid(url);
                    }
                }
            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {

            }
        });
    }

    private void displayImg(String url){
        Glide.with(getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);
    }
    private void displayVid(String url){
        img.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }

    private void goback(){
        CalendarFragment fragment = new CalendarFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragContainer,fragment)
                .commit();
    }

}
