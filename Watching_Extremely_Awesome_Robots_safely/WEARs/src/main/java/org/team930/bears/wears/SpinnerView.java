package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SpinnerView extends LinearLayout {

    String label;
    CharSequence[] tempList;
    String [] list;
    TextView labelView;
    Spinner spinner;
    ArrayAdapter<String> adapter;


    public SpinnerView(Context context) {
        super(context);
        initializeViews(context);
    }

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);

        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.tempList = b.getTextArray(R.styleable.global_android_entries);

        list = new String[tempList.length];
        int i=0;
        for(CharSequence ch: tempList){
            list[i++] = ch.toString();
        }
        spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list); //selected item will look like a spinner set from XML
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);


        labelView = findViewById(R.id.spinnerLabelView);
        labelView.setText(label);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                                       int postion, long arg3) {
                // TODO Auto-generated method stub
                String  spinnerValue= parent.getItemAtPosition(postion).toString();

                Toast.makeText(getContext(),
                        "Selected item: " + spinnerValue,
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public int getPos(){
        return spinner.getSelectedItemPosition();
    }

    public String getSelection() {
        return spinner.getSelectedItem().toString();
    }


    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.spinner_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}