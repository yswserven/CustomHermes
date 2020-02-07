package com.custom.hermes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.custom.hermes.R;
import com.custom.hermes.core.Hermes;
import com.custom.hermes.mode.HermesTestInterface;
import com.custom.hermes.service.HermesService;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvValue = findViewById(R.id.tv_value);
    }

    public void connect(View view) {
        Hermes.getInstance().connect(this, HermesService.class);
    }

    public void getValue(View view) {
        HermesTestInterface anInterface = Hermes.getInstance().getInstance(HermesTestInterface.class);
        tvValue.setText(String.format("获取A进程变量的值：名字 = %s 年龄 = %s", anInterface.getName(), anInterface.getAge()));
    }
}
