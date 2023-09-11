package com.example.myvocabulary;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<Dictionary> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView word;
        protected TextView meaning;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.voca_num);
            this.word = (TextView) view.findViewById(R.id.voca_word);
            this.meaning = (TextView) view.findViewById(R.id.voca_mean);
        }
    }

    public CustomAdapter(ArrayList<Dictionary> list) {
        this.mList = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_voca, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        viewholder.word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        viewholder.meaning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        viewholder.id.setGravity(Gravity.CENTER);
        viewholder.word.setGravity(Gravity.CENTER);
        viewholder.meaning.setGravity(Gravity.CENTER);

        viewholder.id.setText(mList.get(position).getId());
        viewholder.word.setText(mList.get(position).getWord());
        viewholder.meaning.setText(mList.get(position).getMeaning());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else{
            for(Object payload : payloads){
                if (payload instanceof String) {
                    String type = (String) payload;
                        if (TextUtils.equals(type, "click1")) {
                            holder.meaning.setVisibility(View.VISIBLE);
                            holder.word.setVisibility(View.VISIBLE);
                        } else if (TextUtils.equals(type, "click2")) {
                            holder.meaning.setVisibility(View.INVISIBLE);
                            holder.word.setVisibility(View.VISIBLE);
                        } else if (TextUtils.equals(type, "click3")) {
                            holder.meaning.setVisibility(View.VISIBLE);
                            holder.word.setVisibility(View.INVISIBLE);
                        }

                }
            }
        }
    }

    @Override
    public int getItemCount() {
       return mList.size();
    }
}
