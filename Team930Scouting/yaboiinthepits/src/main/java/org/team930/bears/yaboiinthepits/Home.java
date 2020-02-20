package org.team930.bears.yaboiinthepits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.os.Environment.getExternalStorageDirectory;

public class Home extends AppCompatActivity {

    TextboxView teamNum, maxBalls, ballLimiter, outtakeHeight, ballsPerMatch, width, height, length, weight, climberWidth, clearance, maxAutoBalls, auto1, auto2, auto3, otherComments;
    CounterView numWheels, numOmnis, numMotors;
    SpinnerView motorType, drivetrain, endgame;
    SeekbarView topSpeed, bottomSpeed;
    ToggleView camera, turret, wideOuttake, visionTracking, manualWheel, balancingMech, tippedBar, autoDelay, initiationLine;
    CheckboxView intakeOuttake, shooterVariables, scoringGoals, shootingZones, wheelOfFortune, climbLocation, startPos;

    Button takePic, submitData;

    int count;

    Uri photo;

    String pathToFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        count = 0;

        teamNum = findViewById(R.id.teamNum);
        maxBalls = findViewById(R.id.ballLimit);
        ballLimiter = findViewById(R.id.ballLimitMech);
        outtakeHeight = findViewById(R.id.outtakeHeight);
        ballsPerMatch = findViewById(R.id.ballsPerMatch);
        width = findViewById(R.id.width);
        height = findViewById(R.id.height);
        length = findViewById(R.id.length);
        weight = findViewById(R.id.weight);
        climberWidth = findViewById(R.id.climberWidth);
        clearance = findViewById(R.id.groundClearance);
        maxAutoBalls = findViewById(R.id.maxAutoBalls);
        auto1 = findViewById(R.id.autoDes1);
        auto2 = findViewById(R.id.autoDes2);
        auto3 = findViewById(R.id.autoDes3);
        otherComments = findViewById(R.id.otherComments);


        numWheels = findViewById(R.id.numWheels);
        numOmnis = findViewById(R.id.numOmniWheels);
        numMotors = findViewById(R.id.numMotors);

        motorType = findViewById(R.id.drivetrainMotors);
        drivetrain = findViewById(R.id.drivetrainConfig);
        endgame = findViewById(R.id.endgameMech);

        topSpeed = findViewById(R.id.topSpeed);
        bottomSpeed = findViewById(R.id.bottomSpeed);

        camera = findViewById(R.id.camera);
        turret = findViewById(R.id.turret);
        wideOuttake = findViewById(R.id.wideOuttake);
        visionTracking = findViewById(R.id.visionTracking);
        manualWheel = findViewById(R.id.controlPanelCamera);
        balancingMech = findViewById(R.id.balancingMech);
        tippedBar = findViewById(R.id.tippedBar);
        autoDelay = findViewById(R.id.autoDelay);
        initiationLine = findViewById(R.id.crossLine);

        intakeOuttake = findViewById(R.id.intakeOuttake);
        shooterVariables = findViewById(R.id.shooterVariables);
        scoringGoals = findViewById(R.id.shootingGoals);
        shootingZones = findViewById(R.id.shootingZones);
        wheelOfFortune = findViewById(R.id.controlPanel);
        climbLocation = findViewById(R.id.climbPos);
        startPos = findViewById(R.id.autoStartPos);

        takePic = findViewById(R.id.takePicture);
        submitData = findViewById(R.id.submit);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = false;

                File photoFile = getOutputMediaFile();

                //imageFilePath image path which you pass with intent
                Bitmap bmp = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmpFactoryOptions);

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(photoFile);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MediaScannerConnection.scanFile(this, new String[]{photoFile.toString()}, null, null);
            }
        }
    }


    public void setTakePic(View view) {
        count += 1;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photo = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);
        startActivityForResult(intent, 100);

    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/FRCMATCHDATA/Photos/" + teamNum.getText());
        String name = teamNum.getText() + " (" + count + ").jpg";
        File photoFile = new File(mediaStorageDir, name);
        photoFile.setWritable(true);
        photoFile.setReadable(true);
        photoFile.setExecutable(true);
        try {
            photoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoFile;
    }


    long prevTime = 0;

    public void setSubmitData(View v) {

        long thisTime = java.util.Calendar.getInstance().getTimeInMillis();
        if (prevTime < thisTime) {
            if ((thisTime - prevTime) <= 1000) {//1 SEC
                String fullTeamData =

                        teamNum.getText() + "," + length.getText() + "," + width.getText() + "," + height.getText() + "," + weight.getText() + "," +
                                numWheels.getCount() + "," + numOmnis.getCount() + "," + numMotors.getCount() + "," + motorType.getSelection() + "," + drivetrain.getSelection() + "," +
                                bottomSpeed.getProgress() + "," + topSpeed.getProgress() + "," + camera.getState() + "," +
                                intakeOuttake.getCheckBoxCheckedArray()[0] + "," + intakeOuttake.getCheckBoxCheckedArray()[2] + "," + intakeOuttake.getCheckBoxCheckedArray()[1] + "," +
                                maxBalls.getText() + "," + ballLimiter.getText() + "," +
                                turret.getState() + "," + wideOuttake.getState() + "," + shooterVariables.getCheckBoxCheckedArray()[0] + "," + shooterVariables.getCheckBoxCheckedArray()[1] + "," + shooterVariables.getCheckBoxCheckedArray()[2] + "," +
                                visionTracking.getState() + "," + outtakeHeight.getText() + "," + ballsPerMatch.getText() + "," +
                                scoringGoals.getCheckBoxCheckedArray()[0] + "," + scoringGoals.getCheckBoxCheckedArray()[1] + "," + scoringGoals.getCheckBoxCheckedArray()[2] + "," +
                                shootingZones.getCheckBoxCheckedArray()[0] + "," + shootingZones.getCheckBoxCheckedArray()[1] + "," + shootingZones.getCheckBoxCheckedArray()[2] + "," + shootingZones.getCheckBoxCheckedArray()[3] + "," + shootingZones.getCheckBoxCheckedArray()[4] + "," +
                                wheelOfFortune.getCheckBoxCheckedArray()[0] + "," + wheelOfFortune.getCheckBoxCheckedArray()[1] + "," +
                                manualWheel.getState() + "," + endgame.getSelection() + "," + balancingMech.getState() + "," + tippedBar.getState() + "," + climberWidth.getText() + "," + clearance.getText() + "," +
                                startPos.getCheckBoxCheckedArray()[0] + "," + startPos.getCheckBoxCheckedArray()[1] + "," + startPos.getCheckBoxCheckedArray()[2] + "," +
                                autoDelay.getState() + "," + initiationLine.getState() + "," + maxAutoBalls.getText() + "," +
                                auto1.getText() + "," + auto2.getText() + "," + auto3.getText() + "," + otherComments.getText() + "\n";


                try {
                    File path = getExternalStorageDirectory();
                    File dir = new File(path.getAbsolutePath() + "/FRCMATCHDATA");
                    //noinspection ResultOfMethodCallIgnored
                    dir.mkdirs();
                    File file = new File(dir, "PitData.csv");

                    file.setWritable(true);
                    file.setReadable(true);
                    file.setExecutable(true);

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(file, true);
                    fos.write(fullTeamData.getBytes());
                    fos.close();

                    Toast.makeText(this, "Data Exported Successfully", Toast.LENGTH_SHORT).show();

                    MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, null);

                    teamNum.clearContents();
                    length.clearContents();
                    width.clearContents();
                    height.clearContents();
                    weight.clearContents();
                    numWheels.clearContents();
                    numOmnis.clearContents();
                    numMotors.clearContents();
                    motorType.clearContents();
                    drivetrain.clearContents();
                    bottomSpeed.clearContents();
                    topSpeed.clearContents();
                    camera.clearContents();
                    intakeOuttake.clearContents();
                    maxBalls.clearContents();
                    ballLimiter.clearContents();
                    turret.clearContents();
                    wideOuttake.clearContents();
                    shooterVariables.clearContents();
                    visionTracking.clearContents();
                    outtakeHeight.clearContents();
                    ballsPerMatch.clearContents();
                    scoringGoals.clearContents();
                    shootingZones.clearContents();
                    wheelOfFortune.clearContents();
                    manualWheel.clearContents();
                    endgame.clearContents();
                    balancingMech.clearContents();
                    tippedBar.clearContents();
                    climberWidth.clearContents();
                    clearance.clearContents();
                    startPos.clearContents();
                    autoDelay.clearContents();
                    initiationLine.clearContents();
                    maxAutoBalls.clearContents();
                    auto1.clearContents();
                    auto2.clearContents();
                    auto3.clearContents();
                    otherComments.clearContents();

                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "FileNotFound", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {

                    Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
                }
            } else {
                //first tap
                Toast.makeText(this, "Press Again To Submit Data", Toast.LENGTH_SHORT).show();
                prevTime = thisTime;
            }

        }

    }
}
