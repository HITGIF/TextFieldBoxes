package studio.carbonylgroup.textfieldboxestest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences sharedPreferences = this.getSharedPreferences("theme", Context.MODE_PRIVATE);
        final boolean dark = sharedPreferences.getBoolean("dark", false);

        if (dark) setTheme(R.style.AppThemeDark);
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button darkButton = findViewById(R.id.dark_button);
        if (dark) darkButton.setText("LIGHT SIDE");
        else darkButton.setText("DARK SIDE");
        darkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dark) {
                    sharedPreferences.edit().putBoolean("dark", false).apply();
                    restart();
                }
                else {
                    sharedPreferences.edit().putBoolean("dark", true).apply();
                    restart();
                }
            }
        });
    }

    public void restart() {

        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
