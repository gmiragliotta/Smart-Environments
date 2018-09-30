package com.unime.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.unime.ctrlbeacon.Config;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private EditText editTextCounter;
    private EditText editIdObj;
    private EditText editIdUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCounter = (EditText) findViewById(R.id.counter);
        editIdObj = (EditText) findViewById(R.id.idobj);
        editIdUser = (EditText) findViewById(R.id.iduser);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute)
     */
    public void onButtonClick(View v) {
        int buttonClickedId = v.getId();

        Config mConfig = Config.getInstance(this);

        if (buttonClickedId == R.id.btnStart) {
            String counter = editTextCounter.getText().toString();
            String idUser = editIdUser.getText().toString();

            if (!counter.equals("counter")) {
                mConfig.setCounter(counter);
            }

            if (!idUser.equals("iduser")) {
                mConfig.setUserId(idUser);
            }

            String[] objectsId = {"0000", "0001"};

            mConfig.setObjectsId(Arrays.asList(objectsId));

            Intent intent = new Intent(this, ClassifierActivity.class);
            startActivity(intent);

        }

    }


}
