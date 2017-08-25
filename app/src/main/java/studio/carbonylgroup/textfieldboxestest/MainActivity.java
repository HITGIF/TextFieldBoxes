package studio.carbonylgroup.textfieldboxestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Log.d("[][][", ((TextFieldBoxes)findViewById(R.id.tfb1)).getText().toString());
//        Log.d("[][][", ((TextFieldBoxes)findViewById(R.id.tfb2)).getText().toString());
//        Log.d("[][][", ((TextFieldBoxes)findViewById(R.id.tfb3)).getText().toString());
//        Log.d("[][][", ((TextFieldBoxes)findViewById(R.id.tfb4)).getText().toString());
//
//        Log.d("[][][", ((TextFieldBoxes)findViewById(R.id.tfb1)).getText().toString());
        findViewById(R.id.error1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("[][][", ((TextFieldBoxes)findViewById(R.id.tfb1)).getText());
            }
        });
        findViewById(R.id.error2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextFieldBoxes) findViewById(R.id.tfb1)).setError("LARGE \n ERROR MESSAGEE");
            }
        });
        findViewById(R.id.error3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextFieldBoxes) findViewById(R.id.tfb1)).setError("TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS TONS OF ERRORS ");
            }
        });
    }
}
