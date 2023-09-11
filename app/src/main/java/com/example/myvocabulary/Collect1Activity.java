package com.example.myvocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collect1Activity extends AppCompatActivity {

    private ListView listView;
    List titleList = new ArrayList<>();
    ArrayAdapter adapter;

    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_collect_1);

            dbHelper = new MyDBHelper(this);
            dbHelper.open();

        /*
        dbHelper.createNote("토익 영단어","account","계산");
        dbHelper.createNote("토익 영단어","account for","~를 설명하다");
        dbHelper.createNote("토익 영단어","condense","압축하다");
        dbHelper.createNote("토익 영단어","better","~를 개선하다");
        dbHelper.createNote("토익 영단어","conduct","시행하다");
        dbHelper.createNote("토익 영단어","accrue","얻어지다");
        dbHelper.createNote("토익 영단어","conductor","지휘자 ");
        dbHelper.createNote("토익 영단어","confirmation","확정서\n");
        dbHelper.createNote("토익 영단어","be aware of","~를 조심하다");
        dbHelper.createNote("토익 영단어","biannual","반년마다의");
        dbHelper.createNote("토익 영단어","accumulation","축적물");
        dbHelper.createNote("토익 영단어","confirmed","확인된");
        dbHelper.createNote("토익 영단어","beneficiary","수혜자");
        dbHelper.createNote("토익 영단어","confiscate","몰수하다");
        dbHelper.createNote("토익 영단어","binding","의무적인");
         */

        Cursor c = dbHelper.fetchTitleNotes();
        if(c!=null && c.moveToFirst()) {
            do {
                titleList.add(c.getString(0));
            } while (c.moveToNext());
        }

        listView = (ListView)findViewById(R.id.col_list);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, titleList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object[] array = titleList.toArray(new String[titleList.size()]);
                String[] vocaTitle = Arrays.copyOf(array, array.length, String[].class);

                Intent intent = new Intent(Collect1Activity.this, Collect2Activity.class);
                intent.putExtra("title", vocaTitle[position]);
                startActivity(intent);
            }
        });
    }


}
