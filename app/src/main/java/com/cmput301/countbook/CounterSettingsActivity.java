package com.cmput301.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CounterSettingsActivity extends AppCompatActivity {
    private Counter counter = new Counter();
    public String message = "";
    public static final String EXTRA_NAME = "com.example.myfirstapp.NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_settings);
        Intent intent = getIntent();

        message = intent.getStringExtra(CounterActivity.EXTRA_NAME);
        counter = MainActivity.counters.get(Integer.parseInt(message));
        EditText counter_name = (EditText) findViewById(R.id.counter_name);
        EditText counter_init_num = (EditText) findViewById(R.id.counter_init_num);
        EditText counter_curr_num = (EditText) findViewById(R.id.counter_curr_num);
        EditText counter_comment = (EditText) findViewById(R.id.counter_comment);
        counter_name.setText(counter.name);
        counter_curr_num.setText(counter.getNum());
        counter_init_num.setText(counter.getInitNum());
        counter_comment.setText(counter.comment);
    }
    public void changeSettings(View view) {
        EditText counter_name = (EditText) findViewById(R.id.counter_name);
        EditText counter_init_num = (EditText) findViewById(R.id.counter_init_num);
        EditText counter_curr_num = (EditText) findViewById(R.id.counter_curr_num);
        EditText counter_comment = (EditText) findViewById(R.id.counter_comment);
        String name = counter_name.getText().toString();
        int initNum = Integer.parseInt(counter_init_num.getText().toString());
        int currNum = Integer.parseInt(counter_curr_num.getText().toString());
        String comment = counter_comment.getText().toString();
        MainActivity.counters.set(Integer.parseInt(message),new Counter(name,initNum,currNum,comment));
        MainActivity.adapter.notifyDataSetChanged();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",message);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
    public void deleteCounter(View view) {
        Intent returnIntent = new Intent();
        MainActivity.counters.remove(Integer.parseInt(message));
        MainActivity.adapter.notifyDataSetChanged();
        saveInFile();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
    public void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(MainActivity.counters, writer);
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
}
