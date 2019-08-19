package io.github.tlaabs.ctt.ui.view;

import android.databinding.DataBindingUtil;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import io.github.tlaabs.ctt.R;
import io.github.tlaabs.ctt.adapter.DaySpinnerAdapter;
import io.github.tlaabs.ctt.contract.EditContract;
import io.github.tlaabs.ctt.databinding.ActivityEditBinding;
import io.github.tlaabs.ctt.viewmodel.EditActivityViewModel;

public class EditActivity extends AppCompatActivity implements EditContract {
    public static final int RESULT_OK_ADD = 1;
    public static final int RESULT_OK_MODIFY = 2;
    public static final int RESULT_OK_DELETE = 3;

    private ActivityEditBinding binding;
    private RecyclerView timeboxList;
    private RecyclerView.Adapter timeboxListAdapter;
    private ArrayAdapter<String> daySpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        binding.setViewModel(new EditActivityViewModel(this));

        setupViews();
    }

    private void setupViews(){
       timeboxList = binding.timeboxList;
       timeboxList.setHasFixedSize(true);
       timeboxList.setLayoutManager(new LinearLayoutManager(this));
    }
}