package studio.carbonylgroup.textfieldboxestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextFieldBoxes) findViewById(R.id.tfb1)).removeMinCharacters();

        findViewById(R.id.error1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextFieldBoxes) findViewById(R.id.tfb1)).setError("Small Error");
            }
        });
        findViewById(R.id.error2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextFieldBoxes) findViewById(R.id.tfb1)).setError("LARGE \n ERROR");
            }
        });
        findViewById(R.id.error3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextFieldBoxes) findViewById(R.id.tfb1)).setError("TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR TONS OF ERROR ");
            }
        });
    }
}
