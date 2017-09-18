package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;


/**
 * Text Field Boxes
 * Created by CarbonylGroup on 2017/09/01
 */
@SuppressWarnings("unused")
public class ExtendedEditText extends TextInputEditText {

    public int DEFAULT_TEXT_COLOR;

    /**
     * prefix Label text at the start.
     */
    protected String prefix;

    /**
     * suffix Label text at the end.
     */
    protected String suffix;

    /**
     * Prefix text color. DEFAULT_TEXT_COLOR by default.
     */
    protected int prefixTextColor;

    /**
     * Suffix text color. DEFAULT_TEXT_COLOR by default.
     */
    protected int suffixTextColor;

    public ExtendedEditText(Context context) {

        this(context, null);
        initDefaultColor();
    }

    public ExtendedEditText(Context context, AttributeSet attrs) {

        this(context, attrs, android.R.attr.editTextStyle);
        initDefaultColor();
        handleAttributes(context, attrs);
    }

    public ExtendedEditText(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        initDefaultColor();
        handleAttributes(context, attrs);
    }

    protected void initDefaultColor() {

        Resources.Theme theme = getContext().getTheme();
        TypedArray themeArray;

        /* Get Default Text Color From Theme */
        themeArray = theme.obtainStyledAttributes(new int[]{android.R.attr.textColorTertiary});
        DEFAULT_TEXT_COLOR = themeArray.getColor(0, 0);

        themeArray.recycle();
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        /* Texts */
        setPrefix(this.prefix);
        setSuffix(this.suffix);

        /* Colors */
        setPrefixTextColor(this.prefixTextColor);
        setSuffixTextColor(this.suffixTextColor);
    }


    protected void handleAttributes(Context context, AttributeSet attrs) {
        try {

            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ExtendedEditText);

            /* Texts */
            this.prefix = styledAttrs.getString(R.styleable.ExtendedEditText_prefix)
                    == null ? "" : styledAttrs.getString(R.styleable.ExtendedEditText_prefix);
            this.suffix = styledAttrs.getString(R.styleable.ExtendedEditText_suffix)
                    == null ? "" : styledAttrs.getString(R.styleable.ExtendedEditText_suffix);

            /* Colors */
            this.prefixTextColor = styledAttrs
                    .getInt(R.styleable.ExtendedEditText_prefixTextColor, DEFAULT_TEXT_COLOR);
            this.suffixTextColor = styledAttrs
                    .getInt(R.styleable.ExtendedEditText_suffixTextColor, DEFAULT_TEXT_COLOR);
            styledAttrs.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class TextDrawable extends Drawable {

        private TextDrawable() {

            setBounds(0, 0, (int) getPaint().measureText(prefix) + 2, (int) getTextSize());
            setPadding(0, 0, (int) getPaint().measureText(suffix) - 2, 0);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {

            final int lineBase = getLineBounds(0, null);
            final int lineBottom = getLineBounds(getLineCount() - 1, null);
            final float endX = getWidth() - getPaint().measureText(suffix) - 2;

            Paint paint = getPaint();
            paint.setColor(prefixTextColor);
            canvas.drawText(prefix, 0, canvas.getClipBounds().top + lineBase, paint);
            paint.setColor(suffixTextColor);
            canvas.drawText(suffix, endX, canvas.getClipBounds().top + lineBottom, paint);
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

    public void setPrefix(String prefix) {

        this.prefix = prefix;
        this.setCompoundDrawables(new TextDrawable(), null, null, null);
    }

    public void setSuffix(String suffix) {

        this.suffix = suffix;
        this.setCompoundDrawables(new TextDrawable(), null, null, null);
    }

    public void setPrefixTextColor(int color) {
        this.prefixTextColor = color;
    }

    public void setSuffixTextColor(int color) {
        this.suffixTextColor = color;
    }
}
