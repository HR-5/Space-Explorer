package com.example.nasa.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.Toast;

import com.example.nasa.API.DataService;
import com.example.nasa.API.GetDataService;
import com.example.nasa.Adapter;
import com.example.nasa.Model.Data;
import com.example.nasa.Model.Search;
import com.example.nasa.Network.RetrofitClientInstance;
import com.example.nasa.Network.SearchRetrofit;
import com.example.nasa.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    SearchView searchView;
    Adapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        searchView = (SearchView) view.findViewById(R.id.searchview);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        FragmentManager manager = getFragmentManager();
        adapter = new Adapter(manager,getContext(),getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        int resId = R.anim.anim_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scheduleLayoutAnimation();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DataService service = SearchRetrofit.getSearchRetrofit().create(DataService.class);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<Search> searchCall;
                searchCall = service.getSearch(query);
                searchCall.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response != null) {
                            ArrayList<Data> dataList = new ArrayList<>();
                            Search search = response.body();
                            if (search.getCollection() != null) {
                                for (int i = 0; i < search.getCollection().getItems().size(); i++) {
                                    dataList.add(search.getCollection().getItems().get(i).getData().get(0));
                                }
                                adapter.update(dataList);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Call<Search> searchCall;
                searchCall = service.getSearch(newText);
                searchCall.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.body() != null) {
                            ArrayList<Data> dataList = new ArrayList<>();
                            Search search = response.body();
                            if (search.getCollection() != null) {
                                for (int i = 0; i < search.getCollection().getItems().size(); i++) {
                                    dataList.add(search.getCollection().getItems().get(i).getData().get(0));
                                }
                                adapter.update(dataList);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        });
    }


}
