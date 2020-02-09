package org.team930.bears.wears;

import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;

import java.util.Locale;

public class CounterView extends LinearLayout {
    String label;
    int count, base;
    int increment;

    TextView display, labelView;
    Button add, sub;


    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);

        labelView = findViewById(R.id.counterLabelView);
        display = findViewById(R.id.counterD);
        add = findViewById(R.id.counterAdd);
        sub = findViewById(R.id.counterSub);

        labelView.setText(label);
        display.setText(Integer.toString(count));

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > base) {
                    count -= increment;
                    display.setText(String.format(Locale.ENGLISH, "%d", count));
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    count += increment;
                    display.setText(String.format(Locale.ENGLISH, "%d", count));
            }
        });

    }






    public int getCount(){
        return count;
    }



    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.counter,
                0, 0);
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.count = a.getInt(R.styleable.counter_base, 0);
        this.base = a.getInt(R.styleable.counter_base, 0);
        this.increment = a.getInt(R.styleable.counter_increment, 1);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.counter_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}

