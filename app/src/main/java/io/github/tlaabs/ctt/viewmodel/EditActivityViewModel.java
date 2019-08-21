package io.github.tlaabs.ctt.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;
import java.util.List;

import io.github.tlaabs.ctt.R;
import io.github.tlaabs.ctt.contract.EditContract;
import io.github.tlaabs.ctt.ui.view.EditActivity;

public class EditActivityViewModel {
    private final EditContract editView;

    public ObservableInt deleteBtnVisibility = new ObservableInt(View.VISIBLE);
    public ObservableField<String> classTitle = new ObservableField<>("");
    public ObservableField<String> classPlace = new ObservableField<>("");
    public ObservableField<String> professorName = new ObservableField<>("");

    public EditActivityViewModel(EditContract editView) {
        this.editView = editView;
    }

    public void addTimeBtnClick(View view) {
        editView.addNewTime();
    }

    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    public void setDeleteBtnVisibility(boolean on){
        if(on) deleteBtnVisibility.set(View.VISIBLE);
        else deleteBtnVisibility.set(View.GONE);
    }
}
