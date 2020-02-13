package org.team930.bears.wears;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.icu.util.Calendar;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import android.widget.Toast;
@SuppressWarnings("ALL")
public class Settings extends AppCompatActivity {
    Button submitPassword, deleteData;
    EditText password;
    SpinnerView scouterPos;
    ConstraintLayout deleteView;


    String adminPassword, previousAttempt;
    String matchDataPreferences, otherPreferences, numMatchesStored, TBA_AUTH_KEY;

    SharedPreferences matchData, otherSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        previousAttempt = "";


        numMatchesStored = getString(R.string.numStoredMatches);


        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);


        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);
        submitPassword = findViewById(R.id.submitPassword);
        password = findViewById(R.id.password);
        scouterPos = findViewById(R.id.scouterPos);
        deleteData = findViewById(R.id.deleteData);
        deleteView = findViewById(R.id.deleteView);

        scouterPos.setPos(otherSettings.getInt("scouterPos",0));

        if (otherSettings.getBoolean("deleteData", false)) {
            deleteView.setVisibility(View.VISIBLE);
        } else {
            deleteView.setVisibility(View.GONE);
        }

        if (otherSettings.getBoolean("admin", false)) {
           setAdminTrue();
        } else {
           setAdminFalse();
        }
    }

    public void setSubmitPassword(View v) {
        if (password.getText().toString().length() != 0) {
            SharedPreferences.Editor SPOS = otherSettings.edit();

            if (password.getText().toString().toLowerCase().equals(adminPassword)) {
                SPOS.putBoolean("admin", !otherSettings.getBoolean("admin", false));
                SPOS.apply();

                if(otherSettings.getBoolean("admin", false)) {
                    Toast.makeText(this, "You are now Admin!", Toast.LENGTH_SHORT).show();
                    setAdminTrue();
                } else {
                    Toast.makeText(this, "Admin Permissions have been Revoked", Toast.LENGTH_SHORT).show();
                    setAdminFalse();
                }
                password.setText("");
            } else if (!password.getText().toString().toLowerCase().equals(adminPassword) && !password.getText().toString().toLowerCase().equals(previousAttempt)) {
                Toast.makeText(this, "Password is Incorrect", Toast.LENGTH_SHORT).show();
                previousAttempt = password.getText().toString();
            }
        } else {
            //invalid password
        }
    }

    long prevTime = 0;
    public void setDeleteData(View v) {
        long thisTime = Calendar.getInstance().getTimeInMillis();
        if(prevTime<thisTime) {
            if ((thisTime - prevTime) <= 1000) {//1 SEC
                deleteData();
                deleteView.setVisibility(View.GONE);
                super.recreate();
            } else {
                //first tap
                Toast.makeText(this, "Press Again to Confirm Data Clear", Toast.LENGTH_SHORT).show();
                prevTime = thisTime;
            }
        }

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
        Toast.makeText(this, "Data Successfully Deleted", Toast.LENGTH_LONG).show();
        }

    private void setAdminTrue(){
        password.setHint("Type \"revoke\"");
        submitPassword.setText("Revoke");
        adminPassword = "revoke";
    }
    private void setAdminFalse(){
        password.setHint("Admin Password");
        submitPassword.setText("Unlock");
        adminPassword = getResources().getString(R.string.adminPassword);
        password.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
    }


    public void onPause() {
        super.onPause();
        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putInt("scouterPos",scouterPos.getPos());
        SPOS.apply();
    }

}


