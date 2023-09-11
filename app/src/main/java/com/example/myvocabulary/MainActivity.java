package com.example.myvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void collect1Click(View v) {
        Intent intent = new Intent(MainActivity.this, Collect1Activity.class);
        startActivity(intent);
    }

    public void createClick(View v) {
        Intent intent = new Intent(MainActivity.this, CreateActivity.class);
        startActivity(intent);
    }

    public void downloadClick(View v) {
        Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
        startActivity(intent);
    }

    public void testClick(View v) {
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        startActivity(intent);
    }
}