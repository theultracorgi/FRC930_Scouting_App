package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextboxView extends LinearLayout {

    String label, hint;

    TextView sideLabelView, topLabelView;
    EditText textbox;


    public TextboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.textbox,
                0, 0);
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);

        sideLabelView = findViewById(R.id.textboxSideLabelView);
        topLabelView = findViewById(R.id.textboxTopLabelView);
        textbox = findViewById(R.id.textbox);

        textbox.setFilters(new InputFilter[]{setFilter()});

        this.label = b.getString(R.styleable.global_label);
        this.hint = a.getString(R.styleable.textbox_hint);

        setLabelLocation(a.getInt(R.styleable.textbox_label_location, 0));
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

    private void setLabelLocation(int location) {
        if (location == 0) {
            sideLabelView.setVisibility(GONE);
            topLabelView.setText(label);
        } else if (location == 1) {
            topLabelView.setVisibility(GONE);
            sideLabelView.setText(label);
        } else {
            topLabelView.setVisibility(GONE);
            sideLabelView.setVisibility(GONE);
            textbox.setPadding(8,8,8,8);
        }
    }


    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.textbox_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
