package io.github.tlaabs.ctt.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import io.github.tlaabs.ctt.R;

public class DaySpinnerAdapter extends ArrayAdapter<String> {
    public static final int FONT_SIZE_SP = 17;

    private Context mConetext;

    public DaySpinnerAdapter(Context context) {
        super(
                context,
                android.R.layout.simple_spinner_item,
                context.getResources().getStringArray(R.array.days));
        this.mConetext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(mConetext.getResources().getColor(R.color.colorWeakBlack));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE_SP);
        return label;
    }
}
