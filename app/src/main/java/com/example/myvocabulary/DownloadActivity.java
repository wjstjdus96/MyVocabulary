package com.example.myvocabulary;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity implements TextWatcher {

    private FilterAdapter filterAdapter;
    EditText etDownTitle;
    RecyclerView recyclerView;

    List titleList = new ArrayList<>();
    static boolean calledAlready = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        etDownTitle = findViewById(R.id.downTitle);
        etDownTitle.addTextChangedListener(this);

        recyclerView = findViewById(R.id.my_recycler_view);
        filterAdapter = new FilterAdapter(this, titleList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filterAdapter);

        if(!calledAlready){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행
            calledAlready = true;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot fileSnapshot : snapshot.getChildren()){
                    String str = fileSnapshot.getKey();
                    Log.i("TAG: value is ", str);
                    titleList.add(str);
                }
                filterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG: ","Failed to read value", error.toException());

            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterAdapter.getFilter().filter(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
