package com.example.myvocabulary;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomTestAdapter extends RecyclerView.Adapter<CustomTestAdapter.CustomTestViewHolder>{
    private ArrayList<Dictionary> mList;

    public class CustomTestViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView word;
        protected EditText meaning;

        public CustomTestViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.voca_testnum);
            this.word = (TextView) view.findViewById(R.id.voca_testword);
            meaning = (EditText) view.findViewById(R.id.voca_testmean);
            meaning.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mList.get(getAdapterPosition()).setMeaning(meaning.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public CustomTestAdapter(ArrayList<Dictionary> list) {
        this.mList = list;
    }

    @Override
    public CustomTestViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_testvoca, viewGroup, false);
        CustomTestViewHolder viewHolder = new CustomTestViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomTestViewHolder holder, int position) {
        holder.id.setText(mList.get(position).getId());
        holder.word.setText(mList.get(position).getWord());
        holder.meaning.setText(mList.get(position).getMeaning());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
