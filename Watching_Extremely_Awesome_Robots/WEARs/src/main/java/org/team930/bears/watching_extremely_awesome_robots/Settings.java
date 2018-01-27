package org.team930.bears.watching_extremely_awesome_robots;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class Settings extends AppCompatActivity {

    Spinner scouterList;
    LinearLayout stillEnabler, deleteBar;
    ToggleButton stillFRC;
    Button deleteData;
    ContextThemeWrapper ctw;
    AlertDialog.Builder builder;

    String stillPreferences, matchDataPreferences, otherPreferences, numMatchesStored;

    SharedPreferences matchData, stillEnabled, otherSettings;
    MediaPlayer Still;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        builder = new AlertDialog.Builder(ctw);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        stillPreferences = getString(R.string.stillPreferences);
        otherPreferences = getString(R.string.otherPreferences);
        numMatchesStored = getString(R.string.numStoredMatches);

        matchData = getSharedPreferences(matchDataPreferences, 0);
        stillEnabled = getSharedPreferences(stillPreferences, 0);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        Still = MediaPlayer.create(this, R.raw.still_frc_mixdown);
        Still.setLooping(true);

        stillEnabler = findViewById(R.id.stillEnabler);
        stillFRC = findViewById(R.id.stillFRC);
        scouterList = findViewById(R.id.scouterList);
        deleteData = findViewById(R.id.deleteData);
        deleteBar = findViewById(R.id.deleteBar);

        scouterList.setSelection(otherSettings.getInt("scouterIDNum", 0));

        if (stillEnabled.getBoolean("stillEnabled", false)) {
            stillEnabler.setVisibility(View.VISIBLE);
        }
        if (otherSettings.getBoolean("deleteData", false)) {
            deleteBar.setVisibility(View.VISIBLE);
        }

    }

    public void setStillFRC(View v) {

        if (stillFRC.isChecked()) {
            Still.start();

        } else {
            Still.pause();

        }
    }

    public void setDeleteData(View v) {

        builder.setTitle("Delete Data");
        builder.setMessage("This will delete all match data you have on the device");
        builder.setCancelable(true);


        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        SharedPreferences.Editor SPOS = otherSettings.edit();

                        SPOS.putBoolean("deleteData", false);
                        SPOS.putInt(numMatchesStored, 0);

                        SPOS.putString("csvMatch1", "");
                        SPOS.putString("csvMatch2", "");
                        SPOS.putString("csvMatch3", "");
                        SPOS.putString("csvMatch4", "");
                        SPOS.putString("csvMatch5", "");
                        SPOS.putString("csvMatch6", "");
                        SPOS.putInt("scannedID", 0);
                        SPOS.putBoolean("csVisible", false);

                        SPOS.putBoolean("dataAvailable", false);

                        SPOS.commit();

                        SharedPreferences.Editor SPMD = matchData.edit();

                        SPMD.putString("teamNum", "");
                        SPMD.putString("matchNum", "");
                        SPMD.putString("aAutoline", "");
                        SPMD.putString("aSwitch", "");
                        SPMD.putString("aScale", "");
                        SPMD.putString("tSwitch", "");
                        SPMD.putString("tScale", "");
                        SPMD.putString("tVault", "");
                        SPMD.putString("tParked", "");
                        SPMD.putString("tElevated", "");
                        SPMD.putString("disabled", "");
                        SPMD.putString("comments", "");

                        SPMD.putString("match1", "");
                        SPMD.putString("match2", "");
                        SPMD.putString("match3", "");
                        SPMD.putString("match4", "");
                        SPMD.putString("match5", "");
                        SPMD.putString("match6", "");

                        SPMD.commit();


                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();




    }

    protected void onStop() {
        // call the superclass method first
        super.onStop();




        SharedPreferences.Editor SPMD = matchData.edit();

        switch (scouterList.getSelectedItemPosition()) {
            case 0:
                SPMD.putString("scouterID", "ARr005");
                SPMD.commit();
                break;
            case 1:
                SPMD.putString("scouterID", "AMk003");
                SPMD.commit();
                break;
            case 2:
                SPMD.putString("scouterID", "ASr006");
                SPMD.commit();
                break;
            case 3:
                SPMD.putString("scouterID", "ABr001");
                SPMD.commit();
                break;
            case 4:
                SPMD.putString("scouterID", "AJu002");
                SPMD.commit();
                break;
            case 5:
                SPMD.putString("scouterID", "AWk007");
                SPMD.commit();
                break;
            case 6:
                SPMD.putString("scouterID", "AOk004");
                SPMD.commit();
                break;
            case 7:
                SPMD.putString("scouterID", "AAx000");
                SPMD.commit();
                break;
            case 8:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 9:
                SPMD.putString("scouterID", "DMb010");
                SPMD.commit();
                break;
            case 10:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 11:
                SPMD.putString("scouterID", "DEu009");
                SPMD.commit();
                break;
            case 12:
                SPMD.putString("scouterID", "EEx011");
                SPMD.commit();
                break;
            case 13:
                SPMD.putString("scouterID", "EZu013");
                SPMD.commit();
                break;
            case 14:
                SPMD.putString("scouterID", "EZr012");
                SPMD.commit();
                break;
            case 15:
                SPMD.putString("scouterID", "JMr017");
                SPMD.commit();
                break;
            case 16:
                SPMD.putString("scouterID", "JDk015");
                SPMD.commit();
                break;
            case 17:
                SPMD.putString("scouterID", "JPx018");
                SPMD.commit();
                break;
            case 18:
                SPMD.putString("scouterID", "JWr021");
                SPMD.commit();
                break;
            case 19:
                SPMD.putString("scouterID", "JKw016");
                SPMD.commit();
                break;
            case 20:
                SPMD.putString("scouterID", "JWr020");
                SPMD.commit();
                break;
            case 21:
                SPMD.putString("scouterID", "JVb019");
                SPMD.commit();
                break;
            case 22:
                SPMD.putString("scouterID", "KKr022");
                SPMD.commit();
                break;
            case 23:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 24:
                SPMD.putString("scouterID", "KMx023");
                SPMD.commit();
                break;
            case 25:
                SPMD.putString("scouterID", "KSk024");
                SPMD.commit();
                break;
            case 26:
                SPMD.putString("scouterID", "KSu025");
                SPMD.commit();
                break;
            case 27:
                SPMD.putString("scouterID", "MMr028");
                SPMD.commit();
                break;
            case 28:
                SPMD.putString("scouterID", "MRk029");
                SPMD.commit();
                break;
            case 29:
                SPMD.putString("scouterID", "MRb030");
                SPMD.commit();
                break;
            case 30:
                SPMD.putString("scouterID", "MHb027");
                SPMD.commit();
                break;
            case 31:
                SPMD.putString("scouterID", "NBr031");
                SPMD.commit();
                break;
            case 32:
                SPMD.putString("scouterID", "NKk032");
                SPMD.commit();
                break;
            case 33:
                SPMD.putString("scouterID", "NRr034");
                SPMD.commit();
                break;
            case 34:
                SPMD.putString("scouterID", "SUPAHOTFIREx048");
                SPMD.commit();
                break;
            case 35:
                SPMD.putString("scouterID", "NNb033");
                SPMD.commit();
                break;
            case 36:
                SPMD.putString("scouterID", "NFx047");
                SPMD.commit();
                break;
            case 37:
                SPMD.putString("scouterID", "PRo036");
                SPMD.commit();
                break;
            case 38:
                SPMD.putString("scouterID", "SRr039");
                SPMD.commit();
                break;
            case 39:
                SPMD.putString("scouterID", "SBr037");
                SPMD.commit();
                break;
            case 40:
                SPMD.putString("scouterID", "SGi038");
                SPMD.commit();
                break;
            case 41:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 42:
                SPMD.putString("scouterID", "SSk040");
                SPMD.commit();
                break;
            case 43:
                SPMD.putString("scouterID", "TSr043");
                SPMD.commit();
                break;
            case 44:
                SPMD.putString("scouterID", "TGk042");
                SPMD.commit();
                break;
            case 45:
                SPMD.putString("scouterID", "TBk041");
                SPMD.commit();
                break;
            case 46:
                SPMD.putString("scouterID", "VKb044");
                SPMD.commit();
                break;
            case 47:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();
                break;
            case 48:
                SPMD.putString("scouterID", "ZAk045");
                SPMD.commit();
                break;
            case 49:
                SPMD.putString("scouterID", "ZYb046");
                SPMD.commit();
                break;
            default:
                SPMD.putString("scouterID", "Low'a");
                SPMD.commit();


        }

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putInt("scouterIDNum", scouterList.getSelectedItemPosition());

        SPOS.commit();


    }

}


