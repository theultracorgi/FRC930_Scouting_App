package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class SeekbarView extends LinearLayout {

    String label, floorName, ceilName, zeroName;
    int floor, ceil, seekbarProgress;
    TextView labelView, progressView;
    SeekBar seekbar;



    public SeekbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);

        seekbarProgress =0;

        labelView = findViewById(R.id.seekbarLabelView);
        progressView = findViewById(R.id.seekbarProgress);
        seekbar = findViewById(R.id.seekbar);

        labelView.setText(label);
        displayStatus();

        seekbar.setMax(ceil);



        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressView.setText(Integer.toString(progress));
                seekbarProgress = seekbar.getProgress();

                displayStatus();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void displayStatus(){
        if (seekbarProgress == ceil && ceilName != null) {
            progressView.setText(ceilName);
        } else if(seekbarProgress == floor && floorName != null) {
            progressView.setText(floorName);
        }else if(seekbarProgress == 0 && zeroName != null) {
            progressView.setText(zeroName);
        } else {
            progressView.setText(Integer.toString(seekbarProgress));
        }
    }

    public int getProgress() {
        return seekbarProgress;
    }


    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.seekbar,
                0, 0);
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.ceil = a.getInt(R.styleable.seekbar_ceil,5);
        this.floor = a.getInt(R.styleable.seekbar_floor,0);
        this.floorName = a.getString(R.styleable.seekbar_floor_name);
        this.ceilName = a.getString(R.styleable.seekbar_ceil_name);
        this.zeroName = a.getString(R.styleable.seekbar_zero_name);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.seekbar_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
