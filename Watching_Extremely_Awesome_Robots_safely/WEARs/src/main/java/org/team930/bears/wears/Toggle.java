package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Toggle extends LinearLayout {

    String label, textOn, textOff;
    TextView labelView;
    ToggleButton toggle;

    public Toggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Toggle,
                0, 0);
        this.label = a.getString(R.styleable.Toggle_toggleLabel);
        this.textOn = a.getString(R.styleable.Toggle_textOn);
        this.textOff = a.getString(R.styleable.Toggle_textOff);

        labelView = findViewById(R.id.toggleLabelView);
        toggle = findViewById(R.id.toggle);

        labelView.setText(label);

        toggle.setTextOff(textOff);
        toggle.setTextOn(textOn);

    }


    public int getState(){
        if(toggle.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }


    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.toggle, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
