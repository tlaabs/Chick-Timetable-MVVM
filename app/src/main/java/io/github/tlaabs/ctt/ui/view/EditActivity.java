package io.github.tlaabs.ctt.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;

import java.util.ArrayList;
import java.util.List;

import io.github.tlaabs.ctt.R;
import io.github.tlaabs.ctt.adapter.TimeboxListAdapter;
import io.github.tlaabs.ctt.base.BaseActivity;
import io.github.tlaabs.ctt.contract.EditContract;
import io.github.tlaabs.ctt.databinding.ActivityEditBinding;
import io.github.tlaabs.ctt.model.EditMode;
import io.github.tlaabs.ctt.viewmodel.EditActivityViewModel;

public class EditActivity extends BaseActivity implements EditContract, View.OnClickListener {
    public static final int RESULT_OK_ADD = 1;
    public static final int RESULT_OK_MODIFY = 2;
    public static final int RESULT_OK_DELETE = 3;

    private ActivityEditBinding binding;
    private EditActivityViewModel viewModel;

    private RecyclerView timeboxList;
    private TimeboxListAdapter timeboxListAdapter;

    private EditMode mode;
    private ArrayList<Schedule> allSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        viewModel = new EditActivityViewModel(this);
        binding.setViewModel(viewModel);

        checkMode();
        initAllSchedulesFromIntent();
        setupViews();
    }

    private void checkMode() {
        mode = (EditMode) getIntent().getSerializableExtra(MainActivity.EXTRA_MODE);
    }

    private void initAllSchedulesFromIntent() {
        allSchedules = (ArrayList<Schedule>) getIntent().getSerializableExtra(MainActivity.EXTRA_ALL_SCHEDULES);
    }

    private int getTargetIdxFromIntent() {
        return getIntent().getIntExtra(MainActivity.EXTRA_IDX, -1);
    }

    private List<Schedule> getEditableSchedulesFromIntent() {
        return (List<Schedule>) getIntent().getSerializableExtra(MainActivity.EXTRA_SCHEDULES);
    }

    private void restoreViews(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            viewModel.classTitle.set(schedule.getClassTitle());
            viewModel.classPlace.set(schedule.getClassPlace());
            viewModel.professorName.set(schedule.getProfessorName());
        }
        timeboxListAdapter.add(schedules);
    }

    private void setupViews() {
        timeboxList = binding.timeboxList;
        timeboxList.setHasFixedSize(true);
        timeboxList.setLayoutManager(new LinearLayoutManager(this));
        timeboxListAdapter = new TimeboxListAdapter(this);
        timeboxList.setAdapter(timeboxListAdapter);

        binding.editActionbar.submitBtn.setOnClickListener(this);
        binding.editActionbar.deleteBtn.setOnClickListener(this);
        binding.editActionbar.backBtn.setOnClickListener(this);

        if (mode == EditMode.ADD) {
            viewModel.setDeleteBtnVisibility(false);
            timeboxListAdapter.add();
        } else if (mode == EditMode.MODIFY) {
            viewModel.setDeleteBtnVisibility(true);
            restoreViews(getEditableSchedulesFromIntent());
        }
    }

    @Override
    public void addNewTime() {
        timeboxListAdapter.add();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.submit_btn:
                ArrayList<Schedule> thisSchedules = (ArrayList<Schedule>) timeboxListAdapter.getSchedules();
                addMetaDataToSchedules(thisSchedules);
                if (!isValidSchedule(allSchedules, thisSchedules)) {
                    showToastMessage("다른 수업과 시간이 겹칩니다.");
                    return;
                }
                if (mode == EditMode.ADD) {
                    i.putExtra(MainActivity.EXTRA_SCHEDULES, thisSchedules);
                    setResult(RESULT_OK_ADD, i);
                } else if (mode == EditMode.MODIFY) {
                    i.putExtra(MainActivity.EXTRA_SCHEDULES, thisSchedules);
                    i.putExtra(MainActivity.EXTRA_IDX, getTargetIdxFromIntent());
                    setResult(RESULT_OK_MODIFY, i);
                }
                finish();
                break;
            case R.id.delete_btn:
                i.putExtra(MainActivity.EXTRA_IDX, getTargetIdxFromIntent());
                setResult(RESULT_OK_DELETE, i);
                finish();
                break;
            case R.id.back_btn:
                setResult(RESULT_OK,i);
                finish();
                break;
        }
    }

    private void addMetaDataToSchedules(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            schedule.setClassTitle(viewModel.classTitle.get());
            schedule.setClassPlace(viewModel.classPlace.get());
            schedule.setProfessorName(viewModel.professorName.get());
        }
    }

    private boolean isValidSchedule(List<Schedule> allSchedules, List<Schedule> thisSchedules) {
        char[] checker = new char[60 * 24 * 5];
        for (int i = 0; i < 60 * 24 * 5; i++) checker[i] = 0;

        //자신과의 검증
        for (Schedule schedule : thisSchedules) {
            Time thisStartTime = schedule.getStartTime();
            Time thisEndTime = schedule.getEndTime();
            int day = schedule.getDay();
            for (int i = thisStartTime.getHour(), k = thisStartTime.getMinute() + 1; ; k++) {
                int aa = k / 60;
                int pp = k % 60;
                if (i + aa == thisEndTime.getHour() && pp == thisEndTime.getMinute()) {
                    break;
                }
                checker[(60 * 24) * day + (i + aa) * 60 + pp] += 1;
                if (checker[(60 * 24) * day + (i + aa) * 60 + pp] > 1) return false;
            }
        }
        for (int i = 0; i < 60 * 24 * 5; i++) checker[i] = 0;

        //모든 스케줄 검증
        for (Schedule inner : allSchedules) {
            Time innerStartTime = inner.getStartTime();
            Time innerEndTime = inner.getEndTime();
            int day = inner.getDay();
            for (int i = innerStartTime.getHour(), k = innerStartTime.getMinute() + 1; ; k++) {
                int aa = k / 60;
                int pp = k % 60;
                if (i + aa == innerEndTime.getHour() && pp == innerEndTime.getMinute()) {
                    break;
                }
                checker[(60 * 24) * day + (i + aa) * 60 + pp] = 1;
            }
        }
        for (Schedule schedule : thisSchedules) {
            Time thisStartTime = schedule.getStartTime();
            Time thisEndTime = schedule.getEndTime();
            int day = schedule.getDay();
            for (int i = thisStartTime.getHour(), k = thisStartTime.getMinute(); ; k++) {
                int aa = k / 60;
                int pp = k % 60;
                if (checker[(60 * 24) * day + (i + aa) * 60 + pp] > 0) return false;
                if (i + aa == thisEndTime.getHour() && pp == thisEndTime.getMinute()) {
                    break;
                }
            }
        }

        return true;
    }

    public void showToastMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
