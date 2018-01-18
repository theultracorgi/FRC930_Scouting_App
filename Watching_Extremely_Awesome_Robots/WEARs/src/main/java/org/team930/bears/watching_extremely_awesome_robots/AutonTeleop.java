package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AutonTeleop extends AppCompatActivity {
    Button aSwitchSubtract, aSwitchAdd, aScaleSubtract, aScaleAdd, tSwitchSubtract, tSwitchAdd, tScaleSubtract, tScaleAdd, tVaultSubtract, tVaultAdd, goToPostMatch;
    TextView aSwitchDisplay, aScaleDisplay, tSwitchDisplay, tScaleDisplay, tVaultDisplay;
    ToggleButton autoline;
    RadioButton onField, parked, elevated;
    Integer aAutoLine, aSwitch, aScale, tSwitch, tScale, tVault, tParked, tElevated;

    PublicResources PR = new PublicResources(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonteleop);

        aAutoLine = 0; //binary
        aSwitch = 0;
        aScale = 0;
        tSwitch = 0;
        tScale = 0;
        tVault = 0;
        tParked = 0; //binary
        tElevated = 0; //binary

        autoline = findViewById(R.id.autoline);

        aSwitchSubtract = findViewById(R.id.aSwitchSubtract);
        aSwitchDisplay = findViewById(R.id.aSwitchDisplay);
        aSwitchAdd = findViewById(R.id.aSwitchAdd);

        aScaleSubtract = findViewById(R.id.aScaleSubtract);
        aScaleDisplay = findViewById(R.id.aScaleDisplay);
        aScaleAdd = findViewById(R.id.aScaleAdd);

        tSwitchSubtract = findViewById(R.id.tSwitchSubtract);
        tSwitchDisplay = findViewById(R.id.tSwitchDisplay);
        tSwitchAdd = findViewById(R.id.tSwitchAdd);

        tScaleSubtract = findViewById(R.id.tScaleSubtract);
        tScaleDisplay = findViewById(R.id.tScaleDisplay);
        tScaleAdd = findViewById(R.id.tScaleAdd);

        tVaultSubtract = findViewById(R.id.tVaultSubtract);
        tVaultDisplay = findViewById(R.id.tVaultDisplay);
        tVaultAdd = findViewById(R.id.tVaultAdd);

        onField = findViewById(R.id.onField);
        parked = findViewById(R.id.parked);
        elevated = findViewById(R.id.elevated);

        goToPostMatch = (Button) findViewById(R.id.gotoPostMatch);

    }

    //AUTON AUTOLINE
    public void setAutoline(View v) {

        if (autoline.isChecked()) {
            aAutoLine = 1;

        } else {
            aAutoLine = 0;

        }
    }

    //AUTON SWITCH
    public void setASwitchSubtract(View v) {
        if (aSwitch == 0) {

        } else {
            aSwitch -= 1;
            aSwitchDisplay.setText(Integer.toString(aSwitch));
        }

    }

    public void setASwitchAdd(View v) {

        aSwitch += 1;
        aSwitchDisplay.setText(Integer.toString(aSwitch));


    }

    //AUTON SCALE
    public void setAScaleSubtract(View v) {
        if (aScale == 0) {

        } else {
            aScale -= 1;
            aScaleDisplay.setText(Integer.toString(aScale));
        }

    }

    public void setAScaleAdd(View v) {

        aScale += 1;
        aScaleDisplay.setText(Integer.toString(aScale));


    }

    //TELEOP SWITCH
    public void setTSwitchSubtract(View v) {
        if (tSwitch == 0) {

        } else {
            tSwitch -= 1;
            tSwitchDisplay.setText(Integer.toString(tSwitch));
        }

    }

    public void setTSwitchAdd(View v) {

        tSwitch += 1;
        tSwitchDisplay.setText(Integer.toString(tSwitch));


    }

    //TELEOP SCALE
    public void setTScaleSubtract(View v) {
        if (tScale == 0) {

        } else {
            tScale -= 1;
            tScaleDisplay.setText(Integer.toString(tScale));
        }

    }

    public void setTScaleAdd(View v) {

        tScale += 1;
        tScaleDisplay.setText(Integer.toString(tScale));


    }

    //TELEOP VAULT
    public void setTVaultSubtract(View v) {
        if (tVault == 0) {

        } else {
            tVault -= 1;
            tVaultDisplay.setText(Integer.toString(tVault));
        }

    }

    public void setTVaultAdd(View v) {

        tVault += 1;
        tVaultDisplay.setText(Integer.toString(tVault));


    }

    public void setOnField(View v) {
        tParked = 0;
        tElevated = 0;

    }

    public void setParked(View v) {
        tParked = 1;
        tElevated = 0;

    }

    public void setElevated(View v) {
        tParked = 0;
        tElevated = 1;

    }

    public void setGoToPostMatch(View v) {

        PR.matchData[2] = aAutoLine.toString();
        PR.matchData[3] = aSwitch.toString();
        PR.matchData[4] = aScale.toString();
        PR.matchData[5] = tSwitch.toString();
        PR.matchData[6] = tScale.toString();
        PR.matchData[7] = tVault.toString();
        PR.matchData[8] = tParked.toString();
        PR.matchData[9] = tElevated.toString();

       Intent goToPostMatch = new Intent(AutonTeleop.this, PostMatch.class);
       startActivity(goToPostMatch);
    }


}



