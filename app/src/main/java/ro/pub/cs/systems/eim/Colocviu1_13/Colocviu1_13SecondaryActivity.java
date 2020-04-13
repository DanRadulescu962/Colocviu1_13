package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Colocviu1_13SecondaryActivity extends AppCompatActivity {

    public class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register:
                    Intent intent = new Intent();
                    intent.putExtra("buton", "Register");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    break;
                case R.id.cancel:
                    Intent intent2 = new Intent();
                    intent2.putExtra("buton", "Cancel");
                    setResult(Activity.RESULT_OK, intent2);
                    finish();
                    break;

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_secondary);
        MyClickListener lst = new MyClickListener();
        Button reg = findViewById(R.id.register);
        Button cancel = findViewById(R.id.cancel);
        reg.setOnClickListener(lst);
        cancel.setOnClickListener(lst);

        Intent intent = getIntent();
        if (intent != null) {
            EditText txt = findViewById(R.id.content2);
            String o = (String)intent.getExtras().get("suma");
            txt.setText(o);
        }
    }
}
