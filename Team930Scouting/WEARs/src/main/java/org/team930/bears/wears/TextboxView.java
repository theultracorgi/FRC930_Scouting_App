package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;

public class TextboxView extends LinearLayout {

    String label, hint;

    int location;
    TextView sideLabelView, topLabelView;
    EditText textbox;



    public TextboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);

        if (location == 0) {
            topLabelView = findViewById(R.id.textboxTopLabelView);
            textbox = findViewById(R.id.textboxTop);
            topLabelView.setText(label);
        } else if (location == 1) {
            sideLabelView = findViewById(R.id.textboxSideLabelView);
            textbox = findViewById(R.id.textboxSide);
            sideLabelView.setText(label);
        } else {
            textbox = findViewById(R.id.textboxNone);
            textbox.setPadding(8,8,8,8);
        }

        textbox.setFilters(new InputFilter[]{setFilter()});
        textbox.clearFocus();
        textbox.setHint(hint);
    }


    public String getText() {
        return textbox.getText().toString();
    }

    private InputFilter setFilter() {
        final String blockCharacterSet = ",";

        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };
        return filter;
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.textbox,
                0, 0);
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);


        this.label = b.getString(R.styleable.global_label);
        this.hint = a.getString(R.styleable.textbox_hint);
        this.location = a.getInt(R.styleable.textbox_label_location, 0);

        if (location == 0) {
            inflater.inflate(R.layout.top_textbox_view, this);
        } else if (location == 1) {
            inflater.inflate(R.layout.side_textbox_view, this);
        } else {
            inflater.inflate(R.layout.none_textbox_view, this);


        }

    }

    protected void onFinishInflate() {
        super.onFinishInflate();




    }

}
