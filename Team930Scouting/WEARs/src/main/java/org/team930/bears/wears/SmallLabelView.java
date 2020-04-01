package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SmallLabelView extends LinearLayout {

    String label;
    TextView labelView;
    boolean center;

    public SmallLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);


        labelView = findViewById(R.id.labelLabelView);

        if(center) {
            labelView.setGravity(Gravity.CENTER);
        }

        labelView.setText(label);


    }





    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.label,
                0, 0);
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.center = a.getBoolean(R.styleable.label_center, false);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.small_label_view, this);

    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
