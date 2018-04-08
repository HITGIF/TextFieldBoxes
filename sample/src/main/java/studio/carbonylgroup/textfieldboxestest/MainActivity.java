package studio.carbonylgroup.textfieldboxestest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = this.getSharedPreferences("theme", Context.MODE_PRIVATE);
        final boolean dark = sharedPreferences.getBoolean("dark", false);
        setTheme(dark ? R.style.AppThemeDark : R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final TextFieldBoxes tfb = findViewById(R.id.text_field_boxes2);
        final ExtendedEditText t = findViewById(R.id.extendedEditText);

        final String[] aWords = {"Apple", "Android", "Alternative"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, aWords);
        t.setThreshold(1);
        t.setAdapter(adapter);

        setupDarkThemeButton(dark);
        setupErrorButton();
    }

    private void setupDarkThemeButton(final boolean dark) {

        final Button darkButton = findViewById(R.id.dark_button);

        darkButton.setText(dark ? "LIGHT SIDE" : "DARK SIDE");
        darkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putBoolean("dark", !dark).apply();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    private void setupErrorButton(){
        final Button errorButton = findViewById(R.id.error_button);
        final TextFieldBoxes errorField = findViewById(R.id.text_field_boxes5);
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorField.setError("Invalid coupon code.", false);
            }
        });
    }
}
