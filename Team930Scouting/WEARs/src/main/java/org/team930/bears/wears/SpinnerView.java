package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class SpinnerView extends LinearLayout {

    String label;
    CharSequence[] tempList;
    String [] list;
    TextView labelView;
    EditText other;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    boolean otherEnabled;


    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);



        if(otherEnabled){
            list = new String[tempList.length  +1];
        } else {
            list = new String[tempList.length];
        }


        for(int i = 0; i< tempList.length; i++){
            list[i] = String.valueOf(tempList[i]);
        }

        if (otherEnabled) {
            list[list.length - 1] = "Other";
        }

        spinner = findViewById(R.id.spinner);

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list); //selected item will look like a spinner set from XML
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        other = findViewById(R.id.spinnerOther);
        other.setVisibility(GONE);

        labelView = findViewById(R.id.spinnerLabelView);
        labelView.setText(label);


        if (otherEnabled) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    if(spinner.getSelectedItem().toString().equals("Other")){
                        other.setVisibility(VISIBLE);
                    } else {
                        other.setVisibility(GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        }


    }

    public void setPos(int pos) {
        spinner.setSelection(pos);
    }


    public int getPos(){
        return spinner.getSelectedItemPosition();
    }

    public String getSelection() {

        if (spinner.getSelectedItem().toString().contains("Other")) {
            return other.getText().toString();
        } else {
            return spinner.getSelectedItem().toString();
        }
    }



    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.tempList = b.getTextArray(R.styleable.global_android_entries);
        this.otherEnabled = b.getBoolean(R.styleable.global_other_enabled, false);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.spinner_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
