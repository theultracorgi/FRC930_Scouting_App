package org.team930.bears.watching_extremely_awesome_robots;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

public class QRCreator extends AppCompatActivity {

    Button genQRCode, deleteData;
    String otherPreferences, numStoredMatches, news;
    TextView neatoDeleto;
    LinearLayout deleteBar, border;
    SharedPreferences otherSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcreator);

        numStoredMatches = getString(R.string.numStoredMatches);

        otherPreferences = getString(R.string.otherPreferences);

        otherSettings = getSharedPreferences(otherPreferences, 0);

        news = "JJJJJJ";

        genQRCode = findViewById(R.id.genQRCode);
        deleteData = findViewById(R.id.deleteData);

        deleteBar = findViewById(R.id.deleteBar);
        neatoDeleto = findViewById(R.id.neatoDeleto);
        border = findViewById(R.id.border);
    }

    public void setGenQRCode(View v){

        QRCodeWriter writer = new QRCodeWriter();
        try{
            BitMatrix bitMatrix = writer.encode(news, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x  = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.qrCode)).setImageBitmap(bmp);
        }
        catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Caught WriterException !!!", Toast.LENGTH_LONG).show();
        }

        deleteData.setVisibility(View.VISIBLE);
        neatoDeleto.setVisibility(View.VISIBLE);
        border.setVisibility(View.VISIBLE);

    }

    public void setDeleteData(View v){

        SharedPreferences.Editor SPOS = otherSettings.edit();

        SPOS.putInt(numStoredMatches,0);
        SPOS.commit();

    }
}
