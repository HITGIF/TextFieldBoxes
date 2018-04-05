package studio.carbonylgroup.textfieldboxes;

import android.text.TextWatcher;

public interface SimpleTextChangedWatcher {
    /**
     * Called after a {@link TextWatcher} observes a text change event
     * @param theNewText the (now) current text of the text field
     * @param isError true if the current text means the textview shows as in error state
     */
    void onTextChanged(String theNewText, boolean isError);
}
