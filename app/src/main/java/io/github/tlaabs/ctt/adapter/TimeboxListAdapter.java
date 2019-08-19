package io.github.tlaabs.ctt.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Time;

import io.github.tlaabs.ctt.R;
import io.github.tlaabs.ctt.databinding.TimeboxItemBinding;
import io.github.tlaabs.ctt.viewmodel.DaySpinnerViewModel;

public class TimeboxListAdapter extends RecyclerView.Adapter<TimeboxListAdapter.TimeboxHolder> {
    private final Context context;

    public TimeboxListAdapter(Context context) {
        this.context = context;
    }

    public static class TimeboxHolder extends RecyclerView.ViewHolder{
        public TimeboxHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public TimeboxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TimeboxItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.timebox_item, parent, false);
        binding.setViewModel(new DaySpinnerViewModel());
        return new TimeboxHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull TimeboxHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
