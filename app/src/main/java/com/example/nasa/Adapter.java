package com.example.nasa;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.example.nasa.Fragment.ImageFragment;
import com.example.nasa.Model.Data;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Data> dataList;
    FragmentManager manager;
    Context context;
    Activity activity;

    public Adapter(FragmentManager manager,Context context,Activity activity) {
        this.manager = manager;
        this.context = context;
        this.activity = activity;
        dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(dataList.get(position).getTitle());
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

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleset);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
                    assert inputMethodManager != null;
                    inputMethodManager.hideSoftInputFromWindow(itemView.getApplicationWindowToken(),0);
                    ImageFragment fragment = ImageFragment.newInstance(dataList.get(position).getNasa_id(),dataList.get(position).getTitle());
                    fragment.setSharedElementEnterTransition(TransitionInflater.from(context).inflateTransition(R.transition.trans));
                    fragment.setEnterTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move));
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragContainer,fragment)
                            .addToBackStack("transaction")
                            .addSharedElement(title,"title")
                            .commit();
                }
            });
        }
    }
}
