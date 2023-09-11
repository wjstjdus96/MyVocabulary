package com.example.myvocabulary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private  RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Dictionary> mArrayList;

    private DatabaseReference mDatabase;
    List<String> titleList = new ArrayList<String>();
    EditText etWord, etMean, etTitle;
    Button btnAddWord;
    int wordId = 1;
    private MyDBHelper myHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        myHelper = new MyDBHelper(this);
        myHelper.open();

        etWord = (EditText)findViewById(R.id.etWord);
        etMean = (EditText)findViewById(R.id.etMean);
        etTitle = (EditText)findViewById(R.id.etTitle);
        btnAddWord = (Button)findViewById(R.id.addword);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String title = etTitle.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot fileSnapshot : snapshot.getChildren()){
                    String str = fileSnapshot.getKey();
                    titleList.add(str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.createRecycle);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mArrayList= new ArrayList<>();

        btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getWord = etWord.getText().toString();
                String getMean = etMean.getText().toString();

                myHelper.createNote(etTitle.getText().toString(), getWord, getMean);

                Dictionary data = new Dictionary(Integer.toString(wordId),getWord, getMean);
                mArrayList.add(data);
                wordId++;
                mAdapter = new CustomAdapter(mArrayList);
                mRecyclerView.setAdapter(mAdapter);

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("word", getWord);
                result.put("name", getMean);


                writeNewWord("", getWord,getMean);
                etWord.setText("");
                etMean.setText("");
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void writeNewWord(String wordId, String wording, String meaning) {
        Word word = new Word(wording, meaning);
        String title = etTitle.getText().toString();

        mDatabase.child(title).push().setValue(word)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateActivity.this, "추가 완료", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateActivity.this, "추가 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

