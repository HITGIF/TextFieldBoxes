package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TextFieldBoxes extends FrameLayout {

    public View card;
    public TextView label;
    public EditText editText;
    public ViewGroup editTextLayout;
    protected InputMethodManager inputMethodManager;

    protected int labelColor = -1;
    protected int labelTopMargin = -1;
    protected boolean activated = false;
    protected int ANIMATION_DURATION = 100;

    /**
     * Attr
     */
    protected String hint = "";
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

        card = findViewById(R.id.mtf_card);
        editTextLayout = findViewById(R.id.text_field_boxes_editTextLayout);
        editText = findViewById(R.id.text_field_boxes_editText);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setAlpha(0f);
        label = findViewById(R.id.text_field_boxes_label);
        label.setText(hint);
        label.setPivotX(0f);
        label.setPivotY(0f);
        labelColor = label.getCurrentTextColor();
        labelTopMargin = RelativeLayout.LayoutParams.class.cast(label.getLayoutParams()).topMargin;

        card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activated) {
                    activate();
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

        setHasFocus(hasFocus);
    }

    protected void handleAttributes(Context context, AttributeSet attrs) {

        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TextFieldBoxes);
            imageDrawableId = styledAttrs.getResourceId(R.styleable.TextFieldBoxes_image, -1);
            hasFocus = styledAttrs.getBoolean(R.styleable.TextFieldBoxes_hasFocus, false);
            hint = styledAttrs.getString(R.styleable.TextFieldBoxes_hint);
            styledAttrs.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void deactivate() {

        activated = false;
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
    }

    protected void activate() {

        activated = true;
        editText.setVisibility(View.VISIBLE);
        editText.requestFocus();

        ViewCompat.animate(editText)
                .alpha(1f)
                .setDuration(ANIMATION_DURATION);

        ViewCompat.animate(label)
                .scaleX(0.75f)
                .scaleY(0.75f)
                .translationY(-labelTopMargin + getContext().getResources().getDimensionPixelOffset(R.dimen.text_field_boxes_margin_top))
                .setDuration(ANIMATION_DURATION);
    }

    public void setHasFocus(boolean hasFocus) {

        this.hasFocus = hasFocus;

        if (hasFocus) {
            activate();
            label.setTextColor(Utils.fetchAccentColor(getContext()));
            card.setBackgroundResource(R.drawable.bg_focus);

        } else {
            deactivate();
            label.setTextColor(labelColor);
            card.setBackgroundResource(R.drawable.bg_unfocus);
        }
    }

    public void setImage(int imageID) {
//        this.hint = hint;
    }

    public void setHint(String hint) {

        this.hint = hint;
        label.setText(hint);
    }

    public String getHint() {
        return hint;
    }

    public Editable getText() {
        return editText.getText();
    }

    public boolean isActivated() {
        return activated;
    }
}
