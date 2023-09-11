package com.example.myvocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Collect2Activity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private  RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int count = 0;
    String strTitle;
    private ArrayList<Dictionary> mArrayList;

    RadioGroup rGroup;
    RadioButton rBtn1, rBtn2, rBtn3;
    TextView vocaTitle;

    private MyDBHelper myHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_2);

        myHelper = new MyDBHelper(this);
        myHelper.open();

        Intent intent = getIntent();
        strTitle = intent.getStringExtra("title");

        rGroup = (RadioGroup)findViewById(R.id.rGroup);
        rBtn1 = (RadioButton)findViewById(R.id.Rbtn1);
        rBtn2 = (RadioButton)findViewById(R.id.Rbtn2);
        rBtn3 = (RadioButton)findViewById(R.id.Rbtn3);
        vocaTitle = (TextView)findViewById(R.id.vocaTitle);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mArrayList= new ArrayList<>();

        Cursor c = myHelper.fetchNeedNotes(strTitle);
        while (c.moveToNext()){
            count++;
            Dictionary data = new Dictionary(Integer.toString(count), c.getString(2), c.getString(3));
            mArrayList.add(data);
            vocaTitle.setText(c.getString(1));
        }

        mAdapter = new CustomAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if(i == rBtn1.getId())
                    mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount(),"click1");
                if(i == rBtn2.getId())
                    mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount(),"click2");
                if(i == rBtn3.getId())
                    mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount(),"click3");
            }
        });
    }


}
