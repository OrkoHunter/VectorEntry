package net.orkohunter.vectorentrygestureexperiments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testGuided(View view) {
        Intent intent = new Intent(this, TestGuided.class);

        Switch s = (Switch) findViewById(R.id.switch_debug);
        intent.putExtra("isDebugTest", s.isChecked());

        startActivity(intent);
    }
}
