package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.view.ViewCompat;
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
import android.widget.TextView;

import java.lang.reflect.Field;


public class TextFieldBoxes extends FrameLayout {

    public View card;
    public TextView label;
    public EditText editText;
    public ViewGroup editTextLayout;
    public AppCompatTextView helper;
    public AppCompatTextView counter;
    protected InputMethodManager inputMethodManager;

    protected int labelColor = -1;
    protected int labelTopMargin = -1;
    protected boolean activated = false;
    protected boolean onError = false;
    protected int ANIMATION_DURATION = 100;
    protected int DEFAULT_TEXT_COLOR = getContext().getResources().getColor(R.color.default_text);
    protected int DEFAULT_ERROR_COLOR = getContext().getResources().getColor(R.color.default_error);

    /**
     * Attr
     */
    protected String text = "";
    protected String hint = "";
    protected String helperText = "";
    protected boolean singleLine = false;
    protected int maxLines = Integer.MAX_VALUE;
    protected int maxCharacters = -1;
    protected int minCharacters = 0;
    protected int helperTextColor = DEFAULT_TEXT_COLOR;
    protected int errorColor = DEFAULT_ERROR_COLOR;
    protected int accentColor = Utils.fetchAccentColor(getContext());
    protected int imageDrawableId = -1;
    protected boolean hasFocus = false;

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

        card = findViewById(R.id.text_field_boxes_card);
        editTextLayout = findViewById(R.id.text_field_boxes_editTextLayout);
        editText = findViewById(R.id.text_field_boxes_editText);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setAlpha(0f);
        label = findViewById(R.id.text_field_boxes_label);
        label.setPivotX(0f);
        label.setPivotY(0f);
        labelColor = label.getCurrentTextColor();
        labelTopMargin = RelativeLayout.LayoutParams.class.cast(label.getLayoutParams()).topMargin;
        helper = findViewById(R.id.text_field_boxes_helper);
        counter = findViewById(R.id.text_field_boxes_counter);

        card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activated) {
                    activate(true);
                    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
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
                removeError();
                updateCounterText();
            }
        });

        setText(text);
        setHint(hint);
        setSingleLine(singleLine);
        setMaxLines(maxLines);
        setMaxCharacters(maxCharacters);
        setMinCharacters(minCharacters);
        setHelperText(helperText);
        setHelperTextColor(helperTextColor);
        setErrorColor(errorColor);
        setAccentColor(accentColor);
        setHasFocus(hasFocus);
        updateCounterText();
    }

    protected void handleAttributes(Context context, AttributeSet attrs) {

        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TextFieldBoxes);
            text = styledAttrs.getString(R.styleable.TextFieldBoxes_text);
            hint = styledAttrs.getString(R.styleable.TextFieldBoxes_hint);
            singleLine = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_singleLine, false);
            maxLines = styledAttrs.getInt(R.styleable.TextFieldBoxes_maxLines, Integer.MAX_VALUE);
            maxCharacters = styledAttrs.getInt(R.styleable.TextFieldBoxes_maxCharacters, -1);
            minCharacters = styledAttrs.getInt(R.styleable.TextFieldBoxes_minCharacters, 0);
            helperText = styledAttrs.getString(R.styleable.TextFieldBoxes_helperText);
            helperTextColor = styledAttrs.getInt(R.styleable.TextFieldBoxes_helperTextColor, DEFAULT_TEXT_COLOR);
            errorColor = styledAttrs.getInt(R.styleable.TextFieldBoxes_errorColor, DEFAULT_ERROR_COLOR);
            accentColor = styledAttrs.getColor(R.styleable.TextFieldBoxes_accentColor, Utils.fetchAccentColor(getContext()));
            imageDrawableId = styledAttrs.getResourceId(R.styleable.TextFieldBoxes_image, -1);
            hasFocus = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_hasFocus, false);
            styledAttrs.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void deactivate() {

        if (editText.getText().toString().equals("")) {

            ViewCompat.animate(label)
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

    protected void activate(boolean animated) {

        editText.setVisibility(View.VISIBLE);
        editText.requestFocus();

        if (animated) {

            if (editText.getText().toString().equals("")) {

                editText.setAlpha(0f);
                label.setScaleX(1f);
                label.setScaleY(1f);
                label.setTranslationY(0);
            }

            ViewCompat.animate(editText)
                    .alpha(1f)
                    .setDuration(ANIMATION_DURATION);

            ViewCompat.animate(label)
                    .scaleX(0.75f)
                    .scaleY(0.75f)
                    .translationY(-labelTopMargin + getContext().getResources().getDimensionPixelOffset(R.dimen.text_field_boxes_margin_top))
                    .setDuration(ANIMATION_DURATION);
        } else {

            editText.setAlpha(1f);
            label.setScaleX(0.75f);
            label.setScaleY(0.75f);
            label.setTranslationY(-labelTopMargin + getContext().getResources().getDimensionPixelOffset(R.dimen.text_field_boxes_margin_top));
        }
        activated = true;
    }

    protected void setBGColor(int colorID) {

        label.setTextColor(colorID);
        Utils.setCursorDrawableColor(editText, colorID);
        ((GradientDrawable) ((LayerDrawable) card.getBackground()).findDrawableByLayerId(R.id.bg_bottom_line)).setColor(colorID);
    }

    protected void updateCounterText() {

        int length = getText().toString().replaceAll(" ", "").length();
        String lengthStr = Integer.toString(length) + " / ";

        if (this.maxCharacters != -1) {
            if (this.minCharacters != 0) {
                /* MAX & MIN */
                counter.setText(lengthStr + Integer.toString(this.minCharacters) + "-" + Integer.toString(this.maxCharacters));
                if (length < this.minCharacters || length > maxCharacters) {
                    setCounterError();
                } else removeCounterError();
            } else {
                /* MAX ONLY */
                counter.setText(lengthStr + Integer.toString(this.maxCharacters));
                if (length > maxCharacters) {
                    setCounterError();
                } else removeCounterError();
            }
        } else {
            if (this.minCharacters != 0) {
                /* MIN ONLY */
                counter.setText(lengthStr + Integer.toString(this.minCharacters) + "+");
                if (length < minCharacters) {
                    setCounterError();
                } else removeCounterError();
            } else {
                counter.setText("");
            }
        }
    }

    protected void setCounterError() {

        onError = true;
        setBGColor(errorColor);
        counter.setTextColor(this.errorColor);
    }

    protected void removeCounterError() {

        onError = false;
        setBGColor(accentColor);
        counter.setTextColor(this.DEFAULT_TEXT_COLOR);
    }

    public void setError(String _errorText) {

        onError = true;
        setBGColor(errorColor);
        helper.setTextColor(this.errorColor);
        helper.setText(_errorText);
    }

    public void removeError() {

        onError = false;
        setBGColor(errorColor);
        helper.setTextColor(this.helperTextColor);
        helper.setText(helperText);
    }

    public void setText(String _text) {

        if (text != null && !_text.equals("")) {
            this.text = _text;
            editText.setText(text);
            activate(false);
        }
    }

    public Editable getText() {
        return editText.getText();
    }

    public void setHint(String _hint) {

        this.hint = _hint;
        label.setText(hint);
    }

    public String getHint() {
        return this.hint;
    }

    public void setSingleLine(boolean _singleLine) {

        this.singleLine = _singleLine;
        editText.setSingleLine(singleLine);
    }

    public boolean getSingleLine() {
        return this.singleLine;
    }

    public void setMaxLines(int _maxLine) {

        this.maxLines = _maxLine;
        editText.setMaxLines(maxLines);
    }

    public void removeMaxLines() {

        this.maxLines = Integer.MAX_VALUE;
        editText.setMaxLines(maxLines);
    }

    public int getMaxLines() {
        return this.maxLines;
    }

    public void setMaxCharacters(int _maxCharacters) {
        this.maxCharacters = _maxCharacters;
    }

    public void removeMaxCharacters() {
        this.maxCharacters = -1;
    }

    public int getMaxCharacters() {
        return this.maxCharacters;
    }

    public void setMinCharacters(int _minCharacters) {
        this.minCharacters = _minCharacters;
    }

    public void removeMinCharacters() {
        this.minCharacters = 0;
    }

    public int getMinCharacters() {
        return this.minCharacters;
    }

    public void setHelperText(String _text) {

        if (!_text.equals("")) {
            this.helperText = _text;
            helper.setText(helperText);
        }
    }

    public String getHelperText() {
        return this.helperText;
    }

    public String getCounterText() {
        return counter.getText().toString();
    }

    public void setHelperTextColor(int _color) {

        if (_color != -1) {
            this.helperTextColor = _color;
            helper.setTextColor(this.helperTextColor);
        }
    }

    public int getHelperTextColor() {
        return this.helperTextColor;
    }

    public void setErrorColor(int _color) {

        if (_color != -1) {
            this.errorColor = _color;
        }
    }

    public int getErrorColor() {
        return this.errorColor;
    }

    public void setAccentColor(int _color) {

        if (_color != -1) {
            this.accentColor = _color;
            if (hasFocus) {
                setBGColor(accentColor);
            }
        }
    }

    public int getAccentColor() {
        return this.accentColor;
    }

    public void setImage(int _imageID) {
        this.imageDrawableId = _imageID;
    }

    public int getImage() {
        return this.imageDrawableId;
    }

    public void setHasFocus(boolean hasFocus) {

        this.hasFocus = hasFocus;

        if (hasFocus) {
            activate(false);
            if (!onError) setBGColor(accentColor);

        } else {
            deactivate();
            if (!onError) setBGColor(getResources().getColor(R.color.bg_deactivate));
        }
    }

    public boolean isActivated() {
        return activated;
    }
}
