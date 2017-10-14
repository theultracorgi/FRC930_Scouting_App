package org.team930.bears.frc930_scouting_app_2017;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Root_Screen extends AppCompatActivity {
    TextView displayNum;
    Button addNum, subtractNum;
    int numBalls;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root__screen);
        numBalls = 0;
        init();

    }
    public void init(){
        addNum = (Button)findViewById(R.id.addNum);
        subtractNum = (Button)findViewById(R.id.subtractNum);
        displayNum = (TextView)findViewById(R.id.displayNum);
    }
    public void addNum(View v){
        numBalls = numBalls +1;
        displayNum.setText(Integer.toString(numBalls));
    }
}
