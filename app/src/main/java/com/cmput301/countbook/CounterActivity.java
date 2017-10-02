package com.cmput301.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

// version 0.3 since 0.3
//https://stackoverflow.com/questions/30642474/update-activity-from-another-activity
public class CounterActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.myfirstapp.NAME";
    public static Counter counter = new Counter();
    public String message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_NAME);
        counter = MainActivity.counters.get(Integer.parseInt(message));
        // Capture the layout's TextView and set the string as its text
        TextView textView3 = (TextView) findViewById(R.id.counter_comment);
        TextView textView2 = (TextView) findViewById(R.id.counter_num);
        TextView textView = (TextView) findViewById(R.id.counter_name);
        textView.setText(counter.name);
        textView2.setText(counter.getNum());
        textView3.setText(counter.comment);
    }

    public void increaseCounter(View view) {
        counter.num +=1;
        MainActivity.counters.set(Integer.parseInt(message),counter);
        TextView textView2 = (TextView) findViewById(R.id.counter_num);
        textView2.setText(counter.getNum());
        MainActivity.adapter.notifyDataSetChanged();
        saveInFile();
    }
    public void decreaseCounter(View view) {
        counter.num -=1;
        MainActivity.counters.set(Integer.parseInt(message),counter);
        TextView textView2 = (TextView) findViewById(R.id.counter_num);
        textView2.setText(counter.getNum());
        MainActivity.adapter.notifyDataSetChanged();
        saveInFile();
    }
    public void counterSettings(View view) {
        Intent intent = new Intent(this, CounterSettingsActivity.class);
        intent.putExtra(EXTRA_NAME, message);
        startActivityForResult(intent,1);
    }
    public void resetCounter(View view) {
        counter.num = counter.init_num;
        MainActivity.counters.set(Integer.parseInt(message),counter);
        TextView textView2 = (TextView) findViewById(R.id.counter_num);
        textView2.setText(counter.getNum());
        MainActivity.adapter.notifyDataSetChanged();
        saveInFile();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                counter = MainActivity.counters.get(Integer.parseInt(message));
                TextView textView2 = (TextView) findViewById(R.id.counter_num);
                textView2.setText(counter.getNum());
            }
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }//onActivityResult
}
