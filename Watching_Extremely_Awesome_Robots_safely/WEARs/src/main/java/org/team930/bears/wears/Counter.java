package org.team930.bears.wears;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Locale;

public class Counter extends LinearLayout {
    String label;
    int count, base;
    int increment;

    TextView display, labelView;
    Button add, sub;


    public Counter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Counter,
                0, 0);
        this.label = a.getString(R.styleable.Counter_label);
        this.count = a.getInt(R.styleable.Counter_base, 0);
        this.base = a.getInt(R.styleable.Counter_base, 0);
        this.increment = a.getInt(R.styleable.Counter_increment, 1);

        labelView = findViewById(R.id.label);
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



    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.counter, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}

