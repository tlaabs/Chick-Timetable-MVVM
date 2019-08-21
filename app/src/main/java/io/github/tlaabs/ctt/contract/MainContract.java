package io.github.tlaabs.ctt.contract;

import com.github.tlaabs.timetableview.Schedule;

import java.util.ArrayList;
import java.util.List;

import io.github.tlaabs.ctt.model.Days;

public interface MainContract {
    void startEditActivityForAdd();
    void startEditActivityForModify(int idx, List<Schedule> schedules);
}
