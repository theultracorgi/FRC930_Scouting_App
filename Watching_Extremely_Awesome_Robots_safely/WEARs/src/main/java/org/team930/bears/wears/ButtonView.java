package org.team930.bears.wears;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;



public class ButtonView extends LinearLayout {

    String label, buttonText;
    TextView labelView;
    Button button;
    int buttonMode;
    Class next_activity;

    String matchDataPreferences, numMatchesStored;

    SharedPreferences matchData, otherSettings;


    public ButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);


        numMatchesStored = getContext().getString(R.string.numStoredMatches);


        matchDataPreferences = getContext().getString(R.string.matchDataPreferences);
        matchData = getContext().getSharedPreferences(matchDataPreferences, 0);

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
        this.buttonMode = a.getInteger(R.styleable.button_button_function,0);
        try {
            next_activity = Class.forName("org.team930.bears.wears." + a.getString((R.styleable.button_next_activity)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            next_activity = null;
        }

        labelView = findViewById(R.id.buttonLabelView);
        button = findViewById(R.id.button);

        button.setText(buttonText);
        labelView.setText(label);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (buttonMode) {
                    case 0:

                        break;
                    case 1:
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

                }
                Intent newActivity = new Intent(getContext(), next_activity);
                getContext().startActivity(newActivity);
                }
        });


    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
