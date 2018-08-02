package com.example.len.coolstorybro;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Story> storyList;
    private Context context;

    StoryListAdapter(Context context,ArrayList<Story> storyList){
        this.storyList = storyList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public StoryListAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryListAdapter.ViewHolder viewHolder, int i) {
        final Story story = storyList.get(i);
        viewHolder.tv.setText(story.getTitle());
        String preview = story.getContent();
        if(preview.length()>41){
            preview = preview.substring(0,40)+"...";
        }else{
            preview = preview;
        }
        viewHolder.tv2.setText(preview);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,StoryViewActivity.class);
                String number = story.getNumber();
                intent.putExtra("number", number);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv;
        final TextView tv2;
        final CardView cv;
        ViewHolder(View view){
            super(view);
            cv = (CardView) view.findViewById(R.id.cv_item);
            tv = (TextView) view.findViewById(R.id.tv_item);
            tv2 = (TextView) view.findViewById(R.id.tv_item2);
        }
    }
}
