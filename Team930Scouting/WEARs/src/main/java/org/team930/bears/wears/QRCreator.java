package org.team930.bears.wears;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

@SuppressWarnings("ALL")
public class QRCreator extends AppCompatActivity {

    LinearLayout border;
    ImageView qrCode;
    TextView indicator;

    int disColor;
    boolean showToast, disFirstQR, oneQR;
    String otherPreferences, matchDataPreferences, sendableData, numStoredMatches;

    SharedPreferences otherSettings, matchData;
    ContextThemeWrapper ctw;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcreator);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        showToast = true;
        ctw = new ContextThemeWrapper(this, THEME_HOLO_LIGHT);
        builder = new AlertDialog.Builder(ctw);

        numStoredMatches = getString(R.string.numStoredMatches);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        matchDataPreferences = getString(R.string.matchDataPreferences);
        matchData = getSharedPreferences(matchDataPreferences, 0);

        oneQR = true;

        border = findViewById(R.id.border);
        qrCode = findViewById(R.id.qrCode);
        indicator = findViewById(R.id.qrcodey);

        disFirstQR = false;
        if(otherSettings.getInt(numStoredMatches, 6) > 1) {
            disFirstQR = true;

        }
        if (otherSettings.getInt(numStoredMatches, 0) <= 0) {
            qrCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.bad));

        } else {
            qrCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.good));
        }
    }

    public void setGenQRCode(View v) {

        if(otherSettings.getInt(numStoredMatches,3) <= 3) {
            sendableData = matchData.getString("firstQR", "0");
            disColor = BLACK;
        } else {
            if (disFirstQR == true) {
                sendableData = matchData.getString("firstQR", "0");
                disColor = Color.BLUE;

            } else {
                sendableData = matchData.getString("secondQR", "0");
                disColor = Color.RED;
            }
        }
        if (otherSettings.getInt(numStoredMatches, 0) > 0) {

            QRCodeWriter writer = new QRCodeWriter();
            try {
                //QR Code generation {

                Map<EncodeHintType, Object> hints;
                    hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
                    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

                BitMatrix bitMatrix = writer.encode(sendableData, BarcodeFormat.QR_CODE, 736, 736, hints);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? disColor : WHITE);
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
            if(otherSettings.getInt(numStoredMatches,3) <= 3) {

            } else {
                disFirstQR = !disFirstQR;
                    if (disFirstQR) {
                        indicator.setText("QR Code 2");

                    } else {
                        indicator.setText("QR Code 1");
                }
            }


        } else if (showToast) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            showToast = false;

        } else {

        }


    }


}
