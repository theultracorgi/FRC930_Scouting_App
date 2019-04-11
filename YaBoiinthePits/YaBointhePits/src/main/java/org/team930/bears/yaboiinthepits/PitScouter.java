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
    EditText teamNum;
    int count;

    Bitmap bmp;
    FileOutputStream fos;
    BitmapFactory.Options bmpFactoryOptions;
    File path, dir, file;
    String dataKey;

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


        teamNum = findViewById(R.id.teamNum);


    }

    public void setTakePic(View v) {
        count += 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            path = Environment.getExternalStorageDirectory();
            dir = new File(path.getAbsolutePath() + "/FRCMATCHDATA/Photos/" + teamNum.getText().toString());
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();


            file = new File(dir, teamNum.getText().toString() + " (" + count + ").jpg");

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {

            try {
                // Decode it for real
                bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = false;

                //imageFilePath image path which you pass with intent
                bmp = BitmapFactory.decodeFile(file.getAbsolutePath(), bmpFactoryOptions);

                fos = new FileOutputStream(file);

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