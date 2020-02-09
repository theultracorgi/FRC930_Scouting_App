package org.team930.bears.wears;

import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Locale;

public class CheckboxView extends LinearLayout {
    String label;
    String [] list;
    CharSequence[] tempList;
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    LinearLayout checkboxLayout;
    TextView labelView;


    public CheckboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);

        list = new String[tempList.length];
        int i=0;
        for(CharSequence ch: tempList){
            list[i++] = ch.toString();
        }


        labelView = findViewById(R.id.checkboxLabelView);
        checkboxLayout = findViewById(R.id.checkboxLayout);
        labelView.setText(label);


        for (int j = 0; j < list.length; j++) {

            final CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(list[j]);
            checkBoxes.add(checkBox);
            checkBox.setTypeface(ResourcesCompat.getFont(getContext(), R.font.montserratregular));
            checkboxLayout.addView(checkBox);
        }
    }


    public int [] getCheckBoxCheckedArray(){
        int [] checkBoxStates = new int [checkBoxes.size()];
        for (int i = 0; i < checkBoxes.size(); i++) {

            if(checkBoxes.get(i).isChecked()) {
                checkBoxStates[i] = 1;
            } else {
                checkBoxStates[i] = 0;
            }
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

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.checkbox_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}

