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

//version 0.2 since 0.3
public class NewCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_counter);
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editInitNum = (EditText) findViewById(R.id.editInitNum);
        editName.setText("Counter");
        editInitNum.setText("0");
    }

    public void createCounter(View view){
        // Do something in response to button

        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editInitNum = (EditText) findViewById(R.id.editInitNum);
        EditText editComment = (EditText) findViewById(R.id.editComment);
        String name = editName.getText().toString();
        String num = editInitNum.getText().toString();
        int num2 = Integer.parseInt(num);
        String comment = editComment.getText().toString();
        MainActivity.counters.add(new Counter(name,num2,comment));
        saveInFile();
        finish();
        MainActivity.adapter.notifyDataSetChanged();
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
