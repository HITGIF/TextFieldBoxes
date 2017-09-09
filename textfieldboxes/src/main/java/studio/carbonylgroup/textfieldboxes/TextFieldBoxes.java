package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


/**
 * Text Field Boxes
 * Created by CarbonylGroup on 2017/08/25
 */
public class TextFieldBoxes extends FrameLayout {

    /**
     * all the default colors to be used on light or dark themes.
     */
    public int DEFAULT_ERROR_COLOR;
    public int DEFAULT_PRIMARY_COLOR;
    public int DEFAULT_TEXT_COLOR;
    public int DEFAULT_DISABLED_TEXT_COLOR;
    public int DEFAULT_BG_COLOR;

    /**
     * whether the text field is enabled. True by default.
     */
    protected boolean enabled;

    /**
     * editText text.
     */
    protected String text;

    /**
     * hint text at the top.
     */
    protected String hint;

    /**
     * helper Label text at the bottom.
     */
    protected String helperText;

    /**
     * prefix Label text at the start.
     */
    protected String prefix;

    /**
     * suffix Label text at the end.
     */
    protected String suffix;

    /**
     * whether the EditText is single-lined. False by default.
     */
    protected boolean singleLine;

    /**
     * the max line number limit. Integer.MAX_VALUE by default
     */
    protected int maxLines;

    /**
     * max characters count limit. 0 means no limit. 0 by default.
     */
    protected int maxCharacters;

    /**
     * min characters count limit. 0 means no limit. 0 by default.
     */
    protected int minCharacters;

    /**
     * the text color for the helperLabel text. DEFAULT_TEXT_COLOR by default.
     */
    protected int helperTextColor;

    /**
     * the text color for when something is wrong (e.g. exceeding max characters, setError()).
     * DEFAULT_ERROR_COLOR by default.
     */
    protected int errorColor;

    /**
     * the color for the underline and the hint text. Current theme primary color by default.
     */
    protected int primaryColor;

    /**
     * Prefix text color. DEFAULT_TEXT_COLOR by default.
     */
    protected int prefixTextColor;

    /**
     * Suffix text color. DEFAULT_TEXT_COLOR by default.
     */
    protected int suffixTextColor;

    /**
     * the color for panel at the back. DEFAULT_BG_COLOR by default.
     */
    protected int panelBackgroundColor;

    /**
     * the resource ID of the icon signifier. 0 by default.
     */
    protected int iconSignifierResourceId;

    /**
     * whether the EditText is having the focus. False by default.
     */
    protected boolean hasFocus;

    protected View panel;
    protected FrameLayout bottomLine;
    protected ViewGroup editTextLayout;
    protected ExtendedEditText editText;
    protected AppCompatTextView hintLabel;
    protected AppCompatTextView helperLabel;
    protected AppCompatTextView counterLabel;
    protected AppCompatImageView iconImageView;
    protected InputMethodManager inputMethodManager;
    protected int labelColor = -1;
    protected int labelTopMargin = -1;
    protected int ANIMATION_DURATION = 100;
    protected boolean onError = false;
    protected boolean activated = false;

    public TextFieldBoxes(Context context) {

        super(context);
        init();
    }

    public TextFieldBoxes(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
        handleAttributes(context, attrs);
    }

    public TextFieldBoxes(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        init();
        handleAttributes(context, attrs);
    }

    protected void init() {

        initDefaultColor();
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    protected void initDefaultColor() {

        Resources.Theme theme = getContext().getTheme();
        TypedArray themeArray;

        /* Get Default Error Color From Theme */
        DEFAULT_ERROR_COLOR = getContext().getResources().getColor(R.color.A400red);

        /* Get Default Background Color From Theme */
        themeArray = theme.obtainStyledAttributes(new int[]{android.R.attr.colorForeground});
        DEFAULT_BG_COLOR = Utils.adjustAlpha(themeArray.getColor(0, 0), 0.06f);

        /* Get Default Primary Color From Theme */
        themeArray = theme.obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        if (Utils.isLight(DEFAULT_BG_COLOR))
            DEFAULT_PRIMARY_COLOR = Utils.lighter(themeArray.getColor(0, 0), 0.2f);
        else DEFAULT_PRIMARY_COLOR = themeArray.getColor(0, 0);

        /* Get Default Text Color From Theme */
        themeArray = theme.obtainStyledAttributes(new int[]{android.R.attr.textColorTertiary});
        DEFAULT_TEXT_COLOR = themeArray.getColor(0, 0);

        /* Get Default Disabled Text Color From Theme */
        themeArray = theme.obtainStyledAttributes(new int[]{android.R.attr.disabledAlpha});
        float disabledAlpha = themeArray.getFloat(0, 0);
        themeArray = theme.obtainStyledAttributes(new int[]{android.R.attr.textColorTertiary});
        DEFAULT_DISABLED_TEXT_COLOR = Utils.adjustAlpha(themeArray.getColor(0, 0), disabledAlpha);

        themeArray.recycle();
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        addView(LayoutInflater.from(getContext()).inflate(R.layout.text_field_boxes_layout, this, false));

        this.panel = findViewById(R.id.text_field_boxes_panel);
        this.editText = findViewById(R.id.text_field_boxes_editText);
        this.editText.setBackgroundColor(Color.TRANSPARENT);
        this.editText.setAlpha(0f);
        this.hintLabel = findViewById(R.id.text_field_boxes_label);
        this.hintLabel.setPivotX(0f);
        this.hintLabel.setPivotY(0f);
        this.bottomLine = findViewById(R.id.bg_bottom_line);
        this.labelColor = this.hintLabel.getCurrentTextColor();
        this.helperLabel = findViewById(R.id.text_field_boxes_helper);
        this.counterLabel = findViewById(R.id.text_field_boxes_counter);
        this.iconImageView = findViewById(R.id.text_field_boxes_imageView);
        this.editTextLayout = findViewById(R.id.text_field_boxes_editTextLayout);
        this.labelTopMargin = RelativeLayout.LayoutParams.class
                .cast(this.hintLabel.getLayoutParams()).topMargin;

        panel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isActivated()) activate(true);
                setHasFocus(true);
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        iconImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isActivated()) activate(true);
                setHasFocus(true);
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) setHasFocus(true);
                else setHasFocus(false);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                text = editText.getText().toString();
                removeError();
                updateCounterText();
            }
        });

        /* Texts */
        setText(this.text);
        setHint(this.hint);
        setHelperText(this.helperText);
        setPrefix(this.prefix);
        setSuffix(this.suffix);

        /* Colors */
        setHelperTextColor(this.helperTextColor);
        setErrorColor(this.errorColor);
        setPrimaryColor(this.primaryColor);
        setPrefixTextColor(this.prefixTextColor);
        setSuffixTextColor(this.suffixTextColor);
        setPanelBackgroundColor(this.panelBackgroundColor);

        /* Characters counter */
        setMaxCharacters(this.maxCharacters);
        setMinCharacters(this.minCharacters);

        /* Others */
        setEnabled(this.enabled);
        setSingleLine(this.singleLine);
        setMaxLines(this.maxLines);
        setIconSignifier(this.iconSignifierResourceId);
        setHasFocus(this.hasFocus);
        updateCounterText();
    }

    protected void handleAttributes(Context context, AttributeSet attrs) {

        try {

            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TextFieldBoxes);

            /* Texts */
            this.text = styledAttrs.getString(R.styleable.TextFieldBoxes_text)
                    == null ? "" : styledAttrs.getString(R.styleable.TextFieldBoxes_text);
            this.hint = styledAttrs.getString(R.styleable.TextFieldBoxes_hint)
                    == null ? "" : styledAttrs.getString(R.styleable.TextFieldBoxes_hint);
            this.helperText = styledAttrs.getString(R.styleable.TextFieldBoxes_helperText)
                    == null ? "" : styledAttrs.getString(R.styleable.TextFieldBoxes_helperText);
            this.prefix = styledAttrs.getString(R.styleable.TextFieldBoxes_prefix)
                    == null ? "" : styledAttrs.getString(R.styleable.TextFieldBoxes_prefix);
            this.suffix = styledAttrs.getString(R.styleable.TextFieldBoxes_suffix)
                    == null ? "" : styledAttrs.getString(R.styleable.TextFieldBoxes_suffix);

            /* Colors */
            this.helperTextColor = styledAttrs
                    .getInt(R.styleable.TextFieldBoxes_helperTextColor, DEFAULT_TEXT_COLOR);
            this.errorColor = styledAttrs
                    .getInt(R.styleable.TextFieldBoxes_errorColor, DEFAULT_ERROR_COLOR);
            this.primaryColor = styledAttrs
                    .getColor(R.styleable.TextFieldBoxes_primaryColor, DEFAULT_PRIMARY_COLOR);
            this.prefixTextColor = styledAttrs
                    .getInt(R.styleable.TextFieldBoxes_prefixTextColor, DEFAULT_TEXT_COLOR);
            this.suffixTextColor = styledAttrs
                    .getInt(R.styleable.TextFieldBoxes_suffixTextColor, DEFAULT_TEXT_COLOR);
            this.panelBackgroundColor = styledAttrs
                    .getColor(R.styleable.TextFieldBoxes_panelBackgroundColor, DEFAULT_BG_COLOR);

            /* Characters counter */
            this.maxCharacters = styledAttrs.getInt(R.styleable.TextFieldBoxes_maxCharacters, 0);
            this.minCharacters = styledAttrs.getInt(R.styleable.TextFieldBoxes_minCharacters, 0);

            /* Others */
            this.enabled = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_enabled, true);
            this.singleLine = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_singleLine, false);
            this.maxLines = styledAttrs.getInt(R.styleable.TextFieldBoxes_maxLines, Integer.MAX_VALUE);
            this.iconSignifierResourceId = styledAttrs.
                    getResourceId(R.styleable.TextFieldBoxes_iconSignifier, 0);
            this.hasFocus = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_hasFocus, false);

            styledAttrs.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * lower the hint hint Label when there is no text at losing focus
     */
    protected void deactivate() {

        if (this.editText.getText().toString().equals("")) {

            ViewCompat.animate(hintLabel)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1)
                    .translationY(0)
                    .setDuration(ANIMATION_DURATION);

            this.editText.setVisibility(View.GONE);

            if (this.editText.hasFocus()) {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                this.editText.clearFocus();
            }
        }
        this.activated = false;
    }

    /**
     * raise the hint hint Label when gaining focus
     *
     * @param animated whether to animate the hint hintLabel or not
     */
    protected void activate(boolean animated) {

        this.editText.setVisibility(View.VISIBLE);

        if (animated) {

            if (this.editText.getText().toString().equals("") && !isActivated()) {

                this.editText.setAlpha(0f);
                this.hintLabel.setScaleX(1f);
                this.hintLabel.setScaleY(1f);
                this.hintLabel.setTranslationY(0);
            }

            ViewCompat.animate(this.editText)
                    .alpha(1f)
                    .setDuration(ANIMATION_DURATION);

            ViewCompat.animate(this.hintLabel)
                    .scaleX(0.75f)
                    .scaleY(0.75f)
                    .translationY(-labelTopMargin + getContext().getResources().getDimensionPixelOffset(R.dimen.text_field_boxes_margin_top))
                    .setDuration(ANIMATION_DURATION);

        } else {

            this.editText.setAlpha(1f);
            this.hintLabel.setScaleX(0.75f);
            this.hintLabel.setScaleY(0.75f);
            this.hintLabel.setTranslationY(-labelTopMargin + getContext().getResources().getDimensionPixelOffset(R.dimen.text_field_boxes_margin_top));
        }
        activated = true;
    }

    /**
     * set the color of the hint Label, EditText cursor, icon signifier and the underline
     *
     * @param colorRes color resource
     */
    protected void setHighlightColor(int colorRes) {

        this.hintLabel.setTextColor(colorRes);
        Utils.setCursorDrawableColor(this.editText, colorRes);

        this.iconImageView.setColorFilter(colorRes);
        if (colorRes == DEFAULT_TEXT_COLOR) this.iconImageView.setAlpha(0.54f);
        else this.iconImageView.setAlpha(1f);
        if (colorRes == DEFAULT_DISABLED_TEXT_COLOR) this.iconImageView.setAlpha(0.35f);

        this.bottomLine.setBackgroundColor(colorRes);
    }

    /**
     * check if the character count meets the upper or lower limits,
     * <p>
     * if exceeds limit, setCounterError()
     * otherwise removeCounterError()
     * <p>
     * <i>NOTE: SPACE AND LINE FEED WILL NOT COUNT</i>
     */
    protected void updateCounterText() {

        /* Don't Count Space & Line Feed */
        int length = getText().replaceAll(" ", "").replaceAll("\n", "").length();
        String lengthStr = Integer.toString(length) + " / ";

        if (this.maxCharacters > 0) {
            if (this.minCharacters > 0) {
                /* MAX & MIN */
                this.counterLabel.setText(lengthStr + Integer.toString(this.minCharacters)
                        + "-" + Integer.toString(this.maxCharacters));
                if (length < this.minCharacters || length > this.maxCharacters) {
                    setCounterError();
                }
                else {
                    removeCounterError();
                }

            } else {
                /* MAX ONLY */
                this.counterLabel.setText(lengthStr + Integer.toString(this.maxCharacters) + "");
                if (length > this.maxCharacters) setCounterError();
                else removeCounterError();
            }
        } else {
            if (this.minCharacters > 0) {
                /* MIN ONLY */
                this.counterLabel.setText(lengthStr + Integer.toString(this.minCharacters) + "+");
                if (length < this.minCharacters) setCounterError();
                else removeCounterError();

            } else this.counterLabel.setText("");
        }
    }

    /**
     * set highlight color and counter Label text color to error color
     */
    protected void setCounterError() {

        this.onError = true;
        setHighlightColor(this.errorColor);
        this.counterLabel.setTextColor(this.errorColor);
    }

    /**
     * set highlight color to primary color if having focus,
     * otherwise set to DEFAULT_TEXT_COLOR
     * set counterLabel Label text color to DEFAULT_TEXT_COLOR
     */
    protected void removeCounterError() {

        this.onError = false;
        if (this.hasFocus) setHighlightColor(this.primaryColor);
        else setHighlightColor(this.DEFAULT_TEXT_COLOR);
        this.counterLabel.setTextColor(this.DEFAULT_TEXT_COLOR);
    }

    /**
     * set highlight color and helperLabel Label text color to error color
     * set helperLabel Label text to error message
     *
     * @param errorText error message
     */
    public void setError(String errorText) {

        if (this.enabled) {
            this.onError = true;
            setHighlightColor(this.errorColor);
            this.helperLabel.setTextColor(this.errorColor);
            this.helperLabel.setText(errorText);
        }
    }

    /**
     * set highlight to primary color if having focus,
     * otherwise set to DEFAULT_TEXT_COLOR
     * set helperLabel Label text color to DEFAULT_TEXT_COLOR
     * <p>
     * <i>NOTE: WILL BE CALLED WHEN THE EDITTEXT CHANGES</i>
     */
    public void removeError() {

        this.onError = false;
        if (this.hasFocus) setHighlightColor(this.primaryColor);
        else setHighlightColor(this.DEFAULT_TEXT_COLOR);
        this.helperLabel.setTextColor(this.helperTextColor);
        this.helperLabel.setText(this.helperText);
    }

    /* Text Setters */
    /**
     * set EditText text, raise the hint hintLabel if there is something
     *
     * @param text new text
     */
    public void setText(String text) {

        if (text != null) {
            this.text = text;
            editText.setText(this.text);
            if (!text.equals("")) activate(false);
        }
    }

    public void setHint(String hint) {

        this.hint = hint;
        this.hintLabel.setText(this.hint);
    }

    public void setHelperText(String helperText) {

        this.helperText = helperText;
        this.helperLabel.setText(this.helperText);
    }

    public void setPrefix(String prefix) {

        this.prefix = prefix;
        this.editText.setPrefix(this.prefix);
    }

    public void setSuffix(String suffix) {

        this.suffix = suffix;
        this.editText.setSuffix(this.suffix);
    }

    /* Color Setters */
    public void setHelperTextColor(int colorRes) {

        this.helperTextColor = colorRes;
        this.helperLabel.setTextColor(this.helperTextColor);
    }

    public void setErrorColor(int colorRes) {
        this.errorColor = colorRes;
    }

    /**
     * <i>NOTE: the color will automatically be made lighter by 20% if it's on the DARK theme</i>
     */
    public void setPrimaryColor(int colorRes) {

        this.primaryColor = colorRes;
        if (this.hasFocus) setHighlightColor(this.primaryColor);
    }

    public void setPrefixTextColor(int colorRes) {

        this.prefixTextColor = colorRes;
        this.editText.setPrefixTextColor(this.prefixTextColor);
    }

    public void setSuffixTextColor(int colorRes) {

        this.suffixTextColor = colorRes;
        this.editText.setSuffixTextColor(this.suffixTextColor);
    }

    public void setPanelBackgroundColor(int colorRes) {

        this.panelBackgroundColor = colorRes;
        ((GradientDrawable) ((LayerDrawable) this.panel.getBackground())
                .findDrawableByLayerId(R.id.bg_cover)).setColor(panelBackgroundColor);
    }

    /* Characters Counter Setters */
    public void setMaxCharacters(int maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    /**
     * remove the max character count limit by setting it to 0
     */
    public void removeMaxCharacters() {
        this.maxCharacters = 0;
    }

    public void setMinCharacters(int minCharacters) {
        this.minCharacters = minCharacters;
    }

    /**
     * remove the min character count limit by setting it to 0
     */
    public void removeMinCharacters() {
        this.minCharacters = 0;
    }

    /* Other Setters */
    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
        if (this.enabled) {
            this.editText.setEnabled(true);
            this.editText.setFocusableInTouchMode(true);
            this.editText.setFocusable(true);
            this.helperLabel.setVisibility(View.VISIBLE);
            this.counterLabel.setVisibility(View.VISIBLE);
            this.bottomLine.setVisibility(View.VISIBLE);
            this.panel.setEnabled(true);
            this.iconImageView.setEnabled(true);
            this.iconImageView.setClickable(true);
            setHighlightColor(DEFAULT_TEXT_COLOR);
            updateCounterText();

        } else {
            removeError();
            setHasFocus(false);
            this.editText.setEnabled(false);
            this.editText.setFocusableInTouchMode(false);
            this.editText.setFocusable(false);
            this.iconImageView.setClickable(false);
            this.iconImageView.setEnabled(false);
            this.helperLabel.setVisibility(View.INVISIBLE);
            this.counterLabel.setVisibility(View.INVISIBLE);
            this.bottomLine.setVisibility(View.INVISIBLE);
            this.panel.setEnabled(false);
            setHighlightColor(DEFAULT_DISABLED_TEXT_COLOR);
        }
    }

    /**
     * set if the EditText is single-lined, that scrolls horizontally
     *
     * @param singleLine whether is single-lined
     */
    public void setSingleLine(boolean singleLine) {

        this.singleLine = singleLine;
        this.editText.setSingleLine(this.singleLine);
    }

    /**
     * set the max line number limit, can be removed by calling removeMaxLines()
     * can scroll vertically if exceeds
     *
     * @param maxLines max line number limit
     */
    public void setMaxLines(int maxLines) {

        this.maxLines = maxLines;
        this.editText.setMaxLines(this.maxLines);
    }

    /**
     * remove the max line number limit by setting it to Integer.MAX_VALUE
     */
    public void removeMaxLines() {

        this.maxLines = Integer.MAX_VALUE;
        this.editText.setMaxLines(this.maxLines);
    }

    public void setIconSignifier(int resourceID) {

        this.iconSignifierResourceId = resourceID;
        if (this.iconSignifierResourceId != 0) {
            this.iconImageView.setImageResource(this.iconSignifierResourceId);
            this.iconImageView.setVisibility(View.VISIBLE);
        } else removeIconSignifier();
    }

    /**
     * remove the icon by setting the visibility of the image view to View.GONE
     */
    public void removeIconSignifier() {

        this.iconSignifierResourceId = 0;
        this.iconImageView.setVisibility(View.GONE);
    }

    /**
     * set if the EditText is having focus
     *
     * @param hasFocus gain focus if true, lose if false
     */
    public void setHasFocus(boolean hasFocus) {

        this.hasFocus = hasFocus;
        if (this.hasFocus) {
            activate(false);
            this.editText.requestFocus();
            /* if there's an error, keep the error color */
            if (!this.onError && this.enabled) setHighlightColor(this.primaryColor);

        } else {
            deactivate();
            /* if there's an error, keep the error color */
            if (!this.onError && this.enabled) setHighlightColor(DEFAULT_TEXT_COLOR);
        }
    }

    /* Text Getters */
    public EditText getEditText() {
        return this.editText;
    }

    public String getText() {
        return this.text;
    }

    public String getHint() {
        return this.hint;
    }

    public String getHelperText() {
        return this.helperText;
    }

    public String getCounterText() {
        return counterLabel.getText().toString();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    /* Color Getters */
    public int getHelperTextColor() {
        return this.helperTextColor;
    }

    public int getErrorColor() {
        return this.errorColor;
    }

    public int getPrimaryColor() {
        return this.primaryColor;
    }

    public int getPrefixTextColor() {
        return this.prefixTextColor;
    }

    public int getSuffixTextColor() {
        return this.suffixTextColor;
    }

    public int getPanelBackgroundColor() {
        return this.panelBackgroundColor;
    }

    /* Characters Counter Getters */
    public int getMaxCharacters() {
        return this.maxCharacters;
    }

    public int getMinCharacters() {
        return this.minCharacters;
    }

    /* Other Getters */
    public boolean isActivated() {
        return this.activated;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean getSingleLine() {
        return this.singleLine;
    }

    public int getMaxLines() {
        return this.maxLines;
    }

    public int getIconSignifierResourceId() {
        return this.iconSignifierResourceId;
    }

    public boolean getHasFocus() {
        return this.hasFocus;
    }
}

