package org.team930.bears.wears;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Attr;

import java.util.Locale;
import java.util.logging.LogRecord;


public class ButtonView extends LinearLayout {

    String label, buttonText, confirmMessage;
    TextView labelView;
    Button button;
    int buttonMode, clickCount;
    Class next_activity;

    String matchDataPreferences, numMatchesStored, otherPreferences;

    SharedPreferences matchData, otherSettings;


    public ButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);


        numMatchesStored = getContext().getString(R.string.numStoredMatches);


        matchDataPreferences = getContext().getString(R.string.matchDataPreferences);
        matchData = getContext().getSharedPreferences(matchDataPreferences, 0);

        otherPreferences = getContext().getString(R.string.otherPreferences);
        otherSettings = getContext().getSharedPreferences(otherPreferences, 0);


        clickCount = 0;

        labelView = findViewById(R.id.buttonLabelView);
        button = findViewById(R.id.button);

        button.setText(buttonText);
        labelView.setText(label);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;
                Handler handler = new Handler();
                if(buttonMode == 0) {
                    Intent newActivity = new Intent(getContext(), next_activity);
                    getContext().startActivity(newActivity);

                } else if(buttonMode == 1 || buttonMode == 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (clickCount == 1) {
                                Toast.makeText(getContext(), confirmMessage, Toast.LENGTH_SHORT).show();
                            } else if (clickCount == 2) {
                                if (buttonMode == 1) {
                                    deleteData();
                                }
                                Intent newActivity = new Intent(getContext(), next_activity);
                                getContext().startActivity(newActivity);
                            }
                            clickCount = 0;
                        }
                    }, 700);
                }
                }


        });

    }

    private void deleteData() {

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putBoolean("deleteData", false);
        SPOS.putInt(numMatchesStored, 0);

        SPOS.putString("scannedMatches", "");
        SPOS.putInt("scannedID", 0);
        SPOS.putBoolean("csVisible", false);

        SPOS.putBoolean("dataAvailable", false);

        SPOS.apply();

        SharedPreferences.Editor SPMD = matchData.edit();


        SPMD.putString("preMatchVals", "");
        SPMD.putString("autonTeleopVals", "");
        SPMD.putString("postMatchVals", "");


        SPMD.putString("firstQR", "");
        SPMD.putString("secondQR", "");

        SPMD.apply();
        Toast.makeText(getContext(), "Data Successfully Deleted", Toast.LENGTH_LONG).show();
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.button,
                0, 0);

        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.buttonText = a.getString(R.styleable.button_text);
        this.buttonMode = a.getInteger(R.styleable.button_button_function, 0);
        this.confirmMessage = a.getString(R.styleable.button_confirm_message);


        try {
            next_activity = Class.forName("org.team930.bears.wears." + a.getString((R.styleable.button_next_activity)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            next_activity = null;
        }

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
