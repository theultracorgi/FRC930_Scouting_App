package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class ToggleView extends LinearLayout {

    String label;
    TextView labelView;
    Switch toggle;

    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);

        labelView = findViewById(R.id.toggleLabelView);
        toggle = findViewById(R.id.toggle);

        labelView.setText(label);

        toggle.setChecked(false);

    }


    public int getState(){
        if(toggle.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }


    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.toggle_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
