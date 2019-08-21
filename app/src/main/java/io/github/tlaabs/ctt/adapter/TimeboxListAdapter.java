package io.github.tlaabs.ctt.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.github.tlaabs.timetableview.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.tlaabs.ctt.R;
import io.github.tlaabs.ctt.databinding.TimeboxItemBinding;
import io.github.tlaabs.ctt.viewmodel.TimeboxItemViewModel;

public class TimeboxListAdapter extends RecyclerView.Adapter<TimeboxListAdapter.TimeboxHolder> {
    private final Context context;

    private List<Schedule> schedules;

    public TimeboxListAdapter(Context context) {
        this.context = context;
        schedules = new ArrayList<>();
    }

    public void add(){
        schedules.add(createDefaultSchedule());
        notifyDataSetChanged();
    }

    public void add(List<Schedule> received){
        for(Schedule schedule : received){
            schedules.add(schedule);
        }
        notifyDataSetChanged();
    }

    public void remove(int position){
        schedules.remove(position);
        notifyDataSetChanged();
    }

    private Schedule createDefaultSchedule(){
        Schedule schedule = new Schedule();
        schedule.setDay(0);
        schedule.getStartTime().setHour(9);
        schedule.getStartTime().setMinute(0);
        schedule.getEndTime().setHour(10);
        schedule.getEndTime().setMinute(0);
        return schedule;
    }

    public List<Schedule> getSchedules(){
        return this.schedules;
    }

    @NonNull
    @Override
    public TimeboxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TimeboxItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.timebox_item, parent, false);
        binding.setViewModel(new TimeboxItemViewModel());
        return new TimeboxHolder(binding.getRoot(), binding.getViewModel());
    }

    @Override
    public void onBindViewHolder(@NonNull TimeboxHolder holder, final int position) {
//        loadItem()
        //변화가 생기면 hashData 변경
        final Schedule schedule = schedules.get(position);
//        HashMap 저장  = holder.viewModel.endTime;
//        Log.d("devsim","position : " + position + "|" + holder.viewModel.day.get());
        holder.loadItem(schedule);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }


    public static class TimeboxHolder extends RecyclerView.ViewHolder{
        private final TimeboxItemViewModel viewModel;
        private Spinner daySpinner;
        private DaySpinnerAdapter daySpinnerAdapter;
        private ImageButton deleteBtn;

        public TimeboxHolder(@NonNull View itemView, TimeboxItemViewModel viewModel) {
            super(itemView);
            this.viewModel = viewModel;

            daySpinner = itemView.findViewById(R.id.day_spinner);
            daySpinnerAdapter = new DaySpinnerAdapter(itemView.getContext());
            daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            daySpinner.setAdapter(daySpinnerAdapter);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }

        public void loadItem(Schedule schedule){
            this.viewModel.loadItem(schedule);
        }
    }

}
