package com.example.len.coolstorybro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.len.coolstorybro.Parser;
import com.example.len.coolstorybro.R;
import com.example.len.coolstorybro.Story;
import com.example.len.coolstorybro.StoryListAdapter;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private RecyclerView rv;
    private ProgressBar pb;
    private EditText editText;
    String search;
    StoryListAdapter adapter;
    ArrayList<Story> stories = new ArrayList<>();

    Context context;

    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv_search);
        pb = (ProgressBar) view.findViewById(R.id.pb_search);
        editText = (EditText) view.findViewById(R.id.editText);

        pb.setVisibility(View.GONE);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        context = getContext();

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)){
                    search = editText.getText().toString();
                    if(context!= null){
                        if(isOnline(context)){
                            ProgressTask pt = new ProgressTask();
                            pt.execute();
                        }else{
                            Toast.makeText(context, "Проверьте ваше подключение к сети.", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }

    private class ProgressTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            rv.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stories = Parser.searchStory(search);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            rv.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);

            adapter = new StoryListAdapter(context,stories);
            rv.setAdapter(adapter);
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
