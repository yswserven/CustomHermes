package com.custom.hermes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.custom.hermes.R;
import com.custom.hermes.core.Hermes;
import com.custom.hermes.mode.HermesTestMode;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hermes.getInstance().register(HermesTestMode.class);
    }

    public void setValue(View view) {
        HermesTestMode.getInstance().setName("杨胜文");
        HermesTestMode.getInstance().setAge("30");
    }

    public void jump(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}
