package com.viger.customview.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.customview.R;

import java.util.ArrayList;
import java.util.List;

public class Custom08Activity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom08);

        mListView = (ListView) findViewById(R.id.list_view);

        mItems = new ArrayList<String>();

        for (int i=0;i<200;i++){
            mItems.add("i -> "+i);
        }

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public String getItem(int position) {
                return mItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) LayoutInflater.from(Custom08Activity.this).inflate(R.layout.item_lv,null);
                textView.setText(getItem(position));
                return textView;
            }
        });
    }
}
