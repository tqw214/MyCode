package com.viger.binderclient.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.binderclient.R;

public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        DNEventbus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DNEventbus.getDefault().unregister(this);
    }

    public void changeText(View v) {
        DNEventbus.getDefault().post(new String("我是新文字"));
    }

    @DNSubscribe(threadMode = DNThreadMode.MAIN)
    public void showText(String message) {
        ((TextView)findViewById(R.id.tv)).setText(message);
    }
}
