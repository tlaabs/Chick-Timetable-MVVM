package io.github.tlaabs.ctt.viewmodel;

import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.github.tlaabs.timetableview.Schedule;

import java.util.ArrayList;
import java.util.List;

import io.github.tlaabs.ctt.contract.MainContract;

public class MainActivityViewModel {
    public final ObservableInt highlight = new ObservableInt(1);
    private final MainContract mainView;

    public MainActivityViewModel(MainContract mainView) {
        this.mainView = mainView;
    }

    public void menuAddClick(View view) {
        mainView.startEditActivityForAdd();
    }

    //ref:https://stackoverflow.com/questions/32401150/data-bindings-with-custom-listeners-on-custom-view
    public void onStickerSelected(int idx, List<Schedule> schedules){
        mainView.startEditActivityForModify(idx,schedules);
    }
}
