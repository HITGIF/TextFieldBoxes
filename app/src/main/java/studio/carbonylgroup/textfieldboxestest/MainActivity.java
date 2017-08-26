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

//        ((TextFieldBoxes) findViewById(R.id.tfb1)).setHelperText();
        ((TextFieldBoxes) findViewById(R.id.tfb1)).getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int actionId, KeyEvent event) {
                if (actionId == 66)
                    if (!((TextFieldBoxes) findViewById(R.id.tfb1)).getEditText().getText().toString().equals("Text fields"))
                        ((TextFieldBoxes) findViewById(R.id.tfb1)).setError("Error message");
                return false;
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
