package org.team930.bears.wears;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

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
    LinearLayout adminUnlock;
    Button submitPassword;
    EditText password;
    SpinnerView scouterPos;
    boolean doubleBackToExitPressedOnce = false;

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
        adminUnlock = findViewById(R.id.adminUnlock);
        submitPassword = findViewById(R.id.submitPassword);
        password = findViewById(R.id.password);
        scouterPos = findViewById(R.id.scouterPos);

        scouterPos.setPos(otherSettings.getInt("scouterPos",0));

        if (otherSettings.getBoolean("deleteData", false)) {

        } else {

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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}


