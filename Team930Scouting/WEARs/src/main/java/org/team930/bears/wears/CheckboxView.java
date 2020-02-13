package org.team930.bears.wears;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.CompoundButtonCompat;

import java.util.ArrayList;
import java.util.Locale;

public class CheckboxView extends LinearLayout {
    String label;
    String[] list;
    CharSequence[] tempList;
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    boolean otherEnabled;

    LinearLayout checkboxLayout;
    TextView labelView;
    EditText other;


    public CheckboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);


        if(otherEnabled){
            list = new String[tempList.length + 1];
        } else {
            list = new String[tempList.length];
        }

        int i = 0;
        for (CharSequence ch : tempList) {
            list[i++] = ch.toString();
        }
        if(otherEnabled) {
            list[list.length-1] = "Other";
        }


        labelView = findViewById(R.id.checkboxLabelView);
        checkboxLayout = findViewById(R.id.checkboxLayout);
        labelView.setText(label);


        for (int j = 0; j < list.length; j++) {

            final CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(list[j]);
            checkBoxes.add(checkBox);
            checkBox.setTextAppearance(R.style.checkBoxStyle);
            if (Math.random() >= .5) {
                CompoundButtonCompat.setButtonTintList(checkBox, ContextCompat.getColorStateList(getContext(), R.color.colorAccentOne));
            } else {
                CompoundButtonCompat.setButtonTintList(checkBox, ContextCompat.getColorStateList(getContext(), R.color.colorBase));
            }
            checkboxLayout.addView(checkBox);
        }

        if (otherEnabled) {
            other = new EditText(getContext());
            other.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(other.getLayoutParams());
            lp.setMargins(4, 4, 4, 4);
            other.setLayoutParams(lp);
            other.setHint("Other (Please Specify)");
            other.setTextAppearance(R.style.textBoxStyle);
            other.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            checkboxLayout.addView(other);
            other.setVisibility(GONE);
            checkBoxes.get(checkBoxes.size() - 1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(checkBoxes.get(checkBoxes.size() - 1).isChecked()) {
                        other.setVisibility(VISIBLE);
                    } else {
                        other.setVisibility(GONE);
                    }
                }
            }
            );
        }



    }


    public int[] getCheckBoxCheckedArray() {
        int[] checkBoxStates = new int[checkBoxes.size()];
        for (int i = 0; i < checkBoxes.size(); i++) {

            if (checkBoxes.get(i).isChecked()) {
                checkBoxStates[i] = 1;
            } else {
                checkBoxStates[i] = 0;
            }
        }
        return checkBoxStates;
    }


    public String[] getCheckedItemsArray() {

        String[] checkBoxStates = new String[checkBoxes.size()];
        for (int i = 0; i < checkBoxes.size()-1; i++) {

            if (checkBoxes.get(i).isChecked()) {
                checkBoxStates[i] = checkBoxes.get(i).getText().toString();
            } else {
                checkBoxStates[i] = "";
            }
        }
        if(checkBoxes.get(checkBoxes.size()-1).isChecked()) {
            checkBoxStates[checkBoxStates.length-1] = other.getText().toString();
        } else {
            checkBoxStates[checkBoxStates.length-1] = "";
        }
        return checkBoxStates;

    }

    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.tempList = b.getTextArray(R.styleable.global_android_entries);
        this.otherEnabled = b.getBoolean(R.styleable.global_other_enabled, false);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.checkbox_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}

