package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class AutonTeleop extends AppCompatActivity {
    SeekBar sBar;
    TextView sBarView, uCounterView;
    Integer numUnItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonteleop);
        numUnItems = 0;
        main();

    }

    public void main(){
        sBar = (SeekBar)findViewById(R.id.numItems);
        sBarView = (TextView)findViewById(R.id.displaySeek);
        uCounterView = (TextView) findViewById(R.id.displayUnNum);
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sBarView.setText(Integer.toString(progress));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void unlimitedCounterAdd(View v){

        numUnItems = numUnItems + 1;

        uCounterView.setText(Integer.toString(numUnItems));
    }

    public void unlimitedCounterSubtract(View v){

        numUnItems = numUnItems - 1;

        uCounterView.setText(Integer.toString(numUnItems));
    }

    public void goToTeleop(View v){
        Intent nextScreen = new Intent(AutonTeleop.this,PostMatch.class);
        startActivity(nextScreen);


    }

}

