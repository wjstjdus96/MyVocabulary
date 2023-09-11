package com.example.myvocabulary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> implements Filterable {

    private List<String> unfilterList, filterList;
    private Context context;
    private MyDBHelper dbHelper;

    public FilterAdapter(Context context, List<String> unfilterList){
        this.unfilterList = unfilterList;
        this.filterList = unfilterList;
        this.context = context;

    }

    @NonNull
    @Override
    public FilterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_download, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(filterList.get(position));

        final Context myContext = holder.itemView.getContext();
        String clickList = holder.tvTitle.getText().toString();

        dbHelper = new MyDBHelper(myContext);
        dbHelper.open();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference();

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot fileSnapshot : snapshot.child(clickList).getChildren()){
                            String word = fileSnapshot.child("word").getValue(String.class);
                            String meaning = fileSnapshot.child("meaning").getValue(String.class);
                            dbHelper.createNote(clickList, word, meaning);

                        }
                        Toast.makeText(myContext,"다운완료", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    String word = data.child("word").getValue(String.class);
                    String meaning = data.child("meaning").getValue(String.class);
;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if (str.isEmpty()){
                    filterList = unfilterList;
                } else {
                    List<String> filteringList = new ArrayList<>();

                    for(String item : unfilterList){
                        if(item.toLowerCase().contains(str))
                            filteringList.add(item);
                    }
                    filterList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList = (List<String>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        Button btn;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.downTitle);
            btn = itemView.findViewById(R.id.downBtn);
        }
    }
}
