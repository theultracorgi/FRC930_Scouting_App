package com.example.pickmaster;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.woxthebox.draglistview.DragListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String listID;

    DragListView userListView;
    FloatingActionButton addItem;

    ArrayList <String> listContents;
    ArrayAdapter listAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listID = "FILL IN";

        addItem = findViewById(R.id.addTeam);
        userListView = findViewById(R.id.userListView);

        listContents = FileHelper.readData(this);
        listAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listContents);

    }

    public void addItem(final View v) {
        final EditText taskEditText = new EditText(MainActivity.this);
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Add a new task")
                .setMessage("What do you want to do next?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        Snackbar.make(v, task + " was added to " + listID, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();


    }



}


