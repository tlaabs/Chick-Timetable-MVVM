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
    public ObservableInt startTimeHour = new ObservableInt(9);
    public ObservableInt startTimeMinute = new ObservableInt(0);
    public ObservableField<String> startTime = new ObservableField<>("오전 09:00");
    public ObservableInt endTimeHour = new ObservableInt(10);
    public ObservableInt endTimeMinute = new ObservableInt(0);
    public ObservableField<String> endTime = new ObservableField<>("오전 10:00");

    private Schedule schedule;

    public void onDaySpinnerItemSelected(AdapterView<?> parent, View view, int position, long id) {
        day.set(position);
        notifyDataChanged();
    }

    public void onStartTimeClick(View view) {
        TimePickerDialog.OnTimeSetListener startListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeHour.set(hourOfDay);
                startTimeMinute.set(minute);
                transInvalidateTime();
                notifyDataChanged();
            }
        };

        TimePickerDialog dialog = new TimePickerDialog(view.getContext(), startListener, startTimeHour.get(), startTimeMinute.get(), false);
        dialog.show();
    }

    public void onEndTimeClick(View view) {
        TimePickerDialog.OnTimeSetListener endListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTimeHour.set(hourOfDay);
                endTimeMinute.set(minute);
                transInvalidateTime();
                notifyDataChanged();

            }
        };

        TimePickerDialog dialog = new TimePickerDialog(view.getContext(), endListener, endTimeHour.get(), endTimeMinute.get(), false);
        dialog.show();
    }

    private String getTimeString(int hourOfDay, int minute) {
        StringBuilder hourSb = new StringBuilder();
        StringBuilder minuteSb = new StringBuilder();
        StringBuilder resultSb = new StringBuilder();
        String ampm = (hourOfDay < 12) ? "오전" : "오후";
        if (hourOfDay < 10) hourSb.append(0).append(hourOfDay);
        else if (hourOfDay > 12) hourSb.append(0).append(hourOfDay - 12);
        else hourSb.append(hourOfDay);

        if (minute < 10) minuteSb.append(0).append(minute);
        else minuteSb.append(minute);
        return resultSb.append(ampm).append(" ").append(hourSb).append(":").append(minuteSb).toString();
    }

    private void transInvalidateTime() {
        //시간 범위 : 9~20
        if (startTimeHour.get() < 9) startTimeHour.set(9);
        if (startTimeHour.get() > 20) startTimeHour.set(20);
        if (endTimeHour.get() < 9) endTimeHour.set(9);
        if (endTimeHour.get() > 20) endTimeHour.set(20);

        if (startTimeHour.get() > endTimeHour.get()) { //시작시간이 더 느리면
            endTimeHour.set(startTimeHour.get() + 1);
            endTimeMinute.set(0);
        } else if (startTimeHour.get() == endTimeHour.get()) {
            if (startTimeMinute.get() >= endTimeMinute.get()) {
                endTimeHour.set(startTimeHour.get() + 1);
                endTimeMinute.set(0);
            }
        }
    }

    private void updateTimeText() {
        startTime.set(getTimeString(startTimeHour.get(), startTimeMinute.get()));
        endTime.set(getTimeString(endTimeHour.get(), endTimeMinute.get()));
    }

    private void notifyDataChanged() {
        updateTimeText();
        this.schedule.setDay(day.get());
        this.schedule.getStartTime().setHour(startTimeHour.get());
        this.schedule.getStartTime().setMinute(startTimeMinute.get());
        this.schedule.getEndTime().setHour(endTimeHour.get());
        this.schedule.getEndTime().setMinute(endTimeMinute.get());

    }

    public void loadItem(Schedule schedule) {
        this.schedule = schedule;
        day.set(schedule.getDay());
        startTimeHour.set(schedule.getStartTime().getHour());
        startTimeMinute.set(schedule.getStartTime().getMinute());
        endTimeHour.set(schedule.getEndTime().getHour());
        endTimeMinute.set(schedule.getEndTime().getMinute());
        updateTimeText();
    }

}
