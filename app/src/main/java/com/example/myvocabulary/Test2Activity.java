package com.example.myvocabulary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Test2Activity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private  RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Dictionary> mArrayList;
    private int count = 0;
    int totNum, resNum = 0;
    String testWord;
    Button btnResult;

    private MyDBHelper myHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_2);

        Intent intent = getIntent();
        testWord = intent.getStringExtra("testWord");

        myHelper = new MyDBHelper(this);
        myHelper.open();

        btnResult = (Button)findViewById(R.id.btnResult);

        mRecyclerView = (RecyclerView)findViewById(R.id.testRecycle);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArrayList= new ArrayList<>();

        Cursor c = myHelper.fetchNeedNotes(testWord);
        while (c.moveToNext()){
            count++;
            Dictionary data = new Dictionary(Integer.toString(count), c.getString(2), c.getString(3));
            mArrayList.add(data);
        }
        mAdapter = new CustomTestAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        totNum = mArrayList.size();
        for(int i = 0; i < totNum; i++){

        }

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("결과")
                        .setMessage(totNum + "중에" + resNum + "맞히셨습니다")
                        .setPositiveButton("틀린 문제 확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        /*
        private ArrayList<EditModel> populateList(){

            ArrayList<EditModel> list = new ArrayList<>();

            for(int i = 0; i < 8; i++){
                EditModel editModel = new EditModel();
                editModel.setEditTextValue(String.valueOf(i));
                list.add(editModel);
            }

            return list;
        }
        
         */


    }
}
