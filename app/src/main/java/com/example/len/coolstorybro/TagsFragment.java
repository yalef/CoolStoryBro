package com.example.len.coolstorybro;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.len.coolstorybro.Parser;
import com.example.len.coolstorybro.R;
import com.example.len.coolstorybro.StoryListActivity;

import java.util.ArrayList;


public class TagsFragment extends Fragment {

    ListView lv;
    ProgressBar pb;
    ArrayList<String> tags_list;
    ArrayAdapter<String> adapter;
    private Context context;
    public TagsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_tags, container, false);*/
        //View view = inflater.inflate(R.layout.fragment_tags, container, false);
        View view = inflater.inflate(R.layout.fragment_tags, container, false);
        lv = (ListView) view.findViewById(R.id.lv);

        pb = (ProgressBar) view.findViewById(R.id.progressBarTags);
        pb.setVisibility(View.INVISIBLE);

        context = getContext();
        if(context!=null){
            if(isOnline(context)){
                ProgressTask progressTask = new ProgressTask();
                progressTask.execute();
            }else{
                Toast.makeText(context, "Проверьте ваше подключение к сети.", Toast.LENGTH_SHORT).show();
            }
        }


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),StoryListActivity.class);
                intent.putExtra("tag",tags_list.get(i));
                startActivity(intent);
            }
        });

        return view;
    }
    private class ProgressTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            lv.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tags_list = Parser.getTags();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pb.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
            adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,tags_list);
            lv.setAdapter(adapter);
        }
    }

    private static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
}
