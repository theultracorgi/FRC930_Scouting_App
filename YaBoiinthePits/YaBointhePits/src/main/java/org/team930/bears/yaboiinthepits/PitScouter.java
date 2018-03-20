package org.team930.bears.yaboiinthepits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class PitScouter extends AppCompatActivity {
    EditText weight, height;
    CheckBox cExchange, cSwitch, cScale;
    TextView cimDisplay, miniCIMDisplay;

    Integer groundI, portalExchangeI, oExchange, oSwitch, oScale, park, climb, bar, ramp, cims, miniCIMs;
    String outputType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scouter);

        groundI = 0;
        portalExchangeI = 0;

        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);


        oExchange = 0;
        oSwitch = 0;
        oScale = 0;

        cExchange = findViewById(R.id.cExchange);
        cSwitch = findViewById(R.id.cSwitch);
        cScale = findViewById(R.id.cScale);

        outputType = "Place";

        park = 1;
        climb = 0;
        bar = 0;
        ramp = 0;

        cims = 0;
        miniCIMs = 0;

        cimDisplay = findViewById(R.id.cimCount);
        miniCIMDisplay = findViewById(R.id.miniCIMCount);

        cimDisplay.setText(String.format(Locale.ENGLISH));
    }

    public void setNoIntake(View v) {
        groundI = 0;
        portalExchangeI = 0;

    }

    public void setPortalExchange(View v) {
        groundI = 0;
        portalExchangeI = 1;
    }

    public void setGround(View v) {
        groundI = 1;
        portalExchangeI = 1;
    }


    public void setCExchange(View v) {
        if(cExchange.isChecked()){
            oExchange = 1;
        } else {
            oExchange = 0;
        }
    }

    public void setCSwitch(View v) {
        if(cSwitch.isChecked()){
            oSwitch = 1;
        } else {
            oSwitch = 0;
        }
    }

    public void setCScale(View v) {
        if(cScale.isChecked()){
            oScale = 1;
        } else {
            oScale = 0;
        }
    }


    public void setLaunch(View v) {
        outputType = "Launch";
    }

    public void setDrop(View v) {
        outputType = "Drop";
    }

    public void setPlace(View v) {
        outputType = "Place";
    }


    public void setPark(View v) {
        park = 1;
        climb = 0;
        bar = 0;
        ramp = 0;
    }

    public void setClimb(View v) {
        park = 0;
        climb = 1;
        bar = 0;
        ramp = 0;
    }

    public void setBar(View v) {
        park = 0;
        climb = 0;
        bar = 1;
        ramp = 0;
    }

    public void setRamp(View v) {
        park = 0;
        climb = 0;
        bar = 0;
        ramp = 1;
    }

    public void setSubtractCIM(View v) {

    }

    public void setAddCIM(View v) {

    }

    public void setSubtractMiniCIM(View v) {

    }

    public void setAddMiniCIM(View v) {


    }

    public void setSubmitData(View v) {

        //get height/weight/teamnum
    }
}
