package com.viger.mycode.recyclerview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viger.mycode.R;

import java.util.ArrayList;
import java.util.List;

public class FruitActivity extends AppCompatActivity {

    private Fruit[] fruits = {new Fruit("Apple", R.drawable.ic_launcher_background),
            new Fruit("Banana", R.drawable.ic_launcher_background)};

    private List<Fruit> fruitList = new ArrayList<Fruit>();

    private FruitAdapter fruitAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recyclerview);
        fruitAdapter = new FruitAdapter(this, fruitList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(fruitAdapter);
    }
}
