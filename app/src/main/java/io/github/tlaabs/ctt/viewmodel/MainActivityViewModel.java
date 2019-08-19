package io.github.tlaabs.ctt.viewmodel;

import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import io.github.tlaabs.ctt.contract.MainContract;

public class MainActivityViewModel {
    public final ObservableInt highlightDay = new ObservableInt(1);
    private final MainContract mainView;

    public MainActivityViewModel(MainContract mainView){
        this.mainView = mainView;
    }

    public void onAddClick(View btn){
        Log.d("devsim","onAddClickEvent");
    }
}
