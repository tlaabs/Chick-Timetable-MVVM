package io.github.tlaabs.ctt.viewmodel;

import android.app.TimePickerDialog;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TimePicker;

import com.github.tlaabs.timetableview.Schedule;

public class TimeboxItemViewModel {
    public ObservableInt day = new ObservableInt(0);
    public ObservableInt startTimeHour= new ObservableInt(9);
    public ObservableInt startTimeMinute= new ObservableInt(0);
    public ObservableField<String> startTime = new ObservableField<>("오전 09:00");
    public ObservableInt endTimeHour= new ObservableInt(10);
    public ObservableInt endTimeMinute= new ObservableInt(0);
    public ObservableField<String> endTime = new ObservableField<>("오전 10:00");

    private Schedule schedule;

    public void onDaySpinnerItemSelected(AdapterView<?> parent, View view, int position, long id) {
        day.set(position);
        notifyDataChanged();
    }

    public void onStartTimeClick(View view){
        TimePickerDialog.OnTimeSetListener startListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeHour.set(hourOfDay);
                startTimeMinute.set(minute);
                startTime.set(getTimeString(hourOfDay,minute));
                notifyDataChanged();
            }
        };

        TimePickerDialog dialog = new TimePickerDialog(view.getContext(), startListener, startTimeHour.get(), startTimeMinute.get(), false);
        dialog.show();
    }
    public void onEndTimeClick(View view){
        TimePickerDialog.OnTimeSetListener endListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTimeHour.set(hourOfDay);
                endTimeMinute.set(minute);
                endTime.set(getTimeString(hourOfDay,minute));
                notifyDataChanged();
//                startViews.get(count).setText(hourOfDay + " : " + minute);
//                schedules.get(count).getStartTime().setHour(hourOfDay);
//                schedules.get(count).getStartTime().setMinute(minute);
//                transInvalidateTime(schedules.get(count).getStartTime(), schedules.get(count).getEndTime());
//                startViews.get(count).setText(getTimeString(schedules.get(count).getStartTime().getHour(), schedules.get(count).getStartTime().getMinute()));
//                endViews.get(count).setText(getTimeString(schedules.get(count).getEndTime().getHour(), schedules.get(count).getEndTime().getMinute()));

            }
        };

        TimePickerDialog dialog = new TimePickerDialog(view.getContext(), endListener, endTimeHour.get(), endTimeMinute.get(), false);
        dialog.show();
    }

    private String getTimeString(int hourOfDay, int minute) {
        String ampm;
        String hourStr = hourOfDay + "";
        String minuteStr = minute + "";
        ampm = (hourOfDay < 12) ? "오전" : "오후";
        if (hourOfDay < 10) hourStr = "0" + hourStr;
        if (hourOfDay > 12) hourStr = "0" + (hourOfDay - 12);
        if (minute < 10) minuteStr = "0" + minuteStr;
        return ampm + " " + hourStr + ":" + minuteStr;
    }
    private void notifyDataChanged(){
        this.schedule.setDay(day.get());
        this.schedule.getStartTime().setHour(startTimeHour.get());
        this.schedule.getStartTime().setMinute(startTimeMinute.get());
        this.schedule.getEndTime().setHour(endTimeHour.get());
        this.schedule.getEndTime().setMinute(endTimeMinute.get());

    }
    public void loadItem(Schedule schedule){
        this.schedule = schedule;
        day.set(schedule.getDay());
        startTimeHour.set(schedule.getStartTime().getHour());
        startTimeMinute.set(schedule.getStartTime().getMinute());
        endTimeHour.set(schedule.getEndTime().getHour());
        endTimeMinute.set(schedule.getEndTime().getMinute());
    }

}
