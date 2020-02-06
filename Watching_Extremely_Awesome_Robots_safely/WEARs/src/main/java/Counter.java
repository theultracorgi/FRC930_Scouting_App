import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.team930.bears.wears.R;

public class Counter extends View {
    String title;
    int count;
    int increment;

    TextView display;
    Button add, sub;


    public Counter(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Counter,
                0, 0);
        this.title = a.getString(R.styleable.Counter_title);
        this.count = a.getInt(R.styleable.Counter_base, 0);
        this.increment = a.getInt(R.styleable.Counter_increment, 1);


        display = findViewById(R.id.add);
    }


}

