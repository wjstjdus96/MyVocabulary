package com.example.myvocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class  TestActivity extends AppCompatActivity {

    private MyDBHelper dbHelper;
    Button gotoTest;
    String testWord, testMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_1);

        gotoTest = (Button) findViewById(R.id.goTest);
        dbHelper = new MyDBHelper(this);
        dbHelper.open();

        Spinner spinnerWord = (Spinner)findViewById(R.id.spinnerWord);
        ArrayList listWord = new ArrayList<>();

        Cursor c = dbHelper.fetchTitleNotes();
        if(c!=null && c.moveToFirst()) {
            do {
                listWord.add(c.getString(0));
            } while (c.moveToNext());
        }

        ArrayAdapter arrayAdapter1 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listWord);
        spinnerWord.setAdapter(arrayAdapter1);
        spinnerWord.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object[] array = listWord.toArray(new String[listWord.size()]);
                String[] ary = Arrays.copyOf(array, array.length, String[].class);
                testWord = ary[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*
        Spinner spinnerMode = (Spinner)findViewById(R.id.spinnerMode);
        ArrayList listMode = new ArrayList<>();
        listMode.add("단어 가리기");
        listMode.add("뜻 가리기");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listMode);
        spinnerMode.setAdapter(arrayAdapter2);
        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object[] array = listMode.toArray(new String[listMode.size()]);
                String[] ary = Arrays.copyOf(array, array.length, String[].class);
                testWord = ary[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         */

        gotoTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, Test2Activity.class);
                intent.putExtra("testWord", testWord );
                //intent.putExtra("testMode", testMode);
                startActivity(intent);
            }
        });

    }
}
