package io.github.tlaabs.ctt.contract;

import com.github.tlaabs.timetableview.Schedule;

import java.util.List;

public interface MainContract {
    void startEditActivityForAdd();

    void startEditActivityForModify(int idx, List<Schedule> schedules);
}
