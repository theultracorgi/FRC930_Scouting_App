package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;


public class SpinnerView extends LinearLayout {

    String label;
    CharSequence[] tempList;
    String [] list;
    TextView labelView;
    Spinner spinner;
    ArrayAdapter<String> adapter;


    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);



        list = new String[tempList.length];
        int i=0;
        for(CharSequence ch: tempList){
            list[i++] = ch.toString();
        }
        spinner = findViewById(R.id.spinner);

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list); //selected item will look like a spinner set from XML
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);


        labelView = findViewById(R.id.spinnerLabelView);
        labelView.setText(label);



    }

    public void setPos(int pos) {
        spinner.setSelection(pos);
    }


    public int getPos(){
        return spinner.getSelectedItemPosition();
    }

    public String getSelection() {
        return spinner.getSelectedItem().toString();
    }


    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.tempList = b.getTextArray(R.styleable.global_android_entries);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.spinner_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
