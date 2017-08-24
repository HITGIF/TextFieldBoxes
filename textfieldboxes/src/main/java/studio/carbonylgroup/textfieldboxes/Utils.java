package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;


public class Utils {

    public static int fetchAccentColor(Context context) {

        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}
