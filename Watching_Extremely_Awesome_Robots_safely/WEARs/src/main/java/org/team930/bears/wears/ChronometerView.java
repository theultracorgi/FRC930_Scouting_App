package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ChronometerView extends LinearLayout {

    String label;
    TextView labelView;
    Button start, stop;
    Chronometer chronometer;
    boolean running;
    long pauseOffset;
    Animation anim;


    public ChronometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);


        labelView = findViewById(R.id.chronometerLabelView);
        chronometer = findViewById(R.id.chronometer);
        start = findViewById(R.id.chronometerStart);
        stop = findViewById(R.id.chronometerStop);

        running = false;
        labelView.setText(label);
        start.setText("Start");
        stop.setText("Reset");

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(500);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChronometer();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(running){
                    pauseChronometer();
                } else {
                    resetChronometer();
                }

            }
        });

    }

    public double getChonometerReading() {
        return (double) (SystemClock.elapsedRealtime()-pauseOffset);
    }


    private void startChronometer(){
        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            chronometer.start();
            running = true;
            start.setText("Resume");
            stop.setText("Pause");
            chronometer.clearAnimation();


        }
    }

    private void pauseChronometer() {
        if(running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            start.setText("Resume");
            stop.setText("Reset");
            chronometer.startAnimation(anim);

        }
    }

    private void resetChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        start.setText("Start");
        stop.setText("Reset");
        chronometer.clearAnimation();
    }



    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chronometer_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
