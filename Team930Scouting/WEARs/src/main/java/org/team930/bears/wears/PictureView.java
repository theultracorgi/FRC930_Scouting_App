package org.team930.bears.wears;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PictureView extends LinearLayout {

    String label;
    TextView labelView;
    Drawable source;
    ImageView image;

    public PictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);

        labelView = findViewById(R.id.imageLabelView);
        image = findViewById(R.id.imageImageView);

        image.setBackground(source);
        labelView.setText(label);


    }

    public void setImage(Drawable d) {
        image.setBackground(d);
    }




    private void initializeViews(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.image,
                0, 0);
        TypedArray b = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.global,
                0, 0);
        this.label = b.getString(R.styleable.global_label);
        this.source = a.getDrawable(R.styleable.image_source);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.image_view, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
