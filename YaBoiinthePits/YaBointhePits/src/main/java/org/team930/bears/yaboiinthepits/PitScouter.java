package org.team930.bears.yaboiinthepits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PitScouter extends AppCompatActivity {

    EditText weight, height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scouter);

        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
    }

    public void setPortalExchange(View v) {


    }

    public void setGround(View v) {


    }
}
