package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PreMatch extends AppCompatActivity {
    EditText teamNum, matchNum;
    ToggleButton allianceColor;
    char alliance;
    Integer still;

    PublicResources PR = new PublicResources(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);

        still = 0;

        teamNum = findViewById(R.id.teamNum);
        matchNum = findViewById(R.id.matchNum);

        allianceColor = findViewById(R.id.allianceToggle);
    }

    public void setGoToAuton(View v) {
        if(allianceColor.isChecked()){
            alliance = 'r';
        } else {
            alliance = 'b';
        }


        PR.matchData[0] = teamNum.getText().toString();
        PR.matchData[1] = (matchNum.getText().toString() + alliance);

        Intent goToAuton = new Intent(PreMatch.this, AutonTeleop.class);
        startActivity(goToAuton);
    }
    public void setAllianceColor(View v) {
        still += 1;
        if(still == 50) {
            allianceColor.setBackgroundColor(getResources().getColor(R.color.stillGreen));

            Toast.makeText(getApplicationContext(), "STILL Unlocked", Toast.LENGTH_LONG).show();
        } else {

        }
    }

}

