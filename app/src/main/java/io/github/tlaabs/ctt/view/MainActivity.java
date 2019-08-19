package io.github.tlaabs.ctt.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.github.tlaabs.timetableview.TimetableView;

import io.github.tlaabs.ctt.R;
import io.github.tlaabs.ctt.contract.MainContract;
import io.github.tlaabs.ctt.databinding.ActivityMainBinding;
import io.github.tlaabs.ctt.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements MainContract {

    private ImageButton addBtn;
    private TimetableView timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new MainActivityViewModel(this));
    }


}
