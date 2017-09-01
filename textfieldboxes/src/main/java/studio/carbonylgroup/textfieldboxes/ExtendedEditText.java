package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


/**
 * Text Field Boxes
 * Created by CarbonylGroup on 2017/09/01
 */
public class ExtendedEditText extends AppCompatEditText {

    protected String prefixText = "";
    protected String suffixText = "";
    protected ColorStateList prefixTextColor;
    protected ColorStateList suffixTextColor;

    public ExtendedEditText(Context context) {
        this(context, null);
    }

    public ExtendedEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ExtendedEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private class TextDrawable extends Drawable {

        private TextDrawable() {

            setBounds(0, 0, (int) getPaint().measureText(prefixText) + 2, (int) getTextSize());
            setPadding(0, 0, getPaddingRight() + (int) getPaint().measureText(suffixText) - 2, 0);
        }

        @Override
        public void draw(Canvas canvas) {

            final int lineBase = getLineBounds(0, null);
            final int lineBottom = getLineBounds(getLineCount() - 1, null);
            final float endX = getWidth() - getPaddingRight() +
                    (int) getPaint().measureText(suffixText) - 2 - getPaint().measureText(suffixText);

            Paint paint = getPaint();
            paint.setColor(prefixTextColor.getColorForState(getDrawableState(), 0));
            canvas.drawText(prefixText, 0, canvas.getClipBounds().top + lineBase, paint);
            paint.setColor(suffixTextColor.getColorForState(getDrawableState(), 0));
            canvas.drawText(suffixText, endX, canvas.getClipBounds().top + lineBottom, paint);
        }

        @Override
        public void setAlpha(int alpha) {/* Not supported */}

        @Override
        public void setColorFilter(ColorFilter colorFilter) {/* Not supported */}

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
    }

    public void setPrefix(String _prefix) {

        prefixText = _prefix;
        setCompoundDrawables(new TextDrawable(), null, null, null);
    }

    public void setSuffix(String _suffix) {

        suffixText = _suffix;
        setCompoundDrawables(new TextDrawable(), null, null, null);
    }

    public void setPrefixTextColor(int color) {
        prefixTextColor = ColorStateList.valueOf(color);
    }

    public void setPrefixTextColor(ColorStateList color) {
        prefixTextColor = color;
    }

    public void setSuffixTextColor(int color) {
        suffixTextColor = ColorStateList.valueOf(color);
    }

    public void setSuffixTextColor(ColorStateList color) {
        suffixTextColor = color;
    }
}
