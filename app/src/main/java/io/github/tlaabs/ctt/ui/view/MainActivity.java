package io.github.tlaabs.ctt.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.tlaabs.timetableview.TimetableView;

import io.github.tlaabs.ctt.R;
import io.github.tlaabs.ctt.contract.MainContract;
import io.github.tlaabs.ctt.databinding.ActivityMainBinding;
import io.github.tlaabs.ctt.util.DayUtil;
import io.github.tlaabs.ctt.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements MainContract {

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_MODIFY = 2;

    public static final String EXTRA_ALL_SCHEDULES = "all_schedules";

    private ActivityMainBinding binding;
    private TimetableView timetableView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new MainActivityViewModel(this));

        setupViews();
    }

    private void setupViews(){
        timetableView = binding.timetable;

        timetableView.setHeaderHighlight(DayUtil.getToday());
    }

    @Override
    public void startEditActivityForAdd() {
        Intent i = new Intent(this,EditActivity.class);
        i.putExtra(EXTRA_ALL_SCHEDULES, timetableView.getAllSchedulesInStickers());
        startActivityForResult(i,REQUEST_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ADD:
                break;
        }
    }
}
