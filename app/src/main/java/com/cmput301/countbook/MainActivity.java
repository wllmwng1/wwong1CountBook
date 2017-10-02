package com.cmput301.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//version 0.5 since 0.1
public class MainActivity extends AppCompatActivity {
    public static final String FILENAME = "file.sav";
    public static final String EXTRA_NAME = "name";
    public ListView counterlist;
    public static ArrayList<Counter> counters = new ArrayList<>();
    public static ArrayAdapter<Counter> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();
        counterlist = (ListView) findViewById(R.id.counter_list);
        adapter = new ArrayAdapter<Counter>(this, R.layout.list_view, counters);
        counterlist.setAdapter(adapter);
        counterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Counter listItem = (Counter) counterlist.getItemAtPosition(position);
                gotoCounter(position);
        };
    });}


    public void gotoCounter(int position) {

        Intent intent = new Intent(this, CounterActivity.class);
        intent.putExtra(EXTRA_NAME, Integer.toString(position));
        startActivity(intent);
    }
    public void createCounter(View view) {

        Intent intent = new Intent(this, NewCounterActivity.class);
        startActivity(intent);
    }

    public void clearCounter(View view) {
        counters.clear();
        adapter.notifyDataSetChanged();
        saveInFile();
    }


    public void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {
            }.getType();
            counters = gson.fromJson(in, listType);
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counters = new ArrayList<Counter>();
        }
    }
}