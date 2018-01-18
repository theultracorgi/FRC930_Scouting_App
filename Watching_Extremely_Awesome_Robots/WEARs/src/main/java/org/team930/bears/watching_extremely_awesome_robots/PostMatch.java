package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class PostMatch extends AppCompatActivity {
    ToggleButton disabled;
    Button submitData;
    EditText comments;
    Integer mDisabled;

    PublicResources PR = new PublicResources(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_match);

        mDisabled = 0;

        disabled = findViewById(R.id.disabled);
        comments = findViewById(R.id.comments);
        submitData = findViewById(R.id.submitData);
    }

    public void setDisabled(View v){

        if(disabled.isChecked()){
            mDisabled = 1;
        } else {
            mDisabled = 0;
        }
    }

    public void setSubmitData(View v) {

        PR.matchData[10] = mDisabled.toString();
        PR.matchData[11] = comments.getText().toString();

        Intent submitData = new Intent(this, Home_Screen.class);
        startActivity(submitData);
    }


}
