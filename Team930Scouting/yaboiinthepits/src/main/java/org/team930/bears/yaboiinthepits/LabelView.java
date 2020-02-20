package org.team930.bears.yaboiinthepits;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class LabelView extends LinearLayout {

    String label;
    TextView labelView;
    boolean center;
    int stripeColor;
    ImageView stripe;

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);


        labelView = findViewById(R.id.labelLabelView);

        if(center) {
            labelView.setGravity(Gravity.CENTER);
        }
        if(stripeColor !=0) {
            labelView = findViewById(R.id.labelStripeLabelView);
        }

        labelView.setText(label);

        switch (stripeColor){

            case 1:
                stripe = findViewById(R.id.labelStripe);
                stripe.setColorFilter(ContextCompat.getColor(getContext(),R.color.textBlue));
                break;
            case 2:
                stripe = findViewById(R.id.labelStripe);
                stripe.setColorFilter(ContextCompat.getColor(getContext(),R.color.textRed));
                break;
            case 3:
                stripe = findViewById(R.id.labelStripe);
                stripe.setColorFilter(ContextCompat.getColor(getContext(),R.color.textPurple));
                break;
            case 4:
                stripe = findViewById(R.id.labelStripe);
                stripe.setColorFilter(ContextCompat.getColor(getContext(),R.color.textYellow));
                break;
            case 5:
                stripe = findViewById(R.id.labelStripe);
                stripe.setColorFilter(ContextCompat.getColor(getContext(),R.color.textGreen));
                break;

            default:
                break;
        }



    }





    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.label,
                0, 0);
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.center = a.getBoolean(R.styleable.label_center, false);
        this.stripeColor = a.getInt(R.styleable.label_color_text,0);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(stripeColor ==0) {
            inflater.inflate(R.layout.label_view, this);
        } else {
            inflater.inflate(R.layout.stripe_label_view, this);
        }

    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
