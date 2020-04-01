package org.team930.bears.wears;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class QRCreator extends AppCompatActivity {

    LinearLayout border;
    ImageView qrCode;

    boolean showToast;
    String otherPreferences, matchDataPreferences, sendableData, numStoredMatches;

    SharedPreferences otherSettings, matchData;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcreator);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        showToast = true;

        numStoredMatches = getString(R.string.numStoredMatches);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        border = findViewById(R.id.border);
        qrCode = findViewById(R.id.qrCode);

        if (otherSettings.getInt(numStoredMatches, 0) <= 0) {
            qrCode.setBackground(getResources().getDrawable(R.drawable.bad));

        } else {
            qrCode.setBackground(getResources().getDrawable(R.drawable.good));
        }
    }

    public void setGenQRCode(View v) {
            sendableData = matchData.getString(getString(R.string.sendableData), "Bad data something wrong");
        if (otherSettings.getInt(numStoredMatches, 0) > 0 || sendableData != null) {

            QRCodeWriter writer = new QRCodeWriter();
            try {
                //QR Code generation {
                BitMatrix bitMatrix = writer.encode(sendableData, BarcodeFormat.QR_CODE, 736, 736);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                    }
                }
                qrCode.setBackgroundDrawable(getResources().getDrawable(R.color.colorWhite));
                qrCode.setImageBitmap(bmp);
            } catch (WriterException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "WriterException", Toast.LENGTH_LONG).show();
            }

            border.setVisibility(View.VISIBLE);

            SharedPreferences.Editor SPOS = otherSettings.edit();
            SPOS.putBoolean("deleteData", true);
            SPOS.apply();
            //QR Code Generation

        } else if (showToast) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            showToast = false;

        } else {

        }


    }


}
