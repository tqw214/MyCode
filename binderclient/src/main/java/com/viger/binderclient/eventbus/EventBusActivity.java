package com.viger.binderclient.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.binderclient.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void changeText(View v) {
        EventBus.getDefault().post(new String("我是新文字"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showText(String message) {
        ((TextView)findViewById(R.id.tv)).setText(message);
    }
}
