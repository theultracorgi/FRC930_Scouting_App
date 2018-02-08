package org.team930.bears.watching_extremely_awesome_robots;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

public class QRCreator extends AppCompatActivity {

    Button genQRCode;
    LinearLayout border;
    ImageView qrCode;

    boolean showToast;
    String otherPreferences, matchDataPreferences, sendableData, numStoredMatches;

    SharedPreferences otherSettings, matchData;
    ContextThemeWrapper ctw;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcreator);

        showToast = true;
        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        builder = new AlertDialog.Builder(ctw);

        numStoredMatches = getString(R.string.numStoredMatches);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        genQRCode = findViewById(R.id.genQRCode);
        border = findViewById(R.id.border);
        qrCode = findViewById(R.id.qrCode);

        sendableData = matchData.getString("match1", "") + matchData.getString("match2", "") +
                matchData.getString("match3", "") + matchData.getString("match4", "") +
                matchData.getString("match5", "") + matchData.getString("match6", "");


        if (otherSettings.getInt(numStoredMatches, 0) == 0) {
            qrCode.setBackgroundResource(R.drawable.bad);

        } else {
            qrCode.setBackgroundResource(R.drawable.good);
        }
    }

    public void setGenQRCode(View v) {

        if (otherSettings.getInt(numStoredMatches, 0) != 0) {

            QRCodeWriter writer = new QRCodeWriter();
            try {
                //QR Code generation {
                BitMatrix bitMatrix = writer.encode(sendableData, BarcodeFormat.QR_CODE, 736, 736);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                qrCode.setImageBitmap(bmp);
            } catch (WriterException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "WriterException", Toast.LENGTH_LONG).show();
            }
            border.setVisibility(View.VISIBLE);

            SharedPreferences.Editor SPOS = otherSettings.edit();
            SPOS.putBoolean("deleteData", true);
            SPOS.commit();
            //QR Code Generation}

        } else if (showToast) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            showToast = false;

        } else {

        }

    }


}
