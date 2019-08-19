package io.github.tlaabs.ctt.viewmodel;

import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.github.tlaabs.ctt.contract.EditContract;
import io.github.tlaabs.ctt.ui.view.EditActivity;

public class EditActivityViewModel {
    public ObservableField<List<String>> dayEntires = new ObservableField();

    private final EditContract editView;

    public EditActivityViewModel(EditContract editView) {
        this.editView = editView;
    }


}
