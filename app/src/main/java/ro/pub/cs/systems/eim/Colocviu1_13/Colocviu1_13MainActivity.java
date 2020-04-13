package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Colocviu1_13MainActivity extends AppCompatActivity {

    private int pressed = 0;

    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver;
    private IntentFilter startedServiceIntentFilter;

    public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

        // TODO: exercise 9 - default constructor
        public StartedServiceBroadcastReceiver() {
            Log.d("My debug", "Called default constructpr!");
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: exercise 7 - get the action and the extra information from the intent
            Log.d("My debug", (String) intent.getExtras().get("transmis"));
        }
    }

    public class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.north:
                    EditText txt = findViewById(R.id.content);
                    txt.append(", NORTH");
                    pressed += 1;
                    break;
                case R.id.east:
                    EditText txt2 = findViewById(R.id.content);
                    txt2.append(", EAST");
                    pressed += 1;
                    break;
                case R.id.west:
                    EditText txt3 = findViewById(R.id.content);
                    txt3.append(", WEST");
                    pressed += 1;
                    break;
                case R.id.south:
                    EditText txt4 = findViewById(R.id.content);
                    txt4.append(", SOUTH");
                    pressed += 1;
                    break;
                case R.id.navigate:
                    Intent intent = new Intent(getApplicationContext(), Colocviu1_13SecondaryActivity.class);
                    EditText txt5 = findViewById(R.id.content);
                    intent.putExtra("suma", txt5.getText().toString());
                    intent.setAction("actiune_mare");
                    pressed = 0;
                    txt5.setText("");
                    startActivityForResult(intent, 2020);
                    break;
            }
            if (pressed >= 4) {
                Intent intent = new Intent();
                EditText txt5 = findViewById(R.id.content);
                intent.putExtra("val", txt5.getText().toString());
                intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.Colocviu1_13", "ro.pub.cs.systems.eim.Colocviu1_13.StartedService"));
                startService(intent);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("My msg", "Got here");
        outState.putString("cheie", Integer.valueOf(pressed).toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String output = (String) savedInstanceState.get("cheie");
        pressed = Integer.parseInt(output);
        if (pressed != 0)
            Log.d("My msg", "Recuperat " + Integer.valueOf(pressed).toString());;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2020) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getApplicationContext(), (String) data.getExtras().get("buton"), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_main);

        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver();
        // TODO: exercise 8b - create an instance of an IntentFilter
        // with all available actions contained within the broadcast intents sent by the service
        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction("actiune2");
        registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter);

        pressed = 0;
        Button north = findViewById(R.id.north);
        Button south = findViewById(R.id.south);
        Button west = findViewById(R.id.west);
        Button east = findViewById(R.id.east);
        Button nav = findViewById(R.id.navigate);
        MyClickListener lst = new MyClickListener();
        north.setOnClickListener(lst);
        south.setOnClickListener(lst);
        east.setOnClickListener(lst);
        west.setOnClickListener(lst);
        nav.setOnClickListener(lst);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(startedServiceBroadcastReceiver);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.Colocviu1_13", "ro.pub.cs.systems.eim.Colocviu1_13.StartedService"));
        stopService(intent);
        super.onDestroy();
    }
}
