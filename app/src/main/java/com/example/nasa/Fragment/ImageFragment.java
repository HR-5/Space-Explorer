package com.example.nasa.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

    String id;
    ImageView img;
    WebView webView;
    Button back;
    public ImageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(String id) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
      args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          id = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
//        https://images-assets.nasa.gov/video/T803048_EGRESS-TRAINING-IN-THE-GULF-OF-MEXICO-WITH-APOLLO-8-BACKUP-CREW/T803048_EGRESS-TRAINING-IN-THE-GULF-OF-MEXICO-WITH-APOLLO-8-BACKUP-CREW~large_4.jpg
        Glide.with(getContext())
                .load(u)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);
    }

    private void goback(){
        SearchFragment fragment = new SearchFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragContainer,fragment)
                .commit();
    }
}
