package studio.carbonylgroup.textfieldboxes;

import android.view.View;

/**
 * Define the interactions between a {@link TextFieldBoxes} and its child view.
 */
public interface EmbeddedTextField {
    void setDefaultOnFocusChangeListener(View.OnFocusChangeListener l);
}
