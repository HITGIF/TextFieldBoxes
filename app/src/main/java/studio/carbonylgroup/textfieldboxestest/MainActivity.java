package studio.carbonylgroup.textfieldboxestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextFieldBoxes textFieldBoxes = findViewById(R.id.text_field_boxes);
        textFieldBoxes.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("wrong"))
                    textFieldBoxes.setError("It's wrong");
            }
        });

//        findViewById(R.id.error1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                findViewById(R.id.tfb1).setEnabled(!findViewById(R.id.tfb1).isEnabled());
//                findViewById(R.id.tfb4).setEnabled(!findViewById(R.id.tfb4).isEnabled());
//            }
//        });
//        findViewById(R.id.error2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((TextFieldBoxes) findViewById(R.id.tfb1)).setError("Error Message");
//            }
//        });
//        findViewById(R.id.error3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((TextFieldBoxes) findViewById(R.id.tfb1)).setError("TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS ");
//            }
//        });
    }
}
