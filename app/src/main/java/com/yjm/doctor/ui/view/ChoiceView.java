package com.yjm.doctor.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yjm.doctor.R;

/**
 * Created by zx on 2017/12/9.
 */

public class ChoiceView extends FrameLayout implements Checkable {

    private RadioButton mRadioButton;
    private TextView mTextView;

    public ChoiceView(Context context) {
        super(context);
        View.inflate(context, R.layout.radio_item, this);
        mTextView = (TextView) findViewById(R.id.text);
        mRadioButton = (RadioButton) findViewById(R.id.checkedView);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    @Override
    public void setChecked(boolean checked) {
        mRadioButton.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mRadioButton.isChecked();
    }

    @Override
    public void toggle() {
        mRadioButton.toggle();
    }

}
