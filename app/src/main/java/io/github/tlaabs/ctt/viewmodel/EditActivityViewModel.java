package io.github.tlaabs.ctt.viewmodel;

import io.github.tlaabs.ctt.contract.EditContract;
import io.github.tlaabs.ctt.ui.view.EditActivity;

public class EditActivityViewModel {

    private final EditContract editView;

    public EditActivityViewModel(EditContract editView) {
        this.editView = editView;
    }
}
