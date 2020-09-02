package com.example.nasa.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
    Animation animation;
    TextView tcard,dtext;
    CardView cardView;
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
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
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
        tcard = (TextView) view.findViewById(R.id.tcard);
        cardView = (CardView) view.findViewById(R.id.cardView2);
        dtext = (TextView) view.findViewById(R.id.date);
        back = (Button) view.findViewById(R.id.back);
        animation = AnimationUtils.loadAnimation(getContext(),R.anim.blink);
        Animation animation1 = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        dtext.startAnimation(animation1);
        img.startAnimation(animation);
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
        dtext.setText(date);
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
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        animation.cancel();
                        img.clearAnimation();
                        return false;
                    }
                })
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
        fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.trans));
        fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
        getFragmentManager().beginTransaction().replace(R.id.fragContainer,fragment)
                .addToBackStack("transaction")
                .addSharedElement(back, "btn")
                .addSharedElement(tcard,"tcard")
                .addSharedElement(cardView,"cardview")
                .commit();
    }

}
