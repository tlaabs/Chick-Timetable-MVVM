package io.github.tlaabs.ctt.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import io.github.tlaabs.ctt.contract.EditContract;

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

    public void setDeleteBtnVisibility(boolean on){
        if(on) deleteBtnVisibility.set(View.VISIBLE);
        else deleteBtnVisibility.set(View.GONE);
    }
}
