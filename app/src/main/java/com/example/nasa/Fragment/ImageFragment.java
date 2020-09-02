package com.example.nasa.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.nasa.API.DataService;
import com.example.nasa.API.GetDataService;
import com.example.nasa.ImgModel.Asset;
import com.example.nasa.Network.RetrofitClientInstance;
import com.example.nasa.Network.SearchRetrofit;
import com.example.nasa.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageFragment extends Fragment {

    String id,tit;
    ImageView img;
    WebView webView;
    Button back;
    Animation animation;
    TextView tcard,dtext;
    public ImageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(String id,String title) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("tit",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        if (getArguments() != null) {
          id = getArguments().getString("id");
          tit = getArguments().getString("tit");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apod,container,false);
        img = (ImageView) view.findViewById(R.id.imageView);
        webView = (WebView) view.findViewById(R.id.web);
        back = (Button) view.findViewById(R.id.back);
        dtext = (TextView) view.findViewById(R.id.date);
        tcard = (TextView) view.findViewById(R.id.tcard);
        dtext.setVisibility(View.INVISIBLE);
        tcard.setText(tit);
        animation = AnimationUtils.loadAnimation(getContext(),R.anim.blink);
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
        DataService service = SearchRetrofit.getSearchRetrofit().create(DataService.class);
        Call<Asset> searchCall = service.getItem(id);
        searchCall.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset asset = response.body();
                String url = asset.getCollections().getItems().get(1).getHref();
                displayImg(url);
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Toast.makeText(getContext(),"Process Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayImg(String url){
        String[] ur = url.split(":");
        String u = "https:" + ur[1];
        Glide.with(getContext())
                .load(u)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
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

    private void goback(){
        SearchFragment fragment = new SearchFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragContainer,fragment)
                .commit();
    }
}
