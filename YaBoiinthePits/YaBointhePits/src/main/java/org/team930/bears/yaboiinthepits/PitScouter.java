package org.team930.bears.yaboiinthepits;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class PitScouter extends AppCompatActivity {
    EditText teamNum, weight, height, frontOtherType, middleOtherType, secondMiddleOtherType, backOtherType, orientationOtherType, otherAuton;
    CheckBox cExchange, cSwitch, cScale, autonLeftC, autonMiddleC, autonRightC, aAutoline, aSwitch, aScale, aSwitchScale, aScaleSwitch;
    TextView cimDisplay, miniCIMDisplay;
    ToggleButton autoDelay;
    LinearLayout middleWheelsView, secondMiddleWheelsView, frontOtherLayout, middleOtherLayout, secondMiddleOtherLayout, backOtherLayout, orientationOtherLayout;
    Spinner wheelNum, frontWheels, middleWheels, secondMiddleWheels, backWheels, orientation;
    int count;
    File path, dir, file;


    Integer groundI, portalExchangeI, oExchange, oSwitch, oScale, park, climb, bar, ramp, cims, miniCIMs, wheelCount, aDelay;
    String outputType, frontWheelType, middleWheelType, secondMiddleWheelType, backWheelType, orientationType, startingPos, autonPaths, dataKey;
    String teamNumFinal, startingPosFinal, autoDelayFinal, autonPathsFinal, groundIntakeFinal, exchangeIntakeFinal, portalIntakeFinal, outputExchangeFinal, outputSwitchFinal, outputScaleFinal, typeOutputFinal, rampFinal, barFinal, climbFinal, parkFinal, heightFinal, weightFinal, cimsFinal, miniCIMsFinal, frontWheelFinal, middleWheelFinal, backWheelFinal, secondMiddleWheelFinal, configFinal, fullDataFinal;

    AlertDialog.Builder submit, backPressed;
    ContextThemeWrapper ctw;
    SharedPreferences data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scouter);

        count = 0;


        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        submit = new AlertDialog.Builder(ctw);
        backPressed = new AlertDialog.Builder(ctw);

        dataKey = getString(R.string.data);
        data = getSharedPreferences(dataKey, 0);

        groundI = 0;
        portalExchangeI = 0;

        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        teamNum = findViewById(R.id.teamNum);


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

        cimDisplay.setText(String.format(Locale.ENGLISH, "%d", cims));
        miniCIMDisplay.setText(String.format(Locale.ENGLISH, "%d", miniCIMs));

        wheelNum = findViewById(R.id.numWheels);

        middleWheelsView = findViewById(R.id.middleWheelView);
        secondMiddleWheelsView = findViewById(R.id.secondMiddleWheelView);

        frontWheelType = "Traction";
        middleWheelType = "Traction";
        secondMiddleWheelType = "Traction";
        backWheelType = "Traction";

        frontWheels = findViewById(R.id.frontWheels);
        middleWheels = findViewById(R.id.middleWheels);
        secondMiddleWheels = findViewById(R.id.backMiddleWheels);
        backWheels = findViewById(R.id.backWheels);

        frontOtherType = findViewById(R.id.frontOther);
        middleOtherType = findViewById(R.id.middleOther);
        secondMiddleOtherType = findViewById(R.id.backMiddleOther);
        backOtherType = findViewById(R.id.backOther);

        frontOtherLayout = findViewById(R.id.frontOtherLayout);
        middleOtherLayout = findViewById(R.id.middleOtherLayout);
        secondMiddleOtherLayout = findViewById(R.id.backMiddleOtherLayout);
        backOtherLayout = findViewById(R.id.backOtherLayout);


        wheelNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (wheelNum.getSelectedItemId() == 0) {
                    wheelCount = 4;

                    middleWheelsView.setVisibility(View.GONE);
                    secondMiddleWheelsView.setVisibility(View.GONE);

                    middleWheelType = "";
                    secondMiddleWheelType = "";

                } else if (wheelNum.getSelectedItemId() == 1) {
                    wheelCount = 6;

                    middleWheelsView.setVisibility(View.VISIBLE);
                    secondMiddleWheelsView.setVisibility(View.GONE);

                    middleWheelType = "Traction";
                    secondMiddleWheelType = "";
                } else {
                    wheelCount = 8;

                    middleWheelsView.setVisibility(View.VISIBLE);
                    secondMiddleWheelsView.setVisibility(View.VISIBLE);

                    middleWheelType = "Traction";
                    secondMiddleWheelType = "Traction";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        frontWheels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (frontWheels.getSelectedItemId() == 0) {
                    frontWheelType = "Traction";
                    frontOtherLayout.setVisibility(View.GONE);

                } else if (frontWheels.getSelectedItemPosition() == 1) {
                    frontWheelType = "Omni";
                    frontOtherLayout.setVisibility(View.GONE);

                } else if (frontWheels.getSelectedItemPosition() == 2) {
                    frontWheelType = "Pneumatic";
                    frontOtherLayout.setVisibility(View.GONE);

                } else {
                    frontWheelType = "";
                    frontOtherLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        middleWheels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (middleWheels.getSelectedItemId() == 0) {
                    middleWheelType = "Traction";
                    middleOtherLayout.setVisibility(View.GONE);

                } else if (middleWheels.getSelectedItemPosition() == 1) {
                    middleWheelType = "Omni";
                    middleOtherLayout.setVisibility(View.GONE);

                } else if (middleWheels.getSelectedItemPosition() == 2) {
                    middleWheelType = "Pneumatic";
                    middleOtherLayout.setVisibility(View.GONE);

                } else if (middleWheels.getSelectedItemPosition() == 3) {
                    middleWheelType = "Butterfly Switch";
                    middleOtherLayout.setVisibility(View.GONE);

                } else {
                    middleWheelType = "";
                    middleOtherLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        secondMiddleWheels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (secondMiddleWheels.getSelectedItemId() == 0) {
                    secondMiddleWheelType = "Traction";
                    secondMiddleOtherLayout.setVisibility(View.GONE);

                } else if (secondMiddleWheels.getSelectedItemPosition() == 1) {
                    secondMiddleWheelType = "Omni";
                    secondMiddleOtherLayout.setVisibility(View.GONE);

                } else if (secondMiddleWheels.getSelectedItemPosition() == 2) {
                    secondMiddleWheelType = "Pneumatic";
                    secondMiddleOtherLayout.setVisibility(View.GONE);

                } else if (secondMiddleWheels.getSelectedItemPosition() == 3) {
                    secondMiddleWheelType = "Butterfly Switch";
                    secondMiddleOtherLayout.setVisibility(View.GONE);

                } else {
                    secondMiddleWheelType = "";
                    secondMiddleOtherLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        backWheels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (backWheels.getSelectedItemId() == 0) {
                    backWheelType = "Traction";
                    backOtherLayout.setVisibility(View.GONE);

                } else if (backWheels.getSelectedItemPosition() == 1) {
                    backWheelType = "Omni";
                    backOtherLayout.setVisibility(View.GONE);

                } else if (backWheels.getSelectedItemPosition() == 2) {
                    backWheelType = "Pneumatic";
                    backOtherLayout.setVisibility(View.GONE);

                } else {
                    backWheelType = "";
                    backOtherLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        orientationType = "Tank";

        orientation = findViewById(R.id.orientation);
        orientationOtherType = findViewById(R.id.orientationOther);
        orientationOtherLayout = findViewById(R.id.orientationOtherLayout);

        orientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (orientation.getSelectedItemId() == 0) {
                    orientationType = "Tank";
                    orientationOtherLayout.setVisibility(View.GONE);

                } else if (orientation.getSelectedItemPosition() == 1) {
                    orientationType = "Swerve";
                    orientationOtherLayout.setVisibility(View.GONE);

                } else if (orientation.getSelectedItemPosition() == 2) {
                    orientationType = "Mecanum";
                    orientationOtherLayout.setVisibility(View.GONE);

                } else if (orientation.getSelectedItemPosition() == 3) {
                    orientationType = "Omni";
                    orientationOtherLayout.setVisibility(View.GONE);

                } else if (orientation.getSelectedItemPosition() == 4) {
                    orientationType = "Butterfly";
                    orientationOtherLayout.setVisibility(View.GONE);

                } else {
                    orientationType = "";
                    orientationOtherLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        autonLeftC = findViewById(R.id.posL);
        autonMiddleC = findViewById(R.id.posM);
        autonRightC = findViewById(R.id.posR);

        startingPos = "::";

        aAutoline = findViewById(R.id.aAutoline);
        aSwitch = findViewById(R.id.aSwitch);
        aScale = findViewById(R.id.aScale);
        aSwitchScale = findViewById(R.id.aSwitchScale);
        aScaleSwitch = findViewById(R.id.aScaleSwitch);

        otherAuton = findViewById(R.id.otherAuton);

        autonPaths = "";

        autoDelay = findViewById(R.id.autoDelay);
        aDelay = 0;

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
        if (cExchange.isChecked()) {
            oExchange = 1;
        } else {
            oExchange = 0;
        }
    }

    public void setCSwitch(View v) {
        if (cSwitch.isChecked()) {
            oSwitch = 1;
        } else {
            oSwitch = 0;
        }
    }

    public void setCScale(View v) {
        if (cScale.isChecked()) {
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
        if (cims > 0) {
            cims -= 1;
        }
        cimDisplay.setText(String.format(Locale.ENGLISH, "%d", cims));
    }

    public void setAddCIM(View v) {
        cims += 1;
        cimDisplay.setText(String.format(Locale.ENGLISH, "%d", cims));
    }

    public void setSubtractMiniCIM(View v) {
        if (miniCIMs > 0) {
            miniCIMs -= 1;
        }
        miniCIMDisplay.setText(String.format(Locale.ENGLISH, "%d", miniCIMs));
    }

    public void setAddMiniCIM(View v) {
        miniCIMs += 1;
        miniCIMDisplay.setText(String.format(Locale.ENGLISH, "%d", miniCIMs));
    }


    public void setAutoDelay(View v) {
        if (autoDelay.isChecked()) {
            aDelay = 1;
        } else {
            aDelay = 0;
        }
    }

    public void setTakePic(View v) {
        count += 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            path = Environment.getExternalStorageDirectory();
            dir = new File(path.getAbsolutePath() + "/FRCMATCHDATA/Photos/" + teamNum.getText().toString());
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();


            file = new File(dir, teamNum.getText().toString() + "(" + count + ").jpg");

            file.setWritable(true);
            file.setReadable(true);
            file.setExecutable(true);


            file.createNewFile();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

            startActivityForResult(takePictureIntent, 1777);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setSubmitData(View v) {


        submit.setTitle("Submit Data");
        submit.setMessage("Are you sure you want to submit this data?");
        submit.setCancelable(true);

        submit.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        teamNumFinal = teamNum.getText().toString();

                        if (autonLeftC.isChecked()) {
                            startingPosFinal = "Left:";
                        } else {
                            startingPosFinal = "";
                        }

                        if (autonMiddleC.isChecked()) {
                            startingPosFinal = startingPosFinal + "Middle:";
                        }

                        if (autonRightC.isChecked()) {
                            startingPosFinal = startingPosFinal + "Right";
                        }

                        autoDelayFinal = String.format(Locale.ENGLISH, "%d", aDelay);

                        if (aAutoline.isChecked()) {
                            autonPathsFinal = "Autoline:";
                        } else {
                            autonPathsFinal = "";
                        }

                        if (aSwitch.isChecked()) {
                            autonPathsFinal = autonPathsFinal + "Switch:";
                        }

                        if (aScale.isChecked()) {
                            autonPathsFinal = autonPathsFinal + "Scale:";
                        }

                        if (aSwitchScale.isChecked()) {
                            autonPathsFinal = autonPathsFinal + "Switch then Scale:";
                        }

                        if (aScaleSwitch.isChecked()) {
                            autonPathsFinal = autonPathsFinal + "Scale then Switch:";
                        }
                        autonPathsFinal = autonPathsFinal + otherAuton.getText().toString();

                        groundIntakeFinal = String.format(Locale.ENGLISH, "%d", groundI);
                        exchangeIntakeFinal = String.format(Locale.ENGLISH, "%d", portalExchangeI);
                        portalIntakeFinal = String.format(Locale.ENGLISH, "%d", portalExchangeI);

                        outputExchangeFinal = String.format(Locale.ENGLISH, "%d", oExchange);
                        outputSwitchFinal = String.format(Locale.ENGLISH, "%d", oSwitch);
                        outputScaleFinal = String.format(Locale.ENGLISH, "%d", oScale);

                        typeOutputFinal = outputType;

                        rampFinal = String.format(Locale.ENGLISH, "%d", ramp);
                        barFinal = String.format(Locale.ENGLISH, "%d", bar);
                        climbFinal = String.format(Locale.ENGLISH, "%d", climb);
                        parkFinal = String.format(Locale.ENGLISH, "%d", park);

                        heightFinal = height.getText().toString();
                        weightFinal = weight.getText().toString();

                        cimsFinal = String.format(Locale.ENGLISH, "%d", cims);
                        miniCIMsFinal = String.format(Locale.ENGLISH, "%d", miniCIMs);


                        if (frontWheels.getSelectedItemId() == 0) {
                            frontWheelFinal = "Traction";

                        } else if (frontWheels.getSelectedItemPosition() == 1) {
                            frontWheelFinal = "Omni";

                        } else if (frontWheels.getSelectedItemPosition() == 2) {
                            frontWheelFinal = "Swerve Module";

                        } else if (frontWheels.getSelectedItemPosition() == 3) {
                            frontWheelFinal = "Mecanum";

                        } else if (frontWheels.getSelectedItemPosition() == 4) {
                            frontWheelFinal = "Pneumatic";

                        } else {
                            frontWheelFinal = frontOtherType.getText().toString();
                        }

                        if (wheelNum.getSelectedItemId() != 0) {
                            if (middleWheels.getSelectedItemId() == 0) {
                                middleWheelFinal = "Traction";

                            } else if (middleWheels.getSelectedItemPosition() == 1) {
                                middleWheelFinal = "Omni";

                            } else if (middleWheels.getSelectedItemPosition() == 2) {
                                middleWheelFinal = "Swerve Module";

                            } else if (middleWheels.getSelectedItemPosition() == 3) {
                                middleWheelFinal = "Mecanum";

                            } else if (middleWheels.getSelectedItemPosition() == 4) {
                                middleWheelFinal = "Pneumatic";

                            } else if (middleWheels.getSelectedItemPosition() == 5) {
                                middleWheelFinal = "Butterfly Switch";

                            } else {
                                middleWheelFinal = middleOtherType.getText().toString();
                            }
                        } else {
                            middleWheelFinal = "";
                        }

                        if (backWheels.getSelectedItemId() == 0) {
                            backWheelFinal = "Traction";

                        } else if (backWheels.getSelectedItemPosition() == 1) {
                            backWheelFinal = "Omni";

                        } else if (backWheels.getSelectedItemPosition() == 2) {
                            backWheelFinal = "Swerve Module";

                        } else if (backWheels.getSelectedItemPosition() == 3) {
                            backWheelFinal = "Mecanum";

                        } else if (backWheels.getSelectedItemPosition() == 4) {
                            backWheelFinal = "Pneumatic";

                        } else {
                            backWheelFinal = backOtherType.getText().toString();
                        }

                        if (wheelNum.getSelectedItemId() == 2) {
                            if (secondMiddleWheels.getSelectedItemId() == 0) {
                                secondMiddleWheelFinal = "Traction";

                            } else if (secondMiddleWheels.getSelectedItemPosition() == 1) {
                                secondMiddleWheelFinal = "Omni";

                            } else if (secondMiddleWheels.getSelectedItemPosition() == 2) {
                                secondMiddleWheelFinal = "Swerve Module";

                            } else if (secondMiddleWheels.getSelectedItemPosition() == 3) {
                                secondMiddleWheelFinal = "Mecanum";

                            } else if (secondMiddleWheels.getSelectedItemPosition() == 4) {
                                secondMiddleWheelFinal = "Pneumatic";

                            } else if (secondMiddleWheels.getSelectedItemPosition() == 5) {
                                secondMiddleWheelFinal = "Butterfly Switch";

                            } else {
                                secondMiddleWheelFinal = secondMiddleOtherType.getText().toString();
                            }
                        } else {
                            secondMiddleWheelFinal = "";
                        }

                        if (orientation.getSelectedItemId() == 0) {
                            configFinal = "Tank";

                        } else if (orientation.getSelectedItemPosition() == 1) {
                            configFinal = "Swerve";

                        } else if (orientation.getSelectedItemPosition() == 2) {
                            configFinal = "Mecanum";

                        } else if (orientation.getSelectedItemPosition() == 3) {
                            configFinal = "Omni";

                        } else if (orientation.getSelectedItemPosition() == 4) {
                            configFinal = "Butterfly";

                        } else {
                            configFinal = orientationOtherType.getText().toString();
                        }


                        SharedPreferences.Editor SPD = data.edit();

                        fullDataFinal = teamNumFinal + "," + startingPosFinal + "," +
                                autoDelayFinal + "," + autonPathsFinal + "," +
                                groundIntakeFinal + "," + exchangeIntakeFinal + "," + portalIntakeFinal + "," +
                                outputExchangeFinal + "," + outputSwitchFinal + "," + outputScaleFinal + "," + typeOutputFinal + "," +
                                rampFinal + "," + barFinal + "," + climbFinal + "," + parkFinal + "," +
                                heightFinal + "," + weightFinal + "," + cimsFinal + "," + miniCIMsFinal + "," +
                                frontWheelFinal + "," + middleWheelFinal + "," + backWheelFinal + "," + secondMiddleWheelFinal + "," + configFinal + "\n";

                        SPD.putString("allData", data.getString("allData", "") + fullDataFinal);
                        SPD.apply();

                        Intent next = new Intent(PitScouter.this, HomeScreen.class);
                        startActivity(next);
                    }
                });

        submit.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert = submit.create();
        alert.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {

            try {
                // Decode it for real
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = false;

                //imageFilePath image path which you pass with intent
                Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath(), bmpFactoryOptions);

                FileOutputStream fos = new FileOutputStream(file);

                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
/*
                fos.close();
                */
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }/* catch (IOException e) {
                e.printStackTrace();
            }*/
            MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, null);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backPressed.setTitle("Go Back");
            backPressed.setMessage("Are you sure you want to go back? All data on this form will be lost.");
            backPressed.setCancelable(true);

            backPressed.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent home = new Intent(PitScouter.this, HomeScreen.class);
                            startActivity(home);
                        }
                    });

            backPressed.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });

            AlertDialog alert = backPressed.create();
            alert.show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}