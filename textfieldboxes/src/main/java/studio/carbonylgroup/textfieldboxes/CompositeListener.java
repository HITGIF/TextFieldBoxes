package studio.carbonylgroup.textfieldboxes;

import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Text Field Boxes
 * Aux class to collect multiple click listeners
 * Created by CarbonylGroup on 2017/11/22
 */
class CompositeListener implements View.OnFocusChangeListener {

    private List<View.OnFocusChangeListener> registeredListeners = new ArrayList<>();

    void registerListener (View.OnFocusChangeListener listener) {
        registeredListeners.add(listener);
    }

    void clearListeners() {
        registeredListeners.clear();
    }

    @Override
    public void onFocusChange(View view, boolean b) {

        for(View.OnFocusChangeListener listener:registeredListeners) {
            listener.onFocusChange(view, b);
        }
    }
}
