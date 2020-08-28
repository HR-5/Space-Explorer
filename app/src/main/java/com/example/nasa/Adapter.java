package com.example.nasa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasa.Fragment.ImageFragment;
import com.example.nasa.Model.Data;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Data> dataList;
    FragmentManager manager;

    public Adapter(FragmentManager manager) {
        this.manager = manager;
        dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageFragment fragment = ImageFragment.newInstance(dataList.get(position).getNasa_id());
                manager.beginTransaction().replace(R.id.fragContainer,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void update(List<Data> data) {
        dataList.clear();
        dataList.addAll(data);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleset);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }
}
