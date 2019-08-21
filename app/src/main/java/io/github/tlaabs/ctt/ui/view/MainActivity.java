package io.github.tlaabs.ctt.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;
import java.util.List;

import io.github.tlaabs.ctt.R;
import io.github.tlaabs.ctt.base.BaseActivity;
import io.github.tlaabs.ctt.contract.MainContract;
import io.github.tlaabs.ctt.databinding.ActivityMainBinding;
import io.github.tlaabs.ctt.model.EditMode;
import io.github.tlaabs.ctt.util.DayUtil;
import io.github.tlaabs.ctt.viewmodel.MainActivityViewModel;

public class MainActivity extends BaseActivity implements MainContract {

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_MODIFY = 2;

    public static final String EXTRA_ALL_SCHEDULES = "all_schedules";
    public static final String EXTRA_IDX = "idx";
    public static final String EXTRA_MODE = "mode";
    public static final String EXTRA_SCHEDULES = "schedules";

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    private TimetableView timetableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityViewModel(this);
        binding.setViewModel(viewModel);

        setupViews();
    }

    private void setupViews() {
        timetableView = binding.timetable;
        viewModel.setHeaderHighlight(DayUtil.getToday());
        timetableView.load(saveManager.get());
    }

    @Override
    public void startEditActivityForAdd() {
        Intent i = new Intent(this, EditActivity.class);
        i.putExtra(EXTRA_MODE, EditMode.ADD);
        i.putExtra(EXTRA_ALL_SCHEDULES, timetableView.getAllSchedulesInStickers());
        startActivityForResult(i, REQUEST_ADD);
    }

    @Override
    public void startEditActivityForModify(int idx, List<Schedule> schedules) {
        Intent i = new Intent(this, EditActivity.class);
        i.putExtra(EXTRA_MODE, EditMode.MODIFY);
        i.putExtra(EXTRA_ALL_SCHEDULES, timetableView.getAllSchedulesInStickersExceptIdx(idx));
        i.putExtra(EXTRA_IDX, idx);
        i.putExtra(EXTRA_SCHEDULES, (ArrayList<Schedule>) schedules);
        startActivityForResult(i, REQUEST_MODIFY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == EditActivity.RESULT_OK_ADD) {
                    ArrayList<Schedule> addItem = (ArrayList<Schedule>) data.getSerializableExtra(EXTRA_SCHEDULES);
                    timetableView.add(addItem);
                }
                break;
            case REQUEST_MODIFY:
                int targetIdx = -1;
                if(data != null) targetIdx = data.getIntExtra(EXTRA_IDX, -1);
                if (resultCode == EditActivity.RESULT_OK_MODIFY) {
                    ArrayList<Schedule> modifiedItem = (ArrayList<Schedule>) data.getSerializableExtra(EXTRA_SCHEDULES);
                    timetableView.edit(targetIdx, modifiedItem);
                } else if (resultCode == EditActivity.RESULT_OK_DELETE) {
                    timetableView.remove(targetIdx);
                }
                break;
        }
        saveManager.save(timetableView.createSaveData());
    }
}
