package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.content.res.ColorStateList;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Text Field Boxes
 * Created by CarbonylGroup on 2017/08/25
 */
public class TextFieldBoxes extends FrameLayout {

    public final int DEFAULT_TEXT_COLOR = getContext().getResources().getColor(R.color.per54black);
    public final int DEFAULT_ERROR_COLOR = getContext().getResources().getColor(R.color.A400red);
    public final int DEFAULT_DISABLED_TEXT_COLOR = getContext().getResources().getColor(R.color.per42black);
    public final int DEFAULT_BG_COLOR = getContext().getResources().getColor(R.color.per06black);

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
     * helperLabel text at the bottom.
     */
    protected String helperText;

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

    public View panel;
    public EditText editText;
    public ViewGroup editTextLayout;
    public FrameLayout bottomLine;
    public AppCompatTextView hintLabel;
    public AppCompatTextView helperLabel;
    public AppCompatTextView counterLabel;
    public AppCompatImageView iconImageView;
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
        handleAttributes(context, attrs);
        init();
    }

    public TextFieldBoxes(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        handleAttributes(context, attrs);
        init();
    }

    protected void init() {
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        addView(LayoutInflater.from(getContext()).inflate(R.layout.text_field_boxes_layout, this, false));

        panel = findViewById(R.id.text_field_boxes_panel);
        editTextLayout = findViewById(R.id.text_field_boxes_editTextLayout);
        editText = findViewById(R.id.text_field_boxes_editText);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setAlpha(0f);
        hintLabel = findViewById(R.id.text_field_boxes_label);
        hintLabel.setPivotX(0f);
        hintLabel.setPivotY(0f);
        labelColor = hintLabel.getCurrentTextColor();
        labelTopMargin = RelativeLayout.LayoutParams.class.cast(hintLabel.getLayoutParams()).topMargin;
        helperLabel = findViewById(R.id.text_field_boxes_helper);
        counterLabel = findViewById(R.id.text_field_boxes_counter);
        iconImageView = findViewById(R.id.text_field_boxes_imageView);
        bottomLine = findViewById(R.id.bg_bottom_line);

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

        setEnabled(enabled);
        setText(text);
        setHint(hint);
        setSingleLine(singleLine);
        setMaxLines(maxLines);
        setMaxCharacters(maxCharacters);
        setMinCharacters(minCharacters);
        setHelperText(helperText);
        setHelperTextColor(helperTextColor);
        setErrorColor(errorColor);
        setPrimaryColor(primaryColor);
        setPanelBackgroundColor(panelBackgroundColor);
        setIconSignifier(iconSignifierResourceId);
        setHasFocus(hasFocus);
        updateCounterText();
    }

    protected void handleAttributes(Context context, AttributeSet attrs) {

        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TextFieldBoxes);
            text = styledAttrs.getString(R.styleable.TextFieldBoxes_text) == null ? "" : styledAttrs.getString(R.styleable.TextFieldBoxes_text);
            hint = styledAttrs.getString(R.styleable.TextFieldBoxes_hint) == null ? "" : styledAttrs.getString(R.styleable.TextFieldBoxes_hint);
            singleLine = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_singleLine, false);
            maxLines = styledAttrs.getInt(R.styleable.TextFieldBoxes_maxLines, Integer.MAX_VALUE);
            maxCharacters = styledAttrs.getInt(R.styleable.TextFieldBoxes_maxCharacters, 0);
            minCharacters = styledAttrs.getInt(R.styleable.TextFieldBoxes_minCharacters, 0);
            helperText = styledAttrs.getString(R.styleable.TextFieldBoxes_helperText) == null ? "" : styledAttrs.getString(R.styleable.TextFieldBoxes_helperText);
            helperTextColor = styledAttrs.getInt(R.styleable.TextFieldBoxes_helperTextColor, DEFAULT_TEXT_COLOR);
            errorColor = styledAttrs.getInt(R.styleable.TextFieldBoxes_errorColor, DEFAULT_ERROR_COLOR);
            primaryColor = styledAttrs.getColor(R.styleable.TextFieldBoxes_primaryColor, Utils.fetchPrimaryColor(getContext()));
            panelBackgroundColor = styledAttrs.getColor(R.styleable.TextFieldBoxes_panelBackgroundColor, DEFAULT_BG_COLOR);
            enabled = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_enabled, true);
            iconSignifierResourceId = styledAttrs.getResourceId(R.styleable.TextFieldBoxes_iconSignifier, 0);
            hasFocus = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_hasFocus, false);
            styledAttrs.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * lower the hint hint Label when there is no text at losing focus
     */
    protected void deactivate() {

        if (editText.getText().toString().equals("")) {

            ViewCompat.animate(hintLabel)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1)
                    .translationY(0)
                    .setDuration(ANIMATION_DURATION);

            editText.setVisibility(View.GONE);

            if (editText.hasFocus()) {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                editText.clearFocus();
            }
        }
        activated = false;
    }

    /**
     * raise the hint hint Label when gaining focus
     *
     * @param animated whether to animate the hint hintLabel or not
     */
    protected void activate(boolean animated) {

        editText.setVisibility(View.VISIBLE);

        if (animated) {

            if (editText.getText().toString().equals("") && !isActivated()) {

                editText.setAlpha(0f);
                hintLabel.setScaleX(1f);
                hintLabel.setScaleY(1f);
                hintLabel.setTranslationY(0);
            }

            ViewCompat.animate(editText)
                    .alpha(1f)
                    .setDuration(ANIMATION_DURATION);

            ViewCompat.animate(hintLabel)
                    .scaleX(0.75f)
                    .scaleY(0.75f)
                    .translationY(-labelTopMargin + getContext().getResources().getDimensionPixelOffset(R.dimen.text_field_boxes_margin_top))
                    .setDuration(ANIMATION_DURATION);

        } else {

            editText.setAlpha(1f);
            hintLabel.setScaleX(0.75f);
            hintLabel.setScaleY(0.75f);
            hintLabel.setTranslationY(-labelTopMargin + getContext().getResources().getDimensionPixelOffset(R.dimen.text_field_boxes_margin_top));
        }
        activated = true;
    }

    /**
     * set the color of the hint Label, EditText cursor, icon signifier and the underline
     *
     * @param colorRes color resource
     */
    protected void setHighlightColor(int colorRes) {

        hintLabel.setTextColor(colorRes);
        Utils.setCursorDrawableColor(editText, colorRes);
        iconImageView.setColorFilter(colorRes);
        if (colorRes == DEFAULT_TEXT_COLOR) iconImageView.setAlpha(0.54f);
        else iconImageView.setAlpha(1f);
        bottomLine.setBackgroundColor(colorRes);
    }

    /**
     * check if the character count meets the upper or lower limits,
     * <p>
     * if exceeds limit, setCounterError()
     * otherwise removeCounterError()
     * <p>
     * NOTE: SPACE AND LINE FEED WILL NOT COUNT
     */
    protected void updateCounterText() {

        /* Don't Count Space & Line Feed */
        int length = getText().replaceAll(" ", "").replaceAll("\n", "").length();
        String lengthStr = Integer.toString(length) + " / ";

        if (this.maxCharacters > 0) {
            if (this.minCharacters > 0) {
                /* MAX & MIN */
                counterLabel.setText(lengthStr + Integer.toString(this.minCharacters) + "-" + Integer.toString(this.maxCharacters));
                if (length < this.minCharacters || length > maxCharacters) setCounterError();
                else removeCounterError();

            } else {
                /* MAX ONLY */
                counterLabel.setText(lengthStr + Integer.toString(this.maxCharacters));
                if (length > maxCharacters) setCounterError();
                else removeCounterError();
            }
        } else {
            if (this.minCharacters > 0) {
                /* MIN ONLY */
                counterLabel.setText(lengthStr + Integer.toString(this.minCharacters) + "+");
                if (length < minCharacters) setCounterError();
                else removeCounterError();

            } else counterLabel.setText("");
        }
    }

    /**
     * set highlight color and counter Label text color to error color
     */
    protected void setCounterError() {

        this.onError = true;
        setHighlightColor(errorColor);
        counterLabel.setTextColor(this.errorColor);
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
        counterLabel.setTextColor(this.DEFAULT_TEXT_COLOR);
    }

    public void setEnabled(boolean _enabled) {

        this.enabled = _enabled;
        if (enabled) {
            editText.setEnabled(true);
            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
            helperLabel.setVisibility(View.VISIBLE);
            counterLabel.setVisibility(View.VISIBLE);
            bottomLine.setVisibility(View.VISIBLE);
            panel.setEnabled(true);
            iconImageView.setEnabled(true);
            iconImageView.setClickable(true);
            setHighlightColor(DEFAULT_TEXT_COLOR);
            updateCounterText();

        } else {
            removeError();
            setHasFocus(false);
            editText.setEnabled(false);
            editText.setFocusableInTouchMode(false);
            editText.setFocusable(false);
            iconImageView.setClickable(false);
            iconImageView.setEnabled(false);
            hintLabel.setTextColor(DEFAULT_DISABLED_TEXT_COLOR);
            iconImageView.setColorFilter(DEFAULT_DISABLED_TEXT_COLOR);
            helperLabel.setVisibility(View.INVISIBLE);
            counterLabel.setVisibility(View.INVISIBLE);
            bottomLine.setVisibility(View.INVISIBLE);
            panel.setEnabled(false);
        }
    }

    /**
     * set highlight color and helperLabel Label text color to error color
     * set helperLabel Label text to error message
     *
     * @param _errorText error message
     */
    public void setError(String _errorText) {

        if (enabled) {
            onError = true;
            setHighlightColor(errorColor);
            helperLabel.setTextColor(this.errorColor);
            helperLabel.setText(_errorText);
        }
    }

    /**
     * set highlight to primary color if having focus,
     * otherwise set to DEFAULT_TEXT_COLOR
     * set helperLabel Label text color to DEFAULT_TEXT_COLOR
     * <p>
     * NOTE: WILL BE CALLED WHEN THE EDITTEXT CHANGES
     */
    public void removeError() {

        onError = false;
        if (this.hasFocus) setHighlightColor(this.primaryColor);
        else setHighlightColor(this.DEFAULT_TEXT_COLOR);
        helperLabel.setTextColor(this.helperTextColor);
        helperLabel.setText(helperText);
    }

    /**
     * set EditText text, raise the hint hintLabel if there is something
     *
     * @param _text new text
     */
    public void setText(String _text) {

        if (_text != null) {
            this.text = _text;
            editText.setText(this.text);
            if (!_text.equals("")) activate(false);
        }
    }

    public void setHint(String _hint) {

        this.hint = _hint;
        hintLabel.setText(hint);
    }

    /**
     * set if the EditText is single-lined, that scrolls horizontally
     *
     * @param _singleLine whether is single-lined
     */
    public void setSingleLine(boolean _singleLine) {

        this.singleLine = _singleLine;
        editText.setSingleLine(singleLine);
    }

    /**
     * set the max line number limit, can be removed by calling removeMaxLines()
     * can scroll vertically if exceeds
     *
     * @param _maxLines max line number limit
     */
    public void setMaxLines(int _maxLines) {

        this.maxLines = _maxLines;
        editText.setMaxLines(maxLines);
    }

    /**
     * remove the max line number limit by setting it to Integer.MAX_VALUE
     */
    public void removeMaxLines() {

        this.maxLines = Integer.MAX_VALUE;
        editText.setMaxLines(maxLines);
    }

    public void setMaxCharacters(int _maxCharacters) {
        this.maxCharacters = _maxCharacters;
    }

    /**
     * remove the max character count limit by setting it to 0
     */
    public void removeMaxCharacters() {
        this.maxCharacters = 0;
    }

    public void setMinCharacters(int _minCharacters) {
        this.minCharacters = _minCharacters;
    }

    /**
     * remove the min character count limit by setting it to 0
     */
    public void removeMinCharacters() {
        this.minCharacters = 0;
    }

    public void setHelperText(String _helperText) {

        this.helperText = _helperText;
        helperLabel.setText(helperText);
    }

    public void setHelperTextColor(int _colorRes) {

        this.helperTextColor = _colorRes;
        helperLabel.setTextColor(this.helperTextColor);
    }

    public void setErrorColor(int _colorRes) {
        this.errorColor = _colorRes;
    }

    public void setPrimaryColor(int _colorRes) {

        this.primaryColor = _colorRes;
        if (hasFocus) setHighlightColor(primaryColor);
    }

    public void setPanelBackgroundColor(int _colorRes) {

        this.panelBackgroundColor = _colorRes;
        ((GradientDrawable) ((LayerDrawable) panel.getBackground()).findDrawableByLayerId(R.id.bg_cover)).setColor(panelBackgroundColor);
    }

    public void setIconSignifier(int resourceID) {

        iconSignifierResourceId = resourceID;
        if (iconSignifierResourceId != 0) {
            iconImageView.setImageResource(iconSignifierResourceId);
            iconImageView.setVisibility(View.VISIBLE);
        } else removeIconSignifier();
    }

    /**
     * remove the icon by setting the visibility of the image view to View.GONE
     */
    public void removeIconSignifier() {

        iconSignifierResourceId = 0;
        iconImageView.setVisibility(View.GONE);
    }

    /**
     * set if the EditText is having focus
     *
     * @param _hasFocus gain focus if true, lose if false
     */
    public void setHasFocus(boolean _hasFocus) {

        this.hasFocus = _hasFocus;
        if (_hasFocus) {
            activate(false);
            editText.requestFocus();
            /* if there's an error, keep the error color */
            if (!onError && enabled) setHighlightColor(primaryColor);

        } else {
            deactivate();
            /* if there's an error, keep the error color */
            if (!onError && enabled) setHighlightColor(DEFAULT_TEXT_COLOR);
        }
    }

    public boolean isActivated() {
        return this.activated;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public EditText getEditText() {
        return this.editText;
    }

    public String getText() {
        return this.text;
    }

    public String getHint() {
        return this.hint;
    }

    public boolean getSingleLine() {
        return this.singleLine;
    }

    public int getMaxLines() {
        return this.maxLines;
    }

    public int getMaxCharacters() {
        return this.maxCharacters;
    }

    public int getMinCharacters() {
        return this.minCharacters;
    }

    public String getHelperText() {
        return this.helperText;
    }

    public String getCounterText() {
        return counterLabel.getText().toString();
    }

    public int getHelperTextColor() {
        return this.helperTextColor;
    }

    public int getErrorColor() {
        return this.errorColor;
    }

    public int getPrimaryColor() {
        return this.primaryColor;
    }

    public int getPanelBackgroundColor() {
        return this.panelBackgroundColor;
    }

    public int getIconSignifierResourceId() {
        return this.iconSignifierResourceId;
    }

    public boolean getHasFocus() {
        return this.hasFocus;
    }
}
