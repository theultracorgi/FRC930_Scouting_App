package org.team930.bears.watching_extremely_awesome_robots;

import android.content.Context;
import android.widget.Toast;

public final class ToasterOven {

    Context activity;



    ToasterOven(Context c) {
        activity = c;
    }

    public void showToastShort(Toast mToast, String message) { //"Toast toast" is declared in the class
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        mToast.show();
    }


    public void showToastLong(Toast mToast, String message) { //"Toast toast" is declared in the class
        if (mToast != null) {
            mToast.cancel();
        }
        mToast.makeText(activity, message, Toast.LENGTH_LONG).show();

    }
}
